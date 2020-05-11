package app.notifee.core.model;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import app.notifee.core.KeepForSdk;

@KeepForSdk
public class NotificationModel {
  private Bundle mNotificationBundle;

  private NotificationModel(Bundle bundle) {
    mNotificationBundle = bundle;
  }

  public static NotificationModel fromBundle(@NonNull Bundle bundle) {
    return new NotificationModel(bundle);
  }

  public @NonNull
  Integer getHashCode() {
    return getId().hashCode();
  }

  public @NonNull
  String getId() {
    return Objects.requireNonNull(mNotificationBundle.getString("id"));
  }

  public @Nullable
  String getTitle() {
    return mNotificationBundle.getString("title");
  }

  public @Nullable
  String getSubTitle() {
    return mNotificationBundle.getString("subtitle");
  }

  public @Nullable
  String getBody() {
    return mNotificationBundle.getString("body");
  }

  public @NonNull
  NotificationAndroidModel getAndroid() {
    return NotificationAndroidModel.fromBundle(
      mNotificationBundle.getBundle("android")
    );
  }

  public @NonNull
  Bundle getData() {
    Bundle data = mNotificationBundle.getBundle("data");
    if (data != null) return (Bundle) data.clone();
    return new Bundle();
  }

  @KeepForSdk
  public Bundle toBundle() {
    return (Bundle) mNotificationBundle.clone();
  }
}
