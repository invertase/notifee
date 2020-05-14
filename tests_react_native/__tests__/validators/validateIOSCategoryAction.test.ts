import validateIOSCategoryAction from '@notifee/react-native/src/validators/validateIOSCategoryAction';
import { IOSNotificationCategoryAction } from '@notifee/react-native/src/types/NotificationIOS';

describe('Validate IOS Catgeory Action', () => {
  describe('validateIOSCategoryAction()', () => {
    test('returns valid ', () => {
      const categoryAction: IOSNotificationCategoryAction = {
        id: 'id',
        title: 'title',
        input: true,
      };

      const $ = validateIOSCategoryAction(categoryAction);
      expect($.id).toEqual('id');
      expect($.title).toEqual('title');
      expect($.input).toEqual(true);
    });

    test('throws an error with an invalid input param', () => {
      const categoryAction: IOSNotificationCategoryAction = {
        id: 'id',
        title: 'title',
        input: [] as any,
      };

      expect(() => validateIOSCategoryAction(categoryAction)).toThrowError(
        "'action' expected an object value..",
      );
    });

    test('throws an error with an invalid destructive param', () => {
      const categoryAction: IOSNotificationCategoryAction = {
        id: 'id',
        title: 'title',
        destructive: [] as any,
      };

      expect(() => validateIOSCategoryAction(categoryAction)).toThrowError(
        "'destructive' expected a boolean value.",
      );
    });

    test('throws an error with an invalid foreground param', () => {
      const categoryAction: IOSNotificationCategoryAction = {
        id: 'id',
        title: 'title',
        foreground: [] as any,
      };

      expect(() => validateIOSCategoryAction(categoryAction)).toThrowError(
        "'foreground' expected a boolean value.",
      );
    });

    test('throws an error with an invalid authenticationRequired param', () => {
      const categoryAction: IOSNotificationCategoryAction = {
        id: 'id',
        title: 'title',
        authenticationRequired: [] as any,
      };

      expect(() => validateIOSCategoryAction(categoryAction)).toThrowError(
        "'authenticationRequired' expected a boolean value.",
      );
    });
  });
});
