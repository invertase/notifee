package app.notifee.core.utility;

import static app.notifee.core.ContextHolder.getApplicationContext;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import app.notifee.core.ContextHolder;
import app.notifee.core.Logger;

public class AlarmUtils {
  private static final String TAG = "AlarmUtils";

  public static AlarmManager getAlarmManager() {
    return (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
  }

  /**
   * Attempts to open the device's alarm special access settings
   *
   * @param activity
   */
  public static void openAlarmPermissionSettings(Activity activity) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
      return;
    }

    try {
      Intent intent = new Intent();
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      intent.setAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
      Context context = ContextHolder.getApplicationContext();
      String packageName = context.getPackageName();
      intent.setData(Uri.parse("package:" + packageName));

      IntentUtils.startActivityOnUiThread(activity, intent);
    } catch (Exception e) {
      Logger.e(TAG, "An error occurred whilst trying to open alarm permission settings", e);
    }
  }

  public static boolean canScheduleExactAlarms() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      return getAlarmManager().canScheduleExactAlarms();
    }
    return true;
  }
}
