package io.invertase.notifee;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.core.app.NotificationManagerCompat.IMPORTANCE_NONE;
import static io.invertase.notifee.NotifeeUtils.getColor;
import static io.invertase.notifee.NotifeeUtils.getFileName;
import static io.invertase.notifee.NotifeeUtils.getSoundUri;
import static io.invertase.notifee.NotifeeUtils.getVibrationPattern;
import static io.invertase.notifee.NotifeeUtils.parseVibrationPattern;
import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

public class NotifeeNotificationChannel {
  // https://developer.android.com/reference/androidx/core/app/NotificationManagerCompat.html
  private static NotificationManagerCompat getNotificationManager() {
    return NotificationManagerCompat.from(getApplicationContext());
  }

  public static WritableMap getChannel(String channelId) {
    return createChannelMap(getNotificationManager().getNotificationChannel(channelId));
  }

  public static WritableArray getChannels() {
    return createChannelsArray(getNotificationManager().getNotificationChannels());
  }

  public static WritableMap getChannelGroup(String channelGroupId) {
    return createChannelGroupMap(getNotificationManager().getNotificationChannelGroup(
      channelGroupId));
  }

  public static WritableArray getChannelGroups() {
    return createChannelGroupsArray(getNotificationManager().getNotificationChannelGroups());
  }

  public static void createChannel(ReadableMap channelMap) {
    NotificationChannel channel = parseChannelMap(channelMap);

    if (channel != null) {
      getNotificationManager().createNotificationChannel(channel);
    }
  }

  public static void createChannelGroup(ReadableMap channelGroupMap) {
    NotificationChannelGroup channelGroup = parseChannelGroupMap(channelGroupMap);

    if (channelGroup != null) {
      getNotificationManager().createNotificationChannelGroup(channelGroup);
    }
  }

  public static void createChannelGroups(ReadableArray channelGroupsArray) {
    if (Build.VERSION.SDK_INT >= 26) {
      List<NotificationChannelGroup> channelGroups = new ArrayList<>();
      for (int i = 0; i < channelGroupsArray.size(); i++) {
        NotificationChannelGroup channelGroup = parseChannelGroupMap(channelGroupsArray.getMap(i));
        channelGroups.add(channelGroup);
      }
      getNotificationManager().createNotificationChannelGroups(channelGroups);
    }
  }

  public static void createChannels(ReadableArray channelsArray) {
    if (Build.VERSION.SDK_INT >= 26) {
      List<NotificationChannel> channels = new ArrayList<>();
      for (int i = 0; i < channelsArray.size(); i++) {
        NotificationChannel channel = parseChannelMap(channelsArray.getMap(i));
        channels.add(channel);
      }
      getNotificationManager().createNotificationChannels(channels);
    }
  }

  public static void deleteChannelGroup(String groupId) {
    getNotificationManager().deleteNotificationChannelGroup(groupId);
  }

  public static void deleteChannel(String channelId) {
    getNotificationManager().deleteNotificationChannel(channelId);
  }

  static private WritableArray createChannelsArray(List<NotificationChannel> notificationChannels) {
    WritableArray writableArray = Arguments.createArray();

    for (NotificationChannel notificationChannel : notificationChannels) {
      writableArray.pushMap(createChannelMap(notificationChannel));
    }

    return writableArray;
  }

  static private WritableArray createChannelGroupsArray(List<NotificationChannelGroup> notificationChannelGroups) {
    WritableArray writableArray = Arguments.createArray();

    for (NotificationChannelGroup notificationChannelGroup : notificationChannelGroups) {
      writableArray.pushMap(createChannelGroupMap(notificationChannelGroup));
    }

    return writableArray;
  }

  /**
   * Converts a JS object into a native NotificationChannelGroup
   */
  private static NotificationChannelGroup parseChannelGroupMap(ReadableMap channelGroupMap) {
    if (Build.VERSION.SDK_INT >= 26) {
      NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(
        channelGroupMap.getString("id"),
        channelGroupMap.getString("name")
      );

      if (Build.VERSION.SDK_INT >= 28 && channelGroupMap.hasKey("description")) {
        String description = channelGroupMap.getString("description");
        notificationChannelGroup.setDescription(description);
      }

      return notificationChannelGroup;
    }

    return null;
  }

