package app.notifee.core.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import app.notifee.core.KeepForSdk;
import app.notifee.core.utils.JSONUtils;

public class NotificationBundle {
  private Bundle mNotificationBundle;

  private NotificationBundle(Bundle bundle) {
    mNotificationBundle = bundle;
  }

  public static NotificationBundle fromBundle(@NonNull Bundle bundle) {
    return new NotificationBundle(bundle);
  }

  /**
   * Returns null if JSON failed to parse.
   */
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
  NotificationAndroidBundle getAndroidBundle() {
    return NotificationAndroidBundle.fromBundle(
      mNotificationBundle.getBundle("android")
    );
  }

  public @Nullable
  Bundle getData() {
    Bundle data = mNotificationBundle.getBundle("data");
    if (data != null) return (Bundle) data.clone();
    return null;
  }

  /**
   * Returns null if failed to convert Bundle to JSON string
   */
  public @Nullable
  String toJSONString() {
    try {
      return JSONUtils.convertToJSON(mNotificationBundle).toString();
    } catch (JSONException e) {
      return null;
    }
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationBundle.clone();
  }
}
