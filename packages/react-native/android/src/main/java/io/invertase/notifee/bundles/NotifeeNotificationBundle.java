package io.invertase.notifee.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.invertase.notifee.core.BundleJSONConverter;

public class NotifeeNotificationBundle {

  private Bundle mNotificationBundle;

  public NotifeeNotificationBundle(Bundle bundle) {
    mNotificationBundle = bundle;
  }

  public @Nullable
  static NotifeeNotificationBundle fromJSONString(String jsonString) {
    try {
      JSONObject jsonObject = new JSONObject(jsonString);
      Bundle notificationBundle = BundleJSONConverter.convertToBundle(jsonObject);
      return new NotifeeNotificationBundle(notificationBundle);
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
  NotifeeNotificationAndroidBundle getAndroidBundle() {
    return new NotifeeNotificationAndroidBundle(
      mNotificationBundle.getBundle("android")
    );
  }

  public @Nullable
  Bundle getData() {
    Bundle data = mNotificationBundle.getBundle("data");
    if (data != null) return (Bundle) data.clone();
    return null;
  }

  public String toJSONString() {
    try {
      return BundleJSONConverter.convertToJSON(mNotificationBundle).toString();
    } catch (JSONException e) {
      return "";
    }
  }

}
