package io.invertase.notifee;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import io.invertase.notifee.core.NotifeeContextHolder;
import io.invertase.notifee.core.NotifeeEvent;
import io.invertase.notifee.core.NotifeeEventEmitter;
import io.invertase.notifee.core.NotifeeLogger;

import static io.invertase.notifee.NotifeeUtils.getLaunchActivity;
import static io.invertase.notifee.core.NotifeeCoreUtils.isAppInForeground;

public class NotifeeReceiverService extends HeadlessJsTaskService {
  public static final String ACTION_NOTIFICATION_INTENT = "io.invertase.notifee.intent";
  public static final String ACTION_NOTIFICATION_DELETE_INTENT = "io.invertase.notifee.intent.delete";
  public static final String ACTION_NOTIFICATION_PRESS_INTENT = "io.invertase.notifee.intent.press";
  public static final String ACTION_NOTIFICATION_ACTION_PRESS_INTENT = "io.invertase.notifee.intent.action_press";
  public static final String ACTION_NOTIFICATION_DELIVERED_INTENT = "io.invertase.notifee.intent.delivered";
  public static final String ACTION_APP_BLOCK_STATE_CHANGED = "io.invertase.notifee.intent.app_blocked";
  public static final String ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED = "io.invertase.notifee.intent.channel_blocked";
  public static final String ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED = "io.invertase.notifee.intent.channel_group_blocked";
  public static final List<String> reactComponents = new CopyOnWriteArrayList<>();
  private static final String TAG = "NotifeeReceiverService";
  private static final int RETURN_COMMAND = START_NOT_STICKY;
  public static String RECEIVER_REMOTE_INPUT_KEY = "receiver_remote_input";
  public static String RECEIVER_SERVICE_EVENT_KEY = "receiver_service";
  public static String RECEIVER_SERVICE_TASK_KEY = "notifee_receiver_service";

  /**
   * Creates a PendingIntent, which when sent triggers this class.
   *
   * @param action       An Action - matches up with the JS EventType Enum.
   * @param extraKeys    Array of strings
   * @param extraBundles One or more bundles
   * @return
   */
  public static PendingIntent createIntent(String action, String[] extraKeys, Bundle... extraBundles) {
    Context context = NotifeeContextHolder.getApplicationContext();
    Intent intent = new Intent(context, NotifeeReceiverService.class);
    intent.setAction(action);

    for (int i = 0; i < extraKeys.length; i++) {
      String key = extraKeys[i];

      if (i <= extraBundles.length - 1) {
        Bundle bundle = extraBundles[i];
        intent.putExtra(key, bundle);
      } else {
        intent.putExtra(key, (String) null);
      }
    }

    int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);

