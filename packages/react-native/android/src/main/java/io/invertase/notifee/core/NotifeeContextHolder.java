package io.invertase.notifee.core;

import android.content.Context;
import android.util.Log;

public class NotifeeContextHolder {
  private static Context applicationContext;

  public static Context getApplicationContext() {
    return applicationContext;
  }

  public static void setApplicationContext(Context applicationContext) {
    Log.d("NotifeeContextHolder", "received application context.");
    NotifeeContextHolder.applicationContext = applicationContext;
  }
}
