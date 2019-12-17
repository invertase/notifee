package app.notifee.core;

import app.notifee.core.events.BlockStateEvent;
import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

@KeepForSdk
public interface EventListener {
  @KeepForSdk
  void onNotificationEvent(NotificationEvent notificationEvent);
  @KeepForSdk
  void onLogEvent(LogEvent logEvent);
  @KeepForSdk
  void onBlockStateEvent(BlockStateEvent blockStateEvent);
}
