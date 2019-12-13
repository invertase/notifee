package io.invertase.notifee;

import com.facebook.flipper.core.FlipperConnection;
import com.facebook.flipper.core.FlipperObject;
import com.facebook.flipper.core.FlipperPlugin;

import org.greenrobot.eventbus.Subscribe;

import io.invertase.notifee.events.NotifeeNotificationEvent;

public class NotifeeFlipperPlugin implements FlipperPlugin {

  private FlipperConnection mFlipperConnection = null;

  @Override
  public String getId() {
    return "flipper-plugin-notifee";
  }

  @Override
  public void onConnect(FlipperConnection connection) throws Exception {
    NotifeeEventBus.getInstance().getDefault().register(this);
    mFlipperConnection = connection;
  }

  @Override
  public void onDisconnect() throws Exception {
    NotifeeEventBus.getInstance().getDefault().unregister(this);
    mFlipperConnection = null;
  }

  @Override
  public boolean runInBackground() {
    return false;
  }

  @Subscribe
  private void onNotificationEvent(NotifeeNotificationEvent event) {
    FlipperObject.Builder flipperBuilder = new FlipperObject.Builder();

    flipperBuilder.put("type", event.getType());
    flipperBuilder.put("id", event.getNotification().getId());
    flipperBuilder.put("title", event.getNotification().getTitle());
    flipperBuilder.put("body", event.getNotification().getBody());
    flipperBuilder.put("subtitle", event.getNotification().getSubTitle());

    flipperBuilder.put("timestamp", System.currentTimeMillis());

    if (mFlipperConnection != null) {
      mFlipperConnection.send("onNotificationEvent", flipperBuilder.build());
    }
  }
}
