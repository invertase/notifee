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

import android.app.Notification;
import android.content.pm.ServiceInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import app.notifee.core.Logger;
import app.notifee.core.utility.ObjectUtils;
import app.notifee.core.utility.ResourceUtils;
import java.util.ArrayList;
import java.util.Objects;

@Keep
public class NotificationAndroidModel {
  private static final String TAG = "NotificationAndroidModel";
  private Bundle mNotificationAndroidBundle;

  private NotificationAndroidModel(Bundle bundle) {
    mNotificationAndroidBundle = bundle;
  }

  public static NotificationAndroidModel fromBundle(Bundle bundle) {
    return new NotificationAndroidModel(bundle);
  }

  public @Nullable ArrayList<NotificationAndroidActionModel> getActions() {
    if (mNotificationAndroidBundle.containsKey("actions")) {
      ArrayList<Bundle> actionBundles =
          Objects.requireNonNull(mNotificationAndroidBundle.getParcelableArrayList("actions"));
      ArrayList<NotificationAndroidActionModel> actions = new ArrayList<>(actionBundles.size());

      for (Bundle actionBundle : actionBundles) {
        actions.add(NotificationAndroidActionModel.fromBundle(actionBundle));
      }

      return actions;
    }

    return null;
  }

  @RequiresApi(api = Build.VERSION_CODES.Q)
  public int getForegroundServiceType() {
    if (!mNotificationAndroidBundle.containsKey("foregroundServiceTypes")) {
      // no foreground service types provided, so we default to manifest
      return ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST;
    }

    ArrayList<?> foregroundServiceTypesArrayList =
      Objects.requireNonNull(mNotificationAndroidBundle.getParcelableArrayList("foregroundServiceTypes"));

    int foregroundServiceType = 0;
    for (int i = 0; i < foregroundServiceTypesArrayList.size(); i++) {
      foregroundServiceType |= ObjectUtils.getInt(foregroundServiceTypesArrayList.get(i));
    }

    // from Android 14, it is disallowed to use NONE type, so we default to manifest
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && foregroundServiceType == ServiceInfo.FOREGROUND_SERVICE_TYPE_NONE) {
      return ServiceInfo.FOREGROUND_SERVICE_TYPE_MANIFEST;
    }

