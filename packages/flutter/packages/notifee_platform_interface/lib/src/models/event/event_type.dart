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

/// An enum representing an event type, defined on [Event]
enum EventType {
  /// Event type is sent when the user dismisses a notification. This is triggered via the user swiping
  /// the notification from the notification shade or performing "Clear all" notifications.
  ///
  /// This event is **not** sent when a notification is cancelled or times out.
  ///
  /// Android only
  dismissed,

  /// Event type is sent when a notification has been pressed by the user.
  ///
  ///On Android, notifications must include an `android.pressAction` property for this event to trigger.
  ///
  /// On iOS, this event is always sent when the user presses a notification.
  press,

  /// Event type is sent when a user presses a notification action.
  actionPress,

  /// Event type sent when a notification has been delivered to the device. For trigger notifications,
  /// this event is sent at the point when the trigger executes, not when a the trigger notification is created.
  ///
  /// It's important to note even though a notification has been delivered, it may not be shown to the
  /// user. For example, they may have notifications disabled on the device/channel/app.
  delivered,

  /// Event is sent when the user changes the notification blocked state for the entire application or
  /// when the user opens the application settings.
  ///
  /// Android API Level >= 28
  appBlocked,

  /// Event type is sent when the user changes the notification blocked state for a channel in the application.
  ///
  /// Android API Level >= 28
  channelBlocked,

  /// Event type is sent when the user changes the notification blocked state for a channel group in the application.
  ///
  /// Adroid API Level >= 28
  channelGroupBlocked,

  /// Event type is sent when a notification trigger is created.
  triggerNotificationCreated
}
