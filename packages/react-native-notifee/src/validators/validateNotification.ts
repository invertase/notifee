/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { generateId, hasOwnProperty, isObject, isString } from '../utils';

import validateAndroidNotification from './validateAndroidNotification';
import validateiOSNotification from './validateiOSNotification';
import { Notification } from '../../types/Notification';

export default function validateNotification(notification: Notification): Notification {
  if (!isObject(notification)) {
    throw new Error("'notification' expected an object value.");
  }

  // Defaults
  const out: Notification = {
    id: '',
    title: '',
    subtitle: '',
    body: '',
    data: {},
    ios: {},
    android: {},
  };

  /**
   * id
   */
  if (hasOwnProperty(notification, 'id')) {
    if (!isString(notification.id) || !notification.id) {
      throw new Error("'notification.id' invalid notification ID, expected a unique string value.");
    }

    out.id = notification.id;
  } else {
    out.id = generateId();
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
   * body
   */
  if (hasOwnProperty(notification, 'body')) {
    if (!isString(notification.body)) {
      throw new Error("'notification.body' expected a string value.");
    }

    out.body = notification.body;
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

  /**
   * android
   */
  out.android = validateAndroidNotification(notification.android);

  /**
   * ios
   */
  out.ios = validateiOSNotification(notification.ios);

  return out;
}
