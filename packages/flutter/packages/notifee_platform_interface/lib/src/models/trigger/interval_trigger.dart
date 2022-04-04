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
import 'package:notifee_platform_interface/src/models/trigger/time_unit.dart';
import 'package:notifee_platform_interface/src/models/trigger/trigger_type.dart';

class IntervalTrigger {
  IntervalTrigger({required this.interval, this.timeUnit}) {
    timeUnit = TimeUnit.seconds;
  }

  TriggerType type = TriggerType.interval;

  int interval;

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
