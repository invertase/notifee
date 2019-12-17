package io.invertase.notifee;

import android.util.Log;

import androidx.annotation.Keep;

import app.notifee.core.EventSubscriber;
import app.notifee.core.EventListener;
import app.notifee.core.events.BlockStateEvent;
import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

@Keep
public class NotifeeEventSubscriber implements EventListener {
  private static final NotifeeEventSubscriber mInstance = new NotifeeEventSubscriber();
  private NotifeeEventSubscriber() {
    EventSubscriber.register(this);
  }

  public static NotifeeEventSubscriber getInstance() {
    return mInstance;
  }

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    Log.d("MIKE", notificationEvent.getType());
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {
    Log.d("MIKE", logEvent.getMessage());
  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    // do something
    blockStateEvent.setResult();
  }
}
