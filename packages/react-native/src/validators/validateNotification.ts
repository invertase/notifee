/*
 * Copyright (c) 2016-present Invertase Limited
 */

import {
  generateId,
  objectHasProperty,
  isObject,
  isString,
  isAndroid,
  isIOS,
  isNumber,
} from '../utils';

import validateAndroidNotification from './validateAndroidNotification';
import validateIOSNotification from './validateIOSNotification';
import { Notification } from '../types/Notification';
import { Platform } from 'react-native';
import { NotificationAndroid } from '../types/NotificationAndroid';
import { NotificationIOS } from '..';

/**
 * Validate platform-specific notification
 *
 * Only throws a validation error if the device is on the same platform
 * Otherwise, will show a debug log in the console
 */
export const validatePlatformSpecificNotification = (
  out: Notification,
  specifiedPlatform: string,
): NotificationAndroid | NotificationIOS => {
  try {
    if (specifiedPlatform === 'ios') {
      return validateIOSNotification(out.ios);
    } else {
      return validateAndroidNotification(out.android);
    }
  } catch (error) {
    const isRunningOnSamePlatform = specifiedPlatform === Platform.OS;
    if (isRunningOnSamePlatform) {
      throw error;
    } else {
      console.debug(`Invalid ${specifiedPlatform} notification ->`, error);
      return {};
    }
  }
};

export default function validateNotification(notification: Notification): Notification {
  if (!isObject(notification)) {
    throw new Error("'notification' expected an object value.");
  }

  // Defaults
  const out: Notification = {
    id: '',
    data: {},
  };

  if (isAndroid) {
    /* istanbul ignore next */
    out.android = {};
  } else if (isIOS) {
    out.ios = {};
  }

  /**
   * id
   */
  if (objectHasProperty(notification, 'id')) {
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
  if (objectHasProperty(notification, 'title')) {
    if (notification.title !== undefined && !isString(notification.title)) {
      throw new Error("'notification.title' expected a string value or undefined.");
    }

    out.title = notification.title;
  }

  /**
   * body
   */
  if (objectHasProperty(notification, 'body')) {
    if (notification.body !== undefined && !isString(notification.body)) {
      throw new Error("'notification.body' expected a string value or undefined.");
    }

    out.body = notification.body;
  }

  /**
   * subtitle
   */
  if (objectHasProperty(notification, 'subtitle')) {
    if (notification.subtitle !== undefined && !isString(notification.subtitle)) {
      throw new Error("'notification.subtitle' expected a string value or undefined.");
    }

    out.subtitle = notification.subtitle;
  }

  /**
   * data
   */
  if (objectHasProperty(notification, 'data') && notification.data !== undefined) {
    if (!isObject(notification.data)) {
      throw new Error("'notification.data' expected an object value containing key/value pairs.");
    }

    const entries = Object.entries(notification.data);

    for (let i = 0; i < entries.length; i++) {
      const [key, value] = entries[i];
      if (!isString(value) && !isNumber(value) && !isObject(value)) {
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
  const validatedAndroid = validatePlatformSpecificNotification(
    notification,
    'android',
  ) as NotificationAndroid;

  if (isAndroid) {
    out.android = validatedAndroid;
  }

  /**
   * ios
   */
  const validatedIOS = validatePlatformSpecificNotification(notification, 'ios') as NotificationIOS;
  if (isIOS) {
    out.ios = validatedIOS;
  }

  return out;
}
