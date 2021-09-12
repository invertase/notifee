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

public class EventBus {
  private static final EventBus instance = new EventBus();
  private org.greenrobot.eventbus.EventBus eventBus;

  private EventBus() {
    eventBus = org.greenrobot.eventbus.EventBus.builder().build();
  }

  public static EventBus getInstance() {
    return instance;
  }

  public static void register(Object subscriber) {
    getInstance().getDefault().register(subscriber);
  }

  public static void unregister(Object subscriber) {
    getInstance().getDefault().unregister(subscriber);
  }

  public static <T> T getStickyEvent(Class<T> eventType) {
    return getInstance().getDefault().getStickyEvent(eventType);
  }

  public static <T> T removeStickEvent(Class<T> eventType) {
    return getInstance().getDefault().removeStickyEvent(eventType);
  }

  public static void post(Object event) {
    getInstance().getDefault().post(event);
  }

  static void postSticky(Object event) {
    getInstance().getDefault().postSticky(event);
  }

  static <T> T removeStickyEvent(Class<T> eventType) {
    return getInstance().getDefault().removeStickyEvent(eventType);
  }

  private org.greenrobot.eventbus.EventBus getDefault() {
    return eventBus;
  }
}
