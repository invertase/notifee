package io.invertase.notifee;

import android.util.Log;

import androidx.annotation.Keep;

import com.facebook.react.bridge.Arguments;

import app.notifee.core.EventListener;
import app.notifee.core.events.BlockStateEvent;
import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

@Keep
public class NotifeeEventSubscriber implements EventListener {
  static final String NOTIFICATION_EVENT_KEY = "app.notifee.notification.event";
  static final String FOREGROUND_NOTIFICATION_TASK_KEY = "app.notifee.foreground.task";

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    Log.d("MIKE", notificationEvent.getType());
    NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, Arguments.createMap());
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {
    Log.d("MIKE", logEvent.getMessage());
    NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, Arguments.createMap());
  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    Log.d("MIKE", blockStateEvent.getType());
    NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, Arguments.createMap());
    blockStateEvent.setCompletionResult();
  }
}
