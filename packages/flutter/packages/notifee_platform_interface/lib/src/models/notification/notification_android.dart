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

class NotificationAndroid {
  NotificationAndroid(
      {required this.channelId, this.importance, required this.smallIcon});

  List<Object>? actions;
  bool? asForegroundService;
  bool? autoCancel;
  int? badgeCount;
  Object? badgeIconType;
  Object? category;
  String channelId;
  String? color;
  bool? colorized;
  String? chronometerDirection;
  Object? defaults;
  String? groupId;
  Object? groupAlertBehavior;
  bool? groupSummary;
  List<String>? inputHistory;
  Object? largeIcon;
  bool? circularLargeIcon;
  Object? lights;
  bool? localOnly;
  bool? ongoing;
  bool? onlyAlertOnce;
  Object? pressAction;
  Object? fullScreenAction;
  Object? importance;
  Object? progress;
  bool? showTimestamp;
  String smallIcon;
  int? smallIconLevel;
  String? sortKey;
  Object? style;
  String? ticker;
  int? timeoutAfter;
  bool? showChronometer;
  List<num>? vibrationPattern;
  Object? visibility;
  String? tag;
  DateTime? timestamp;
  String? sound;

  factory NotificationAndroid.fromMap(Map<String, dynamic> map) =>
      NotificationAndroid(
        channelId: map['channelId'] as String,
        importance: map['importance'],
        smallIcon: map['smallIcon'] as String,
      )
        ..actions =
            (map['actions'] as List<dynamic>?)?.map((e) => e as Object).toList()
        ..asForegroundService = map['asForegroundService'] as bool?
        ..autoCancel = map['autoCancel'] as bool?
        ..badgeCount = map['badgeCount'] as int?
        ..badgeIconType = map['badgeIconType']
        ..category = map['category']
        ..color = map['color'] as String?
        ..colorized = map['colorized'] as bool?
        ..chronometerDirection = map['chronometerDirection'] as String?
        ..defaults = map['defaults']
        ..groupId = map['groupId'] as String?
        ..groupAlertBehavior = map['groupAlertBehavior']
        ..groupSummary = map['groupSummary'] as bool?
        ..inputHistory = (map['inputHistory'] as List<dynamic>?)
            ?.map((e) => e as String)
            .toList()
        ..largeIcon = map['largeIcon']
        ..circularLargeIcon = map['circularLargeIcon'] as bool?
        ..lights = map['lights']
        ..localOnly = map['localOnly'] as bool?
        ..ongoing = map['ongoing'] as bool?
        ..onlyAlertOnce = map['onlyAlertOnce'] as bool?
        ..pressAction = map['pressAction']
        ..fullScreenAction = map['fullScreenAction']
        ..progress = map['progress']
        ..showTimestamp = map['showTimestamp'] as bool?
        ..smallIconLevel = map['smallIconLevel'] as int?
        ..sortKey = map['sortKey'] as String?
        ..style = map['style']
        ..ticker = map['ticker'] as String?
        ..timeoutAfter = map['timeoutAfter'] as int?
        ..showChronometer = map['showChronometer'] as bool?
        ..vibrationPattern = (map['vibrationPattern'] as List<dynamic>?)
            ?.map((e) => e as num)
            .toList()
        ..visibility = map['visibility']
        ..tag = map['tag'] as String?
        ..timestamp = map['timestamp'] == null
            ? null
            : DateTime.parse(map['timestamp'] as String)
        ..sound = map['sound'] as String?;

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'actions': actions?.asMap(),
      'asForegroundService': asForegroundService,
      'autoCancel': autoCancel,
      'badgeCount': badgeCount,
      'badgeIconType': badgeIconType,
      'category': category,
      'channelId': channelId,
      'color': color,
      'colorized': colorized,
      'chronometerDirection': chronometerDirection,
      'defaults': defaults,
      'groupId': groupId,
      'groupAlertBehavior': groupAlertBehavior,
      'groupSummary': groupSummary,
      'inputHistory': inputHistory?.asMap(),
      'largeIcon': largeIcon,
      'circularLargeIcon': circularLargeIcon,
      'lights': lights,
      'localOnly': localOnly,
      'ongoing': ongoing,
      'onlyAlertOnce': onlyAlertOnce,
      'pressAction': pressAction,
      'fullScreenAction': fullScreenAction,
      'importance': importance,
      'progress': progress,
      'showTimestamp': showTimestamp,
      'smallIcon': smallIcon,
      'smallIconLevel': smallIconLevel,
      'sortKey': sortKey,
      'style': style,
      'ticker': ticker,
      'timeoutAfter': timeoutAfter,
      'showChronometer': showChronometer,
      'vibrationPattern': vibrationPattern?.asMap(),
      'visibility': visibility,
      'tag': tag,
      'timestamp': timestamp, // date
      'sound': sound,
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
