package io.invertase.notifee.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class NotifeeNotificationAndroidPressActionBundle {

  private Bundle mNotificationAndroidPressActionBundle;

  NotifeeNotificationAndroidPressActionBundle(Bundle onPressActionBundle) {
    mNotificationAndroidPressActionBundle = onPressActionBundle;
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mNotificationAndroidPressActionBundle.getString("id"));
  }

  public @Nullable String getLaunchActivity() {
    return mNotificationAndroidPressActionBundle.getString("launchActivity");
  }

  public @Nullable String getReactComponent() {
    return mNotificationAndroidPressActionBundle.getString("reactComponent");
  }
}
