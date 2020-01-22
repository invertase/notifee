package app.notifee.core;

import androidx.annotation.Nullable;

@KeepForSdk
public interface MethodCallResult<T> {
  @KeepForSdk
  void onComplete(@Nullable Exception error, T result);
}
