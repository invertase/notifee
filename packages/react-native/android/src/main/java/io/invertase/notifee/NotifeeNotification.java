package io.invertase.notifee;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.invertase.notifee.bundles.NotifeeNotificationAndroidActionBundle;
import io.invertase.notifee.bundles.NotifeeNotificationAndroidBundle;
import io.invertase.notifee.bundles.NotifeeNotificationBundle;
import io.invertase.notifee.core.NotifeeLogger;
import io.invertase.notifee.events.NotifeeNotificationEvent;

import static io.invertase.notifee.NotifeeForegroundService.START_FOREGROUND_SERVICE_ACTION;
import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

public class NotifeeNotification {
  static final ExecutorService NOTIFICATION_BUILD_EXECUTOR = Executors.newFixedThreadPool(6); // TODO break out
  private static final ExecutorService NOTIFICATION_DISPLAY_EXECUTOR = Executors.newFixedThreadPool(2);

  private NotifeeNotificationBundle mNotificationBundle;
  private NotifeeNotificationAndroidBundle mNotificationAndroidBundle;

  NotifeeNotification(Bundle notificationBundle) {
    this.mNotificationBundle = new NotifeeNotificationBundle(notificationBundle);
    this.mNotificationAndroidBundle = this.mNotificationBundle.getAndroidBundle();
  }

  /**
   * Returns the component name for an activity to open, triggered by a notification Intent
   *
   * @param defaultName the default component name if no intent or reactComponent was available
   * @return the string component name
   */
  public static String getMainComponentName(String defaultName) {
    synchronized (NotifeeReceiverService.reactComponents) {
      String reactComponent = null;

      if (NotifeeReceiverService.reactComponents.size() > 0) {
        synchronized (NotifeeReceiverService.reactComponents) {
          reactComponent = NotifeeReceiverService.reactComponents.get(0);
          NotifeeReceiverService.reactComponents.remove(0);
        }
      }

      if (reactComponent != null) {
        return reactComponent;
      }

      return defaultName;
    }
  }

