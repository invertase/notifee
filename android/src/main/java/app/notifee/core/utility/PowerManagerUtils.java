package app.notifee.core.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import app.notifee.core.ContextHolder;
import app.notifee.core.Logger;

public class PowerManagerUtils {
  private static final String TAG = "PowerManagerUtils";

  public static void openBatteryOptimizationSettings(Activity activity) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return;
    }

    try {
      Intent intent = new Intent();
      intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

      // check activity exists on device
      if (activity != null) {
        Boolean isAvailableOnDevice =
          IntentUtils.isAvailableOnDevice(ContextHolder.getApplicationContext(), intent);
        if (!isAvailableOnDevice) {
          return;
        }

        IntentUtils.startActivityOnUiThread(activity, intent);
      }
    } catch (Exception e) {
      Logger.e(TAG, "An error occurred whilst trying to open battery optimization settings", e);
    }
  }


  public static Boolean isBatteryOptimizationEnabled(Context context) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return false;
    }
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    return !pm.isIgnoringBatteryOptimizations(context.getPackageName());
  }
}
