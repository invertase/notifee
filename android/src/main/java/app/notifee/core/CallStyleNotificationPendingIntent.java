package app.notifee.core;

import static app.notifee.core.event.NotificationEvent.TYPE_ACTION_PRESS;

import android.app.PendingIntent;
import android.os.Bundle;
import app.notifee.core.model.NotificationModel;
import java.util.Objects;

public class CallStyleNotificationPendingIntent {

  public static PendingIntent getAnswerIntent(
      Bundle callTypeActionsBundle, NotificationModel notificationModel) {
    Bundle answerActionBundle =
        Objects.requireNonNull(callTypeActionsBundle.getBundle("answerAction"));
    return getPendingIntent(answerActionBundle, notificationModel.getHashCode(), notificationModel);
  }

  public static PendingIntent getDeclineIntent(
      Bundle callTypeActionsBundle, NotificationModel notificationModel) {
    Bundle declineActionBundle =
        Objects.requireNonNull(callTypeActionsBundle.getBundle("declineAction"));
    return getPendingIntent(
        declineActionBundle, notificationModel.getHashCode() + 1, notificationModel);
  }

  public static PendingIntent getHangupIntent(
      Bundle callTypeActionsBundle, NotificationModel notificationModel) {
    Bundle hangUpActionBundle =
        Objects.requireNonNull(callTypeActionsBundle.getBundle("hangUpAction"));
    return getPendingIntent(
        hangUpActionBundle, notificationModel.getHashCode() + 1, notificationModel);
  }

  private static PendingIntent getPendingIntent(
      Bundle hangUpActionBundle, int notificationModel, NotificationModel notificationModel1) {
    Bundle pressActionBundle = hangUpActionBundle.getBundle("pressAction");
    return NotificationPendingIntent.createIntent(
        notificationModel,
        pressActionBundle,
        TYPE_ACTION_PRESS,
        new String[] {"notification", "pressAction"},
        notificationModel1.toBundle(),
        pressActionBundle);
  }
}
