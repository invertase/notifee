package app.notifee.core;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import app.notifee.core.bundles.ChannelBundle;
import app.notifee.core.bundles.ChannelGroupBundle;

import static androidx.core.app.NotificationManagerCompat.IMPORTANCE_NONE;

public class ChannelManager {

  static Task<Void> createChannel(ChannelBundle channelBundle) {
    return Tasks.call(() -> {
      if (Build.VERSION.SDK_INT < 26) {
        return null;
      }

      NotificationChannel channel = new NotificationChannel(
        channelBundle.getId(),
        channelBundle.getName(),
        channelBundle.getImportance()
      );

      channel.setShowBadge(channelBundle.getBadge());
      channel.setBypassDnd(channelBundle.getBypassDnd());
      channel.setDescription(channelBundle.getDescription());
      channel.setGroup(channelBundle.getGroupId());
      channel.enableLights(channelBundle.getLights());

      if (channelBundle.getLightColor() != null) {
        channel.setLightColor(channelBundle.getLightColor());
      }

      channel.setLockscreenVisibility(channelBundle.getVisibility());
      channel.enableVibration(channelBundle.getVibration());

      long[] vibrationPattern = channelBundle.getVibrationPattern();
      if (vibrationPattern.length > 0) {
        channel.setVibrationPattern(vibrationPattern);
      }

      // TODO channel sound
//      if (channelBundle.getSound()) {
//        channel.setSound();
//      }

      NotificationManagerCompat.from(ContextHolder.getApplicationContext())
        .createNotificationChannel(channel);

      return null;
    });
  }

  static Task<Void> createChannels(List<ChannelBundle> channelBundles) {
    return Tasks.call(() -> {
      for (ChannelBundle channelBundle : channelBundles) {
        Tasks.await(createChannel(channelBundle));
      }

      return null;
    });
  }

  static Task<Void> createChannelGroup(ChannelGroupBundle channelGroupBundle) {
    return Tasks.call(() -> {
      if (Build.VERSION.SDK_INT < 26) {
        return null;
      }

      NotificationChannelGroup notificationChannelGroup = new NotificationChannelGroup(
        channelGroupBundle.getId(),
        channelGroupBundle.getName()
      );

      if (Build.VERSION.SDK_INT >= 28 && channelGroupBundle.getDescription() != null) {
        notificationChannelGroup.setDescription(channelGroupBundle.getDescription());
      }

      return null;
    });
  }

  static Task<Void> createChannelGroups(List<ChannelGroupBundle> channelGroupBundles) {
    return Tasks.call(() -> {
      if (Build.VERSION.SDK_INT < 26) {
        return null;
      }

      for (ChannelGroupBundle channelGroupBundle : channelGroupBundles) {
        Tasks.await(createChannelGroup(channelGroupBundle));
      }

      return null;
    });
  }

  static void deleteChannel(@NonNull String channelId) {
    NotificationManagerCompat.from(ContextHolder.getApplicationContext())
      .deleteNotificationChannel(channelId);
  }

  static void deleteChannelGroup(@NonNull String channelGroupId) {
    NotificationManagerCompat.from(ContextHolder.getApplicationContext())
      .deleteNotificationChannelGroup(channelGroupId);
  }

  static Task<List<Bundle>> getAllChannels() {
    return Tasks.call(() -> {
      List<NotificationChannel> channels = NotificationManagerCompat.from(ContextHolder.getApplicationContext())
        .getNotificationChannels();

      if (channels.size() == 0 || Build.VERSION.SDK_INT < 26) {
        return Collections.emptyList();
      }

      ArrayList<Bundle> channelBundles = new ArrayList<>(channels.size());
      for (NotificationChannel channel : channels) {
        channelBundles.add(convertChannelToBundle(channel));
      }

      return channelBundles;
    });
  }

  static Task<Bundle> getChannel(String channelId) {
    return Tasks.call(() -> {
      NotificationChannel channel = NotificationManagerCompat.from(ContextHolder.getApplicationContext())
        .getNotificationChannel(channelId);

      return convertChannelToBundle(channel);
    });
  }

  static Task<List<Bundle>> getAllChannelGroups() {
    return Tasks.call(() -> {
      List<NotificationChannelGroup> channelGroups = NotificationManagerCompat.from(ContextHolder.getApplicationContext())
        .getNotificationChannelGroups();

      if (channelGroups.size() == 0 || Build.VERSION.SDK_INT < 26) {
        return Collections.emptyList();
      }

      ArrayList<Bundle> channelGroupBundles = new ArrayList<>(channelGroups.size());
      for (NotificationChannelGroup channelGroup : channelGroups) {
        channelGroupBundles.add(convertChannelGroupToBundle(channelGroup));
      }

      return channelGroupBundles;
    });
  }

  static Task<Bundle> getChannelGroup(String channelGroupId) {
    return Tasks.call(() -> {
      NotificationChannelGroup channelGroup = NotificationManagerCompat.from(ContextHolder.getApplicationContext())
        .getNotificationChannelGroup(channelGroupId);

      return convertChannelGroupToBundle(channelGroup);
    });
  }

  private static Bundle convertChannelToBundle(NotificationChannel channel) {
    if (channel == null || Build.VERSION.SDK_INT < 26) {
      return null;
    }

    Bundle channelBundle = new Bundle();
    channelBundle.putString("id", channel.getId());
    channelBundle.putString("name", channel.getName().toString());
    channelBundle.putBoolean("badge", channel.canShowBadge());
    channelBundle.putBoolean("bypassDnd", channel.canBypassDnd());
    channelBundle.putString("description", channel.getDescription());
    channelBundle.putBoolean("lights", channel.shouldShowLights());
    channelBundle.putString("groupId", channel.getGroup());
    channelBundle.putInt("importance", channel.getImportance());
    channelBundle.putBoolean("vibration", channel.shouldVibrate());
    channelBundle.putString("sound", ""); // TODO convert sound to string
    channelBundle.putBoolean("blocked", channel.getImportance() == IMPORTANCE_NONE);
    channelBundle.putString("lightColor", ""); // TODO convert light color

    long[] vibrationPattern = channel.getVibrationPattern();
    if (vibrationPattern.length > 0) channelBundle.putLongArray("vibrationPattern", vibrationPattern);

    // Unless the user manually changes this in app settings, it is always -1000.
    int visibility = channel.getLockscreenVisibility();
    if (visibility != -1000) { // -1000 = not set
      channelBundle.putInt("visibility", visibility);
    }

    return channelBundle;
  }

  private static Bundle convertChannelGroupToBundle(NotificationChannelGroup channelGroup) {
    if (channelGroup == null || Build.VERSION.SDK_INT < 26) {
      return null;
    }

    Bundle channelGroupBundle = new Bundle();
    channelGroupBundle.putString("id", channelGroup.getId());
    channelGroupBundle.putString("name", channelGroup.getName().toString());

    List<NotificationChannel> notificationChannels = channelGroup.getChannels();
    ArrayList<Bundle> channels = new ArrayList<>(notificationChannels.size());

    for (NotificationChannel notificationChannel : notificationChannels) {
      channels.add(convertChannelToBundle(notificationChannel));
    }

    channelGroupBundle.putParcelableArrayList("channels", channels);

    if (Build.VERSION.SDK_INT >= 28) {
      channelGroupBundle.putBoolean("blocked", channelGroup.isBlocked());
      channelGroupBundle.putString("description", channelGroup.getDescription());
    } else {
      channelGroupBundle.putBoolean("blocked", false);
      channelGroupBundle.putString("description", "");
    }

    return channelGroupBundle;
  }
}
