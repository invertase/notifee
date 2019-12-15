package app.notifee.core.events;

import android.os.Bundle;

import androidx.annotation.Nullable;

import app.notifee.core.bundles.NotificationBundle;

public class NotificationEvent {
  public final static String TYPE_DELIVERED = "delivered";
  public final static String TYPE_DISMISSED = "dismissed";

  private final String type;
  private final Bundle extras;
  private final NotificationBundle notification;

  private NotificationEvent(String type, NotificationBundle bundle) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
  }

  private NotificationEvent(String type, NotificationBundle bundle, Bundle extras) {
    this.type = type;
    this.notification = bundle;
    this.extras = extras;
  }

  public String getType() {
    return type;
  }

  public NotificationBundle getNotification() {
    return notification;
  }

  @Nullable
  public Bundle getExtras() {
    return extras;
  }
}
