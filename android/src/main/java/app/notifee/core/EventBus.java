package app.notifee.core;

public class EventBus {
  private static final EventBus instance = new EventBus();
  private org.greenrobot.eventbus.EventBus eventBus;

  private EventBus() {
    eventBus = org.greenrobot.eventbus.EventBus.builder().build();
  }

  public static EventBus getInstance() {
    return instance;
  }

  public static void register(Object subscriber) {
    getInstance().getDefault().register(subscriber);
  }

  public static void unregister(Object subscriber) {
    getInstance().getDefault().unregister(subscriber);
  }

  public static <T> T getStickyEvent(Class<T> eventType) {
    return getInstance().getDefault().getStickyEvent(eventType);
  }

  public static <T> T removeStickEvent(Class<T> eventType) {
    return getInstance().getDefault().removeStickyEvent(eventType);
  }

  public static void post(Object event) {
    getInstance().getDefault().post(event);
  }

  static void postSticky(Object event) {
    getInstance().getDefault().postSticky(event);
  }

  static <T> T removeStickyEvent(Class<T> eventType) {
    return getInstance().getDefault().removeStickyEvent(eventType);
  }

  private org.greenrobot.eventbus.EventBus getDefault() {
    return eventBus;
  }
}
