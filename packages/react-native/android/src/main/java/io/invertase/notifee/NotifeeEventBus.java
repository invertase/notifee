package io.invertase.notifee;

import org.greenrobot.eventbus.EventBus;

public class NotifeeEventBus {
  private final static NotifeeEventBus instance = new NotifeeEventBus();
  private EventBus eventBus;

  private NotifeeEventBus() {
    eventBus = EventBus.builder().build();
  }

  public static NotifeeEventBus getInstance() {
    return instance;
  }

  public static void register(Object subscriber) {
    getInstance().getDefault().register(subscriber);
  }

  public static void unregister(Object subscriber) {
    getInstance().getDefault().unregister(subscriber);
  }

  public static void post(Object event) {
    getInstance().getDefault().post(event);
  }

  public static void postSticky(Object event) {
    getInstance().getDefault().postSticky(event);
  }

  public static <T> T getStickyEvent(Class<T> eventType) {
    return getInstance().getDefault().getStickyEvent(eventType);
  }

  public static <T> T removeStickyEvent(Class<T> eventType) {
    return getInstance().getDefault().removeStickyEvent(eventType);
  }

  public EventBus getDefault() {
    return eventBus;
  }
}
