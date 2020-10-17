package app.notifee.core;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static app.notifee.core.LicenseManager.logLicenseWarningForMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import app.notifee.core.event.InitialNotificationEvent;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.ChannelGroupModel;
import app.notifee.core.model.ChannelModel;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.utility.PowerManagerUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@KeepForSdk
public class Notifee {
  private static final String TAG = "API";
  private static Notifee mNotifee = null;
  private static NotifeeConfig mNotifeeConfig = null;

  @KeepForSdk
  public static Notifee getInstance() {
    return mNotifee;
  }

  @KeepForSdk
  public static @Nullable Context getContext() {
    return ContextHolder.getApplicationContext();
  }

  @KeepForSdk
  public static void configure(@NonNull NotifeeConfig notifeeConfig) {
    synchronized (Notifee.class) {
      if (mNotifee == null) {
        initialize(notifeeConfig);
      }
    }
  }

  private static void initialize(NotifeeConfig notifeeConfig) {
    synchronized (Notifee.class) {
      if (mNotifee != null) return;
      mNotifee = new Notifee();
      mNotifeeConfig = notifeeConfig;
      EventSubscriber.register(notifeeConfig.getEventSubscriber());
      LicenseManager.initialize();
    }
  }

  static NotifeeConfig getNotifeeConfig() {
    return mNotifeeConfig;
  }

  @KeepForSdk
  public @NonNull String getMainComponent(@NonNull String defaultComponent) {
    MainComponentEvent event = EventBus.removeStickEvent(MainComponentEvent.class);

    if (event == null) {
      return defaultComponent;
    }

    return event.getMainComponent();
  }

