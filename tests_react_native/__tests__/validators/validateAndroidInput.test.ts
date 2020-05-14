import validateAndroidInput from '@notifee/react-native/src/validators/validateAndroidInput';
import { AndroidInput } from '@notifee/react-native/src/types/NotificationAndroid';

describe('Validate Android Channel Group', () => {
  describe('validateAndroidChannelGroup()', () => {
    test('returns valid ', () => {
      const androidInput: AndroidInput = {
        allowFreeFormInput: false,
        allowGeneratedReplies: false,
        choices: ['choices'],
        editableChoices: false,
        placeholder: 'placeholder',
      };

      const $ = validateAndroidInput(androidInput);
      expect($.allowFreeFormInput).toEqual(false);
      expect($.allowGeneratedReplies).toEqual(false);
      expect($.choices).toEqual(['choices']);
      expect($.editableChoices).toEqual(false);
      expect($.placeholder).toEqual('placeholder');
    });

    test('throws an error with an invalid input ', () => {
      const $ = validateAndroidInput();

      expect($.allowFreeFormInput).toEqual(true);
      expect($.allowFreeFormInput).toEqual(true);
    });
  });
});
