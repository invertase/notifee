package io.invertase.notifee.events;

import io.invertase.notifee.bundles.NotifeeNotificationBundle;

public class NotifeeNotificationEvent {
  private final NotifeeNotificationBundle notification;
  private String type;

  public NotifeeNotificationEvent(String type, NotifeeNotificationBundle bundle) {
    this.type = type;
    this.notification = bundle;
  }

  public String getType() {
    return type;
  }

  public NotifeeNotificationBundle getNotification() {
    return notification;
  }
}
