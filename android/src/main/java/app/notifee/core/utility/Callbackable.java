package app.notifee.core.utility;

/**
 * Since lambda functions are not supported prior to SDK 24, and this project's SDK min. is 20,
 * This interface was created to pass a function to another object.
 */
public interface Callbackable<T> {

  void call(Exception e, T result);
}
