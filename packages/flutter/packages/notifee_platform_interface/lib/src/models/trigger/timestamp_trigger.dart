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

class TimestampTrigger {
  TimestampTrigger(
      {required this.timestamp, this.repeatFrequency, this.allowWhileIdle}) {
    allowWhileIdle = allowWhileIdle ?? false;
    repeatFrequency = repeatFrequency ?? RepeatFrequency.none;
  }

  TriggerType type = TriggerType.timestamp;

  int timestamp;
  RepeatFrequency? repeatFrequency;
  bool? allowWhileIdle;

  factory TimestampTrigger.fromMap(Map map) => TimestampTrigger(
        timestamp: map['timestamp'] as int,
        repeatFrequency:
            $enumDecodeNullable(repeatFrequencyMap, map['repeatFrequency']),
        allowWhileIdle: map['allowWhileIdle'] as bool?,
      );

  Map<String, Object?> asMap() {
    return {
      'type': triggerTypeMap[type],
      'timestamp': timestamp,
      'allowWhileIdol': allowWhileIdle,
      'repeatFrequency': repeatFrequencyMap[repeatFrequency],
    };
  }
}
