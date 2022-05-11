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
  TimestampTrigger({required this.timestamp, repeatFrequency, allowWhileIdle})
      : allowWhileIdle = allowWhileIdle ?? false,
        repeatFrequency = repeatFrequency ?? RepeatFrequency.none;

  TriggerType type = TriggerType.timestamp;

  /// The timestamp when the notification should first be shown, in milliseconds since 1970.
  int timestamp;

  /// The frequency at which the trigger repeats.
  /// If unset, the notification will only be displayed once.
  ///
  /// For example:
  ///  if set to [RepeatFrequency.hourly], the notification will repeat every hour from the timestamp specified.
  ///  if set to [RepeatFrequency.daily], the notification will repeat every day from the timestamp specified.
  ///  if set to [RepeatFrequency.weekly], the notification will repeat every week from the timestamp specified.
  RepeatFrequency? repeatFrequency;

  /// Sets whether your trigger notification should be displayed even when the system is in low-power idle modes.
  ///
  /// Defaults to `false`.
  /// Android only
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
      'allowWhileIdle': allowWhileIdle,
      'repeatFrequency': repeatFrequencyMap[repeatFrequency],
    };
  }
}

/// An interface representing the different frequencies which can be used with [TimestampTrigger].
enum RepeatFrequency { none, hourly, daily, weekly }

const repeatFrequencyMap = {
  RepeatFrequency.none: -1,
  RepeatFrequency.hourly: 0,
  RepeatFrequency.daily: 1,
  RepeatFrequency.weekly: 2,
};
