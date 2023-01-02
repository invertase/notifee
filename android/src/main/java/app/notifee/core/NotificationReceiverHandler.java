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

import static app.notifee.core.NotificationPendingIntent.EVENT_TYPE_INTENT_KEY;
import static app.notifee.core.NotificationPendingIntent.NOTIFICATION_ID_INTENT_KEY;
import static app.notifee.core.event.NotificationEvent.TYPE_ACTION_PRESS;
import static app.notifee.core.event.NotificationEvent.TYPE_PRESS;

import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.NotificationManagerCompat;
import app.notifee.core.event.InitialNotificationEvent;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.model.NotificationAndroidPressActionModel;
import app.notifee.core.model.NotificationModel;

public class NotificationReceiverHandler {
  public static final String REMOTE_INPUT_RECEIVER_KEY =
      "app.notifee.core.ReceiverService.REMOTE_INPUT_RECEIVER_KEY";
  private static final String TAG = "NotificationReceiverHandler";

  static void handleNotification(Context context, Intent intent) {
    if (!intent.hasExtra("notification")) {
      return;
    }

    if (context != null && ContextHolder.getApplicationContext() == null) {
      ContextHolder.setApplicationContext(context.getApplicationContext());
    }

    handleNotificationIntent(context, intent);
  }

  private static void handleNotificationIntent(Context context, Intent intent) {
    int type = intent.getIntExtra(EVENT_TYPE_INTENT_KEY, 0);

    if (type == 0) {
      // no-op, ignore intent
      return;
    }

    switch (type) {
      case TYPE_PRESS:
        handleNotificationPressIntent(context, intent);
        break;
      case TYPE_ACTION_PRESS:
        handleNotificationActionIntent(context, intent);
        break;
    }
  }

  private static void handleNotificationActionIntent(Context context, Intent intent) {
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

    NotificationManagerCompat.from(context)
        .cancel(intent.getIntExtra(NOTIFICATION_ID_INTENT_KEY, 0));

    InitialNotificationEvent initialNotificationEvent =
        new InitialNotificationEvent(notificationModel, extras);
    EventBus.postSticky(initialNotificationEvent);

    // Send event
    EventBus.post(new NotificationEvent(TYPE_ACTION_PRESS, notificationModel, extras));
  }

  private static void handleNotificationPressIntent(Context context, Intent intent) {
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

    String mainComponent = pressActionBundle.getMainComponent();
    InitialNotificationEvent initialNotificationEvent =
        new InitialNotificationEvent(notificationModel, extras);
    EventBus.postSticky(initialNotificationEvent);

    // Send sticky event to save the mainComponent
    if (mainComponent != null) {
      EventBus.postSticky(new MainComponentEvent(mainComponent));
    }
  }
}
