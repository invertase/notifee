package app.notifee.core;

import android.content.Context;

public class ContextHolder {
  private static Context applicationContext;

  public static Context getApplicationContext() {
    return applicationContext;
  }

  static void setApplicationContext(Context applicationContext) {
    Logger.d("context", "received application context");
    ContextHolder.applicationContext = applicationContext;
  }
}
