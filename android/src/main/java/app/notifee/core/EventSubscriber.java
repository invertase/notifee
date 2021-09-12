package app.notifee.core;

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

import android.content.Context;
import app.notifee.core.event.BlockStateEvent;
import app.notifee.core.event.ForegroundServiceEvent;
import app.notifee.core.event.LogEvent;
import app.notifee.core.event.NotificationEvent;
import app.notifee.core.interfaces.EventListener;
import java.util.HashSet;
import java.util.Set;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@KeepForSdk
public class EventSubscriber {
  private static final EventSubscriber mInstance = new EventSubscriber();
  private final Set<EventListener> mListeners = new HashSet<>();

  private EventSubscriber() {
    EventBus.register(this);
  }

  @KeepForSdk
  public static void register(EventListener listener) {
    mInstance.mListeners.add(listener);
  }

  @KeepForSdk
  public static void unregister(EventListener listener) {
    mInstance.mListeners.remove(listener);
  }

  @KeepForSdk
  public static Context getContext() {
    return ContextHolder.getApplicationContext();
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onNotificationEvent(NotificationEvent notificationEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onNotificationEvent(notificationEvent);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onLogEvent(LogEvent logEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onLogEvent(logEvent);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onBlockStateEvent(BlockStateEvent blockStateEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onBlockStateEvent(blockStateEvent);
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onForegroundServiceEvent(ForegroundServiceEvent foregroundServiceEvent) {
    for (EventListener eventListener : mListeners) {
      eventListener.onForegroundServiceEvent(foregroundServiceEvent);
    }
  }
}
