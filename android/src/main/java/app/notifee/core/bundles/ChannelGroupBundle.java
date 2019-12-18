package app.notifee.core.bundles;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class ChannelGroupBundle {

  private final Bundle mChannelGroupBundle;

  private ChannelGroupBundle(Bundle bundle) {
    this.mChannelGroupBundle = bundle;
  }

  public static ChannelGroupBundle fromBundle(Bundle bundle) {
    return new ChannelGroupBundle(bundle);
  }

  public @NonNull String getId() {
    return Objects.requireNonNull(mChannelGroupBundle.getString("id"));
  }

  public @NonNull String getName() {
    return Objects.requireNonNull(mChannelGroupBundle.getString("name"));
  }

  public @Nullable String getDescription() {
    return mChannelGroupBundle.getString("description");
  }
}
