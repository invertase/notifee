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

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.model.NotificationAndroidPressActionModel;
import app.notifee.core.utility.IntentUtils;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationPendingIntent {
  public static final String EVENT_TYPE_INTENT_KEY = "notifee_event_type";
  public static final String NOTIFICATION_ID_INTENT_KEY = "notification_id";
  private static final AtomicInteger uniqueIds = new AtomicInteger(0);
  private static final String TAG = "NotificationPendingIntent";

  /**
   * Creates a PendingIntent, which when sent triggers this class.
   *
   * @param notificationId int
   * @param pressActionModelBundle NotificationAndroidPressActionModel.
   * @param extraKeys Array of strings
   * @param extraBundles One or more bundles
   */
  static PendingIntent createIntent(
      int notificationId,
      Bundle pressActionModelBundle,
      int eventType,
      String[] extraKeys,
      Bundle... extraBundles) {
    Context context = ContextHolder.getApplicationContext();

    // Get launch activity intent
    Intent launchActivityIntent = null;
    if (pressActionModelBundle != null) {
      launchActivityIntent =
          createLaunchActivityIntent(
              context,
              notificationId,
              NotificationAndroidPressActionModel.fromBundle(pressActionModelBundle));
    }

    // Create an activity to receive notification events
    Intent receiverIntent = new Intent(context, NotificationReceiverActivity.class);

    // Set extras for each intent
    setIntentExtras(launchActivityIntent, eventType, notificationId, extraKeys, extraBundles);
    setIntentExtras(receiverIntent, eventType, notificationId, extraKeys, extraBundles);

    // Create pending intent with activities
    int uniqueInt = uniqueIds.getAndIncrement();
    Intent[] intents;

    if (launchActivityIntent != null) {
      intents = new Intent[2];
      intents[0] = launchActivityIntent;

      receiverIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      intents[1] = receiverIntent;
    } else {
      intents = new Intent[1];
      receiverIntent.setFlags(
          Intent.FLAG_ACTIVITY_NEW_TASK
              | Intent.FLAG_ACTIVITY_MULTIPLE_TASK
              | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

      intents[0] = receiverIntent;
    }

    return PendingIntent.getActivities(
        context,
        uniqueInt,
        intents,
        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
  }

  static void setIntentExtras(
      Intent intent,
      int eventType,
      int notificationId,
      String[] extraKeys,
      Bundle... extraBundles) {
    if (intent == null) {
      return;
    }

    intent.putExtra(EVENT_TYPE_INTENT_KEY, eventType);
    intent.putExtra(NOTIFICATION_ID_INTENT_KEY, notificationId);

    for (int i = 0; i < extraKeys.length; i++) {
      String key = extraKeys[i];

      if (i <= extraBundles.length - 1) {
        Bundle bundle = extraBundles[i];
        intent.putExtra(key, bundle);
      } else {
        intent.putExtra(key, (String) null);
      }
    }
  }

  static boolean shouldCreateLaunchActivityIntent(
      NotificationAndroidPressActionModel pressActionModel) {
    if (pressActionModel == null) {
      return false;
    }

    if (pressActionModel.getLaunchActivity() == null
        && pressActionModel.getMainComponent() == null) {
      return false;
    }

    return true;
  }

  static Intent createLaunchActivityIntent(
      Context context, int notificationId, NotificationAndroidPressActionModel pressActionModel) {
    if (!shouldCreateLaunchActivityIntent(pressActionModel)) {
      return null;
    }

    try {
      Intent launchActivityIntent =
          context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());

      // Get launchActivity value from payload
      String launchActivity = null;
      if (pressActionModel != null) {
        launchActivity = pressActionModel.getLaunchActivity();
      }

      // Determine if existing launchActivityIntent should be overwritten
      // to handle a custom launchActivity
      Boolean shouldOverwriteDefaultLaunchActivityIntent = launchActivityIntent == null;
      if (launchActivityIntent != null) {
        // overwrite if custom launch activity set (launch activity in payload does not equal current activity)
        shouldOverwriteDefaultLaunchActivityIntent = launchActivity != "default" && launchActivityIntent.getComponent().getClassName() != launchActivity;
      }

      // Set new launch activity intent
      if (launchActivity != null && shouldOverwriteDefaultLaunchActivityIntent) {
        Class<?> launchActivityClass = IntentUtils.getLaunchActivity(launchActivity);
        launchActivityIntent = new Intent(context, launchActivityClass);

        launchActivityIntent.setPackage(null);

        launchActivityIntent.setFlags(
            Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
      }

      // Set any additional flags or main component if specified
      if (pressActionModel.getLaunchActivityFlags() != -1) {
        launchActivityIntent.setFlags(pressActionModel.getLaunchActivityFlags());
      }

      if (pressActionModel.getMainComponent() != null) {
        launchActivityIntent.putExtra("mainComponent", pressActionModel.getMainComponent());
        EventBus.postSticky(new MainComponentEvent(pressActionModel.getMainComponent()));
      }

      return launchActivityIntent;
    } catch (Exception e) {
      Logger.e(
          "NotificationPendingIntent",
          "Failed to create LaunchActivityIntent for notification " + notificationId,
          e);
    }

    return null;
  }
}