  /**
   * Creates a JS object from a native NotificationChannelGroup
   */
  private static WritableMap createChannelGroupMap(@Nullable NotificationChannelGroup notificationChannelGroup) {
    // Older devices may not have channels
    if (notificationChannelGroup == null) {
      return null;
    }

    WritableMap writableMap = Arguments.createMap();

    if (Build.VERSION.SDK_INT >= 26) {
      writableMap.putString("id", notificationChannelGroup.getId());
      writableMap.putString("name", notificationChannelGroup.getName().toString());
      writableMap.putArray("channels", createChannelsArray(notificationChannelGroup.getChannels()));
      if (Build.VERSION.SDK_INT >= 28) {
        writableMap.putBoolean("blocked", notificationChannelGroup.isBlocked());
        writableMap.putString("description", notificationChannelGroup.getDescription());
      } else {
        writableMap.putBoolean("blocked", false);
        writableMap.putString("description", "");
      }
    }

    return writableMap;
  }

  /**
   * Converts a JS object into a native NotificationChannel
   */
  private static NotificationChannel parseChannelMap(ReadableMap channelMap) {
    if (Build.VERSION.SDK_INT >= 26) {
      NotificationChannel channel = new NotificationChannel(
        channelMap.getString("id"),
        channelMap.getString("name"),
        channelMap.getInt("importance")
      );

      if (channelMap.hasKey("badge")) {
        channel.setShowBadge(channelMap.getBoolean("badge"));
      }

      if (channelMap.hasKey("bypassDnd")) {
        channel.setBypassDnd(channelMap.getBoolean("bypassDnd"));
      }

      if (channelMap.hasKey("description")) {
        channel.setDescription(channelMap.getString("description"));
      }

      if (channelMap.hasKey("groupId")) {
        channel.setGroup(channelMap.getString("groupId"));
      }

      if (channelMap.hasKey("lightColor")) {
        String lightColor = channelMap.getString("lightColor");
        channel.setLightColor(Color.parseColor(lightColor));
      }

      if (channelMap.hasKey("lights")) {
        channel.enableLights(channelMap.getBoolean("lights"));
      }

      if (channelMap.hasKey("visibility")) {
        channel.setLockscreenVisibility(channelMap.getInt("visibility"));
      }

      if (channelMap.hasKey("sound")) {
        Uri sound = getSoundUri(channelMap.getString("sound"));
        channel.setSound(sound, null);
      }

      if (channelMap.hasKey("vibration")) {
        channel.enableVibration(channelMap.getBoolean("vibration"));
      }

      if (channelMap.hasKey("vibrationPattern")) {
        long[] pattern = parseVibrationPattern(Objects.requireNonNull(channelMap.getArray("vibrationPattern")));
        channel.setVibrationPattern(pattern);
      }

      return channel;
    }

    return null;
  }

  /**
   * Creates a JS object from a native NotificationChannel
   */
  private static WritableMap createChannelMap(@Nullable NotificationChannel notificationChannel) {
    // Older devices may not have channels
    if (notificationChannel == null) {
      return null;
    }

    WritableMap writableMap = Arguments.createMap();

    if (Build.VERSION.SDK_INT >= 26) {
      writableMap.putString("id", notificationChannel.getId());
      writableMap.putString("name", notificationChannel.getName().toString());
      writableMap.putBoolean("badge", notificationChannel.canShowBadge());
      writableMap.putBoolean("bypassDnd", notificationChannel.canBypassDnd());
      writableMap.putString("description", notificationChannel.getDescription());
      writableMap.putBoolean("lights", notificationChannel.shouldShowLights());
      writableMap.putString("groupId", notificationChannel.getGroup());
      writableMap.putInt("importance", notificationChannel.getImportance());
      writableMap.putBoolean("vibration", notificationChannel.shouldVibrate());
      writableMap.putString("sound", getFileName(getApplicationContext(), notificationChannel.getSound()));
      writableMap.putBoolean("blocked", notificationChannel.getImportance() == IMPORTANCE_NONE);
      writableMap.putString("lightColor", getColor(notificationChannel.getLightColor()));

      WritableArray pattern = getVibrationPattern(notificationChannel.getVibrationPattern());
      if (pattern.size() > 0) {
        writableMap.putArray("vibrationPattern", pattern);
      }

      // Unless the user manually changes this in app settings, it is always -1000.
      int visibility = notificationChannel.getLockscreenVisibility();
      if (visibility != -1000) { // -1000 = not set
        writableMap.putInt("visibility", visibility);
      }
    }

    return writableMap;
  }


}
