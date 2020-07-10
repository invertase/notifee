/*
 * Copyright (c) 2016-present Invertase Limited
 */

package io.invertase.notifee;

import static io.invertase.notifee.NotifeeReactUtils.isAppInForeground;

import android.os.Bundle;
import androidx.annotation.Keep;
import app.notifee.core.event.BlockStateEvent;
import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.event.LogEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.interfaces.EventListener;
import app.notifee.core.model.NotificationModel;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

@Keep
public class NotifeeEventSubscriber implements EventListener {
  static final String NOTIFICATION_EVENT_KEY = "app.notifee.notification-event";
  static final String FOREGROUND_NOTIFICATION_TASK_KEY =
      "app.notifee.foreground-service-headless-task";

  private static final String KEY_TYPE = "type";
  private static final String KEY_DETAIL = "detail";
  private static final String KEY_BLOCKED = "blocked";
  private static final String KEY_HEADLESS = "headless";
  private static final String KEY_NOTIFICATION = "notification";

  private static final String KEY_DETAIL_PRESS_ACTION = "pressAction";
  private static final String KEY_DETAIL_INPUT = "input";

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    WritableMap eventMap = Arguments.createMap();
    WritableMap eventDetailMap = Arguments.createMap();
    eventMap.putInt(KEY_TYPE, notificationEvent.getType());

    eventDetailMap.putMap(
        KEY_NOTIFICATION, Arguments.fromBundle(notificationEvent.getNotification().toBundle()));

    Bundle extras = notificationEvent.getExtras();
    if (extras != null) {
      Bundle pressAction = extras.getBundle(KEY_DETAIL_PRESS_ACTION);
      if (pressAction != null) {
        eventDetailMap.putMap(KEY_DETAIL_PRESS_ACTION, Arguments.fromBundle(pressAction));
      }

      String input = extras.getString(KEY_DETAIL_INPUT);
      if (input != null) {
        eventDetailMap.putString(KEY_DETAIL_INPUT, input);
      }
    }

    eventMap.putMap(KEY_DETAIL, eventDetailMap);

    if (isAppInForeground()) {
      eventMap.putBoolean(KEY_HEADLESS, false);
      NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, eventMap);
    } else {
      eventMap.putBoolean(KEY_HEADLESS, true);
      NotifeeReactUtils.startHeadlessTask(NOTIFICATION_EVENT_KEY, eventMap, 60000, null);
    }
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {
    // TODO
  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    WritableMap eventMap = Arguments.createMap();
    WritableMap eventDetailMap = Arguments.createMap();

    eventMap.putInt(KEY_TYPE, blockStateEvent.getType());

    int type = blockStateEvent.getType();

    if (type == BlockStateEvent.TYPE_CHANNEL_BLOCKED
        || type == BlockStateEvent.TYPE_CHANNEL_GROUP_BLOCKED) {
      String mapKey = type == BlockStateEvent.TYPE_CHANNEL_BLOCKED ? "channel" : "channelGroup";
      Bundle channelOrGroupBundle = blockStateEvent.getChannelOrGroupBundle();
      if (channelOrGroupBundle != null) {
        eventDetailMap.putMap(
            mapKey, Arguments.fromBundle(blockStateEvent.getChannelOrGroupBundle()));
      }
    }

    if (type == BlockStateEvent.TYPE_APP_BLOCKED) {
      eventDetailMap.putBoolean(KEY_BLOCKED, blockStateEvent.isBlocked());
    }

    eventMap.putMap(KEY_DETAIL, eventDetailMap);

    if (isAppInForeground()) {
      eventMap.putBoolean(KEY_HEADLESS, false);
      NotifeeReactUtils.sendEvent(NOTIFICATION_EVENT_KEY, eventMap);
    } else {
      eventMap.putBoolean(KEY_HEADLESS, true);
      NotifeeReactUtils.startHeadlessTask(
          NOTIFICATION_EVENT_KEY, eventMap, 0, blockStateEvent::setCompletionResult);
    }
  }

  @Override
  public void onForegroundServiceEvent(ForegroundServiceEvent foregroundServiceEvent) {
    NotificationModel notificationBundle = foregroundServiceEvent.getNotification();

    WritableMap eventMap = Arguments.createMap();
    eventMap.putMap(KEY_NOTIFICATION, Arguments.fromBundle(notificationBundle.toBundle()));

    NotifeeReactUtils.startHeadlessTask(
        FOREGROUND_NOTIFICATION_TASK_KEY, eventMap, 0, foregroundServiceEvent::setCompletionResult);
  }
}