  /**
   * Gets a non-compat notification manager
   *
   * @return NotificationManager
   */
  @SuppressWarnings("WeakerAccess")
  static NotificationManager getNotificationManager() {
    return (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
  }

  /**
   * Returns a compat notification manager
   *
   * @return NotificationManagerCompat
   */
  static NotificationManagerCompat getNotificationManagerCompat() {
    return NotificationManagerCompat.from(getApplicationContext());
  }

  /**
   * Returns whether this notification should display in a foreground service
   *
   * @return Boolean
   */
  Boolean isForegroundServiceNotification() {
    return mNotificationAndroidBundle.getAsForegroundService();
  }

  /**
   * Gets a Notification instance from the current bundle passed from JS
   *
   * @return Notification
   */
  private Task<Notification> getNotification() {
    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, () -> {
      if (Build.VERSION.SDK_INT >= 26) {
        NotificationChannel notificationChannel = getNotificationManager().getNotificationChannel(
          mNotificationAndroidBundle.getChannelId()
        );

        if (notificationChannel == null) {
          throw new InvalidNotificationParameterException(
            InvalidNotificationParameterException.CHANNEL_NOT_FOUND,
            String.format("Notification channel does not exist for the specified id '%s'.", mNotificationAndroidBundle.getChannelId())
          );
        }
      }

      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
        getApplicationContext(),
        mNotificationAndroidBundle.getChannelId()
      );

      // Always keep at top. Some Compat fields set values in extras.
      if (mNotificationBundle.getData() != null) {
        notificationBuilder.setExtras(mNotificationBundle.getData());
      }

//      notificationBuilder.setDeleteIntent(NotifeeReceiverService.createIntent(
//        ACTION_NOTIFICATION_DELETE_INTENT,
//        new String[]{"notification"},
//        notificationBundle
//      ));

//      if (androidOptionsBundle.containsKey("onPressAction")) {
//        notificationBuilder.setContentIntent(NotifeeReceiverService.createIntent(
//          ACTION_NOTIFICATION_PRESS_INTENT,
//          new String[]{"notification", "action"},
//          notificationBundle,
//          androidOptionsBundle.getBundle("onPressAction")
//        ));
//      }

      if (mNotificationBundle.getTitle() != null) {
        notificationBuilder.setContentTitle(mNotificationBundle.getTitle());
      }

      if (mNotificationBundle.getSubTitle() != null) {
        notificationBuilder.setSubText(mNotificationBundle.getSubTitle());
      }

      if (mNotificationBundle.getBody() != null) {
        notificationBuilder.setContentText(mNotificationBundle.getBody());
      }

      ArrayList<NotifeeNotificationAndroidActionBundle> actions = mNotificationAndroidBundle.getActions();
      if (actions != null) {
        for (NotifeeNotificationAndroidActionBundle actionBundle : actions) {
          NotificationCompat.Action action = Tasks.await(NotifeeNotificationAndroidActionBundle.toNotificationAction(actionBundle));
          notificationBuilder.addAction(action);
        }
      }

      notificationBuilder.setAutoCancel(mNotificationAndroidBundle.getAutoCancel());

      if (mNotificationAndroidBundle.getBadgeIconType() != null) {
        notificationBuilder.setBadgeIconType(mNotificationAndroidBundle.getBadgeIconType());
      }

      if (mNotificationAndroidBundle.getCategory() != null) {
        notificationBuilder.setCategory(mNotificationAndroidBundle.getCategory());
      }

      if (mNotificationAndroidBundle.getColor() != null) {
        notificationBuilder.setColor(mNotificationAndroidBundle.getColor());
      }

      notificationBuilder.setColorized(mNotificationAndroidBundle.getColorized());
      notificationBuilder.setChronometerCountDown(mNotificationAndroidBundle.getChronometerCountDown());
      notificationBuilder.setDefaults(mNotificationAndroidBundle.getDefaults());

      if (mNotificationAndroidBundle.getGroup() != null) {
        notificationBuilder.setGroup(mNotificationAndroidBundle.getGroup());
      }

      notificationBuilder.setGroupAlertBehavior(mNotificationAndroidBundle.getGroupAlertBehaviour());
      notificationBuilder.setGroupSummary(mNotificationAndroidBundle.getGroupSummary());

      if (mNotificationAndroidBundle.getInputHistory() != null) {
        notificationBuilder.setRemoteInputHistory(mNotificationAndroidBundle.getInputHistory());
      }

      if (mNotificationAndroidBundle.hasLargeIcon()) {
        Bitmap largeIcon = Tasks.await(mNotificationAndroidBundle.getLargeIcon());
        if (largeIcon != null) notificationBuilder.setLargeIcon(largeIcon);
      }

      if (mNotificationAndroidBundle.getLights() != null) {
        ArrayList<Integer> lights = mNotificationAndroidBundle.getLights();
        notificationBuilder.setLights(lights.get(0), lights.get(1), lights.get(2));
      }

      notificationBuilder.setLocalOnly(mNotificationAndroidBundle.getLocalOnly());

      if (mNotificationAndroidBundle.getNumber() != null) {
        notificationBuilder.setNumber(mNotificationAndroidBundle.getNumber());
      }

      notificationBuilder.setOngoing(mNotificationAndroidBundle.getOngoing());
      notificationBuilder.setOnlyAlertOnce(mNotificationAndroidBundle.getOnlyAlertOnce());
      notificationBuilder.setPriority(mNotificationAndroidBundle.getPriority());

      NotifeeNotificationAndroidBundle.AndroidProgress progress = mNotificationAndroidBundle.getProgress();
      if (progress != null) {
        notificationBuilder.setProgress(
          progress.getMax(),
          progress.getCurrent(),
          progress.getIndeterminate()
        );
      }

      if (mNotificationAndroidBundle.getShortcutId() != null) {
        notificationBuilder.setShortcutId(mNotificationAndroidBundle.getShortcutId());
      }

      notificationBuilder.setShowWhen(mNotificationAndroidBundle.getShowTimestamp());

      ArrayList<Integer> smallIconArray = mNotificationAndroidBundle.getSmallIcon();
      if (smallIconArray != null) {
        int iconId = smallIconArray.get(0);
        int level = smallIconArray.get(1);

        if (level == -1) {
          notificationBuilder.setSmallIcon(iconId);
        } else {
          notificationBuilder.setSmallIcon(iconId, level);
        }
      }

      if (mNotificationAndroidBundle.getSortKey() != null) {
        notificationBuilder.setSortKey(mNotificationAndroidBundle.getSortKey());
      }

      if (mNotificationAndroidBundle.hasStyle()) {
        NotificationCompat.Style style = Tasks.await(mNotificationAndroidBundle.getStyle());
        if (style != null) notificationBuilder.setStyle(style);
      }

      if (mNotificationAndroidBundle.getTicker() != null) {
        notificationBuilder.setTicker(mNotificationAndroidBundle.getTicker());
      }

      if (mNotificationAndroidBundle.getTimeoutAfter() != null) {
        notificationBuilder.setTimeoutAfter(mNotificationAndroidBundle.getTimeoutAfter());
      }

      notificationBuilder.setUsesChronometer(mNotificationAndroidBundle.getShowChronometer());

      long[] vibrationPattern = mNotificationAndroidBundle.getVibrationPattern();
      if (vibrationPattern.length > 0) notificationBuilder.setVibrate(vibrationPattern);

      notificationBuilder.setVisibility(mNotificationAndroidBundle.getVisibility());

      long timestamp = mNotificationAndroidBundle.getTimestamp();
      if (timestamp > -1) notificationBuilder.setWhen(timestamp);

      return notificationBuilder.build();
    });
  }

  /**
   * Displays a notification in the app
   *
   * @return void
   */
  Task<Void> displayNotification() {
    return Tasks.call(NOTIFICATION_DISPLAY_EXECUTOR, () -> {
      Notification notification = Tasks.await(getNotification());

      NotifeeLogger.d("Notification", "Displaying notification " + mNotificationBundle.getHashCode());
      getNotificationManagerCompat().notify(
        mNotificationBundle.getHashCode(),
        notification
      );

      NotifeeEventBus.post(
        new NotifeeNotificationEvent(NotifeeNotificationEvent.DELIVERED, mNotificationBundle)
      );

      return null;
    });
  }

  /**
   * Displays a notification inside of a foreground service, the user can control this service
   * via their JS by registering a runner function with notifee
   *
   * @return void
   */
  Task<Void> displayForegroundServiceNotification() {
    return Tasks.call(NOTIFICATION_DISPLAY_EXECUTOR, () -> {
      Notification notification = Tasks.await(getNotification());

      Intent serviceIntent = new Intent(getApplicationContext(), NotifeeForegroundService.class);

      serviceIntent.setAction(START_FOREGROUND_SERVICE_ACTION);
//      serviceIntent.putExtra("notificationBundle", notificationBundle);
      serviceIntent.putExtra("notification", notification);
      serviceIntent.putExtra("hash", mNotificationBundle.getHashCode());

      NotifeeLogger.d("Notification", "Starting foreground service for notification " + mNotificationBundle.getId());
      ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);

      return null;
    });
  }
}
