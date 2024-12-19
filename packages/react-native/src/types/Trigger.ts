/**
 * Interface for building a trigger with a timestamp.
 *
 * View the [Triggers](/react-native/triggers) documentation to learn more.
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

  /**
   * The frequency at which the trigger repeats.
   * If unset, the notification will only be displayed once.
   *
   * For example:
   *  if set to `RepeatFrequency.HOURLY`, the notification will repeat every hour from the timestamp specified.
   *  if set to `RepeatFrequency.DAILY`, the notification will repeat every day from the timestamp specified.
   *  if set to `RepeatFrequency.WEEKLY`, the notification will repeat every week from the timestamp specified.
   */
  repeatFrequency?: RepeatFrequency;

  /**
   * Choose to schedule your trigger notification with Android's AlarmManager API.
   *
   * By default, trigger notifications are created with Android's WorkManager API.
   *
   * @platform android
   */
  alarmManager?: boolean | TimestampTriggerAlarmManager | undefined;
}

/**
 * An interface representing the different alarm types which can be used with `TimestampTrigger.alarmManager.type`.
 *
 * View the [Triggers](/react-native/triggers) documentation to learn more.
 */
export enum AlarmType {
  SET,
  SET_AND_ALLOW_WHILE_IDLE,
  SET_EXACT,
  SET_EXACT_AND_ALLOW_WHILE_IDLE,
  SET_ALARM_CLOCK,
}

/**
 * Interface to specify additional options for the AlarmManager which can be used with `TimestampTrigger.alarmManager`.
 *
 * View the [Triggers](/react-native/triggers) documentation to learn more.
 *
 * @platform android
 */
export interface TimestampTriggerAlarmManager {
  /**
   * @deprecated use `type` instead
   * -----
   *
   * Sets whether your trigger notification should be displayed even when the system is in low-power idle modes.
   *
   * Defaults to `false`.
   */
  allowWhileIdle?: boolean;

  /** The type of alarm set by alarm manager of android */
  type?: AlarmType;
}

/**
 * An interface representing the different frequencies which can be used with `TimestampTrigger.repeatFrequency`.
 *
 * View the [Triggers](/react-native/triggers) documentation to learn more.
 */
export enum RepeatFrequency {
  NONE = -1,
  HOURLY = 0,
  DAILY = 1,
  WEEKLY = 2,
}

/**
 * Interface for building a trigger that repeats at a specified interval.
 *
 * View the [Triggers](/react-native/triggers) documentation to learn more.
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
 * An interface representing the different units of time which can be used with `IntervalTrigger.timeUnit`.
 *
 * View the [Triggers](/react-native/triggers) documentation to learn more.
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
 * View the [Triggers](/react-native/triggers) documentation to learn more with example usage.
 */
export enum TriggerType {
  TIMESTAMP = 0,
  INTERVAL = 1,
}

export declare type Trigger = TimestampTrigger | IntervalTrigger;
