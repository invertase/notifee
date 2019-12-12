package io.invertase.notifee.core;

import android.util.Log;

import androidx.annotation.NonNull;

public class NotifeeLogger {
  private static final String TAG = "Notifee";

  public static void v(@NonNull String Class, String message) {
    Log.v(TAG + Class, message);
  }

  public static void d(@NonNull String Class, String message) {
    Log.d(TAG + Class, message);
  }

  public static void i(@NonNull String Class, String message) {
    Log.i(TAG + Class, message);
  }

  public static void w(@NonNull String Class, String message) {
    Log.w(TAG + Class, message);
  }

  public static void e(@NonNull String Class, String message, Exception e) {
    // TODO Send to API
    Log.e(TAG + Class, message, e.getCause());
  }

  public static void e(@NonNull String Class, String message, Throwable tr) {
    // TODO Send to API
    Log.e(TAG + Class, message, tr);
  }
}
