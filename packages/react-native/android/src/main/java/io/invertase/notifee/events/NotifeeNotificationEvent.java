package io.invertase.notifee.events;

import android.os.Bundle;

import androidx.annotation.Nullable;

import io.invertase.notifee.bundles.NotifeeNotificationBundle;

public class NotifeeNotificationEvent {
  public final static String DELIVERED = "delivered";

  private final String type;
  private final Bundle extras;
  private final NotifeeNotificationBundle notification;

  public NotifeeNotificationEvent(String type, NotifeeNotificationBundle bundle) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
  }

  public NotifeeNotificationEvent(String type, NotifeeNotificationBundle bundle, Bundle extras) {
    this.type = type;
    this.notification = bundle;
    this.extras = extras;
  }

  public String getType() {
    return type;
  }

  public NotifeeNotificationBundle getNotification() {
    return notification;
  }

  @Nullable
  public Bundle getExtras() {
    return extras;
  }
}
