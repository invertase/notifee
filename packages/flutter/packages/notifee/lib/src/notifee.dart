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

/// Handles events when the application is in a foreground state.
Stream<Event> get onForegroundEvent {
  return _delegate.onForegroundEvent;
}

void onBackgroundEvent(BackgroundEventHandler handler) {
  NotifeePlatform.onBackgroundEvent = handler;
}

/// Returns the ids of trigger notifications that are pending.
Future<List<String>> getTriggerNotificationIds() async {
  return _delegate.getTriggerNotificationIds();
}

/// Returns the trigger notifications that are pending.
Future<List<TriggerNotification>> getTriggerNotifications() async {
  return _delegate.getTriggerNotifications();
}

/// Returns the notifications that are displayed.
Future<List<Object>> getDisplayedNotifications() async {
  return _delegate.getDisplayedNotifications();
}

/// Checks if a channel is blocked.
/// On iOS, this will return false
Future<bool> isChannelBlocked(String channelId) async {
  return _delegate.isChannelBlocked(channelId);
}

/// Checks if a channel is created.
/// On iOS, this will return false
Future<bool> isChannelCreated(String channelId) async {
  return _delegate.isChannelCreated(channelId);
}

/// Cancels all notifications, which includes removing any displayed notifications from the users device and
/// any pending trigger notifications.
Future<void> cancelAllNotifications(
    {List<String>? notificationIds, String? tag}) async {
  return _delegate.cancelAllNotifications(
      notificationIds: notificationIds, tag: tag);
}

/// Cancels any displayed notifications except for [ForegroundService] notifications.
Future<void> cancelDisplayedNotifications(
    {List<String>? notificationIds, String? tag}) async {
  return _delegate.cancelDisplayedNotifications(
      notificationIds: notificationIds, tag: tag);
}

/// Cancels any trigger notifications.
Future<void> cancelTriggerNotifications({List<String>? notificationIds}) async {
  return _delegate.cancelTriggerNotifications(notificationIds: notificationIds);
}

/// Cancels a single notification
///
/// Removes displayed or pending trigger notifications set for the specified ID,
/// except for [ForegroundService] notifications.
Future<void> cancelNotification(
    {required String notificationId, String? tag}) async {
  return _delegate.cancelNotification(notificationId: notificationId, tag: tag);
}

/// Cancels a single displayed notification
Future<void> cancelDisplayedNotification(
    {required String notificationId, String? tag}) async {
  return _delegate.cancelDisplayedNotification(
      notificationId: notificationId, tag: tag);
}

/// Cancels a single trigger notification.
Future<void> cancelTriggerNotification({required String notificationId}) async {
  return _delegate.cancelTriggerNotification(notificationId: notificationId);
}

/// Creates and update channels on supported Android devices.
///
/// Creates a new Android channel. Channels are used to collectively assign notifications to
/// a single responsible channel. Users can manage settings for channels, e.g. disabling sound or vibration.
///
///  By providing a `groupId` property, channels can be assigned to groups created with [createChannelGroup].
///
///  The channel ID is returned once the operation has completed.
Future<String> createChannel(Channel androidChannel) {
  return _delegate.createChannel(androidChannel);
}

/// Creates and updates multiple channels on supported Android devices.
Future<void> createChannels(List<Channel> channels) {
  return _delegate.createChannels(channels);
}

/// Creates or updates a channel group on supported Android devices.
///
/// Creates a new Android channel group. Groups are used to further organize the appearance of your
/// channels in the settings UI. Groups allow users to easily identify and control multiple notification channels.
///
/// Channels can be assigned to groups during creation using the [createChannel]
/// The channel group ID is returned once the operation has completed.
Future<String> createChannelGroup(ChannelGroup channelGroup) {
  return _delegate.createChannelGroup(channelGroup);
}

/// Creates and updates multiple channel groups on supported Android devices.
Future<void> createChannelGroups(List<ChannelGroup> channelGroups) {
  return _delegate.createChannelGroups(channelGroups);
}

/// Deletes a channel by ID on supported Android devices.
///
/// Channels can be deleted using this API by providing the channel ID. Channel information (including the ID)
/// can be retrieved from [getChannels]
///
/// When a channel is deleted, notifications assigned to that channel will fail to display.
Future<void> deleteChannel(String channelId) {
  return _delegate.deleteChannel(channelId);
}

/// Deletes a channel group by ID on supported Android devices.
///
/// Channel groups can be deleted using this API by providing the channel ID.
/// Channel information (including the ID) can be retrieved from the [getChannels].
///
/// Deleting a group does not delete channels which are assigned to the group,
/// they will instead be unassigned the group and continue to function as expected.
Future<void> deleteChannelGroup(String channelGroupId) {
  return _delegate.deleteChannelGroup(channelGroupId);
}

/// Displays or updates a notification on the users device.
///
/// All channels/categories should be created before triggering this method during the apps lifecycle.
Future<String> displayNotification(NotifeeNotification notification) {
  return _delegate.displayNotification(notification);
}

/// Creates a trigger notification that repeats at a specified interval.
Future<String> createIntervalTriggerNotification(
    {required NotifeeNotification notification,
    required IntervalTrigger trigger}) {
  return _delegate.createIntervalTriggerNotification(
      notification: notification, trigger: trigger);
}

/// Creates a trigger notification that is scheduled at a specific data and time
Future<String> createTimestampTriggerNotification(
    {required NotifeeNotification notification,
    required TimestampTrigger trigger}) {
  return _delegate.createTimestampTriggerNotification(
      notification: notification, trigger: trigger);
}

/// Returns a channel on supported Android devices.
///
/// Returns `null` if no channel could be matched to the given ID.
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

Future<void> openNotificationSettings([String? channelId]) async {
  return _delegate.openNotificationSettings(channelId);
}

Future<NotificationSettings> requestPermission(
    [IOSNotificationPermissions? permissions]) async {
  return _delegate.requestPermission(permissions);
}

Future<void> setNotificationCategories(
    List<IOSNotificationCategory> categories) async {
  return _delegate.setNotificationCategories(categories);
}

Future<List<IOSNotificationCategory>> getNotificationCategories() async {
  return _delegate.getNotificationCategories();
}

Future<NotificationSettings> getNotificationSettings() async {
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

Future<void> hideNotificationDrawer() async {
  return _delegate.hideNotificationDrawer();
}

// TODO: register foreground service
// TODO stop foreground service
