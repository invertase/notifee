package io.invertase.notifee;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.Person;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.invertase.notifee.NotifeeForegroundService.START_FOREGROUND_SERVICE_ACTION;
import static io.invertase.notifee.NotifeeReceiverService.ACTION_NOTIFICATION_ACTION_PRESS_INTENT;
import static io.invertase.notifee.NotifeeReceiverService.ACTION_NOTIFICATION_DELETE_INTENT;
import static io.invertase.notifee.NotifeeReceiverService.ACTION_NOTIFICATION_DELIVERED_INTENT;
import static io.invertase.notifee.NotifeeReceiverService.ACTION_NOTIFICATION_PRESS_INTENT;
import static io.invertase.notifee.NotifeeReceiverService.RECEIVER_REMOTE_INPUT_KEY;
import static io.invertase.notifee.NotifeeUtils.getImageBitmapFromUrl;
import static io.invertase.notifee.NotifeeUtils.getImageResourceId;
import static io.invertase.notifee.NotifeeUtils.getLaunchActivity;
import static io.invertase.notifee.NotifeeUtils.getPerson;
import static io.invertase.notifee.NotifeeUtils.getSoundUri;
import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

public class NotifeeNotification {
  static final ExecutorService NOTIFICATION_BUILD_EXECUTOR = Executors.newFixedThreadPool(6); // TODO break out
  private static final ExecutorService NOTIFICATION_DISPLAY_EXECUTOR = Executors.newFixedThreadPool(2);
  private Bundle notificationBundle;
  private Bundle androidOptionsBundle;
  private int notificationHashCode;

  private NotifeeNotification(Bundle notificationBundle) {
    this.notificationBundle = notificationBundle;
    this.androidOptionsBundle = notificationBundle.getBundle("android");
    this.notificationHashCode = Objects.requireNonNull(notificationBundle.getString("id")).hashCode();
  }

  /**
   * Returns the component name for an activity to open, triggered by a notification Intent
   *
   * @param defaultName the default component name if no intent or reactComponent was available
   * @return the string component name
   */
  public static String getMainComponentName(String defaultName) {
    synchronized (NotifeeReceiverService.reactComponents) {
      String reactComponent = null;

      if (NotifeeReceiverService.reactComponents.size() > 0) {
        synchronized (NotifeeReceiverService.reactComponents) {
          Log.d("ELLIOT", "reactComponents has size");

          reactComponent = NotifeeReceiverService.reactComponents.get(0);
          NotifeeReceiverService.reactComponents.remove(0);
        }
      } else {
        Log.d("ELLIOT", "reactComponents is empty");
      }

      Log.d("ELLIOT", "reactComponents = " + reactComponent);


      if (reactComponent != null) {
        return reactComponent;
      }

      return defaultName;
    }

//    if (intent == null) {
//      Log.d("ELLIOT", "intent null");
//      return defaultName;
//    }
//
//    String action = intent.getAction();
//    Log.d("ELLIOT", "action = " + action);
//
//    if (action == null || !action.equals(NotifeeReceiverService.ACTION_NOTIFICATION_INTENT)) {
//      return defaultName;
//    }
//
//    Bundle extras = intent.getExtras();
//
//    if (extras == null) return defaultName;
//
//    String reactComponent = extras.getString("reactComponent");
//
//    if (reactComponent == null) return defaultName;
//
//    return reactComponent;
  }

  @SuppressWarnings("unused")
  static NotifeeNotification fromBundle(Bundle bundle) {
    return new NotifeeNotification(bundle);
  }

  /**
   * Converts a ReadableMap from JS into a NotifeeNotification class
   *
   * @param readableMap ReadableMap from JS land
   * @return the NotifeeNotification
   */
  static NotifeeNotification fromReadableMap(@NonNull ReadableMap readableMap) {
    return new NotifeeNotification(Objects.requireNonNull(Arguments.toBundle(readableMap)));
  }