    return foregroundServiceType;
  }

  /**
   * Gets whether the notification is for a foreground service
   *
   * @return Boolean
   */
  public Boolean getAsForegroundService() {
    return mNotificationAndroidBundle.getBoolean("asForegroundService", false);
  }

  /**
   * Gets if the notification should light up the screen when displayed
   *
   * @return Boolean
   */
  public Boolean getLightUpScreen() {
    return mNotificationAndroidBundle.getBoolean("lightUpScreen", false);
  }

  /**
   * Gets whether the notification can be auto cancelled
   *
   * @return Boolean
   */
  public Boolean getAutoCancel() {
    return mNotificationAndroidBundle.getBoolean("autoCancel", false);
  }

  /**
   * Gets the badge icon type for the notification
   *
   * @return Integer
   */
  public @Nullable Integer getBadgeIconType() {
    if (mNotificationAndroidBundle.containsKey("badgeIconType")) {

      return ObjectUtils.getInt(mNotificationAndroidBundle.get("badgeIconType"));
    }

    return NotificationCompat.BADGE_ICON_LARGE;
  }

  /**
   * Gets the channel ID for the notification, returns an empty string if none is available
   *
   * @return String
   */
  public @NonNull String getChannelId() {
    if (mNotificationAndroidBundle.containsKey("channelId")) {
      return Objects.requireNonNull(mNotificationAndroidBundle.getString("channelId"));
    }

    return "";
  }

  /**
   * Gets the notification category
   *
   * @return String
   */
  public @Nullable String getCategory() {
    return mNotificationAndroidBundle.getString("category");
  }

  /**
   * Gets the parsed notification color
   *
   * @return Integer
   */
  public @Nullable Integer getColor() {
    if (mNotificationAndroidBundle.containsKey("color")) {
      return Color.parseColor(mNotificationAndroidBundle.getString("color"));
    }

    return null;
  }

  /**
   * Gets whether the notification is colorized
   *
   * @return Boolean
   */
  public Boolean getColorized() {
    return mNotificationAndroidBundle.getBoolean("colorized", false);
  }

  /**
   * Gets whether the notification chronometer should count down
   *
   * @return Boolean
   */
  public Boolean getChronometerCountDown() {
    if (mNotificationAndroidBundle.containsKey("chronometerDirection")) {
      String direction = mNotificationAndroidBundle.getString("chronometerDirection");
      return direction != null && direction.equals("down");
    }

    return false;
  }

  /**
   * Gets the notification defaults (for API < 26)
   *
   * @param hasCustomSound A flag to indicate if notificaiton has a custom sound and has successfuly
   *     resolved
   * @return Integer
   */
  public Integer getDefaults(Boolean hasCustomSound) {
    String TAG = "NotificationManager";
    Integer defaults = null;

    if (mNotificationAndroidBundle.containsKey("defaults")) {
      ArrayList<Integer> defaultsArray = mNotificationAndroidBundle.getIntegerArrayList("defaults");

      for (Integer integer : Objects.requireNonNull(defaultsArray)) {
        if (defaults == null) {
          defaults = integer;
        } else {
          defaults |= integer;
        }
      }
    } else {
      defaults = Notification.DEFAULT_ALL;
    }

    if (hasCustomSound) {
      defaults &= ~Notification.DEFAULT_SOUND;
    }

    if (!mNotificationAndroidBundle.containsKey("vibrationPattern")) {
      defaults &= ~Notification.DEFAULT_VIBRATE;
    }

    if (mNotificationAndroidBundle.containsKey("lights")) {
      defaults &= ~Notification.DEFAULT_LIGHTS;
    }

    return defaults;
  }

  /**
   * Gets the notification tag
   *
   * @return String
   */
  public @Nullable String getTag() {
    return mNotificationAndroidBundle.getString("tag");
  }

  /**
   * Gets the notification group key
   *
   * @return String
   */
  public @Nullable String getGroup() {
    return mNotificationAndroidBundle.getString("groupId");
  }

  /**
   * Gets the group alert behaviour for a notification
   *
   * @return int
   */
  public int getGroupAlertBehaviour() {
    if (mNotificationAndroidBundle.containsKey("groupAlertBehavior")) {
      return ObjectUtils.getInt(mNotificationAndroidBundle.get("groupAlertBehavior"));
    }

    return NotificationCompat.GROUP_ALERT_ALL;
  }

  /**
   * Gets whether the notification is the group summary
   *
   * @return Boolean
   */
  public Boolean getGroupSummary() {
    return mNotificationAndroidBundle.getBoolean("groupSummary", false);
  }

  /**
   * Gets the input history of the notification
   *
   * @return CharSequence[]
   */
  public @Nullable CharSequence[] getInputHistory() {
    if (mNotificationAndroidBundle.containsKey("inputHistory")) {
      ArrayList<String> inputHistoryArray =
          mNotificationAndroidBundle.getStringArrayList("inputHistory");
      return Objects.requireNonNull(inputHistoryArray)
          .toArray(new CharSequence[inputHistoryArray.size()]);
    }

    return null;
  }

  /**
   * Returns true if the notification has a large icon field
   *
   * @return Boolean
   */
  public Boolean hasLargeIcon() {
    return mNotificationAndroidBundle.containsKey("largeIcon");
  }

  /** Returns the large icon string */
  public String getLargeIcon() {
    if (hasLargeIcon()) {
      return Objects.requireNonNull(mNotificationAndroidBundle.getString("largeIcon"));
    }

    return null;
  }

  /** Returns the large icon string */
  public Boolean getCircularLargeIcon() {
    return Objects.requireNonNull(
        mNotificationAndroidBundle.getBoolean("circularLargeIcon", false));
  }

  /**
   * Gets the light output for the notification
   *
   * @return ArrayList<Integer>
   */
  public @Nullable ArrayList<Integer> getLights() {
    if (mNotificationAndroidBundle.containsKey("lights")) {
      try {
        ArrayList<?> lightList =
          Objects.requireNonNull(mNotificationAndroidBundle.getParcelableArrayList("lights"));
        String rawColor = (String) lightList.get(0);

        ArrayList<Integer> lights = new ArrayList<>(3);
        lights.add(Color.parseColor(rawColor));
        lights.add((Integer) lightList.get(1));
        lights.add((Integer) lightList.get(2));

        return lights;
      } catch (Exception e) {
        Logger.e(
          TAG,
          "getLights -> Failed to parse lights");
        return null;
      }
    }

    return null;
  }

  /**
   * Gets whether the notification is for local devices only
   *
   * @return Boolean
   */
  public Boolean getLocalOnly() {
    return mNotificationAndroidBundle.getBoolean("localOnly", false);
  }

  /**
   * Gets a custom set number for the device notification count
   *
   * @return Integer
   */
  public Integer getNumber() {
    if (mNotificationAndroidBundle.containsKey("badgeCount")) {
      return ObjectUtils.getInt(mNotificationAndroidBundle.get("badgeCount"));
    }

    return null;
  }

  /**
   * Gets whether this notification is ongoing
   *
   * @return Boolean
   */
  public Boolean getOngoing() {
    return mNotificationAndroidBundle.getBoolean("ongoing", false);
  }

  /**
   * Gets whether this notification should loop the sound
   *
   * @return Boolean
   */
  public Boolean getLoopSound() {
    return mNotificationAndroidBundle.getBoolean("loopSound", false);
  }

  /**
   * Gets an array of flags
   *
   * @return int[]
   */
  public int[] getFlags() {
    if (!mNotificationAndroidBundle.containsKey("flags")) {
      return null;
    }

    ArrayList<?> flagsArrayList =
        Objects.requireNonNull(mNotificationAndroidBundle.getParcelableArrayList("flags"));

    int[] flagsArray = new int[flagsArrayList.size()];

    for (int i = 0; i < flagsArrayList.size(); i++) {
      flagsArray[i] = ObjectUtils.getInt(flagsArrayList.get(i));
    }

    return flagsArray;
  }

  /**
   * Gets whether this notification should only alert once if updated
   *
   * @return Boolean
   */
  public Boolean getOnlyAlertOnce() {
    return mNotificationAndroidBundle.getBoolean("onlyAlertOnce", false);
  }

  /**
   * Returns true if the notification has a fullScreenAction
   *
   * @return Boolean
   */
  public Boolean hasFullScreenAction() {
    return mNotificationAndroidBundle.containsKey("fullScreenAction");
  }

  /**
   * Gets an pressAction bundle for the notification
   *
   * @return Bundle or null
   */
  public @Nullable Bundle getPressAction() {
    return mNotificationAndroidBundle.getBundle("pressAction");
  }

  /**
   * Returns a notification full screen action
   *
   * @return NotificationAndroidFullScreenActionModel
   */
  public @Nullable NotificationAndroidPressActionModel getFullScreenAction() {
    if (!hasFullScreenAction()) {
      return null;
    }

    return NotificationAndroidPressActionModel.fromBundle(
        mNotificationAndroidBundle.getBundle("fullScreenAction"));
  }

  /**
   * JS uses the same API as importance for priority so we dont confuse users. This maps importance
   * to a priority flag.
   *
   * @return int
   */
  public int getPriority() {
    if (!mNotificationAndroidBundle.containsKey("importance")) {
      return NotificationCompat.PRIORITY_DEFAULT;
    }

    int importance = ObjectUtils.getInt(mNotificationAndroidBundle.get("importance"));
    switch (importance) {
      case NotificationManagerCompat.IMPORTANCE_HIGH:
        return NotificationCompat.PRIORITY_HIGH;
      case NotificationManagerCompat.IMPORTANCE_MIN:
        return NotificationCompat.PRIORITY_LOW;
      case NotificationManagerCompat.IMPORTANCE_NONE:
        return NotificationCompat.PRIORITY_MIN;
      case NotificationManagerCompat.IMPORTANCE_DEFAULT:
      default:
        return NotificationCompat.PRIORITY_DEFAULT;
    }
  }

  /**
   * Gets a AndroidProgress class for the current notification
   *
   * @return AndroidProgress
   */
  public @Nullable AndroidProgress getProgress() {
    if (mNotificationAndroidBundle.containsKey("progress")) {
      Bundle progressBundle =
          Objects.requireNonNull(mNotificationAndroidBundle.getBundle("progress"));

      return new AndroidProgress(
          ObjectUtils.getInt(progressBundle.get("max")),
          ObjectUtils.getInt(progressBundle.get("current")),
          progressBundle.getBoolean("indeterminate", false));
    }

    return null;
  }

  /**
   * Gets the shortcut ID for the notification
   *
   * @return String
   */
  public @Nullable String getShortcutId() {
    return mNotificationAndroidBundle.getString("shortcutId");
  }

  /**
   * Gets whether this notification should show the timestamp
   *
   * @return Boolean
   */
  public Boolean getShowTimestamp() {
    return mNotificationAndroidBundle.getBoolean("showTimestamp", false);
  }

  /**
   * Gets the small icon resource id from its string name, or null if the icon is missing from the
   * device.
   */
  public @Nullable Integer getSmallIcon() {
    if (!mNotificationAndroidBundle.containsKey("smallIcon")) {
      return null;
    }

    String rawIcon = mNotificationAndroidBundle.getString("smallIcon");
    int smallIconId = ResourceUtils.getImageResourceId(rawIcon);

    if (smallIconId == 0) {
      Logger.d(
          "NotificationAndroidModel",
          String.format("Notification small icon '%s' could not be found", rawIcon));
      return null;
    }

    return smallIconId;
  }

  /**
   * Gets the small icon & it's level, or null if the icon is missing from the device.
   *
   * @return ArrayList<Integer>
   */
  public @Nullable Integer getSmallIconLevel() {
    if (!mNotificationAndroidBundle.containsKey("smallIconLevel")) {
      return null;
    }

    return mNotificationAndroidBundle.getInt("smallIconLevel");
  }

  /**
   * Gets the sort key
   *
   * @return String
   */
  public @Nullable String getSortKey() {
    return mNotificationAndroidBundle.getString("sortKey");
  }

  /**
   * Returns whether the notification has any styles
   *
   * @return Boolean
   */
  public Boolean hasStyle() {
    return mNotificationAndroidBundle.containsKey("style");
  }

  /**
   * Returns a task containing a notification style
   *
   * @return Task<NotificationCompat.Style>
   */
  public @Nullable NotificationAndroidStyleModel getStyle() {
    if (!hasStyle()) {
      return null;
    }

    return NotificationAndroidStyleModel.fromBundle(mNotificationAndroidBundle.getBundle("style"));
  }

  /**
   * Gets the ticker text
   *
   * @return String
   */
  public @Nullable String getTicker() {
    return mNotificationAndroidBundle.getString("ticker");
  }

  /**
   * Gets the timeout after ms, -1 if none exists
   *
   * @return long
   */
  public @Nullable Long getTimeoutAfter() {
    if (mNotificationAndroidBundle.containsKey("timeoutAfter")) {
      return ObjectUtils.getLong(mNotificationAndroidBundle.get("timeoutAfter"));
    }

    return null;
  }

  /**
   * Gets whether the chronometer should be shown
   *
   * @return Boolean
   */
  public Boolean getShowChronometer() {
    return mNotificationAndroidBundle.getBoolean("showChronometer", false);
  }

  public @Nullable String getSound() {
    if (!mNotificationAndroidBundle.containsKey("sound")) {
      return null;
    }

    return mNotificationAndroidBundle.getString("sound");
  }

  /**
   * Gets an array vibration pattern for the notification, or empty if not provided
   *
   * @return long[]
   */
  public long[] getVibrationPattern() {
    if (!mNotificationAndroidBundle.containsKey("vibrationPattern")) {
      return new long[0];
    }

    ArrayList<?> vibrationPattern =
        Objects.requireNonNull(
            mNotificationAndroidBundle.getParcelableArrayList("vibrationPattern"));

    long[] vibrateArray = new long[vibrationPattern.size()];

    for (int i = 0; i < vibrationPattern.size(); i++) {
      Integer value = (Integer) vibrationPattern.get(i);
      vibrateArray[i] = value.longValue();
    }

    return vibrateArray;
  }

  /**
   * Gets the notification visibility
   *
   * @return int
   */
  public int getVisibility() {
    if (mNotificationAndroidBundle.containsKey("visibility")) {
      return ObjectUtils.getInt(mNotificationAndroidBundle.get("visibility"));
    }

    return NotificationCompat.VISIBILITY_PRIVATE;
  }

  /**
   * Gets the notification timestamp, or -1 if not provided
   *
   * @return long
   */
  public long getTimestamp() {
    if (mNotificationAndroidBundle.containsKey("timestamp")) {
      return ObjectUtils.getLong(mNotificationAndroidBundle.get("timestamp"));
    }

    return -1;
  }

  public static class AndroidProgress {
    int max;
    int current;
    boolean indeterminate;

    AndroidProgress(int max, int current, boolean indeterminate) {
      this.max = max;
      this.current = current;
      this.indeterminate = indeterminate;
    }

    public int getMax() {
      return max;
    }

    public int getCurrent() {
      return current;
    }

    public boolean getIndeterminate() {
      return indeterminate;
    }
  }
}