  /**
   * NOTE: Allow cancelling notifications even if the license is invalid.
   *
   * @param notificationId
   * @param result
   */
  @KeepForSdk
  public void cancelNotification(
      String notificationId, int notificationType, MethodCallResult<Void> result) {
    NotificationManager.cancelNotification(notificationId, notificationType)
        .addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) {
                result.onComplete(null, task.getResult());
              } else {
                result.onComplete(task.getException(), null);
              }
            });
  }

  /**
   * NOTE: Allow cancelling notifications even if the license is invalid.
   *
   * @param result
   */
  @KeepForSdk
  public void cancelAllNotifications(int notificationType, MethodCallResult<Void> result) {
    NotificationManager.cancelAllNotifications(notificationType)
        .addOnCompleteListener(
            task -> {
              if (task.isSuccessful()) {
                result.onComplete(null, task.getResult());
              } else {
                result.onComplete(task.getException(), null);
              }
            });
  }

  @KeepForSdk
  public void createChannel(Bundle channelMap, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("createChannel");
      result.onComplete(null, null);
    } else {
      ChannelModel channelModel = ChannelModel.fromBundle(channelMap);
      ChannelManager.createChannel(channelModel)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void createChannels(List<Bundle> channelsList, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("createChannels");
      result.onComplete(null, null);
    } else {
      ArrayList<ChannelModel> channelModels = new ArrayList<>(channelsList.size());
      for (Bundle bundle : channelsList) {
        channelModels.add(ChannelModel.fromBundle(bundle));
      }

      ChannelManager.createChannels(channelModels)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void createChannelGroup(Bundle channelGroupMap, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("createChannelGroup");
      result.onComplete(null, null);
    } else {
      ChannelGroupModel channelGroupModel = ChannelGroupModel.fromBundle(channelGroupMap);
      ChannelManager.createChannelGroup(channelGroupModel)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void createChannelGroups(List<Bundle> channelGroupsList, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("createChannelGroups");
      result.onComplete(null, null);
    } else {
      ArrayList<ChannelGroupModel> channelGroupModels = new ArrayList<>(channelGroupsList.size());
      for (Bundle bundle : channelGroupsList) {
        channelGroupModels.add(ChannelGroupModel.fromBundle(bundle));
      }

      ChannelManager.createChannelGroups(channelGroupModels)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void deleteChannel(String channelId, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("deleteChannel");
      result.onComplete(null, null);
    } else {
      ChannelManager.deleteChannel(channelId);
      result.onComplete(null, null);
    }
  }

  @KeepForSdk
  public void deleteChannelGroup(String channelGroupId, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("deleteChannelGroup");
      result.onComplete(null, null);
    } else {
      ChannelManager.deleteChannelGroup(channelGroupId);
      result.onComplete(null, null);
    }
  }

  @KeepForSdk
  public void displayNotification(Bundle notificationMap, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("displayNotification");
      result.onComplete(null, null);
    } else {
      NotificationModel notificationModel = NotificationModel.fromBundle(notificationMap);
      NotificationManager.displayNotification(notificationModel)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, null);
                } else {
                  Logger.e(TAG, "displayNotification", task.getException());
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void createTriggerNotification(
      Bundle notificationMap, Bundle triggerMap, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("createTriggerNotification");
      result.onComplete(null, null);
    } else {
      NotificationModel notificationModel = NotificationModel.fromBundle(notificationMap);
      NotificationManager.createTriggerNotification(notificationModel, triggerMap)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, null);
                } else {
                  Logger.e(TAG, "createTriggerNotification", task.getException());
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void getTriggerNotificationIds(MethodCallResult<List<String>> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("getTriggerNotificationIds");
      result.onComplete(null, Collections.emptyList());
    } else {
      NotificationManager.getTriggerNotificationIds()
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void getChannels(MethodCallResult<List<Bundle>> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("getChannels");
      result.onComplete(null, Collections.emptyList());
    } else {
      ChannelManager.getChannels()
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void getChannel(String channelId, MethodCallResult<Bundle> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("getChannel");
      result.onComplete(null, null);
    } else {
      ChannelManager.getChannel(channelId)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void getChannelGroups(MethodCallResult<List<Bundle>> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("getChannelGroups");
      result.onComplete(null, Collections.emptyList());
    } else {
      ChannelManager.getChannelGroups()
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void getChannelGroup(String channelGroupId, MethodCallResult<Bundle> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("getChannelGroup");
      result.onComplete(null, null);
    } else {
      ChannelManager.getChannelGroup(channelGroupId)
          .addOnCompleteListener(
              task -> {
                if (task.isSuccessful()) {
                  result.onComplete(null, task.getResult());
                } else {
                  result.onComplete(task.getException(), null);
                }
              });
    }
  }

  @KeepForSdk
  public void getInitialNotification(MethodCallResult<Bundle> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("getInitialNotification");
      result.onComplete(null, null);
    } else {
      InitialNotificationEvent event = EventBus.removeStickEvent(InitialNotificationEvent.class);
      if (event == null) {
        result.onComplete(null, null);
      } else {
        Bundle initialNotificationBundle = new Bundle();
        initialNotificationBundle.putAll(event.getExtras());
        initialNotificationBundle.putBundle(
            "notification", event.getNotificationModel().toBundle());
        result.onComplete(null, initialNotificationBundle);
      }
    }
  }

  @KeepForSdk
  public void isBatteryOptimizationEnabled(MethodCallResult<Boolean> result) {
    Boolean isBatteryOptimizationEnabled =
        PowerManagerUtils.isBatteryOptimizationEnabled(ContextHolder.getApplicationContext());
    result.onComplete(null, isBatteryOptimizationEnabled);
  }

  @KeepForSdk
  public void openBatteryOptimizationSettings(Activity activity, MethodCallResult<Void> result) {
    PowerManagerUtils.openBatteryOptimizationSettings(activity);
    result.onComplete(null, null);
  }

  @KeepForSdk
  public void getPowerManagerInfo(MethodCallResult<Bundle> result) {
    PowerManagerUtils.PowerManagerInfo info = PowerManagerUtils.getPowerManagerInfo();
    result.onComplete(null, info.toBundle());
  }

  @KeepForSdk
  public void openPowerManagerSettings(Activity activity, MethodCallResult<Void> result) {
    PowerManagerUtils.openPowerManagerSettings(activity);
    result.onComplete(null, null);
    return;
  }

  @KeepForSdk
  public void openNotificationSettings(
      @Nullable String channelId, Activity activity, MethodCallResult<Void> result) {
    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForMethod("openNotificationSettings");
      result.onComplete(null, null);
    } else {
      if (getContext() == null || activity == null) {
        Logger.d(
            "openNotificationSettings",
            "attempted to start activity but no current activity or context was available.");
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
}
