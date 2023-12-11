package app.notifee.core;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import app.notifee.core.event.InitialNotificationEvent;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.interfaces.EventListener;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.ChannelGroupModel;
import app.notifee.core.model.ChannelModel;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.utility.AlarmUtils;
import app.notifee.core.utility.PowerManagerUtils;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import java.util.ArrayList;
import java.util.List;

@KeepForSdk
public class Notifee {
  private static final String TAG = "API";
  private static Notifee mNotifee = null;
  private static boolean mIsnitialized = false;

  @KeepForSdk public static final int REQUEST_CODE_NOTIFICATION_PERMISSION = 11111;

  @KeepForSdk
  public static Notifee getInstance() {
    if (!mIsnitialized) {
      Logger.w(TAG, "getInstance() accessed before event listener is initialized");
      mNotifee = new Notifee();
    }

    return mNotifee;
  }

  @KeepForSdk
  public static @Nullable Context getContext() {
    return ContextHolder.getApplicationContext();
  }

  @KeepForSdk
  public static void initialize(@Nullable EventListener eventListener) {
    synchronized (Notifee.class) {
      if (mIsnitialized) {
        return;
      }

      if (mNotifee == null) {
        mNotifee = new Notifee();
      }

      if (eventListener != null) {
        EventSubscriber.register(eventListener);
      }

      mIsnitialized = true;
    }
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
   * @param result
   */
  @KeepForSdk
  public void cancelAllNotifications(int notificationType,
    MethodCallResult<Void> result) {
    Futures.addCallback(
      NotificationManager.cancelAllNotifications(notificationType),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void cancelAllNotificationsWithIds(
    int type, List<String> ids, String tag, MethodCallResult<Void> result) {
    Futures.addCallback(
      NotificationManager.cancelAllNotificationsWithIds(type, ids, tag),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void openAlarmPermissionSettings(Activity activity, MethodCallResult<Void> result) {
    AlarmUtils.openAlarmPermissionSettings(activity);
    result.onComplete(null, null);
  }

  @KeepForSdk
  public void createChannel(Bundle channelMap, MethodCallResult<Void> result) {
    ChannelModel channelModel = ChannelModel.fromBundle(channelMap);
    Futures.addCallback(ChannelManager.createChannel(channelModel),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void createChannels(List<Bundle> channelsList, MethodCallResult<Void> result) {
    ArrayList<ChannelModel> channelModels = new ArrayList<>(channelsList.size());
    for (Bundle bundle : channelsList) {
      channelModels.add(ChannelModel.fromBundle(bundle));
    }
    Futures.addCallback(ChannelManager.createChannels(channelModels),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void createChannelGroup(Bundle channelGroupMap, MethodCallResult<Void> result) {
    ChannelGroupModel channelGroupModel = ChannelGroupModel.fromBundle(channelGroupMap);
    Futures.addCallback(ChannelManager.createChannelGroup(channelGroupModel),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void createChannelGroups(List<Bundle> channelGroupsList, MethodCallResult<Void> result) {
    ArrayList<ChannelGroupModel> channelGroupModels = new ArrayList<>(channelGroupsList.size());
    for (Bundle bundle : channelGroupsList) {
      channelGroupModels.add(ChannelGroupModel.fromBundle(bundle));
    }
    Futures.addCallback(ChannelManager.createChannelGroups(channelGroupModels),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
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
  public void displayNotification(Bundle notificationMap,
    MethodCallResult<Void> result) {
    NotificationModel notificationModel = NotificationModel.fromBundle(
      notificationMap);
    Futures.addCallback(
      NotificationManager.displayNotification(notificationModel, null),
      new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          Exception e = new Exception(t);
          Logger.e(TAG, "displayNotification", e);
          result.onComplete(e, null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void createTriggerNotification(
      Bundle notificationMap, Bundle triggerMap, MethodCallResult<Void> result) {
    NotificationModel notificationModel = NotificationModel.fromBundle(notificationMap);
    Futures.addCallback(
      NotificationManager.createTriggerNotification(notificationModel,
        triggerMap), new FutureCallback<Void>() {
        @Override
        public void onSuccess(Void taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          Exception e = new Exception(t);
          Logger.e(TAG, "createTriggerNotification", e);
          result.onComplete(e, null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void getTriggerNotificationIds(MethodCallResult<List<String>> result) {
    NotificationManager.getTriggerNotificationIds(result);
  }

  @KeepForSdk
  public void getDisplayedNotifications(MethodCallResult<List<Bundle>> result) {
    Futures.addCallback(NotificationManager.getDisplayedNotifications(),
      new FutureCallback<List<Bundle>>() {
        @Override
        public void onSuccess(List<Bundle> taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void getTriggerNotifications(MethodCallResult<List<Bundle>> result) {
    NotificationManager.getTriggerNotifications(result);
  }

  @KeepForSdk
  public void getChannels(MethodCallResult<List<Bundle>> result) {
    Futures.addCallback(ChannelManager.getChannels(),
      new FutureCallback<List<Bundle>>() {
        @Override
        public void onSuccess(List<Bundle> taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void getChannel(String channelId, MethodCallResult<Bundle> result) {
    Futures.addCallback(ChannelManager.getChannel(channelId),
      new FutureCallback<Bundle>() {
        @Override
        public void onSuccess(Bundle taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void getChannelGroups(MethodCallResult<List<Bundle>> result) {
    Futures.addCallback(ChannelManager.getChannelGroups(),
      new FutureCallback<List<Bundle>>() {
        @Override
        public void onSuccess(List<Bundle> taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void getChannelGroup(String channelGroupId,
    MethodCallResult<Bundle> result) {
    Futures.addCallback(ChannelManager.getChannelGroup(channelGroupId),
      new FutureCallback<Bundle>() {
        @Override
        public void onSuccess(Bundle taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void isChannelCreated(String channelId,
    MethodCallResult<Boolean> result) {
    Futures.addCallback(ChannelManager.isChannelCreated(channelId),
      new FutureCallback<Boolean>() {
        @Override
        public void onSuccess(Boolean taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void isChannelBlocked(String channelId,
    MethodCallResult<Boolean> result) {
    Futures.addCallback(ChannelManager.isChannelBlocked(channelId),
      new FutureCallback<Boolean>() {
        @Override
        public void onSuccess(Boolean taskResult) {
          result.onComplete(null, taskResult);
        }

        @Override
        public void onFailure(Throwable t) {
          result.onComplete(new Exception(t), null);
        }
      }, ChannelManager.getListeningExecutorService());
  }

  @KeepForSdk
  public void getInitialNotification(Activity activity, MethodCallResult<Bundle> result) {
    InitialNotificationEvent event = EventBus.removeStickEvent(InitialNotificationEvent.class);
    Bundle initialNotificationBundle = new Bundle();

    if (event != null) {
      initialNotificationBundle.putAll(event.getExtras());
      initialNotificationBundle.putBundle("notification", event.getNotificationModel().toBundle());
      result.onComplete(null, initialNotificationBundle);
      return;
    } else if (activity != null) {
      try {
        // get intent from current activity
        Intent intent = activity.getIntent();
        if (intent != null && intent.getExtras() != null && intent.hasExtra("notification")) {
          initialNotificationBundle.putBundle(
              "notification", intent.getBundleExtra("notification"));
          result.onComplete(null, initialNotificationBundle);
          return;
        }
      } catch (Exception e) {
        Logger.e(TAG, "getInitialNotification", e);
      }
    }

    // If no initial notification, return
    result.onComplete(null, null);
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
  }

  @KeepForSdk
  public void getNotificationSettings(MethodCallResult<Bundle> result) {
    boolean areNotificationsEnabled =
        NotificationManagerCompat.from(ContextHolder.getApplicationContext())
            .areNotificationsEnabled();

    Bundle notificationSettingsBundle = new Bundle();
    if (areNotificationsEnabled) {
      notificationSettingsBundle.putInt("authorizationStatus", 1);
    } else {
      notificationSettingsBundle.putInt("authorizationStatus", 0);
    }

    boolean canScheduleExactAlarms = AlarmUtils.canScheduleExactAlarms();
    Bundle androidSettingsBundle = new Bundle();

    if (canScheduleExactAlarms) {
      androidSettingsBundle.putInt("alarm", 1);
    } else {
      androidSettingsBundle.putInt("alarm", 0);
    }

    notificationSettingsBundle.putBundle("android", androidSettingsBundle);
    result.onComplete(null, notificationSettingsBundle);
  }

  @Nullable private MethodCallResult<Bundle> requestPermissionCallResult;

  @KeepForSdk
  public void setRequestPermissionCallback(MethodCallResult<Bundle> result) {
    requestPermissionCallResult = result;
  }

  @KeepForSdk
  public boolean onRequestPermissionsResult(
      int requestCode, String[] permissions, int[] grantResults) {
    if (requestCode == REQUEST_CODE_NOTIFICATION_PERMISSION) {
      if (requestPermissionCallResult != null) {
        getNotificationSettings(requestPermissionCallResult);
        return true;
      }
    }
    return false;
  }

  @KeepForSdk
  public void openNotificationSettings(
      @Nullable String channelId, Activity activity, MethodCallResult<Void> result) {
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

  @KeepForSdk
  public void stopForegroundService(MethodCallResult<Void> result) {
    ForegroundService.stop();
    result.onComplete(null, null);
  }
}
