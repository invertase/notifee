import validateIOSPermissions from '@notifee/react-native/src/validators/validateIOSPermissions';
import { IOSNotificationPermissions } from '@notifee/react-native/src/types/NotificationIOS';

describe('Validate IOS Input', () => {
  describe('validateIOSInput()', () => {
    test('returns valid ', () => {
      const permissions: IOSNotificationPermissions = {
        alert: false,
        criticalAlert: false,
        badge: false,
        sound: false,
        carPlay: false,
        provisional: false,
        announcement: false,
      };

      const $ = validateIOSPermissions(permissions);

      expect($.alert).toEqual(false);
      expect($.criticalAlert).toEqual(false);
      expect($.badge).toEqual(false);
      expect($.sound).toEqual(false);
      expect($.carPlay).toEqual(false);
      expect($.provisional).toEqual(false);
      expect($.announcement).toEqual(false);
    });

    test('returns valid when no value is provided', () => {
      const $ = validateIOSPermissions(null as any);

      expect($.alert).toEqual(true);
      expect($.criticalAlert).toEqual(false);
      expect($.badge).toEqual(true);
      expect($.sound).toEqual(true);
      expect($.carPlay).toEqual(true);
      expect($.provisional).toEqual(false);
      expect($.announcement).toEqual(false);
    });

    test('returns invalid when an invalid critical property is provided', () => {
      const notification: IOSNotificationPermissions = {
        alert: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'alert' expected a boolean value.",
      );
    });

    test('returns invalid when an invalid alert property is provided', () => {
      const notification: IOSNotificationPermissions = {
        alert: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'alert' expected a boolean value.",
      );
    });

    test('returns invalid when an invalid sound property is provided', () => {
      const notification: IOSNotificationPermissions = {
        sound: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'sound' expected a boolean value.",
      );
    });

    test('returns invalid when an invalid carPlay property is provided', () => {
      const notification: IOSNotificationPermissions = {
        carPlay: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'carPlay' expected a boolean value.",
      );
    });

    test('returns invalid when an invalid provisional property is provided', () => {
      const notification: IOSNotificationPermissions = {
        provisional: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'provisional' expected a boolean value.",
      );
    });

    test('returns invalid when an invalid announcement property is provided', () => {
      const notification: IOSNotificationPermissions = {
        announcement: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'announcement' expected a boolean value.",
      );
    });

    test('returns invalid when an invalid criticalAlert property is provided', () => {
      const notification: IOSNotificationPermissions = {
        criticalAlert: [] as any,
      };

      expect(() => validateIOSPermissions(notification)).toThrowError(
        "'criticalAlert' expected a boolean value.",
      );
    });
  });
});
