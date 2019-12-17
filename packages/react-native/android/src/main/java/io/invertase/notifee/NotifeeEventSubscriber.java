package io.invertase.notifee;

import android.util.Log;

import androidx.annotation.Keep;

import org.greenrobot.eventbus.Subscribe;

import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

@Keep
public class NotifeeEventSubscriber {
  @Subscribe
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    Log.d("MIKE", notificationEvent.getType());
  }

  @Subscribe
  public void onLogEvent(LogEvent logEvent) {
    Log.d("MIKE", logEvent.getMessage());
  }
}
