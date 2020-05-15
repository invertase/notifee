import validateAndroidInput from '@notifee/react-native/src/validators/validateAndroidInput';
import { AndroidInput } from '@notifee/react-native/src/types/NotificationAndroid';

describe('Validate Android Channel Group', () => {
  describe('validateAndroidChannelGroup()', () => {
    test('returns valid ', () => {
      const androidInput: AndroidInput = {
        allowFreeFormInput: true,
        allowGeneratedReplies: false,
        choices: ['choices'],
        editableChoices: false,
        placeholder: 'placeholder',
      };

      const $ = validateAndroidInput(androidInput);
      expect($.allowFreeFormInput).toEqual(true);
      expect($.allowGeneratedReplies).toEqual(false);
      expect($.choices).toEqual(['choices']);
      expect($.editableChoices).toEqual(false);
      expect($.placeholder).toEqual('placeholder');
    });

    test('throws an error with an invalid allowGeneratedReplies ', () => {
      const androidInput: AndroidInput = {
        allowGeneratedReplies: [] as any,
      };
      expect(() => validateAndroidInput(androidInput)).toThrowError(
        "'input.allowGeneratedReplies' expected a boolean value.",
      );
    });

    test('throws an error with an invalid choices ', () => {
      const androidInput: AndroidInput = {
        choices: {} as any,
      };

      expect(() => validateAndroidInput(androidInput)).toThrowError(
        "'input.choices' expected an array of string values.",
      );
    });

    test('throws an error with an invalid editableChoices ', () => {
      const androidInput: AndroidInput = {
        editableChoices: [] as any,
      };

      expect(() => validateAndroidInput(androidInput)).toThrowError(
        "'input.editableChoices' expected a boolean value.",
      );
    });

    test('throws an error with an invalid placeholder ', () => {
      const androidInput: AndroidInput = {
        placeholder: [] as any,
      };

      expect(() => validateAndroidInput(androidInput)).toThrowError(
        "'input.placeholder' expected a string value.",
      );
    });

    test('throws an error with a free form input without any choices included', () => {
      const androidInput: AndroidInput = {
        allowFreeFormInput: false,
        choices: [] as any,
      };

      expect(() => validateAndroidInput(androidInput)).toThrowError(
        "'input.allowFreeFormInput' when false, you must provide at least one choice.",
      );
    });

    test('throws an error when editable choice is active without allowFreeFormInput as active', () => {
      const androidInput: AndroidInput = {
        editableChoices: true,
        allowFreeFormInput: false,
        choices: ['test'],
      };

      expect(() => validateAndroidInput(androidInput)).toThrowError(
        "'input.editableChoices' when true, allowFreeFormInput must also be true.",
      );
    });
  });
});
