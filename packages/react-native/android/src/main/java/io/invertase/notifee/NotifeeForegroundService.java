package io.invertase.notifee;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import java.util.Objects;

public class NotifeeForegroundService extends HeadlessJsTaskService {

  static final String START_FOREGROUND_SERVICE_ACTION = "io.invertase.notifee.start_foreground_service";
  static final String STOP_FOREGROUND_SERVICE_ACTION = "io.invertase.notifee.stop_foreground_service";

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
        startForeground(hash, notification);
        startTask(getTaskConfig(intent));
      } else {
        stopSelf(hash);
      }
    }

    return START_NOT_STICKY;
  }

  @Override
  protected @Nullable HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    Log.d("ELLIOT", "getTaskConfig");
    Bundle extras = intent.getExtras();
    WritableMap foo =  Arguments.createMap();
    foo.putMap("notification", Arguments.fromBundle(extras.getBundle("notificationBundle")));

    if (extras != null) {
      return new HeadlessJsTaskConfig(
        "SomeTaskName",
        foo,
        0, // timeout for the task
        true // optional: defines whether or not  the task is allowed in foreground. Default is false
      );
    }
    return null;
  }
}
