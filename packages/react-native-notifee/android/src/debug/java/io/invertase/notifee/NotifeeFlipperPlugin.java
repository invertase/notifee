package io.invertase.notifee;

import com.facebook.flipper.core.FlipperConnection;
import com.facebook.flipper.core.FlipperPlugin;

import java.util.concurrent.atomic.AtomicInteger;

import app.notifee.core.EventListener;
import app.notifee.core.EventSubscriber;
import app.notifee.core.events.BlockStateEvent;
import app.notifee.core.events.ForegroundServiceEvent;
import app.notifee.core.events.LogEvent;
import app.notifee.core.events.NotificationEvent;


public class NotifeeFlipperPlugin implements FlipperPlugin, EventListener {
  private FlipperConnection mFlipperConnection = null;
  private AtomicInteger eventIdInteger = new AtomicInteger(0);

  @Override
  public String getId() {
    return "flipper-plugin-notifee";
  }

  @Override
  public void onConnect(FlipperConnection connection) {
    mFlipperConnection = connection;
    EventSubscriber.register(this);
//
//     connection.receive("displayNotification", (params, responder) -> {
//       String jsonString = params.getString("notification");
//       NotificationBundle notificationBundle = NotificationBundle.fromJSONString(jsonString);
//       if (notificationBundle == null) {
//         responder.error(
//           new FlipperObject.Builder()
//             .put("message", "Error parsing notification from JSON.")
//             .build()
//         );
//         return;
//       }
//
//       Notifee.getInstance().displayNotification(notificationBundle.toBundle(), (e, aVoid) -> {
//         if (e != null) {
//           responder.error(
//             new FlipperObject.Builder()
//               .put("message", "Error occurred while displaying notification.")
//               .build()
//           );
//         } else {
//           FlipperObject.Builder flipperBuilder = new FlipperObject.Builder();
//           flipperBuilder.put("timestamp", System.currentTimeMillis());
//           flipperBuilder.put("notification", jsonString);
//           responder.success(flipperBuilder.build());
//         }
//       });
//     });
  }

  @Override
  public void onDisconnect() throws Exception {
    EventSubscriber.unregister(this);
    mFlipperConnection = null;
  }

  @Override
  public boolean runInBackground() {
    return false;
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {

  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {

  }

  @Override
  public void onForegroundServiceEvent(ForegroundServiceEvent foregroundServiceEvent) {

  }

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
//     if (mFlipperConnection == null) return;
//     FlipperObject.Builder flipperBuilder = new FlipperObject.Builder();
//     flipperBuilder.put("eventType", notificationEvent.getType());
//     flipperBuilder.put("eventId", eventIdInteger.incrementAndGet());
//     flipperBuilder.put("eventTimestamp", System.currentTimeMillis());
//     flipperBuilder.put("extras", notificationEvent.getExtras());
//     flipperBuilder.put("notification", notificationEvent.getNotification().toJSONString());
//     mFlipperConnection.send("onNotificationEvent", flipperBuilder.build());
  }
}
