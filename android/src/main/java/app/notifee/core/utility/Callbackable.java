package app.notifee.core.utility;

public interface Callbackable<T> {

  void call(Exception e, T result);
}
