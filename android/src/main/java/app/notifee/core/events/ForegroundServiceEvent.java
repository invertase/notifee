package app.notifee.core.events;

import app.notifee.core.KeepForSdk;
import app.notifee.core.MethodCallResult;
import app.notifee.core.bundles.NotificationBundle;

@KeepForSdk
public class ForegroundServiceEvent {
  private final NotificationBundle notification;
  private MethodCallResult<Void> result;
  private boolean completed = false;

  public ForegroundServiceEvent(NotificationBundle notificationBundle, MethodCallResult<Void> result) {
    this.notification = notificationBundle;
    this.result = result;
  }

  @KeepForSdk
  public NotificationBundle getNotification() {
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
