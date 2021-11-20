package app.notifee.core;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import static app.notifee.core.event.NotificationEvent.TYPE_ACTION_PRESS;
import static app.notifee.core.event.NotificationEvent.TYPE_DISMISSED;
import static app.notifee.core.event.NotificationEvent.TYPE_PRESS;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.RemoteInput;
import app.notifee.core.event.InitialNotificationEvent;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.model.NotificationAndroidPressActionModel;
import app.notifee.core.model.NotificationModel;
import app.notifee.core.utility.IntentUtils;
import java.util.concurrent.atomic.AtomicInteger;

public class ReceiverService extends Service {
  private static final String TAG = "ReceiverService";
  public static final String REMOTE_INPUT_RECEIVER_KEY =
      "app.notifee.core.ReceiverService.REMOTE_INPUT_RECEIVER_KEY";

  private static final AtomicInteger uniqueIds = new AtomicInteger(0);

  static final String DELETE_INTENT = "app.notifee.core.ReceiverService.DELETE_INTENT";
  static final String PRESS_INTENT = "app.notifee.core.ReceiverService.PRESS_INTENT";
  static final String ACTION_PRESS_INTENT = "app.notifee.core.ReceiverService.ACTION_PRESS_INTENT";

  /**
   * Creates a PendingIntent, which when sent triggers this class.
   *
   * @param action An Action - matches up with the JS EventType Enum.
   * @param extraKeys Array of strings
   * @param extraBundles One or more bundles
   */
  public static PendingIntent createIntent(
      String action, String[] extraKeys, Bundle... extraBundles) {
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

    int uniqueInt = uniqueIds.getAndIncrement();
    return PendingIntent.getService(context, uniqueInt, intent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);
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

  /** Handle users delete/dismiss intents */
  private void onDeleteIntent(Intent intent) {
    Bundle notification = intent.getBundleExtra("notification");

    if (notification == null) {
      return;
    }

    NotificationModel notificationModel = NotificationModel.fromBundle(notification);
    EventBus.post(new NotificationEvent(TYPE_DISMISSED, notificationModel));
  }

  /** Handle user notification press */
  private void onPressIntent(Intent intent) {
    Bundle notification = intent.getBundleExtra("notification");

    if (notification == null) {
      return;
    }

    NotificationModel notificationModel = NotificationModel.fromBundle(notification);

    Bundle pressAction = intent.getBundleExtra("pressAction");
    NotificationAndroidPressActionModel pressActionBundle = null;

    Bundle extras = new Bundle();

    if (pressAction != null) {
      pressActionBundle = NotificationAndroidPressActionModel.fromBundle(pressAction);
      extras.putBundle("pressAction", pressActionBundle.toBundle());
    }

    EventBus.post(new NotificationEvent(TYPE_PRESS, notificationModel, extras));

    if (pressActionBundle == null) {
      return;
    }

    String launchActivity = pressActionBundle.getLaunchActivity();
    String mainComponent = pressActionBundle.getMainComponent();

    if (launchActivity != null || mainComponent != null) {
      InitialNotificationEvent initialNotificationEvent =
          new InitialNotificationEvent(notificationModel, extras);
      launchPendingIntentActivity(
          initialNotificationEvent,
          launchActivity,
          mainComponent,
          pressActionBundle.getLaunchActivityFlags());
    }
  }

  /** Handle action intents */
  private void onActionPressIntent(Intent intent) {
    Bundle notification = intent.getBundleExtra("notification");
    Bundle pressAction = intent.getBundleExtra("pressAction");

    if (notification == null || pressAction == null) {
      return;
    }

    NotificationModel notificationModel = NotificationModel.fromBundle(notification);
    NotificationAndroidPressActionModel pressActionBundle =
        NotificationAndroidPressActionModel.fromBundle(pressAction);

    Bundle extras = new Bundle();
    extras.putBundle("pressAction", pressActionBundle.toBundle());

    Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
    if (remoteInput != null) {
      CharSequence input = remoteInput.getCharSequence(REMOTE_INPUT_RECEIVER_KEY);
      if (input != null) {
        extras.putString("input", input.toString());
      }
    }

    EventBus.post(new NotificationEvent(TYPE_ACTION_PRESS, notificationModel, extras));

    String launchActivity = pressActionBundle.getLaunchActivity();
    String mainComponent = pressActionBundle.getMainComponent();

    if (launchActivity != null || mainComponent != null) {
      InitialNotificationEvent initialNotificationEvent =
          new InitialNotificationEvent(notificationModel, extras);
      launchPendingIntentActivity(
          initialNotificationEvent,
          launchActivity,
          mainComponent,
          pressActionBundle.getLaunchActivityFlags());
    }
  }

  private void launchPendingIntentActivity(
      InitialNotificationEvent initialNotificationEvent,
      @Nullable String launchActivity,
      @Nullable String mainComponent,
      int launchActivityFlags) {
    Class<?> launchActivityClass = IntentUtils.getLaunchActivity(launchActivity);
    if (launchActivityClass == null) {
      Logger.e(TAG, "Failed to get launch activity");
      return;
    }

    Intent launchIntent = new Intent(getApplicationContext(), launchActivityClass);

    if (launchActivityFlags != -1) {
      launchIntent.addFlags(launchActivityFlags);
    }

    if (mainComponent != null) {
      launchIntent.putExtra("mainComponent", mainComponent);
    }

    PendingIntent pendingContentIntent =
        PendingIntent.getActivity(
            getApplicationContext(),
            initialNotificationEvent.getNotificationModel().getHashCode(),
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_MUTABLE);

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
          "Failed to send PendingIntent from launchPendingIntentActivity for notification "
              + initialNotificationEvent.getNotificationModel().getId(),
          e);
    }
  }
}
