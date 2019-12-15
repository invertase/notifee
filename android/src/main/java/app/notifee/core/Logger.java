package app.notifee.core;

import android.util.Log;

import androidx.annotation.NonNull;

import app.notifee.core.events.LogErrorEvent;

public class Logger {
  private static final String TAG = "NTFE";

  public static void v(@NonNull String clazz, String message) {
    Log.v(TAG + clazz, message);
  }

  public static void d(@NonNull String clazz, String message) {
    Log.d(TAG + clazz, message);
  }

  public static void i(@NonNull String clazz, String message) {
    Log.i(TAG + clazz, message);
  }

  public static void w(@NonNull String clazz, String message) {
    Log.w(TAG + clazz, message);
  }

  public static void e(@NonNull String clazz, String message, Exception e) {
    Log.e(TAG + clazz, message, e.getCause());
    EventBus.post(new LogErrorEvent(clazz, message, e.getCause()));
  }

  public static void e(@NonNull String clazz, String message, Throwable throwable) {
    Log.e(TAG + clazz, message, throwable);
    EventBus.post(new LogErrorEvent(clazz, message, throwable));
  }
}