  /**
   * Gets a non-compat notification manager
   *
   * @return NotificationManager
   */
  @SuppressWarnings("WeakerAccess")
  static NotificationManager getNotificationManager() {
    return (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
  }

  /**
   * Returns a compat notification manager
   *
   * @return NotificationManagerCompat
   */
  static NotificationManagerCompat getNotificationManagerCompat() {
    return NotificationManagerCompat.from(getApplicationContext());
  }

  /**
   * Creates a WritableMap from the current notification Bundle
   *
   * @return WritableMap
   */
  WritableMap toWritableMap() {
    return Arguments.fromBundle(notificationBundle);
  }

  /**
   * Returns whether this notification should display in a foreground service
   *
   * @return boolean value
   */
  Boolean isForegroundServiceNotification() {
    return this.androidOptionsBundle.containsKey("asForegroundService") && this.androidOptionsBundle.getBoolean("asForegroundService");
  }

  /**
   * Gets a Notification instance from the current bundle passed from JS
   *
   * @return Notification
   */
  private Task<Notification> getNotification() {
    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, () -> {
      String channelId = Objects.requireNonNull(androidOptionsBundle.getString("channelId"));
      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
        getApplicationContext(),
        channelId
      );

      // Always keep at top. Some Compat fields set values in extras.
      if (notificationBundle.containsKey("data")) {
        Bundle data = notificationBundle.getBundle("data");
        notificationBuilder.setExtras((Bundle) Objects.requireNonNull(data).clone());
      }

      notificationBuilder.setDeleteIntent(NotifeeReceiverService.createIntent(
        ACTION_NOTIFICATION_DELETE_INTENT,
        new String[]{"notification"},
        notificationBundle
      ));

      if (androidOptionsBundle.containsKey("onPressAction")) {
        notificationBuilder.setContentIntent(NotifeeReceiverService.createIntent(
          ACTION_NOTIFICATION_PRESS_INTENT,
          new String[]{"notification", "action"},
          notificationBundle,
          androidOptionsBundle.getBundle("onPressAction")
        ));
      }

      if (notificationBundle.containsKey("title")) {
        notificationBuilder.setContentTitle(notificationBundle.getString("title"));
      }

      if (notificationBundle.containsKey("subtitle")) {
        notificationBuilder.setSubText(notificationBundle.getString("subtitle"));
      }

      if (notificationBundle.containsKey("body")) {
        notificationBuilder.setContentText(notificationBundle.getString("body"));
      }

      // TODO sound
      if (androidOptionsBundle.containsKey("sound")) {
        Uri sound = getSoundUri(Objects.requireNonNull(notificationBundle.getString("sound")));
        notificationBuilder.setSound(sound);
      }

      if (androidOptionsBundle.containsKey("actions")) {
        List<NotificationCompat.Action> actions = Tasks.await(
          getActions(androidOptionsBundle.getParcelableArrayList("actions"))
        );

        for (NotificationCompat.Action action : actions) {
          notificationBuilder.addAction(action);
        }
      }

      if (androidOptionsBundle.containsKey("autoCancel")) {
        notificationBuilder.setAutoCancel(androidOptionsBundle.getBoolean("autoCancel"));
      }

      if (androidOptionsBundle.containsKey("badgeIconType")) {
        int badgeIconType = (int) androidOptionsBundle.getDouble("badgeIconType");
        notificationBuilder.setBadgeIconType(badgeIconType);
      }

//      if (androidOptionsBundle.containsKey("bubble")) {
//        Context context = getApplicationContext();
//        Intent target = new Intent(context, NotifeeBubbleActivity.class);
//        PendingIntent bubbleIntent = PendingIntent.getActivity(context, 0, target, 0);
//
//        Bundle bubbleBundle = androidOptionsBundle.getBundle("bubble");
//        NotificationCompat.BubbleMetadata.Builder bubbleBuilder = new NotificationCompat.BubbleMetadata.Builder();
//
//        bubbleBuilder.setIntent(bubbleIntent);
//
//        Bitmap bitmap = Tasks.await(getImageBitmapFromUrl(Objects.requireNonNull(bubbleBundle.getString("icon"))));
//
//        IconCompat bubbleIcon = IconCompat.createWithAdaptiveBitmap(bitmap);
//        bubbleBuilder.setIcon(bubbleIcon);
//
//        if (bubbleBundle.containsKey("height")) {
//          bubbleBuilder.setDesiredHeight(bubbleBundle.getInt("height"));
//        }
//
//        if (bubbleBundle.containsKey("autoExpand")) {
//          bubbleBuilder.setAutoExpandBubble(bubbleBundle.getBoolean("autoExpand"));
//        }
//
//        if (bubbleBundle.containsKey("suppressNotification")) {
//          bubbleBuilder.setSuppressNotification(bubbleBundle.getBoolean("suppressNotification"));
//        }
//
//        notificationBuilder.setBubbleMetadata(bubbleBuilder.build());
//      }


      if (androidOptionsBundle.containsKey("category")) {
        notificationBuilder.setCategory(androidOptionsBundle.getString("category"));
      }

      // Validate the specified channel exists
      if (androidOptionsBundle.containsKey("channelId") && Build.VERSION.SDK_INT >= 26) {
        NotificationChannel notificationChannel = getNotificationManager().getNotificationChannel(
          channelId
        );

        if (notificationChannel == null) {
          throw new InvalidNotificationParameterException(
            InvalidNotificationParameterException.CHANNEL_NOT_FOUND,
            String.format("Notification channel does not exist for the specified id '%s'.", channelId)
          );
        }
      }

      if (androidOptionsBundle.containsKey("color")) {
        int color = Color.parseColor(androidOptionsBundle.getString("color"));
        notificationBuilder.setColor(color);
      }

      if (androidOptionsBundle.containsKey("colorized")) {
        notificationBuilder.setColorized(androidOptionsBundle.getBoolean("colorized"));
      }

      if (androidOptionsBundle.containsKey("chronometerDirection")) {
        String direction = androidOptionsBundle.getString("chronometerDirection");
        if (Objects.requireNonNull(direction).equals("down")) {
          notificationBuilder.setChronometerCountDown(true);
        }
      }

      if (androidOptionsBundle.containsKey("defaults")) {
        ArrayList<Integer> defaultsArray = androidOptionsBundle.getIntegerArrayList("defaults");
        Integer defaults = null;

        for (Integer integer : Objects.requireNonNull(defaultsArray)) {
          if (integer != Notification.DEFAULT_ALL) {
            if (defaults == null) {
              defaults = integer;
            } else {
              defaults |= integer;
            }
          }
        }

        if (defaults != null) {
          notificationBuilder.setDefaults(defaults);
        }
      }

      if (androidOptionsBundle.containsKey("fullScreenAction")) {
        Bundle fullScreenActionBundle = androidOptionsBundle.getBundle("fullScreenAction");

        String launchActivity = null;

        if (Objects.requireNonNull(fullScreenActionBundle).containsKey("launchActivity")) {
          launchActivity = fullScreenActionBundle.getString("launchActivity");
        }

        Class launchActivityClass = getLaunchActivity(launchActivity);

        Intent contentIntent = new Intent(getApplicationContext(), launchActivityClass);
        contentIntent.putExtra("actionId", fullScreenActionBundle.getString("id"));
        contentIntent.putExtra("notificationBundle", notificationBundle);
        contentIntent.putExtra("launchActivity", launchActivity);

        if (fullScreenActionBundle.containsKey("reactComponent")) {
          contentIntent.putExtra("reactComponent", fullScreenActionBundle.getString("reactComponent"));
        }

        PendingIntent pendingContentIntent = PendingIntent.getActivity(
          getApplicationContext(),
          notificationHashCode,
          contentIntent,
          PendingIntent.FLAG_UPDATE_CURRENT
        );

        notificationBuilder.setFullScreenIntent(pendingContentIntent, true);
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

      if (androidOptionsBundle.containsKey("inputHistory")) {
        ArrayList<String> inputHistoryArray = androidOptionsBundle.getStringArrayList("inputHistory");
        CharSequence[] sequence = Objects.requireNonNull(inputHistoryArray).toArray(new CharSequence[inputHistoryArray.size()]);
        notificationBuilder.setRemoteInputHistory(sequence);
      }

      if (androidOptionsBundle.containsKey("largeIcon")) {
        Bitmap largeIcon = Tasks.await(
          getImageBitmapFromUrl(
            Objects.requireNonNull(androidOptionsBundle.getString("largeIcon"))
          )
        );

        if (largeIcon != null) {
          notificationBuilder.setLargeIcon(largeIcon);
        }
      }

      if (androidOptionsBundle.containsKey("lights")) {
        ArrayList lightList = Objects.requireNonNull(
          androidOptionsBundle.getParcelableArrayList("lights")
        );

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

      if (androidOptionsBundle.containsKey("importance")) {
        // Convert importance to priority
        int importance = (int) androidOptionsBundle.getDouble("priority");

        switch (importance) {
          case NotificationManagerCompat.IMPORTANCE_DEFAULT:
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
            break;
          case NotificationManagerCompat.IMPORTANCE_HIGH:
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
            break;
          case NotificationManagerCompat.IMPORTANCE_MIN:
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_LOW);
            break;
          case NotificationManagerCompat.IMPORTANCE_NONE:
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_MIN);
            break;
        }
      }

      if (androidOptionsBundle.containsKey("progress")) {
        Bundle progressBundle = Objects.requireNonNull(
          androidOptionsBundle.getBundle("progress")
        );

        int max = (int) progressBundle.getDouble("max");
        int current = (int) progressBundle.getDouble("current");
        boolean indeterminate = progressBundle.getBoolean("indeterminate");
        notificationBuilder.setProgress(max, current, indeterminate);
      }

      if (androidOptionsBundle.containsKey("remoteInputHistory")) {
        ArrayList remoteInputHistory = Objects.requireNonNull(
          androidOptionsBundle.getParcelableArrayList("remoteInputHistory")
        );
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
        ArrayList smallIconList = Objects.requireNonNull(
          androidOptionsBundle.getParcelableArrayList("smallIcon")
        );

        String smallIconRaw = (String) smallIconList.get(0);
        int smallIcon = getImageResourceId(smallIconRaw);

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
        Bundle styleBundle = Objects.requireNonNull(
          androidOptionsBundle.getBundle("style")
        );

        int type = (int) styleBundle.getDouble("type");
        NotificationCompat.Style style = null;

        switch (type) {
          case 0:
            style = Tasks.await(getBigPictureStyle(styleBundle));
            break;
          case 1:
            style = getBigTextStyle(styleBundle);
            break;
          case 2:
            style = getInboxStyle(styleBundle);
            break;
          case 3:
            style = Tasks.await(getMessagingStyle(styleBundle));
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
        ArrayList vibrationPattern = Objects.requireNonNull(
          androidOptionsBundle.getParcelableArrayList("vibrationPattern")
        );

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
    });
  }

  /**
   * Actions
   */
  private Task<List<NotificationCompat.Action>> getActions(ArrayList actionsList) {
    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, () -> {
      ArrayList<NotificationCompat.Action> actions = new ArrayList<>(actionsList.size());

      for (int i = 0; i < actionsList.size(); i++) {
        Bundle actionBundle = (Bundle) actionsList.get(i);
        Bundle onPressActionBundle = actionBundle.getBundle("onPressAction");

        PendingIntent actionPendingIntent = NotifeeReceiverService.createIntent(
          ACTION_NOTIFICATION_ACTION_PRESS_INTENT,
          new String[]{"notification", "action"},
          notificationBundle,
          onPressActionBundle
        );

        Bitmap bitmap = Tasks.await(getImageBitmapFromUrl(Objects.requireNonNull(actionBundle.getString("icon"))));

        NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(
          IconCompat.createWithAdaptiveBitmap(bitmap),
          actionBundle.getString("title"),
          actionPendingIntent
        );

        if (
          actionBundle.containsKey("input") &&
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH
        ) {
          Bundle inputBundle = Objects.requireNonNull(actionBundle.getBundle("input"));
          RemoteInput.Builder remoteInputBuilder = new RemoteInput.Builder(RECEIVER_REMOTE_INPUT_KEY);

          if (inputBundle.containsKey("allowFreeFormInput")) {
            remoteInputBuilder.setAllowFreeFormInput(inputBundle.getBoolean("allowFreeFormInput"));
          }

          if (inputBundle.containsKey("allowGeneratedReplies")) {
            actionBuilder.setAllowGeneratedReplies(inputBundle.getBoolean("allowGeneratedReplies"));
          }

          if (inputBundle.containsKey("placeholder")) {
            remoteInputBuilder.setLabel(inputBundle.getCharSequence("placeholder"));
          }

          if (inputBundle.containsKey("choices")) {
            ArrayList<String> choicesArray = inputBundle.getStringArrayList("choices");
            CharSequence[] choices = Objects.requireNonNull(choicesArray).toArray(new CharSequence[choicesArray.size()]);
            remoteInputBuilder.setChoices(choices);
          }

          if (inputBundle.containsKey("editableChoices")) {
            boolean editable = inputBundle.getBoolean("editableChoices");
            if (editable) {
              remoteInputBuilder.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_ENABLED);
            } else {
              remoteInputBuilder.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_DISABLED);
            }
          } else {
            remoteInputBuilder.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_AUTO);
          }

          actionBuilder.addRemoteInput(remoteInputBuilder.build());
        }

        actions.add(actionBuilder.build());
      }

