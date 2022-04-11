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
import 'package:notifee_platform_interface/src/models/trigger/trigger_type.dart';

/// Interface for building a trigger that repeats at a specified interval.
class IntervalTrigger {
  IntervalTrigger({required this.interval, this.timeUnit}) {
    timeUnit = TimeUnit.seconds;
  }

  /// Constant enum value used to identify the trigger type.
  TriggerType type = TriggerType.interval;

  /// How frequently the notification should be repeated.
  ///
  /// For example, if set to 30, the notification will be displayed every 30 minutes.
  /// Must be set to a minimum of 15 minutes.
  int interval;

  /// The unit of time that the `interval` is measured in
  ///
  /// For example, if set to `TimeUnit.days` and repeat interval is set to 3, the notification will repeat every 3 days
  ///  Defaults to `TimeUnit.seconds`
  TimeUnit? timeUnit;

  factory IntervalTrigger.fromMap(Map<String, dynamic> map) => IntervalTrigger(
      interval: map['interval'] as int,
      timeUnit: $enumDecodeNullable(timeUnitMap, map['timeUnit']));

  Map<String, Object?> asMap() {
    return {
      'type': triggerTypeMap[type],
      'interval': interval,
      'timeUnit': timeUnitMap[type],
    };
  }
}

/// An interface representing the different units of time which can be used with [IntervalTrigger].
enum TimeUnit { seconds, minutes, hours, days }

const timeUnitMap = {
  TimeUnit.seconds: 'SECONDS',
  TimeUnit.minutes: 'MINUTES',
  TimeUnit.hours: 'HOURS',
  TimeUnit.days: 'DAYS',
};
