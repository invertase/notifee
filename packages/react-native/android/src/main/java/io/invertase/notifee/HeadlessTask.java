/*
 * This software has been integrated, with great appreciation, from the
 * react-native-background-geolocation library, and was originally authored by
 * Chris Scott @ TransistorSoft. It is published in that repository under this license,
 * included here in it's entirety
 *
 * https://github.com/transistorsoft/react-native-background-geolocation/blob/master/LICENSE
 *
 * ----
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Chris Scott
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.invertase.notifee;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.notifee.core.EventSubscriber;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceEventListener;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * The BackgroundGeolocation SDK creates a single instance of this class (via reflection upon Config.headlessJobService)
 * The SDK delivers events to this instance by dropping them onto EventBus (see @Subscribe).  Because this instance is subscribed
 * into EventBus, it's protected from GC.
 *
 * Because there's only one instance of this class, we have to be mindful that we can possibly receive events rapidly,
 * so we store them in a Queue (#mTaskQueue).
 *
 * We also have to be mindful that it's a heavy operation to do the initial launch the ReactNative Host, so several events
 * might build up in the queue before the Host is finally launched, when we drain the queue (see #drainTaskQueue).
 *
 * For finally sending events to the client, we wrap the RN HeadlessJsTaskConfig with our own TaskConfig class.  This class
 * adds our own auto-incremented "taskId" field to maintain a mapping between our taskId and RN's.  See #invokeStartTask.
 * This class appends our custom taskId into the the event params sent to Javascript, for the following purpose:
 *
 * ```javascript
 * const BackgroundGeolocationHeadlessTask = async (event) => {
 *   console.log('[HeadlessTask] taskId: ', event.taskId);  // <-- here's our custom taskId.
 *
 *   await doWork();  // <-- perform some arbitrarily long process (eg: http request).
 *
 *   BackgroundGeolocation.finishHeadlessTask(event.taskId);  // <-- $$$ Here's the money $$$
 * }
 * ```
 *
 * See "Here's the $money" above: We want to signal back to this native HeadlessTask instance , that our JS task is now complete.
 * This is a pretty easy task to do via EventBus -- Just create a new instance of FinishHeadlessTaskEvent(params.taskId);
 *
 * The code then looks up a TaskConfig from mEventQueue using the given event.taskId.
 *
 * All this extra fussing with taking care to finish our RN HeadlessTasks seems to be more important with RN's "new architecture", where before,
 * RN seemed to automatically complete its tasks seemingly when the Javascript function stopped executing.  This is why it was always so
 * important to await one's Promises and do all the work before the the last line of the function executed.
 *
 * Now, the Javascript must explicitly call back to the native side to say "I'M DONE" -- BackgroundGeolocation.finishHeadlessTask(taskId)
 *
 * Created by chris on 2018-01-23.
 */

public class HeadlessTask {
  private static final String HEADLESS_TASK_NAME = "NotifeeHeadlessJS";
  // Hard-coded time-limit for headless-tasks is 60000 @todo configurable?
  private static final int TASK_TIMEOUT = 60000;
  private static final AtomicInteger sLastTaskId = new AtomicInteger(0);

  static synchronized int getNextTaskId() {
    return sLastTaskId.incrementAndGet();
  }

  private final List<TaskConfig> mTaskQueue = new ArrayList<>();
  private final AtomicBoolean mIsReactContextInitialized = new AtomicBoolean(false);
  private final AtomicBoolean mWillDrainTaskQueue = new AtomicBoolean(false);
  private final AtomicBoolean mIsInitializingReactContext = new AtomicBoolean(false);
  private final AtomicBoolean mIsHeadlessJsTaskListenerRegistered = new AtomicBoolean(false);

  public void stopAllTasks() {
    for (TaskConfig task : mTaskQueue) {
      onFinishHeadlessTask(task.getTaskId());
    }
  }

  /**
   * EventBus Receiver. Called when developer runs BackgroundGeolocation.finishHeadlessTask(taskId)
   * from within their registered HeadlessTask. Calls RN's HeadlessJsTaskContext.finishTask(taskId);
   *
   * @param taskId the taskId returned from startTask
   */
  public void onFinishHeadlessTask(int taskId) {
    if (!mIsReactContextInitialized.get()) {
      Log.w(HEADLESS_TASK_NAME, taskId + " found no ReactContext");
      return;
    }
    ReactContext reactContext = getReactContext(EventSubscriber.getContext());
    if (reactContext != null) {

      synchronized (mTaskQueue) {
        // Locate the TaskConfig instance by our local taskId.
        TaskConfig taskConfig = null;
        for (TaskConfig config : mTaskQueue) {
          if (config.getTaskId() == taskId) {
            taskConfig = config;
            break;
          }
        }
        if (taskConfig != null) {
          HeadlessJsTaskContext headlessJsTaskContext =
              HeadlessJsTaskContext.getInstance(reactContext);
          // Tell RN we're done using the mapped getReactTaskId().
          headlessJsTaskContext.finishTask(taskConfig.getReactTaskId());
        } else {
          Log.w(HEADLESS_TASK_NAME, "Failed to find task: " + taskId);
        }
      }
    } else {
      Log.w(
          HEADLESS_TASK_NAME,
          "Failed to finishHeadlessTask: "
              + taskId
              + " -- HeadlessTask onFinishHeadlessTask failed to find a ReactContext.  This is"
              + " unexpected");
    }
  }

