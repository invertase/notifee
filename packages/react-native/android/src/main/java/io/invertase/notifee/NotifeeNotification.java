package io.invertase.notifee;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;
import static io.invertase.notifee.NotifeeUtils.getSoundUri;

class NotifeeNotification {
  private Bundle notificationBundle;
  private Bundle androidOptionsBundle;

  private NotifeeNotification(Bundle notificationBundle) {
    this.notificationBundle = notificationBundle;
    this.androidOptionsBundle = notificationBundle.getBundle("android");
  }

  static NotifeeNotification fromBundle(Bundle bundle) {
    return new NotifeeNotification(bundle);
  }

  static NotifeeNotification fromReadableMap(@NonNull ReadableMap readableMap) {
    return new NotifeeNotification(Objects.requireNonNull(Arguments.toBundle(readableMap)));
  }

  WritableMap toWritableMap() {
    return Arguments.fromBundle(notificationBundle);
  }

  private NotificationManager getNotificationManager() {
    return (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
  }

  private NotificationManagerCompat getNotificationManagerCompat() {
    return NotificationManagerCompat.from(getApplicationContext());
  }

  private Notification getNotification() {
    String channelId = Objects.requireNonNull(androidOptionsBundle.getString("channelId"));
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
      getApplicationContext(),
      channelId
    );

    // TODO go through all options from bundle and set on notification
    if (notificationBundle.containsKey("title")) {
      notificationBuilder.setContentTitle(notificationBundle.getString("title"));
    }

    if (notificationBundle.containsKey("subtitle")) {
      notificationBuilder.setSubText(notificationBundle.getString("subtitle"));
    }

    if (notificationBundle.containsKey("body")) {
      notificationBuilder.setContentText(notificationBundle.getString("body"));
    }

    if (notificationBundle.containsKey("data")) {
      notificationBuilder.setExtras(notificationBundle.getBundle("data"));
    }

    // TODO sound
    if (androidOptionsBundle.containsKey("sound")) {
      Uri sound = getSoundUri(Objects.requireNonNull(notificationBundle.getString("sound")));
      notificationBuilder.setSound(sound);
    }

    if (androidOptionsBundle.containsKey("actions")) {
      ArrayList actions = androidOptionsBundle.getParcelableArrayList("actions");
      // TODO implement actions
    }

    if (androidOptionsBundle.containsKey("autoCancel")) {
      notificationBuilder.setAutoCancel(androidOptionsBundle.getBoolean("autoCancel"));
    }

    if (androidOptionsBundle.containsKey("badgeIconType")) {
      int badgeIconType = (int) androidOptionsBundle.getDouble("badgeIconType");
      notificationBuilder.setBadgeIconType(badgeIconType);
    }

    if (androidOptionsBundle.containsKey("bubbleMetadata")) {
      // TODO implement bubbles - requires channel flag setting to true
    }


    if (androidOptionsBundle.containsKey("category")) {
      notificationBuilder.setCategory(androidOptionsBundle.getString("category"));
    }

    if (androidOptionsBundle.containsKey("channelId") && Build.VERSION.SDK_INT >= 26) {
      NotificationChannel notificationChannel = getNotificationManager().getNotificationChannel(
        channelId);

      if (notificationChannel == null) {
        throw new InvalidNotificationParameterException(
          InvalidNotificationParameterException.CHANNEL_NOT_FOUND,
          String.format("Notification channel does not exist for the specified id '%s'.", channelId)
        );
      }

      // TODO do we even need to set this if its in the builder?
      notificationBuilder.setChannelId(Objects.requireNonNull(channelId));
    }

    if (androidOptionsBundle.containsKey("clickAction")) {
      // todo clickAction
    }

    if (androidOptionsBundle.containsKey("color")) {
      int color = Color.parseColor(androidOptionsBundle.getString("color"));
      notificationBuilder.setColor(color);
    }

    if (androidOptionsBundle.containsKey("colorized")) {
      notificationBuilder.setColorized(androidOptionsBundle.getBoolean("colorized"));
    }

    if (androidOptionsBundle.containsKey("contentInfo")) {
      notificationBuilder.setContentInfo(androidOptionsBundle.getString("contentInfo"));
    }

    if (androidOptionsBundle.containsKey("chronometerDirection")) {
      String direction = androidOptionsBundle.getString("chronometerDirection");
      if (Objects.requireNonNull(direction).equals("down")) {
//        notificationBuilder.setChronometerCountDown(true);
      }
    }

    if (androidOptionsBundle.containsKey("defaults")) {
      // todo defaults
    }

    if (androidOptionsBundle.containsKey("group")) {
      notificationBuilder.setGroup(androidOptionsBundle.getString("group"));
    }

    if (androidOptionsBundle.containsKey("groupAlertBehavior")) {
      int groupAlertBehavior = (int) androidOptionsBundle.getDouble("groupAlertBehavior");
      notificationBuilder.setGroupAlertBehavior(groupAlertBehavior);
    }

    if (androidOptionsBundle.containsKey("groupSummary")) {
      notificationBuilder.setGroupSummary(androidOptionsBundle.getBoolean("groupSummary"));
    }

    if (androidOptionsBundle.containsKey("largeIcon")) {
      Bitmap largeIcon = getImageBitmap(Objects.requireNonNull(androidOptionsBundle.getString(
        "largeIcon")));

      if (largeIcon != null) {
        notificationBuilder.setLargeIcon(largeIcon);
      }
    }

    if (androidOptionsBundle.containsKey("lights")) {
      ArrayList lightList = androidOptionsBundle.getParcelableArrayList("lights");
      String rawColor = (String) lightList.get(0);
      int color = Color.parseColor(rawColor);
      int onMs = (int) lightList.get(1);
      int offMs = (int) lightList.get(2);
      notificationBuilder.setLights(color, onMs, offMs);
    }

    if (androidOptionsBundle.containsKey("localOnly")) {
      notificationBuilder.setLocalOnly(androidOptionsBundle.getBoolean("localOnly"));
    }

    if (androidOptionsBundle.containsKey("number")) {
      notificationBuilder.setNumber((int) androidOptionsBundle.getDouble("number"));
    }

    if (androidOptionsBundle.containsKey("ongoing")) {
      notificationBuilder.setOngoing(androidOptionsBundle.getBoolean("ongoing"));
    }

    if (androidOptionsBundle.containsKey("onlyAlertOnce")) {
      notificationBuilder.setOnlyAlertOnce(androidOptionsBundle.getBoolean("onlyAlertOnce"));
    }

    if (androidOptionsBundle.containsKey("priority")) {
      notificationBuilder.setPriority((int) androidOptionsBundle.getDouble("priority"));
    }

    if (androidOptionsBundle.containsKey("progress")) {
      Bundle progressBundle = androidOptionsBundle.getBundle("progress");
      int max = (int) progressBundle.getDouble("max");
      int current = (int) progressBundle.getDouble("current");
      boolean indeterminate = progressBundle.getBoolean("indeterminate");
      notificationBuilder.setProgress(max, current, indeterminate);
    }

    if (androidOptionsBundle.containsKey("remoteInputHistory")) {
      ArrayList remoteInputHistory = androidOptionsBundle.getParcelableArrayList(
        "remoteInputHistory");
      String[] history = new String[remoteInputHistory.size()];
      for (int i = 0; i < remoteInputHistory.size(); i++) {
        history[i] = (String) remoteInputHistory.get(i);
      }

      notificationBuilder.setRemoteInputHistory(history);
    }

    if (androidOptionsBundle.containsKey("shortcutId")) {
      notificationBuilder.setShortcutId(androidOptionsBundle.getString("shortcutId"));
    }

    if (androidOptionsBundle.containsKey("showTimestamp")) {
      notificationBuilder.setShowWhen(androidOptionsBundle.getBoolean("showTimestamp"));
    }

    if (androidOptionsBundle.containsKey("smallIcon")) {
      ArrayList smallIconList = androidOptionsBundle.getParcelableArrayList("smallIcon");
      String smallIconRaw = (String) smallIconList.get(0);
      int smallIcon = getResourceIcon(smallIconRaw);

      if (smallIcon != 0) {
        int level = (int) smallIconList.get(1);
        if (level == -1) {
          notificationBuilder.setSmallIcon(smallIcon);
        } else {
          notificationBuilder.setSmallIcon(smallIcon, level);
        }
      }
    }

    if (androidOptionsBundle.containsKey("sortKey")) {
      notificationBuilder.setSortKey(androidOptionsBundle.getString("sortKey"));
    }

    if (androidOptionsBundle.containsKey("style")) {
      Bundle styleBundle = androidOptionsBundle.getBundle("style");
      int type = (int) styleBundle.getDouble("type");
      NotificationCompat.Style style = null;

      switch (type) {
        case 0:
          style = getBigPictureStyle(styleBundle);
          break;
        case 1:
          style = getBigTextStyle(styleBundle);
          break;
        case 2:
          style = getInboxStyle(styleBundle);
          break;
      }

      if (style != null) {
        notificationBuilder.setStyle(style);
      }
    }

    if (androidOptionsBundle.containsKey("ticker")) {
      notificationBuilder.setTicker(androidOptionsBundle.getString("ticker"));
    }

    if (androidOptionsBundle.containsKey("timeoutAfter")) {
      long timeoutAfter = (long) androidOptionsBundle.getDouble("timeoutAfter");
      notificationBuilder.setTimeoutAfter(timeoutAfter);
    }

    if (androidOptionsBundle.containsKey("showChronometer")) {
      notificationBuilder.setUsesChronometer(androidOptionsBundle.getBoolean("showChronometer"));
    }

    if (androidOptionsBundle.containsKey("vibrationPattern")) {
      ArrayList vibrationPattern = androidOptionsBundle.getParcelableArrayList("vibrationPattern");
      long[] vibrateArray = new long[vibrationPattern.size()];

      for (int i = 0; i < vibrationPattern.size(); i++) {
        Integer value = (Integer) vibrationPattern.get(i);
        vibrateArray[i] = value.longValue();
      }

      notificationBuilder.setVibrate(vibrateArray);
    }

    if (androidOptionsBundle.containsKey("visibility")) {
      notificationBuilder.setVisibility((int) androidOptionsBundle.getDouble("visibility"));
    }

    if (androidOptionsBundle.containsKey("timestamp")) {
      long when = (long) androidOptionsBundle.getDouble("timestamp");
      notificationBuilder.setWhen(when);
    }

    return notificationBuilder.build();
  }

//  private NotificationCompat.Action buildNotificationAction(Object action) {
//    String icon = action.getString();
//
//    NotificationCompat.Action.Builder ab = new NotificationCompat.Action.Builder(
//      "foo",
//      "bar",
//      "baz"
//    );
//  }

