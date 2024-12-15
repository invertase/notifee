/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee;

import static io.invertase.notifee.HeadlessTask.getReactContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ProcessLifecycleOwner;
import app.notifee.core.EventSubscriber;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.lang.reflect.Method;
import java.util.List;

class NotifeeReactUtils {
  public static final HeadlessTask headlessTaskManager = new HeadlessTask();

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

  static void startHeadlessTask(
      String taskName,
      WritableMap taskData,
      long taskTimeout,
      @Nullable HeadlessTask.GenericCallback taskCompletionCallback) {

    HeadlessTask.TaskConfig config =
        new HeadlessTask.TaskConfig(taskName, taskTimeout, taskData, taskCompletionCallback);
    headlessTaskManager.startTask(EventSubscriber.getContext(), config);
  }

  static void sendEvent(String eventName, WritableMap eventMap) {
    try {
      ReactContext reactContext = getReactContext(EventSubscriber.getContext());

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
    return ProcessLifecycleOwner.get()
        .getLifecycle()
        .getCurrentState()
        .isAtLeast(Lifecycle.State.RESUMED);
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
}
