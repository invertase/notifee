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

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.NotificationModel;

public class ForegroundService extends Service {
  private static final String TAG = "ForegroundService";
  public static final String START_FOREGROUND_SERVICE_ACTION =
      "app.notifee.core.ForegroundService.START";
  public static final String STOP_FOREGROUND_SERVICE_ACTION =
      "app.notifee.core.ForegroundService.STOP";

  public static String mCurrentNotificationId = null;

  public static int mCurrentForegroundServiceType = -1;

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

  static void stop() {
    Intent intent = new Intent(ContextHolder.getApplicationContext(), ForegroundService.class);
    intent.setAction(STOP_FOREGROUND_SERVICE_ACTION);

    try {
      // Call start service first with stop action
      ContextHolder.getApplicationContext().startService(intent);
    } catch (IllegalStateException illegalStateException) {
      // try to stop with stopService command
      ContextHolder.getApplicationContext().stopService(intent);
    } catch (Exception exception) {
      Logger.e(TAG, "Unable to stop foreground service", exception);
    }
  }

  @SuppressLint({"ForegroundServiceType", "MissingPermission"})
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // Check if action is to stop the foreground service
    if (intent == null || STOP_FOREGROUND_SERVICE_ACTION.equals(intent.getAction())) {
      stopSelf();
      mCurrentNotificationId = null;
      mCurrentForegroundServiceType = -1;
      return Service.START_STICKY_COMPATIBILITY;
    }

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

          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            int foregroundServiceType = notificationModel.getAndroid().getForegroundServiceType();
            startForeground(hashCode, notification, foregroundServiceType);
            mCurrentForegroundServiceType = foregroundServiceType;
          } else {
            startForeground(hashCode, notification);
          }

          // On headless task complete
          final MethodCallResult<Void> methodCallResult =
            (e, aVoid) -> {
              stopForeground(true);
              mCurrentNotificationId = null;
              mCurrentForegroundServiceType = -1;
            };

          ForegroundServiceEvent foregroundServiceEvent =
            new ForegroundServiceEvent(notificationModel, methodCallResult);

          EventBus.post(foregroundServiceEvent);
        } else {
          if (mCurrentNotificationId.equals(notificationModel.getId())) {
            boolean shouldPostNotificationAgain = true;
            // find if we need to start the service again if the type was changed
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
              int foregroundServiceType = notificationModel.getAndroid().getForegroundServiceType();
              if (foregroundServiceType != mCurrentForegroundServiceType) {
                startForeground(hashCode, notification, foregroundServiceType);
                mCurrentForegroundServiceType = foregroundServiceType;
                shouldPostNotificationAgain = false;
              }
            }
            if (shouldPostNotificationAgain) {
              NotificationManagerCompat.from(ContextHolder.getApplicationContext())
                .notify(hashCode, notification);
            }
          } else {
            EventBus.post(
              new NotificationEvent(NotificationEvent.TYPE_FG_ALREADY_EXIST, notificationModel));
          }
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
