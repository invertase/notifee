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
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.SettableFuture;
import java.util.ArrayList;
import java.util.Objects;
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
  private static ListenableFuture<Person> getPerson(
    ListeningExecutorService lExecutor, Bundle personBundle) {
    return lExecutor.submit(
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
                      ResourceUtils.getImageBitmapFromUrl(personIcon)
                          .get(10, TimeUnit.SECONDS);
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
  public ListenableFuture<NotificationCompat.Style> getStyleTask(
    ListeningExecutorService lExecutor) {
    int type = ObjectUtils.getInt(mNotificationAndroidStyleBundle.get("type"));
    ListenableFuture<NotificationCompat.Style> styleTask = null;

    switch (type) {
      case 0:
        styleTask = getBigPictureStyleTask(lExecutor);
        break;
      case 1:
        styleTask = Futures.immediateFuture(getBigTextStyle());
        break;
      case 2:
        styleTask = Futures.immediateFuture(getInboxStyle());
        break;
      case 3:
        styleTask = getMessagingStyleTask(lExecutor);
        break;
    }

    return styleTask;
  }

  /**
   * Gets a BigPictureStyle for a notification
   *
   * @return
   */
  private ListenableFuture<NotificationCompat.Style> getBigPictureStyleTask(
    ListeningExecutorService lExecutor) {
    return lExecutor.submit(
        () -> {
          NotificationCompat.BigPictureStyle bigPictureStyle =
              new NotificationCompat.BigPictureStyle();

          if (mNotificationAndroidStyleBundle.containsKey("picture")) {
            String picture =
                Objects.requireNonNull(mNotificationAndroidStyleBundle.getString("picture"));
            Bitmap pictureBitmap = null;

            try {
              pictureBitmap = 
                  ResourceUtils.getImageBitmapFromUrl(picture)
                      .get(10, TimeUnit.SECONDS);
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
              bigPictureStyle.bigLargeIcon(null);
            }
          }

          if (largeIcon != null) {
            Bitmap largeIconBitmap = null;

            try {
              largeIconBitmap = 
                  ResourceUtils.getImageBitmapFromUrl(largeIcon)
                    .get(10, TimeUnit.SECONDS);
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
  private ListenableFuture<NotificationCompat.Style> getMessagingStyleTask(
    ListeningExecutorService lExecutor) {
    return lExecutor.submit(
        () -> {
          Person person =
              getPerson(lExecutor, 
                  Objects.requireNonNull(
                      mNotificationAndroidStyleBundle.getBundle("person"))
              ).get(20, TimeUnit.SECONDS);

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
                  getPerson(lExecutor,
                      Objects.requireNonNull(message.getBundle("person"))
                  ).get(20, TimeUnit.SECONDS);
            }

            messagingStyle =
                messagingStyle.addMessage(
                    TextUtils.fromHtml(message.getString("text")), timestamp, messagePerson);
          }

          return messagingStyle;
        });
  }
}
