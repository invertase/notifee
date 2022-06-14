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

/// Enum used to define how a notification badge is displayed in badge mode.
enum AndroidBadgeIconType {
  /// No badge is displayed, will always show as a number.
  none,

  /// Shows the badge as the notifications `smallIcon`.
  small,

  /// Shows the badge as the notifications `largeIcon` (if available).
  /// This is the default value used by a notification if not provided.
  large,
}

/// Enum used to describe how a notification alerts the user when it apart of a group.
enum AndroidGroupAlertBehavior {
  /// All notifications will alert.
  all,

  /// Only the summary notification will alert the user when displayed. The children of the group will not alert.
  summary,

  /// Children of a group will alert the user. The summary notification will not alert when displayed.
  children,
}

/// An enum representing a notification or channel importance on Android.
enum AndroidImportance {
  /// When a notification is received, the device smallIcon will appear in the notification shade.
  /// When the user pulls down the notification shade, the content of the notification will be shown
  /// in it's expanded state.
  auto,

  /// The highest priority level a notification can be set to.
  high,

  /// The application small icon will show in the device status bar, however the notification will
  /// not alert the user (no sound or vibration). The notification will show in it's expanded state
  /// when the notification shade is pulled down.
  low,

  /// The application small icon will not show up in the status bar, or alert the user. The notification
  /// will be in a collapsed state in the notification shade and placed at the bottom of the list.
  min,
  none
}

const androidImportanceMap = {
  AndroidImportance.auto: 3,
  AndroidImportance.high: 4,
  AndroidImportance.low: 2,
  AndroidImportance.min: 1,
  AndroidImportance.none: 0,
};

/// An enum representing the visibility level of a notification on Android.
///
/// Default value is `AndroidVisibility.PRIVATE`.
enum AndroidVisibility {
  /// Show this notification on all lock-screens, but conceal sensitive or private information on secure lock-screens.
  private,

  /// Show this notification in its entirety on all lock-screens.
  public,

  /// Do not reveal any part of this notification on a secure lock-screen.
  secret
}

const androidVisibilityMap = {
  AndroidVisibility.private: 0,
  AndroidVisibility.public: 1,
  AndroidVisibility.secret: -1,
};
