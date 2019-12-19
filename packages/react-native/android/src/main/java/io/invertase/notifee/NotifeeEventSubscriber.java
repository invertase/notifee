package io.invertase.notifee;

import androidx.annotation.Keep;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

import app.notifee.core.EventListener;
import app.notifee.core.events.BlockStateEvent;
import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

import static io.invertase.notifee.NotifeeReactUtils.isAppInForeground;

@Keep
public class NotifeeEventSubscriber implements EventListener {
  static final String NOTIFICATION_EVENT_KEY = "app.notifee.notification.event";
  static final String FOREGROUND_NOTIFICATION_TASK_KEY = "app.notifee.foreground.task";

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    WritableMap eventMap = Arguments.createMap();
    switch (notificationEvent.getType()) {
      case NotificationEvent.TYPE_DELIVERED:
        eventMap.putInt("eventType", 3);
        break;
      case NotificationEvent.TYPE_DISMISSED:
        eventMap.putInt("eventType", 0);
        break;
      // TODO other types, press = 1, action_press = 2
    }
    // TODO `action` bundle if applicable?
    // TODO `input` if applicable?

    eventMap
      .putMap("notification", Arguments.fromBundle(notificationEvent.getNotification().toBundle()));

    if (isAppInForeground()) {
      eventMap.putBoolean("headless", false);
      NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, eventMap);
    } else {
      eventMap.putBoolean("headless", true);
      NotifeeReactUtils.startHeadlessTask(NOTIFICATION_EVENT_KEY, eventMap, 60000, null);
    }
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {
  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    WritableMap eventMap = Arguments.createMap();

    switch (blockStateEvent.getType()) {
      case BlockStateEvent.TYPE_APP:
        eventMap.putInt("eventType", 4);
        break;
      case BlockStateEvent.TYPE_CHANNEL:
        eventMap.putInt("eventType", 5);
        // TODO js is expecting the full channel object rather than id
        eventMap.putString("channel", blockStateEvent.getChannelOrGroupId());
        break;
      case BlockStateEvent.TYPE_CHANNEL_GROUP:
        eventMap.putInt("eventType", 6);
        // TODO js is expecting the full channel group object rather than id
        eventMap.putString("channelGroup", blockStateEvent.getChannelOrGroupId());
        break;
    }

    if (isAppInForeground()) {
      eventMap.putBoolean("headless", false);
      NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, eventMap);
    } else {
      eventMap.putBoolean("headless", true);
      NotifeeReactUtils.startHeadlessTask(NOTIFICATION_EVENT_KEY, eventMap, 0,
        blockStateEvent::setCompletionResult
      );
    }
  }
}
