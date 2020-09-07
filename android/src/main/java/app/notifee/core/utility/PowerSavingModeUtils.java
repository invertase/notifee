package app.notifee.core.utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

/*
 * Utility class to see if the user has power save mode enabled
 */
public class PowerSavingModeUtils {
  private static final String HUAWEI_SMART_MODE_STATUS = "SmartModeStatus";
  private static final int HUAWEI_SMART_MODE_STATUS_ON = 4;
  private static final String HUAWEI_MANUFACTURE = "Huawei";

  public static Boolean isPowerSavingMode(Context context) {
    if (Build.MANUFACTURER.equalsIgnoreCase(HUAWEI_MANUFACTURE)) {
      return isPowerSavingModeHuawei(context);
    } else {
      return isPowerSavingModeAndroid(context);
    }
  }

  @TargetApi(21)
  private static Boolean isPowerSavingModeAndroid(Context context) {
    boolean isPowerSaveMode = false;
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
      if (pm != null) isPowerSaveMode = pm.isPowerSaveMode();
    }
    return isPowerSaveMode;
  }

  private static Boolean isPowerSavingModeHuawei(Context context) {
    try {
      int value =
          android.provider.Settings.System.getInt(
              context.getContentResolver(), HUAWEI_SMART_MODE_STATUS);
      return (value == HUAWEI_SMART_MODE_STATUS_ON);
    } catch (Settings.SettingNotFoundException e) {
      return isPowerSavingModeAndroid(context);
    }
  }
}
