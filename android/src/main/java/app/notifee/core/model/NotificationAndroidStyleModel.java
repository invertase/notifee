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

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;
import app.notifee.core.Logger;
import app.notifee.core.utility.ObjectUtils;
import app.notifee.core.utility.ResourceUtils;
import app.notifee.core.utility.TextUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Keep
public class NotificationAndroidStyleModel {
  private static final String TAG = "NotificationAndroidStyle";
  private Bundle mNotificationAndroidStyleBundle;

  private NotificationAndroidStyleModel(Bundle styleBundle) {
    mNotificationAndroidStyleBundle = styleBundle;
  }

  public static NotificationAndroidStyleModel fromBundle(Bundle styleBundle) {
    return new NotificationAndroidStyleModel(styleBundle);
  }

  /**
   * Converts a person bundle from JS into a Person
   *
   * @param personBundle
   * @return
   */
  private static Task<Person> getPerson(Executor executor, Bundle personBundle) {
    return Tasks.call(
        executor,
        () -> {
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
            String personIcon = Objects.requireNonNull(personBundle.getString("icon"));
            Bitmap personIconBitmap = null;

            try {
              personIconBitmap =
                  Tasks.await(
                      ResourceUtils.getImageBitmapFromUrl(personIcon), 10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
              Logger.e(
                  TAG,
                  "Timeout occurred whilst trying to retrieve a person icon: " + personIcon,
                  e);
            } catch (Exception e) {
              Logger.e(
                  TAG,
                  "An error occurred whilst trying to retrieve a person icon: " + personIcon,
                  e);
            }

            if (personIconBitmap != null) {
              personBuilder.setIcon(IconCompat.createWithAdaptiveBitmap(personIconBitmap));
            }
          }

          if (personBundle.containsKey("uri")) {
            personBuilder.setUri(personBundle.getString("uri"));
          }

          return personBuilder.build();
        });
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidStyleBundle.clone();
  }

  @Nullable
  public Task<NotificationCompat.Style> getStyleTask(Executor executor) {
    int type = ObjectUtils.getInt(mNotificationAndroidStyleBundle.get("type"));
    Task<NotificationCompat.Style> styleTask = null;

    switch (type) {
      case 0:
        styleTask = getBigPictureStyleTask(executor);
        break;
      case 1:
        styleTask = Tasks.forResult(getBigTextStyle());
        break;
      case 2:
        styleTask = Tasks.forResult(getInboxStyle());
        break;
      case 3:
        styleTask = getMessagingStyleTask(executor);
        break;
    }

    return styleTask;
  }

