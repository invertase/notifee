package io.invertase.notifee;

import com.facebook.flipper.core.FlipperConnection;
import com.facebook.flipper.core.FlipperObject;
import com.facebook.flipper.core.FlipperPlugin;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.atomic.AtomicInteger;

import io.invertase.notifee.bundles.NotifeeNotificationBundle;
import io.invertase.notifee.events.NotifeeNotificationEvent;

public class NotifeeFlipperPlugin implements FlipperPlugin {
  private FlipperConnection mFlipperConnection = null;
  private AtomicInteger eventIdInteger = new AtomicInteger(0);

  @Override
  public String getId() {
    return "flipper-plugin-notifee";
  }

  @Override
  public void onConnect(FlipperConnection connection) throws Exception {
    mFlipperConnection = connection;

    connection.receive("displayNotification", (params, responder) -> {
      String jsonString = params.getString("notification");
      NotifeeNotificationBundle notificationBundle = NotifeeNotificationBundle.fromJSONString(jsonString);
      if (notificationBundle == null) {
        responder.error(
          new FlipperObject.Builder()
            .put("message", "Error parsing notification from JSON.")
            .build()
        );
        return;
      }

      NotifeeNotification notifeeNotification = new NotifeeNotification(notificationBundle);
      notifeeNotification.displayNotification().addOnCompleteListener((task -> {
        if (task.isSuccessful()) {
          FlipperObject.Builder flipperBuilder = new FlipperObject.Builder();
          flipperBuilder.put("timestamp", System.currentTimeMillis());
          flipperBuilder.put("notification", jsonString);
          responder.success(flipperBuilder.build());
        } else {
          responder.error(
            new FlipperObject.Builder()
              .put("message", "Error occurred while displaying notification.")
              .build()
          );
        }
      }));
    });

    NotifeeEventBus.register(this);
  }

  @Override
  public void onDisconnect() throws Exception {
    NotifeeEventBus.unregister(this);
    mFlipperConnection = null;
  }

  @Override
  public boolean runInBackground() {
    return false;
  }

  @Subscribe
  public void onNotificationEvent(NotifeeNotificationEvent event) {
    if (mFlipperConnection == null) return;

    FlipperObject.Builder flipperBuilder = new FlipperObject.Builder();
    flipperBuilder.put("eventType", event.getType());
    flipperBuilder.put("eventId", eventIdInteger.incrementAndGet());
    flipperBuilder.put("eventTimestamp", System.currentTimeMillis());

    flipperBuilder.put("extras", event.getExtras());
    flipperBuilder.put("notification", event.getNotification().toJSONString());
    mFlipperConnection.send("onNotificationEvent", flipperBuilder.build());
  }
}
