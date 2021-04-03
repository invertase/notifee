package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

@Keep
public class NotificationAndroidFullScreenActionModel {
  private Bundle mNotificationAndroidFullScreenActionBundle;

  private NotificationAndroidFullScreenActionModel(Bundle bundle) {
    mNotificationAndroidFullScreenActionBundle = bundle;
  }

  public static NotificationAndroidFullScreenActionModel fromBundle(Bundle bundle) {
    return new NotificationAndroidFullScreenActionModel(bundle);
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidFullScreenActionBundle.clone();
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mNotificationAndroidFullScreenActionBundle.getString("id"));
  }

  public @Nullable String getLaunchActivity() {
    return mNotificationAndroidFullScreenActionBundle.getString("launchActivity");
  }

  public @Nullable String getMainComponent() {
    return mNotificationAndroidFullScreenActionBundle.getString("mainComponent");
  }
}
