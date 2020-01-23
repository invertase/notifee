package app.notifee.core;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.RemoteInput;

import java.security.spec.ECField;
import java.util.concurrent.TimeUnit;

import app.notifee.core.bundles.NotificationAndroidPressActionBundle;
import app.notifee.core.bundles.NotificationBundle;
import app.notifee.core.events.InitialNotificationEvent;
import app.notifee.core.events.MainComponentEvent;
import app.notifee.core.events.NotificationEvent;

import static app.notifee.core.LicenseManager.logLicenseWarningForEvent;
import static app.notifee.core.events.NotificationEvent.TYPE_ACTION_PRESS;
import static app.notifee.core.events.NotificationEvent.TYPE_DISMISSED;
import static app.notifee.core.events.NotificationEvent.TYPE_PRESS;

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

    switch (action) {
      case DELETE_INTENT:
        onDeleteIntent(intent);
        break;
      case PRESS_INTENT:
        onPressIntent(intent);
        break;
      case ACTION_PRESS_INTENT:
        onActionPressIntent(intent);
        break;
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

    Bundle pressAction = intent.getBundleExtra("pressAction");
    NotificationAndroidPressActionBundle pressActionBundle = null;

    Bundle extras = new Bundle();

    if (pressAction != null) {
      pressActionBundle = NotificationAndroidPressActionBundle.fromBundle(pressAction);
      extras.putBundle("pressAction", pressActionBundle.toBundle());
    }

    EventBus.post(new NotificationEvent(TYPE_PRESS, notificationBundle, extras));

    if (pressActionBundle == null) {
      return;
    }

    String launchActivity = pressActionBundle.getLaunchActivity();
    String mainComponent = pressActionBundle.getMainComponent();

    if (launchActivity != null || mainComponent != null) {
      InitialNotificationEvent initialNotificationEvent = new InitialNotificationEvent(notificationBundle, extras);
      launchPendingIntentActivity(initialNotificationEvent, launchActivity, mainComponent);
    }
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

    String launchActivity = pressActionBundle.getLaunchActivity();
    String mainComponent = pressActionBundle.getMainComponent();

    if (launchActivity != null || mainComponent != null) {
      InitialNotificationEvent initialNotificationEvent = new InitialNotificationEvent(notificationBundle, extras);
      launchPendingIntentActivity(initialNotificationEvent, launchActivity, mainComponent);
    }
  }

  /**
   * @param initialNotificationEvent
   * @param launchActivity
   * @param mainComponent
   */
  private void launchPendingIntentActivity(InitialNotificationEvent initialNotificationEvent, @Nullable String launchActivity, @Nullable String mainComponent) {
    Class launchActivityClass = getLaunchActivity(launchActivity);

    Intent launchIntent = new Intent(getApplicationContext(), launchActivityClass);

    if (mainComponent != null) {
      launchIntent.putExtra("mainComponent", mainComponent);
    }

    PendingIntent pendingContentIntent = PendingIntent.getActivity(
      getApplicationContext(),
      initialNotificationEvent.getNotificationBundle().getHashCode(),
      launchIntent,
      PendingIntent.FLAG_UPDATE_CURRENT
    );

    try {
      pendingContentIntent.send();
      EventBus.postSticky(initialNotificationEvent);

      // Send sticky event to save the mainComponent
      if (mainComponent != null) {
        EventBus.postSticky(new MainComponentEvent(mainComponent));
      }
    } catch (Exception e) {
      Logger.e(
        "ReceiverService",
        "Failed to send PendingIntent from launchPendingIntentActivity for notification " + initialNotificationEvent.getNotificationBundle().getId(),
        e
      );
    }
  }

  /**
   * @param launchActivity
   * @return
   */
  private Class getLaunchActivity(@Nullable String launchActivity) {
    String activity;

    if (launchActivity != null && !launchActivity.equals("default")) {
      activity = launchActivity;
    } else {
      activity = getMainActivityClassName();
    }

    if (activity == null) {
      Logger.e(
        "ReceiverService",
        "Launch Activity for notification could not be found."
      );
      return null;
    }

    Class launchActivityClass = getClassForName(activity);

    if (launchActivityClass == null) {
      Logger.e(
        "ReceiverService",
        String.format("Launch Activity for notification does not exist ('%s').", launchActivity)
      );
      return null;
    }

    return launchActivityClass;
  }

  /**
   * @param className
   * @return
   */
  private @Nullable
  Class getClassForName(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      return null;
    }
  }

  /**
   * @return
   */
  private @Nullable
  String getMainActivityClassName() {
    String packageName = getApplicationContext().getPackageName();
    Intent launchIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);

    if (launchIntent == null || launchIntent.getComponent() == null) {
      return null;
    }

    return launchIntent.getComponent().getClassName();
  }
}
