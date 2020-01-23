package app.notifee.core.events;

import android.os.Bundle;

import app.notifee.core.bundles.NotificationBundle;

public class InitialNotificationEvent {

  private NotificationBundle notificationBundle;
  private Bundle extras;

  public InitialNotificationEvent(NotificationBundle notificationBundle, Bundle extras) {
    this.notificationBundle = notificationBundle;
    this.extras = extras;
  }

  public NotificationBundle getNotificationBundle() {
    return notificationBundle;
  }

  public Bundle getExtras() {
    return extras;
  }
}
