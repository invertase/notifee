package io.invertase.notifee;

import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule.RCTDeviceEventEmitter;

import app.notifee.core.EventListener;
import app.notifee.core.EventSubscriber;
import app.notifee.core.events.BlockStateEvent;
import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

@Keep
public class NotifeeEventSubscriber implements EventListener {
  static final String NOTIFICATION_EVENT_KEY = "app.notifee.notification.event";
  static final String FOREGROUND_NOTIFICATION_TASK_KEY = "app.notifee.foreground.task";

  private static final NotifeeEventSubscriber mInstance = new NotifeeEventSubscriber();

  private NotifeeEventSubscriber() {
    EventSubscriber.register(this);
  }

  public static NotifeeEventSubscriber getInstance() {
    return mInstance;
  }

  private void sendJsEvent(String eventName, @Nullable WritableMap eventMap) {
    try {
      ReactContext reactContext = (ReactContext) EventSubscriber.getContext();
      if (reactContext == null || !reactContext.hasActiveCatalystInstance()) {
        return;
      }

      reactContext.getJSModule(RCTDeviceEventEmitter.class).emit(eventName, eventMap);
    } catch (Exception e) {
      Log.e("SEND_EVENT", "", e);
    }
  }

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    Log.d("MIKE", notificationEvent.getType());
    sendJsEvent(NOTIFICATION_EVENT_KEY, null);
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {
    Log.d("MIKE", logEvent.getMessage());
    sendJsEvent(NOTIFICATION_EVENT_KEY, null);
  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    Log.d("MIKE", blockStateEvent.getType());
    sendJsEvent(NOTIFICATION_EVENT_KEY, null);
    blockStateEvent.setCompletionResult();
  }
}
