/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationIOS } from '../types/NotificationIOS';
import {
  hasOwnProperty,
  isArrayOfStrings,
  isNumber,
  isString,
  isUndefined,
} from '../utils';

export default function validateIOSNotification(ios?: NotificationIOS): NotificationIOS {
  const out: NotificationIOS = {
    sound: 'default',
  };

  if (isUndefined(ios)) {
    return out;
  }

  /**
   * attachments
   */
  if (hasOwnProperty(ios, 'attachments')) {
    //   if (!isArray(ios.attachments)) {
    //     throw new Error("'notification.android.attachments' expected an array value.");
    //   }
    //
    //   for (let i = 0; i < ios.attachments.length; i++) {
    //     try {
    //       validateIOSAttachment(ios.attachments[i]);
    //     } catch (e) {
    //       // todo error
    //     }
    //   }
    //
    //   out.groupId = android.groupId;
  }

  /**
   * badgeCount
   */
  if (hasOwnProperty(ios, 'badgeCount')) {
    if (!isNumber(ios.badgeCount) || ios.badgeCount < 0) {
      throw new Error("'notification.ios.badgeCount' expected a positive number value.");
    }

    out.badgeCount = ios.badgeCount;
  }

  /**
   * categories
   */
  if (hasOwnProperty(ios, 'categories')) {
    if (!isArrayOfStrings(ios.categories)) {
      throw new Error("'notification.ios.categories' expected an array of string values.");
    }

    out.categories = ios.categories;
  }

  /**
   * groupId
   */
  if (hasOwnProperty(ios, 'groupId')) {
    if (!isString(ios.groupId)) {
      throw new Error("'notification.ios.groupId' expected a string value.");
    }

    out.groupId = ios.groupId;
  }

  /**
   * groupMessage
   */
  if (hasOwnProperty(ios, 'groupMessage')) {
    if (!isString(ios.groupMessage)) {
      throw new Error("'notification.ios.groupMessage' expected a string value.");
    }

    out.groupMessage = ios.groupMessage;
  }

  /**
   * groupCount
   */
  if (hasOwnProperty(ios, 'groupCount')) {
    if (!isNumber(ios.groupCount) || ios.groupCount < 0) {
      throw new Error("'notification.ios.groupCount' expected a positive number value.");
    }

    out.badgeCount = ios.badgeCount;
  }

  /**
   * launchImage
   */
  if (hasOwnProperty(ios, 'launchImage')) {
    if (!isString(ios.launchImage)) {
      throw new Error("'notification.ios.launchImage' expected a string value.");
    }

    out.launchImage = ios.launchImage;
  }

  /**
   * sound
   */
  if (hasOwnProperty(ios, 'sound')) {
    if (!isString(ios.sound)) {
      throw new Error("'notification.ios.sound' expected a string value.");
    }

    out.sound = ios.sound;
  }

  return out;
}
