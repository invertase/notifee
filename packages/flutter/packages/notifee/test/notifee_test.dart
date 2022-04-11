import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';
import 'package:notifee_platform_interface/notifee_platform_interface.dart';
import 'package:notifee/notifee.dart' as notifee;

void main() {
  const MethodChannel channel = MethodChannel('notifee');

  TestWidgetsFlutterBinding.ensureInitialized();

  final MockNotifee kMockNotifeePlatform = MockNotifee();

  setUpAll(() async {
    NotifeePlatform.instance = kMockNotifeePlatform;
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  group('cancelAllNotifications', () {
    test('verify delegate method is called with correct args', () async {
      when(kMockNotifeePlatform.cancelAllNotifications(
              notificationIds: anyNamed('notificationIds'),
              tag: anyNamed('tag')))
          .thenAnswer((_) => Future<void>.value());

      await notifee.cancelAllNotifications();
      verify(kMockNotifeePlatform.cancelAllNotifications());

      await notifee.cancelAllNotifications(notificationIds: ['id-1']);
      verify(kMockNotifeePlatform
          .cancelAllNotifications(notificationIds: ['id-1']));

      await notifee
          .cancelAllNotifications(notificationIds: ['id-1'], tag: 'tag-1');
      verify(kMockNotifeePlatform
          .cancelAllNotifications(notificationIds: ['id-1'], tag: 'tag-1'));
    });
  });
}

// Platform Interface Mock Classes

// NotifeePlatform Mock
class MockNotifee extends Mock
    with MockPlatformInterfaceMixin
    implements NotifeePlatform {
  MockNotifee() {
    TestNotifeePlatform();
  }

  @override
  Future<void> cancelAllNotifications(
      {List<String>? notificationIds, String? tag}) {
    return super.noSuchMethod(
        Invocation.method(#cancelAllNotifications, [],
            {#notificationIds: notificationIds, #tag: tag}),
        returnValue: Future<void>.value(),
        returnValueForMissingStub: Future<void>.value());
  }

  @override
  Future<void> cancelDisplayedNotification(
      {required String notificationId, String? tag}) {
    // TODO: implement cancelDisplayedNotification
    throw UnimplementedError();
  }

  @override
  Future<void> cancelDisplayedNotifications(
      {List<String>? notificationIds, String? tag}) {
    // TODO: implement cancelDisplayedNotifications
    throw UnimplementedError();
  }

  @override
  Future<void> cancelNotification(
      {required String notificationId, String? tag}) {
    // TODO: implement cancelNotification
    throw UnimplementedError();
  }

  @override
  Future<void> cancelTriggerNotification({required String notificationId}) {
    // TODO: implement cancelTriggerNotification
    throw UnimplementedError();
  }

  @override
  Future<void> cancelTriggerNotifications({List<String>? notificationIds}) {
    // TODO: implement cancelTriggerNotifications
    throw UnimplementedError();
  }

  @override
  Future<String> createChannel(Channel androidChannel) {
    // TODO: implement createChannel
    throw UnimplementedError();
  }

  @override
  Future<String> createChannelGroup(ChannelGroup channelGroup) {
    // TODO: implement createChannelGroup
    throw UnimplementedError();
  }

  @override
  Future<void> createChannelGroups(List<ChannelGroup> channelGroups) {
    // TODO: implement createChannelGroups
    throw UnimplementedError();
  }

  @override
  Future<void> createChannels(List<Channel> channels) {
    // TODO: implement createChannels
    throw UnimplementedError();
  }

  @override
  Future<String> createIntervalTriggerNotification(
      {required NotifeeNotification notification,
      required IntervalTrigger trigger}) {
    // TODO: implement createIntervalTriggerNotification
    throw UnimplementedError();
  }

  @override
  Future<String> createTimestampTriggerNotification(
      {required NotifeeNotification notification,
      required TimestampTrigger trigger}) {
    // TODO: implement createTimestampTriggerNotification
    throw UnimplementedError();
  }

  @override
  Future<void> decrementBadgeCount([int? decrementBy = 1]) {
    // TODO: implement decrementBadgeCount
    throw UnimplementedError();
  }

  @override
  Future<void> deleteChannel(String channelId) {
    // TODO: implement deleteChannel
    throw UnimplementedError();
  }

  @override
  Future<void> deleteChannelGroup(String channelGroupId) {
    // TODO: implement deleteChannelGroup
    throw UnimplementedError();
  }

  @override
  Future<String> displayNotification(NotifeeNotification notification) {
    // TODO: implement displayNotification
    throw UnimplementedError();
  }

  @override
  Future<int> getBadgeCount() {
    // TODO: implement getBadgeCount
    throw UnimplementedError();
  }

  @override
  Future<Channel?> getChannel(String channelId) {
    // TODO: implement getChannel
    throw UnimplementedError();
  }

  @override
  Future<ChannelGroup?> getChannelGroup(String channelGroupId) {
    // TODO: implement getChannelGroup
    throw UnimplementedError();
  }

  @override
  Future<List<ChannelGroup>> getChannelGroups() {
    // TODO: implement getChannelGroups
    throw UnimplementedError();
  }

  @override
  Future<List<Channel>> getChannels() {
    // TODO: implement getChannels
    throw UnimplementedError();
  }

  @override
  Future<List<Object>> getDisplayedNotifications() {
    // TODO: implement getDisplayedNotifications
    throw UnimplementedError();
  }

  @override
  Future<InitialNotification?> getInitialNotification() {
    // TODO: implement getInitialNotification
    throw UnimplementedError();
  }

  @override
  Future<List<IOSNotificationCategory>> getNotificationCategories() {
    // TODO: implement getNotificationCategories
    throw UnimplementedError();
  }

  @override
  Future<NotificationSettings> getNotificationSettings() {
    // TODO: implement getNotificationSettings
    throw UnimplementedError();
  }

  @override
  Future<Object> getPowerManagerInfo() {
    // TODO: implement getPowerManagerInfo
    throw UnimplementedError();
  }

  @override
  Future<List<String>> getTriggerNotificationIds() {
    // TODO: implement getTriggerNotificationIds
    throw UnimplementedError();
  }

  @override
  Future<List<TriggerNotification>> getTriggerNotifications() {
    // TODO: implement getTriggerNotifications
    throw UnimplementedError();
  }

  @override
  Future<void> hideNotificationDrawer() {
    // TODO: implement hideNotificationDrawer
    throw UnimplementedError();
  }

  @override
  Future<void> incrementBadgeCount([int? incrementBy = 1]) {
    // TODO: implement incrementBadgeCount
    throw UnimplementedError();
  }

  @override
  Future<bool> isBatteryOptimizationEnabled() {
    // TODO: implement isBatteryOptimizationEnabled
    throw UnimplementedError();
  }

  @override
  Future<bool> isChannelBlocked(String channelId) {
    // TODO: implement isChannelBlocked
    throw UnimplementedError();
  }

  @override
  Future<bool> isChannelCreated(String channelId) {
    // TODO: implement isChannelCreated
    throw UnimplementedError();
  }

  @override
  // TODO: implement onForegroundEvent
  Stream<Event> get onForegroundEvent => throw UnimplementedError();

  @override
  Future<void> openBatteryOptimizationSettings() {
    // TODO: implement openBatteryOptimizationSettings
    throw UnimplementedError();
  }

  @override
  Future<void> openNotificationSettings(String? channelId) {
    // TODO: implement openNotificationSettings
    throw UnimplementedError();
  }

  @override
  Future<void> openPowerManagerSettings() {
    // TODO: implement openPowerManagerSettings
    throw UnimplementedError();
  }

  @override
  Future<void> registerBackgroundEventHandler(BackgroundEventHandler handler) {
    // TODO: implement registerBackgroundEventHandler
    throw UnimplementedError();
  }

  @override
  Future<NotificationSettings> requestPermission(
      [IOSNotificationPermissions? permissions]) {
    // TODO: implement requestPermission
    throw UnimplementedError();
  }

  @override
  Future<void> setBadgeCount(int count) {
    // TODO: implement setBadgeCount
    throw UnimplementedError();
  }

  @override
  Future<void> setNotificationCategories(
      List<IOSNotificationCategory> categories) {
    // TODO: implement setNotificationCategories
    throw UnimplementedError();
  }
}

class TestNotifeePlatform extends NotifeePlatform {
  TestNotifeePlatform() : super();
}