    return PendingIntent.getService(context, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);
  }

  /**
   * Returns a unique request code for the services actions.
   * These codes should match the JS NotificationEventType enum.
   *
   * @param action
   * @return
   */
  public static int getActionRequestCode(String action) {
    switch (action) {
      case ACTION_NOTIFICATION_DELETE_INTENT:
        return 0;
      case ACTION_NOTIFICATION_PRESS_INTENT:
        return 1;
      case ACTION_NOTIFICATION_ACTION_PRESS_INTENT:
        return 2;
      case ACTION_NOTIFICATION_DELIVERED_INTENT:
        return 3;
      case ACTION_APP_BLOCK_STATE_CHANGED:
        return 4;
      case ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED:
        return 5;
      case ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED:
        return 6;
      default:
        return -1;
    }
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (intent.getAction() == null) {
      Log.d(TAG, "Received intent with no action");
      return RETURN_COMMAND;
    }

    String action = intent.getAction();

    switch (action) {
      case ACTION_NOTIFICATION_DELETE_INTENT:
        onDeleteIntent(intent);
        break;
      case ACTION_NOTIFICATION_PRESS_INTENT:
        onPressIntent(intent);
        break;
      case ACTION_NOTIFICATION_DELIVERED_INTENT:
        onDeliveredIntent(intent);
        break;
      case ACTION_NOTIFICATION_ACTION_PRESS_INTENT:
        onActionPressIntent(intent);
        break;
    }

    // If the app is current running, send a JS event
    if (isAppInForeground(NotifeeContextHolder.getApplicationContext())) {
      Log.d(TAG, "Sending event to running application");
      NotifeeEvent event = new NotifeeEvent(RECEIVER_SERVICE_EVENT_KEY, getIntentMap(intent, false));
      NotifeeEventEmitter.getSharedInstance().sendEvent(event);
    }
    // The app is not running, so spawn a headless task process
    else {
      Log.d(TAG, "Sending event to Headless task");
      startTask(getTaskConfig(intent));
    }

    return RETURN_COMMAND;
  }

  @Override
  protected @Nullable
  HeadlessJsTaskConfig getTaskConfig(Intent intent) {
    return new HeadlessJsTaskConfig(
      RECEIVER_SERVICE_TASK_KEY,
      getIntentMap(intent, true),
      60000,
      false
    );
  }

  /**
   * Creates a WritableMap of the current intent data
   *
   * @param intent
   * @return
   */
  private WritableMap getIntentMap(Intent intent, Boolean headless) {
    WritableMap writableMap = Arguments.createMap();
    WritableMap eventMap = Arguments.createMap();

    // eventType
    if (intent.getAction() == null) {
      writableMap.putInt("type", -1);
    } else {
      writableMap.putInt("type", getActionRequestCode(intent.getAction()));
    }

    // event
    Bundle extras = intent.getExtras();
    if (extras != null) eventMap.merge(Arguments.fromBundle(extras));

    // Attach any "input" data to the event map
    Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
    if (remoteInput != null) {
      CharSequence reply = remoteInput.getCharSequence(RECEIVER_REMOTE_INPUT_KEY);
      if (reply != null) eventMap.putString("input", reply.toString());
    }

    writableMap.putMap("event", eventMap);

    // headless
    writableMap.putBoolean("headless", headless);

    return writableMap;
  }

  /**
   * Called when a notification is dismissed or cleared
   *
   * @param intent
   * @url https://developer.android.com/reference/androidx/core/app/NotificationCompat.Builder.html#setDeleteIntent(android.app.PendingIntent)
   */
  private void onDeleteIntent(Intent intent) {
    // TODO update database with "dismissed" status
    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));
  }

  /**
   * Called when a notification is pressed by the user
   *
   * @param intent
   * @url https://developer.android.com/reference/androidx/core/app/NotificationCompat.Builder.html#setContentIntent(android.app.PendingIntent)
   */
  private void onPressIntent(Intent intent) {
    // TODO update database with "read" status
    Bundle onPressActionBundle = Objects.requireNonNull(intent.getBundleExtra("action"));
    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));

    // Always launch an activity when a notification is pressed
    launchPendingIntentActivity(
      Objects.requireNonNull(notificationBundle.getString("id")),
      onPressActionBundle.getString("launchActivity"),
      onPressActionBundle.getString("reactComponent")
    );
  }

  /**
   * Called when a notification is delivered to the device
   *
   * @param intent
   */
  private void onDeliveredIntent(Intent intent) {
    // TODO update database with notification delivered
    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));
  }

  /**
   * Called when a notification is delivered to the device
   *
   * @param intent
   */
  private void onActionPressIntent(Intent intent) {
    Bundle onPressActionBundle = Objects.requireNonNull(intent.getBundleExtra("action"));
    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));

    String launchActivity = onPressActionBundle.getString("launchActivity");
    String reactComponent = onPressActionBundle.getString("reactComponent");

    // If there is no activity, do nothing
    if (launchActivity == null && reactComponent == null) {
      return;
    }

    String notificationId = Objects.requireNonNull(notificationBundle.getString("id"));

    launchPendingIntentActivity(notificationId, launchActivity, reactComponent);

    NotificationManagerCompat.from(getApplicationContext()).cancel(Objects.requireNonNull(notificationId).hashCode());
  }

  /**
   * Launch an activity with a pending intent
   *
   * @param notificationId Unique notification id
   * @param launchActivity Optional activity name to launch
   * @param reactComponent Optional react component to use
   */
  private void launchPendingIntentActivity(@NonNull String notificationId, @Nullable String launchActivity, @Nullable String reactComponent) {
    Class launchActivityClass = getLaunchActivity(launchActivity);

    Intent launchIntent = new Intent(getApplicationContext(), launchActivityClass);
    launchIntent.setAction(ACTION_NOTIFICATION_INTENT);

    if (reactComponent != null) {
      synchronized (reactComponents) {
        reactComponents.add(reactComponent);
      }
      launchIntent.putExtra("reactComponent", reactComponent);
    }

    PendingIntent pendingContentIntent = PendingIntent.getActivity(
      getApplicationContext(),
      notificationId.hashCode(),
      launchIntent,
      PendingIntent.FLAG_UPDATE_CURRENT
    );

    try {
      pendingContentIntent.send();
    } catch (Exception e) {
      NotifeeLogger.e(
        "ReceiverService",
        "Failed to send PendingIntent from launchPendingIntentActivity for notification " + notificationId,
        e
      );
    }
  }
}
