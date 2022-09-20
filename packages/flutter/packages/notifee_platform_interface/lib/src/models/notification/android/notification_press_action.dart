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

class NotificationPressAction {
  NotificationPressAction(
      {required this.id, this.launchActivity, this.mainComponent});

  factory NotificationPressAction.fromMap(Map<String, dynamic> map) =>
      NotificationPressAction(
        id: map['id'] as String,
        launchActivity: map['launchActivity'] as String?,
        mainComponent: map['mainComponent'] as String?,
      );

  String id;
  String? launchActivity;
  String? mainComponent;

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'id': id,
      'launchActivity': launchActivity,
      'mainComponent': mainComponent,
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
