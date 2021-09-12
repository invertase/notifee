package app.notifee.core.interfaces;

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
import app.notifee.core.event.BlockStateEvent;
import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.event.LogEvent;
import app.notifee.core.event.NotificationEvent;

@KeepForSdk
public interface EventListener {
  @KeepForSdk
  void onNotificationEvent(NotificationEvent notificationEvent);

  @KeepForSdk
  void onLogEvent(LogEvent logEvent);

  @KeepForSdk
  void onBlockStateEvent(BlockStateEvent blockStateEvent);

  @KeepForSdk
  void onForegroundServiceEvent(ForegroundServiceEvent foregroundServiceEvent);
}
