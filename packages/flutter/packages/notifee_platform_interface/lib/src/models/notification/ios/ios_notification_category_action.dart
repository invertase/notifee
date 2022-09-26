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

class IOSNotificationCategoryAction {
  IOSNotificationCategoryAction({
    required this.id,
    this.title,
    destructive,
    foreground,
    authenticationRequired,
  })  : destructive = destructive ?? false,
        foreground = foreground ?? false,
        authenticationRequired = authenticationRequired ?? false;

  String id;

  String? title;

  bool? destructive;

  bool? foreground;

  bool? authenticationRequired;

  factory IOSNotificationCategoryAction.fromMap(Map<String, dynamic> map) =>
      IOSNotificationCategoryAction(
        id: map['id'] as String,
        title: map['title'] as String?,
        destructive: map['destructive'] as bool?,
        foreground: map['foreground'] as bool?,
        authenticationRequired: map['authenticationRequired'] as bool?,
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'id': id,
      'title': title,
      'destructive': destructive,
      'foreground': foreground,
      'authenticationRequired': authenticationRequired,
    };

    return map;
  }
}
