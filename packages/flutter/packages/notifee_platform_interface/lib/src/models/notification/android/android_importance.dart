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
