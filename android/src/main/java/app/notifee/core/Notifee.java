package app.notifee.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import app.notifee.core.bundles.ChannelBundle;
import app.notifee.core.bundles.ChannelGroupBundle;
import app.notifee.core.bundles.NotificationBundle;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

@KeepForSdk
public class Notifee {
  private static Notifee mNotifee = null;

  @KeepForSdk
  public static Notifee getInstance() {
    return mNotifee;
  }

  @KeepForSdk
  public static @Nullable
  Context getContext() {
    return ContextHolder.getApplicationContext();
  }

  @KeepForSdk
  public static void configure(
    @NonNull String jsonConfigString, @NonNull EventListener defaultListener
  ) {
    synchronized (Notifee.class) {
      if (mNotifee == null) initialize();
      JSONConfig.initialize(jsonConfigString);
      EventSubscriber.register(defaultListener);
    }
  }

  static void initialize() {
    synchronized (Notifee.class) {
      if (mNotifee != null) return;
      mNotifee = new Notifee();
      LicenseChecker.initialize();
    }
  }

  @KeepForSdk
  public void cancelNotification(String notificationId, MethodCallResult<Void> result) {
    NotificationManager.cancelNotification(notificationId).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void cancelAllNotifications(MethodCallResult<Void> result) {
    NotificationManager.cancelAllNotifications().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void createChannel(Bundle channelMap, MethodCallResult<Void> result) {
    ChannelBundle channelBundle = ChannelBundle.fromBundle(channelMap);
    ChannelManager.createChannel(channelBundle).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void createChannels(List<Bundle> channelsList, MethodCallResult<Void> result) {
    ArrayList<ChannelBundle> channelBundles = new ArrayList<>(channelsList.size());
    for (Bundle bundle : channelsList) {
      channelBundles.add(ChannelBundle.fromBundle(bundle));
    }

    ChannelManager.createChannels(channelBundles).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void createChannelGroup(Bundle channelGroupMap, MethodCallResult<Void> result) {
    ChannelGroupBundle channelGroupBundle = ChannelGroupBundle.fromBundle(channelGroupMap);
    ChannelManager.createChannelGroup(channelGroupBundle).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void createChannelGroups(List<Bundle> channelGroupsList, MethodCallResult<Void> result) {
    ArrayList<ChannelGroupBundle> channelGroupBundles = new ArrayList<>(channelGroupsList.size());
    for (Bundle bundle : channelGroupsList) {
      channelGroupBundles.add(ChannelGroupBundle.fromBundle(bundle));
    }

    ChannelManager.createChannelGroups(channelGroupBundles).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void deleteChannel(String channelId, MethodCallResult<Void> result) {
    ChannelManager.deleteChannel(channelId);
    result.onComplete(null, null);
  }

  @KeepForSdk
  public void deleteChannelGroup(String channelGroupId, MethodCallResult<Void> result) {
    ChannelManager.deleteChannelGroup(channelGroupId);
    result.onComplete(null, null);
  }

  @KeepForSdk
  public void displayNotification(Bundle notificationMap, MethodCallResult<Void> result) {
    NotificationBundle notificationBundle = NotificationBundle.fromBundle(notificationMap);
    NotificationManager.displayNotification(notificationBundle).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void getAllChannels(MethodCallResult<List<Bundle>> result) {
    ChannelManager.getAllChannels().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void getChannel(String channelId, MethodCallResult<Bundle> result) {
    ChannelManager.getChannel(channelId).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void getAllChannelGroups(MethodCallResult<List<Bundle>> result) {
    ChannelManager.getAllChannelGroups().addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  @KeepForSdk
  public void getChannelGroup(String channelGroupsId, MethodCallResult<Bundle> result) {
    ChannelManager.getChannelGroup(channelGroupsId).addOnCompleteListener(task -> {
      if (task.isSuccessful()) {
        result.onComplete(null, task.getResult());
      } else {
        result.onComplete(task.getException(), null);
      }
    });
  }

  // TODO
  @KeepForSdk
  public void getInitialNotification(MethodCallResult<Bundle> result) {
    result.onComplete(null, null);
  }

  @KeepForSdk
  public void openNotificationSettings(Activity activity, MethodCallResult<Void> result) {
    openNotificationSettings(null, activity, result);
  }

  @KeepForSdk
  public void openNotificationSettings(
    @Nullable String channelId, Activity activity, MethodCallResult<Void> result
  ) {
    if (getContext() == null || activity == null) {
      Logger.d(
        "openNotificationSettings",
        "attempted to start activity but no current activity or context was available."
      );
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