  /**
   * Start a task. This method handles starting a new React instance if required.
   *
   * <p>Has to be called on the UI thread.
   *
   * @param taskConfig describes what task to start and the parameters to pass to it
   */
  public void startTask(Context context, final TaskConfig taskConfig) throws AssertionError {
    UiThreadUtil.assertOnUiThread();

    // push this HeadlessEvent onto the taskQueue, to be drained once the React context is finished
    // initializing,
    // or executed immediately if Context exists currently.
    synchronized (mTaskQueue) {
      mTaskQueue.add(taskConfig);
    }

    if (!mIsReactContextInitialized.get()) {
      createReactContextAndScheduleTask(context);
    } else {
      invokeStartTask(getReactContext(context), taskConfig);
    }
  }

  private synchronized void invokeStartTask(
      ReactContext reactContext, final TaskConfig taskConfig) {
    if (taskConfig.mReactTaskId > 0) {
      Log.w(HEADLESS_TASK_NAME, "Task already invoked <IGNORED>: " + this);
      return;
    }
    final HeadlessJsTaskContext headlessJsTaskContext =
        HeadlessJsTaskContext.getInstance(reactContext);
    try {
      if (mIsHeadlessJsTaskListenerRegistered.compareAndSet(false, true)) {
        // Register the RN HeadlessJSTaskEventListener just once.
        // This inline-listener is handy here as a closure around the HeadlessJsTaskContext.
        // Otherwise, we'd have to store this Context to an instance var.
        // The only purpose of this listener is to clear events from the queue.  The RN task is
        // assumed to have had its .stopTask(taskId) method called, which is why this event has
        // fired.
        // WARNING:  this listener seems to receive events from ANY OTHER plugin's Headless events.
        // TODO we might use a LifeCycle event-listener here to remove the listener when the app is
        // launched to foreground.
        headlessJsTaskContext.addTaskEventListener(
            new HeadlessJsTaskEventListener() {
              @Override
              public void onHeadlessJsTaskStart(int taskId) {}

              @Override
              public void onHeadlessJsTaskFinish(int taskId) {
                synchronized (mTaskQueue) {
                  if (mTaskQueue.isEmpty()) {
                    // Nothing in queue?  This event cannot be for us.
                    return;
                  }
                  // Query our queue for this event...
                  TaskConfig taskConfig = null;
                  for (TaskConfig config : mTaskQueue) {
                    if (config.getReactTaskId() == taskId) {
                      taskConfig = config;
                      break;
                    }
                  }
                  if (taskConfig != null) {
                    // Clear it from the Queue.
                    Log.d(HEADLESS_TASK_NAME, "taskId: " + taskConfig.getTaskId());
                    mTaskQueue.remove(taskConfig);
                    if (taskConfig.getCallback() != null) {
                      taskConfig.getCallback().call();
                    }
                  } else {
                    Log.w(HEADLESS_TASK_NAME, "Failed to find taskId: " + taskId);
                  }
                }
              }
            });
      }
      // Finally:  the actual launch of the RN headless-task!
      int taskId = headlessJsTaskContext.startTask(taskConfig.getTaskConfig());
      // Provide the RN taskId to our private TaskConfig instance, mapping the RN taskId to our
      // TaskConfig's internal taskId.
      taskConfig.setReactTaskId(taskId);
      Log.d(HEADLESS_TASK_NAME, "taskId: " + taskId);
    } catch (IllegalStateException e) {
      Log.e(HEADLESS_TASK_NAME, e.getMessage(), e);
    }
  }

  public static ReactNativeHost getReactNativeHost(Context context) {
    return ((ReactApplication) context.getApplicationContext()).getReactNativeHost();
  }

  /** Get the {ReactHost} used by this app. ure and returns null if not. */
  public static @Nullable Object getReactHost(Context context) {
    context = context.getApplicationContext();
    try {
      Method getReactHost = context.getClass().getMethod("getReactHost");
      return getReactHost.invoke(context);
    } catch (Exception e) {
      return null;
    }
  }

  public static ReactContext getReactContext(Context context) {
    if (isBridgelessArchitectureEnabled()) {
      Object reactHost = getReactHost(context);
      Assertions.assertNotNull(reactHost, "getReactHost() is null in New Architecture");
      try {
        Method getCurrentReactContext = reactHost.getClass().getMethod("getCurrentReactContext");
        return (ReactContext) getCurrentReactContext.invoke(reactHost);
      } catch (Exception e) {
        Log.e(HEADLESS_TASK_NAME, "Reflection error getCurrentReactContext: " + e.getMessage(), e);
      }
    }
    final ReactInstanceManager reactInstanceManager =
        getReactNativeHost(context).getReactInstanceManager();
    return reactInstanceManager.getCurrentReactContext();
  }

