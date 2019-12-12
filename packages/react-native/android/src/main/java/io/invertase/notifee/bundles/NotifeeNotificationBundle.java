package io.invertase.notifee.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Objects;

import javax.annotation.Nullable;

public class NotifeeNotificationBundle {

  private Bundle mNotificationBundle;

  public NotifeeNotificationBundle(Bundle bundle) {
    mNotificationBundle = bundle;
  }

  public @NonNull int getHashCode() {
    return getId().hashCode();
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mNotificationBundle.getString("id"));
  }

  public @Nullable String getTitle() {
    return mNotificationBundle.getString("title");
  }

  public @Nullable String getSubTitle() {
    return mNotificationBundle.getString("subtitle");
  }

  public @Nullable String getBody() {
    return mNotificationBundle.getString("body");
  }

  public @NonNull NotifeeNotificationAndroidBundle getAndroidBundle() {
    return new NotifeeNotificationAndroidBundle(
      mNotificationBundle.getBundle("android")
    );
  }

  public @Nullable Bundle getData() {
    Bundle data = mNotificationBundle.getBundle("data");
    if (data != null) return (Bundle) data.clone();
    return null;
  }

}
