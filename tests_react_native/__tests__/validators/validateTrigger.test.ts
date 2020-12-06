import validateNotification from '@notifee/react-native/src/validators/validateNotification';
import { Notification } from '@notifee/react-native/src/types/Notification';

describe('Validate Trigger', () => {
  describe('validateTrigger()', () => {
    test('throws error if value is not an object', () => {
        expect(() => validateTrigger(null)).toThrowError(
        "'trigger' expected an object value.",
      );

       expect(() => validateTrigger(undefined)).toThrowError(
        "'trigger' expected an object value.",
      );

      expect(() => validateTrigger('string')).toThrowError(
        "'trigger' expected an object value.",
      );

        expect(() => validateTrigger(1)).toThrowError(
        "'trigger' expected an object value.",
      );
    });

    test('throws an error if trigger type is unknown', () => {
      const trigger: Trigger = {
       type: -1,
      };

      expect(() => validateTrigger(trigger)).toThrowError(
        'Unknown trigger type.',
      );
    });

     describe('validateTimestampTrigger()', () => {
      test('throws error if timestamp is invalid', () => {
        let trigger: TimestampTrigger = {
          timestamp: null,
        };

             expect(() => validateTrigger(trigger)).toThrowError(
        "trigger.timestamp' expected a number value.",
      );
  trigger: TimestampTrigger = {
          timestamp: '',
        };
        expect(() => validateTrigger(trigger)).toThrowError(
        "trigger.timestamp' expected a number value.",
      );

          });
     });



    test('throws error when timestamp is in the past', () => {
      const date = new Date(Date.now());
      const trigger: TimestampTrigger = {
        timestamp: date.getTime(),
      };

      expect(() => validateTrigger(trigger)).toThrowError(
        "'trigger.timestamp' date must be in the future.",
      );
    });

    test('repeatFrequency defaults to -1 if not set', () => {
        const date = new Date(Date.now());
     const trigger: TimestampTrigger = {
        timestamp: date.getTime(),
      };

      const $ = validateTrigger(trigger);

       expect($.repeatFrequency).toEqual(-1);
    });

    test('throws error if repeatFrequency is invalid', () => {
        const date = new Date(Date.now());
     const trigger: TimestampTrigger = {
        timestamp: date.getTime(),
repeatFrequency: 2
      };

      expect(() => validateNotification(notification)).toThrowError(
       "'trigger.repeatFrequency' expected a RepeatFrequency value.",
      );
    });

        test('returns a valid timestamp trigger object', () => {
        const date = new Date(Date.now());
     const trigger: TimestampTrigger = {
        timestamp: date.getTime(),
repeatFrequency: 2
      };

    
    });
  });
});
