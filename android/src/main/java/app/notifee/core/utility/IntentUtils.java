package app.notifee.core.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import app.notifee.core.ContextHolder;
import app.notifee.core.Logger;
import java.util.List;

public class IntentUtils {
  private static final String TAG = "IntentUtils";

  public static boolean isAvailableOnDevice(Context ctx, Intent intent) {
    try {
      if (ctx == null || intent == null) {
        return false;
      }

      final PackageManager mgr = ctx.getPackageManager();
      List<ResolveInfo> list = mgr.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
      return list.size() > 0;
    } catch (Exception e) {
      Logger.e(TAG, "An error occurred whilst trying to check if intent is available on device", e);

      return false;
    }
  }

  public static String getActivityName(Intent intent) {
    if (intent == null) {
      return null;
    }

    try {
      String className = intent.getComponent().getClassName();
      int index = className.lastIndexOf(".");

      if (index != -1) {
        return className.substring(index + 1);
      }

    } catch (Exception e) {
      // noop
    }

    return null;
  }

  public static void startActivityOnUiThread(Activity activity, Intent intent) {
    if (activity == null || intent == null) {
      Logger.w(TAG, "Activity or intent is null when calling startActivityOnUiThread()");
      return;
    }

    Context ctx = ContextHolder.getApplicationContext();
    if (ctx == null) {
      Logger.w(TAG, "Unable to get application context when calling startActivityOnUiThread()");
    }

    activity.runOnUiThread(
        () -> {
          try {
            ctx.startActivity(intent);
          } catch (Exception e) {
            Logger.e(TAG, "An error occurred whilst trying to start activity on Ui Thread", e);
          }
        });
  }
}
