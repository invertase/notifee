package app.notifee.core;

import android.content.Context;

class ContextHolder {
  private static Context applicationContext;

  static Context getApplicationContext() {
    return applicationContext;
  }

  static void setApplicationContext(Context applicationContext) {
    Logger.d(ContextHolder.class.getName(), "received application context");
    ContextHolder.applicationContext = applicationContext;
  }
}
