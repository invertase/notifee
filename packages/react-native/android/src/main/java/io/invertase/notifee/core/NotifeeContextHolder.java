package io.invertase.notifee.core;

import android.content.Context;

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
