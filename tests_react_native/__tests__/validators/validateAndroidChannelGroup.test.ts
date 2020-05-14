import validateAndroidChannelGroup from '@notifee/react-native/src/validators/validateAndroidChannelGroup';
import { AndroidChannelGroup } from '@notifee/react-native/src/types/NotificationAndroid';

describe('Validate Android Channel Group', () => {
  describe('validateAndroidChannelGroup()', () => {
    test('returns valid ', () => {
      const channelGroup: AndroidChannelGroup = {
        id: 'id',
        name: 'name',
        description: 'description',
      };

      const $ = validateAndroidChannelGroup(channelGroup);
      expect($.id).toEqual('id');
      expect($.name).toEqual('name');
      expect($.description).toEqual('description');
    });

    test('throws an error with an invalid channel ', () => {
      expect(() => validateAndroidChannelGroup([] as any)).toThrowError(
        "'group' expected an object value.",
      );
    });

    test('throws an error with an invalid id ', () => {
      const channelGroup: AndroidChannelGroup = {
        id: [] as any,
        name: 'name',
        description: 'description',
      };

      expect(() => validateAndroidChannelGroup(channelGroup)).toThrowError(
        "'group.id' expected a string value.",
      );
    });

    test('throws an error with an invalid name ', () => {
      const channelGroup: AndroidChannelGroup = {
        id: 'id',
        name: [] as any,
        description: 'description',
      };

      expect(() => validateAndroidChannelGroup(channelGroup)).toThrowError(
        "'group.name' expected a string value.",
      );
    });

    test('throws an error with an invalid description ', () => {
      const channelGroup: AndroidChannelGroup = {
        id: 'id',
        name: 'name',
        description: [] as any,
      };

      expect(() => validateAndroidChannelGroup(channelGroup)).toThrowError(
        "'group.description' expected a string value.",
      );
    });
  });
});
