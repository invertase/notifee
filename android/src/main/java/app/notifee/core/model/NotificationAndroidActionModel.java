package app.notifee.core.model;

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

import static app.notifee.core.ReceiverService.REMOTE_INPUT_RECEIVER_KEY;

import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import java.util.ArrayList;
import java.util.Objects;

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
