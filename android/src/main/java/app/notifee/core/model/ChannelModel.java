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

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import app.notifee.core.utility.ObjectUtils;
import java.util.ArrayList;
import java.util.Objects;

public class ChannelModel {

  private final Bundle mChannelBundle;

  private ChannelModel(Bundle bundle) {
    this.mChannelBundle = bundle;
  }

  public static ChannelModel fromBundle(@NonNull Bundle bundle) {
    return new ChannelModel(bundle);
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mChannelBundle.getString("id"));
  }

  public @NonNull String getName() {
    return Objects.requireNonNull(mChannelBundle.getString("name"));
  }

  public Boolean getBadge() {
    return mChannelBundle.getBoolean("badge", true);
  }

  public Boolean getBypassDnd() {
    return mChannelBundle.getBoolean("bypassDnd", false);
  }

  public @Nullable String getDescription() {
    return mChannelBundle.getString("description");
  }

  public Boolean getLights() {
    return mChannelBundle.getBoolean("lights", true);
  }

  public Boolean getVibration() {
    return mChannelBundle.getBoolean("vibration", true);
  }

  public @Nullable String getGroupId() {
    return mChannelBundle.getString("groupId");
  }

  public Integer getImportance() {
    if (mChannelBundle.containsKey("importance")) {
      return ObjectUtils.getInt(mChannelBundle.get("importance"));
    }

    return NotificationManagerCompat.IMPORTANCE_DEFAULT;
  }

  public @Nullable Integer getLightColor() {
    if (mChannelBundle.containsKey("lightColor")) {
      return Color.parseColor(mChannelBundle.getString("lightColor"));
    }

    return null;
  }

  public int getVisibility() {
    if (mChannelBundle.containsKey("visibility")) {
      return ObjectUtils.getInt(mChannelBundle.get("visibility"));
    }

    return NotificationCompat.VISIBILITY_PRIVATE;
  }

  public long[] getVibrationPattern() {
    if (!mChannelBundle.containsKey("vibrationPattern")) {
      return new long[0];
    }

    ArrayList vibrationPattern =
        Objects.requireNonNull(mChannelBundle.getParcelableArrayList("vibrationPattern"));

    long[] vibrateArray = new long[vibrationPattern.size()];

    for (int i = 0; i < vibrationPattern.size(); i++) {
      Integer value = (Integer) vibrationPattern.get(i);
      vibrateArray[i] = value.longValue();
    }

    return vibrateArray;
  }

  public @Nullable String getSound() {
    if (!mChannelBundle.containsKey("sound")) {
      return null;
    }

    return mChannelBundle.getString("sound");
  }
}
