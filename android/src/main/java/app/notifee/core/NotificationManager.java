package app.notifee.core;

import android.app.PendingIntent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import app.notifee.core.bundles.NotificationBundle;

class NotificationManager {

  static Task<Void> displayNotification(NotificationBundle notificationBundle) {
    return Tasks.call(() -> {
      NotificationCompat.Builder builder = new NotificationCompat.Builder(
        ContextHolder.getApplicationContext(),
        notificationBundle.getAndroidBundle().getChannelId()
      );

      if (notificationBundle.getTitle() != null) {
        builder.setContentTitle(notificationBundle.getTitle());
      }

      if (notificationBundle.getBody() != null) {
        builder.setContentText(notificationBundle.getBody());
      }


      PendingIntent deleteIntent = ReceiverService.createIntent(
        ReceiverService.DELETE_INTENT,
        new String[]{"notification"},
        notificationBundle.toBundle()
      );
      builder.setDeleteIntent(deleteIntent);

      NotificationManagerCompat.from(ContextHolder.getApplicationContext())
        .notify(
          notificationBundle.getHashCode(),
          builder.build()
        );

      return null;
    });
  }
}
