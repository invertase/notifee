import 'package:flutter/services.dart';
import 'package:notifee_platform_interface/notifee_platform_interface.dart';

class MethodChannelNotifee extends NotifeePlatform {
  MethodChannelNotifee() : super();

  static MethodChannel channel = const MethodChannel(
    'plugins.invertase.io/notifee',
  );

  @override
  Future<void> cancelAllNotifications(
      {List<String>? notificationIds, String? tag}) {
    throw UnimplementedError();
  }

  @override
  Future<void> cancelDisplayedNotification(
      {required String notificationId, String? tag}) {
    throw UnimplementedError();
  }

  @override
  Future<void> cancelDisplayedNotifications(
      {List<String>? notificationIds, String? tag}) {
    throw UnimplementedError();
  }

  @override
  Future<void> cancelNotification(
      {required String notificationId, String? tag}) {
    throw UnimplementedError();
  }

  @override
  Future<void> cancelTriggerNotification({required String notificationId}) {
    throw UnimplementedError();
  }

  @override
  Future<void> cancelTriggerNotifications({List<String>? notificationIds}) {
    throw UnimplementedError();
  }

  @override
  Future<String> createChannel(Object channel) {
    throw UnimplementedError();
  }

  @override
  Future<String> createChannelGroup(Object channelGroup) {
    throw UnimplementedError();
  }

  @override
  Future<void> createChannelGroups(List<Object> channelGroups) {
    throw UnimplementedError();
  }

  @override
  Future<void> createChannels(List<Object> channels) {
    throw UnimplementedError();
  }

  @override
  Future<String> createTriggerNotification(
      Object notification, Object trigger) {
    throw UnimplementedError();
  }

  @override
  Future<void> decrementBadgeCount([int? incrementBy]) {
    throw UnimplementedError();
  }

  @override
  Future<void> deleteChannel(String channelId) {
    throw UnimplementedError();
  }

  @override
  Future<void> deleteChannelGroup(String channelGroupId) {
    throw UnimplementedError();
  }

  @override
  Future<String> displayNotification(Object notification) {
    throw UnimplementedError();
  }

  @override
  Future<int> getBadgeCount() {
    throw UnimplementedError();
  }

  @override
  Future<Object?> getChannel() {
    throw UnimplementedError();
  }

  @override
  Future<Object?> getChannelGroup() {
    throw UnimplementedError();
  }

  @override
  Future<List<Object>> getChannelGroups() {
    throw UnimplementedError();
  }

  @override
  Future<List<Object>> getChannels() {
    throw UnimplementedError();
  }

  @override
  Future<List<Object>> getDisplayedNotifications() {
    throw UnimplementedError();
  }

  @override
  Future<Object?> getInitialNotification() {
    throw UnimplementedError();
  }

  @override
  Future<List<Object>> getNotificationCategories() {
    throw UnimplementedError();
  }

  @override
  Future<Object> getNotificationSettings() {
    throw UnimplementedError();
  }

  @override
  Future<Object> getPowerManagerInfo() {
    throw UnimplementedError();
  }

  @override
  Future<List<String>> getTriggerNotificationIds() {
    throw UnimplementedError();
  }

  @override
  Future<List<Object>> getTriggerNotifications() {
    throw UnimplementedError();
  }

  @override
  Future<void> hideNotificationDrawer() {
    throw UnimplementedError();
  }

  @override
  Future<void> incrementBadgeCount([int? incrementBy]) {
    throw UnimplementedError();
  }

  @override
  Future<bool> isBatteryOptimizationEnabled() {
    throw UnimplementedError();
  }

  @override
  Future<bool> isChannelBlocked() {
    throw UnimplementedError();
  }

  @override
  Future<bool> isChannelCreated(String channelId) {
    throw UnimplementedError();
  }

  @override
  Future<void> openBatteryOptimizationSettings() {
    throw UnimplementedError();
  }

  @override
  Future<void> openNotificationSettings([String? channelId]) {
    throw UnimplementedError();
  }

  @override
  Future<void> openPowerManagerSettings() {
    throw UnimplementedError();
  }

  @override
  Future<Object> requestPermission([Object? permissions]) {
    throw UnimplementedError();
  }

  @override
  Future<void> setBadgeCount(int count) {
    throw UnimplementedError();
  }

  @override
  Future<void> setNotificationCategories(List<Object> categories) {
    throw UnimplementedError();
  }
}
