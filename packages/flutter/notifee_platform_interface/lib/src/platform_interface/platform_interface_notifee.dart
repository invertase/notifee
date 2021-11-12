import 'dart:async';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';
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

  Future<String> createChannel(Object channel) {
    throw UnimplementedError('createChannel() is not implemented');
  }

  Future<String> createChannelGroup(Object channelGroup) {
    throw UnimplementedError('createChannelGroup() is not implemented');
  }

  Future<void> createChannelGroups(List<Object> channelGroups) {
    throw UnimplementedError('createChannelGroups() is not implemented');
  }

  Future<void> createChannels(List<Object> channels) {
    throw UnimplementedError('createChannels() is not implemented');
  }

  Future<String> createTriggerNotification(
      Object notification, Object trigger) {
    throw UnimplementedError('createTriggerNotification() is not implemented');
  }

  Future<void> decrementBadgeCount([int? incrementBy]) async {
    throw UnimplementedError('decrementBadgeCount() is not implemented');
  }

  Future<void> deleteChannel(String channelId) {
    throw UnimplementedError('deleteChannel() is not implemented');
  }

  Future<void> deleteChannelGroup(String channelGroupId) {
    throw UnimplementedError('deleteChannelGroup() is not implemented');
  }

  Future<String> displayNotification(Object notification) {
    throw UnimplementedError('displayNotification() is not implemented');
  }

  Future<Object?> getChannel() async {
    throw UnimplementedError('getChannel() is not implemented');
  }

  Future<Object?> getChannelGroup() async {
    throw UnimplementedError('getChannelGroup() is not implemented');
  }

  Future<List<Object>> getChannelGroups() async {
    throw UnimplementedError('getChannelGroups() is not implemented');
  }

  Future<List<Object>> getChannels() async {
    throw UnimplementedError('getChannels() is not implemented');
  }

  Future<List<Object>> getDisplayedNotifications() async {
    throw UnimplementedError('getDisplayedNotifications() is not implemented');
  }

  Future<Object?> getInitialNotification() async {
    throw UnimplementedError('getInitialNotification() is not implemented');
  }

  Future<List<String>> getTriggerNotificationIds() async {
    throw UnimplementedError('getTriggerNotificationIds() is not implemented');
  }

  Future<List<Object>> getTriggerNotifications() async {
    throw UnimplementedError('getTriggerNotifications() is not implemented');
  }

  Future<bool> isBatteryOptimizationEnabled() async {
    throw UnimplementedError(
        'isBatteryOptimizationEnabled() is not implemented');
  }

  Future<bool> isChannelBlocked() async {
    throw UnimplementedError('isChannelBlocked() is not implemented');
  }

  Future<bool> isChannelCreated(String channelId) async {
    throw UnimplementedError('isChannelCreated() is not implemented');
  }

  Future<int> getBadgeCount() async {
    throw UnimplementedError('getBadgeCount() is not implemented');
  }

  Future<List<Object>> getNotificationCategories() async {
    throw UnimplementedError('getNotificationCategories() is not implemented');
  }

  Future<Object> getNotificationSettings() async {
    throw UnimplementedError('getNotificationSettings() is not implemented');
  }

  Future<void> hideNotificationDrawer() async {
    throw UnimplementedError('hideNotificationDrawer() is not implemented');
  }

  Future<void> incrementBadgeCount([int? incrementBy]) async {
    throw UnimplementedError('incrementBadgeCount() is not implemented');
  }

  Future<void> openBatteryOptimizationSettings() async {
    throw UnimplementedError(
        'openBatteryOptimizationSettings() is not implemented');
  }

  Future<void> openNotificationSettings([String? channelId]) async {
    throw UnimplementedError('openNotificationSettings() is not implemented');
  }

  Future<void> openPowerManagerSettings() async {
    throw UnimplementedError('openPowerManagerSettings() is not implemented');
  }

  Future<Object> getPowerManagerInfo() async {
    throw UnimplementedError('getPowerManagerInfo() is not implemented');
  }

  Future<Object> requestPermission([Object? permissions]) async {
    throw UnimplementedError('requestPermission() is not implemented');
  }

  Future<void> setBadgeCount(int count) async {
    throw UnimplementedError('setBadgeCount() is not implemented');
  }

  Future<void> setNotificationCategories(List<Object> categories) async {
    throw UnimplementedError('setNotificationCategories() is not implemented');
  }

  // TODO on background event
  // TODO on foregound event
  // TODO register foreground service
  // TODO stop foreground service
}
