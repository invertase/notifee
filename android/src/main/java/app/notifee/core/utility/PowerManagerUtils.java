package app.notifee.core.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import app.notifee.core.ContextHolder;

public class PowerManagerUtils {
  public static void openBatteryOptimizationSettings(Activity activity) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return;
    }
    Intent intent = new Intent();
    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    activity.runOnUiThread(() -> ContextHolder.getApplicationContext().startActivity(intent));
  }

  public static Boolean isBatteryOptimizationEnabled(Context context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return false;
    }
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    return !pm.isIgnoringBatteryOptimizations(context.getPackageName());
  }
}
