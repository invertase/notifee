import validateAndroidAction from '@notifee/react-native/src/validators/validateAndroidAction';
import { AndroidAction } from '@notifee/react-native/src/types/NotificationAndroid';

describe('Validate Android Action', () => {
  describe('validateAndroidAction()', () => {
    test.concurrent('returns valid ', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id' },
        title: 'title',
      };

      const { pressAction, title } = validateAndroidAction(action);
      expect(pressAction).toEqual({ id: 'id' });
      expect(title).toEqual('title');
    });

    test.concurrent('returns invalid if not a valid object ', async () => {
      expect(() => validateAndroidAction([] as any)).toThrowError(
        "'action' expected an object value.",
      );
    });

    test.concurrent('returns invalid if pressAction not a valid object ', async () => {
      const action: AndroidAction = {
        pressAction: [] as any,
        title: 'title',
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'action' 'pressAction' expected an object value..",
      );
    });

    test.concurrent('returns invalid if id not a valid string ', async () => {
      const action: AndroidAction = {
        pressAction: { id: 0 as any },
        title: 'title',
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'action' 'id' expected a non-empty string value..",
      );
    });

    test.concurrent('returns valid with launch activity ', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id', launchActivity: 'launchActivity' },
        title: 'title',
      };

      const { pressAction, title } = validateAndroidAction(action);
      expect(pressAction).toEqual({ id: 'id', launchActivity: 'launchActivity' });
      expect(title).toEqual('title');
    });

    test.concurrent('returns invalid with non string based launch activity', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id', launchActivity: 0 as any },
        title: 'title',
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'launchActivity' expected a string value.",
      );
    });

    test.concurrent('returns valid with main component', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id', mainComponent: 'mainComponent' },
        title: 'title',
      };

      const { pressAction, title } = validateAndroidAction(action);
      expect(pressAction).toEqual({ id: 'id', mainComponent: 'mainComponent' });
      expect(title).toEqual('title');
    });

    test.concurrent('returns invalid with non string based main component', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id', mainComponent: 0 as any },
        title: 'title',
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'mainComponent' expected a string value.",
      );
    });

    test.concurrent('returns valid with action icon', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id' },
        title: 'title',
        icon: 'icon',
      };

      const { pressAction, icon, title } = validateAndroidAction(action);
      expect(pressAction).toEqual({ id: 'id' });
      expect(icon).toEqual(icon);
      expect(title).toEqual('title');
    });

    test.concurrent('returns invalid with non string based icon', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id' },
        title: 'title',
        icon: 0 as any,
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'action.icon' expected a string value.",
      );
    });
    test.concurrent('returns valid with action input', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id' },
        title: 'title',
        input: true,
      };

      const { pressAction, icon, input } = validateAndroidAction(action);
      expect(pressAction).toEqual({ id: 'id' });
      expect(icon).toEqual(icon);
      expect(input).toEqual({ allowFreeFormInput: true, allowGeneratedReplies: true });
    });

    test.concurrent('throws an error with invalid input', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id' },
        title: [] as any,
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'action.title' expected a string value.",
      );
    });

    test.concurrent('throws an error with invalid input', async () => {
      const action: AndroidAction = {
        pressAction: { id: 'id' },
        title: 'title',
        input: { allowFreeFormInput: [] } as any,
      };

      expect(() => validateAndroidAction(action)).toThrowError(
        "'action.input' 'input.allowFreeFormInput' expected a boolean value..",
      );
    });
  });
});
