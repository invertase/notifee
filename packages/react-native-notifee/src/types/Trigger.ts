/**
 * Interface for building a trigger with a timestamp.
 *
 * View the [Triggers](/react-native/docs/triggers) documentation to learn more.
 */
export interface TimestampTrigger {
  /**
   * Constant enum value used to identify the trigger type.
   */
  type: TriggerType.TIMESTAMP;
  /**
   * The timestamp when the notification should first be shown, in milliseconds since 1970.
   */
  timestamp: number;
}

/**
 * Interface for building a trigger that repeats.
 *
 * View the [Triggers](/react-native/docs/triggers) documentation to learn more.
 */
export interface IntervalTrigger {
  /**
   * Constant enum value used to identify the trigger type.
   */
  type: TriggerType.INTERVAL;

  /**
   * How frequently the notification should be repeated.
   *
   * For example, if set to 30, the notification will be displayed every 30 minutes.
   *
   * Must be set to a minimum of 15 minutes.
   */
  interval: number;

  /**
   * The unit of time that the `interval` is measured in.
   *
   * For example, if set to `TimeUnit.DAYS` and repeat interval is set to 3, the notification will repeat every 3 days.
   *
   * Defaults to `TimeUnit.SECONDS`
   */
  timeUnit?: TimeUnit | TimeUnit.SECONDS;
}

/**
 * An interface representing the different units of time which can be used with `TimeTrigger.repeatIntervalTimeUnit`.
 *
 * View the [Triggers](/react-native/docs/triggers) documentation to learn more.
 */
export enum TimeUnit {
  SECONDS = 'SECONDS',
  MINUTES = 'MINUTES',
  HOURS = 'HOURS',
  DAYS = 'DAYS',
}

/**
 * Available Trigger Types.
 *
 * View the [Triggers](/react-native/docs/triggers) documentation to learn more with example usage.
 */
export enum TriggerType {
  TIMESTAMP = 0,
  INTERVAL = 1,
}

export declare type Trigger = TimestampTrigger | IntervalTrigger;
