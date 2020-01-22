package app.notifee.core;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.RemoteInput;

import app.notifee.core.bundles.NotificationAndroidActionBundle;
import app.notifee.core.bundles.NotificationAndroidPressActionBundle;
import app.notifee.core.bundles.NotificationBundle;
import app.notifee.core.events.NotificationEvent;

import static app.notifee.core.LicenseManager.logLicenseWarningForEvent;
import static app.notifee.core.events.NotificationEvent.TYPE_ACTION_PRESS;
import static app.notifee.core.events.NotificationEvent.TYPE_DISMISSED;

public class ReceiverService extends Service {
  public static final String REMOTE_INPUT_RECEIVER_KEY = "app.notifee.core.ReceiverService.REMOTE_INPUT_RECEIVER_KEY";

  static final String DELETE_INTENT = "app.notifee.core.ReceiverService.DELETE_INTENT";
  static final String PRESS_INTENT = "app.notifee.core.ReceiverService.PRESS_INTENT";
  static final String ACTION_PRESS_INTENT = "app.notifee.core.ReceiverService.ACTION_PRESS_INTENT";

  /**
   * Creates a PendingIntent, which when sent triggers this class.
   *
   * @param action       An Action - matches up with the JS EventType Enum.
   * @param extraKeys    Array of strings
   * @param extraBundles One or more bundles
   * @return
   */
  public static PendingIntent createIntent(
    String action, String[] extraKeys, Bundle... extraBundles
  ) {
    Context context = ContextHolder.getApplicationContext();
    Intent intent = new Intent(context, ReceiverService.class);
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

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    String action = intent.getAction();

    if (action == null) {
      return START_NOT_STICKY;
    }

    // TODO handle additional intents (actions, click etc)
    switch (action) {
      case DELETE_INTENT:
        onDeleteIntent(intent);
      case PRESS_INTENT:
        onPressIntent(intent);
      case ACTION_PRESS_INTENT:
        onActionPressIntent(intent);
    }

    return START_NOT_STICKY;
  }

  /**
   * Handle users delete/dismiss intents
   *
   * @param intent
   */
  private void onDeleteIntent(Intent intent) {
    Bundle notification = intent.getBundleExtra("notification");

    if (notification == null) {
      return;
    }

    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForEvent("notification dismissed");
      return;
    }

    NotificationBundle notificationBundle = NotificationBundle.fromBundle(notification);
    EventBus.post(new NotificationEvent(TYPE_DISMISSED, notificationBundle));
  }

  /**
   * Handle user notification press
   *
   * @param intent
   */
  private void onPressIntent(Intent intent) {
    Bundle notification = intent.getBundleExtra("notification");

    if (notification == null) {
      return;
    }

    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForEvent("notification press");
      return;
    }


    NotificationBundle notificationBundle = NotificationBundle.fromBundle(notification);
    EventBus.post(new NotificationEvent(TYPE_DISMISSED, notificationBundle));

    // Optional
    Bundle pressAction = intent.getBundleExtra("pressAction");

    if (pressAction == null) {
      return;
    }

    NotificationAndroidPressActionBundle pressActionBundle = NotificationAndroidPressActionBundle.fromBundle(pressAction);

    String launchActivity = pressActionBundle.getLaunchActivity();
    String reactComponent = pressActionBundle.getReactComponent();

    // TODO Launch the app / compone
  }


  /**
   * Handle action intents
   *
   * @param intent
   */
  private void onActionPressIntent(Intent intent) {
    Bundle notification = intent.getBundleExtra("notification");
    Bundle pressAction = intent.getBundleExtra("pressAction");

    if (notification == null || pressAction == null) {
      return;
    }

    if (LicenseManager.isLicenseInvalid()) {
      logLicenseWarningForEvent("notification press action");
      return;
    }

    NotificationBundle notificationBundle = NotificationBundle.fromBundle(notification);
    NotificationAndroidPressActionBundle pressActionBundle = NotificationAndroidPressActionBundle.fromBundle(pressAction);

    String launchActivity = pressActionBundle.getLaunchActivity();
    String reactComponent = pressActionBundle.getReactComponent();

    // TODO Launch the app / component

    Bundle extras = new Bundle();
    extras.putBundle("pressAction", pressActionBundle.toBundle());

    Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
    if (remoteInput != null) {
      CharSequence input = remoteInput.getCharSequence(REMOTE_INPUT_RECEIVER_KEY);
      if (input != null) {
        extras.putString("input", input.toString());
      }
    }

    EventBus.post(new NotificationEvent(TYPE_ACTION_PRESS, notificationBundle, extras));
  }
}


