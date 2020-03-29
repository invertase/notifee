/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Importance } from '..';
import { IOSNotificationSound, NotificationIOS } from '../types/NotificationIOS';
import { hasOwnProperty, isBoolean, isNumber, isObject, isString, isUndefined } from '../utils';

export default function validateIOSNotification(ios?: NotificationIOS): NotificationIOS {
  const out: NotificationIOS = {
    importance: Importance.DEFAULT,
  };

  if (isUndefined(ios)) {
    return out;
  }

  /**
   * attachments
   *
   * TODO attachments
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
   * sound
   */
  if (hasOwnProperty(ios, 'sound')) {
    if (isString(ios.sound)) {
      out.sound = {
        name: ios.sound,
      };
    } else if (isObject(ios.sound)) {
      const soundOut = {} as IOSNotificationSound;

      // sound.name
      if (!isString(ios.sound.name)) {
        throw new Error("'notification.ios.sound.name' must be a string value.");
      } else {
        soundOut.name = ios.sound.name;
      }

      // sound.critical
      if (hasOwnProperty(ios.sound, 'critical')) {
        if (!isBoolean(ios.sound.critical)) {
          throw new Error(
            "'notification.ios.sound.critical' must be a boolean value if specified.",
          );
        } else {
          soundOut.critical = ios.sound.critical;
        }
      }

      // sound.criticalVolume
      if (hasOwnProperty(ios.sound, 'criticalVolume')) {
        if (!isNumber(ios.sound.criticalVolume)) {
          throw new Error(
            "'notification.ios.sound.criticalVolume' must be a number value if specified.",
          );
        } else {
          if (ios.sound.criticalVolume < 0 || ios.sound.criticalVolume > 1) {
            throw new Error(
              "'notification.ios.sound.criticalVolume' must be a float value between 0.0 and 1.0.",
            );
          }
          soundOut.criticalVolume = ios.sound.criticalVolume;
        }
      }

      out.sound = soundOut;
    } else {
      throw new Error("'notification.ios.sound' must be a string or an object value if specified.");
    }

    if (!isNumber(ios.badgeCount) || ios.badgeCount < 0) {
      throw new Error("'notification.ios.badgeCount' expected a positive number value.");
    }

    out.badgeCount = ios.badgeCount;
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
   * categoryId
   */
  if (hasOwnProperty(ios, 'categoryId')) {
    if (!isString(ios.categoryId)) {
      throw new Error("'notification.ios.categoryId' expected a of string value");
    }

    out.categoryId = ios.categoryId;
  }

  /**
   * groupId
   */
  if (hasOwnProperty(ios, 'threadId')) {
    if (!isString(ios.threadId)) {
      throw new Error("'notification.ios.threadId' expected a string value.");
    }

    out.threadId = ios.threadId;
  }

  /**
   * summaryArgument
   */
  if (hasOwnProperty(ios, 'summaryArgument')) {
    if (!isString(ios.summaryArgument)) {
      throw new Error("'notification.ios.summaryArgument' expected a string value.");
    }

    out.summaryArgument = ios.summaryArgument;
  }

  /**
   * summaryArgumentCount
   */
  if (hasOwnProperty(ios, 'summaryArgumentCount')) {
    if (!isNumber(ios.summaryArgumentCount) || ios.summaryArgumentCount <= 0) {
      throw new Error(
        "'notification.ios.summaryArgumentCount' expected a positive number greater than 0.",
      );
    }

    out.summaryArgumentCount = ios.summaryArgumentCount;
  }

  /**
   * launchImageName
   */
  if (hasOwnProperty(ios, 'launchImageName')) {
    if (!isString(ios.launchImageName)) {
      throw new Error("'notification.ios.launchImageName' expected a string value.");
    }

    out.launchImageName = ios.launchImageName;
  }

  /**
   * importance
   */
  if (hasOwnProperty(ios, 'importance') && !isUndefined(ios.importance)) {
    if (!Object.values(Importance).includes(ios.importance)) {
      throw new Error("'notification.ios.importance' expected a valid Importance.");
    }

    out.importance = ios.importance;
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
