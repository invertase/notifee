package app.notifee.core;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;

// TODO handle foreground service
public class ForegroundService extends Service {

  public static final String START_FOREGROUND_SERVICE_ACTION = "app.notifee.core.ForegroundService.START";
  public static final String STOP_FOREGROUND_SERVICE_ACTION = "app.notifee.core.ForegroundService.STOP";

  static Intent createIntent(Bundle notificationBundle) {
    Intent intent = new Intent(ContextHolder.getApplicationContext(), ForegroundService.class);
    intent.setAction(START_FOREGROUND_SERVICE_ACTION);
    intent.putExtra("notification", notificationBundle);

    return intent;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
