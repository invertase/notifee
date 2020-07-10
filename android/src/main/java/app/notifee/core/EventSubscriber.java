package app.notifee.core;

import android.content.Context;
import app.notifee.core.event.BlockStateEvent;
import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.event.LogEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.interfaces.EventListener;
import java.util.HashSet;
import java.util.Set;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@KeepForSdk
public class EventSubscriber {
  private static final EventSubscriber mInstance = new EventSubscriber();
  private final Set<EventListener> mListeners = new HashSet<>();

  private EventSubscriber() {
    EventBus.register(this);
  }

  @KeepForSdk
  public static void register(EventListener listener) {
    mInstance.mListeners.add(listener);
  }

  @KeepForSdk
  public static void unregister(EventListener listener) {
    mInstance.mListeners.remove(listener);
  }

  @KeepForSdk
  public static Context getContext() {
    return ContextHolder.getApplicationContext();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onNotificationEvent(notificationEvent);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onLogEvent(LogEvent logEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onLogEvent(logEvent);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onBlockStateEvent(blockStateEvent);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onForegroundServiceEvent(ForegroundServiceEvent foregroundServiceEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onForegroundServiceEvent(foregroundServiceEvent);
    }
  }
}
