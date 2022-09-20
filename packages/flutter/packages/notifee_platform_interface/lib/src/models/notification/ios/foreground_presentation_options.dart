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

class ForegroundPresentationOptions {
  ForegroundPresentationOptions(
      {bool? alert, bool? badge, bool? sound, bool? banner, bool? list})
      : alert = alert ?? false,
        badge = badge ?? false,
        sound = sound ?? false,
        banner = banner ?? false,
        list = list ?? false;

  bool alert;
  bool badge;
  bool sound;
  bool banner;
  bool list;

  factory ForegroundPresentationOptions.fromMap(Map<String, dynamic> json) =>
      ForegroundPresentationOptions(
        alert: json['alert'] as bool?,
        badge: json['badge'] as bool?,
        sound: json['sound'] as bool?,
        banner: json['banner'] as bool?,
        list: json['list'] as bool?,
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'alert': alert,
      'badge': badge,
      'sound': sound,
      'banner': banner,
      'list': list,
    };

    return map;
  }
}
