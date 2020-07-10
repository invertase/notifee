package app.notifee.core.model;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.Objects;

public class ChannelGroupModel {

  private final Bundle mChannelGroupBundle;

  private ChannelGroupModel(Bundle bundle) {
    this.mChannelGroupBundle = bundle;
  }

  public static ChannelGroupModel fromBundle(Bundle bundle) {
    return new ChannelGroupModel(bundle);
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