      return actions;
    });
  }

  /**
   * BigPictureStyle
   */
  private Task<NotificationCompat.BigPictureStyle> getBigPictureStyle(Bundle bigPictureStyleBundle) {
    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, () -> {
      NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

      if (bigPictureStyleBundle.containsKey("picture")) {
        Bitmap picture = Tasks.await(
          getImageBitmapFromUrl(
            Objects.requireNonNull(bigPictureStyleBundle.getString("picture"))
          )
        );

        if (picture != null) {
          bigPictureStyle.bigPicture(picture);
        }
      }

      if (bigPictureStyleBundle.containsKey("largeIcon")) {
        Bitmap largeIcon = Tasks.await(
          getImageBitmapFromUrl(
            Objects.requireNonNull(bigPictureStyleBundle.getString("largeIcon"))
          )
        );

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
    });
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
   * MessagingStyle
   */
  private Task<NotificationCompat.MessagingStyle> getMessagingStyle(Bundle messagingStyleBundle) {
    return Tasks.call(NOTIFICATION_BUILD_EXECUTOR, () -> {
      Person person = Tasks.await(getPerson(Objects.requireNonNull(messagingStyleBundle.getBundle("person"))));

      NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(person);

      if (messagingStyleBundle.containsKey("title")) {
        messagingStyle = messagingStyle.setConversationTitle(messagingStyleBundle.getString("title"));
      }

      if (messagingStyleBundle.containsKey("group")) {
        messagingStyle = messagingStyle.setGroupConversation(messagingStyleBundle.getBoolean("group"));
      }

      ArrayList<Bundle> messages = messagingStyleBundle.getParcelableArrayList("messages");

      for (int i = 0; i < Objects.requireNonNull(messages).size(); i++) {
        Bundle message = messages.get(i);
        Person messagePerson = null;
        long timestamp = (long) message.getDouble("timestamp");

        if (message.containsKey("person")) {
          messagePerson = Tasks.await(getPerson(Objects.requireNonNull(message.getBundle("person"))));
        }

        messagingStyle = messagingStyle.addMessage(message.getString("text"), timestamp, messagePerson);
      }

      return messagingStyle;
    });
  }

  /**
   * Displays a notification in the app
   *
   * @return void
   */
  Task<Void> displayNotification() {
    return Tasks.call(NOTIFICATION_DISPLAY_EXECUTOR, () -> {
      Notification notification = Tasks.await(getNotification());

      // TODO removed TAG, causes cancelling issues. We can store it in the database anyway?
      getNotificationManagerCompat().notify(
        notificationHashCode,
        notification
      );

      sendDeliveredIntent();

      return null;
    });
  }

  /**
   * Displays a notification inside of a foreground service, the user can control this service
   * via their JS by registering a runner function with notifee
   *
   * @return void
   */
  Task<Void> displayForegroundServiceNotification() {
    return Tasks.call(NOTIFICATION_DISPLAY_EXECUTOR, () -> {
      Notification notification = Tasks.await(getNotification());

      Intent serviceIntent = new Intent(getApplicationContext(), NotifeeForegroundService.class);

      serviceIntent.setAction(START_FOREGROUND_SERVICE_ACTION);
      serviceIntent.putExtra("notificationBundle", notificationBundle);
      serviceIntent.putExtra("notification", notification);
      serviceIntent.putExtra("hash", notificationHashCode);

      ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);

      sendDeliveredIntent();

      return null;
    });
  }

  /**
   * Instantly triggers an intent to notify user that the notification has been delivered to the
   * device.
   */
  private void sendDeliveredIntent() {
    PendingIntent intent = NotifeeReceiverService.createIntent(
      ACTION_NOTIFICATION_DELIVERED_INTENT,
      new String[]{"notification"},
      notificationBundle
    );

    try {
      intent.send();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
