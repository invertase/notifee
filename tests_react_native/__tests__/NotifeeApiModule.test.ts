// @ts-ignore
import NotifeeApiModule from '@notifee/react-native/src/NotifeeApiModule';
import Notifee from '@notifee/react-native';

import {
  /* @ts-ignore */
  mockNotifeeNativeModule,
} from '@notifee/react-native/src/NotifeeNativeModule';
import { AndroidChannel } from '@notifee/react-native/src/types/NotificationAndroid';
import { setPlatform } from './testSetup';
import { TriggerNotification, TriggerType } from '@notifee/react-native/src';

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
    expect(apiModule).not.toBeNull();
  });

  test('getDisplayedNotifications', async () => {
    const notification = {
      id: 'notification',
      date: new Date(Date.now()).getTime(),
      notification: {
        id: 'notification',
      },
    };
    const notifications = [notification];
    mockNotifeeNativeModule.getDisplayedNotifications.mockResolvedValue(notifications);

    const res = await apiModule.getDisplayedNotifications();
    expect(res.length).toBe(1);
    expect(res[0]).toBe(notification);
    expect(mockNotifeeNativeModule.getDisplayedNotifications).toBeCalledTimes(1);
  });

  test('getTriggerNotifications', async () => {
    const triggerNotification: TriggerNotification = {
      notification: { id: 'notification' },
      trigger: {
        type: TriggerType.TIMESTAMP,
        timestamp: new Date(Date.now()).getTime(),
      },
    };

    const triggerNotifications = [triggerNotification];
    mockNotifeeNativeModule.getTriggerNotifications.mockResolvedValue(triggerNotifications);

    const res = await apiModule.getTriggerNotifications();
    expect(res).toBe(triggerNotifications);
    expect(mockNotifeeNativeModule.getTriggerNotifications).toBeCalledTimes(1);
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

  test('cancelAllNotifications(ids)', async () => {
    const res = await apiModule.cancelAllNotifications(['id']);

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelAllNotificationsWithIds).nthCalledWith(1, ['id']);
  });

  test('cancelDisplayedNotifications(ids)', async () => {
    const res = await apiModule.cancelDisplayedNotifications(['id']);

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelDisplayedNotificationsWithIds).nthCalledWith(1, ['id']);
  });

  test('cancelTriggerNotifications(ids)', async () => {
    const res = await apiModule.cancelTriggerNotifications(['id']);

    expect(res).toBe(undefined);
    expect(mockNotifeeNativeModule.cancelTriggerNotificationsWithIds).nthCalledWith(1, ['id']);
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

  describe('isChannelBlocked', () => {
    test('on iOS', async () => {
      setPlatform('ios');
      const channel: AndroidChannel = {
        id: 'channel-id',
        name: 'channel',
      };

      const res = await apiModule.isChannelBlocked(channel.id);

      expect(res).toBe(false);
      expect(mockNotifeeNativeModule.createChannel).not.toBeCalled();
    });

    test('on Android', async () => {
      setPlatform('android');
      const channel: AndroidChannel = {
        id: 'channel-id',
        name: 'channel',
      };

      const res = await apiModule.isChannelBlocked(channel.id);

      expect(res).toBe(false);
      expect(mockNotifeeNativeModule.createChannel).not.toBeCalled();
    });
  });

  describe('isChannelCreated', () => {
    test('on iOS', async () => {
      setPlatform('ios');
      const channel: AndroidChannel = {
        id: 'channel-id',
        name: 'channel',
      };

      const res = await apiModule.isChannelCreated(channel.id);

      expect(res).toBe(true);
      expect(mockNotifeeNativeModule.createChannel).not.toBeCalled();
    });

    test('on Android', async () => {
      setPlatform('android');
      const channel: AndroidChannel = {
        id: 'channel-id',
        name: 'channel',
      };

      const res = await apiModule.isChannelCreated(channel.id);

      expect(res).toBe(true);
      expect(mockNotifeeNativeModule.createChannel).not.toBeCalled();
    });
  });

  describe('getNotificationSettings', () => {
    describe('on Android', () => {
      beforeEach(() => {
        setPlatform('android');
      });

      test('return alert 1 with the rest set to default values', async () => {
        mockNotifeeNativeModule.getNotificationSettings.mockResolvedValue({
          alert: 1,
        });
        const settings = await apiModule.getNotificationSettings();
        expect(settings).toEqual({
          alert: 1,
          badge: 1,
          criticalAlert: 1,
          showPreviews: 1,
          sound: 1,
          carPlay: 1,
          lockScreen: 1,
          announcement: 1,
          notificationCenter: 1,
          inAppNotificationSettings: 1,
          authorizationStatus: 1,
        });
      });

      test('return alert 0 with the rest set to default data', async () => {
        mockNotifeeNativeModule.getNotificationSettings.mockResolvedValue({
          alert: 0,
        });
        const settings = await apiModule.getNotificationSettings();
        expect(settings).toEqual({
          alert: 0,
          badge: 1,
          criticalAlert: 1,
          showPreviews: 1,
          sound: 1,
          carPlay: 1,
          lockScreen: 1,
          announcement: 1,
          notificationCenter: 1,
          inAppNotificationSettings: 1,
          authorizationStatus: 1,
        });
      });
    });
  });
});
