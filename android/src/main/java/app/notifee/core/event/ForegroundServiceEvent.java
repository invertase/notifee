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

import app.notifee.core.KeepForSdk;
import app.notifee.core.interfaces.MethodCallResult;
import app.notifee.core.model.NotificationModel;

@KeepForSdk
public class ForegroundServiceEvent {
  private final NotificationModel notification;
  private MethodCallResult<Void> result;
  private boolean completed = false;

  public ForegroundServiceEvent(
      NotificationModel notificationModel, MethodCallResult<Void> result) {
    this.notification = notificationModel;
    this.result = result;
  }

  @KeepForSdk
  public NotificationModel getNotification() {
    return notification;
  }

  @KeepForSdk
  public void setCompletionResult() {
    if (!completed) {
      completed = true;
      result.onComplete(null, null);
    }
  }
}
