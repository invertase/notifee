package io.invertase.notifee.core;

import android.content.Context;
import android.util.Log;

public class NotifeeContextHolder {
  private static Context applicationContext;

  public static Context getApplicationContext() {
    return applicationContext;
  }

  static void setApplicationContext(Context applicationContext) {
    NotifeeLogger.d("ContextHolder", "received application context.");
    NotifeeContextHolder.applicationContext = applicationContext;
  }
}
