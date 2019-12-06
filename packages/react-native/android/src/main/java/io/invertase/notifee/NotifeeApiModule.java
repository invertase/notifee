package io.invertase.notifee;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.Tasks;

import java.util.Objects;

import io.invertase.notifee.core.NotifeeNativeModule;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static io.invertase.notifee.NotifeeNotification.NOTIFICATION_INTENT_ACTION;

public class NotifeeApiModule extends NotifeeNativeModule implements ActivityEventListener {
  private static final String TAG = "NotifeeApiModule";
  private Bundle initialNotificationBundle;

  NotifeeApiModule(ReactApplicationContext reactContext) {
    super(reactContext, TAG);
    reactContext.addActivityEventListener(this);
    initialNotificationBundle = null;
  }

  @ReactMethod
  public void displayNotification(ReadableMap notificationRaw, Promise promise) {
    Tasks.call(getExecutor(), () -> {
      NotifeeNotification notification = NotifeeNotification.fromReadableMap(notificationRaw);

      if (notification.isForegroundServiceNotification()) {
        Tasks.await(notification.displayForegroundServiceNotification());
      } else {
        Tasks.await(notification.displayNotification());
      }

      return notification;
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

  // TODO these dont seem to work
  @ReactMethod
  public void cancelNotification(String notificationId, Promise promise) {
    NotificationManagerCompat notificationManagerCompat = NotifeeNotification.getNotificationManagerCompat(getApplicationContext());
    notificationManagerCompat.cancel(notificationId.hashCode());
    promise.resolve(null);
  }

  @ReactMethod
  public void cancelAllNotifications(Promise promise) {
    NotificationManagerCompat notificationManagerCompat = NotifeeNotification.getNotificationManagerCompat(getApplicationContext());
    notificationManagerCompat.cancelAll();
    promise.resolve(null);
  }

  @ReactMethod
  public void createChannel(ReadableMap channelMap, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannel(channelMap);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void createChannelGroup(ReadableMap channelGroupMap, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannelGroup(channelGroupMap);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void createChannelGroups(ReadableArray channelGroupsArray, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannelGroups(channelGroupsArray);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void createChannels(ReadableArray channelsArray, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.createChannels(channelsArray);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void deleteChannelGroup(String channelId, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.deleteChannelGroup(channelId);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void deleteChannel(String channelId, Promise promise) {
    Tasks.call(() -> {
      NotifeeNotificationChannel.deleteChannel(channelId);
      return null;
    }).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getInitialNotification(Promise promise) {
    // TODO handle intent
    promise.resolve(null);
  }

  @ReactMethod
  public void getChannel(String channelId, Promise promise) {
    Tasks.call(() -> NotifeeNotificationChannel.getChannel(channelId)).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannels(Promise promise) {
    Tasks.call(NotifeeNotificationChannel::getChannels).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannelGroup(String channelGroupId, Promise promise) {
    Tasks.call(() -> NotifeeNotificationChannel.getChannelGroup(channelGroupId)).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void getChannelGroups(Promise promise) {
    Tasks.call(NotifeeNotificationChannel::getChannelGroups).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        promise.resolve(task.getResult());
      } else {
        rejectPromiseWithExceptionMap(promise, task.getException());
      }
    });
  }

  @ReactMethod
  public void openNotificationSettings(String channelId, Promise promise) {
    Intent intent;

    if (Build.VERSION.SDK_INT >= 26) {
      if (channelId != null) {
        intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
      } else {
        intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
      }

      intent.putExtra(Settings.EXTRA_APP_PACKAGE, getApplicationContext().getPackageName());
    } else {
      intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
    }

    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

    if (getCurrentActivity() != null) {
      getCurrentActivity().runOnUiThread(() -> getApplicationContext().startActivity(intent));
    } else {
      Log.d(TAG, "Attempted to start activity but no current activity was available.");
    }

    promise.resolve(null);
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    // noop
  }

  @Override
  public void onNewIntent(Intent intent) {
    if (intent.getAction() != null && intent.getAction().equals(NOTIFICATION_INTENT_ACTION)) {
      // TODO intent handling
    }
  }
}
