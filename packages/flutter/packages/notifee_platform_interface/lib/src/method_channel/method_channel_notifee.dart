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

import 'dart:async';
import 'dart:ui';

import 'package:flutter/cupertino.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:notifee_platform_interface/notifee_platform_interface.dart';

enum NotificationType { all, displayed, trigger }

// This is the entrypoint for the background isolate. Since we can only enter
// an isolate once, we setup a MethodChannel to listen for method invocations
// from the native portion of the plugin. This allows for the plugin to perform
// any necessary processing in Dart (e.g., populating a custom object) before
// invoking the provided callback.
void _notifeeCallbackDispatcher() {
  // Initialize state necessary for MethodChannels.
  WidgetsFlutterBinding.ensureInitialized();

  const MethodChannel _channel = MethodChannel(
    'plugins.flutter.io/notifee/on_background',
  );

  // This is where we handle background events from the native portion of the plugin.
  _channel.setMethodCallHandler((MethodCall call) async {
    if (call.method == 'onBackgroundEvent') {
      final CallbackHandle handle =
          CallbackHandle.fromRawHandle(call.arguments['userCallbackHandle']);

      // PluginUtilities.getCallbackFromHandle performs a lookup based on the
      // callback handle and returns a tear-off of the original callback.
      final closure = PluginUtilities.getCallbackFromHandle(handle)!
          as Future<void> Function(Event);

      try {
        Map<String, dynamic> messageMap =
            Map<String, dynamic>.from(call.arguments['message']);
        final Event event = Event.fromMap(messageMap);
        await closure(event);
      } catch (e) {
        // ignore: avoid_print
        print(
            'Notifeee: An error occurred in your background messaging handler:');
        // ignore: avoid_print
        print(e);
      }
    } else {
      throw UnimplementedError('${call.method} has not been implemented');
    }
  });

  // Once we've finished initializing, let the native portion of the plugin
  // know that it can start scheduling alarms.
  _channel.invokeMethod<void>('Background#initialized');
}

class MethodChannelNotifee extends NotifeePlatform {
  static bool _initialized = false;
  static bool _bgHandlerInitialized = false;

  static MethodChannel channel = const MethodChannel(
    'plugins.invertase.io/notifee',
  );

  static const EventChannel _eventChannel =
      EventChannel('plugins.invertase.io/notifee/on_foreground');

  static final StreamController<Event> _onForegroundStreamListener =
      StreamController<Event>.broadcast();

  MethodChannelNotifee() : super() {
    if (_initialized) return;
    // Background Event
    // channel.setMethodCallHandler((MethodCall call) async {
    //   switch (call.method) {
    //     case 'onBackgroundEvent':
    //     // iOS only. Android calls via separate background channel.
    //       Map<String, dynamic> results =
    //       Map<String, dynamic>.from(call.arguments);
    //       return NotifeePlatform.onBackgroundEvent
    //           ?.call(Event.fromMap(results));
    //     default:
    //       throw UnimplementedError('${call.method} has not been implemented');
    //   }
    // });

    // Foreground Event
    _eventChannel
        .receiveBroadcastStream()
        .map((dynamic event) => Event.fromMap(event))
        .listen((data) {
      _handleOnForegroundListener(data);
    }, onError: (error, stackTrace) {
      _handleOnForegroundError(error, stackTrace);
    });

    _initialized = true;
  }

  void _handleOnForegroundError(Object error, [StackTrace? stackTrace]) {
    _onForegroundStreamListener.addError("", stackTrace);
  }

  void _handleOnForegroundListener(Event arguments) {
    _onForegroundStreamListener.add(arguments);
  }

  @override
  Stream<Event> get onForegroundEvent {
    return _onForegroundStreamListener.stream;
  }

