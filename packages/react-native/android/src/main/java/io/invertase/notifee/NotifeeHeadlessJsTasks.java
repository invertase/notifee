package io.invertase.notifee;

import android.content.Context;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class NotifeeHeadlessJsTasks implements HeadlessJsTaskEventListener {

  private static final WeakHashMap<Context, NotifeeHeadlessJsTasks> INSTANCES = new WeakHashMap<>();
  private final WeakReference<ReactContext> reactContext;
  private final Map<Integer, NotifeeTaskListener> notifeeTaskListeners = new ConcurrentHashMap<>();

  private NotifeeHeadlessJsTasks(Context context) {
    reactContext = new WeakReference<>(getReactContextForContext(context));
  }

  static ReactContext getReactContextForContext(Context context) {
    ReactNativeHost reactNativeHost = ((ReactApplication) context).getReactNativeHost();
    ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
    return reactInstanceManager.getCurrentReactContext();
  }

  static NotifeeHeadlessJsTasks getInstance(Context context) {
    NotifeeHeadlessJsTasks tasks = INSTANCES.get(context);

    if (tasks == null) {
      tasks = new NotifeeHeadlessJsTasks(context);
      HeadlessJsTaskContext.getInstance(getReactContextForContext(context)).addTaskEventListener(tasks);
      INSTANCES.put(context, tasks);
    }

    return tasks;
  }

  void addListenerForTaskId(int taskId, NotifeeTaskListener listener) {
    notifeeTaskListeners.put(taskId, listener);
  }

  @Override
  public void onHeadlessJsTaskStart(int taskId) {
    // noop
  }

  @Override
  public void onHeadlessJsTaskFinish(int taskId) {
    NotifeeTaskListener listener = notifeeTaskListeners.get(taskId);

    if (listener != null) {
      listener.onTaskFinished();
      notifeeTaskListeners.remove(taskId);
    }
  }

  public ReactContext getReactContext() {
    return reactContext.get();
  }

  public interface NotifeeTaskListener {
    void onTaskFinished();
  }
}
