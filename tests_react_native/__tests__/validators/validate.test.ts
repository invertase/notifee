import {
  isValidColor,
  isValidLightPattern,
  isValidVibratePattern,
  isValidTimestamp,
} from '@notifee/react-native/src/validators/validate';

describe('Validate', () => {
  describe('isValidColor', () => {
    test('returns valid with a valid text color', async () => {
      expect(isValidColor('blue')).toBe(true);
    });

    test('returns invalid with a invalud text color', async () => {
      expect(isValidColor('foobar')).toBe(false);
    });

    test('returns invalid with an invalid hex format', async () => {
      expect(isValidColor('2d3748')).toBe(false);
    });

    test('returns valid with a hex value and 6 chars', async () => {
      expect(isValidColor('#2d374')).toBe(false);
    });

    test('returns valid with a hex value and 8 chars', async () => {
      expect(isValidColor('#2d37489')).toBe(false);
    });

    test('returns invalid with a hex value without a 6/8 char length', async () => {
      expect(isValidColor('#2d3')).toBe(false);
    });
  });

  describe('isValidLightPattern()', () => {
    test('returns valid', async () => {
      const [type] = isValidLightPattern(['blue', 1, 1]);

      expect(type).toEqual(true);
    });

    test('returns invalid with an invalid text color', async () => {
      const [type, value] = isValidLightPattern(['foobar', 0, 0]);
      expect(type).toEqual(false);
      expect(value).toEqual('color');
    });

    test('returns invalid with an invalid onMs number', async () => {
      const [type, value] = isValidLightPattern(['blue', 'foobar' as any, 0]);

      expect(type).toEqual(false);
      expect(value).toEqual('onMs');
    });

    test('returns invalid with an invalid offMs number', async () => {
      const [type, value] = isValidLightPattern(['blue', 0, 'foobar' as any]);

      expect(type).toEqual(false);
      expect(value).toEqual('offMs');
    });

    test('returns invalid with an onMs number less than one', async () => {
      const [type, value] = isValidLightPattern(['blue', 0, 0]);

      expect(type).toEqual(false);
      expect(value).toEqual('onMs');
    });

    test('returns invalid with an offMs number less than one', async () => {
      const [type, value] = isValidLightPattern(['blue', 1, 0]);

      expect(type).toEqual(false);
      expect(value).toEqual('offMs');
    });
  });

  describe('isValidTimestamp()', () => {
    test('returns valid', async () => {
      expect(isValidTimestamp(1589270618)).toEqual(true);
    });

    test('returns invalid', async () => {
      expect(isValidTimestamp(0)).toEqual(false);
    });
  });

  describe('isValidVibratePattern()', () => {
    test('returns valid', async () => {
      expect(isValidVibratePattern([200, 200])).toEqual(true);
    });

    test('returns invalid with non a non numeric value', async () => {
      expect(isValidVibratePattern([200, '200' as any])).toEqual(false);
    });

    test('returns invalid with an even number of options', async () => {
      expect(isValidVibratePattern([200])).toEqual(false);
    });

    test('returns invalid with a negative option', async () => {
      expect(isValidVibratePattern([200, -1])).toEqual(false);
    });
  });
});
