/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationIOS } from './NotificationIOS';
import { NotificationAndroid } from './NotificationAndroid';

/**
 * Interface for building a local notification for both Android & iOS devices.
 *
 * #### Example
 *
 * ```js
 * const notification = {
 *   body: 'Hello World!',
 *   title: 'Welcome',
 *   data: {
 *     user: '123',
 *   },
 *   android: {
 *     color: '#3f51b5',
 *   },
 *   ios: {
 *     alertAction: 'Open App',
 *   },
 * };
 *
 * await notifee.displayNotification(notification);
 * ```
 */
export interface Notification {
  /**
   * The main body content of a notification. This field required and is the main body of your notification.
   *
   * On Android, if you wish to read the body when the notification is opened, add it to the `data` object.
   *
   * #### Example
   *
   * ![Body Text](https://prismic-io.s3.amazonaws.com/invertase%2F7f9cc068-c618-44f0-88da-6041c6d55f48_new+project+%2817%29.jpg)
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  body: string;

  /**
   * A unique identifier for your notification.
   *
   * Defaults to a random string.
   */
  notificationId?: string;

  /**
   * The notification title which appears above the body text.
   *
   * On Android, if you wish to read the title when the notification is opened, add it to the `data` object.
   *
   * #### Example
   *
   * ![Title Text](https://prismic-io.s3.amazonaws.com/invertase%2F6fa1dea9-8cf6-4e9a-b318-8d8f73d568cf_new+project+%2818%29.jpg)
   *
   * ```js
   * const notification = {
   *   title: 'Welcome!',
   *   body: 'Hello World!',
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  title?: string;

  /**
   * The notification subtitle, which appears on a new line below the title.
   *
   * #### Example
   *
   * ![Title Text](https://prismic-io.s3.amazonaws.com/invertase%2F6fa1dea9-8cf6-4e9a-b318-8d8f73d568cf_new+project+%2818%29.jpg)
   *
   * ```js
   * const notification = {
   *   title: 'Welcome!',
   *   subtitle: 'Learn more...',
   *   body: 'Hello World!',
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  subtitle?: string;

  /**
   * Additional data to store on the notification. Only `string` values can be stored.
   *
   * #### Example
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   *   data: {
   *     user: '123',
   *   }
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  data?: { [key: string]: string };

  /**
   * Overrides the sound the notification is displayed with.
   *
   * The default value is `default`, which is the system default sound.
   *
   * TODO @ehesp FAQ/Guide on custom sounds
   */
  sound?: string;
}

/**
 * TODO
 */
export interface NotificationBuilder extends Notification {
  /**
   * Android specific notification options. See the `NotificationAndroid` interface for more
   * information and default options which are applied to a notification.
   *
   * #### Example
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   *   android: {
   *     color: '#3f51b5',
   *   },
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  android?: NotificationAndroid;

  /**
   * iOS specific notification options. See the `NotificationIOS` interface for more information
   * and default options which are applied to a notification.
   *
   * #### Example
   *
   * ```js
   * const notification = {
   *   body: 'Hello World!',
   *   ios: {
   *     alertAction: 'Open App',
   *   },
   * };
   *
   * await notifee.displayNotification(notification);
   * ```
   */
  ios?: NotificationIOS;
}

/**
 * TODO
 */
export interface RemoteNotification extends Notification {
  android: NotificationAndroid;
  ios: NotificationIOS;
}

/**
 * TODO
 */
export type NotificationObserver = (notification: RemoteNotification) => void;

/**
 * TODO
 */
export type NotificationObserverUnsubscribe = () => void;

/**
 * TODO
 */
export interface NotificationSchedule {
  /**
   * The date when the notification should first be shown, in milliseconds since 1970.
   *
   * #### Example
   *
   * Schedule notification to display 10 minutes from now.
   *
   * ```js
   * await notifee.scheduleNotification(notification, {
   *   fireDate: Date.now() + 600000,
   * });
   * ```
   */
  fireDate: number;

  /**
   * Whether the `fireDate` should be respected exactly.
   *
   * To help save battery, only set to `true` under scenarios where the notification
   * `fireDate` is critical.
   *
   * Defaults to `false`. Has no effect on iOS.
   *
   * @android
   */
  exact?: boolean;

  /**
   * How frequently after  the `fireDate` should the notification be repeated.
   *
   * If not present, the notification will only be displayed once on the given `fireDate`.
   *
   * #### Example
   *
   * Schedule notification to display 10 minutes from now, and repeat
   * every week
   *
   * ```js
   * import notifee, { AndroidRepeatInterval } from '@notifee/react-native';
   *
   * await notifee.scheduleNotification(notification, {
   *   fireDate: Date.now() + 600000,
   *   repeatInterval: AndroidRepeatInterval.WEEK,
   * });
   */
  repeatInterval?: NotificationRepeatInterval;
}

/**
 * Interface used when defining the `repeatInterval` on a scheduled notification.
 */
export enum NotificationRepeatInterval {
  MINUTE = 'minute',
  HOUR = 'hour',
  DAY = 'day',
  WEEK = 'week',
}
