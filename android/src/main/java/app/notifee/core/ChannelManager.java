package app.notifee.core;

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

import static androidx.core.app.NotificationManagerCompat.IMPORTANCE_NONE;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import app.notifee.core.model.ChannelGroupModel;
import app.notifee.core.model.ChannelModel;
import app.notifee.core.utility.ColorUtils;
import app.notifee.core.utility.ResourceUtils;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChannelManager {

  private static String TAG = "ChannelManager";
  private static ExecutorService executorService = Executors.newCachedThreadPool();
  private static ListeningExecutorService lExecutorService = MoreExecutors.listeningDecorator(
    executorService);

  static ListenableFuture<Void> createChannel(ChannelModel channelModel) {
    return lExecutorService.submit(
      () -> {
        if (Build.VERSION.SDK_INT < 26) {
          return null;
        }

          NotificationChannel channel =
              new NotificationChannel(
                  channelModel.getId(), channelModel.getName(), channelModel.getImportance());

          channel.setShowBadge(channelModel.getBadge());
          channel.setBypassDnd(channelModel.getBypassDnd());
          channel.setDescription(channelModel.getDescription());
          channel.setGroup(channelModel.getGroupId());
          channel.enableLights(channelModel.getLights());

          if (channelModel.getLightColor() != null) {
            channel.setLightColor(channelModel.getLightColor());
          }

          channel.setLockscreenVisibility(channelModel.getVisibility());
          channel.enableVibration(channelModel.getVibration());

          long[] vibrationPattern = channelModel.getVibrationPattern();
          if (vibrationPattern.length > 0) {
            channel.setVibrationPattern(vibrationPattern);
          }

          if (channelModel.getSound() != null) {
            Uri soundUri = ResourceUtils.getSoundUri(channelModel.getSound());
            if (soundUri != null) {
              AudioAttributes audioAttributes =
                  new AudioAttributes.Builder()
                      .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                      .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                      .build();
              channel.setSound(soundUri, audioAttributes);
            } else {
              Logger.w(
                  TAG,
                  "Unable to retrieve sound for channel, sound was specified as: "
                      + channel.getSound());
            }
          } else {
            channel.setSound(null, null);
          }

          NotificationManagerCompat.from(ContextHolder.getApplicationContext())
              .createNotificationChannel(channel);

          return null;
        });
  }

  static ListenableFuture<Void> createChannels(List<ChannelModel> channelModels) {
    return lExecutorService.submit(
        () -> {
          for (ChannelModel channelModel : channelModels) {
            createChannel(channelModel).get();
          }

          return null;
        });
  }

  static ListenableFuture<Void> createChannelGroup(ChannelGroupModel channelGroupModel) {
    return lExecutorService.submit(
        () -> {
          if (Build.VERSION.SDK_INT < 26) {
            return null;
          }

          NotificationChannelGroup notificationChannelGroup =
              new NotificationChannelGroup(channelGroupModel.getId(), channelGroupModel.getName());

          if (Build.VERSION.SDK_INT >= 28 && channelGroupModel.getDescription() != null) {
            notificationChannelGroup.setDescription(channelGroupModel.getDescription());
          }

          NotificationManagerCompat.from(ContextHolder.getApplicationContext())
              .createNotificationChannelGroup(notificationChannelGroup);

          return null;
        });
  }

  static ListenableFuture<Void> createChannelGroups(
      List<ChannelGroupModel> channelGroupModels) {
      return lExecutorService.submit(
        () -> {
          if (Build.VERSION.SDK_INT < 26) {
            return null;
          }

          for (ChannelGroupModel channelGroupModel : channelGroupModels) {
            createChannelGroup(channelGroupModel).get();
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

  static ListenableFuture<List<Bundle>> getChannels() {
    return lExecutorService.submit(
        () -> {
          List<NotificationChannel> channels =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext())
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

  static ListenableFuture<Bundle> getChannel(String channelId) {
    return lExecutorService.submit(
        () -> {
          NotificationChannel channel =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext())
                  .getNotificationChannel(channelId);

          return convertChannelToBundle(channel);
        });
  }

  static ListenableFuture<Boolean> isChannelBlocked(String channelId) {
    return lExecutorService.submit(
        () -> {
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return false;

          NotificationChannel channel =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext())
                  .getNotificationChannel(channelId);

          if (channel == null) {
            return false;
          }

          return IMPORTANCE_NONE == channel.getImportance();
        });
  }

  static ListenableFuture<Boolean> isChannelCreated(String channelId) {
    return lExecutorService.submit(
        () -> {
          if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return false;

          NotificationChannel channel =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext())
                  .getNotificationChannel(channelId);

          return channel != null;
        });
  }

  static ListenableFuture<List<Bundle>> getChannelGroups() {
      return lExecutorService.submit(
        () -> {
          List<NotificationChannelGroup> channelGroups =
              NotificationManagerCompat.from(ContextHolder.getApplicationContext())
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

  static ListenableFuture<Bundle> getChannelGroup(String channelGroupId) {
    return lExecutorService.submit(
        () -> {
          NotificationChannelGroup channelGroup =
            NotificationManagerCompat.from(ContextHolder.getApplicationContext())
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

    // can be null, don't include if null
    if (channel.getDescription() != null) {
      channelBundle.putString("description", channel.getDescription());
    }

    // can be null, don't include if null
    if (channel.getGroup() != null) {
      channelBundle.putString("groupId", channel.getGroup());
    }

    channelBundle.putInt("importance", channel.getImportance());
    channelBundle.putBoolean("lights", channel.shouldShowLights());
    channelBundle.putBoolean("vibration", channel.shouldVibrate());
    channelBundle.putBoolean("blocked", channel.getImportance() == IMPORTANCE_NONE);

    // can be null, don't include if null
    if (channel.getSound() != null) {
      channelBundle.putString("soundURI", channel.getSound().toString());
      // try to parse uri
      String soundValue = ResourceUtils.getSoundName(channel.getSound());
      if (soundValue != null) channelBundle.putString("sound", soundValue);
    }

    // optional, can be 0
    if (channel.getLightColor() != 0) {
      channelBundle.putString("lightColor", ColorUtils.getColorString(channel.getLightColor()));
    }

    // getVibrationPattern can be null
    long[] vibrationPattern = channel.getVibrationPattern();

    if (vibrationPattern != null && vibrationPattern.length > 0) {
      try {
        int[] convertedVibrationPattern = new int[vibrationPattern.length];
        // cast to int array
        for (int i = 0; i < vibrationPattern.length; i++) {
          convertedVibrationPattern[i] = (int) vibrationPattern[i];
        }

        channelBundle.putIntArray("vibrationPattern", convertedVibrationPattern);
      } catch (Exception e) {
        Logger.e(TAG, "Unable to convert Vibration Pattern to Channel Bundle", e);
      }
    }

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
    }

    return channelGroupBundle;
  }

  public static ListeningExecutorService getListeningExecutorService() {
    return lExecutorService;
  }
}
