import validateTrigger from '@notifee/react-native/src/validators/validateTrigger';
import {
  Trigger,
  TimestampTrigger,
  TriggerType,
  IntervalTrigger,
  TimeUnit,
} from '@notifee/react-native/src/types/Trigger';

describe('Validate Trigger', () => {
  describe('validateTrigger()', () => {
    test('throws error if value is not an object', () => {
      // @ts-ignore
      expect(() => validateTrigger(null)).toThrowError("'trigger' expected an object value.");

      // @ts-ignore
      expect(() => validateTrigger(undefined)).toThrowError("'trigger' expected an object value.");

      // @ts-ignore
      expect(() => validateTrigger('string')).toThrowError("'trigger' expected an object value.");

      // @ts-ignore
      expect(() => validateTrigger(1)).toThrowError("'trigger' expected an object value.");
    });

    test('throws an error if trigger type is unknown', () => {
      // @ts-ignore
      const trigger: Trigger = {
        type: -1,
      };

      expect(() => validateTrigger(trigger)).toThrowError('Unknown trigger type');
    });

    describe('validateTimestampTrigger()', () => {
      test('throws error if timestamp is invalid', () => {
        let trigger: TimestampTrigger = {
          type: TriggerType.TIMESTAMP,
          // @ts-ignore
          timestamp: null,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "trigger.timestamp' expected a number value.",
        );

        trigger = {
          type: TriggerType.TIMESTAMP,
          // @ts-ignore
          timestamp: '',
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "trigger.timestamp' expected a number value.",
        );
      });

      test('throws error when timestamp is in the past', () => {
        const date = new Date(Date.now());
        const trigger: TimestampTrigger = {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.timestamp' date must be in the future.",
        );
      });

      test('repeatFrequency defaults to -1 if not set', () => {
        const date = new Date(Date.now());
        date.setSeconds(date.getSeconds() + 10);
        const trigger: TimestampTrigger = {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
        };

        const $ = validateTrigger(trigger) as TimestampTrigger;

        expect($.repeatFrequency).toEqual(-1);
      });

      test('throws error if repeatFrequency is invalid', () => {
        const date = new Date(Date.now());
        date.setSeconds(date.getSeconds() + 10);

        const trigger: TimestampTrigger = {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
          repeatFrequency: 3,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.repeatFrequency' expected a RepeatFrequency value.",
        );
      });

      test('accepts -1 for repeatFrequency when creating a timestamp trigger', () => {
        const date = new Date(Date.now());
        date.setSeconds(date.getSeconds() + 10);

        const trigger: TimestampTrigger = {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
          repeatFrequency: -1,
        };

        const $ = validateTrigger(trigger) as TimestampTrigger;

        expect($.repeatFrequency).toEqual(-1);
        expect($.timestamp).toEqual(date.getTime());
      });

      test('returns a valid timestamp trigger object', () => {
        const date = new Date(Date.now());
        date.setSeconds(date.getSeconds() + 10);

        const trigger: TimestampTrigger = {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
          repeatFrequency: 2,
        };

        const $ = validateTrigger(trigger) as TimestampTrigger;

        // expect($.).toEqual(date.getTime());
        expect($.repeatFrequency).toEqual(2);
        expect($.timestamp).toEqual(date.getTime());
      });

      describe('alarmManager', () => {
        test('ignores property when false', () => {
          const date = new Date(Date.now());
          date.setSeconds(date.getSeconds() + 10);

          const trigger: TimestampTrigger = {
            type: TriggerType.TIMESTAMP,
            timestamp: date.getTime(),
            repeatFrequency: 2,
            alarmManager: false,
          };

          const $ = validateTrigger(trigger) as TimestampTrigger;

          // expect($.).toEqual(date.getTime());
          expect($.repeatFrequency).toEqual(2);
          expect($.timestamp).toEqual(date.getTime());
          expect($.alarmManager).not.toBeDefined();
        });

        test('parses property to the default values', () => {
          const date = new Date(Date.now());
          date.setSeconds(date.getSeconds() + 10);

          const trigger: TimestampTrigger = {
            type: TriggerType.TIMESTAMP,
            timestamp: date.getTime(),
            repeatFrequency: 2,
            alarmManager: true,
          };

          const $ = validateTrigger(trigger) as TimestampTrigger;

          // expect($.).toEqual(date.getTime());
          expect($.repeatFrequency).toEqual(2);
          expect($.timestamp).toEqual(date.getTime());
          expect($.alarmManager).toEqual({ allowWhileIdle: false });
        });

        test('parses property to an object with allowIdle as true', () => {
          const date = new Date(Date.now());
          date.setSeconds(date.getSeconds() + 10);

          const trigger: TimestampTrigger = {
            type: TriggerType.TIMESTAMP,
            timestamp: date.getTime(),
            repeatFrequency: 2,
            alarmManager: {
              allowWhileIdle: true,
            },
          };

          const $ = validateTrigger(trigger) as TimestampTrigger;

          // expect($.).toEqual(date.getTime());
          expect($.repeatFrequency).toEqual(2);
          expect($.timestamp).toEqual(date.getTime());
          expect($.alarmManager).toEqual({ allowWhileIdle: true });
        });
      });
    });

    describe('validateIntervalTrigger()', () => {
      test('throws error if interval is invalid', () => {
        let trigger: IntervalTrigger = {
          type: TriggerType.INTERVAL,
          // @ts-ignore
          interval: null,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "trigger.interval' expected a number value.",
        );

        trigger = {
          type: TriggerType.INTERVAL,
          // @ts-ignore
          interval: '',
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "trigger.interval' expected a number value.",
        );
      });

      test('defaults timeUnit to SECONDS if not set', () => {
        const trigger: IntervalTrigger = {
          type: TriggerType.INTERVAL,
          interval: 1000,
        };

        const $ = validateTrigger(trigger) as IntervalTrigger;

        expect($.type).toEqual(TriggerType.INTERVAL);
        expect($.timeUnit).toEqual(TimeUnit.SECONDS);
        expect($.interval).toEqual(1000);
      });

      test('throws error if timeUnit is invalid', () => {
        const trigger: IntervalTrigger = {
          type: TriggerType.INTERVAL,
          // @ts-ignore
          timeUnit: 'MONTHS',
          interval: 60,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.timeUnit' expected a TimeUnit value.",
        );
      });

      test('throws error if interval is less than 15 minutes', () => {
        let trigger: IntervalTrigger = {
          type: TriggerType.INTERVAL,
          timeUnit: TimeUnit.SECONDS,
          interval: 60,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.interval' expected to be at least 15 minutes.",
        );

        trigger = {
          type: TriggerType.INTERVAL,
          timeUnit: TimeUnit.MINUTES,
          interval: 12,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.interval' expected to be at least 15 minutes.",
        );

        trigger = {
          type: TriggerType.INTERVAL,
          timeUnit: TimeUnit.HOURS,
          interval: 0.5,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.interval' expected to be at least 15 minutes.",
        );

        trigger = {
          type: TriggerType.INTERVAL,
          timeUnit: TimeUnit.DAYS,
          interval: 0.5,
        };

        expect(() => validateTrigger(trigger)).toThrowError(
          "'trigger.interval' expected to be at least 15 minutes.",
        );
      });

      test('returns a valid interval trigger object', () => {
        const date = new Date(Date.now());
        date.setSeconds(date.getSeconds() + 10);

        const trigger: IntervalTrigger = {
          type: TriggerType.INTERVAL,
          timeUnit: TimeUnit.DAYS,
          interval: 1,
        };

        const $ = validateTrigger(trigger) as IntervalTrigger;

        expect($.type).toEqual(TriggerType.INTERVAL);
        expect($.timeUnit).toEqual(TimeUnit.DAYS);
        expect($.interval).toEqual(1);
      });
    });
  });
});
