package app.notifee.core.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import app.notifee.core.KeepForSdk;
import app.notifee.core.utils.JSONUtils;

@KeepForSdk
public class NotificationBundle {
  private Bundle mNotificationBundle;

  private NotificationBundle(Bundle bundle) {
    mNotificationBundle = bundle;
  }

  @KeepForSdk
  public static NotificationBundle fromBundle(@NonNull Bundle bundle) {
    return new NotificationBundle(bundle);
  }

  /**
   * Returns null if JSON failed to parse.
   */
  @KeepForSdk
  public @Nullable
  static NotificationBundle fromJSONString(@NonNull String jsonString) {
    try {
      JSONObject jsonObject = new JSONObject(jsonString);
      Bundle notificationBundle = JSONUtils.convertToBundle(jsonObject);
      return new NotificationBundle(notificationBundle);
    } catch (JSONException jsonException) {
      return null;
    }
  }

  @KeepForSdk
  public @NonNull
  Integer getHashCode() {
    return getId().hashCode();
  }

  @KeepForSdk
  public @NonNull
  String getId() {
    return Objects.requireNonNull(mNotificationBundle.getString("id"));
  }

  @KeepForSdk
  public @Nullable
  String getTitle() {
    return mNotificationBundle.getString("title");
  }

  @KeepForSdk
  public @Nullable
  String getSubTitle() {
    return mNotificationBundle.getString("subtitle");
  }

  @KeepForSdk
  public @Nullable
  String getBody() {
    return mNotificationBundle.getString("body");
  }

  @KeepForSdk
  public @NonNull
  NotificationAndroidBundle getAndroidBundle() {
    return NotificationAndroidBundle.fromBundle(
      mNotificationBundle.getBundle("android")
    );
  }

  @KeepForSdk
  public @Nullable
  Bundle getData() {
    Bundle data = mNotificationBundle.getBundle("data");
    if (data != null) return (Bundle) data.clone();
    return null;
  }

  /**
   * Returns null if failed to convert Bundle to JSON string
   */
  @KeepForSdk
  public @Nullable
  String toJSONString() {
    try {
      return JSONUtils.convertToJSON(mNotificationBundle).toString();
    } catch (JSONException e) {
      return null;
    }
  }

  @KeepForSdk
  public Bundle toBundle() {
    return (Bundle) mNotificationBundle.clone();
  }
}
