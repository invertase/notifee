package app.notifee.core.utility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* Based on the Android library AutoStarter (https://github.com/judemanutd/AutoStarter)
 * Utility class to bring up the autostart permission manager of a phone to the user so they can add an app to autostart.
 */
public class AutoStartPermissionUtils {
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
  private static final String PACKAGE_ASUS_COMPONENT =
      "com.asus.mobilemanager.powersaver.PowerSaverSettings";
  private static final String PACKAGE_ASUS_COMPONENT_FALLBACK =
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
  private static final String PACKAGE_HUAWEI_COMPONENT =
      "com.huawei.systemmanager.optimize.process.ProtectActivity";
  private static final String PACKAGE_HUAWEI_COMPONENT_FALLBACK =
      "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity";

  /** Oppo */
  private static final String BRAND_OPPO = "oppo";

  private static final String PACKAGE_OPPO_MAIN = "com.coloros.safecenter";
  private static final String PACKAGE_OPPO_FALLBACK = "com.oppo.safe";
  private static final String PACKAGE_OPPO_COMPONENT =
      "com.coloros.safecenter.permission.startup.StartupAppListActivity";
  private static final String PACKAGE_OPPO_COMPONENT_FALLBACK =
      "com.oppo.safe.permission.startup.StartupAppListActivity";
  private static final String PACKAGE_OPPO_COMPONENT_FALLBACK_A =
      "com.coloros.safecenter.startupapp.StartupAppListActivity";

  /** Vivo */
  private static final String BRAND_VIVO = "vivo";

  private static final String PACKAGE_VIVO_MAIN = "com.iqoo.secure";
  private static final String PACKAGE_VIVO_FALLBACK = "com.vivo.permissionmanager";
  private static final String PACKAGE_VIVO_COMPONENT =
      "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity";
  private static final String PACKAGE_VIVO_COMPONENT_FALLBACK =
      "com.vivo.permissionmanager.activity.BgStartUpManagerActivity";
  private static final String PACKAGE_VIVO_COMPONENT_FALLBACK_A =
      "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager";

  /** Nokia */
  private static final String BRAND_NOKIA = "nokia";

  private static final String PACKAGE_NOKIA_MAIN = "com.evenwell.powersaving.g3";
  private static final String PACKAGE_NOKIA_COMPONENT =
      "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity";

  /***
   * Samsung
   */
  private static final String BRAND_SAMSUNG = "samsung";

  private static final String PACKAGE_SAMSUNG_MAIN = "com.samsung.android.lool";
  private static final String PACKAGE_SAMSUNG_COMPONENT =
      "com.samsung.android.sm.ui.battery.BatteryActivity";

  /***
   * One plus
   */
  private static final String BRAND_ONE_PLUS = "oneplus";

  private static final String PACKAGE_ONE_PLUS_MAIN = "com.oneplus.security";
  private static final String PACKAGE_ONE_PLUS_COMPONENT =
      "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity";

  private static List<String> mPossiblePackages =
      Arrays.asList(
          PACKAGE_ASUS_MAIN,
          PACKAGE_XIAOMI_MAIN,
          PACKAGE_LETV_MAIN,
          PACKAGE_HONOR_MAIN,
          PACKAGE_OPPO_MAIN,
          PACKAGE_OPPO_FALLBACK,
          PACKAGE_VIVO_MAIN,
          PACKAGE_VIVO_FALLBACK,
          PACKAGE_NOKIA_MAIN,
          PACKAGE_HUAWEI_MAIN,
          PACKAGE_SAMSUNG_MAIN,
          PACKAGE_ONE_PLUS_MAIN);

  public static Boolean isAutoStartPermissionAvailable(Context context) {
    List<ApplicationInfo> packages;
    PackageManager pm = context.getPackageManager();
    packages = pm.getInstalledApplications(0);
    for (int i = 0; i < packages.size(); i++) {
      String packageName = packages.get(i).packageName;
      if (mPossiblePackages.contains(packageName)) {
        return true;
      }
    }

    return false;
  }

  private static Boolean attemptGetAutoStartPermission(
      AutoStartPermissionManufacture manufacture, Context context) {
    String mainPackage = manufacture.mainPackage;
    if (!isPackageExists(context, mainPackage)) {
      return false;
    }

    for (int i = 0; i < manufacture.componentPackages.size(); i++) {
      String componentPackage = manufacture.componentPackages.get(i);
      try {
        startIntent(mainPackage, componentPackage, context);

        // Intent was a success, return result
        return true;
      } catch (Exception e) {
        e.printStackTrace();
        // Continue to attempt any fallback component packages
        continue;
      }
    }

    // Failed to get auto start permission
    return false;
  }

  public static Boolean getAutoStartPermission(Context context) {
    String manufactureName = Build.BRAND.toLowerCase(Locale.getDefault());
    AutoStartPermissionManufacture manufacture = null;
    AutoStartPermissionManufacture fallBackManufacture = null;
    switch (manufactureName) {
      case BRAND_ASUS:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_ASUS,
                PACKAGE_ASUS_MAIN,
                Arrays.asList(PACKAGE_ASUS_COMPONENT, PACKAGE_ASUS_COMPONENT_FALLBACK));
        break;
      case BRAND_XIAOMI:
      case BRAND_XIAOMI_REDMI:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_XIAOMI, PACKAGE_XIAOMI_MAIN, Arrays.asList(PACKAGE_XIAOMI_COMPONENT));
        break;
      case BRAND_LETV:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_LETV, PACKAGE_ASUS_MAIN, Arrays.asList(PACKAGE_LETV_COMPONENT));
        break;
      case BRAND_HONOR:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_HONOR, PACKAGE_HONOR_MAIN, Arrays.asList(PACKAGE_HONOR_COMPONENT));
        break;
      case BRAND_HUAWEI:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_HUAWEI,
                PACKAGE_HUAWEI_MAIN,
                Arrays.asList(PACKAGE_HUAWEI_COMPONENT, PACKAGE_HUAWEI_COMPONENT_FALLBACK));
        break;
      case BRAND_OPPO:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_OPPO,
                PACKAGE_OPPO_MAIN,
                Arrays.asList(PACKAGE_OPPO_COMPONENT, PACKAGE_OPPO_COMPONENT_FALLBACK_A));
        fallBackManufacture =
            new AutoStartPermissionManufacture(
                BRAND_OPPO, PACKAGE_OPPO_FALLBACK, Arrays.asList(PACKAGE_OPPO_COMPONENT_FALLBACK));
        break;
      case BRAND_VIVO:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_VIVO,
                PACKAGE_VIVO_MAIN,
                Arrays.asList(PACKAGE_VIVO_COMPONENT, PACKAGE_VIVO_COMPONENT_FALLBACK_A));
        fallBackManufacture =
            new AutoStartPermissionManufacture(
                BRAND_VIVO, PACKAGE_VIVO_FALLBACK, Arrays.asList(PACKAGE_VIVO_COMPONENT_FALLBACK));
        break;
      case BRAND_NOKIA:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_NOKIA, PACKAGE_NOKIA_MAIN, Arrays.asList(PACKAGE_NOKIA_COMPONENT));
        break;
      case BRAND_SAMSUNG:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_SAMSUNG, PACKAGE_SAMSUNG_MAIN, Arrays.asList(PACKAGE_SAMSUNG_COMPONENT));
        break;
      case BRAND_ONE_PLUS:
        manufacture =
            new AutoStartPermissionManufacture(
                BRAND_ONE_PLUS, PACKAGE_ONE_PLUS_MAIN, Arrays.asList(PACKAGE_ONE_PLUS_COMPONENT));
        break;
      default:
        break;
    }

    if (manufacture == null) {
      return false;
    }

    Boolean isSuccess = attemptGetAutoStartPermission(manufacture, context);
    if (!isSuccess && fallBackManufacture != null) {
      isSuccess = attemptGetAutoStartPermission(fallBackManufacture, context);
    }
    return isSuccess;
  }

  private static void startIntent(String packageName, String componentName, Context context) {
    try {
      Intent intent = new Intent();
      intent.setComponent(new ComponentName(packageName, componentName));
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent);
    } catch (Exception exception) {
      exception.printStackTrace();
      throw exception;
    }
  }

  private static Boolean isPackageExists(Context context, String targetPackage) {
    List<ApplicationInfo> packages;
    PackageManager pm = context.getPackageManager();
    packages = pm.getInstalledApplications(0);

    for (int i = 0; i < packages.size(); i++) {
      String packageName = packages.get(i).packageName;
      if (packageName.equals(targetPackage)) {

        return true;
      }
    }
    return false;
  }

  private static class AutoStartPermissionManufacture {
    String name;
    String mainPackage;
    List<String> componentPackages;

    AutoStartPermissionManufacture(
        String name, String mainPackage, List<String> componentPackages) {
      this.name = name;
      this.mainPackage = mainPackage;
      this.componentPackages = componentPackages;
    }
  }
}
