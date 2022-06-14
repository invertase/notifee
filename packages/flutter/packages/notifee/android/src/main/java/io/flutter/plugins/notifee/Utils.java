package io.flutter.plugins.notifee;

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
import android.os.Bundle;
import app.notifee.core.ContextHolder;
import java.util.List;
import java.util.Map;

public class Utils {
  public static Bundle mapToBundle(Map<String, Object> map) {
    Bundle bundle = new Bundle();

    for (Map.Entry<String, Object> entry : map.entrySet()) {
      Object value = entry.getValue();
      if (value instanceof Map<?, ?>) {
        bundle.putBundle(entry.getKey(), mapToBundle((Map<String, Object>) value));
      } else if (value instanceof List<?>) {
        // TODO: check if this is needed
        //          value = listToJson((List<Object>) value);
      } else if (value instanceof Boolean) {
        bundle.putBoolean(entry.getKey(), (Boolean) value);
      } else if (value instanceof String) {
        bundle.putString(entry.getKey(), (String) value);
      } else if (value instanceof Double) {
        bundle.putDouble(entry.getKey(), (Double) value);
      } else if (value instanceof Long) {
        bundle.putLong(entry.getKey(), (Long) value);
      } else if (value instanceof Number) {
        bundle.putInt(entry.getKey(), (int) value);
      }
    }

    return bundle;
  }

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
