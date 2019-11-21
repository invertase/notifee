/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { generateNotificationId, hasOwnProperty, isObject, isString } from '../utils';

import validateAndroidNotification from './validateAndroidNotification';
import validateiOSNotification from './validateiOSNotification';
import { NotificationBuilder } from '../../types/Notification';

export default function validateNotification(
  notification: NotificationBuilder,
): NotificationBuilder {
  if (!isObject(notification)) {
    throw new Error("'notification' expected an object value.");
  }

  // Required
  if (!hasOwnProperty(notification, 'body') || !isString(notification.body) || !notification.body) {
    throw new Error("'notification.body' expected a string value containing notification text.");
  }

  // Defaults
  const out: NotificationBuilder = {
    notificationId: '',
    title: '',
    subtitle: '',
    body: notification.body,
    data: {},
    ios: {},
    android: {},
    sound: 'default',
  };

  /**
   * notificationId
   */
  if (hasOwnProperty(notification, 'notificationId')) {
    if (!isString(notification.notificationId) || !notification.notificationId) {
      throw new Error(
        "'notification.notificationId' invalid notification ID, expected a unique string value.",
      );
    }

    out.notificationId = notification.notificationId;
  } else {
    out.notificationId = generateNotificationId();
  }

  /**
   * title
   */
  if (hasOwnProperty(notification, 'title')) {
    if (!isString(notification.title)) {
      throw new Error("'notification.title' expected a string value.");
    }

    out.title = notification.title;
  }

  /**
   * subtitle
   */
  if (hasOwnProperty(notification, 'subtitle')) {
    if (!isString(notification.subtitle)) {
      throw new Error("'notification.subtitle' expected a string value.");
    }

    out.subtitle = notification.subtitle;
  }

  /**
   * data
   */
  if (hasOwnProperty(notification, 'data') && notification.data != undefined) {
    if (!isObject(notification.data)) {
      throw new Error("'notification.data' expected an object value containing key/value pairs.");
    }

    const entries = Object.entries(notification.data);

    for (let i = 0; i < entries.length; i++) {
      const [key, value] = entries[i];
      if (!isString(value)) {
        throw new Error(
          `'notification.data' value for key "${key}" is invalid, expected a string value.`,
        );
      }
    }

    out.data = notification.data;
  }

  if (hasOwnProperty(notification, 'sound')) {
    if (!isString(notification.sound)) {
      throw new Error("'notification.sound' expected a string value.");
    }

    out.sound = notification.sound;
  }

  /**
   * android
   */
  if (hasOwnProperty(notification, 'android') && notification.android != undefined) {
    out.android = validateAndroidNotification(notification.android);
  }

  /**
   * ios
   */
  if (hasOwnProperty(notification, 'ios') && notification.ios != undefined) {
    // out.ios = validateiOSNotification(); // todo
  }

  return out;
}
