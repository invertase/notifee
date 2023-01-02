import validateIOSNotification from '@notifee/react-native/src/validators/validateIOSNotification';
import { NotificationIOS } from '@notifee/react-native/src/types/NotificationIOS';

describe('Validate IOS Notification', () => {
  describe('validateIOSNotification()', () => {
    test('returns valid ', () => {
      const notification: NotificationIOS = {
        attachments: [],
        badgeCount: 0,
        categoryId: 'categoryId',
        launchImageName: 'launchImageName',
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
      expect($.sound).toEqual('placeholderText');
      expect($.critical).toEqual(true);
      expect($.criticalVolume).toEqual(0);
      expect($.threadId).toEqual('threadId');
      expect($.summaryArgument).toEqual('summaryArgument');
      expect($.summaryArgumentCount).toEqual(1);
      //   expect($.targetContentId).toEqual('targetContentId');
    });

    test('returns valid when no value is provided', () => {
      const $ = validateIOSNotification();
      expect($).toEqual({
        foregroundPresentationOptions: {
          alert: true,
          badge: true,
          sound: true,
          banner: true,
          list: true,
        },
      });
    });

    test('returns valid when there is a foregroundPresentationOptions', () => {
      const $ = validateIOSNotification({
        foregroundPresentationOptions: {
          alert: true,
          badge: true,
          sound: true,
          banner: true,
          list: true,
        },
      });
      expect($).toEqual({
        foregroundPresentationOptions: {
          alert: true,
          badge: true,
          sound: true,
          banner: true,
          list: true,
        },
      });
    });

    test('returns valid when there is a valid communicationInfo property', () => {
      const $ = validateIOSNotification({
        communicationInfo: {
          conversationId: 'id',
          sender: {
            id: 'sender-id',
            displayName: 'John Doe',
          },
        },
      });
      expect($).toEqual({
        communicationInfo: {
          conversationId: 'id',
          sender: {
            id: 'sender-id',
            displayName: 'John Doe',
          },
        },
        foregroundPresentationOptions: {
          alert: true,
          badge: true,
          sound: true,
          banner: true,
          list: true,
        },
      });
    });

    test('returns invalid when an invalid critical property is provided', () => {
      const notification: NotificationIOS = {
        critical: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.critical' must be a boolean value if specified.",
      );
    });

    test('returns invalid when an invalid criticalVolume property is provided', () => {
      const notification: NotificationIOS = {
        criticalVolume: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.criticalVolume' must be a number value if specified.",
      );
    });

    test('returns invalid when an invalid sound property is provided', () => {
      let notification: NotificationIOS = {
        sound: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.sound' must be a string value if specified.",
      );

      notification = {
        sound: [] as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.sound' must be a string value if specified.",
      );
    });

    test('returns invalid when an invalid badgeCount property is provided', () => {
      const notification: NotificationIOS = {
        badgeCount: [] as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.badgeCount' expected a number value >=0.",
      );
    });

    test('returns invalid when an invalid categoryId property is provided', () => {
      const notification: NotificationIOS = {
        categoryId: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.categoryId' expected a of string value",
      );
    });

    test('returns invalid when an invalid threadId property is provided', () => {
      const notification: NotificationIOS = {
        threadId: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.threadId' expected a string value.",
      );
    });

    test('returns invalid when an invalid summaryArgument property is provided', () => {
      const notification: NotificationIOS = {
        summaryArgument: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.summaryArgument' expected a string value.",
      );
    });

    test('returns invalid when an invalid launchImageName property is provided', () => {
      const notification: NotificationIOS = {
        launchImageName: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'notification.ios.launchImageName' expected a string value.",
      );
    });

    test('returns invalid when an invalid communicationInfo property is provided', () => {
      let notification: NotificationIOS = {
        communicationInfo: '' as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'ios.communicationInfo' expected an object.",
      );

      notification = {
        communicationInfo: {} as any,
      };

      expect(() => validateIOSNotification(notification)).toThrowError(
        "'ios.communicationInfo' 'conversationId' expected a valid string value.",
      );
    });
  });
});
