package app.notifee.core.utility;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import static app.notifee.core.ContextHolder.getApplicationContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.annotation.Nullable;
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

    Context ctx = getApplicationContext();
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

  public static Class<?> getLaunchActivity(@Nullable String launchActivity) {
    String activity;

    if (launchActivity != null && !launchActivity.equals("default")) {
      activity = launchActivity;
    } else {
      activity = getMainActivityClassName();
    }

    if (activity == null) {
      Logger.e("ReceiverService", "Launch Activity for notification could not be found.");
      return null;
    }

    Class<?> launchActivityClass = getClassForName(activity);

    if (launchActivityClass == null) {
      Logger.e(
          "ReceiverService",
          String.format("Launch Activity for notification does not exist ('%s').", launchActivity));
      return null;
    }

    return launchActivityClass;
  }

  private @Nullable static Class<?> getClassForName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  private @Nullable static String getMainActivityClassName() {
    String packageName = getApplicationContext().getPackageName();
    Intent launchIntent =
        getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);

    if (launchIntent == null || launchIntent.getComponent() == null) {
      return null;
    }

    return launchIntent.getComponent().getClassName();
  }
}