//package app.notifee.core;
//
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.core.app.RemoteInput;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import static io.invertase.notifee.NotifeeUtils.getLaunchActivity;
//import static io.invertase.notifee.core.NotifeeCoreUtils.isAppInForeground;
//
//public class ReceiverService extends Service {
//  public static final String ACTION_NOTIFICATION_INTENT = "io.invertase.notifee.intent";
//  public static final String ACTION_NOTIFICATION_DELETE_INTENT = "io.invertase.notifee.intent.delete";
//  public static final String ACTION_NOTIFICATION_PRESS_INTENT = "io.invertase.notifee.intent.press";
//  public static final String ACTION_NOTIFICATION_ACTION_PRESS_INTENT = "io.invertase.notifee.intent.action_press";
//  public static final String ACTION_NOTIFICATION_DELIVERED_INTENT = "io.invertase.notifee.intent.delivered";
//  public static final String ACTION_APP_BLOCK_STATE_CHANGED = "io.invertase.notifee.intent.app_blocked";
//  public static final String ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED = "io.invertase.notifee.intent.channel_blocked";
//  public static final String ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED = "io.invertase.notifee.intent.channel_group_blocked";
//  public static final List<String> reactComponents = new CopyOnWriteArrayList<>();
//  private static final String TAG = "ReceiverService";
//  private static final int RETURN_COMMAND = START_NOT_STICKY;
//  public static String RECEIVER_REMOTE_INPUT_KEY = "receiver_remote_input";
//  public static String RECEIVER_SERVICE_EVENT_KEY = "receiver_service";
//  public static String RECEIVER_SERVICE_TASK_KEY = "notifee_receiver_service";
//
//  /**
//   * Creates a PendingIntent, which when sent triggers this class.
//   *
//   * @param action       An Action - matches up with the JS EventType Enum.
//   * @param extraKeys    Array of strings
//   * @param extraBundles One or more bundles
//   * @return
//   */
//  public static PendingIntent createIntent(String action, String[] extraKeys, Bundle... extraBundles) {
//    Context context = ContextHolder.getApplicationContext();
//    Intent intent = new Intent(context, ReceiverService.class);
//    intent.setAction(action);
//
//    for (int i = 0; i < extraKeys.length; i++) {
//      String key = extraKeys[i];
//
//      if (i <= extraBundles.length - 1) {
//        Bundle bundle = extraBundles[i];
//        intent.putExtra(key, bundle);
//      } else {
//        intent.putExtra(key, (String) null);
//      }
//    }
//
//    int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
//
//    return PendingIntent.getService(context, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//  }
//
//  /**
//   * Returns a unique request code for the services actions.
//   * These codes should match the JS NotificationEventType enum.
//   *
//   * @param action
//   * @return
//   */
//  public static int getActionRequestCode(String action) {
//    switch (action) {
//      case ACTION_NOTIFICATION_DELETE_INTENT:
//        return 0;
//      case ACTION_NOTIFICATION_PRESS_INTENT:
//        return 1;
//      case ACTION_NOTIFICATION_ACTION_PRESS_INTENT:
//        return 2;
//      case ACTION_NOTIFICATION_DELIVERED_INTENT:
//        return 3;
//      case ACTION_APP_BLOCK_STATE_CHANGED:
//        return 4;
//      case ACTION_NOTIFICATION_CHANNEL_BLOCK_STATE_CHANGED:
//        return 5;
//      case ACTION_NOTIFICATION_CHANNEL_GROUP_BLOCK_STATE_CHANGED:
//        return 6;
//      default:
//        return -1;
//    }
//  }
//
//  @Nullable
//  @Override
//  public IBinder onBind(Intent intent) {
//    return null;
//  }
//
//  @Override
//  public int onStartCommand(Intent intent, int flags, int startId) {
//    if (intent.getAction() == null) {
//      Log.d(TAG, "Received intent with no action");
//      return RETURN_COMMAND;
//    }
//
//    String action = intent.getAction();
//
//    switch (action) {
//      case ACTION_NOTIFICATION_DELETE_INTENT:
//        onDeleteIntent(intent);
//        break;
//      case ACTION_NOTIFICATION_PRESS_INTENT:
//        onPressIntent(intent);
//        break;
//      case ACTION_NOTIFICATION_DELIVERED_INTENT:
//        onDeliveredIntent(intent);
//        break;
//      case ACTION_NOTIFICATION_ACTION_PRESS_INTENT:
//        onActionPressIntent(intent);
//        break;
//    }
//
//    // If the app is current running, send a JS event
//    if (isAppInForeground(ContextHolder.getApplicationContext())) {
//      Log.d(TAG, "Sending event to running application");
//      NotifeeEvent event = new NotifeeEvent(RECEIVER_SERVICE_EVENT_KEY, getIntentMap(intent, false));
//      NotifeeEventEmitter.getSharedInstance().sendEvent(event);
//    }
//    // The app is not running, so spawn a headless task process
//    else {
//      Log.d(TAG, "Sending event to Headless task");
//      startTask(getTaskConfig(intent));
//    }
//
//    return RETURN_COMMAND;
//  }
//
//  /**
//   * Called when a notification is dismissed or cleared
//   *
//   * @param intent
//   * @url https://developer.android.com/reference/androidx/core/app/NotificationCompat.Builder.html#setDeleteIntent(android.app.PendingIntent)
//   */
//  private void onDeleteIntent(Intent intent) {
//    // TODO update database with "dismissed" status
//    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));
//  }
//
//  /**
//   * Called when a notification is pressed by the user
//   *
//   * @param intent
//   * @url https://developer.android.com/reference/androidx/core/app/NotificationCompat.Builder.html#setContentIntent(android.app.PendingIntent)
//   */
//  private void onPressIntent(Intent intent) {
//    // TODO update database with "read" status
//    Bundle onPressActionBundle = Objects.requireNonNull(intent.getBundleExtra("action"));
//    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));
//
//    // Always launch an activity when a notification is pressed
//    launchPendingIntentActivity(
//      Objects.requireNonNull(notificationBundle.getString("id")),
//      onPressActionBundle.getString("launchActivity"),
//      onPressActionBundle.getString("reactComponent")
//    );
//  }
//
//  /**
//   * Called when a notification is delivered to the device
//   *
//   * @param intent
//   */
//  private void onDeliveredIntent(Intent intent) {
//    // TODO update database with notification delivered
//    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));
//  }
//
//  /**
//   * Called when a notification is delivered to the device
//   *
//   * @param intent
//   */
//  private void onActionPressIntent(Intent intent) {
//    Bundle onPressActionBundle = Objects.requireNonNull(intent.getBundleExtra("action"));
//    Bundle notificationBundle = Objects.requireNonNull(intent.getBundleExtra("notification"));
//
//    String launchActivity = onPressActionBundle.getString("launchActivity");
//    String reactComponent = onPressActionBundle.getString("reactComponent");
//
//    // If there is no activity, do nothing
//    if (launchActivity == null && reactComponent == null) {
//      return;
//    }
//
//    String notificationId = Objects.requireNonNull(notificationBundle.getString("id"));
//
//    launchPendingIntentActivity(notificationId, launchActivity, reactComponent);
//
//    NotificationManagerCompat.from(getApplicationContext()).cancel(Objects.requireNonNull(notificationId).hashCode());
//  }
//
//  /**
//   * Launch an activity with a pending intent
//   *
//   * @param notificationId Unique notification id
//   * @param launchActivity Optional activity name to launch
//   * @param reactComponent Optional react component to use
//   */
//  private void launchPendingIntentActivity(@NonNull String notificationId, @Nullable String launchActivity, @Nullable String reactComponent) {
//    Class launchActivityClass = getLaunchActivity(launchActivity);
//
//    Intent launchIntent = new Intent(getApplicationContext(), launchActivityClass);
//    launchIntent.setAction(ACTION_NOTIFICATION_INTENT);
//
//    if (reactComponent != null) {
//      synchronized (reactComponents) {
//        reactComponents.add(reactComponent);
//      }
//      launchIntent.putExtra("reactComponent", reactComponent);
//    }
//
//    PendingIntent pendingContentIntent = PendingIntent.getActivity(
//      getApplicationContext(),
//      notificationId.hashCode(),
//      launchIntent,
//      PendingIntent.FLAG_UPDATE_CURRENT
//    );
//
//    try {
//      pendingContentIntent.send();
//    } catch (Exception e) {
//      Logger.e(
//        "ReceiverService",
//        "Failed to send PendingIntent from launchPendingIntentActivity for notification " + notificationId,
//        e
//      );
//    }
//  }
//}
