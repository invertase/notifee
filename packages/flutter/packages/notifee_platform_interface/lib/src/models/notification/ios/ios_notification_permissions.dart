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

class IOSNotificationPermissions {
  IOSNotificationPermissions(
      {alert, badge, sound, carPlay, provisional, announcement, criticalAlert})
      : alert = alert ?? true,
        badge = badge ?? true,
        sound = sound ?? true,
        carPlay = carPlay ?? true,
        provisional = provisional ?? false,
        announcement = announcement ?? false,
        criticalAlert = criticalAlert ?? false;

  bool? alert;
  bool? criticalAlert;
  bool? badge;
  bool? sound;
  bool? carPlay;
  bool? provisional;
  bool? announcement;

  factory IOSNotificationPermissions.fromMap(Map<String, dynamic> map) =>
      IOSNotificationPermissions(
        alert: map['alert'],
        badge: map['badge'],
        sound: map['sound'],
        carPlay: map['carPlay'],
        provisional: map['provisional'],
        announcement: map['announcement'],
        criticalAlert: map['criticalAlert'],
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'alert': alert,
      'criticalAlert': criticalAlert,
      'badge': badge,
      'sound': sound,
      'carPlay': carPlay,
      'provisional': provisional,
      'announcement': announcement,
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
