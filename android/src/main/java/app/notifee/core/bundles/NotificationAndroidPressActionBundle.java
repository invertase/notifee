package app.notifee.core.bundles;

import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

@Keep
public class NotificationAndroidPressActionBundle {

  private Bundle mNotificationAndroidPressActionBundle;

  private NotificationAndroidPressActionBundle(Bundle pressActionBundle) {
    mNotificationAndroidPressActionBundle = pressActionBundle;
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidPressActionBundle.clone();
  }

  public static NotificationAndroidPressActionBundle fromBundle(Bundle pressActionBundle) {
    return new NotificationAndroidPressActionBundle(pressActionBundle);
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
  String getReactComponent() {
    return mNotificationAndroidPressActionBundle.getString("reactComponent");
  }
}
