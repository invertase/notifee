import validateNotification from '@notifee/react-native/src/validators/validateNotification';
import { Notification } from '@notifee/react-native/src/types/Notification';

describe('Validate Notification', () => {
  describe('validateNotification()', () => {
    test('returns valid android', async () => {
      const permissions: Notification = {
        id: 'id',
        title: 'title',
        subtitle: 'subtitle',
        body: 'body',
        data: { test: 'test' },

        android: {
          channelId: 'channelId',
        },
      };

      const $ = validateNotification(permissions);

      expect($.id).toEqual('id');
      expect($.title).toEqual('title');
      expect($.subtitle).toEqual('subtitle');
      expect($.body).toEqual('body');
      expect($.data).toEqual({ test: 'test' });

      // TODO Figure out why IOS / Android returns undefined

      // expect($.android).toEqual({ channelId: 'channelId' });
      // expect($.ios).toEqual({});
    });

    test('returns valid ios', async () => {
      const permissions: Notification = {
        id: 'id',
        title: 'title',
        subtitle: 'subtitle',
        body: 'body',
        data: { test: 'test' },
        ios: {},
      };

      const $ = validateNotification(permissions);

      expect($.id).toEqual('id');
      expect($.title).toEqual('title');
      expect($.subtitle).toEqual('subtitle');
      expect($.body).toEqual('body');
      expect($.data).toEqual({ test: 'test' });

      // TODO Figure out why IOS / Android returns undefined

      // expect($.android).toEqual({ channelId: 'channelId' });
      // expect($.ios).toEqual({});
    });

    test('returns valid when no value is provided', async () => {
      const $ = validateNotification({} as any);

      expect($.id).toBeDefined();
      expect($.title).toEqual('');
      expect($.subtitle).toEqual('');
      expect($.body).toEqual('');
      expect($.data).toEqual({});
    });

    test('returns invalid when an invalid id property is provided', async () => {
      const notification: Notification = {
        id: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.id' invalid notification ID, expected a unique string value.",
      );
    });

    test('returns invalid when an invalid title property is provided', async () => {
      const notification: Notification = {
        title: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.title' expected a string value.",
      );
    });

    test('returns invalid when an invalid body property is provided', async () => {
      const notification: Notification = {
        body: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.body' expected a string value.",
      );
    });

    test('returns invalid when an invalid subtitle property is provided', async () => {
      const notification: Notification = {
        subtitle: null as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.subtitle' expected a string value.",
      );
    });

    test('returns invalid when an invalid data property is provided', async () => {
      const notification: Notification = {
        data: [] as any,
      };

      expect(() => validateNotification(notification)).toThrowError(
        "'notification.data' expected an object value containing key/value pairs.",
      );
    });

    test('returns invalid when an invalid data properties are provided', async () => {
      const notification: Notification = {
        data: { id: 0 as any },
      };

      expect(() => validateNotification(notification)).toThrowError(
        '\'notification.data\' value for key "id" is invalid, expected a string value.',
      );
    });
  });
});
