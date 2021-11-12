library notifee;

import 'package:notifee_platform_interface/notifee_platform_interface.dart';

/// The delegate interface for the Notifee library.
NotifeePlatform get _delegate {
  return NotifeePlatform.instance;
}

Future<List<String>> getTriggerNotificationIds() async {
  return _delegate.getTriggerNotificationIds();
}

Future<List<Object>> getTriggerNotifications() async {
  return _delegate.getTriggerNotifications();
}

Future<List<Object>> getDisplayedNotifications() async {
  return _delegate.getDisplayedNotifications();
}

Future<bool> isChannelBlocked() async {
  return _delegate.isChannelBlocked();
}

Future<bool> isChannelCreated(String channelId) async {
  return _delegate.isChannelCreated(channelId);
}

Future<void> cancelAllNotifications(
    {List<String>? notificationIds, String? tag}) async {
  return _delegate.cancelAllNotifications(
      notificationIds: notificationIds, tag: tag);
}

Future<void> cancelDisplayedNotifications(
    {List<String>? notificationIds, String? tag}) async {
  return _delegate.cancelDisplayedNotifications(
      notificationIds: notificationIds, tag: tag);
}

Future<void> cancelTriggerNotifications({List<String>? notificationIds}) async {
  return _delegate.cancelTriggerNotifications(notificationIds: notificationIds);
}

Future<void> cancelNotification(
    {required String notificationId, String? tag}) async {
  return _delegate.cancelNotification(notificationId: notificationId, tag: tag);
}

Future<void> cancelDisplayedNotification(
    {required String notificationId, String? tag}) async {
  return _delegate.cancelDisplayedNotification(
      notificationId: notificationId, tag: tag);
}

Future<void> cancelTriggerNotification({required String notificationId}) async {
  return _delegate.cancelTriggerNotification(notificationId: notificationId);
}

Future<String> createChannel(Object channel) {
  return _delegate.createChannel(channel);
}

Future<void> createChannels(List<Object> channels) {
  return _delegate.createChannels(channels);
}

Future<String> createChannelGroup(Object channelGroup) {
  return _delegate.createChannelGroup(channelGroup);
}

Future<void> createChannelGroups(List<Object> channelGroups) {
  return _delegate.createChannelGroups(channelGroups);
}

Future<void> deleteChannel(String channelId) {
  return _delegate.deleteChannel(channelId);
}

Future<void> deleteChannelGroup(String channelGroupId) {
  return _delegate.deleteChannelGroup(channelGroupId);
}

Future<String> displayNotification(Object notification) {
  return _delegate.displayNotification(notification);
}

Future<String> createTriggerNotification(Object notification, Object trigger) {
  return _delegate.createTriggerNotification(notification, trigger);
}

Future<Object?> getChannel() async {
  return _delegate.getChannel();
}

Future<List<Object>> getChannels() async {
  return _delegate.getChannels();
}

Future<Object?> getChannelGroup() async {
  return _delegate.getChannelGroup();
}

Future<List<Object>> getChannelGroups() async {
  return _delegate.getChannelGroups();
}

Future<Object?> getInitialNotification() async {
  return _delegate.getInitialNotification();
}

// TODO on background event
// TODO on foregound event

Future<void> openNotificationSettings([String? channelId]) async {
  return _delegate.openNotificationSettings(channelId);
}

Future<Object> requestPermission([Object? permissions]) async {
  return _delegate.requestPermission(permissions);
}

// TODO register foreground service

Future<void> setNotificationCategories(List<Object> categories) async {
  return _delegate.setNotificationCategories(categories);
}

Future<List<Object>> getNotificationCategories() async {
  return _delegate.getNotificationCategories();
}

Future<Object> getNotificationSettings() async {
  return _delegate.getNotificationSettings();
}

Future<int> getBadgeCount() async {
  return _delegate.getBadgeCount();
}

Future<void> setBadgeCount(int count) async {
  return _delegate.setBadgeCount(count);
}

Future<void> incrementBadgeCount([int? incrementBy]) async {
  return _delegate.incrementBadgeCount(incrementBy);
}

Future<void> decrementBadgeCount([int? incrementBy]) async {
  return _delegate.decrementBadgeCount(incrementBy);
}

Future<bool> isBatteryOptimizationEnabled() async {
  return _delegate.isBatteryOptimizationEnabled();
}

Future<void> openBatteryOptimizationSettings() async {
  return _delegate.openBatteryOptimizationSettings();
}

Future<Object> getPowerManagerInfo() async {
  return _delegate.getPowerManagerInfo();
}

Future<void> openPowerManagerSettings() async {
  return _delegate.openPowerManagerSettings();
}

// TODO stop foreground service

Future<void> hideNotificationDrawer() async {
  return _delegate.hideNotificationDrawer();
}
