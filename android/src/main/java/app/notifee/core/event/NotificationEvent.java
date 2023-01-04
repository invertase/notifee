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
import app.notifee.core.utility.ObjectUtils;
import java.util.HashMap;
import java.util.Map;

@KeepForSdk
public class NotificationEvent {
  @KeepForSdk public static final int TYPE_DISMISSED = 0;

  @KeepForSdk public static final int TYPE_PRESS = 1;

  @KeepForSdk public static final int TYPE_ACTION_PRESS = 2;

  @KeepForSdk public static final int TYPE_DELIVERED = 3;

  @KeepForSdk public static final int TYPE_TRIGGER_NOTIFICATION_CREATED = 7;

  @KeepForSdk public static final int TYPE_FG_ALREADY_EXIST = 8;

  private final int type;
  private final Bundle extras;
  private final NotificationModel notification;

  public NotificationEvent(int type, NotificationModel bundle) {
    this.type = type;
    this.notification = bundle;
    this.extras = null;
  }

  public NotificationEvent(int type, NotificationModel bundle, Bundle extras) {
    this.type = type;
    this.notification = bundle;
    this.extras = extras;
  }

  @KeepForSdk
  public int getType() {
    return type;
  }

  @KeepForSdk
  public NotificationModel getNotification() {
    return notification;
  }

  public Map<String, Object> toMap() {
    Map<String, Object> eventMap = new HashMap<>();
    Map<String, Object> eventDetailMap = new HashMap<>();
    eventMap.put("type", type);

    eventDetailMap.put("notification", ObjectUtils.bundleToMap(notification.toBundle()));

    if (extras != null) {
      Bundle pressAction = extras.getBundle("press_action");
      if (pressAction != null) {
        eventDetailMap.put("press_action", ObjectUtils.bundleToMap(pressAction));
      }

      String input = extras.getString("input");
      if (input != null) {
        eventDetailMap.put("input", input);
      }
    }

    eventMap.put("detail", eventDetailMap);

    return eventMap;
  }

  @KeepForSdk
  @Nullable
  public Bundle getExtras() {
    return extras;
  }
}
