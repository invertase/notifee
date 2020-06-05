import validateAndroidPressAction from '@notifee/react-native/src/validators/validateAndroidPressAction';
import { NotificationPressAction } from '@notifee/react-native/src/types/Notification';

describe('Validate Android Press Action', () => {
  describe('validateAndroidPressAction()', () => {
    test('returns valid ', () => {
      const pressAction: NotificationPressAction = {
        id: 'id',
        launchActivity: 'launchActivity',
        mainComponent: 'mainComponent',
      };

      const $ = validateAndroidPressAction(pressAction);
      expect($.id).toEqual('id');
      expect($.launchActivity).toEqual('launchActivity');
      expect($.mainComponent).toEqual('mainComponent');
    });

    test('throws an error with an invalid param', () => {
      expect(() => validateAndroidPressAction([] as any)).toThrowError(
        "'pressAction' expected an object value.",
      );
    });

    test('throws an error with an invalid id', () => {
      const pressAction: NotificationPressAction = {
        id: [] as any,
      };

      expect(() => validateAndroidPressAction(pressAction)).toThrowError(
        "'id' expected a non-empty string value.",
      );
    });

    test('throws an error with an invalid launch activity', () => {
      const pressAction: NotificationPressAction = {
        id: 'id',
        launchActivity: {} as any,
      };

      expect(() => validateAndroidPressAction(pressAction)).toThrowError(
        "'launchActivity' expected a string value.",
      );
    });

    test('throws an error with an invalid main component', () => {
      const pressAction: NotificationPressAction = {
        id: 'id',
        mainComponent: {} as any,
      };

      expect(() => validateAndroidPressAction(pressAction)).toThrowError(
        "'mainComponent' expected a string value.",
      );
    });
  });
});
