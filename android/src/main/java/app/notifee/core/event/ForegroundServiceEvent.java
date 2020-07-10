package app.notifee.core.event;

import app.notifee.core.KeepForSdk;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.NotificationModel;

@KeepForSdk
public class ForegroundServiceEvent {
  private final NotificationModel notification;
  private MethodCallResult<Void> result;
  private boolean completed = false;

  public ForegroundServiceEvent(
      NotificationModel notificationModel, MethodCallResult<Void> result) {
    this.notification = notificationModel;
    this.result = result;
  }

  @KeepForSdk
  public NotificationModel getNotification() {
    return notification;
  }

  @KeepForSdk
  public void setCompletionResult() {
    if (!completed) {
      completed = true;
      result.onComplete(null, null);
    }
  }
}
