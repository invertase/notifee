package app.notifee.core.model;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.Person;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import app.notifee.core.Logger;
import app.notifee.core.utility.ResourceUtils;

@Keep
public class NotificationAndroidPersonModel {
  private static final String TAG = "NotificationAndroidPersonModel";

  private Bundle mNotificationAndroidPersonBundle;

  private NotificationAndroidPersonModel(Bundle actionBundle) {
    mNotificationAndroidPersonBundle = actionBundle;
  }

  public static NotificationAndroidPersonModel fromBundle(Bundle actionBundle) {
    return new NotificationAndroidPersonModel(actionBundle);
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidPersonBundle.clone();
  }

  /**
   * Gets the id of the person
   *
   * @return String
   */
  public @NonNull
  String getId() {
    return mNotificationAndroidPersonBundle.getString("id");
  }

  /**
   * Gets the name of the person
   *
   * @return String
   */
  public @Nullable
  String getName() {
    return mNotificationAndroidPersonBundle.getString("name");
  }

  /**
   * Gets the bot of the person
   *
   * @return Boolean
   */
  public @NonNull
  Boolean getBot() {
    return mNotificationAndroidPersonBundle.getBoolean("bot", false);
  }

  /**
   * Gets the bot of the person
   *
   * @return Boolean
   */
  public @NonNull
  Boolean getImportant() {
    return mNotificationAndroidPersonBundle.getBoolean("important", false);
  }

  /**
   * Gets the icon of the person
   *
   * @return String
   */
  public @Nullable
  String getIcon() {
    return mNotificationAndroidPersonBundle.getString("icon");
  }

  /**
   * Gets the icon of the person
   *
   * @return String
   */
  public @Nullable
  String getUri() {
    return mNotificationAndroidPersonBundle.getString("uri");
  }

  /**
   * Converts a person bundle from JS into a Person
   *
   * @return Person
   */
  public
  Task<Person> buildPersonTask(Executor executor) {
    return Tasks.call(
      executor,
      () -> {
        Person.Builder personBuilder = new Person.Builder();
        personBuilder.setName(getName());

        if (mNotificationAndroidPersonBundle.containsKey("id")) {
          personBuilder.setKey(getId());
        }

        personBuilder.setBot(getBot());
        personBuilder.setImportant(getImportant());


        if (mNotificationAndroidPersonBundle.containsKey("icon")) {
          String personIcon = Objects.requireNonNull(getIcon());
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

        if (mNotificationAndroidPersonBundle.containsKey("uri")) {
          personBuilder.setUri(getUri());
        }

        return personBuilder.build();
      });
  }
}
