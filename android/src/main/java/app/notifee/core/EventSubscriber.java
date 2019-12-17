package app.notifee.core;

import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;

import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;

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

  @Subscribe
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onNotificationEvent(notificationEvent);
    }
  }

  @Subscribe
  public void onLogEvent(LogEvent logEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onLogEvent(logEvent);
    }
  }


}
