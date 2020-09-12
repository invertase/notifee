package app.notifee.core.utility;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import app.notifee.core.ContextHolder;

public class PowerSaveUtils {
  private static final String HUAWEI_SMART_MODE_STATUS = "SmartModeStatus";
  private static final int HUAWEI_SMART_MODE_STATUS_ON = 4;
  private static final String HUAWEI_MANUFACTURE = "Huawei";

  @RequiresApi(api = Build.VERSION_CODES.M)
  public static Boolean openPowerOptimizationSettings(Activity activity) {
    try {
      Intent intent = new Intent();
      intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      activity.runOnUiThread(() -> ContextHolder.getApplicationContext().startActivity(intent));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return false;
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  @SuppressLint("BatteryLife")
  public static Boolean requestDisablePowerOptimizations() {
    if (isPowerOptimizationsDisabled(ContextHolder.getApplicationContext())) {
      return true;
    }

    try {
      if (!isPowerOptimizationsDisabled(ContextHolder.getApplicationContext())) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(
            Uri.parse("package:" + ContextHolder.getApplicationContext().getPackageName()));
        ContextHolder.getApplicationContext().startActivity(intent);
        return true;
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return false;
  }

  @RequiresApi(api = Build.VERSION_CODES.M)
  public static Boolean isPowerOptimizationsDisabled(Context context) {
    PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
    return pm.isIgnoringBatteryOptimizations(context.getPackageName());
  }

  public static Boolean isPowerSaveMode(Context context) {
    if (Build.MANUFACTURER.equalsIgnoreCase(HUAWEI_MANUFACTURE)) {
      return isPowerSaveModeHuawei(context);
    } else {
      return isPowerSaveModeAndroid(context);
    }
  }

  public static Boolean openPowerModeSettings(Activity activity) {
    try {
      Intent intent = new Intent();
      intent.setAction(Settings.ACTION_BATTERY_SAVER_SETTINGS);
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);

      activity.runOnUiThread(() -> ContextHolder.getApplicationContext().startActivity(intent));
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return false;
  }

  @TargetApi(21)
  private static Boolean isPowerSaveModeAndroid(Context context) {
    boolean isPowerSaveMode = false;
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
      if (pm != null) isPowerSaveMode = pm.isPowerSaveMode();
    }
    return isPowerSaveMode;
  }

  private static Boolean isPowerSaveModeHuawei(Context context) {
    try {
      int value =
          android.provider.Settings.System.getInt(
              context.getContentResolver(), HUAWEI_SMART_MODE_STATUS);
      return (value == HUAWEI_SMART_MODE_STATUS_ON);
    } catch (Settings.SettingNotFoundException e) {
      return isPowerSaveModeAndroid(context);
    }
  }
}
