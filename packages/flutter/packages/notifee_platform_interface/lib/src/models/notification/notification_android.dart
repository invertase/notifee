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

/// The interface for Android specific options which are applied to a notification.
class NotificationAndroid {
  NotificationAndroid({
    required this.channelId,
    required this.smallIcon,
    this.sound,
    pressAction,
    importance,
    autoCancel,
    badgeIconType,
    chronometerDirection,
    groupAlertBehavior,
  })  : autoCancel = autoCancel ?? true,
        pressAction = pressAction ??
            NotificationPressAction(id: 'default', launchActivity: 'default'),
        badgeIconType = badgeIconType ?? AndroidBadgeIconType.large,
        chronometerDirection = chronometerDirection ?? 'up',
        groupAlertBehavior =
            groupAlertBehavior ?? AndroidGroupAlertBehavior.all,
        importance = importance ?? AndroidImportance.auto;

  /// Quick actions to a notification. Quick Actions enable users to interact with your application
  /// directly from the notification body, providing an overall greater user experience.
  List<Object>? actions;

  /// When set to `true` this notification will be shown as a foreground service.
  bool? asForegroundService;

  /// Setting this flag will make it so the notification is automatically canceled when the user
  /// presses it in the panel.
  ///
  /// By default when the user taps a notification it is automatically removed from the notification
  /// panel. Setting this to `false` will keep the notification in the panel.
  ///
  /// If `false`, the notification will persist in the notification panel after being pressed. It will
  /// remain there until the user removes it (e.g. swipes away) or is cancelled via
  /// [cancelNotification].
  ///
  /// Defaults to `true`
  bool? autoCancel;

  /// Overrides the current number of active notifications shown on the device.
  /// If no number is provided, the system displays the current number of active notifications.
  int? badgeCount;

  /// Sets the type of badge used when the notification is being displayed in badge mode.
  ///
  /// Defaults to `AndroidBadgeIconType.large`.
  /// Android API Level >= 26
  AndroidBadgeIconType? badgeIconType;

  /// Assigns the notification to a category. Use the one which best describes the notification.
  /// The category may be used by the device for ranking and filtering. It has no visual or behavioural
  /// impact.
  Object? category;

  /// Specifies the [Channel] which the notification will be delivered on.
  ///
  /// On Android 8.0 (API 26) the channel ID is required. Providing a invalid channel ID will throw
  /// an error.
  String channelId;

  /// Set an custom accent color for the notification. If not provided, the default notification
  /// system color will be used.
  ///
  /// The color can be a predefined system [AndroidColor] or [hexadecimal]
  String? color;

  /// When `asForegroundService` is `true`, the notification will use the provided `color` property
  /// to set a background color on the notification. This property has no effect when `asForegroundService`
  //// is `false`.
  ///
  /// This should only be used for high priority ongoing tasks like navigation, an ongoing call,
  /// or other similarly high-priority events for the user.
  ///
  ///  Defaults to `false`.
  bool? colorized;

  /// If `showChronometer` is `true`, the direction of the chronometer can be changed to count down instead of up.
  /// Has no effect if `showChronometer` is `false`.
  /// Defaults to `up`.
  String? chronometerDirection;

  /// For devices without notification channel support, this property sets the default behaviour
  /// for a notification.
  ///
  /// On API Level >= 26, this has no effect.
  Object? defaults;

  /// Set this notification to be part of a group of notifications sharing the same key. Grouped notifications may
  /// display in a cluster or stack on devices which support such rendering.
  ///
  /// On some devices, the system may automatically group notifications.
  String? groupId;

  /// Sets the group alert behavior for this notification.
  ///
  /// Use this method to mute this notification if alerts for this notification's group should be handled by a different notification.
  /// This is only applicable for notifications that belong to a `groupId`.
  /// This must be called on all notifications you want to mute.
  /// For example, if you want only the summary of your group to make noise, all
  /// children in the group should have the group alert behavior [AndroidGroupAlertBehavior.summary].
  AndroidGroupAlertBehavior? groupAlertBehavior;

