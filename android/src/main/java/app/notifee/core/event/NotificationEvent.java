package app.notifee.core.event;

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
import androidx.annotation.Nullable;
import app.notifee.core.KeepForSdk;
import app.notifee.core.model.NotificationModel;

@KeepForSdk
public class NotificationEvent {
  @KeepForSdk public static final int TYPE_DISMISSED = 0;

  @KeepForSdk public static final int TYPE_PRESS = 1;

  @KeepForSdk public static final int TYPE_ACTION_PRESS = 2;

  @KeepForSdk public static final int TYPE_DELIVERED = 3;

  @KeepForSdk public static final int TYPE_TRIGGER_NOTIFICATION_CREATED = 7;

  private final int type;
  private final Bundle extras;
  private final NotificationModel notification;
  private final Boolean presented;

  public NotificationEvent(int type, NotificationModel bundle, Boolean presented) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
    this.presented = presented;
  }

  public NotificationEvent(int type, NotificationModel bundle) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
    this.presented = null;
  }

  public NotificationEvent(int type, NotificationModel bundle, Bundle extras) {
    this.type = type;
    this.notification = bundle;
    this.extras = extras;
    presented = null;
  }

  @KeepForSdk
  public int getType() {
    return type;
  }

  @KeepForSdk
  public NotificationModel getNotification() {
    return notification;
  }

  @KeepForSdk
  public Boolean getPresented() {
    return presented;
  }

  @KeepForSdk
  @Nullable
  public Bundle getExtras() {
    return extras;
  }
}
