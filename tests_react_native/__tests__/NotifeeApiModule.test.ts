// @ts-ignore
import NotifeeApiModule from '@notifee/react-native/src/NotifeeApiModule';
import Notifee from '@notifee/react-native';

import {
  /* @ts-ignore */
  mockNotifeeNativeModule,
} from '@notifee/react-native/src/NotifeeNativeModule';
import { AndroidChannel } from '@notifee/react-native/src/types/NotificationAndroid';
import { setPlatform } from './testSetup';

jest.mock('@notifee/react-native/src/NotifeeNativeModule');

const apiModule = new NotifeeApiModule({
  version: Notifee.SDK_VERSION,
  nativeModuleName: 'NotifeeApiModule',
  nativeEvents: [],
});

describe('Notifee Api Module', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('Module is defined on import', () => {
    expect(NotifeeApiModule).toBeDefined();
  });
  test('Constructor', () => {
    expect(apiModule).not.toBeNull;
  });

  test('getTriggerNotificationIds', async () => {
    const triggerIds = ['12345'];
    mockNotifeeNativeModule.getTriggerNotificationIds.mockResolvedValue(triggerIds);

    const res = await apiModule.getTriggerNotificationIds();
    expect(res).toBe(triggerIds);
    expect(mockNotifeeNativeModule.getTriggerNotificationIds).toBeCalledTimes(1);
  });

  test('cancelAllNotifications', async () => {
    const res = await apiModule.cancelAllNotifications();

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelAllNotifications).toBeCalledTimes(1);
  });

  test('cancelAllNotifications', async () => {
    const res = await apiModule.cancelAllNotifications();

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelAllNotifications).toBeCalledTimes(1);
  });

  test('cancelAllNotifications', async () => {
    const res = await apiModule.cancelAllNotifications();

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelAllNotifications).toBeCalledTimes(1);
  });

  test('cancelDisplayedNotifications', async () => {
    const res = await apiModule.cancelDisplayedNotifications();

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelDisplayedNotifications).toBeCalledTimes(1);
  });

  test('cancelTriggerNotifications', async () => {
    const res = await apiModule.cancelTriggerNotifications();

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelTriggerNotifications).toBeCalledTimes(1);
  });

  test('cancelNotification', async () => {
    const notificationId = 'id';
    const res = await apiModule.cancelNotification(notificationId);

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelNotification).toBeCalledWith(notificationId);
  });

  test('cancelDisplayedNotification', async () => {
    const notificationId = 'id';
    const res = await apiModule.cancelDisplayedNotification(notificationId);

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelDisplayedNotification).toBeCalledWith(notificationId);
  });

  test('cancelTriggerNotification', async () => {
    const notificationId = 'id';
    const res = await apiModule.cancelTriggerNotification(notificationId);

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelTriggerNotification).toBeCalledWith(notificationId);
  });

  describe('createChannel', () => {
    test('return empty string for iOS', async () => {
      setPlatform('ios');
      const channel: AndroidChannel = {
        id: 'channel-id',
        name: 'channel',
      };

      const res = await apiModule.createChannel(channel);

      expect(res).toBe('');
      expect(mockNotifeeNativeModule.createChannel).not.toBeCalled();
    });

    test('return channel id for Android', async () => {
      setPlatform('android');

      const channel: AndroidChannel = {
        id: 'channel-id',
        name: 'channel',
      };
      mockNotifeeNativeModule.createChannel.mockResolvedValue(channel);

      const res = await apiModule.createChannel(channel);

      expect(res).toBe(channel.id);
      expect(mockNotifeeNativeModule.createChannel).toBeCalledWith({
        badge: true,
        bypassDnd: false,
        id: 'channel-id',
        importance: 3,
        lights: true,
        name: 'channel',
        vibration: true,
        visibility: 0,
      });
    });
  });
});
