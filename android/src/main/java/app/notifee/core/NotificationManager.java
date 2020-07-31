package app.notifee.core;

import static app.notifee.core.ReceiverService.ACTION_PRESS_INTENT;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.model.NotificationAndroidActionModel;
import app.notifee.core.model.NotificationAndroidModel;
import app.notifee.core.model.NotificationAndroidStyleModel;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.model.TimeTriggerModel;
import app.notifee.core.utility.ObjectUtils;
import app.notifee.core.utility.ResourceUtils;
import app.notifee.core.utility.TextUtils;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

class NotificationManager {
  private static final String TAG = "NotificationManager";
  private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();
  private static final int NOTIFICATION_TYPE_DELIVERED = 1;
  private static final int NOTIFICATION_TYPE_TRIGGER = 2;
  private static final int NOTIFICATION_TYPE_ALL = 0;

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
              new NotificationCompat.Builder(
                  ContextHolder.getApplicationContext(), androidModel.getChannelId());

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
              builder.setLargeIcon(largeIconBitmap);
            }
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
        .continueWith(CACHED_THREAD_POOL, styleContinuation);
  }

  static Task<Void> cancelNotification(@NonNull String notificationId) {
    return Tasks.call(
        () -> {
          NotificationManagerCompat notificationManagerCompat =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext());
          notificationManagerCompat.cancel(notificationId.hashCode());

          WorkManager.getInstance(ContextHolder.getApplicationContext())
              .cancelUniqueWork("schedule:" + notificationId);

          return null;
        });
  }

  static Task<Void> cancelAllNotifications(@NonNull int notificationType) {
    return Tasks.call(
        () -> {
          NotificationManagerCompat notificationManagerCompat =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext());

          if (notificationType == NOTIFICATION_TYPE_DELIVERED
              || notificationType == NOTIFICATION_TYPE_ALL) {
            notificationManagerCompat.cancelAll();
          }

          if (notificationType == NOTIFICATION_TYPE_TRIGGER
              || notificationType == NOTIFICATION_TYPE_ALL) {
            WorkManager.getInstance(ContextHolder.getApplicationContext())
                .cancelAllWorkByTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);
          }

          return null;
        });
  }

  static Task<Void> displayNotification(NotificationModel notificationModel) {
    return notificationBundleToBuilder(notificationModel)
        .continueWith(
            CACHED_THREAD_POOL,
            (task) -> {
              NotificationCompat.Builder builder = task.getResult();
              NotificationAndroidModel androidBundle = notificationModel.getAndroid();
              Notification notification = Objects.requireNonNull(builder).build();
              int hashCode = notificationModel.getHashCode();

              if (androidBundle.getAsForegroundService()) {
                ForegroundService.start(hashCode, notification, notificationModel.toBundle());
              } else {
                NotificationManagerCompat.from(ContextHolder.getApplicationContext())
                    .notify(hashCode, notification);
              }

              EventBus.post(
                  new NotificationEvent(NotificationEvent.TYPE_DELIVERED, notificationModel));

              return null;
            });
  }

  static Task<Void> createNotificationTrigger(
      NotificationModel notificationModel, Bundle triggerBundle) {
    if (triggerBundle == null) return displayNotification(notificationModel);

    return Tasks.call(
        CACHED_THREAD_POOL,
        () -> {
          TimeTriggerModel trigger = TimeTriggerModel.fromBundle(triggerBundle);

          String uniqueWorkName = "trigger:" + notificationModel.getId();
          WorkManager workManager = WorkManager.getInstance(ContextHolder.getApplicationContext());

          double delay = trigger.getDelay();
          int interval = trigger.getInterval();

          Data.Builder workDataBuilder =
              new Data.Builder()
                  .putString(Worker.KEY_WORK_TYPE, Worker.WORK_TYPE_NOTIFICATION_TRIGGER)
                  .putByteArray("trigger", ObjectUtils.bundleToBytes(triggerBundle))
                  .putByteArray(
                      "notification", ObjectUtils.bundleToBytes(notificationModel.toBundle()));

          // One time trigger
          if (interval == -1) {
            OneTimeWorkRequest.Builder workRequestBuilder =
                new OneTimeWorkRequest.Builder(Worker.class);
            workRequestBuilder.addTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);
            workRequestBuilder.setInputData(workDataBuilder.build());
            workRequestBuilder.setInitialDelay((long) delay, TimeUnit.SECONDS);
            workManager.enqueueUniqueWork(
                uniqueWorkName, ExistingWorkPolicy.REPLACE, workRequestBuilder.build());
          } else {
            PeriodicWorkRequest.Builder workRequestBuilder =
                new PeriodicWorkRequest.Builder(
                    Worker.class, interval, trigger.getIntervalTimeUnit());
            workRequestBuilder.addTag(Worker.WORK_TYPE_NOTIFICATION_TRIGGER);
            workRequestBuilder.setInputData(workDataBuilder.build());
            workRequestBuilder.setInitialDelay((long) delay, TimeUnit.SECONDS);
            workManager.enqueueUniquePeriodicWork(
                uniqueWorkName, ExistingPeriodicWorkPolicy.REPLACE, workRequestBuilder.build());
          }

          EventBus.post(
              new NotificationEvent(NotificationEvent.TYPE_TRIGGER_CREATED, notificationModel));

          return null;
        });
  }

  static void doScheduledWork(
      Data workData, CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) {
    byte[] triggerBytes = workData.getByteArray("trigger");
    byte[] notificationBytes = workData.getByteArray("notification");

    if (notificationBytes == null || triggerBytes == null) {
      Logger.w(
          TAG, "Attempted to handle doTriggerWork but no notification or trigger data was found.");
      completer.set(ListenableWorker.Result.success());
      return;
    }

    NotificationModel notificationModel =
        NotificationModel.fromBundle(ObjectUtils.bytesToBundle(notificationBytes));

    NotificationManager.displayNotification(notificationModel)
        .addOnCompleteListener(
            task -> {
              completer.set(ListenableWorker.Result.success());
              if (!task.isSuccessful()) {
                Logger.e(TAG, "Failed to display notification", task.getException());
              }
            });
  }
}
