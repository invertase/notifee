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

import 'package:notifee_platform_interface/notifee_platform_interface.dart';

import '../../utils/generate_id.dart';

class NotifeeNotification {
  NotifeeNotification(
      {this.id,
      this.title,
      this.subtitle,
      this.body,
      this.data,
      this.android,
      this.ios}) {
    id = GenerateId.generateId();
  }

  String? id;
  String? title;
  String? subtitle;
  String? body;
  Map<String, String>? data;
  NotificationAndroid? android;
  NotificationIOS? ios;

  factory NotifeeNotification.fromMap(Map<String, dynamic> map) =>
      NotifeeNotification(
        id: map['id'] as String?,
        title: map['title'] as String?,
        subtitle: map['subtitle'] as String?,
        body: map['body'] as String?,
        data: (map['data'] as Map?)?.map(
          (k, e) => MapEntry(k as String, e as String),
        ),
        android: map['android'] == null
            ? null
            : NotificationAndroid.fromMap(
                Map<String, dynamic>.from(map['android'] as Map)),
        ios: map['ios'] == null
            ? null
            : NotificationIOS.fromMap(
                Map<String, dynamic>.from(map['ios'] as Map)),
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'id': id,
      'title': title,
      'subtitle': subtitle,
      'body': body,
      'data': data,
      'android': android?.asMap(),
      'ios': ios?.asMap(),
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
