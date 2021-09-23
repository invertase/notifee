/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import androidx.annotation.Nullable;
import app.notifee.core.EventSubscriber;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.jstasks.HeadlessJsTaskContext;
import com.facebook.react.jstasks.HeadlessJsTaskEventListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.lang.reflect.Method;
import java.util.List;

class NotifeeReactUtils {
  private static final SparseArray<GenericCallback> headlessTasks = new SparseArray<>();
  private static final HeadlessJsTaskEventListener headlessTasksListener =
      new HeadlessJsTaskEventListener() {
        @Override
        public void onHeadlessJsTaskStart(int taskId) {}

        @Override
        public void onHeadlessJsTaskFinish(int taskId) {
          synchronized (headlessTasks) {
            GenericCallback callback = headlessTasks.get(taskId);
            if (callback != null) {
              headlessTasks.remove(taskId);
              callback.call();
            }
          }
        }
      };

  static void promiseResolver(Promise promise, Exception e, Bundle bundle) {
    if (e != null) {
      // TODO custom error class with message/code
      promise.reject(e);
    } else if (bundle != null) {
      promise.resolve(Arguments.fromBundle(bundle));
    } else {
      promise.resolve(null);
    }
  }

  static void promiseResolver(Promise promise, Exception e, List<Bundle> bundleList) {
    if (e != null) {
      // TODO custom error class with message/code
      promise.reject(e);
    } else {
      WritableArray writableArray = Arguments.createArray();
      for (Bundle bundle : bundleList) {
        writableArray.pushMap(Arguments.fromBundle(bundle));
      }
      promise.resolve(writableArray);
    }
  }

  static void promiseBooleanResolver(Promise promise, Exception e, Boolean bool) {
    if (e != null) {
      // TODO custom error class with message/code
      promise.reject(e);
    } else {
      promise.resolve(bool);
    }
  }

  static void promiseStringListResolver(Promise promise, Exception e, List<String> stringList) {
    if (e != null) {
      // TODO custom error class with message/code
      promise.reject(e);
    } else {
      WritableArray writableArray = Arguments.createArray();
      for (String string : stringList) {
        writableArray.pushString(string);
      }
      promise.resolve(writableArray);
    }
  }

  static void promiseResolver(Promise promise, Exception e) {
    if (e != null) {
      // TODO custom error class with message/code
      promise.reject(e);
    } else {
      promise.resolve(null);
    }
  }

  private static @Nullable ReactContext getReactContext() {
    ReactNativeHost reactNativeHost =
        ((ReactApplication) EventSubscriber.getContext()).getReactNativeHost();
    ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
    return reactInstanceManager.getCurrentReactContext();
  }

  private static void initializeReactContext(GenericCallback callback) {
    ReactNativeHost reactNativeHost =
        ((ReactApplication) EventSubscriber.getContext()).getReactNativeHost();

    ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();

    reactInstanceManager.addReactInstanceEventListener(
        new ReactInstanceManager.ReactInstanceEventListener() {
          @Override
          public void onReactContextInitialized(final ReactContext reactContext) {
            reactInstanceManager.removeReactInstanceEventListener(this);
            new Handler(Looper.getMainLooper()).postDelayed(callback::call, 100);
          }
        });

    if (!reactInstanceManager.hasStartedCreatingInitialContext()) {
      reactInstanceManager.createReactContextInBackground();
    }
  }

  static void clearRunningHeadlessTasks() {
    for (int i = 0; i < headlessTasks.size(); i++) {
      GenericCallback callback = headlessTasks.valueAt(i);
      callback.call();
      headlessTasks.remove(i);
    }
  }

  static void startHeadlessTask(
      String taskName,
      WritableMap taskData,
      long taskTimeout,
      @Nullable GenericCallback taskCompletionCallback) {
    GenericCallback callback =
        () -> {
          HeadlessJsTaskContext taskContext = HeadlessJsTaskContext.getInstance(getReactContext());
          HeadlessJsTaskConfig taskConfig =
              new HeadlessJsTaskConfig(taskName, taskData, taskTimeout, true);

          synchronized (headlessTasks) {
            if (headlessTasks.size() == 0) {
              taskContext.addTaskEventListener(headlessTasksListener);
            }
          }

          headlessTasks.put(
              taskContext.startTask(taskConfig),
              () -> {
                synchronized (headlessTasks) {
                  if (headlessTasks.size() == 0) {
                    taskContext.removeTaskEventListener(headlessTasksListener);
                  }
                }
                if (taskCompletionCallback != null) {
                  taskCompletionCallback.call();
                }
              });
        };

    if (getReactContext() == null) {
      initializeReactContext(callback);
    } else {
      callback.call();
    }
  }

  static void sendEvent(String eventName, WritableMap eventMap) {
    try {
      ReactContext reactContext = getReactContext();

      if (reactContext == null || !reactContext.hasActiveCatalystInstance()) {
        return;
      }

      reactContext
          .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
          .emit(eventName, eventMap);

    } catch (Exception e) {
      Log.e("SEND_EVENT", "", e);
    }
  }

  static boolean isAppInForeground() {
    Context context = EventSubscriber.getContext();

    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    if (activityManager == null) return false;

    List<ActivityManager.RunningAppProcessInfo> appProcesses =
        activityManager.getRunningAppProcesses();
    if (appProcesses == null) return false;

    final String packageName = context.getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
          && appProcess.processName.equals(packageName)) {
        ReactContext reactContext;

        try {
          reactContext = (ReactContext) context;
        } catch (ClassCastException exception) {
          return true;
        }

        return reactContext.getLifecycleState() == LifecycleState.RESUMED;
      }
    }

    return false;
  }

  static void hideNotificationDrawer() {
    Context context = EventSubscriber.getContext();

    try {
      @SuppressLint("WrongConstant")
      Object service = context.getSystemService("statusbar");
      Class<?> statusbarManager = Class.forName("android.app.StatusBarManager");
      Method collapse =
          statusbarManager.getMethod((Build.VERSION.SDK_INT >= 17) ? "collapsePanels" : "collapse");
      collapse.setAccessible(true);
      collapse.invoke(service);
    } catch (Exception e) {
      Log.e("HIDE_NOTIF_DRAWER", "", e);
    }
  }

  interface GenericCallback {
    void call();
  }
}
