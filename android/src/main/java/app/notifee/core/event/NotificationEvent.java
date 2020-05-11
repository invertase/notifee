package app.notifee.core.event;

import android.os.Bundle;

import androidx.annotation.Nullable;

import app.notifee.core.KeepForSdk;
import app.notifee.core.model.NotificationModel;

@KeepForSdk
public class NotificationEvent {
  @KeepForSdk
  public final static int TYPE_DISMISSED = 0;

  @KeepForSdk
  public final static int TYPE_PRESS = 1;

  @KeepForSdk
  public final static int TYPE_ACTION_PRESS = 2;

  @KeepForSdk
  public final static int TYPE_DELIVERED = 3;

  @KeepForSdk
  public final static int TYPE_SCHEDULED = 7;

  private final int type;
  private final Bundle extras;
  private final NotificationModel notification;

  public NotificationEvent(int type, NotificationModel bundle) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
  }

  public NotificationEvent(int type, NotificationModel bundle, Bundle extras) {
    this.type = type;
    this.notification = bundle;
    this.extras = extras;
  }

  @KeepForSdk
  public int getType() {
    return type;
  }

  @KeepForSdk
  public NotificationModel getNotification() {
    return notification;
  }

  @KeepForSdk
  @Nullable
  public Bundle getExtras() {
    return extras;
  }
}
