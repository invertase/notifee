package app.notifee.core.bundles;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;

import app.notifee.core.utils.ResourceUtils;
//import io.invertase.notifee.NotifeeReceiverService;

@Keep
public class NotificationAndroidActionBundle {

  private Bundle mNotificationAndroidActionBundle;

  private NotificationAndroidActionBundle(Bundle actionBundle) {
    mNotificationAndroidActionBundle = actionBundle;
  }

  public static NotificationAndroidActionBundle fromBundle(Bundle actionBundle) {
    return new NotificationAndroidActionBundle(actionBundle);
  }

  /**
   * Creates a NotificationCompat.Action from the given NotificationAndroidActionBundle instance
   *
   * @return Task<NotificationCompat.Action>
   */
//  public Task<NotificationCompat.Action> toNotificationAction() {
//    // TODO executor
//    return Tasks.call(() -> {
//
//      // TODO action pending intent handler!
//      Intent intent = new Intent(ContextHolder.getApplicationContext(), NotifeeReceiverService.class);
//      PendingIntent pendingIntent = PendingIntent.getService(ContextHolder.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//      Bitmap icon = Tasks.await(getIcon());
//
//      NotificationCompat.Action.Builder actionBuilder = new NotificationCompat.Action.Builder(
//        IconCompat.createWithAdaptiveBitmap(icon),
//        getTitle(),
//        pendingIntent
//      );
//
//      RemoteInput remoteInput = getRemoteInput(actionBuilder);
//      if (remoteInput != null) {
//        actionBuilder.addRemoteInput(remoteInput);
//      }
//
//      return actionBuilder.build();
//    });
//  }

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
    return ResourceUtils.getImageBitmapFromUrl(icon);
  }

  /**
   * Gets the onPressAction instance for this action
   *
   * @return NotificationAndroidPressActionBundle
   */
  public @NonNull
  NotificationAndroidPressActionBundle getPressAction() {
    Bundle pressActionBundle = mNotificationAndroidActionBundle.getBundle("onPressAction");
    return NotificationAndroidPressActionBundle.fromBundle(pressActionBundle);
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
