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

import 'native_android_channel.dart';
import 'native_android_channel_group.dart';

class EventDetail {
  EventDetail(
      {this.notification,
      this.pressAction,
      this.input,
      this.channel,
      this.channelGroup,
      this.blocked});

  NotifeeNotification? notification;

  NotificationPressAction? pressAction;

  String? input;

  NativeAndroidChannel? channel;

  NativeAndroidChannelGroup? channelGroup;

  bool? blocked;

  factory EventDetail.fromMap(Map<String, dynamic> map) => EventDetail(
        notification: map['notification'] == null
            ? null
            : NotifeeNotification.fromMap(
                Map<String, dynamic>.from(map['notification'] as Map)),
        pressAction: map['pressAction'] == null
            ? null
            : NotificationPressAction.fromMap(
                Map<String, dynamic>.from(map['pressAction'] as Map)),
        input: map['input'] as String?,
        channel: map['channel'] == null
            ? null
            : NativeAndroidChannel.fromMap(
                Map<String, dynamic>.from(map['channel'] as Map)),
        channelGroup: map['channelGroup'] == null
            ? null
            : NativeAndroidChannelGroup.fromMap(
                Map<String, dynamic>.from(map['channelGroup'] as Map)),
        blocked: map['blocked'] as bool?,
      );
}
