package app.notifee.core;

import android.app.Notification;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;

import app.notifee.core.bundles.NotificationAndroidBundle;
import app.notifee.core.bundles.NotificationBundle;
import app.notifee.core.events.NotificationEvent;

class NotificationManager {

  static Task<Void> cancelNotification(@NonNull String notificationId) {
    return Tasks.call(() -> {
      // TODO get notification by ID, and whether it's scheduled
      Boolean scheduled = false;

      if (scheduled == false) {
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(
          ContextHolder.getApplicationContext()
        );
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
      NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(
        ContextHolder.getApplicationContext()
      );
      notificationManagerCompat.cancelAll();
      // TODO update DB?
      return null;
    });
  }

  static Task<Void> displayNotification(NotificationBundle notificationBundle) {
    return Tasks.call(() -> {
      NotificationAndroidBundle androidBundle = notificationBundle.getAndroidBundle();

      NotificationCompat.Builder builder = new NotificationCompat.Builder(
        ContextHolder.getApplicationContext(),
        androidBundle.getChannelId()
      );

      // Always keep at top
      builder.setExtras(notificationBundle.getData());

      builder.setDeleteIntent(ReceiverService.createIntent(
        ReceiverService.DELETE_INTENT,
        new String[]{"notification"},
        notificationBundle.toBundle()
      ));

      if (notificationBundle.getAndroidBundle().getPressAction() != null) {
        builder.setContentIntent(ReceiverService.createIntent(
          ReceiverService.PRESS_INTENT,
          new String[]{"notification"},
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

      // TODO actions

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

      if (androidBundle.hasLargeIcon()) {
        Bitmap largeIcon = Tasks.await(androidBundle.getLargeIcon());
        if (largeIcon != null) builder.setLargeIcon(largeIcon);
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
        builder.setProgress(
          progress.getMax(),
          progress.getCurrent(),
          progress.getIndeterminate()
        );
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


      int hashCode = notificationBundle.getHashCode();
      Notification notification = builder.build();

      if (androidBundle.getAsForegroundService()) {
        ContextCompat.startForegroundService(
          ContextHolder.getApplicationContext(),
          ForegroundService.createIntent(notificationBundle.toBundle())
        );
      } else {
        NotificationManagerCompat.from(ContextHolder.getApplicationContext()).notify(hashCode, notification);
      }

      EventBus.post(new NotificationEvent(
        NotificationEvent.TYPE_DELIVERED,
        notificationBundle
      ));

      return null;
    });
  }
}
