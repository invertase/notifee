package io.invertase.notifee;

import app.notifee.core.EventSubscriber;
import app.notifee.core.InitProvider;

public class NotifeeInitProvider extends InitProvider {
  @Override
  public boolean onCreate() {
    boolean onCreate = super.onCreate();
    EventSubscriber.register(new NotifeeEventSubscriber());
    return onCreate;
  }
}
