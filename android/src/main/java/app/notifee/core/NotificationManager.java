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
import static app.notifee.core.ReceiverService.ACTION_PRESS_INTENT;
import static java.lang.Integer.parseInt;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import androidx.annotation.NonNull;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import app.notifee.core.database.WorkDataEntity;
import app.notifee.core.database.WorkDataRepository;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.IntervalTriggerModel;
import app.notifee.core.model.NotificationAndroidActionModel;
import app.notifee.core.model.NotificationAndroidModel;
import app.notifee.core.model.NotificationAndroidPressActionModel;
import app.notifee.core.model.NotificationAndroidStyleModel;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.model.TimestampTriggerModel;
import app.notifee.core.utility.IntentUtils;
import app.notifee.core.utility.ObjectUtils;
import app.notifee.core.utility.ResourceUtils;
import app.notifee.core.utility.TextUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class NotificationManager {
  private static final String TAG = "NotificationManager";
  private static final String EXTRA_NOTIFEE_NOTIFICATION = "notifee.notification";
  private static final String EXTRA_NOTIFEE_TRIGGER = "notifee.trigger";
  private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();
  private static final int NOTIFICATION_TYPE_ALL = 0;
  private static final int NOTIFICATION_TYPE_DISPLAYED = 1;
  private static final int NOTIFICATION_TYPE_TRIGGER = 2;

  private static Task<NotificationCompat.Builder> notificationBundleToBuilder(
      NotificationModel notificationModel) {
    final NotificationAndroidModel androidModel = notificationModel.getAndroid();

    /*
     * Construct the initial NotificationCompat.Builder instance
     */
    Callable<NotificationCompat.Builder> builderCallable =
        () -> {
          Boolean hasCustomSound = false;
          NotificationCompat.Builder builder =
              new NotificationCompat.Builder(getApplicationContext(), androidModel.getChannelId());

          // must always keep at top
          builder.setExtras(notificationModel.getData());

          builder.setDeleteIntent(
              ReceiverService.createIntent(
                  ReceiverService.DELETE_INTENT,
                  new String[] {"notification"},
                  notificationModel.toBundle()));

          builder.setContentIntent(
              ReceiverService.createIntent(
                  ReceiverService.PRESS_INTENT,
                  new String[] {"notification", "pressAction"},
                  notificationModel.toBundle(),
                  androidModel.getPressAction()));

          if (notificationModel.getTitle() != null) {
            builder.setContentTitle(TextUtils.fromHtml(notificationModel.getTitle()));
          }

          if (notificationModel.getSubTitle() != null) {
            builder.setSubText(TextUtils.fromHtml(notificationModel.getSubTitle()));
          }

          if (notificationModel.getBody() != null) {
            builder.setContentText(TextUtils.fromHtml(notificationModel.getBody()));
          }

          if (androidModel.getBadgeIconType() != null) {
            builder.setBadgeIconType(androidModel.getBadgeIconType());
          }

          if (androidModel.getCategory() != null) {
            builder.setCategory(androidModel.getCategory());
          }

          if (androidModel.getColor() != null) {
            builder.setColor(androidModel.getColor());
          }

          builder.setColorized(androidModel.getColorized());

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setChronometerCountDown(androidModel.getChronometerCountDown());
          }

          if (androidModel.getGroup() != null) {
            builder.setGroup(androidModel.getGroup());
          }

          builder.setGroupAlertBehavior(androidModel.getGroupAlertBehaviour());
          builder.setGroupSummary(androidModel.getGroupSummary());

          if (androidModel.getInputHistory() != null) {
            builder.setRemoteInputHistory(androidModel.getInputHistory());
          }

          if (androidModel.getLights() != null) {
            ArrayList<Integer> lights = androidModel.getLights();
            builder.setLights(lights.get(0), lights.get(1), lights.get(2));
          }

          builder.setLocalOnly(androidModel.getLocalOnly());

          if (androidModel.getNumber() != null) {
            builder.setNumber(androidModel.getNumber());
          }

          if (androidModel.getSound() != null) {
            Uri soundUri = ResourceUtils.getSoundUri(androidModel.getSound());
            if (soundUri != null) {
              hasCustomSound = true;
              builder.setSound(soundUri);
            } else {
              Logger.w(
                  TAG,
                  "Unable to retrieve sound for notification, sound was specified as: "
                      + androidModel.getSound());
            }
          }

          builder.setDefaults(androidModel.getDefaults(hasCustomSound));
          builder.setOngoing(androidModel.getOngoing());
          builder.setOnlyAlertOnce(androidModel.getOnlyAlertOnce());
          builder.setPriority(androidModel.getPriority());

          NotificationAndroidModel.AndroidProgress progress = androidModel.getProgress();
          if (progress != null) {
            builder.setProgress(
                progress.getMax(), progress.getCurrent(), progress.getIndeterminate());
          }

          if (androidModel.getShortcutId() != null) {
            builder.setShortcutId(androidModel.getShortcutId());
          }

          builder.setShowWhen(androidModel.getShowTimestamp());

          Integer smallIconId = androidModel.getSmallIcon();
          if (smallIconId != null) {
            Integer smallIconLevel = androidModel.getSmallIconLevel();
            if (smallIconLevel != null) {
              builder.setSmallIcon(smallIconId, smallIconLevel);
            } else {
              builder.setSmallIcon(smallIconId);
            }
          }

          if (androidModel.getSortKey() != null) {
            builder.setSortKey(androidModel.getSortKey());
          }

          if (androidModel.getTicker() != null) {
            builder.setTicker(androidModel.getTicker());
          }

          if (androidModel.getTimeoutAfter() != null) {
            builder.setTimeoutAfter(androidModel.getTimeoutAfter());
          }

          builder.setUsesChronometer(androidModel.getShowChronometer());

          long[] vibrationPattern = androidModel.getVibrationPattern();
          if (vibrationPattern.length > 0) builder.setVibrate(vibrationPattern);

          builder.setVisibility(androidModel.getVisibility());

          long timestamp = androidModel.getTimestamp();
          if (timestamp > -1) builder.setWhen(timestamp);

          builder.setAutoCancel(androidModel.getAutoCancel());

          return builder;
        };

    /*
     * A task continuation that fetches the largeIcon through Fresco, if specified.
     */
    Continuation<NotificationCompat.Builder, NotificationCompat.Builder> largeIconContinuation =
        task -> {
          NotificationCompat.Builder builder = task.getResult();

          if (androidModel.hasLargeIcon()) {
            String largeIcon = androidModel.getLargeIcon();
            Bitmap largeIconBitmap = null;

            try {
              largeIconBitmap =
                  Tasks.await(ResourceUtils.getImageBitmapFromUrl(largeIcon), 10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
              Logger.e(
                  TAG,
                  "Timeout occurred whilst trying to retrieve a largeIcon image: " + largeIcon,
                  e);
            } catch (Exception e) {
              Logger.e(
                  TAG,
                  "An error occurred whilst trying to retrieve a largeIcon image: " + largeIcon,
                  e);
            }

            if (largeIconBitmap != null) {
              if (androidModel.getCircularLargeIcon()) {
                largeIconBitmap = ResourceUtils.getCircularBitmap(largeIconBitmap);
              }

              builder.setLargeIcon(largeIconBitmap);
            }
          }

          return builder;
        };

    /*
     * A task continuation for full-screen action, if specified.
     */
    Continuation<NotificationCompat.Builder, NotificationCompat.Builder>
        fullScreenActionContinuation =
            task -> {
              NotificationCompat.Builder builder = task.getResult();
              if (androidModel.hasFullScreenAction()) {
                NotificationAndroidPressActionModel fullScreenActionBundle =
                    androidModel.getFullScreenAction();

                String launchActivity = fullScreenActionBundle.getLaunchActivity();
                Class launchActivityClass = IntentUtils.getLaunchActivity(launchActivity);
                if (launchActivityClass == null) {
                  Logger.e(
                      TAG,
                      String.format(
                          "Launch Activity for full-screen action does not exist ('%s').",
                          launchActivity));
                  return builder;
                }

                Intent launchIntent = new Intent(getApplicationContext(), launchActivityClass);
                if (fullScreenActionBundle.getLaunchActivityFlags() != -1) {
                  launchIntent.addFlags(fullScreenActionBundle.getLaunchActivityFlags());
                }

                if (fullScreenActionBundle.getMainComponent() != null) {
                  launchIntent.putExtra("mainComponent", fullScreenActionBundle.getMainComponent());
                  launchIntent.putExtra("notification", notificationModel.toBundle());
                  EventBus.postSticky(
                      new MainComponentEvent(fullScreenActionBundle.getMainComponent()));
                }

                PendingIntent fullScreenPendingIntent =
                    PendingIntent.getActivity(
                        getApplicationContext(),
                        notificationModel.getHashCode(),
                        launchIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
                builder.setFullScreenIntent(fullScreenPendingIntent, true);
              }

              return builder;
            };

    /*
     * A task continuation that builds all actions, if any. Additionally fetches
     * icon bitmaps through Fresco.
     */
    Continuation<NotificationCompat.Builder, NotificationCompat.Builder> actionsContinuation =
        task -> {
          NotificationCompat.Builder builder = task.getResult();
          ArrayList<NotificationAndroidActionModel> actionBundles = androidModel.getActions();

          if (actionBundles == null) {
            return builder;
          }

          for (NotificationAndroidActionModel actionBundle : actionBundles) {
            PendingIntent pendingIntent =
                ReceiverService.createIntent(
                    ACTION_PRESS_INTENT,
                    new String[] {"notification", "pressAction"},
                    notificationModel.toBundle(),
                    actionBundle.getPressAction().toBundle());

            String icon = actionBundle.getIcon();
            Bitmap iconBitmap = null;

            if (icon != null) {
              try {
                iconBitmap =
                    Tasks.await(
                        ResourceUtils.getImageBitmapFromUrl(actionBundle.getIcon()),
                        10,
                        TimeUnit.SECONDS);
              } catch (TimeoutException e) {
                Logger.e(
                    TAG, "Timeout occurred whilst trying to retrieve an action icon: " + icon, e);
              } catch (Exception e) {
                Logger.e(
                    TAG, "An error occurred whilst trying to retrieve an action icon: " + icon, e);
              }
            }

            IconCompat iconCompat = null;
            if (iconBitmap != null) {
              iconCompat = IconCompat.createWithAdaptiveBitmap(iconBitmap);
            }

            NotificationCompat.Action.Builder actionBuilder =
                new NotificationCompat.Action.Builder(
                    iconCompat, TextUtils.fromHtml(actionBundle.getTitle()), pendingIntent);

            RemoteInput remoteInput = actionBundle.getRemoteInput(actionBuilder);
            if (remoteInput != null) {
              actionBuilder.addRemoteInput(remoteInput);
            }

            builder.addAction(actionBuilder.build());
          }

          return builder;
        };

    /*
     * A task continuation that builds the notification style, if any. Additionally
     * fetches any image bitmaps (e.g. Person image, or BigPicture image) through
     * Fresco.
     */
    Continuation<NotificationCompat.Builder, NotificationCompat.Builder> styleContinuation =
        task -> {
          NotificationCompat.Builder builder = task.getResult();
          NotificationAndroidStyleModel androidStyleBundle = androidModel.getStyle();
          if (androidStyleBundle == null) {
            return builder;
          }

          Task<NotificationCompat.Style> styleTask =
              androidStyleBundle.getStyleTask(CACHED_THREAD_POOL);
          if (styleTask == null) {
            return builder;
          }

          NotificationCompat.Style style = Tasks.await(styleTask);
          if (style != null) {
            builder.setStyle(style);
          }

          return builder;
        };

    return Tasks.call(CACHED_THREAD_POOL, builderCallable)
        // get a large image bitmap if largeIcon is set
        .continueWith(CACHED_THREAD_POOL, largeIconContinuation)
        // build notification actions, tasks based to allow image fetching
        .continueWith(CACHED_THREAD_POOL, actionsContinuation)
        // build notification style, tasks based to allow image fetching
        .continueWith(CACHED_THREAD_POOL, styleContinuation)
        // set full screen action, if fullScreenAction is set
        .continueWith(CACHED_THREAD_POOL, fullScreenActionContinuation);
  }

  static Task<Void> cancelAllNotifications(@NonNull int notificationType) {
    return Tasks.call(
            () -> {
              NotificationManagerCompat notificationManagerCompat =
                  NotificationManagerCompat.from(getApplicationContext());

              if (notificationType == NOTIFICATION_TYPE_DISPLAYED
                  || notificationType == NOTIFICATION_TYPE_ALL) {
                notificationManagerCompat.cancelAll();
              }

              if (notificationType == NOTIFICATION_TYPE_TRIGGER
                  || notificationType == NOTIFICATION_TYPE_ALL) {
                WorkManager workManager = WorkManager.getInstance(getApplicationContext());
                workManager.cancelAllWorkByTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);

                // Remove all cancelled and finished work from its internal database
                // states include SUCCEEDED, FAILED and CANCELLED
                workManager.pruneWork();
              }
              return null;
            })
        .continueWith(
            CACHED_THREAD_POOL,
            task -> {
              if (notificationType == NOTIFICATION_TYPE_TRIGGER
                  || notificationType == NOTIFICATION_TYPE_ALL) {
                NotifeeAlarmManager.cancelAllNotifications();
                // delete all from database
                WorkDataRepository.getInstance(getApplicationContext()).deleteAll();
              }
              return null;
            });
  }

  static Task<Void> cancelAllNotificationsWithIds(
      @NonNull int notificationType, @NonNull List<String> ids, String tag) {
    return Tasks.call(
            () -> {
              WorkManager workManager = WorkManager.getInstance(getApplicationContext());
              NotificationManagerCompat notificationManagerCompat =
                  NotificationManagerCompat.from(getApplicationContext());

              for (String id : ids) {
                Logger.i(TAG, "Removing notification with id " + id);

                if (notificationType != NOTIFICATION_TYPE_TRIGGER) {
                  // Cancel notifications displayed by FCM which will always have
                  // an id of 0 and a tag, see https://github.com/invertase/notifee/pull/175
                  if (tag != null && id.equals("0")) {
                    // Attempt to parse id as integer
                    Integer integerId = null;

                    try {
                      integerId = parseInt(id);
                    } catch (Exception e) {
                      Logger.e(
                          TAG,
                          "cancelAllNotificationsWithIds -> Failed to parse id as integer  " + id);
                    }

                    if (integerId != null) {
                      notificationManagerCompat.cancel(tag, integerId);
                    }
                  }

                  // Cancel a notification created with notifee
                  notificationManagerCompat.cancel(tag, id.hashCode());
                }

                if (notificationType != NOTIFICATION_TYPE_DISPLAYED) {
                  Logger.i(TAG, "Removing notification with id " + id);

                  workManager.cancelUniqueWork("trigger:" + id);
                  // Remove all cancelled and finished work from its internal database
                  // states include SUCCEEDED, FAILED and CANCELLED
                  workManager.pruneWork();

                  // And with alarm manager
                  NotifeeAlarmManager.cancelNotification(id);
                }
              }

              return null;
            })
        .continueWith(
            task -> {
              // delete all from database
              if (notificationType != NOTIFICATION_TYPE_DISPLAYED) {
                WorkDataRepository.getInstance(getApplicationContext()).deleteByIds(ids);
              }
              return null;
            });
  }

  static Task<Void> displayNotification(NotificationModel notificationModel, Bundle triggerBundle) {
    return notificationBundleToBuilder(notificationModel)
        .continueWith(
            CACHED_THREAD_POOL,
            (task) -> {
              NotificationCompat.Builder builder = task.getResult();

              // Add the following extras for `getDisplayedNotifications()`
              Bundle extrasBundle = new Bundle();
              extrasBundle.putBundle(EXTRA_NOTIFEE_NOTIFICATION, notificationModel.toBundle());
              if (triggerBundle != null) {
                extrasBundle.putBundle(EXTRA_NOTIFEE_TRIGGER, triggerBundle);
              }
              builder.addExtras(extrasBundle);

              // build notification
              Notification notification = Objects.requireNonNull(builder).build();

              int hashCode = notificationModel.getHashCode();

              NotificationAndroidModel androidBundle = notificationModel.getAndroid();
              if (androidBundle.getAsForegroundService()) {
                ForegroundService.start(hashCode, notification, notificationModel.toBundle());
              } else {
                NotificationManagerCompat.from(getApplicationContext())
                    .notify(androidBundle.getTag(), hashCode, notification);
              }

              EventBus.post(
                  new NotificationEvent(NotificationEvent.TYPE_DELIVERED, notificationModel));

              return null;
            });
  }

  static Task<Void> createTriggerNotification(
      NotificationModel notificationModel, Bundle triggerBundle) {
    return Tasks.call(
        CACHED_THREAD_POOL,
        () -> {
          int triggerType = (int) triggerBundle.getDouble("type");
          switch (triggerType) {
            case 0:
              createTimestampTriggerNotification(notificationModel, triggerBundle);
              break;
            case 1:
              createIntervalTriggerNotification(notificationModel, triggerBundle);
              break;
          }

          EventBus.post(
              new NotificationEvent(
                  NotificationEvent.TYPE_TRIGGER_NOTIFICATION_CREATED, notificationModel));

          return null;
        });
  }

  static void createIntervalTriggerNotification(
      NotificationModel notificationModel, Bundle triggerBundle) {
    IntervalTriggerModel trigger = IntervalTriggerModel.fromBundle(triggerBundle);
    String uniqueWorkName = "trigger:" + notificationModel.getId();
    WorkManager workManager = WorkManager.getInstance(getApplicationContext());

    Data.Builder workDataBuilder =
        new Data.Builder()
            .putString(Worker.KEY_WORK_TYPE, Worker.WORK_TYPE_NOTIFICATION_TRIGGER)
            .putString(Worker.KEY_WORK_REQUEST, Worker.WORK_REQUEST_PERIODIC)
            .putString("id", notificationModel.getId());

    WorkDataRepository.getInstance(getApplicationContext())
        .insertTriggerNotification(notificationModel, triggerBundle, false);

    long interval = trigger.getInterval();

    PeriodicWorkRequest.Builder workRequestBuilder;
    workRequestBuilder =
        new PeriodicWorkRequest.Builder(Worker.class, interval, trigger.getTimeUnit());

    workRequestBuilder.addTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);
    workRequestBuilder.addTag(uniqueWorkName);
    workRequestBuilder.setInputData(workDataBuilder.build());
    workManager.enqueueUniquePeriodicWork(
        uniqueWorkName, ExistingPeriodicWorkPolicy.REPLACE, workRequestBuilder.build());
  }

  static void createTimestampTriggerNotification(
      NotificationModel notificationModel, Bundle triggerBundle) {
    TimestampTriggerModel trigger = TimestampTriggerModel.fromBundle(triggerBundle);

    String uniqueWorkName = "trigger:" + notificationModel.getId();

    long delay = trigger.getDelay();
    int interval = trigger.getInterval();

    // Save in DB
    Data.Builder workDataBuilder =
        new Data.Builder()
            .putString(Worker.KEY_WORK_TYPE, Worker.WORK_TYPE_NOTIFICATION_TRIGGER)
            .putString("id", notificationModel.getId());

    Boolean withAlarmManager = trigger.getWithAlarmManager();

    WorkDataRepository.getInstance(getApplicationContext())
        .insertTriggerNotification(notificationModel, triggerBundle, withAlarmManager);

    // Schedule notification with alarm manager
    if (withAlarmManager) {
      NotifeeAlarmManager.scheduleTimestampTriggerNotification(notificationModel, trigger, true);
      return;
    }

    // Continue to schedule trigger notification with WorkManager
    WorkManager workManager = WorkManager.getInstance(getApplicationContext());

    // WorkManager - One time trigger
    if (interval == -1) {
      OneTimeWorkRequest.Builder workRequestBuilder = new OneTimeWorkRequest.Builder(Worker.class);
      workRequestBuilder.addTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);
      workRequestBuilder.addTag(uniqueWorkName);
      workDataBuilder.putString(Worker.KEY_WORK_REQUEST, Worker.WORK_REQUEST_ONE_TIME);
      workRequestBuilder.setInputData(workDataBuilder.build());
      workRequestBuilder.setInitialDelay(delay, TimeUnit.SECONDS);
      workManager.enqueueUniqueWork(
          uniqueWorkName, ExistingWorkPolicy.REPLACE, workRequestBuilder.build());
    } else {
      // WorkManager - repeat trigger
      PeriodicWorkRequest.Builder workRequestBuilder;

      workRequestBuilder =
          new PeriodicWorkRequest.Builder(
              Worker.class, trigger.getInterval(), trigger.getTimeUnit());

      workRequestBuilder.addTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);
      workRequestBuilder.addTag(uniqueWorkName);
      workRequestBuilder.setInitialDelay(delay, TimeUnit.SECONDS);
      workDataBuilder.putString(Worker.KEY_WORK_REQUEST, Worker.WORK_REQUEST_PERIODIC);
      workRequestBuilder.setInputData(workDataBuilder.build());
      workManager.enqueueUniquePeriodicWork(
          uniqueWorkName, ExistingPeriodicWorkPolicy.REPLACE, workRequestBuilder.build());
    }
  }

  static Task<List<Bundle>> getDisplayedNotifications() {
    return Tasks.call(
        () -> {
          List<Bundle> notifications = new ArrayList<Bundle>();

          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return notifications;
          }

          android.app.NotificationManager notificationManager =
              (android.app.NotificationManager)
                  getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

          StatusBarNotification delivered[] = notificationManager.getActiveNotifications();

          for (StatusBarNotification sbNotification : delivered) {
            Notification original = sbNotification.getNotification();

            Bundle extras = original.extras;
            Bundle displayNotificationBundle = new Bundle();

            Bundle notificationBundle = extras.getBundle(EXTRA_NOTIFEE_NOTIFICATION);
            Bundle triggerBundle = extras.getBundle(EXTRA_NOTIFEE_TRIGGER);

            if (notificationBundle == null) {
              notificationBundle = new Bundle();
              notificationBundle.putString("id", "" + sbNotification.getId());

              Object title = extras.get(Notification.EXTRA_TITLE);

              if (title != null) {
                notificationBundle.putString("title", title.toString());
              }

              Object text = extras.get(Notification.EXTRA_TEXT);

              if (text != null) {
                notificationBundle.putString("body", text.toString());
              }

              Object subtitle = extras.get(Notification.EXTRA_SUB_TEXT);

              if (subtitle != null) {
                notificationBundle.putString("subtitle", subtitle.toString());
              }

              Bundle androidBundle = new Bundle();
              if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                androidBundle.putString("channelId", original.getChannelId());
              }
              androidBundle.putString("tag", sbNotification.getTag());
              androidBundle.putString("group", original.getGroup());

              notificationBundle.putBundle("android", androidBundle);

              displayNotificationBundle.putString("id", "" + sbNotification.getId());
            } else {
              displayNotificationBundle.putString("id", "" + notificationBundle.get("id"));
            }

            if (triggerBundle != null) {
              displayNotificationBundle.putBundle("trigger", triggerBundle);
            }

            displayNotificationBundle.putBundle("notification", notificationBundle);
            displayNotificationBundle.putString("date", "" + sbNotification.getPostTime());

            notifications.add(displayNotificationBundle);
          }

          return notifications;
        });
  }

  static void getTriggerNotifications(MethodCallResult<List<Bundle>> result) {
    WorkDataRepository workDataRepository = new WorkDataRepository(getApplicationContext());

    workDataRepository
        .getAll()
        .addOnCompleteListener(
            task -> {
              List<Bundle> triggerNotifications = new ArrayList<Bundle>();

              if (task.isSuccessful()) {
                List<WorkDataEntity> workDataEntities = task.getResult();
                for (WorkDataEntity workDataEntity : workDataEntities) {
                  Bundle triggerNotificationBundle = new Bundle();

                  triggerNotificationBundle.putBundle(
                      "notification", ObjectUtils.bytesToBundle(workDataEntity.getNotification()));

                  triggerNotificationBundle.putBundle(
                      "trigger", ObjectUtils.bytesToBundle(workDataEntity.getTrigger()));
                  triggerNotifications.add(triggerNotificationBundle);
                }

                result.onComplete(null, triggerNotifications);
              } else {
                result.onComplete(task.getException(), triggerNotifications);
              }
            });
  }

  static void getTriggerNotificationIds(MethodCallResult<List<String>> result) {
    WorkDataRepository workDataRepository = new WorkDataRepository(getApplicationContext());

    workDataRepository
        .getAll()
        .addOnCompleteListener(
            task -> {
              List<String> triggerNotificationIds = new ArrayList<String>();

              if (task.isSuccessful()) {
                List<WorkDataEntity> workDataEntities = task.getResult();
                for (WorkDataEntity workDataEntity : workDataEntities) {
                  triggerNotificationIds.add(workDataEntity.getId());
                }

                result.onComplete(null, triggerNotificationIds);
              } else {
                result.onComplete(task.getException(), null);
              }
            });
  }

  /* Execute work from trigger notifications via WorkManager*/
  static void doScheduledWork(
      Data data, CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) {

    String id = data.getString("id");

    WorkDataRepository workDataRepository = new WorkDataRepository(getApplicationContext());

    Continuation<WorkDataEntity, Task<Void>> workContinuation =
        task -> {
          WorkDataEntity workDataEntity = task.getResult();

          byte[] notificationBytes;

          if (workDataEntity == null || workDataEntity.getNotification() == null) {
            // check if notification bundle is stored with Work Manager
            notificationBytes = data.getByteArray("notification");
            if (notificationBytes != null) {
              Logger.w(
                  TAG,
                  "The trigger notification was created using an older version, please consider"
                      + " recreating the notification.");
            } else {
              Logger.w(
                  TAG, "Attempted to handle doScheduledWork but no notification data was found.");
              completer.set(ListenableWorker.Result.success());
              return null;
            }
          } else {
            notificationBytes = workDataEntity.getNotification();
          }

          NotificationModel notificationModel =
              NotificationModel.fromBundle(ObjectUtils.bytesToBundle(notificationBytes));

          byte[] triggerBytes = workDataEntity.getTrigger();
          Bundle triggerBundle = null;

          if (workDataEntity.getTrigger() != null) {
            triggerBundle = ObjectUtils.bytesToBundle(triggerBytes);
          }

          return NotificationManager.displayNotification(notificationModel, triggerBundle);
        };

    workDataRepository
        .getWorkDataById(id)
        .continueWithTask(CACHED_THREAD_POOL, workContinuation)
        .addOnCompleteListener(
            task -> {
              completer.set(ListenableWorker.Result.success());

              if (!task.isSuccessful()) {
                Logger.e(TAG, "Failed to display notification", task.getException());
              } else {
                String workerRequestType = data.getString(Worker.KEY_WORK_REQUEST);
                if (workerRequestType != null
                    && workerRequestType.equals(Worker.WORK_REQUEST_ONE_TIME)) {
                  // delete database entry if work is a one-time request
                  WorkDataRepository.getInstance(getApplicationContext()).deleteById(id);
                }
              }
            });
  }
}
