package app.notifee.core.model;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
      return (int) mChannelBundle.getDouble("importance");
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
      return (int) mChannelBundle.getDouble("visibility");
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
