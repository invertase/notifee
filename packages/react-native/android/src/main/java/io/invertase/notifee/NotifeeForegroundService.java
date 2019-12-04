package io.invertase.notifee;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import java.util.Objects;

public class NotifeeForegroundService extends HeadlessJsTaskService {

  static final String FOREGROUND_SERVICE_TASK_KEY = "notifee_foreground_service";
  static final String START_FOREGROUND_SERVICE_ACTION = "io.invertase.notifee.start_foreground_service";
  static final String STOP_FOREGROUND_SERVICE_ACTION = "io.invertase.notifee.stop_foreground_service";

  private int activeNotificationHash = -1;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    String action = intent.getAction();

    if (action != null) {
      Bundle extras = intent.getExtras();
      int hash = Objects.requireNonNull(extras).getInt("hash");

      if (action.equals(START_FOREGROUND_SERVICE_ACTION)) {
        Notification notification = extras.getParcelable("notification");

        // If notification exists, and new one is different
        if (activeNotificationHash != -1 && hash != activeNotificationHash) {
          // Stop foreground, but keep service active
          stopForeground(true);

          // Start new
          startForeground(hash, notification);
          startTask(getTaskConfig(intent));
        }
        // No service is active
        else if (activeNotificationHash == -1) {
          startForeground(hash, notification);
          startTask(getTaskConfig(intent));
        }
      } else {
        activeNotificationHash = -1;
        stopSelf(hash);
      }
    }

    return START_NOT_STICKY;
  }

  @Override
  protected @Nullable
  HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    Bundle extras = intent.getExtras();
    WritableMap writableMap = Arguments.createMap();

    writableMap.putMap("notification", Arguments.fromBundle(Objects.requireNonNull(extras.getBundle("notificationBundle"))));

    return new HeadlessJsTaskConfig(
      FOREGROUND_SERVICE_TASK_KEY,
      writableMap,
      0, // timeout for the task
      true
    );
  }

  @Override
  public void onHeadlessJsTaskFinish(int taskId) {
    activeNotificationHash = -1;
    super.onHeadlessJsTaskFinish(taskId);
  }
}
