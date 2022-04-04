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