  /// Whether this notification should be a group summary.
  ///
  /// If `true`, Set this notification to be the group summary for a group of notifications. Grouped notifications may display in
  /// a cluster or stack on devices which support such rendering. Requires a `groupId` key to be set.
  bool? groupSummary;

  /// The local user input history for this notification.
  ///
  /// Input history is shown on supported devices below the main notification body. History of the
  /// users input with the notification should be shown when receiving action input by updating
  /// the existing notification. It is recommended to clear the history when it is no longer
  /// relevant (e.g. someone has responded to the users input).
  List<String>? inputHistory;

  /// A local file path or a remote http to the picture to display.
  ///
  /// Sets a large icon on the notification.
  Object? largeIcon;

  /// Whether the large icon should be circular.
  /// If `true`, the large icon will be rounded in the shape of a circle.
  bool? circularLargeIcon;

  /// Sets the color and frequency of the light pattern. This only has effect on supported devices.
  ///
  /// The option takes an array containing a hexadecimal color value or predefined `AndroidColor`,
  /// along with the number of milliseconds to show the light, and the number of milliseconds to
  /// turn off the light. The light frequency pattern is repeated.
  Object? lights;

  /// Sets whether the notification will only appear on the local device.
  ///
  /// Users who have connected devices which support notifications (such as a smart watch) will
  /// receive an alert for the notification on that device. If set to `true`, the notification will
  /// only alert on the main device.
  bool? localOnly;

  /// Set whether this is an on-going notification.
  ///
  /// Setting this value to `true` changes the default behaviour of a notification:
  ///
  /// - Ongoing notifications are sorted above the regular notifications in the notification panel.
  /// - Ongoing notifications do not have an 'X' close button, and are not affected by the "Clear all" button.
  bool? ongoing;

  /// Notifications with the same `id` will only show a single instance at any one time on your device,
  /// however will still alert the user (for example, by making a sound).
  ///
  /// If this flag is set to `true`, notifications with the same `id` will only alert the user once whilst
  /// the notification is visible.
  ///
  /// This property is commonly used when frequently updating a notification (such as updating the progress bar).
  bool? onlyAlertOnce;

  /// By default notifications have no behaviour when a user presses them. The
  /// `pressAction` property allows you to set what happens when a user presses
  /// the notification.
  NotificationPressAction? pressAction;

  ///The `fullScreenAction` property allows you to show a custom UI
  /// in full screen when the notification is displayed.
  // TODO: class
  Object? fullScreenAction;

  /// Set a notification importance for devices without channel support.
  ///
  /// Devices using Android API Level < 26 have no channel support, meaning incoming notifications
  /// won't be assigned an importance level from the channel. If your application supports devices
  /// without channel support, set this property to directly assign an importance level to the incoming
  /// notification.
  ///
  /// Defaults to [AndroidImportance.auto].
  AndroidImportance? importance;

  /// A notification can show current progress of a task. The progress state can either be fixed or
  /// indeterminate (unknown).
  // TODO: class
  Object? progress;

  /// Sets whether the `timestamp` provided is shown in the notification.
  ///
  /// Setting this field is useful for notifications which are more informative with a timestamp,
  /// such as an E-Mail.
  ///
  /// If no `timestamp` is set, this field has no effect.
  bool? showTimestamp;

  /// The small icon to show in the heads-up notification.
  String smallIcon;

  /// An additional level parameter for when the icon is an instance of a Android `LevelListDrawable`.
  int? smallIconLevel;

  /// Set a sort key that orders this notification among other notifications from the same package.
  ///
  /// This can be useful if an external sort was already applied and an app would like to preserve
  /// this. Notifications will be sorted lexicographically using this value, although providing
  /// different priorities in addition to providing sort key may cause this value to be ignored.
  ///
  /// If a `groupId` has been set, the sort key can also be used to order members of a notification group.
  String? sortKey;

