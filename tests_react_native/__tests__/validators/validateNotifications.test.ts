import validateNotification from '@notifee/react-native/src/validators/validateNotification';
import { Notification } from '@notifee/react-native/src/types/Notification';
import { setPlatform } from '../testSetup';

describe('Validate Notification', () => {
  describe('validateNotification()', () => {
    test('returns valid android', () => {
      setPlatform('android');
      const notification: Notification = {
        id: 'id',
        title: 'title',
        subtitle: 'subtitle',
        body: 'body',
        data: { test: 'test' },

        android: {
          channelId: 'channelId',
        },
      };

      const $ = validateNotification(notification);

      expect($.id).toEqual('id');
      expect($.title).toEqual('title');
      expect($.subtitle).toEqual('subtitle');
      expect($.body).toEqual('body');
      expect($.data).toEqual({ test: 'test' });
      expect($.android).toEqual({
        asForegroundService: false,
        lightUpScreen: false,
        autoCancel: true,
        badgeIconType: 2,
        channelId: 'channelId',
        chronometerDirection: 'up',
        circularLargeIcon: false,
        colorized: false,
        defaults: [-1],
        groupAlertBehavior: 0,
        groupSummary: false,
        importance: 3,
        localOnly: false,
        loopSound: false,
        ongoing: false,
        onlyAlertOnce: false,
        showChronometer: false,
        showTimestamp: false,
        smallIcon: 'ic_launcher',
        visibility: 0,
      });
      expect($.ios).toEqual(undefined);
    });

    test('returns valid ios', () => {
      setPlatform('ios');
      const notification: Notification = {
        id: 'id',
        title: 'title',
        subtitle: 'subtitle',
        body: 'body',
        data: { test: 'test' },
        ios: {},
      };

      const $ = validateNotification(notification);

      expect($.id).toEqual('id');
      expect($.title).toEqual('title');
      expect($.subtitle).toEqual('subtitle');
      expect($.body).toEqual('body');
      expect($.data).toEqual({ test: 'test' });
      expect($.android).toEqual(undefined);
      expect($.ios).toEqual({
        foregroundPresentationOptions: {
          alert: true,
          badge: true,
          sound: true,
          banner: true,
          list: true,
        },
      });
    });

    test('returns valid when no value is provided', () => {
      const $ = validateNotification({} as any);

      expect($.id).toBeDefined();
      expect($.title).toEqual(undefined);
      expect($.subtitle).toEqual(undefined);
      expect($.body).toEqual(undefined);
      expect($.data).toEqual({});
    });

    test('returns invalid when an invalid id property is provided', () => {
      const notification: Notification = {
        id: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.id' invalid notification ID, expected a unique string value.",
      );
    });

    test('returns invalid when an invalid title property is provided', () => {
      const notification: Notification = {
        title: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.title' expected a string value or undefined.",
      );
    });

    test('returns invalid when an invalid body property is provided', () => {
      const notification: Notification = {
        body: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.body' expected a string value or undefined.",
      );
    });

    test('returns invalid when an invalid subtitle property is provided', () => {
      const notification: Notification = {
        subtitle: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.subtitle' expected a string value or undefined.",
      );
    });

    test('returns invalid when an invalid data property is provided', () => {
      const notification: Notification = {
        data: [] as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.data' expected an object value containing key/value pairs.",
      );
    });

    test('returns invalid when an invalid data properties are provided', () => {
      const notification: Notification = {
        data: { id: [] as any },
      };

      expect(() => validateNotification(notification)).toThrowError(
        '\'notification.data\' value for key "id" is invalid, expected a string value.',
      );
    });
  });
});
