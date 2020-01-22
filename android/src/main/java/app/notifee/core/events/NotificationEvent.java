package app.notifee.core.events;

import android.os.Bundle;

import androidx.annotation.Nullable;

import app.notifee.core.KeepForSdk;
import app.notifee.core.bundles.NotificationBundle;

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
  private final NotificationBundle notification;

  public NotificationEvent(int type, NotificationBundle bundle) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
  }

  public NotificationEvent(int type, NotificationBundle bundle, Bundle extras) {
    this.type = type;
    this.notification = bundle;
    this.extras = extras;
  }

  @KeepForSdk
  public int getType() {
    return type;
  }

  @KeepForSdk
  public NotificationBundle getNotification() {
    return notification;
  }

  @KeepForSdk
  @Nullable
  public Bundle getExtras() {
    return extras;
  }
}
