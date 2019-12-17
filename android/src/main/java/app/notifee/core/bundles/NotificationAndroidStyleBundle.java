package app.notifee.core.bundles;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Objects;

import app.notifee.core.utils.ResourceUtils;

@Keep
public class NotificationAndroidStyleBundle {

  private Bundle mNotificationAndroidStyleBundle;

  private NotificationAndroidStyleBundle(Bundle styleBundle) {
    mNotificationAndroidStyleBundle = styleBundle;
  }

  public static NotificationAndroidStyleBundle fromBundle(Bundle styleBundle) {
    return new NotificationAndroidStyleBundle(styleBundle);
  }

  /**
   * Converts a person bundle from JS into a Person
   *
   * @param personBundle
   * @return
   */
  public static Task<Person> getPerson(Bundle personBundle) {
    return Tasks.call(() -> {
      Person.Builder personBuilder = new Person.Builder();

      personBuilder.setName(personBundle.getString("name"));

      if (personBundle.containsKey("id")) {
        personBuilder.setKey(personBundle.getString("id"));
      }

      if (personBundle.containsKey("bot")) {
        personBuilder.setBot(personBundle.getBoolean("bot"));
      }

      if (personBundle.containsKey("important")) {
        personBuilder.setImportant(personBundle.getBoolean("important"));
      }

      if (personBundle.containsKey("icon")) {
        Bitmap icon = Tasks.await(
          ResourceUtils.getImageBitmapFromUrl(
            Objects.requireNonNull(personBundle.getString("icon"))
          )
        );

        if (icon != null) {
          personBuilder.setIcon(IconCompat.createWithAdaptiveBitmap(icon));
        }
      }


      if (personBundle.containsKey("uri")) {
        personBuilder.setUri(personBundle.getString("uri"));
      }

      return personBuilder.build();
    });
  }

  public @Nullable
  Task<NotificationCompat.Style> getStyle() {
    // todo executor
    return Tasks.call(() -> {
      int type = (int) mNotificationAndroidStyleBundle.getDouble("type");
      NotificationCompat.Style style = null;

      switch (type) {
        case 0:
          style = Tasks.await(getBigPictureStyle());
          break;
        case 1:
          style = getBigTextStyle();
          break;
        case 2:
          style = getInboxStyle();
          break;
        case 3:
          style = Tasks.await(getMessagingStyle());
          break;
      }

      return style;
    });
  }

  /**
   * Gets a BigPictureStyle for a notification
   *
   * @return
   */
  private Task<NotificationCompat.BigPictureStyle> getBigPictureStyle() {
    // todo executor
    return Tasks.call(() -> {
      NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();

      if (mNotificationAndroidStyleBundle.containsKey("picture")) {
        Bitmap picture = Tasks.await(
          ResourceUtils.getImageBitmapFromUrl(
            Objects.requireNonNull(mNotificationAndroidStyleBundle.getString("picture"))
          )
        );

        if (picture != null) {
          bigPictureStyle.bigPicture(picture);
        }
      }

      if (mNotificationAndroidStyleBundle.containsKey("largeIcon")) {
        Bitmap largeIcon = Tasks.await(
          ResourceUtils.getImageBitmapFromUrl(
            Objects.requireNonNull(mNotificationAndroidStyleBundle.getString("largeIcon"))
          )
        );

        if (largeIcon != null) {
          bigPictureStyle.bigLargeIcon(largeIcon);
        }
      }

      if (mNotificationAndroidStyleBundle.containsKey("title")) {
        bigPictureStyle = bigPictureStyle.setBigContentTitle(mNotificationAndroidStyleBundle.getString("title"));
      }

      if (mNotificationAndroidStyleBundle.containsKey("summary")) {
        bigPictureStyle = bigPictureStyle.setSummaryText(mNotificationAndroidStyleBundle.getString("summary"));
      }

      return bigPictureStyle;
    });
  }

  /**
   * Gets a BigTextStyle for a notification
   *
   * @return NotificationCompat.BigTextStyle
   */
  private NotificationCompat.BigTextStyle getBigTextStyle() {
    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();

    if (mNotificationAndroidStyleBundle.containsKey("text")) {
      bigTextStyle = bigTextStyle.bigText(mNotificationAndroidStyleBundle.getString("text"));
    }

    if (mNotificationAndroidStyleBundle.containsKey("title")) {
      bigTextStyle = bigTextStyle.setBigContentTitle(mNotificationAndroidStyleBundle.getString("title"));
    }

    if (mNotificationAndroidStyleBundle.containsKey("summary")) {
      bigTextStyle = bigTextStyle.setSummaryText(mNotificationAndroidStyleBundle.getString("summary"));
    }

    return bigTextStyle;
  }

  /**
   * Gets an InboxStyle for a notification
   *
   * @return NotificationCompat.InboxStyle
   */
  private NotificationCompat.InboxStyle getInboxStyle() {
    NotificationCompat.InboxStyle inputStyle = new NotificationCompat.InboxStyle();

    if (mNotificationAndroidStyleBundle.containsKey("title")) {
      inputStyle = inputStyle.setBigContentTitle(mNotificationAndroidStyleBundle.getString("title"));
    }

    if (mNotificationAndroidStyleBundle.containsKey("summary")) {
      inputStyle = inputStyle.setSummaryText(mNotificationAndroidStyleBundle.getString("summary"));
    }

    ArrayList<String> lines = mNotificationAndroidStyleBundle.getStringArrayList("lines");

    for (int i = 0; i < Objects.requireNonNull(lines).size(); i++) {
      String line = lines.get(i);
      inputStyle = inputStyle.addLine(line);
    }

    return inputStyle;
  }

  /**
   * Gets a MessagingStyle for a notification
   *
   * @return Task<NotificationCompat.MessagingStyle>
   */
  private Task<NotificationCompat.MessagingStyle> getMessagingStyle() {
    // todo executor
    return Tasks.call(() -> {
      Person person = Tasks.await(getPerson(Objects.requireNonNull(mNotificationAndroidStyleBundle.getBundle("person"))));

      NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(person);

      if (mNotificationAndroidStyleBundle.containsKey("title")) {
        messagingStyle = messagingStyle.setConversationTitle(mNotificationAndroidStyleBundle.getString("title"));
      }

      if (mNotificationAndroidStyleBundle.containsKey("group")) {
        messagingStyle = messagingStyle.setGroupConversation(mNotificationAndroidStyleBundle.getBoolean("group"));
      }

      ArrayList<Bundle> messages = mNotificationAndroidStyleBundle.getParcelableArrayList("messages");

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
}