  /// Styled notifications provide users with more informative content and additional functionality.
  ///
  /// Android supports different styles, however only one can be used with a notification.
  // TOOO: class
  Object? style;

  /// Text that summarizes this notification for accessibility services.
  ///
  /// As of the Android L release, this text is no longer shown on screen,
  /// but it is still useful to accessibility services
  /// (where it serves as an audible announcement of the notification's appearance).
  ///
  /// Ticker text does not show in the notification.
  String? ticker;

  /// Sets the time in milliseconds at which the notification should be
  /// automatically cancelled once displayed, if it is not already cancelled.
  int? timeoutAfter;

  /// Shows a counting timer on the notification, useful for on-going notifications such as a phone call.
  ///
  /// If no `timestamp` is provided, a counter will display on the notification starting from 00:00. If a `timestamp` is
  /// provided, the number of hours/minutes/seconds since that have elapsed since that value will be shown instead.
  bool? showChronometer;

  /// Sets the vibration pattern the notification uses when displayed. Must be an even amount of numbers.
  List<num>? vibrationPattern;

  /// Sets the visibility for this notification. This may be used for apps which show user
  /// sensitive information (e.g. a banking app).
  ///
  /// Defaults to `AndroidVisibility.private`.
  AndroidVisibility? visibility;

  /// Sets a tag on the notification.
  ///
  /// Tags can be used to query groups notifications by the tag value. Setting a tag has no
  /// impact on the notification itself.
  String? tag;

  /// The timestamp in milliseconds for this notification. Notifications in the panel are sorted by this time.
  ///
  /// The timestamp can be used with other properties to change the behaviour of a notification:
  ///
  /// - Use with `showTimestamp` to show the timestamp to the users.
  /// - Use with `showChronometer` to create a on-going timer.
  DateTime? timestamp;

  /// Overrides the sound the notification is displayed with.
  ///
  /// The default value is to play no sound. To play the default system sound use 'default'.
  ///
  /// This setting has no behaviour on Android after API level version 26, instead you can set the
  /// sound on the notification channels.
  /// Android API Level < 26
  String? sound;

  factory NotificationAndroid.fromMap(Map<String, dynamic> map) =>
      NotificationAndroid(
        channelId: map['channelId'] as String,
        importance: AndroidImportance.values[map['importance']],
        smallIcon: map['smallIcon'] as String,
      )
        ..actions =
            (map['actions'] as List<dynamic>?)?.map((e) => e as Object).toList()
        ..asForegroundService = map['asForegroundService'] as bool?
        ..autoCancel = map['autoCancel'] as bool?
        ..badgeCount = map['badgeCount'] as int?
        ..badgeIconType = AndroidBadgeIconType.values[map['badgeIconType']]
        ..category = map['category']
        ..color = map['color'] as String?
        ..colorized = map['colorized'] as bool?
        ..chronometerDirection = map['chronometerDirection'] as String?
        ..defaults = map['defaults']
        ..groupId = map['groupId'] as String?
        ..groupAlertBehavior =
            AndroidGroupAlertBehavior.values[map['groupAlertBehavior']]
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
        ..pressAction = map['pressAction'] != null
            ? NotificationPressAction.fromMap(
                Map<String, dynamic>.from(map['pressAction']))
            : null
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
      'badgeIconType': badgeIconType?.index,
      'category': category,
      'channelId': channelId,
      'color': color,
      'colorized': colorized,
      'chronometerDirection': chronometerDirection,
      'defaults': defaults,
      'groupId': groupId,
      'groupAlertBehavior': groupAlertBehavior?.index,
      'groupSummary': groupSummary,
      'inputHistory': inputHistory?.asMap(),
      'largeIcon': largeIcon,
      'circularLargeIcon': circularLargeIcon,
      'lights': lights,
      'localOnly': localOnly,
      'ongoing': ongoing,
      'onlyAlertOnce': onlyAlertOnce,
      'pressAction': pressAction?.asMap(),
      'fullScreenAction': fullScreenAction,
      'importance': importance?.index,
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
