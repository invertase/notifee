package app.notifee.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/*
 * This is invoked when the phone restarts to ensure that all notifications created by the alarm manager
 * are rescheduled correctly, as Android removes all scheduled alarms when the phone shuts down.
 */
public class RebootBroadcastReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Log.i("RebootReceiver", "Received reboot event");
    new NotifeeAlarmManager().rescheduleNotifications();
  }
}
