package app.notifee.core.model;

import android.app.PendingIntent;
import android.os.Bundle;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.graphics.drawable.IconCompat;

import java.util.ArrayList;
import java.util.Objects;

import app.notifee.core.R;

import static app.notifee.core.ReceiverService.REMOTE_INPUT_RECEIVER_KEY;

@Keep
public class NotificationAndroidActionModel {

  private Bundle mNotificationAndroidActionBundle;

  private NotificationAndroidActionModel(Bundle actionBundle) {
    mNotificationAndroidActionBundle = actionBundle;
  }

  public static NotificationAndroidActionModel fromBundle(Bundle actionBundle) {
    return new NotificationAndroidActionModel(actionBundle);
  }

  public Bundle toBundle() {
    return (Bundle) mNotificationAndroidActionBundle.clone();
  }

  /**
   * Gets the title of the action
   *
   * @return String
   */
  public @NonNull String getTitle() {
    return Objects.requireNonNull(mNotificationAndroidActionBundle.getString("title"));
  }

  /** Gets the icon of the action */
  public @Nullable String getIcon() {
    return mNotificationAndroidActionBundle.getString("icon");
  }

  /**
   * Gets the pressAction instance for this action
   *
   * @return NotificationAndroidPressActionModel
   */
  public @NonNull NotificationAndroidPressActionModel getPressAction() {
    Bundle pressActionBundle = mNotificationAndroidActionBundle.getBundle("pressAction");
    return NotificationAndroidPressActionModel.fromBundle(pressActionBundle);
  }

  /**
   * Gets the Action.Builder instance for this Media Style action.
   *
   * Sets default values for title and icon if none are specified
   *
   * @return NotificationCompat.Action.Builder
   */
  public @Nullable NotificationCompat.Action.Builder getMediaStyleActionBuilder(IconCompat iconCompat, PendingIntent pendingIntent) {
    NotificationCompat.Action.Builder actionBuilder;
    String defaultMediaTitle = "";
    int defaultMediaIcon = R.drawable.play;

    String title = getTitle();

    if (iconCompat == null || title == null) {
      NotificationAndroidPressActionModel pressActionBundle = getPressAction();

      if (pressActionBundle != null) {
        switch (pressActionBundle.getId()) {
          case "play":
            defaultMediaTitle = "play";
            defaultMediaIcon = R.drawable.play;
            break;
          case "pause":
            defaultMediaTitle = "pause";
            defaultMediaIcon = R.drawable.pause;
            break;
          case "stop":
            defaultMediaTitle = "stop";
            defaultMediaIcon = R.drawable.stop;
            break;
          case "next":
            defaultMediaTitle = "next";
            defaultMediaIcon = R.drawable.next;
            break;
          case "previous":
            defaultMediaTitle = "previous";
            defaultMediaIcon = R.drawable.previous;
            break;
        }
      }
    }

    if (title == null) {
      title = defaultMediaTitle;
    }

    if (iconCompat == null) {
      actionBuilder = new NotificationCompat.Action.Builder(defaultMediaIcon, title, pendingIntent);
    } else {
      actionBuilder = new NotificationCompat.Action.Builder(iconCompat, title, pendingIntent);
    }

    return actionBuilder;
  }

  /**
   * Gets a remote input instance for the action
   *
   * @param actionBuilder The allowGeneratedReplies is inside of the remote input to reduce
   *     confusion, but it lives on the action builder
   * @return RemoteInput
   */
  public @Nullable RemoteInput getRemoteInput(NotificationCompat.Action.Builder actionBuilder) {
    if (mNotificationAndroidActionBundle.containsKey("input")
        && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
      Bundle inputBundle =
          Objects.requireNonNull(mNotificationAndroidActionBundle.getBundle("input"));

      RemoteInput.Builder remoteInputBuilder = new RemoteInput.Builder(REMOTE_INPUT_RECEIVER_KEY);

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
        CharSequence[] choices =
            Objects.requireNonNull(choicesArray).toArray(new CharSequence[choicesArray.size()]);
        remoteInputBuilder.setChoices(choices);
      }

      if (inputBundle.containsKey("editableChoices")) {
        boolean editable = inputBundle.getBoolean("editableChoices");
        if (editable) {
          remoteInputBuilder.setEditChoicesBeforeSending(
              RemoteInput.EDIT_CHOICES_BEFORE_SENDING_ENABLED);
        } else {
          remoteInputBuilder.setEditChoicesBeforeSending(
              RemoteInput.EDIT_CHOICES_BEFORE_SENDING_DISABLED);
        }
      } else {
        remoteInputBuilder.setEditChoicesBeforeSending(
            RemoteInput.EDIT_CHOICES_BEFORE_SENDING_AUTO);
      }

      return remoteInputBuilder.build();
    }

    return null;
  }
}
