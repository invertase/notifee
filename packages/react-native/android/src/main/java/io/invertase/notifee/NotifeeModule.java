package io.invertase.notifee;

import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.gms.tasks.Tasks;

import java.util.Collections;
import java.util.Objects;

import io.invertase.notifee.core.NotifeeEventEmitter;
import io.invertase.notifee.core.NotifeeNativeModule;

public class NotifeeModule extends NotifeeNativeModule {
  private static final String TAG = "NotifeeModule";

  NotifeeModule(ReactApplicationContext reactContext) {
    super(reactContext, TAG);
    NotifeeEventEmitter.getSharedInstance().attachReactContext(reactContext);
  }

  @ReactMethod
  public void displayNotification(ReadableMap notificationRaw, Promise promise) {
    Tasks.call(getExecutor(), () -> {
      NotifeeNotification notifeeNotification = NotifeeNotification.fromReadableMap(
        notificationRaw);
      notifeeNotification.displayNotification();
      return notifeeNotification;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(Objects.requireNonNull(task.getResult()).toWritableMap());
      } else {
        Exception exception = task.getException();
        Log.e(TAG, "Error displaying a notification", exception);
        if (exception instanceof InvalidNotificationParameterException) {
          InvalidNotificationParameterException notificationParameterException = (InvalidNotificationParameterException) exception;
          rejectPromiseWithCodeAndMessage(
            promise,
            notificationParameterException.getCode(),
            notificationParameterException.getMessage(),
            notificationParameterException
          );
        } else {
          rejectPromiseWithExceptionMap(promise, exception);
        }
      }
    });
  }

  @ReactMethod
  public void createChannel(ReadableMap channelMap, Promise promise) {
    try {
      NotifeeNotificationChannel.createChannel(channelMap);
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void createChannelGroup(ReadableMap channelGroupMap, Promise promise) {
    try {
      NotifeeNotificationChannel.createChannelGroup(channelGroupMap);
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void createChannelGroups(ReadableArray channelGroupsArray, Promise promise) {
    try {
      NotifeeNotificationChannel.createChannelGroups(channelGroupsArray);
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void createChannels(ReadableArray channelsArray, Promise promise) {
    try {
      NotifeeNotificationChannel.createChannels(channelsArray);
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void deleteChannelGroup(String channelId, Promise promise) {
    try {
      NotifeeNotificationChannel.deleteChannelGroup(channelId);
      promise.resolve(null);
    } catch (NullPointerException e) {
      promise.reject(
        "notifications/channel-group-not-found",
        "The requested NotificationChannelGroup does not exist, have you created it?"
      );
    }
  }

  @ReactMethod
  public void deleteChannel(String channelId, Promise promise) {
    try {
      NotifeeNotificationChannel.deleteChannel(channelId);
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void getChannel(String channelId, Promise promise) {
    try {
      promise.resolve(NotifeeNotificationChannel.getChannel(channelId));
      return;
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void getChannels(Promise promise) {
    try {
      promise.resolve(NotifeeNotificationChannel.getChannels());
      return;
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(Collections.emptyList());
  }

  @ReactMethod
  public void getChannelGroup(String channelGroupId, Promise promise) {
    try {
      promise.resolve(NotifeeNotificationChannel.getChannelGroup(channelGroupId));
      return;
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(null);
  }

  @ReactMethod
  public void getChannelGroups(Promise promise) {
    try {
      promise.resolve(NotifeeNotificationChannel.getChannelGroups());
      return;
    } catch (Throwable t) {
      // do nothing - most likely a NoSuchMethodError for < v4 support lib
    }
    promise.resolve(Collections.emptyList());
  }
}
