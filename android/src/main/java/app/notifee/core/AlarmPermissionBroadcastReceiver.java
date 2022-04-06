package app.notifee.core;

import static android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmPermissionBroadcastReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {

    if (intent.getAction().equals(ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED)) {
      Log.i("AlarmPermissionReceiver", "Received alarm permission state changed event");
      new NotifeeAlarmManager().rescheduleNotifications();
    }
  }
}
