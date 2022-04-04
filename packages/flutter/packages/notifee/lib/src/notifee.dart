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

/// The delegate interface for the Notifee library.
NotifeePlatform get _delegate {
  return NotifeePlatform.instance;
}

Stream<Event> get onForegroundEvent {
  return _delegate.onForegroundEvent;
}

void onBackgroundMessage(BackgroundEventHandler handler) {
  NotifeePlatform.onBackgroundEvent = handler;
}

Future<List<String>> getTriggerNotificationIds() async {
  return _delegate.getTriggerNotificationIds();
}

Future<List<TriggerNotification>> getTriggerNotifications() async {
  return _delegate.getTriggerNotifications();
}

Future<List<Object>> getDisplayedNotifications() async {
  return _delegate.getDisplayedNotifications();
}

Future<bool> isChannelBlocked(String channelId) async {
  return _delegate.isChannelBlocked(channelId);
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

Future<String> createChannel(Channel androidChannel) {
  return _delegate.createChannel(androidChannel);
}

Future<void> createChannels(List<Channel> channels) {
  return _delegate.createChannels(channels);
}

Future<String> createChannelGroup(ChannelGroup channelGroup) {
  return _delegate.createChannelGroup(channelGroup);
}

Future<void> createChannelGroups(List<ChannelGroup> channelGroups) {
  return _delegate.createChannelGroups(channelGroups);
}

Future<void> deleteChannel(String channelId) {
  return _delegate.deleteChannel(channelId);
}

Future<void> deleteChannelGroup(String channelGroupId) {
  return _delegate.deleteChannelGroup(channelGroupId);
}

Future<String> displayNotification(NotifeeNotification notification) {
  return _delegate.displayNotification(notification);
}

Future<String> createIntervalTriggerNotification(
    {required NotifeeNotification notification,
    required IntervalTrigger trigger}) {
  return _delegate.createIntervalTriggerNotification(
      notification: notification, trigger: trigger);
}

Future<String> createTimestampTriggerNotification(
    {required NotifeeNotification notification,
    required TimestampTrigger trigger}) {
  return _delegate.createTimestampTriggerNotification(
      notification: notification, trigger: trigger);
}

Future<Channel?> getChannel(String channelId) async {
  return _delegate.getChannel(channelId);
}

Future<List<Channel>> getChannels() async {
  return _delegate.getChannels();
}

Future<ChannelGroup?> getChannelGroup(String channelGroupId) async {
  return _delegate.getChannelGroup(channelGroupId);
}

Future<List<ChannelGroup>> getChannelGroups() async {
  return _delegate.getChannelGroups();
}

Future<InitialNotification?> getInitialNotification() async {
  return _delegate.getInitialNotification();
}

// TODO on background event
// TODO on foregound event

Future<void> openNotificationSettings([String? channelId]) async {
  return _delegate.openNotificationSettings(channelId);
}

Future<Object> requestPermission(
    [IOSNotificationPermissions? permissions]) async {
  return _delegate.requestPermission(permissions);
}

// TODO register foreground service

Future<void> setNotificationCategories(List<IOSNotificationCategory> categories) async {
  return _delegate.setNotificationCategories(categories);
}

Future<List<IOSNotificationCategory>> getNotificationCategories() async {
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
