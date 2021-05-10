/**
 * @format
 */
/* eslint-env jest */

import NotifeeJSEventEmitter from '../NotifeeJSEventEmitter';

export const mockNotifeeNativeModule = {
  getTriggerNotificationIds: jest.fn(),
  cancelAllNotifications: jest.fn(),
  cancelDisplayedNotifications: jest.fn(),
  cancelTriggerNotifications: jest.fn(),
  cancelNotification: jest.fn(),
  cancelDisplayedNotification: jest.fn(),
  cancelTriggerNotification: jest.fn(),
  createChannel: jest.fn(),
  createChannels: jest.fn(),
  createChannelGroup: jest.fn(),
  createChannelGroups: jest.fn(),
  deleteChannel: jest.fn(),
  deleteChannelGroup: jest.fn(),
  displayNotification: jest.fn(),
  createTriggerNotification: jest.fn(),
  getChannel: jest.fn(),
  getChannels: jest.fn(),
  getChannelGroup: jest.fn(),
  getChannelGroups: jest.fn(),
  getInitialNotification: jest.fn(),
  onBackgroundEvent: jest.fn(),
  onForegroundEvent: jest.fn(),
  openNotificationSettings: jest.fn(),
  requestPermission: jest.fn(),
  registerForegroundService: jest.fn(),
  setNotificationCategories: jest.fn(),
  getNotificationCategories: jest.fn(),
  getNotificationSettings: jest.fn(),
  getBadgeCount: jest.fn(),
  setBadgeCount: jest.fn(),
  incrementBadgeCount: jest.fn(),
  decrementBadgeCount: jest.fn(),
  isBatteryOptimizationEnabled: jest.fn(),
  openBatteryOptimizationSettings: jest.fn(),
  getPowerManagerInfo: jest.fn(),
  openPowerManagerSettings: jest.fn(),
  stopForegroundService: jest.fn(),
  hideNotificationDrawer: jest.fn(),
};

const mock = jest.fn().mockImplementation(() => {
  return { emitter: NotifeeJSEventEmitter, native: mockNotifeeNativeModule };
});

export default mock;
