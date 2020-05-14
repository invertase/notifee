import validateIOSNotification from '@notifee/react-native/src/validators/validateIOSNotification';
import { NotificationIOS } from '@notifee/react-native/src/types/NotificationIOS';

import { Importance } from '@notifee/react-native/src/types/Notification';

describe('Validate IOS Input', () => {
  describe('validateIOSInput()', () => {
    test('returns valid ', async () => {
      const notification: NotificationIOS = {
        attachments: [],
        badgeCount: 0,
        categoryId: 'categoryId',
        launchImageName: 'launchImageName',
        importance: Importance.NONE,
        sound: 'placeholderText',
        critical: true,
        criticalVolume: 0,
        threadId: 'threadId',
        summaryArgument: 'summaryArgument',
        summaryArgumentCount: 1,
        targetContentId: 'targetContentId',
      };

      const $ = validateIOSNotification(notification);
      //   expect($.attachments).toEqual([]);
      expect($.badgeCount).toEqual(0);
      expect($.categoryId).toEqual('categoryId');
      expect($.launchImageName).toEqual('launchImageName');
      expect($.importance).toEqual(Importance.NONE);
      expect($.sound).toEqual('placeholderText');
      expect($.critical).toEqual(true);
      expect($.criticalVolume).toEqual(0);
      expect($.threadId).toEqual('threadId');
      expect($.summaryArgument).toEqual('summaryArgument');
      expect($.summaryArgumentCount).toEqual(1);
      //   expect($.targetContentId).toEqual('targetContentId');
    });

    test('returns valid when no value is provided', async () => {
      const $ = validateIOSNotification();
      expect($).toEqual({ importance: Importance.DEFAULT });
    });

    test('returns invalid when an invalid critical property is provided', async () => {
      const notification: NotificationIOS = {
        critical: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.critical' must be a boolean value if specified.",
      );
    });

    test('returns invalid when an invalid criticalVolume property is provided', async () => {
      const notification: NotificationIOS = {
        criticalVolume: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.criticalVolume' must be a number value if specified.",
      );
    });

    test('returns invalid when an invalid sound property is provided', async () => {
      const notification: NotificationIOS = {
        sound: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.sound' must be a string value if specified.",
      );
    });

    test('returns invalid when an invalid badgeCount property is provided', async () => {
      const notification: NotificationIOS = {
        badgeCount: [] as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.badgeCount' expected a number value >=0.",
      );
    });

    test('returns invalid when an invalid categoryId property is provided', async () => {
      const notification: NotificationIOS = {
        categoryId: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.categoryId' expected a of string value",
      );
    });

    test('returns invalid when an invalid threadId property is provided', async () => {
      const notification: NotificationIOS = {
        threadId: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.threadId' expected a string value.",
      );
    });

    test('returns invalid when an invalid summaryArgument property is provided', async () => {
      const notification: NotificationIOS = {
        summaryArgument: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.summaryArgument' expected a string value.",
      );
    });

    test('returns invalid when an invalid launchImageName property is provided', async () => {
      const notification: NotificationIOS = {
        launchImageName: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.launchImageName' expected a string value.",
      );
    });

    test('returns invalid when an invalid importance property is provided', async () => {
      const notification: NotificationIOS = {
        importance: ['test'] as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.importance' expected a valid Importance.",
      );
    });

    test('returns invalid when an invalid sound property is provided', async () => {
      const notification: NotificationIOS = {
        sound: [] as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.sound' must be a string value if specified.",
      );
    });
  });
});
