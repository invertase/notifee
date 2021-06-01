export const testNotification = {
  id: 'test-id',
  title: 'test-title',
  body: 'test-body',
  android: {
    channelId: 'default',
  },
};

export const testChannel = {
  id: 'channel-id',
  name: 'channel-name',
};

export const testChannelGroup = {
  id: 'channel-group-id',
  name: 'channel-group',
};

export const testTriggerNotificationIds = ['trigger1', 'trigger2'];

export const testCategory = {
  id: 'test-category',
  actions: [
    {
      id: 'test-action',
      title: 'Test',
    },
  ],
  allowInCarPlay: false,
  allowAnnouncement: false,
  hiddenPreviewsShowTitle: false,
  hiddenPreviewsShowSubtitle: false,
};

export const testNotificationSettings = {
  alert: true,
  badge: true,
  sound: true,
  carPlay: true,
  criticalAlert: true,
  provisional: true,
  lockScreen: true,
  notificationCenter: true,
  showPreviews: true,
  inAppNotificationSettings: true,
};

export const testBadgeCount = 1;

export const testPowerManagerSettings = {
  activity: 'test-activity',
  manufacturer: 'test-manufacturer',
  model: 'test-model',
  version: 'test-version',
};

export default {
  displayNotification: jest.fn(async notification => notification?.id || testNotification.id),
  createTriggerNotification: jest.fn(
    async (notification, _) => notification?.id || testNotification.id,
  ),
  getChannel: jest.fn(async id => ({
    ...testChannel,
    id,
  })),
  getChannels: jest.fn(async () => [testChannel]),
  getChannelGroup: jest.fn(async () => testChannelGroup),
  getChannelGroups: jest.fn(async () => [testChannelGroup]),
  getTriggerNotificationIds: jest.fn(async () => testTriggerNotificationIds),
  cancelAllNotifications: jest.fn(async () => {}),
  cancelDisplayedNotifications: jest.fn(async () => {}),
  cancelTriggerNotifications: jest.fn(async () => {}),
  cancelNotification: jest.fn(async () => {}),
  cancelDisplayedNotification: jest.fn(async () => {}),
  cancelTriggerNotification: jest.fn(async () => {}),
  createChannel: jest.fn(async channel => channel?.id || testChannel.id),
  createChannelGroup: jest.fn(async channelGroup => channelGroup?.id || testChannelGroup.id),
  createChannelGroups: jest.fn(async () => {}),
  deleteChannel: jest.fn(async () => {}),
  getInitialNotification: jest.fn(async () => testNotification),
  onBackgroundEvent: jest.fn(),
  onForegroundEvent: jest.fn(),
  openNotificationSettings: jest.fn(),
  requestPermission: jest.fn(async () => testNotificationSettings),
  registerForegroundService: jest.fn(),
  setNotificationCategories: jest.fn(async () => {}),
  getNotificationCategories: jest.fn(async () => [testCategory]),
  getNotificationSettings: jest.fn(async () => testNotificationSettings),
  getBadgeCount: jest.fn(async () => testBadgeCount),
  setBadgeCount: jest.fn(async () => {}),
  incrementBadgeCount: jest.fn(async () => {}),
  decrementBadgeCount: jest.fn(async () => {}),
  isBatteryOptimizationEnabled: jest.fn(async () => true),
  openBatteryOptimizationSettings: jest.fn(async () => {}),
  getPowerManagerInfo: jest.fn(async () => {}),
  openPowerManagerSettings: jest.fn(async () => testPowerManagerSettings),
  stopForegroundService: jest.fn(async () => {}),
  hideNotificationDrawer: jest.fn(async () => {}),
};
