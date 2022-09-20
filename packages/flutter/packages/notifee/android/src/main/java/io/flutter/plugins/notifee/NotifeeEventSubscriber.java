package io.flutter.plugins.notifee;

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

import android.content.Intent;
import androidx.annotation.NonNull;
import app.notifee.core.ContextHolder;
import app.notifee.core.event.BlockStateEvent;
import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.event.LogEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.interfaces.EventListener;
import io.flutter.plugin.common.EventChannel.EventSink;
import io.flutter.plugins.notifee.background.BackgroundUtils;
import io.flutter.plugins.notifee.background.FlutterBackgroundService;

public class NotifeeEventSubscriber implements EventListener {
  protected EventSink eventSink;

  protected NotifeeEventSubscriber() {}

  public void onForegroundListen(@NonNull EventSink eventSink) {
    this.eventSink = eventSink;
  }

  public void onForegroundCancel() {
    this.eventSink = null;
  }

  @Override
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    if (Utils.isApplicationForeground() && eventSink != null) {
      eventSink.success(notificationEvent.toMap());
      return;
    }

    //  |-> ---------------------
    //    App in Background/Quit
    //   ------------------------
    Intent onBackgroundMessageIntent =
        new Intent(ContextHolder.getApplicationContext(), FlutterBackgroundService.class);
    onBackgroundMessageIntent.putExtra(
        BackgroundUtils.EXTRA_NOTIFICATION_EVENT_NOTIFICATION,
        notificationEvent.getNotification().toBundle());
    onBackgroundMessageIntent.putExtra(
        BackgroundUtils.EXTRA_NOTIFICATION_EVENT_EXTRAS, notificationEvent.getExtras());
    onBackgroundMessageIntent.putExtra(
        BackgroundUtils.EXTRA_NOTIFICATION_EVENT_TYPE, notificationEvent.getType());
    FlutterBackgroundService.enqueueMessageProcessing(
        ContextHolder.getApplicationContext(), onBackgroundMessageIntent);
  }

  @Override
  public void onLogEvent(LogEvent logEvent) {
    // TODO: log events
  }

  @Override
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    if (Utils.isApplicationForeground() && eventSink != null) {
      eventSink.success(blockStateEvent.toMap());
      return;
    }

    // TODO: log events in background
  }

  @Override
  public void onForegroundServiceEvent(ForegroundServiceEvent foregroundServiceEvent) {
    // TODO
  }
}
