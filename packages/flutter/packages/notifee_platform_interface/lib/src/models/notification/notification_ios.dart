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

/// The interface for iOS specific options which are applied to a notification.
class NotificationIOS {
  NotificationIOS(
      {this.attachments,
      this.badgeCount,
      this.foregroundPresentationOptions,
      this.categoryId,
      this.launchImageName,
      this.sound,
      this.critical,
      this.criticalVolume,
      this.threadId,
      this.summaryArgument,
      this.summaryArgumentCount,
      this.targetContentId}) {
    foregroundPresentationOptions ??= ForegroundPresentationOptions(
        badge: true, sound: true, banner: true, list: true);
  }

  /// Optional array of [IOSNotificationAttachment] interfaces
  /// Attachments allow audio, image, or video content to be displayed with the notification, enriching the user's experience.
  List<IOSNotificationAttachment>? attachments;

  /// The application badge count number. Set to null to indicate no change, or 0 to hide.
  int? badgeCount;

  /// Optional property to customise how notifications are presented when the app is in the foreground.
  /// By default, notifee will show iOS notifications in heads-up mode if your app is currently in the foreground.
  ForegroundPresentationOptions? foregroundPresentationOptions;

  /// The id of a registered [IOSCategory] (via the [setNotificationCategories] API) that will be used to determine the
  /// appropriate actions to display for the notification.
  String? categoryId;

  /// The launch image that will be used when the app is opened from this notification.
  String? launchImageName;

  /// The name of the sound file to be played.
  ///
  /// The sound must be in the Library/Sounds folder of the app's data container or
  /// the Library/Sounds folder of an app group data container.
  String? sound;

  /// If the notification is a critical alert set this property to true; critical alerts will bypass
  /// the mute switch and also bypass Do Not Disturb.
  /// iOS >= 12
  bool? critical;

  /// The optional audio volume of the critical sound; a float value between 0.0 and 1.0.
  /// This property is not used unless the `critical: true` option is also set.
  /// iOS >= 12
  int? criticalVolume;

  /// A unique id for the thread or conversation related to this notification.
  /// This will be used to visually group notifications together.
  String? threadId;

  /// The argument that is inserted in the IOSCategory.summaryFormat for this notification.
  /// iOS >= 12
  String? summaryArgument;

  /// A number that indicates how many items in the summary are being represented.
  ///
  /// For example if a messages app sends one notification for 3 new messages in a group chat,
  /// the summaryArgument could be the name of the group chat and the [summaryArgumentCount] should be 3.
  int? summaryArgumentCount;

  /// The identifier for the window to be opened when the user taps a notification.
  /// This value determines the window brought forward when the user taps this notification on iPadOS.
  /// iOS >= 13
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
                badge: true, sound: true, banner: true, list: true)
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
      'targetContentId': targetContentId,
      'attachments':
          attachments?.map((attachment) => attachment.asMap()).toList()
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
