package app.notifee.core.interfaces;

import androidx.annotation.Nullable;

import app.notifee.core.KeepForSdk;

@KeepForSdk
public interface MethodCallResult<T> {
  @KeepForSdk
  void onComplete(@Nullable Exception error, T result);
}
