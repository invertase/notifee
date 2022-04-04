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

import 'ios/foreground_presentation_options.dart';
import 'ios/ios_notification_attachment.dart';

class NotificationIOS {
  // todo params
  NotificationIOS(
      {this.attachments,
      this.badgeCount,
      foregroundPresentationOptions,
      this.categoryId,
      this.launchImageName,
      this.sound,
      this.critical,
      this.criticalVolume,
      this.threadId,
      this.summaryArgument,
      this.summaryArgumentCount,
      this.targetContentId})
      : foregroundPresentationOptions = foregroundPresentationOptions ??
            ForegroundPresentationOptions(
                alert: true, badge: true, sound: true);

  List<IOSNotificationAttachment>? attachments;
  int? badgeCount;
  ForegroundPresentationOptions? foregroundPresentationOptions;
  String? categoryId;
  String? launchImageName;
  String? sound;
  bool? critical;
  int? criticalVolume;
  String? threadId;
  String? summaryArgument;
  int? summaryArgumentCount;
  String? targetContentId;

  factory NotificationIOS.fromMap(Map<String, dynamic> map) => NotificationIOS(
        attachments: (map['attachments'] as List<dynamic>?)
            ?.map((e) => IOSNotificationAttachment.fromMap(
                Map<String, dynamic>.from(e as Map)))
            .toList(),
        badgeCount: map['badgeCount'] as int?,
        foregroundPresentationOptions: map['foregroundPresentationOptions'] ==
                null
            ? ForegroundPresentationOptions(
                alert: true, badge: true, sound: true)
            : ForegroundPresentationOptions.fromMap(Map<String, dynamic>.from(
                map['foregroundPresentationOptions'] as Map)),
        categoryId: map['categoryId'] as String?,
        launchImageName: map['launchImageName'] as String?,
        sound: map['sound'] as String?,
        critical: map['critical'] as bool?,
        criticalVolume: map['criticalVolume'] as int?,
        threadId: map['threadId'] as String?,
        summaryArgument: map['summaryArgument'] as String?,
        summaryArgumentCount: map['summaryArgumentCount'] as int?,
        targetContentId: map['targetContentId'] as String?,
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'foregroundPresentationOptions': foregroundPresentationOptions?.asMap(),
      'badgeCount': badgeCount,
      'categoryId': categoryId,
      'launchImageName': launchImageName,
      'sound': sound,
      'critical': critical,
      'criticalVolume': criticalVolume,
      'threadId': threadId,
      'summaryArgument': summaryArgument,
      'summaryArgumentCount': summaryArgumentCount,
      'targetContentId': targetContentId
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