  private void createReactContextAndScheduleTask(Context context) {

    // ReactContext could have already been initialized by another plugin (eg: background-fetch).
    // If we get a non-null ReactContext here, we're good to go!
    ReactContext reactContext = getReactContext(context);
    if (reactContext != null && !mIsInitializingReactContext.get()) {
      mIsReactContextInitialized.set(true);
      drainTaskQueue(reactContext);
      return;
    }
    if (mIsInitializingReactContext.compareAndSet(false, true)) {
      Log.d(HEADLESS_TASK_NAME, "initialize ReactContext");
      final Object reactHost = getReactHost(context);
      if (isBridgelessArchitectureEnabled()) { // NEW arch
        ReactInstanceEventListener callback =
            new ReactInstanceEventListener() {
              @Override
              public void onReactContextInitialized(@NonNull ReactContext reactContext) {
                mIsReactContextInitialized.set(true);
                drainTaskQueue(reactContext);
                try {
                  Method removeReactInstanceEventListener =
                      reactHost
                          .getClass()
                          .getMethod(
                              "removeReactInstanceEventListener", ReactInstanceEventListener.class);
                  removeReactInstanceEventListener.invoke(reactHost, this);
                } catch (Exception e) {
                  Log.e(HEADLESS_TASK_NAME, "reflection error A: " + e, e);
                }
              }
            };
        try {
          Method addReactInstanceEventListener =
              reactHost
                  .getClass()
                  .getMethod("addReactInstanceEventListener", ReactInstanceEventListener.class);
          addReactInstanceEventListener.invoke(reactHost, callback);
          Method startReactHost = reactHost.getClass().getMethod("start");
          startReactHost.invoke(reactHost);
        } catch (Exception e) {
          Log.e(HEADLESS_TASK_NAME, "reflection error ReactHost start: " + e.getMessage(), e);
        }
      } else { // OLD arch
        final ReactInstanceManager reactInstanceManager =
            getReactNativeHost(context).getReactInstanceManager();
        reactInstanceManager.addReactInstanceEventListener(
            new ReactInstanceEventListener() {
              @Override
              public void onReactContextInitialized(@NonNull ReactContext reactContext) {
                mIsReactContextInitialized.set(true);
                drainTaskQueue(reactContext);
                reactInstanceManager.removeReactInstanceEventListener(this);
              }
            });
        reactInstanceManager.createReactContextInBackground();
      }
    }
  }

  /**
   * Invokes HeadlessEvents queued while waiting for the ReactContext to initialize.
   *
   * @param reactContext
   */
  private void drainTaskQueue(final ReactContext reactContext) {
    if (mWillDrainTaskQueue.compareAndSet(false, true)) {
      new Handler(Looper.getMainLooper())
          .postDelayed(
              () -> {
                synchronized (mTaskQueue) {
                  for (TaskConfig taskConfig : mTaskQueue) {
                    invokeStartTask(reactContext, taskConfig);
                  }
                }
              },
              500);
    }
  }

  /**
   * Return true if this app is running with RN's bridgeless architecture. Cheers to @mikehardy for
   * this idea.
   *
   * @return
   */
  public static boolean isBridgelessArchitectureEnabled() {
    try {
      Class<?> entryPoint =
          Class.forName("com.facebook.react.defaults.DefaultNewArchitectureEntryPoint");
      Method bridgelessEnabled = entryPoint.getMethod("getBridgelessEnabled");
      Object result = bridgelessEnabled.invoke(null);
      return (result == Boolean.TRUE);
    } catch (Exception e) {
      return false;
    }
  }

  /** Wrapper for a client event. Inserts our custom taskId into the RN ClientEvent params. */
  public static class TaskConfig {
    private final String mTaskName;
    private final long mTaskTimeout;
    private final GenericCallback mCallback;
    private final int mTaskId;
    private int mReactTaskId;
    private final WritableMap mParams;

    public TaskConfig(
        String taskName,
        long taskTimeout,
        WritableMap params,
        @Nullable GenericCallback taskCompletionCallback) {
      mTaskName = taskName;
      mTaskTimeout = taskTimeout;
      mCallback = taskCompletionCallback;
      mTaskId = getNextTaskId();
      // Insert our custom taskId for users to call finishHeadlessTask(taskId) with.
      params.putInt("taskId", mTaskId);
      mParams = params;
    }

    public void setReactTaskId(int taskId) {
      mReactTaskId = taskId;
    }

    public int getTaskId() {
      return mTaskId;
    }

    public int getReactTaskId() {
      return mReactTaskId;
    }

    public @Nullable GenericCallback getCallback() {
      return mCallback;
    }

    public HeadlessJsTaskConfig getTaskConfig() {
      return new HeadlessJsTaskConfig(mTaskName, mParams, mTaskTimeout, true);
    }
  }

  public interface GenericCallback {
    void call();
  }
}
