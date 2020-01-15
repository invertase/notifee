package app.notifee.core;

import android.app.Notification;
import android.app.PendingIntent;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import app.notifee.core.bundles.NotificationAndroidActionBundle;
import app.notifee.core.bundles.NotificationAndroidBundle;
import app.notifee.core.bundles.NotificationBundle;
import app.notifee.core.events.NotificationEvent;
import app.notifee.core.utils.ResourceUtils;

import static app.notifee.core.ReceiverService.ACTION_PRESS_INTENT;

class NotificationManager {
  private static final ExecutorService NOTIFICATION_BUILD_EXECUTOR = Executors.newFixedThreadPool(4);

  private static Task<NotificationCompat.Builder> notificationBundleToBuilder(
    NotificationBundle notificationBundle
  ) {
    final NotificationAndroidBundle androidBundle = notificationBundle.getAndroidBundle();

    /*
     * Construct the initial NotificationCompat.Builder instance
     */
    Callable<NotificationCompat.Builder> builderCallable = () -> {
      NotificationCompat.Builder builder = new NotificationCompat.Builder(
        ContextHolder.getApplicationContext(), androidBundle.getChannelId());

      // must always keep at top
      builder.setExtras(notificationBundle.getData());

      builder.setDeleteIntent(ReceiverService
        .createIntent(ReceiverService.DELETE_INTENT, new String[]{"notification"},
          notificationBundle.toBundle()
        ));

      if (notificationBundle.getAndroidBundle().getPressAction() != null) {
        builder.setContentIntent(ReceiverService
          .createIntent(ReceiverService.PRESS_INTENT, new String[]{"notification"},
            notificationBundle.toBundle()
          ));
      }

      if (notificationBundle.getTitle() != null) {
        builder.setContentTitle(notificationBundle.getTitle());
      }

      if (notificationBundle.getSubTitle() != null) {
        builder.setSubText(notificationBundle.getSubTitle());
      }

      if (notificationBundle.getBody() != null) {
        builder.setContentText(notificationBundle.getBody());
      }

      if (androidBundle.getBadgeIconType() != null) {
        builder.setBadgeIconType(androidBundle.getBadgeIconType());
      }

      if (androidBundle.getCategory() != null) {
        builder.setCategory(androidBundle.getCategory());
      }

      if (androidBundle.getColor() != null) {
        builder.setColor(androidBundle.getColor());
      }

      builder.setColorized(androidBundle.getColorized());
      builder.setChronometerCountDown(androidBundle.getChronometerCountDown());
      builder.setDefaults(androidBundle.getDefaults());

      if (androidBundle.getGroup() != null) {
        builder.setGroup(androidBundle.getGroup());
      }

      builder.setGroupAlertBehavior(androidBundle.getGroupAlertBehaviour());
      builder.setGroupSummary(androidBundle.getGroupSummary());

      if (androidBundle.getInputHistory() != null) {
        builder.setRemoteInputHistory(androidBundle.getInputHistory());
      }

      if (androidBundle.getLights() != null) {
        ArrayList<Integer> lights = androidBundle.getLights();
        builder.setLights(lights.get(0), lights.get(1), lights.get(2));
      }

      builder.setLocalOnly(androidBundle.getLocalOnly());

      if (androidBundle.getNumber() != null) {
        builder.setNumber(androidBundle.getNumber());
      }

      builder.setOngoing(androidBundle.getOngoing());
      builder.setOnlyAlertOnce(androidBundle.getOnlyAlertOnce());
      builder.setPriority(androidBundle.getPriority());

      NotificationAndroidBundle.AndroidProgress progress = androidBundle.getProgress();
      if (progress != null) {
        builder.setProgress(progress.getMax(), progress.getCurrent(), progress.getIndeterminate());
      }

      if (androidBundle.getShortcutId() != null) {
        builder.setShortcutId(androidBundle.getShortcutId());
      }

      builder.setShowWhen(androidBundle.getShowTimestamp());

      Integer smallIconId = androidBundle.getSmallIcon();
      if (smallIconId != null) {
        builder.setSmallIcon(smallIconId);
      }

      if (androidBundle.getSortKey() != null) {
        builder.setSortKey(androidBundle.getSortKey());
      }

      // TODO Style

      if (androidBundle.getTicker() != null) {
        builder.setTicker(androidBundle.getTicker());
      }

      if (androidBundle.getTimeoutAfter() != null) {
        builder.setTimeoutAfter(androidBundle.getTimeoutAfter());
      }

      builder.setUsesChronometer(androidBundle.getShowChronometer());

      long[] vibrationPattern = androidBundle.getVibrationPattern();
      if (vibrationPattern.length > 0) builder.setVibrate(vibrationPattern);

      builder.setVisibility(androidBundle.getVisibility());

      long timestamp = androidBundle.getTimestamp();
      if (timestamp > -1) builder.setWhen(timestamp);

      builder.setAutoCancel(androidBundle.getAutoCancel());

      return builder;
    };

    /*
     * A task continuation that fetches the largeIcon through Fresco, if specified.
     */
    Continuation<NotificationCompat.Builder, NotificationCompat.Builder> largeIconContinuation = task -> {
      NotificationCompat.Builder builder = task.getResult();

      if (androidBundle.hasLargeIcon()) {
        String largeIcon = androidBundle.getLargeIcon();
        Bitmap bitmapFromUrl = Tasks.await(
          ResourceUtils.getImageBitmapFromUrl(largeIcon)
          // 10 second timeout - should this be configurable?
          , 10, TimeUnit.SECONDS
        );
        if (bitmapFromUrl != null) builder.setLargeIcon(bitmapFromUrl);
      }

      return builder;
    };

    /*
     * A task continuation that builds all actions, if any. Additionally fetches icon bitmaps
     * through Fresco.
     */
    Continuation<NotificationCompat.Builder, NotificationCompat.Builder> actionsContinuation = task -> {
      NotificationCompat.Builder builder = task.getResult();
      ArrayList<NotificationAndroidActionBundle> actionBundles = androidBundle.getActions();

      if (actionBundles == null) {
        return builder;
      }

      for (NotificationAndroidActionBundle actionBundle : actionBundles) {
        PendingIntent pendingIntent = ReceiverService
          .createIntent(
            ACTION_PRESS_INTENT,
            new String[]{"action"},
            actionBundle.toBundle()
          );

        Bitmap icon = Tasks.await(
          // 5 second timeout - should this be configurable?
          ResourceUtils.getImageBitmapFromUrl(actionBundle.getIcon()),
          5, TimeUnit.SECONDS
        );

        NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(
          IconCompat.createWithAdaptiveBitmap(icon),
          actionBundle.getTitle(),
          pendingIntent
        );

        RemoteInput remoteInput = actionBundle.getRemoteInput(actionBuilder);
        if (remoteInput != null) {
          actionBuilder.addRemoteInput(remoteInput);
        }

        builder.addAction(actionBuilder.build());
      }

      return builder;
    };

    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, builderCallable)
      // get a large image bitmap if largeIcon is set
      .continueWith(NOTIFICATION_BUILD_EXECUTOR, largeIconContinuation)
      // build notification actions, tasks based to allow image fetching
      .continueWith(NOTIFICATION_BUILD_EXECUTOR, actionsContinuation);
  }

  static Task<Void> cancelNotification(@NonNull String notificationId) {
    return Tasks.call(() -> {
      // TODO get notification by ID, and whether it's scheduled
      Boolean scheduled = false;

      if (scheduled == false) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat
          .from(ContextHolder.getApplicationContext());
        notificationManagerCompat.cancel(notificationId.hashCode());
        // TODO Update DB
      } else {
        // TODO cancel a scheduled notification
      }

      return null;
    });
  }

  static Task<Void> cancelAllNotifications() {
    return Tasks.call(() -> {
      // TODO delete database scheduled ones
      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat
        .from(ContextHolder.getApplicationContext());
      notificationManagerCompat.cancelAll();
      // TODO update DB?
      return null;
    });
  }


  static Task<Void> displayNotification(NotificationBundle notificationBundle) {
    return notificationBundleToBuilder(notificationBundle)
      .continueWith(NOTIFICATION_BUILD_EXECUTOR, (task) -> {
        NotificationCompat.Builder builder = task.getResult();
        NotificationAndroidBundle androidBundle = notificationBundle.getAndroidBundle();

        int hashCode = notificationBundle.getHashCode();
        Notification notification = builder.build();

        if (androidBundle.getAsForegroundService()) {
          ContextCompat.startForegroundService(ContextHolder.getApplicationContext(),
            ForegroundService.createIntent(notificationBundle.toBundle())
          );
        } else {
          NotificationManagerCompat.from(ContextHolder.getApplicationContext())
            .notify(hashCode, notification);
        }

        EventBus.post(new NotificationEvent(NotificationEvent.TYPE_DELIVERED, notificationBundle));

        return null;
      });
  }
}