  /**
   * Gets a BigPictureStyle for a notification
   *
   * @return
   */
  private Task<NotificationCompat.Style> getBigPictureStyleTask(Executor executor) {
    return Tasks.call(
        executor,
        () -> {
          NotificationCompat.BigPictureStyle bigPictureStyle =
              new NotificationCompat.BigPictureStyle();

          if (mNotificationAndroidStyleBundle.containsKey("picture")) {
            String picture =
                Objects.requireNonNull(mNotificationAndroidStyleBundle.getString("picture"));
            Bitmap pictureBitmap = null;

            try {
              pictureBitmap =
                  Tasks.await(ResourceUtils.getImageBitmapFromUrl(picture), 10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
              Logger.e(
                  TAG,
                  "Timeout occurred whilst trying to retrieve a big picture style image: "
                      + picture,
                  e);
            } catch (Exception e) {
              Logger.e(
                  TAG,
                  "An error occurred whilst trying to retrieve a big picture style image: "
                      + picture,
                  e);
            }

            if (pictureBitmap != null) {
              bigPictureStyle.bigPicture(pictureBitmap);
            }
          }

          String largeIcon = null;

          if (mNotificationAndroidStyleBundle.containsKey("largeIcon")) {
            largeIcon = mNotificationAndroidStyleBundle.getString("largeIcon");

            // largeIcon has been specified to be null for BigPicture
            if (largeIcon == null) {
              bigPictureStyle.bigLargeIcon((Bitmap) null);
            }
          }

          if (largeIcon != null) {
            Bitmap largeIconBitmap = null;

            try {
              largeIconBitmap =
                  Tasks.await(ResourceUtils.getImageBitmapFromUrl(largeIcon), 10, TimeUnit.SECONDS);
            } catch (TimeoutException e) {
              Logger.e(
                  TAG,
                  "Timeout occurred whilst trying to retrieve a big picture style large icon: "
                      + largeIcon,
                  e);
            } catch (Exception e) {
              Logger.e(
                  TAG,
                  "An error occurred whilst trying to retrieve a big picture style large icon: "
                      + largeIcon,
                  e);
            }

            if (largeIconBitmap != null) {
              bigPictureStyle.bigLargeIcon(largeIconBitmap);
            }
          }

          if (mNotificationAndroidStyleBundle.containsKey("title")) {
            bigPictureStyle =
                bigPictureStyle.setBigContentTitle(
                    TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("title")));
          }

          if (mNotificationAndroidStyleBundle.containsKey("summary")) {
            bigPictureStyle =
                bigPictureStyle.setSummaryText(
                    TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("summary")));
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
      bigTextStyle =
          bigTextStyle.bigText(
              TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("text")));
    }

    if (mNotificationAndroidStyleBundle.containsKey("title")) {
      bigTextStyle =
          bigTextStyle.setBigContentTitle(
              TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("title")));
    }

    if (mNotificationAndroidStyleBundle.containsKey("summary")) {
      bigTextStyle =
          bigTextStyle.setSummaryText(
              TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("summary")));
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
      inputStyle =
          inputStyle.setBigContentTitle(
              TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("title")));
    }

    if (mNotificationAndroidStyleBundle.containsKey("summary")) {
      inputStyle =
          inputStyle.setSummaryText(
              TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("summary")));
    }

    ArrayList<String> lines = mNotificationAndroidStyleBundle.getStringArrayList("lines");

    for (int i = 0; i < Objects.requireNonNull(lines).size(); i++) {
      String line = lines.get(i);
      inputStyle = inputStyle.addLine(TextUtils.fromHtml(line));
    }

    return inputStyle;
  }

  /** Gets a MessagingStyle for a notification */
  private Task<NotificationCompat.Style> getMessagingStyleTask(Executor executor) {
    return Tasks.call(
        executor,
        () -> {
          Person person =
              Tasks.await(
                  getPerson(
                      executor,
                      Objects.requireNonNull(mNotificationAndroidStyleBundle.getBundle("person"))),
                  20,
                  TimeUnit.SECONDS);

          NotificationCompat.MessagingStyle messagingStyle =
              new NotificationCompat.MessagingStyle(person);

          if (mNotificationAndroidStyleBundle.containsKey("title")) {
            messagingStyle =
                messagingStyle.setConversationTitle(
                    TextUtils.fromHtml(mNotificationAndroidStyleBundle.getString("title")));
          }

          if (mNotificationAndroidStyleBundle.containsKey("group")) {
            messagingStyle =
                messagingStyle.setGroupConversation(
                    mNotificationAndroidStyleBundle.getBoolean("group"));
          }

          ArrayList<Bundle> messages =
              mNotificationAndroidStyleBundle.getParcelableArrayList("messages");

          for (int i = 0; i < Objects.requireNonNull(messages).size(); i++) {
            Bundle message = messages.get(i);
            Person messagePerson = null;
            long timestamp = ObjectUtils.getLong(message.get("timestamp"));

            if (message.containsKey("person")) {
              messagePerson =
                  Tasks.await(
                      getPerson(executor, Objects.requireNonNull(message.getBundle("person"))),
                      20,
                      TimeUnit.SECONDS);
            }

            messagingStyle =
                messagingStyle.addMessage(
                    TextUtils.fromHtml(message.getString("text")), timestamp, messagePerson);
          }

          return messagingStyle;
        });
  }
}
