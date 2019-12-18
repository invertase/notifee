package app.notifee.core;

import android.app.NotificationChannel;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import app.notifee.core.bundles.ChannelBundle;

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

  static void deleteChannel(@NonNull String channelId) {
    NotificationManagerCompat.from(ContextHolder.getApplicationContext())
      .deleteNotificationChannel(channelId);
  }

  static void deleteChannelGroup(@NonNull String channelGroupId) {
    NotificationManagerCompat.from(ContextHolder.getApplicationContext())
      .deleteNotificationChannelGroup(channelGroupId);
  }
}
