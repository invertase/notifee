package io.invertase.notifee.bundles;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Objects;

import io.invertase.notifee.NotifeeReceiverService;

import static io.invertase.notifee.NotifeeUtils.getImageBitmapFromUrl;
import static io.invertase.notifee.core.NotifeeContextHolder.getApplicationContext;

public class NotifeeNotificationAndroidActionBundle {

  private Bundle mNotificationAndroidActionBundle;

  NotifeeNotificationAndroidActionBundle(Bundle actionBundle) {
    mNotificationAndroidActionBundle = actionBundle;
  }

  /**
   * Creates a NotificationCompat.Action from the given NotifeeNotificationAndroidActionBundle instance
   *
   * @param bundle NotifeeNotificationAndroidActionBundle instance
   * @return Task<NotificationCompat.Action>
   */
  public static Task<NotificationCompat.Action> toNotificationAction(NotifeeNotificationAndroidActionBundle bundle) {
    // TODO executor
    return Tasks.call(() -> {

      // TODO action pending intent handler!
      Intent intent = new Intent(getApplicationContext(), NotifeeReceiverService.class);
      PendingIntent pendingIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

      Bitmap icon = Tasks.await(bundle.getIcon());

      NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(
        IconCompat.createWithAdaptiveBitmap(icon),
        bundle.getTitle(),
        pendingIntent
      );

      RemoteInput remoteInput = bundle.getRemoteInput(actionBuilder);
      if (remoteInput != null) {
        actionBuilder.addRemoteInput(remoteInput);
      }

      return actionBuilder.build();
    });
  }

  /**
   * Gets the title of the action
   *
   * @return String
   */
  public @NonNull
  String getTitle() {
    return Objects.requireNonNull(mNotificationAndroidActionBundle.getString("title"));
  }

  /**
   * Gets the icon of the action
   *
   * @return Task<Bitmap>
   */
  public Task<Bitmap> getIcon() {
    String icon = Objects.requireNonNull(mNotificationAndroidActionBundle.getString("icon"));
    return getImageBitmapFromUrl(icon);
  }

  /**
   * Gets the onPressAction instance for this action
   *
   * @return NotifeeNotificationAndroidPressActionBundle
   */
  public @NonNull
  NotifeeNotificationAndroidPressActionBundle getPressAction() {
    Bundle pressActionBundle = mNotificationAndroidActionBundle.getBundle("onPressAction");
    return new NotifeeNotificationAndroidPressActionBundle(pressActionBundle);
  }

  /**
   * Gets a remote input instance for the action
   *
   * @param actionBuilder The allowGeneratedReplies is inside of the remote input to reduce confusion,
   *                      but it lives on the action builder
   * @return RemoteInput
   */
  public @Nullable
  RemoteInput getRemoteInput(NotificationCompat.Action.Builder actionBuilder) {
    if (mNotificationAndroidActionBundle.containsKey("input") && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
      Bundle inputBundle = Objects.requireNonNull(mNotificationAndroidActionBundle.getBundle("input"));

      // TODO reciever key
      RemoteInput.Builder remoteInputBuilder = new RemoteInput.Builder("TODO");

      if (inputBundle.containsKey("allowFreeFormInput")) {
        remoteInputBuilder.setAllowFreeFormInput(inputBundle.getBoolean("allowFreeFormInput"));
      }

      if (inputBundle.containsKey("allowGeneratedReplies")) {
        actionBuilder.setAllowGeneratedReplies(inputBundle.getBoolean("allowGeneratedReplies"));
      }

      if (inputBundle.containsKey("placeholder")) {
        remoteInputBuilder.setLabel(inputBundle.getCharSequence("placeholder"));
      }

      if (inputBundle.containsKey("choices")) {
        ArrayList<String> choicesArray = inputBundle.getStringArrayList("choices");
        CharSequence[] choices = Objects.requireNonNull(choicesArray).toArray(new CharSequence[choicesArray.size()]);
        remoteInputBuilder.setChoices(choices);
      }

      if (inputBundle.containsKey("editableChoices")) {
        boolean editable = inputBundle.getBoolean("editableChoices");
        if (editable) {
          remoteInputBuilder.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_ENABLED);
        } else {
          remoteInputBuilder.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_DISABLED);
        }
      } else {
        remoteInputBuilder.setEditChoicesBeforeSending(RemoteInput.EDIT_CHOICES_BEFORE_SENDING_AUTO);
      }

      return remoteInputBuilder.build();
    }

    return null;
  }
}
