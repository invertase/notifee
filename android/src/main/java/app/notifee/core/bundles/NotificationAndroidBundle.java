package app.notifee.core.bundles;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Objects;

import app.notifee.core.Logger;
import app.notifee.core.utils.ResourceUtils;

@Keep
public class NotificationAndroidBundle {
  private Bundle mNotificationAndroidBundle;

  private NotificationAndroidBundle(Bundle bundle) {
    mNotificationAndroidBundle = bundle;
  }

  public static NotificationAndroidBundle fromBundle(Bundle bundle) {
    return new NotificationAndroidBundle(bundle);
  }

  public @Nullable
  ArrayList<NotificationAndroidActionBundle> getActions() {
    if (mNotificationAndroidBundle.containsKey("actions")) {
      ArrayList<Bundle> actionBundles = Objects
        .requireNonNull(mNotificationAndroidBundle.getParcelableArrayList("actions"));
      ArrayList<NotificationAndroidActionBundle> actions = new ArrayList<>(actionBundles.size());

      for (Bundle actionBundle : actionBundles) {
        actions.add(NotificationAndroidActionBundle.fromBundle(actionBundle));
      }

      return actions;
    }

    return null;
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
  public @Nullable
  Integer getBadgeIconType() {
    if (mNotificationAndroidBundle.containsKey("badgeIconType")) {
      return (int) mNotificationAndroidBundle.getDouble("badgeIconType");
    }

    return NotificationCompat.BADGE_ICON_LARGE;
  }

  /**
   * Gets the channel ID for the notification, returns an empty string if none is available
   *
   * @return String
   */
  public @NonNull
  String getChannelId() {
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
  public @Nullable
  String getCategory() {
    return mNotificationAndroidBundle.getString("category");
  }

  /**
   * Gets the parsed notification color
   *
   * @return Integer
   */
  public @Nullable
  Integer getColor() {
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
   * @return Integer
   */
  public Integer getDefaults() {
    if (mNotificationAndroidBundle.containsKey("defaults")) {
      ArrayList<Integer> defaultsArray = mNotificationAndroidBundle.getIntegerArrayList("defaults");
      Integer defaults = null;

      for (Integer integer : Objects.requireNonNull(defaultsArray)) {
        if (defaults == null) {
          defaults = integer;
        } else {
          defaults |= integer;
        }
      }

      if (defaults != null) return defaults;
    }

    return Notification.DEFAULT_ALL;
  }

  /**
   * Gets the notification group key
   *
   * @return String
   */
  public @Nullable
  String getGroup() {
    return mNotificationAndroidBundle.getString("groupId");
  }

  /**
   * Gets the group alert behaviour for a notification
   *
   * @return int
   */
  public int getGroupAlertBehaviour() {
    if (mNotificationAndroidBundle.containsKey("groupAlertBehavior")) {
      return (int) mNotificationAndroidBundle.getDouble("groupAlertBehavior");
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
  public @Nullable
  CharSequence[] getInputHistory() {
    if (mNotificationAndroidBundle.containsKey("inputHistory")) {
      ArrayList<String> inputHistoryArray = mNotificationAndroidBundle
        .getStringArrayList("inputHistory");
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

  /**
   * Returns the large icon string
   */
  public String getLargeIcon() {
    if (hasLargeIcon()) {
      return Objects.requireNonNull(mNotificationAndroidBundle.getString("largeIcon"));
    }

    return null;
  }

  /**
   * Gets the light output for the notification
   *
   * @return ArrayList<Integer>
   */
  public @Nullable
  ArrayList<Integer> getLights() {
    if (mNotificationAndroidBundle.containsKey("lights")) {
      ArrayList lightList = Objects
        .requireNonNull(mNotificationAndroidBundle.getIntegerArrayList("lights"));
      String rawColor = (String) lightList.get(0);

      ArrayList<Integer> lights = new ArrayList<>(3);
      lights.add(Color.parseColor(rawColor));
      lights.add((int) lightList.get(1));
      lights.add((int) lightList.get(2));

      return lights;
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
      return (int) mNotificationAndroidBundle.getDouble("badgeCount");
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
   * Gets whether this notification should only alert once if updated
   *
   * @return Boolean
   */
  public Boolean getOnlyAlertOnce() {
    return mNotificationAndroidBundle.getBoolean("onlyAlertOnce", false);
  }

  /**
   * Gets an pressAction bundle for the notification
   *
   * @return Bundle or null
   */
  public @Nullable
  Bundle getPressAction() {
    return mNotificationAndroidBundle.getBundle("pressAction");
  }

  /**
   * JS uses the same API as importance for priority so we dont confuse users.
   * This maps importance to a priority flag.
   *
   * @return int
   */
  public int getPriority() {
    if (!mNotificationAndroidBundle.containsKey("importance")) {
      return NotificationCompat.PRIORITY_DEFAULT;
    }

    int importance = (int) mNotificationAndroidBundle.getDouble("importance");
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
  public @Nullable
  AndroidProgress getProgress() {
    if (mNotificationAndroidBundle.containsKey("progress")) {
      Bundle progressBundle = Objects
        .requireNonNull(mNotificationAndroidBundle.getBundle("progress"));

      return new AndroidProgress((int) progressBundle.getDouble("max"),
        (int) progressBundle.getDouble("current"), progressBundle.getBoolean("indeterminate", false)
      );
    }

    return null;
  }

  /**
   * Gets the shortcut ID for the notification
   *
   * @return String
   */
  public @Nullable
  String getShortcutId() {
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
   * Gets the small icon resource id from its string name, or null if the icon is missing from the device.
   */
  public @Nullable
  Integer getSmallIcon() {
    if (!mNotificationAndroidBundle.containsKey("smallIcon")) {
      return null;
    }

    String rawIcon = mNotificationAndroidBundle.getString("smallIcon");
    int smallIconId = ResourceUtils.getImageResourceId(rawIcon);

    if (smallIconId == 0) {
      Logger.d("NotificationAndroidBundle",
        String.format("Notification small icon '%s' could not be found", rawIcon)
      );
      return null;
    }

    return smallIconId;
  }

  /**
   * Gets the small icon & it's level, or null if the icon is missing from the device.
   *
   * @return ArrayList<Integer>
   */
  public @Nullable
  Integer getSmallIconLevel() {
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
  public @Nullable
  String getSortKey() {
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
  public @Nullable
  NotificationAndroidStyleBundle getStyle() {
    if (!hasStyle()) {
      return null;
    }

    return NotificationAndroidStyleBundle.fromBundle(
      mNotificationAndroidBundle.getBundle("style")
    );
  }

  /**
   * Gets the ticker text
   *
   * @return String
   */
  public @Nullable
  String getTicker() {
    return mNotificationAndroidBundle.getString("ticker");
  }

  /**
   * Gets the timeout after ms, -1 if none exists
   *
   * @return long
   */
  public @Nullable
  Long getTimeoutAfter() {
    if (mNotificationAndroidBundle.containsKey("timeoutAfter")) {
      return (long) mNotificationAndroidBundle.getDouble("timeoutAfter");
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

  public @Nullable
  String getSound() {
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

    ArrayList vibrationPattern = Objects
      .requireNonNull(mNotificationAndroidBundle.getParcelableArrayList("vibrationPattern"));

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
      return (int) mNotificationAndroidBundle.getDouble("visibility");
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
      return (long) mNotificationAndroidBundle.getDouble("timestamp");
    }

    return -1;
  }

  public class AndroidProgress {
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
