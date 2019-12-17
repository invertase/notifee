package app.notifee.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.Map;

import app.notifee.core.bundles.NotificationBundle;
import app.notifee.core.utils.ObjectUtils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

@KeepForSdk
class Notifee {
  private static Notifee mNotifee = null;
  private Map<String, Object> mModuleBuildConfig;

  private Notifee(Map<String, Object> moduleBuildConfig) {
    mModuleBuildConfig = moduleBuildConfig;
  }

  @KeepForSdk
  public static
  Notifee getInstance() {
    return mNotifee;
  }

  @KeepForSdk
  public static @Nullable
  Context getContext() {
    return ContextHolder.getApplicationContext();
  }

  static void configure() {
    if (mNotifee != null) return;
    synchronized (Notifee.class) {
      Class moduleBuildConfigClass = ObjectUtils.getClassForName(
        ObjectUtils.MODULE_BUILD_CONFIG_CLASS
      );
      if (moduleBuildConfigClass == null) {
        Logger.e("configure", "default module build class was not found!");
        return;
      }

      Map<String, Object> moduleBuildConfig = ObjectUtils.getClassProperties(moduleBuildConfigClass);
      if (moduleBuildConfig == null) {
        Logger.e("configure", "failed to read module build configuration!");
        return;
      }

      Class eventSubscriberClass = ObjectUtils.getClassForName(
        ObjectUtils.MODULE_EVENT_SUBSCRIBER_CLASS
      );
      Object subscriberClassInstance = ObjectUtils.getClassInstanceFromDefaultConstructor(eventSubscriberClass);
      if (subscriberClassInstance != null) {
        Logger.d("configure", "module event subscriber registered with event bus");
        EventBus.register(subscriberClassInstance);
      } else {
        Logger.e("configure", "module event subscriber could not be found!");
      }

      mNotifee = new Notifee(moduleBuildConfig);
    }
  }

  // TODO
  @KeepForSdk
  public void displayNotification(Bundle notificationMap, MethodCallResult<Void> result) {
    NotificationBundle notificationBundle = NotificationBundle.fromBundle(notificationMap);
    NotificationManager.displayNotification(notificationBundle)
      .addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
          result.onComplete(null, task.getResult());
        } else {
          result.onComplete(task.getException(), null);
        }
      });
  }

  // TODO
  @KeepForSdk
  public void cancelNotification(String notificationId, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void cancelAllNotifications(MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void createChannel(Bundle channelBundle, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void createChannels(List<Bundle> channelsList, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void getChannel(String channelId, MethodCallResult<Bundle> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void getAllChannels(MethodCallResult<List<Bundle>> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void deleteChannel(String channelId, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void createChannelGroup(Bundle channelGroupBundle, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void createChannelGroups(List<Bundle> channelGroupsList, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void getChannelGroup(String channelGroupsId, MethodCallResult<Bundle> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void getAllChannelGroups(MethodCallResult<List<Bundle>> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void deleteChannelGroup(String channelGroupId, MethodCallResult<Void> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void getInitialNotification(MethodCallResult<Bundle> result) {
    result.onComplete(null, null);
  }

  // TODO
  @KeepForSdk
  public void openNotificationSettings(Activity activity, MethodCallResult<Void> result) {
    openNotificationSettings(null, activity, result);
  }

  @KeepForSdk
  public void openNotificationSettings(
    @Nullable String channelId,
    Activity activity,
    MethodCallResult<Void> result
  ) {
    if (getContext() == null || activity == null) {
      Logger.d("openNotificationSettings", "attempted to start activity but no current activity or context was available.");
      result.onComplete(null, null);
      return;
    }

    Intent intent;
    if (Build.VERSION.SDK_INT >= 26) {
      if (channelId != null) {
        intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
      } else {
        intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
      }
      intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
    } else {
      intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
    }
    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

    activity.runOnUiThread(() -> getContext().startActivity(intent));
    result.onComplete(null, null);
  }
}


