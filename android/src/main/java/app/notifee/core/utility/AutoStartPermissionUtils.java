package app.notifee.core.utility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* Based on the Android library AutoStarter (https://github.com/judemanutd/AutoStarter)
 * Utility class to bring up the autostart permission manager of a phone to the user so they can add an app to autostart.
 */
public class AutoStartPermissionUtils {
  private static final String TAG = "AutoStartPermUtils";

  /***
   * Xiaomi
   */
  private static final String BRAND_XIAOMI = "xiaomi";

  private static final String BRAND_XIAOMI_REDMI = "redmi";
  private static final String PACKAGE_XIAOMI_MAIN = "com.miui.securitycenter";
  private static final String PACKAGE_XIAOMI_COMPONENT =
    "com.miui.permcenter.autostart.AutoStartManagementActivity";

  /***
   * Letv
   */
  private static final String BRAND_LETV = "letv";

  private static final String PACKAGE_LETV_MAIN = "com.letv.android.letvsafe";
  private static final String PACKAGE_LETV_COMPONENT =
    "com.letv.android.letvsafe.AutobootManageActivity";

  /***
   * ASUS ROG
   */
  private static final String BRAND_ASUS = "asus";

  private static final String PACKAGE_ASUS_MAIN = "com.asus.mobilemanager";
  private static final String PACKAGE_ASUS_COMPONENT_A =
    "com.asus.mobilemanager.powersaver.PowerSaverSettings";
  private static final String PACKAGE_ASUS_COMPONENT_B =
    "com.asus.mobilemanager.autostart.AutoStartActivity";

  /***
   * Honor
   */
  private static final String BRAND_HONOR = "honor";

  private static final String PACKAGE_HONOR_MAIN = "com.huawei.systemmanager";
  private static final String PACKAGE_HONOR_COMPONENT =
    "com.huawei.systemmanager.optimize.process.ProtectActivity";

  /***
   * Huawei
   */
  private static final String BRAND_HUAWEI = "huawei";

  private static final String PACKAGE_HUAWEI_MAIN = "com.huawei.systemmanager";
  private static final String PACKAGE_HUAWEI_COMPONENT_A =
    "com.huawei.systemmanager.optimize.process.ProtectActivity";
  private static final String PACKAGE_HUAWEI_COMPONENT_B =
    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";

  /** Oppo */
  private static final String BRAND_OPPO = "oppo";

  private static final String PACKAGE_OPPO_MAIN = "com.coloros.safecenter";
  private static final String PACKAGE_OPPO_FALLBACK = "com.oppo.safe";
  private static final String PACKAGE_OPPO_COMPONENT_A =
    "com.coloros.safecenter.permission.startup.StartupAppListActivity";
  private static final String PACKAGE_OPPO_FALLBACK_COMPONENT =
    "com.oppo.safe.permission.startup.StartupAppListActivity";
  private static final String PACKAGE_OPPO_COMPONENT_B =
    "com.coloros.safecenter.startupapp.StartupAppListActivity";

  /** Vivo */
  private static final String BRAND_VIVO = "vivo";

  private static final String PACKAGE_VIVO_MAIN = "com.iqoo.secure";
  private static final String PACKAGE_VIVO_FALLBACK = "com.vivo.permissionmanager";
  private static final String PACKAGE_VIVO_COMPONENT_A =
    "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity";
  private static final String PACKAGE_VIVO_FALLBACK_COMPONENT =
    "com.vivo.permissionmanager.activity.BgStartUpManagerActivity";
  private static final String PACKAGE_VIVO_COMPONENT_B =
    "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager";

  /** Nokia */
  private static final String BRAND_NOKIA = "nokia";

  private static final String PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3";
  private static final String PACKAGE_NOKIA_COMPONENT =
    "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity";

  /***
   * One plus
   */
  private static final String BRAND_ONE_PLUS = "oneplus";

  private static final String PACKAGE_ONE_PLUS_MAIN = "com.oneplus.security";
  private static final String PACKAGE_ONE_PLUS_COMPONENT =
    "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity";

  public static Boolean getAutoStartPermission(Context context) {
    Intent intent = attemptGetIntent(context);
    if (intent == null) return false;

    return startIntent(intent, context);
  }

  public static Boolean isAutoStartPermissionAvailable(Context context) {
    Intent intent = attemptGetIntent(context);
    return intent != null;
  }

  public static Intent attemptGetIntent(Context context) {
    String manufactureName = Build.BRAND.toLowerCase(Locale.getDefault());
    AutoStartPermissionManufacture manufacture = null;

    switch (manufactureName) {
      case BRAND_ASUS:
        manufacture =
          new AutoStartPermissionManufacture(
            BRAND_ASUS,
            Arrays.asList(
              createIntent(PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT_A),
              createIntent(PACKAGE_ASUS_MAIN, PACKAGE_ASUS_COMPONENT_B)));
        break;
      case BRAND_XIAOMI:
      case BRAND_XIAOMI_REDMI:
        manufacture = new AutoStartPermissionManufacture(
        BRAND_XIAOMI,
          Arrays.asList(createIntent(PACKAGE_XIAOMI_MAIN, PACKAGE_XIAOMI_COMPONENT)));
        break;
      case BRAND_LETV:
        manufacture =
          new AutoStartPermissionManufacture(
            BRAND_LETV, Arrays.asList(createIntent(PACKAGE_LETV_MAIN, PACKAGE_LETV_COMPONENT)));
        break;
      case BRAND_HONOR:
        manufacture =
          new AutoStartPermissionManufacture(
            BRAND_HONOR, Arrays.asList(createIntent(PACKAGE_HONOR_MAIN, PACKAGE_HONOR_COMPONENT)));
        break;
      case BRAND_HUAWEI:
        manufacture =
          new AutoStartPermissionManufacture(
            BRAND_HUAWEI, Arrays.asList(
              createIntent(PACKAGE_HUAWEI_MAIN, PACKAGE_HUAWEI_COMPONENT_A),
              createIntent( PACKAGE_HUAWEI_MAIN,PACKAGE_HUAWEI_COMPONENT_B)));
        break;
      case BRAND_OPPO:
        manufacture =
          new AutoStartPermissionManufacture(
            BRAND_OPPO, Arrays.asList(
              createIntent(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_A),
              createIntent(PACKAGE_OPPO_MAIN, PACKAGE_OPPO_COMPONENT_B),
              createIntent(PACKAGE_OPPO_FALLBACK, PACKAGE_OPPO_FALLBACK_COMPONENT)
            ));
        break;
      case BRAND_VIVO:
        manufacture = new AutoStartPermissionManufacture(
          BRAND_VIVO, Arrays.asList(
            createIntent(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_A),
            createIntent(PACKAGE_VIVO_MAIN, PACKAGE_VIVO_COMPONENT_B),
            createIntent(PACKAGE_VIVO_FALLBACK, PACKAGE_VIVO_FALLBACK_COMPONENT)
        ));
        break;
      case BRAND_NOKIA:
        manufacture =
          new AutoStartPermissionManufacture(
            BRAND_NOKIA,
            Arrays.asList(createIntent(PACKAGE_NOKIA_MAIN, PACKAGE_NOKIA_COMPONENT)));
        break;
      case BRAND_ONE_PLUS:
        manufacture =
          new AutoStartPermissionManufacture(BRAND_ONE_PLUS,
            Arrays.asList(createIntent(PACKAGE_ONE_PLUS_MAIN, PACKAGE_ONE_PLUS_COMPONENT)));
        break;
      default:
        break;
    }

    if (manufacture == null) {
      return null;
    }

    return attemptGetIntentForManufacture(context, manufacture);
  }


  public static Intent createIntent(String pkg, String cls) {
    Intent intent = new Intent();
    intent.setComponent(new ComponentName(pkg, cls));
    return intent;
  }

  public static Intent attemptGetIntentForManufacture(Context context, AutoStartPermissionManufacture manufacture ) {
    PackageManager pm = context.getPackageManager();

    for (int i = 0; i < manufacture.possibleIntents.size(); i++) {
      Intent possibleIntent = manufacture.possibleIntents.get(i);

      List<ResolveInfo> list = pm.queryIntentActivities(possibleIntent,
        PackageManager.MATCH_DEFAULT_ONLY);
      if (list.size() > 0) {
        return possibleIntent;
      }

      return null;
    }

    return null;
  }

  private static Boolean startIntent(Intent intent, Context context) {
    if (intent == null) return false;

    try {
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
      return true;
    } catch (Exception exception) {
      Log.d(TAG, "Unable to start intent: " + exception.getMessage());
      return false;
    }
  }

  private static class AutoStartPermissionManufacture {
    String name;
    List<Intent> possibleIntents;

    AutoStartPermissionManufacture(
      String name, List<Intent> possibleIntents) {
      this.name = name;
      this.possibleIntents = possibleIntents;
    }
  }
}
