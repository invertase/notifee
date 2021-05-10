import { NotificationFullScreenAction } from '@notifee/react-native/src/types/Notification';
import validateAndroidFullScreenAction from '../../../packages/react-native/src/validators/validateAndroidFullScreenAction';

describe('Validate Android Full-screen Action', () => {
  describe('validateAndroidFullScreenAction()', () => {
    test('returns valid ', () => {
      const action: NotificationFullScreenAction = {
        id: 'id',
        launchActivity: 'launchActivity',
        mainComponent: 'mainComponent',
      };

      const $ = validateAndroidFullScreenAction(action);
      expect($.id).toEqual('id');
      expect($.launchActivity).toEqual('launchActivity');
      expect($.mainComponent).toEqual('mainComponent');
    });

    test('throws an error with an invalid param', () => {
      expect(() => validateAndroidFullScreenAction([] as any)).toThrowError(
        "'fullScreenAction' expected an object value.",
      );
    });

    test('throws an error with an invalid id', () => {
      const action: NotificationFullScreenAction = {
        id: [] as any,
      };

      expect(() => validateAndroidFullScreenAction(action)).toThrowError(
        "'id' expected a non-empty string value.",
      );
    });

    test('throws an error with an invalid launch activity', () => {
      const action: NotificationFullScreenAction = {
        id: 'id',
        launchActivity: {} as any,
      };

      expect(() => validateAndroidFullScreenAction(action)).toThrowError(
        "'launchActivity' expected a string value.",
      );
    });

    test('throws an error with an invalid launch activity flags', () => {
      const action: any = {
        id: 'id',
        launchActivity: 'a-launch-activity',
        launchActivityFlags: '' as any,
      };

      expect(() => validateAndroidFullScreenAction(action)).toThrowError(
        "'launchActivityFlags' must be an array of `AndroidLaunchActivityFlag` values.",
      );
    });

    test('throws an error with an invalid main component', () => {
      const action: NotificationFullScreenAction = {
        id: 'id',
        mainComponent: {} as any,
      };

      expect(() => validateAndroidFullScreenAction(action)).toThrowError(
        "'mainComponent' expected a string value.",
      );
    });
  });
});
