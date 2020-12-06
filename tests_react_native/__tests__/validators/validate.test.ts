import {
  isValidColor,
  isValidLightPattern,
  isValidVibratePattern,
  isValidTimestamp,
} from '@notifee/react-native/src/validators/validate';

describe('Validate', () => {
  describe('isValidColor', () => {
    test('returns valid with a valid text color', () => {
      expect(isValidColor('blue')).toBe(true);
    });

    test('returns invalid with a invalid text color', () => {
      expect(isValidColor('foobar')).toBe(false);
    });

    test('returns invalid with an invalid hex format', () => {
      expect(isValidColor('2d3748')).toBe(false);
    });

    test('returns valid with a hex value and 6 chars', () => {
      expect(isValidColor('#2d374')).toBe(false);
    });

    test('returns valid with a hex value and 8 chars', () => {
      expect(isValidColor('#2d37489')).toBe(false);
    });

    test('returns invalid with a hex value without a 6/8 char length', () => {
      expect(isValidColor('#2d3')).toBe(false);
    });
  });

  describe('isValidLightPattern()', () => {
    test('returns valid', () => {
      const [type] = isValidLightPattern(['blue', 1, 1]);

      expect(type).toEqual(true);
    });

    test('returns invalid with an invalid text color', () => {
      const [type, value] = isValidLightPattern(['foobar', 0, 0]);
      expect(type).toEqual(false);
      expect(value).toEqual('color');
    });

    test('returns invalid with an invalid onMs number', () => {
      const [type, value] = isValidLightPattern(['blue', 'foobar' as any, 0]);

      expect(type).toEqual(false);
      expect(value).toEqual('onMs');
    });

    test('returns invalid with an invalid offMs number', () => {
      const [type, value] = isValidLightPattern(['blue', 0, 'foobar' as any]);

      expect(type).toEqual(false);
      expect(value).toEqual('offMs');
    });

    test('returns invalid with an onMs number less than one', () => {
      const [type, value] = isValidLightPattern(['blue', 0, 0]);

      expect(type).toEqual(false);
      expect(value).toEqual('onMs');
    });

    test('returns invalid with an offMs number less than one', () => {
      const [type, value] = isValidLightPattern(['blue', 1, 0]);

      expect(type).toEqual(false);
      expect(value).toEqual('offMs');
    });
  });

  describe('isValidTimestamp()', () => {
    test('returns valid', () => {
      expect(isValidTimestamp(1589270618)).toEqual(true);
    });

    test('returns invalid', () => {
      expect(isValidTimestamp(0)).toEqual(false);
    });
  });

  describe('isValidVibratePattern()', () => {
    test('returns valid', () => {
      expect(isValidVibratePattern([200, 200])).toEqual(true);
    });

    test('returns invalid with non a non numeric value', () => {
      expect(isValidVibratePattern([200, '200' as any])).toEqual(false);
    });

    test('returns invalid with an even number of options', () => {
      expect(isValidVibratePattern([200])).toEqual(false);
    });

    test('returns invalid with a negative option', () => {
      expect(isValidVibratePattern([200, -1])).toEqual(false);
    });
  });
});
