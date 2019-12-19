package io.invertase.notifee;

import app.notifee.core.InitProvider;
import app.notifee.core.Notifee;

public class NotifeeInitProvider extends InitProvider {
  @Override
  public boolean onCreate() {
    boolean onCreate = super.onCreate();
    Notifee.configure(BuildConfig.NOTIFEE_JSON_RAW, new NotifeeEventSubscriber());
    return onCreate;
  }
}
