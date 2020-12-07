import validateAndroidChannel from '@notifee/react-native/src/validators/validateAndroidChannel';
import {
  AndroidChannel,
  AndroidVisibility,
} from '@notifee/react-native/src/types/NotificationAndroid';
import { AndroidImportance } from '@notifee/react-native/src/types/NotificationAndroid';

describe('Validate Android Channel', () => {
  describe('validateAndroidChannel()', () => {
    test('returns valid ', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        description: 'description',
        lightColor: 'blue',
        groupId: 'groupId',
        bypassDnd: false,
        lights: true,
        sound: 'sound',
        vibration: true,
        badge: true,
        importance: AndroidImportance.DEFAULT,
        visibility: AndroidVisibility.PRIVATE,
        vibrationPattern: [],
      };

      const $ = validateAndroidChannel(channel);
      expect($.id).toEqual('id');
      expect($.name).toEqual('name');
      expect($.description).toEqual('description');
      expect($.lightColor).toEqual('blue');
      expect($.bypassDnd).toEqual(false);
      expect($.lights).toEqual(true);
      expect($.vibration).toEqual(true);
      expect($.vibrationPattern).toEqual([]);
      expect($.importance).toEqual(AndroidImportance.DEFAULT);
      expect($.visibility).toEqual(AndroidVisibility.PRIVATE);
    });

    test('throws an error with an invalid channel ', () => {
      expect(() => validateAndroidChannel([] as any)).toThrowError(
        "'channel' expected an object value.",
      );
    });

    test('throws an error with an invalid id ', () => {
      let channel: AndroidChannel = {
        id: 0 as any,
        name: 'name',
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.id' expected a string value.",
      );

      channel = {
        id: '' as any,
        name: 'name',
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.id' expected a valid string id.",
      );
    });

    test('throws an error with an invalid name', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 0 as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.name' expected a string value.",
      );
    });

    test('throws an error with an invalid name ', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: '' as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.name' expected a valid channel name.",
      );
    });

    test('throws an error with a non boolean badge', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        badge: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.badge' expected a boolean value.",
      );
    });

    test('throws an error with a non boolean bypassDnd', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        bypassDnd: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.bypassDnd' expected a boolean value.",
      );
    });

    test('throws an error with a non string description', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        description: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.description' expected a string value.",
      );
    });

    test('throws an error with a non boolean lights property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        lights: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.lights' expected a boolean value.",
      );
    });

    test('throws an error with a non boolean vibration property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        vibration: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.vibration' expected a boolean value.",
      );
    });

    test('throws an error with a non string groupId property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        groupId: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.groupId' expected a string value.",
      );
    });

    test('throws an error with an invalid importance property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        importance: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.importance' expected an Importance value.",
      );
    });

    test('throws an error with a non string lightColor property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        lightColor: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.lightColor' expected a string value.",
      );
    });

    test('throws an error with an invalid lightColor property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        lightColor: 'unknown',
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.lightColor' invalid color. Expected an AndroidColor or hexadecimal string value",
      );
    });

    test('throws an error with an invalid visability property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        visibility: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.visibility' expected visibility to be an AndroidVisibility value.",
      );
    });

    test('throws an error with a non string sound property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        sound: [] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.sound' expected a string value.",
      );
    });

    test('throws an error with an non array based vibrationPattern property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        vibrationPattern: {} as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.vibrationPattern' expected an array.",
      );
    });

    test('throws an error with an non even array vibrationPattern property', () => {
      const channel: AndroidChannel = {
        id: 'id',
        name: 'name',
        vibrationPattern: ['test'] as any,
      };

      expect(() => validateAndroidChannel(channel)).toThrowError(
        "'channel.vibrationPattern' expected an array containing an even number of positive values.",
      );
    });
  });
});
