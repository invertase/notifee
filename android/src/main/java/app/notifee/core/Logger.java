package app.notifee.core;

import android.util.Log;

import androidx.annotation.NonNull;

import app.notifee.core.events.LogEvent;

public class Logger {
  private static final String TAG = "NOTIFEE";

  private static String tagAndMessage(String tag, String message) {
    return "(" + tag + "): " + message;
  }

  @KeepForSdk
  public static void v(@NonNull String tag, String message) {
    Log.v(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void d(@NonNull String tag, String message) {
    Log.d(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void i(@NonNull String tag, String message) {
    Log.i(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void w(@NonNull String tag, String message) {
    Log.w(TAG, tagAndMessage(tag, message));
  }

  @KeepForSdk
  public static void e(@NonNull String tag, String message, Exception e) {
    Log.e(TAG, tagAndMessage(tag, message), e.getCause());
    EventBus.post(new LogEvent(LogEvent.LEVEL_ERROR, tag, message, e.getCause()));
  }

  @KeepForSdk
  public static void e(@NonNull String tag, String message) {
    Log.e(TAG, tagAndMessage(tag, message));
    EventBus.post(new LogEvent(LogEvent.LEVEL_ERROR, tag, message));
  }

  @KeepForSdk
  public static void e(@NonNull String tag, String message, Throwable throwable) {
    Log.e(TAG, tagAndMessage(tag, message), throwable);
    EventBus.post(new LogEvent(LogEvent.LEVEL_ERROR, tag, message, throwable));
  }
}