  /**
   * BigPictureStyle
   */
  private NotificationCompat.BigPictureStyle getBigPictureStyle(Bundle bigPictureStyleBundle) {
    NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

    if (bigPictureStyleBundle.containsKey("picture")) {
      Bitmap picture = getImageBitmap(Objects.requireNonNull(bigPictureStyleBundle.getString(
        "picture")));

      if (picture != null) {
        bigPictureStyle.bigPicture(picture);
      }
    }

    if (bigPictureStyleBundle.containsKey("largeIcon")) {
      Bitmap largeIcon = getImageBitmap(Objects.requireNonNull(bigPictureStyleBundle.getString(
        "largeIcon")));

      if (largeIcon != null) {
        bigPictureStyle.bigLargeIcon(largeIcon);
      }
    }

    if (bigPictureStyleBundle.containsKey("title")) {
      bigPictureStyle = bigPictureStyle.setBigContentTitle(bigPictureStyleBundle.getString("title"));
    }

    if (bigPictureStyleBundle.containsKey("summary")) {
      bigPictureStyle = bigPictureStyle.setSummaryText(bigPictureStyleBundle.getString("summary"));
    }

    return bigPictureStyle;
  }

  /**
   * BigTextStyle
   */
  private NotificationCompat.BigTextStyle getBigTextStyle(Bundle bigTextStyleBundle) {
    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

    if (bigTextStyleBundle.containsKey("text")) {
      bigTextStyle = bigTextStyle.bigText(bigTextStyleBundle.getString("text"));
    }

    if (bigTextStyleBundle.containsKey("title")) {
      bigTextStyle = bigTextStyle.setBigContentTitle(bigTextStyleBundle.getString("title"));
    }

    if (bigTextStyleBundle.containsKey("summary")) {
      bigTextStyle = bigTextStyle.setSummaryText(bigTextStyleBundle.getString("summary"));
    }

    return bigTextStyle;
  }

