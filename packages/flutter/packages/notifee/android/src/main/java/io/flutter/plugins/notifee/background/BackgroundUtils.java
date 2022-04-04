package io.flutter.plugins.notifee.background;

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

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import app.notifee.core.ContextHolder;
import java.util.List;

public class BackgroundUtils {
  public static final String SHARED_PREFERENCES_KEY = "io.flutter.notifee.callback";
  public static final String EXTRA_NOTIFICATION_EVENT_NOTIFICATION =
      "io.flutter.plugins.notifee.NOTIFICATION_EVENT_NOTIFICATION";
  public static final String EXTRA_NOTIFICATION_EVENT_TYPE =
      "io.flutter.plugins.notifee.NOTIFICATION_EVENT_TYPE";
  public static final String EXTRA_NOTIFICATION_EVENT_EXTRAS =
      "io.flutter.plugins.notifee.NOTIFICATION_EVENT_EXTRAS";
  static final int JOB_ID = 2022;

  /**
   * Identify if the application is currently in a state where user interaction is possible. This
   * method is called when a remote message is received to determine how the incoming message should
   * be handled.
   *
   * @return True if the application is currently in a state where user interaction is possible,
   *     false otherwise.
   */
  static boolean isApplicationForeground() {
    KeyguardManager keyguardManager =
        (KeyguardManager)
            ContextHolder.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);

    if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
      return false;
    }

    ActivityManager activityManager =
        (ActivityManager)
            ContextHolder.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
    if (activityManager == null) return false;

    List<ActivityManager.RunningAppProcessInfo> appProcesses =
        activityManager.getRunningAppProcesses();
    if (appProcesses == null) return false;

    final String packageName = ContextHolder.getApplicationContext().getPackageName();
    for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
      if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
          && appProcess.processName.equals(packageName)) {
        return true;
      }
    }

    return false;
  }
}
