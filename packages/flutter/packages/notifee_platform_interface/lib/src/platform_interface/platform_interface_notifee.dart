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

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import '../../notifee_platform_interface.dart';
import '../method_channel/method_channel_notifee.dart';

class NotifeePlatform extends PlatformInterface {
  NotifeePlatform() : super(token: _token);

  static final Object _token = Object();

  static NotifeePlatform? _instance;

  /// Sets the [NotifeePlatform.instance]
  static set instance(NotifeePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  /// The current default [FirebaseFirestorePlatform] instance.
  ///
  /// It will always default to [MethodChannelFirebaseFirestore]
  /// if no other implementation was provided.
  static NotifeePlatform get instance {
    return _instance ??= MethodChannelNotifee();
  }

  static BackgroundEventHandler? _onBackgroundEventHandler;

  /// Set a message handler function which is called when the app is in the
  /// background or terminated.
  ///
  /// This provided handler must be a top-level function and cannot be
  /// anonymous otherwise an [ArgumentError] will be thrown.
  static BackgroundEventHandler? get onBackgroundEvent {
    return _onBackgroundEventHandler;
  }

  /// Allows the background message handler to be created and calls the
  /// instance delegate [registerBackgrounEventHandler] to perform any
  /// platform specific registration logic.
  static set onBackgroundEvent(BackgroundEventHandler? handler) {
    _onBackgroundEventHandler = handler;

    if (handler != null) {
      instance.registerBackgroundEventHandler(handler);
    }
  }

  Future<void> registerBackgroundEventHandler(
      BackgroundEventHandler handler) async {
    throw UnimplementedError(
        'registerBackgroundEventHandler() is not implemented');
  }

  Future<void> cancelAllNotifications(
      {List<String>? notificationIds, String? tag}) async {
    throw UnimplementedError('cancelAllNotifications() is not implemented');
  }

  Future<void> cancelDisplayedNotification(
      {required String notificationId, String? tag}) async {
    throw UnimplementedError(
        'cancelDisplayedNotification() is not implemented');
  }

  Future<void> cancelDisplayedNotifications(
      {List<String>? notificationIds, String? tag}) async {
    throw UnimplementedError(
        'cancelDisplayedNotifications() is not implemented');
  }

  Future<void> cancelNotification(
      {required String notificationId, String? tag}) async {
    throw UnimplementedError('cancelNotification() is not implemented');
  }

  Future<void> cancelTriggerNotification(
      {required String notificationId}) async {
    throw UnimplementedError('cancelTriggerNotification() is not implemented');
  }

  Future<void> cancelTriggerNotifications(
      {List<String>? notificationIds}) async {
    throw UnimplementedError('cancelTriggerNotifications() is not implemented');
  }

  Future<String> createChannel(Channel androidChannel) async {
    throw UnimplementedError('createChannel() is not implemented');
  }

  Future<String> createChannelGroup(ChannelGroup channelGroup) async {
    throw UnimplementedError('createChannelGroup() is not implemented');
  }

  Future<void> createChannelGroups(List<ChannelGroup> channelGroups) async {
    throw UnimplementedError('createChannelGroups() is not implemented');
  }

  Future<void> createChannels(List<Channel> channels) async {
    throw UnimplementedError('createChannels() is not implemented');
  }

  Future<String> createIntervalTriggerNotification(
      {required NotifeeNotification notification,
      required IntervalTrigger trigger}) async {
    throw UnimplementedError(
        'createIntervalTriggerNotification() is not implemented');
  }

  Future<String> createTimestampTriggerNotification(
      {required NotifeeNotification notification,
      required TimestampTrigger trigger}) async {
    throw UnimplementedError(
        'createTimestampTriggerNotification() is not implemented');
  }

  Future<void> decrementBadgeCount([int? decrementBy = 1]) async {
    throw UnimplementedError('decrementBadgeCount() is not implemented');
  }

  Future<void> deleteChannel(String channelId) {
    throw UnimplementedError('deleteChannel() is not implemented');
  }

  Future<void> deleteChannelGroup(String channelGroupId) {
    throw UnimplementedError('deleteChannelGroup() is not implemented');
  }

  Future<String> displayNotification(NotifeeNotification notification) {
    throw UnimplementedError('displayNotification() is not implemented');
  }

  Future<Channel?> getChannel(String channelId) async {
    throw UnimplementedError('getChannel() is not implemented');
  }

  Future<ChannelGroup?> getChannelGroup(String channelGroupId) async {
    throw UnimplementedError('getChannelGroup() is not implemented');
  }

  Future<List<ChannelGroup>> getChannelGroups() async {
    throw UnimplementedError('getChannelGroups() is not implemented');
  }

  Future<List<Channel>> getChannels() async {
    throw UnimplementedError('getChannels() is not implemented');
  }

  Future<List<Object>> getDisplayedNotifications() async {
    throw UnimplementedError('getDisplayedNotifications() is not implemented');
  }

  Future<InitialNotification?> getInitialNotification() async {
    throw UnimplementedError('getInitialNotification() is not implemented');
  }

  Future<List<String>> getTriggerNotificationIds() async {
    throw UnimplementedError('getTriggerNotificationIds() is not implemented');
  }

  Future<List<TriggerNotification>> getTriggerNotifications() async {
    throw UnimplementedError('getTriggerNotifications() is not implemented');
  }

  Future<bool> isBatteryOptimizationEnabled() async {
    throw UnimplementedError(
        'isBatteryOptimizationEnabled() is not implemented');
  }

  Future<bool> isChannelBlocked(String channelId) async {
    throw UnimplementedError('isChannelBlocked() is not implemented');
  }

  Future<bool> isChannelCreated(String channelId) async {
    throw UnimplementedError('isChannelCreated() is not implemented');
  }

  Future<int> getBadgeCount() async {
    throw UnimplementedError('getBadgeCount() is not implemented');
  }

  Future<List<IOSNotificationCategory>> getNotificationCategories() async {
    throw UnimplementedError('getNotificationCategories() is not implemented');
  }

  Future<NotificationSettings> getNotificationSettings() async {
    throw UnimplementedError('getNotificationSettings() is not implemented');
  }

  Future<void> hideNotificationDrawer() async {
    throw UnimplementedError('hideNotificationDrawer() is not implemented');
  }

  Future<void> incrementBadgeCount([int? incrementBy = 1]) async {
    throw UnimplementedError('incrementBadgeCount() is not implemented');
  }

  Future<void> openBatteryOptimizationSettings() async {
    throw UnimplementedError(
        'openBatteryOptimizationSettings() is not implemented');
  }

  Future<void> openNotificationSettings(String? channelId) async {
    throw UnimplementedError('openNotificationSettings() is not implemented');
  }

  Future<void> openPowerManagerSettings() async {
    throw UnimplementedError('openPowerManagerSettings() is not implemented');
  }

  Future<Object> getPowerManagerInfo() async {
    throw UnimplementedError('getPowerManagerInfo() is not implemented');
  }

  Future<NotificationSettings> requestPermission(
      [IOSNotificationPermissions? permissions]) async {
    throw UnimplementedError('requestPermission() is not implemented');
  }

  Future<void> setBadgeCount(int count) async {
    throw UnimplementedError('setBadgeCount() is not implemented');
  }

  Future<void> setNotificationCategories(
      List<IOSNotificationCategory> categories) async {
    throw UnimplementedError('setNotificationCategories() is not implemented');
  }

  /// Sends a new event via a [Stream] whenever the Installation ID changes.
  Stream<Event> get onForegroundEvent {
    throw UnimplementedError('get onForegroundEvent is not implemented');
  }
}
