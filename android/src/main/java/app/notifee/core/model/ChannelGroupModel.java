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
