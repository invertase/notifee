package io.invertase.notifee;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import app.notifee.core.EventSubscriber;

class NotifeeReactUtils {
  static ReactContext getReactContextForContext(Context context) {
    ReactNativeHost reactNativeHost = ((ReactApplication) context).getReactNativeHost();
    ReactInstanceManager reactInstanceManager = reactNativeHost.getReactInstanceManager();
    return reactInstanceManager.getCurrentReactContext();
  }

  static void sendEvent(String eventName, @Nullable WritableMap eventMap) {
    try {
      ReactContext reactContext = NotifeeReactUtils
        .getReactContextForContext(EventSubscriber.getContext());
      if (reactContext == null || !reactContext.hasActiveCatalystInstance()) {
        return;
      }

      reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit(eventName, eventMap);
    } catch (Exception e) {
      Log.e("SEND_EVENT", "", e);
    }
  }
}
