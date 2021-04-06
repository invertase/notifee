package app.notifee.core.model;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

import app.notifee.core.EventBus;
import app.notifee.core.event.MainComponentEvent;
import app.notifee.core.utility.IntentUtils;

import static app.notifee.core.ContextHolder.getApplicationContext;

@Keep
public class NotificationAndroidBubbleActionModel {
  private Bundle mNotificationAndroidBubbleActionBundle;

  private NotificationAndroidBubbleActionModel(Bundle bundle) {
    mNotificationAndroidBubbleActionBundle = bundle;
  }

  public static NotificationAndroidBubbleActionModel fromBundle(Bundle bundle) {
    return new NotificationAndroidBubbleActionModel(bundle);
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidBubbleActionBundle.clone();
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mNotificationAndroidBubbleActionBundle.getString("id"));
  }

  public @Nullable String getLaunchActivity() {
    return mNotificationAndroidBubbleActionBundle.getString("launchActivity");
  }

  public @Nullable String getMainComponent() {
    return mNotificationAndroidBubbleActionBundle.getString("mainComponent");
  }

  public int getLaunchActivityFlags() {
    if (!mNotificationAndroidBubbleActionBundle.containsKey("launchActivityFlags")) {
      return -1;
    }

    int baseFlags = 0;
    ArrayList<Integer> launchActivityFlags =
      Objects.requireNonNull(
        mNotificationAndroidBubbleActionBundle.getIntegerArrayList("launchActivityFlags"));

    for (int i = 0; i < launchActivityFlags.size(); i++) {
      Integer flag = launchActivityFlags.get(i);
      switch (flag) {
        case 0:
          baseFlags |= Intent.FLAG_ACTIVITY_NO_HISTORY;
          break;
        case 1:
          baseFlags |= Intent.FLAG_ACTIVITY_SINGLE_TOP;
          break;
        case 2:
          baseFlags |= Intent.FLAG_ACTIVITY_NEW_TASK;
          break;
        case 3:
          baseFlags |= Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
          break;
        case 4:
          baseFlags |= Intent.FLAG_ACTIVITY_CLEAR_TOP;
          break;
        case 5:
          baseFlags |= Intent.FLAG_ACTIVITY_FORWARD_RESULT;
          break;
        case 6:
          baseFlags |= Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;
          break;
        case 7:
          baseFlags |= Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS;
          break;
        case 8:
          baseFlags |= Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
          break;
        case 9:
          baseFlags |= Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED;
          break;
        case 10:
          baseFlags |= Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY;
          break;
        case 11:
          baseFlags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
          break;
        case 12:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            baseFlags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
          }
          break;
        case 13:
          baseFlags |= Intent.FLAG_ACTIVITY_NO_USER_ACTION;
          break;
        case 14:
          baseFlags |= Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
          break;
        case 15:
          baseFlags |= Intent.FLAG_ACTIVITY_NO_ANIMATION;
          break;
        case 16:
          baseFlags |= Intent.FLAG_ACTIVITY_CLEAR_TASK;
          break;
        case 17:
          baseFlags |= Intent.FLAG_ACTIVITY_TASK_ON_HOME;
          break;
        case 18:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            baseFlags |= Intent.FLAG_ACTIVITY_RETAIN_IN_RECENTS;
          }
          break;
        case 19:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            baseFlags |= Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT;
          }
          break;
        case 20:
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            baseFlags |= Intent.FLAG_ACTIVITY_MATCH_EXTERNAL;
          }
          break;
      }
    }
    return baseFlags;
  }


  /*
   * A task continuation for bubble, if specified.
   */
  public Task<NotificationCompat.BubbleMetadata> getBubbleActionTask(Executor executor, NotificationModel notificationModel) {
    return Tasks.call(
      executor,
      () -> {
        String launchActivity = getLaunchActivity();
        Class launchActivityClass = IntentUtils.getLaunchActivity(launchActivity);
        Intent bubbleIntent = new Intent(getApplicationContext(), launchActivityClass);
        if (getLaunchActivityFlags() != -1) {
          bubbleIntent.addFlags(getLaunchActivityFlags());
        }

        if (mNotificationAndroidBubbleActionBundle.containsKey("mainComponent")) {
          bubbleIntent.putExtra("mainComponent", getMainComponent());
          EventBus.postSticky(
            new MainComponentEvent(getMainComponent()));
        }

        NotificationCompat.BubbleMetadata.Builder bubbleBuilder = new NotificationCompat.BubbleMetadata.Builder();
        PendingIntent bubblePendingIntent =
          PendingIntent.getActivity(
            getApplicationContext(),
            notificationModel.getHashCode(),
            bubbleIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);
        bubbleBuilder.setIntent(bubblePendingIntent);

        IconCompat bubbleIcon = IconCompat.createWithContentUri(Objects.requireNonNull(mNotificationAndroidBubbleActionBundle.getString("icon")));
        bubbleBuilder.setIcon(bubbleIcon);

        if (mNotificationAndroidBubbleActionBundle.containsKey("height")) {
          bubbleBuilder.setDesiredHeight(mNotificationAndroidBubbleActionBundle.getInt("height"));
        }

        if (mNotificationAndroidBubbleActionBundle.containsKey("autoExpand")) {
          bubbleBuilder.setAutoExpandBubble(mNotificationAndroidBubbleActionBundle.getBoolean("autoExpand"));
        }

        if (mNotificationAndroidBubbleActionBundle.containsKey("suppressNotification")) {
          bubbleBuilder.setSuppressNotification(mNotificationAndroidBubbleActionBundle.getBoolean("suppressNotification"));
        }

        return bubbleBuilder.build();
  });
  }
}
