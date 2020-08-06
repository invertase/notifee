/**
 * Interface for building a trigger based on time.
 *
 * View the [Triggers](/react-native/docs/triggers) documentation to learn more.
 *
 * Currently only supported on Android.
 *
 * @platform android
 */
export interface TimeTrigger {
  /**
   * Constant enum value used to identify the trigger type.
   */
  type: TriggerType.TIME;
  /**
   * The timestamp when the notification should first be shown, in milliseconds since 1970.
   */
  timestamp: number;

  /**
   * How frequently the notification should be repeated.
   *
   * If not present, the notification will be displayed once.
   *
   * For example, if set to 30, the notification will be displayed every 30 minutes.
   *
   * Must be set to a minimum of 15 minutes.
   *
   */
  repeatInterval?: number;

  /**
   * The unit of time that the `repeatInterval` is measured in.
   *
   * For example, if set to `TimeUnit.DAYS` and repeat interval is set to 3, the notification will repeat every 3 days.
   *
   * Defaults to `TimeUnit.MINUTES`
   */
  repeatIntervalTimeUnit?: TimeUnit | TimeUnit.MINUTES;
}

/**
 * An interface representing the different units of time which can be used with `TimeTrigger.repeatIntervalTimeUnit`.
 *
 * View the [Triggers](/react-native/docs/triggers) documentation to learn more.
 *
 * Currently only supported on Android.
 *
 * @platform android
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
 *
 * Currently only supported on Android.
 *
 * @platform android
 */
export enum TriggerType {
  TIME = 0,
}

export declare type Trigger = TimeTrigger;