  /**
   * InboxStyle
   */
  private NotificationCompat.InboxStyle getInboxStyle(Bundle inputStyleBundle) {
    NotificationCompat.InboxStyle inputStyle = new NotificationCompat.InboxStyle();

    if (inputStyleBundle.containsKey("title")) {
      inputStyle = inputStyle.setBigContentTitle(inputStyleBundle.getString("title"));
    }

    if (inputStyleBundle.containsKey("summary")) {
      inputStyle = inputStyle.setSummaryText(inputStyleBundle.getString("summary"));
    }

    ArrayList<String> lines = inputStyleBundle.getStringArrayList("lines");

    for (int i = 0; i < Objects.requireNonNull(lines).size(); i++) {
      String line = lines.get(i);
      inputStyle = inputStyle.addLine(line);
    }

    return inputStyle;
  }

  /**
   * Returns a Bitmap from a user image (local/url)
   */
  private Bitmap getImageBitmap(String image) {
    if (image.startsWith("http://") || image.startsWith("https://")) {
      return getBitmapFromHttpUrl(image);
    }

    if (image.startsWith("file://")) {
      return BitmapFactory.decodeFile(image.replace("file://", ""));
    }

    int resourceId = getResourceIcon(image);

    return BitmapFactory.decodeResource(
      getApplicationContext().getResources(),
      resourceId
    );
  }

  private Bitmap getBitmapFromHttpUrl(String httpUrl) {
    // TODO should probably be using fresco for this, comes with RN, comes with caching e.g for offline
    // this will hang if no connection?
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(httpUrl).openConnection();
      connection.setDoInput(true);
      connection.connect();
      return BitmapFactory.decodeStream(connection.getInputStream());
    } catch (IOException e) {
      Log.e("RNFBNotifications", "Failed to get bitmap for url: " + httpUrl, e);
      return null;
    }
  }

  private int getResourceIcon(String icon) {
    int resourceId = NotifeeUtils.getResourceIdByName(icon, "mipmap");
    if (resourceId == 0) {
      resourceId = NotifeeUtils.getResourceIdByName(icon, "drawable");
    }

    return resourceId;
  }

  public void displayNotification() {
    String notificationId = Objects.requireNonNull(notificationBundle.getString("id"));

    String notificationTag = null;
    if (androidOptionsBundle.containsKey("tag")) {
      notificationTag = Objects.requireNonNull(androidOptionsBundle.getString("tag"));
    }

    getNotificationManagerCompat().notify(
      notificationTag,
      notificationId.hashCode(),
      getNotification()
    );
  }

}
