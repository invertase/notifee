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

class IOSNotificationCategory {
  IOSNotificationCategory({
    required this.id,
    this.summaryFormat,
    allowInCarPlay,
    allowAnnouncement,
    hiddenPreviewsShowTitle,
    hiddenPreviewsShowSubtitle,
    hiddenPreviewsBodyPlaceholder,
    this.intentIdentifiers,
    this.actions,
  })  : allowInCarPlay = allowInCarPlay ?? false,
        allowAnnouncement = allowAnnouncement ?? false,
        hiddenPreviewsShowTitle = hiddenPreviewsShowTitle ?? false,
        hiddenPreviewsShowSubtitle = hiddenPreviewsShowSubtitle ?? false;

  String id;

  String? summaryFormat;

  bool? allowInCarPlay;
  bool? allowAnnouncement;

  bool? hiddenPreviewsShowTitle;

  bool? hiddenPreviewsShowSubtitle;

  bool? hiddenPreviewsBodyPlaceholder;

  List<IOSIntentIdentifier>? intentIdentifiers;

  List<IOSNotificationCategoryAction>? actions;

  factory IOSNotificationCategory.fromMap(Map<String, dynamic> map) =>
      IOSNotificationCategory(
          id: map['id'] as String,
          summaryFormat: map['summaryFormat'] as String?,
          allowInCarPlay: map['allowInCarPlay'] == 0 ? false : true,
          allowAnnouncement: map['allowAnnouncement'] == 0 ? false : true,
          hiddenPreviewsShowTitle:
              map['hiddenPreviewsShowTitle'] == 0 ? false : true,
          hiddenPreviewsShowSubtitle:
              map['hiddenPreviewsShowSubtitle'] == 0 ? false : true,
          hiddenPreviewsBodyPlaceholder:
              map['hiddenPreviewsBodyPlaceholder'] == 0 ? false : true,
          intentIdentifiers: (map['intentIdentifiers'] as List<dynamic>?)
              ?.map((e) => IOSIntentIdentifier.values[e])
              .toList(),
          actions: (map['actions'] as List<dynamic>?)
              ?.map((e) => IOSNotificationCategoryAction.fromMap(e))
              .toList());

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'id': id,
      'summaryFormat': summaryFormat,
      'allowInCarPlay': allowInCarPlay,
      'allowAnnouncement': allowAnnouncement,
      'hiddenPreviewsShowTitle': hiddenPreviewsShowTitle,
      'hiddenPreviewsShowSubtitle': hiddenPreviewsShowSubtitle,
      'hiddenPreviewsBodyPlaceholder': hiddenPreviewsBodyPlaceholder,
      'intentIdentifiers': intentIdentifiers?.asMap(),
      'actions': actions?.map((item) => item.asMap()).toList(),
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
