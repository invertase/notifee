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

class InitialNotification {
  InitialNotification(
      {required this.notification, required this.pressAction, this.input});

  NotifeeNotification notification;
  Object pressAction;
  String? input;

  factory InitialNotification.fromMap(Map<String, dynamic> map) =>
      InitialNotification(
        notification: NotifeeNotification.fromMap(
            Map<String, dynamic>.from(map['notification'] as Map)),
        pressAction: map['pressAction'] as Object,
        input: map['input'] as String?,
      );
}
