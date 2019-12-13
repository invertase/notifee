package io.invertase.notifee;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class NotifeeEventBus {
  private final static NotifeeEventBus instance = new NotifeeEventBus();
  private EventBus eventBus;
  private NotifeeEventBus() {
    eventBus = EventBus.builder().build();
  }

  public static NotifeeEventBus getInstance() {
    return instance;
  }

  public EventBus getDefault() {
    return eventBus;
  }
}
