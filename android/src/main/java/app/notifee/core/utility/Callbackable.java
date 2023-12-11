package app.notifee.core.utility;

import androidx.annotation.Nullable;

/**
 * Since lambda functions are not supported prior to SDK 24, and this project's SDK min. is 20,
 * This interface was created to pass a function to another object.
 */
public interface Callbackable<T> {

  void call(@Nullable Exception e,@Nullable T result);
}
