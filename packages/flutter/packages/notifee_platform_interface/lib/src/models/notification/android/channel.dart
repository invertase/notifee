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

import 'package:json_annotation/json_annotation.dart';
import 'package:notifee_platform_interface/notifee_platform_interface.dart';

class Channel {
  Channel(
      {required this.id,
      required this.name,
      this.badge,
      this.bypassDnd,
      this.lights,
      this.vibration,
      this.visibility,
      this.importance,
      this.vibrationPattern,
      this.sound}) {
    bypassDnd = bypassDnd ?? false;
    lights = lights ?? true;
    vibration = vibration ?? true;
    badge = badge ?? true;
    importance = importance ?? AndroidImportance.auto;
    visibility = visibility ?? AndroidVisibility.private;
  }

  factory Channel.fromMap(Map<String, dynamic> map) => Channel(
      id: map['id'] as String,
      name: map['name'] as String?,
      badge: map['badge'] as bool?,
      bypassDnd: map['bypassDnd'] as bool?,
      lights: map['lights'] as bool?,
      vibration: map['vibration'] as bool?,
      visibility: $enumDecodeNullable(androidVisibilityMap, map['visibility']),
      importance: $enumDecodeNullable(androidImportanceMap, map['importance']),
      vibrationPattern: (map['vibrationPattern'] as List<dynamic>?)
          ?.map((e) => e as int)
          .toList(),
      sound: map['sound'] as String?);

  String id;
  String? name;
  bool? bypassDnd;
  bool? lights;
  bool? vibration;
  bool? badge;
  AndroidImportance? importance;
  AndroidVisibility? visibility;
  String? sound;
  List<int>? vibrationPattern;

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'id': id,
      'name': name,
      'bypassDnd': bypassDnd,
      'lights': lights,
      'vibration': vibration,
      'badge': badge,
      'importance': androidImportanceMap[importance],
      'visibility': androidVisibilityMap[visibility],
      'sound': sound,
      'vibrationPattern': vibrationPattern
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