  @override
  Future<void> registerBackgroundEventHandler(
      BackgroundEventHandler handler) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }

    if (!_bgHandlerInitialized) {
      _bgHandlerInitialized = true;
      final CallbackHandle bgHandle =
          PluginUtilities.getCallbackHandle(_notifeeCallbackDispatcher)!;
      final CallbackHandle userHandle =
          PluginUtilities.getCallbackHandle(handler)!;
      await channel.invokeMapMethod('startBackgroundIsolate', {
        'pluginCallbackHandle': bgHandle.toRawHandle(),
        'userCallbackHandle': userHandle.toRawHandle(),
      });
    }
  }

  @override
  Future<String> createIntervalTriggerNotification(
      {required NotifeeNotification notification,
      required IntervalTrigger trigger}) async {
    await channel.invokeMethod<String>(
        'createTriggerNotification', <String, dynamic>{
      'notification': notification.asMap(),
      'trigger': trigger.asMap()
    });
    return notification.id!;
  }

  @override
  Future<String> createTimestampTriggerNotification(
      {required NotifeeNotification notification,
      required TimestampTrigger trigger}) async {
    await channel.invokeMethod<String>(
        'createTriggerNotification', <String, dynamic>{
      'notification': notification.asMap(),
      'trigger': trigger.asMap()
    });
    return notification.id!;
  }

  @override
  Future<String> displayNotification(NotifeeNotification notification) async {
    await channel.invokeMethod('displayNotification', notification.asMap());

    return notification.id!;
  }

  @override
  Future<void> cancelAllNotifications(
      {List<String>? notificationIds,
      String? tag,
      NotificationType? type}) async {
    if (notificationIds != null && notificationIds.isNotEmpty) {
      await channel.invokeMethod<List<String>>(
          'cancelAllNotificationsWithIds', <String, dynamic>{
        'notificationIds': notificationIds,
        'type': type ?? NotificationType.all
      });
    }
    await channel.invokeMethod('cancelAllNotifications', <String, dynamic>{
      'type': type != null ? type.index : NotificationType.all.index
    });
  }

  @override
  Future<void> cancelDisplayedNotifications(
      {List<String>? notificationIds, String? tag}) async {
    await cancelAllNotifications(
        notificationIds: notificationIds,
        tag: tag,
        type: NotificationType.displayed);
  }

  @override
  Future<void> cancelNotification(
      {required String notificationId, String? tag}) async {
    await cancelAllNotifications(
        notificationIds: [notificationId],
        tag: tag,
        type: NotificationType.displayed);
  }

  @override
  Future<void> cancelDisplayedNotification(
      {required String notificationId, String? tag}) async {
    await cancelAllNotifications(
        notificationIds: [notificationId],
        tag: tag,
        type: NotificationType.displayed);
  }

  @override
  Future<void> cancelTriggerNotification(
      {required String notificationId}) async {
    await cancelAllNotifications(
        notificationIds: [notificationId], type: NotificationType.trigger);
  }

  @override
  Future<void> cancelTriggerNotifications(
      {List<String>? notificationIds, String? tag}) async {
    await cancelAllNotifications(
        notificationIds: notificationIds,
        tag: tag,
        type: NotificationType.trigger);
  }

  @override
  Future<NotificationSettings> requestPermission(
      [IOSNotificationPermissions? permissions]) async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return getNotificationSettings();
    }

    Map<String, dynamic>? results =
        await channel.invokeMapMethod<String, dynamic>(
            'requestPermission', permissions?.asMap() ?? {});
    return NotificationSettings.fromMap(results!);
  }

  @override
  Future<NotificationSettings> getNotificationSettings() async {
    Map<String, dynamic>? results = await channel
        .invokeMapMethod<String, dynamic>('getNotificationSettings');
    return NotificationSettings.fromMap(results!);
  }

  @override
  Future<List<Object>> getDisplayedNotifications() async {
    List<Object>? results =
        await channel.invokeListMethod('getDisplayedNotifications');
    return results!;
  }

  @override
  Future<InitialNotification?> getInitialNotification() async {
    Map<String, dynamic>? initialNotification = await channel
        .invokeMapMethod<String, dynamic>('getInitialNotification');
    if (initialNotification == null) {
      return null;
    }

    return InitialNotification.fromMap(initialNotification);
  }

  @override
  Future<List<String>> getTriggerNotificationIds() async {
    List<String>? data =
        await channel.invokeListMethod<String>('getTriggerNotificationIds');
    return data!;
  }

  @override
  Future<List<TriggerNotification>> getTriggerNotifications() async {
    final List<Map>? result =
        await channel.invokeListMethod<Map>('getTriggerNotifications');
    if (result == null) {
      return List.empty();
    }

    return result
        .map((e) => TriggerNotification.fromMap(Map<String, dynamic>.from(e)))
        .toList();
  }

  // iOS

  @override
  Future<int> getBadgeCount() async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return 0;
    }
    int? badgeCount = await channel.invokeMethod<int>('getBadgeCount');
    return badgeCount!;
  }

  @override
  Future<void> incrementBadgeCount([int? incrementBy = 1]) async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return;
    }
    await channel.invokeMethod('incrementBadgeCount', incrementBy);
  }

  @override
  Future<void> decrementBadgeCount([int? decrementBy = 1]) async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return;
    }
    await channel.invokeMethod('decrementBadgeCount', decrementBy);
  }

  @override
  Future<void> setBadgeCount(int count) async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return;
    }
    await channel.invokeMethod('setBadgeCount', count);
  }

  @override
  Future<void> setNotificationCategories(
      List<IOSNotificationCategory> categories) async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return;
    }
    await channel.invokeMethod('setNotificationCategories', categories);
  }

  @override
  Future<List<IOSNotificationCategory>> getNotificationCategories() async {
    if (defaultTargetPlatform != TargetPlatform.iOS) {
      return [];
    }

    List<Map<String, dynamic>>? categories = await channel
        .invokeListMethod<Map<String, dynamic>>('getNotificationCategories');

    return (categories?.map((e) => IOSNotificationCategory.fromMap(e)).toList())
        as List<IOSNotificationCategory>;
  }

  // Android

  @override
  Future<Object> getPowerManagerInfo() async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return {'manufacturer': 'apple', 'activity': null};
    }
    Object? powerManagerInfo =
        await channel.invokeMethod<Object>('getPowerManagerInfo');
    return powerManagerInfo!;
  }

  @override
  Future<String> createChannel(Channel androidChannel) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return '';
    }

    await channel.invokeMethod('createChannel', androidChannel.asMap());
    return androidChannel.id;
  }

  @override
  Future<String> createChannelGroup(ChannelGroup channelGroup) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return '';
    }

    await channel.invokeMethod('createChannelGroup', channelGroup.asMap());
    return channelGroup.id;
  }

  @override
  Future<void> createChannelGroups(List<ChannelGroup> channelGroups) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }

    await channel.invokeMethod('createChannelGroups', channelGroups);
  }

  @override
  Future<void> createChannels(List<Channel> channels) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('createChannels', channels);
  }

  @override
  Future<void> deleteChannel(String channelId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('deleteChannel', channelId);
  }

  @override
  Future<void> deleteChannelGroup(String channelGroupId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('deleteChannelGroup', channelGroupId);
  }

  @override
  Future<Channel?> getChannel(String channelId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return null;
    }
    Map<String, dynamic>? result =
        await channel.invokeMapMethod<String, dynamic>('getChannel', channelId);
    return Channel.fromMap(result!);
  }

  @override
  Future<ChannelGroup?> getChannelGroup(String channelGroupId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return null;
    }
    Map<String, dynamic>? result = await channel
        .invokeMapMethod<String, dynamic>('getChannelGroup', channelGroupId);
    return ChannelGroup.fromMap(result!);
  }

  @override
  Future<List<ChannelGroup>> getChannelGroups() async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return [];
    }
    List<Map>? result = await channel.invokeListMethod<Map>('getChannelGroups');
    return (result
        ?.map((e) => ChannelGroup.fromMap(e as Map<String, dynamic>))
        .toList()) as List<ChannelGroup>;
  }

  @override
  Future<List<Channel>> getChannels() async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return [];
    }

    List<Map<String, dynamic>>? result =
        await channel.invokeListMethod<Map<String, dynamic>>('getChannels');
    return (result?.map((e) => Channel.fromMap(e)).toList()) as List<Channel>;
  }

  @override
  Future<bool> isChannelCreated(String channelId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return false;
    }
    bool? isChannelCreated =
        await channel.invokeMethod<bool>('isChannelCreated', channelId);
    return isChannelCreated!;
  }

  @override
  Future<bool> isChannelBlocked(String channelId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return false;
    }
    bool? isChannelBlocked =
        await channel.invokeMethod<bool>('isChannelBlocked', channelId);
    return isChannelBlocked!;
  }

  @override
  Future<void> openBatteryOptimizationSettings() async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('openBatteryOptimizationSettings');
  }

  @override
  Future<void> openPowerManagerSettings() async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('openPowerManagerSettings');
  }

  @override
  Future<void> hideNotificationDrawer() async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('hideNotificationDrawer');
  }

  @override
  Future<void> openNotificationSettings(String? channelId) async {
    if (defaultTargetPlatform != TargetPlatform.android) {
      return;
    }
    await channel.invokeMethod('openNotificationSettings', channelId);
  }

  @override
  Future<bool> isBatteryOptimizationEnabled() async {
    bool? isBatteryOptimizationEnabled =
        await channel.invokeMethod<bool>('isBatteryOptimizationEnabled');
    return isBatteryOptimizationEnabled!;
  }
}
