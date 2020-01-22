package app.notifee.core.events;

import app.notifee.core.bundles.NotificationBundle;

public class InitialNotificationEvent {

  private NotificationBundle notificationBundle;

  public InitialNotificationEvent(NotificationBundle notificationBundle) {
    this.notificationBundle = notificationBundle;
  }

  public NotificationBundle getNotificationBundle() {
    return notificationBundle;
  }
}
