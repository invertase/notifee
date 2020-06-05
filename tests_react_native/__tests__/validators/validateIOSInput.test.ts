import validateIOSInput from '@notifee/react-native/src/validators/validateIOSInput';
import { IOSInput } from '@notifee/react-native/src/types/NotificationIOS';

describe('Validate IOS Input', () => {
  describe('validateIOSInput()', () => {
    test('returns valid ', () => {
      const input: IOSInput = {
        buttonText: 'buttonText',
        placeholderText: 'placeholderText',
      };

      const $ = validateIOSInput(input);
      expect($.buttonText).toEqual('buttonText');
      expect($.placeholderText).toEqual('placeholderText');
    });

    test('returns valid when no value is provided', () => {
      const $ = validateIOSInput();
      expect($).toEqual({});
    });

    test('returns valid when a boolean value is provided', () => {
      const $ = validateIOSInput(false as any);
      expect($).toEqual({});
    });

    test('throws an error with an invalid buttonText param', () => {
      const input: IOSInput = {
        buttonText: [] as any,
        placeholderText: 'placeholderText',
      };

      expect(() => validateIOSInput(input)).toThrowError("'buttonText' expected a string value.");
    });

    test('throws an error with an invalid placeholderText param', () => {
      const input: IOSInput = {
        buttonText: 'buttonText',
        placeholderText: [] as any,
      };

      expect(() => validateIOSInput(input)).toThrowError(
        "'placeholderText' expected a string value.",
      );
    });
  });
});
