package io.invertase.notifee;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

import java.util.HashMap;
import java.util.Map;

import app.notifee.core.Notifee;

public class NotifeeApiModule extends ReactContextBaseJavaModule {
  public NotifeeApiModule(@NonNull ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @ReactMethod
  public void cancelNotification(String notificationId, Promise promise) {
    Notifee
      .getInstance()
      .cancelNotification(notificationId, (e, aVoid) -> {
        if (e != null) {
          // TODO custom error class with message/code
          promise.reject(e);
        } else {
          promise.resolve(aVoid);
        }
      });
  }

  @ReactMethod
  public void cancelAllNotifications(Promise promise) {
    Notifee
      .getInstance()
      .cancelAllNotifications((e, aVoid) -> {
        if (e != null) {
          // TODO custom error class with message/code
          promise.reject(e);
        } else {
          promise.resolve(aVoid);
        }
      });
  }

  @ReactMethod
  public void createChannel(ReadableMap channelMap, Promise promise) {
    Notifee
      .getInstance()
      .createChannel(Arguments.toBundle(channelMap), (e, aVoid) -> {
        if (e != null) {
          // TODO custom error class with message/code
          promise.reject(e);
        } else {
          promise.resolve(aVoid);
        }
      });
  }

  @ReactMethod
  public void createChannelGroup(ReadableMap channelGroupMap, Promise promise) {
    Notifee
      .getInstance()
      .createChannelGroup(Arguments.toBundle(channelGroupMap), (e, aVoid) -> {
        if (e != null) {
          // TODO custom error class with message/code
          promise.reject(e);
        } else {
          promise.resolve(aVoid);
        }
      });
  }

  @ReactMethod
  public void createChannelGroups(ReadableArray channelGroupsArray, Promise promise) {
    // TODO
  }

  @ReactMethod
  public void createChannels(ReadableArray channelsArray, Promise promise) {
    // TODO
  }

  @ReactMethod
  public void displayNotification(ReadableMap notificationMap, Promise promise) {
    Notifee
      .getInstance()
      .displayNotification(Arguments.toBundle(notificationMap), (e, aVoid) -> {
        if (e != null) {
          // TODO custom error class with message/code
          promise.reject(e);
        } else {
          promise.resolve(aVoid);
        }
      });
  }

  @ReactMethod
  public void deleteChannelGroup(String channelId, Promise promise) {
    // TODO
  }

  @ReactMethod
  public void deleteChannel(String channelId, Promise promise) {
    // TODO
  }

  @ReactMethod
  public void getInitialNotification(Promise promise) {
    // TODO
  }

  @ReactMethod
  public void getChannel(String channelId, Promise promise) {
    // TODO
  }

  @ReactMethod
  public void getAllChannels(Promise promise) {
    // TODO
  }

  @ReactMethod
  public void getChannelGroup(String channelGroupId, Promise promise) {
    // TODO
  }

  @ReactMethod
  public void getAllChannelGroups(Promise promise) {
    // TODO
  }

  @ReactMethod
  public void openNotificationSettings(String channelId, Promise promise) {
    Notifee.getInstance().openNotificationSettings(channelId, getCurrentActivity(), (e, aVoid) -> {
      promise.resolve(aVoid);
    });
  }

  @NonNull
  @Override
  public String getName() {
    return NotifeeApiModule.class.getName();
  }

  @Override
  public Map<String, Object> getConstants() {
    Map<String, Object> constants = new HashMap<>();
//    constants.put("NOTIFEE_RAW_JSON", NotifeeJSON.getSharedInstance().getRawJSON());
//    constants.put("ANDROID_API_LEVEL", android.os.Build.VERSION.SDK_INT);
//    constants.put("NOTIFEE_RECEIVER_SERVICE_TASK_KEY", RECEIVER_SERVICE_TASK_KEY);
//    constants.put("NOTIFEE_RECEIVER_SERVICE_EVENT_KEY", RECEIVER_SERVICE_EVENT_KEY);
//    constants.put("NOTIFEE_FOREGROUND_SERVICE", FOREGROUND_SERVICE_TASK_KEY);
    return constants;
  }
}
