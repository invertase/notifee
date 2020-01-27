/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationIOS } from './NotificationIOS';
import {
  AndroidPressAction,
  NativeAndroidChannel,
  NativeAndroidChannelGroup,
  NotificationAndroid,
} from './NotificationAndroid';

/**
 * Interface for building a local notification for both Android & iOS devices.
 *
 * See our [Android notification guides](/react-native/docs/android/introduction) for
 * comprehensive examples.
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
 * };
 *
 * await notifee.displayNotification(notification);
 * ```
 */
export interface Notification {
  /**
   * The main body content of a notification.
   */
  body?: string;

  /**
   * A unique identifier for your notification.
   *
   * Defaults to a random string.
   */
  id?: string;

  /**
   * The notification title which appears above the body text.
   */
  title?: string;

  /**
   * The notification subtitle, which appears on a new line below the title.
   */
  subtitle?: string;

  /**
   * Additional data to store on the notification. Only `string` values can be stored.
   */
  data?: { [key: string]: string };

  /**
   * Android specific notification options. See the {@link NotificationAndroid} interface for more
   * information and default options which are applied to a notification.
   *
   * @platform android
   */
  android?: NotificationAndroid;

  /**
   * iOS specific notification options. See the {@link NotificationIOS} interface for more information
   * and default options which are applied to a notification.
   *
   * @platform ios
   */
  ios?: NotificationIOS;
}

/**
 * An interface representing a notification & action that launched the current app / activity.
 *
 * Returned from {@link Module.getInitialNotification}
 */
export interface InitialNotification {
  notification: Notification;
  pressAction: AndroidPressAction;
}

/**
 * An interface representing a Notifee event.
 *
 * Events can be listened to via {@link Module.onEvent}
 */
export interface NotificationEvent {
  type: EventType;
  headless: boolean;
  detail: NotificationEventDetail;
}

export type ForegroundServiceTask = (notification: Notification) => Promise<void>;

/**
 * An enum representing an event type for `onNotificationEvent` subscriptions.
 *
 * The payload sent with this event is [NotificationEvent](/react-native/reference/notificationevent).
 */
export enum EventType {
  /**
   * An unknown event was received.
   *
   * This event type is a failsafe to catch any unknown events from the device. Please
   * report an issue with a reproduction so it can be correctly handled.
   */
  UNKNOWN = -1,

  /**
   * Event type is sent when the user dismisses a notification. This is triggered via the user swiping
   * the notification from the notification shade or performing "Clear all" notifications.
   *
   * This event is **not** sent when a notification is cancelled or times out.
   *
   */
  DISMISSED = 0,

  /**
   * Event type is sent when a notification has been pressed by the user.
   *
   * On Android, notifications must include an `android.pressAction` property for this event to trigger.
   */
  PRESS = 1,

  /**
   * Event type is sent when a user presses a notification action.
   */
  ACTION_PRESS = 2,

  /**
   * Event type sent when a notification has been delivered to the device. For scheduled notifications,
   * this event is sent at the point when the schedule executes, not when a the schedule is created.
   *
   * It's important to note even though a notification has been delivered, it may not be shown to the
   * user. For example, they may have notifications disabled on the device/channel/app.
   */
  DELIVERED = 3,

  /**
   * Event is sent when the user changes the notification blocked state for the entire application or
   * when the user opens the application settings.
   *
   * @platform android API Level >= 28
   */
  APP_BLOCKED = 4,

  /**
   * Event type is sent when the user changes the notification blocked state for a channel in the application.
   *
   * @platform android API Level >= 28
   */
  CHANNEL_BLOCKED = 5,

  /**
   * Event type is sent when the user changes the notification blocked state for a channel group in the application.
   *
   * @platform android API Level >= 28
   */
  CHANNEL_GROUP_BLOCKED = 6,

  /**
   * Event type is sent when a notification has been scheduled for displaying at a future date/time.
   */
  SCHEDULED = 7,
}

export interface NotificationEventDetail {
  /**
   * The notification this event relates to.
   *
   * Available when `event.type` one of:
   *  - {@link EventType.DISMISSED}
   *  - {@link EventType.PRESS}
   *  - {@link EventType.ACTION_PRESS}
   *  - {@link EventType.DELIVERED}
   *  - {@link EventType.SCHEDULED}
   */
  notification?: Notification;

  /**
   * The press action which triggered the event.
   *
   * If a press action caused the event, this property will be available allowing you to retrieve the
   * action ID and perform logic.
   *
   * Available when `event.type` is {@link EventType.PRESS} or {@link EventType.ACTION_PRESS}.
   */
  pressAction?: AndroidPressAction;

  /**
   * The input from a notification action.
   *
   * Once an input has been received, the notification should be updated to remove the pending state
   * of the notification action, by adding the input value to the `inputHistory` property.
   *
   * Available when `event.type` is {@link EventType.ACTION_PRESS} and `input: true` is
   * set on the action.
   *
   * @platform android API Level >= 20
   */
  input?: string;

  /**
   * The channel that had its block state changed.
   *
   * Note that if the channel no longer exists during the time the event
   * was sent the channel property will be undefined.
   *
   * Available when `event.type` is {@link EventType.CHANNEL_BLOCKED}.
   *
   * @platform android API Level >= 28
   */
  channel?: NativeAndroidChannel;

  /**
   * The channel group that had its block state changed.
   *
   * Note that if the channel group no longer exists during the time the event
   * was sent the channelGroup property will be undefined.
   *
   * Available when `event.type` is {@link EventType.CHANNEL_GROUP_BLOCKED}.
   *
   * @platform android API Level >= 28
   */
  channelGroup?: NativeAndroidChannelGroup;

  /**
   * The notification blocked status of your entire application.
   *
   * Available when `event.type` is {@link EventType.APP_BLOCKED}.
   *
   * @platform android API Level >= 28
   */
  blocked?: boolean;
}
