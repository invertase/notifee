package app.notifee.core;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.NotificationModel;

public class ForegroundService extends Service {

  public static final String START_FOREGROUND_SERVICE_ACTION =
      "app.notifee.core.ForegroundService.START";

  public static String mCurrentNotificationId = null;

  static void start(int hashCode, Notification notification, Bundle notificationBundle) {
    Intent intent = new Intent(ContextHolder.getApplicationContext(), ForegroundService.class);
    intent.setAction(START_FOREGROUND_SERVICE_ACTION);
    intent.putExtra("hashCode", hashCode);
    intent.putExtra("notification", notification);
    intent.putExtra("notificationBundle", notificationBundle);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      ContextHolder.getApplicationContext().startForegroundService(intent);
    } else {
      // TODO test this on older device
      ContextHolder.getApplicationContext().startService(intent);
    }
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Bundle extras = intent.getExtras();

    if (extras != null) {
      // Hash code is sent to service to ensure it is kept the same
      int hashCode = extras.getInt("hashCode");
      Notification notification = extras.getParcelable("notification");
      Bundle bundle = extras.getBundle("notificationBundle");

      if (notification != null & bundle != null) {
        NotificationModel notificationModel = NotificationModel.fromBundle(bundle);

        if (mCurrentNotificationId == null) {
          mCurrentNotificationId = notificationModel.getId();
          startForeground(hashCode, notification);

          // On headless task complete
          final MethodCallResult<Void> methodCallResult =
              (e, aVoid) -> {
                stopForeground(true);
                mCurrentNotificationId = null;
              };

          ForegroundServiceEvent foregroundServiceEvent =
              new ForegroundServiceEvent(notificationModel, methodCallResult);

          EventBus.post(foregroundServiceEvent);
        } else if (mCurrentNotificationId.equals(notificationModel.getId())) {
          NotificationManagerCompat.from(ContextHolder.getApplicationContext())
              .notify(hashCode, notification);
        }
      }
    }

    return START_NOT_STICKY;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
