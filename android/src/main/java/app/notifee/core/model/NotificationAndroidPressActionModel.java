package app.notifee.core.model;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

@Keep
public class NotificationAndroidPressActionModel {

  private Bundle mNotificationAndroidPressActionBundle;

  private NotificationAndroidPressActionModel(Bundle pressActionBundle) {
    mNotificationAndroidPressActionBundle = pressActionBundle;
  }

  public static NotificationAndroidPressActionModel fromBundle(Bundle pressActionBundle) {
    return new NotificationAndroidPressActionModel(pressActionBundle);
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidPressActionBundle.clone();
  }

  public @NonNull
  String getId() {
    return Objects.requireNonNull(mNotificationAndroidPressActionBundle.getString("id"));
  }

  public @Nullable
  String getLaunchActivity() {
    return mNotificationAndroidPressActionBundle.getString("launchActivity");
  }

  public @Nullable
  String getMainComponent() {
    return mNotificationAndroidPressActionBundle.getString("mainComponent");
  }
}
