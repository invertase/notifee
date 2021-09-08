package app.notifee.core.model;

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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Objects;

@Keep
public class NotificationAndroidPressActionModel {

  private Bundle mNotificationAndroidPressActionBundle;

  private NotificationAndroidPressActionModel(Bundle pressActionBundle) {
    mNotificationAndroidPressActionBundle = pressActionBundle;
  }

  public static NotificationAndroidPressActionModel fromBundle(Bundle pressActionBundle) {
    return new NotificationAndroidPressActionModel(pressActionBundle);
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidPressActionBundle.clone();
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mNotificationAndroidPressActionBundle.getString("id"));
  }

  public @Nullable String getLaunchActivity() {
    return mNotificationAndroidPressActionBundle.getString("launchActivity");
  }

  public int getLaunchActivityFlags() {
    if (!mNotificationAndroidPressActionBundle.containsKey("launchActivityFlags")) {
      return -1;
    }

    int baseFlags = 0;
    ArrayList<Integer> launchActivityFlags =
        Objects.requireNonNull(
            mNotificationAndroidPressActionBundle.getIntegerArrayList("launchActivityFlags"));

    for (int i = 0; i < launchActivityFlags.size(); i++) {
      Integer flag = launchActivityFlags.get(i);
      switch (flag) {
        case 0:
          baseFlags |= Intent.FLAG_ACTIVITY_NO_HISTORY;
          break;
        case 1:
          baseFlags |= Intent.FLAG_ACTIVITY_SINGLE_TOP;
          break;
        case 2:
          baseFlags |= Intent.FLAG_ACTIVITY_NEW_TASK;
          break;
        case 3:
          baseFlags |= Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
          break;
        case 4:
          baseFlags |= Intent.FLAG_ACTIVITY_CLEAR_TOP;
          break;
        case 5:
          baseFlags |= Intent.FLAG_ACTIVITY_FORWARD_RESULT;
          break;
        case 6:
          baseFlags |= Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;
          break;
        case 7:
          baseFlags |= Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
          break;
        case 8:
          baseFlags |= Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
          break;
        case 9:
          baseFlags |= Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;
          break;
        case 10:
          baseFlags |= Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY;
          break;
        case 11:
          baseFlags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
          break;
        case 12:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            baseFlags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
          }
          break;
        case 13:
          baseFlags |= Intent.FLAG_ACTIVITY_NO_USER_ACTION;
          break;
        case 14:
          baseFlags |= Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
          break;
        case 15:
          baseFlags |= Intent.FLAG_ACTIVITY_NO_ANIMATION;
          break;
        case 16:
          baseFlags |= Intent.FLAG_ACTIVITY_CLEAR_TASK;
          break;
        case 17:
          baseFlags |= Intent.FLAG_ACTIVITY_TASK_ON_HOME;
          break;
        case 18:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            baseFlags |= Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS;
          }
          break;
        case 19:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseFlags |= Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT;
          }
          break;
        case 20:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            baseFlags |= Intent.FLAG_ACTIVITY_MATCH_EXTERNAL;
          }
          break;
      }
    }
    return baseFlags;
  }

  public @Nullable String getMainComponent() {
    return mNotificationAndroidPressActionBundle.getString("mainComponent");
  }
}
