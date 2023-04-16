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

import static app.notifee.core.ContextHolder.getApplicationContext;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.AlarmManagerCompat;
import app.notifee.core.database.WorkDataEntity;
import app.notifee.core.database.WorkDataRepository;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.model.TimestampTriggerModel;
import app.notifee.core.utility.AlarmUtils;
import app.notifee.core.utility.ObjectUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NotifeeAlarmManager {
  private static final String TAG = "NotifeeAlarmManager";
  private static final String NOTIFICATION_ID_INTENT_KEY = "notificationId";
  private static final ExecutorService alarmManagerExecutor = Executors.newCachedThreadPool();

  static void displayScheduledNotification(Bundle alarmManagerNotification) {
    if (alarmManagerNotification == null) {
      return;
    }
    String id = alarmManagerNotification.getString(NOTIFICATION_ID_INTENT_KEY);

    if (id == null) {
      return;
    }

    WorkDataRepository workDataRepository = new WorkDataRepository(getApplicationContext());

    Continuation<WorkDataEntity, Task<Void>> workContinuation =
        task -> {
          WorkDataEntity workDataEntity = task.getResult();

          Bundle notificationBundle;

          Bundle triggerBundle;

          if (workDataEntity == null
              || workDataEntity.getNotification() == null
              || workDataEntity.getTrigger() == null) {
            // check if notification bundle is stored with Work Manager
            Logger.w(
                TAG, "Attempted to handle doScheduledWork but no notification data was found.");
            return null;
          } else {
            triggerBundle = ObjectUtils.bytesToBundle(workDataEntity.getTrigger());
            notificationBundle = ObjectUtils.bytesToBundle(workDataEntity.getNotification());
          }

          NotificationModel notificationModel = NotificationModel.fromBundle(notificationBundle);

          return NotificationManager.displayNotification(notificationModel, triggerBundle)
              .addOnCompleteListener(
                  displayNotificationTask -> {
                    if (!displayNotificationTask.isSuccessful()) {
                      Logger.e(
                          TAG,
                          "Failed to display notification",
                          displayNotificationTask.getException());
                    } else {
                      if (triggerBundle.containsKey("repeatFrequency")
                          && ObjectUtils.getInt(triggerBundle.get("repeatFrequency")) != -1) {
                        TimestampTriggerModel trigger =
                            TimestampTriggerModel.fromBundle(triggerBundle);
                        // Ensure trigger is in the future and the latest timestamp is updated in
                        // the database
                        trigger.setNextTimestamp();
                        scheduleTimestampTriggerNotification(notificationModel, trigger);
                        WorkDataRepository.getInstance(getApplicationContext())
                            .update(
                                new WorkDataEntity(
                                    id,
                                    workDataEntity.getNotification(),
                                    ObjectUtils.bundleToBytes(triggerBundle),
                                    true));
                      } else {
                        // not repeating, delete database entry if work is a one-time request
                        WorkDataRepository.getInstance(getApplicationContext()).deleteById(id);
                      }
                    }
                  });
        };

    workDataRepository
        .getWorkDataById(id)
        .continueWithTask(alarmManagerExecutor, workContinuation)
        .addOnCompleteListener(
            task -> {
              if (!task.isSuccessful()) {
                Logger.e(TAG, "Failed to display notification", task.getException());
                return;
              }
            });
  }

  public static PendingIntent getAlarmManagerIntentForNotification(String notificationId) {
    try {
      Context context = getApplicationContext();
      Intent notificationIntent = new Intent(context, NotificationAlarmReceiver.class);
      notificationIntent.putExtra(NOTIFICATION_ID_INTENT_KEY, notificationId);
      return PendingIntent.getBroadcast(
          context,
          notificationId.hashCode(),
          notificationIntent,
          PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

    } catch (Exception e) {
      Logger.e(TAG, "Unable to create AlarmManager intent", e);
    }

    return null;
  }

  static void scheduleTimestampTriggerNotification(
      NotificationModel notificationModel, TimestampTriggerModel timestampTrigger) {

    PendingIntent pendingIntent = getAlarmManagerIntentForNotification(notificationModel.getId());

    AlarmManager alarmManager = AlarmUtils.getAlarmManager();

    // Verify we can call setExact APIs to avoid a crash, but it requires an Android S+ symbol
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      if (!alarmManager.canScheduleExactAlarms()) {
        System.err.println(
            "Missing SCHEDULE_EXACT_ALARM permission. Trigger not scheduled. See:"
                + " https://notifee.app/react-native/docs/triggers#android-12-limitations");
        return;
      }
    }

    // Ensure timestamp is always in the future when scheduling the alarm
    timestampTrigger.setNextTimestamp();

    TimestampTriggerModel.AlarmType alarmType = timestampTrigger.getAlarmType();

    switch (alarmType) {
      case SET:
        alarmManager.set(AlarmManager.RTC, timestampTrigger.getTimestamp(), pendingIntent);
        break;
      case SET_AND_ALLOW_WHILE_IDLE:
        AlarmManagerCompat.setAndAllowWhileIdle(
          alarmManager, AlarmManager.RTC_WAKEUP, timestampTrigger.getTimestamp(), pendingIntent);
        break;
      case SET_EXACT:
        AlarmManagerCompat.setExact(
          alarmManager, AlarmManager.RTC_WAKEUP, timestampTrigger.getTimestamp(), pendingIntent);
        break;
      case SET_EXACT_AND_ALLOW_WHILE_IDLE:
        AlarmManagerCompat.setExactAndAllowWhileIdle(
          alarmManager, AlarmManager.RTC_WAKEUP, timestampTrigger.getTimestamp(), pendingIntent);
        break;
      case SET_ALARM_CLOCK:
        // probably a good default behavior for setAlarmClock's

        int mutabilityFlag = PendingIntent.FLAG_UPDATE_CURRENT;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
          mutabilityFlag = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;
        }

        Context context = getApplicationContext();
        Intent launchActivityIntent =
          context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());

        PendingIntent pendingLaunchIntent =
          PendingIntent.getActivity(
            context,
            notificationModel.getId().hashCode(),
            launchActivityIntent,
            mutabilityFlag);
        AlarmManagerCompat.setAlarmClock(
          alarmManager, timestampTrigger.getTimestamp(), pendingLaunchIntent, pendingIntent);
        break;
    }
  }

  Task<List<WorkDataEntity>> getScheduledNotifications() {
    WorkDataRepository workDataRepository = new WorkDataRepository(getApplicationContext());
    return workDataRepository.getAllWithAlarmManager(true);
  }

  public static void cancelNotification(String notificationId) {
    PendingIntent pendingIntent = getAlarmManagerIntentForNotification(notificationId);
    AlarmManager alarmManager = AlarmUtils.getAlarmManager();
    if (pendingIntent != null) {
      alarmManager.cancel(pendingIntent);
    }
  }

  public static Continuation<Object, Task> cancelAllNotifications() {

    Continuation continuation =
        task -> {
          WorkDataRepository workDataRepository =
              WorkDataRepository.getInstance(getApplicationContext());

          return workDataRepository
              .getAllWithAlarmManager(true)
              .continueWith(
                  resultTask -> {
                    if (resultTask.isSuccessful()) {
                      List<WorkDataEntity> workDataEntities = resultTask.getResult();
                      for (WorkDataEntity workDataEntity : workDataEntities) {
                        NotifeeAlarmManager.cancelNotification(workDataEntity.getId());
                      }
                    }
                    return null;
                  });
        };

    return continuation;
  }

  /* On reboot, reschedule trigger notifications created via alarm manager  */
  void rescheduleNotification(WorkDataEntity workDataEntity) {
    if (workDataEntity.getNotification() == null || workDataEntity.getTrigger() == null) {
      return;
    }

    byte[] notificationBytes = workDataEntity.getNotification();
    byte[] triggerBytes = workDataEntity.getTrigger();
    Bundle triggerBundle = ObjectUtils.bytesToBundle(triggerBytes);

    NotificationModel notificationModel =
        NotificationModel.fromBundle(ObjectUtils.bytesToBundle(notificationBytes));

    int triggerType = ObjectUtils.getInt(triggerBundle.get("type"));

    switch (triggerType) {
      case 0:
        TimestampTriggerModel trigger = TimestampTriggerModel.fromBundle(triggerBundle);
        if (!trigger.getWithAlarmManager()) {
          return;
        }

        scheduleTimestampTriggerNotification(notificationModel, trigger);
        break;
      case 1:
        // TODO: support interval triggers with alarm manager
        break;
    }
  }

  void rescheduleNotifications() {
    Logger.d(TAG, "Reschedule Notifications on reboot");
    getScheduledNotifications()
        .addOnCompleteListener(
            task -> {
              List<WorkDataEntity> workDataEntities = task.getResult();

              for (WorkDataEntity workDataEntity : workDataEntities) {
                rescheduleNotification(workDataEntity);
              }
            });
  }
}
