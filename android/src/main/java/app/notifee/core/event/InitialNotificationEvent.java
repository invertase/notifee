package app.notifee.core.event;

import android.os.Bundle;

import app.notifee.core.model.NotificationModel;

public class InitialNotificationEvent {

  private NotificationModel notificationModel;
  private Bundle extras;

  public InitialNotificationEvent(NotificationModel notificationModel, Bundle extras) {
    this.notificationModel = notificationModel;
    this.extras = extras;
  }

  public NotificationModel getNotificationModel() {
    return notificationModel;
  }

  public Bundle getExtras() {
    return extras;
  }
}
