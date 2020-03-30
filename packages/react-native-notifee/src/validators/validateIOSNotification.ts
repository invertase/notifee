/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Importance } from '..';
import { NotificationIOS } from '../types/NotificationIOS';
import { hasOwnProperty, isBoolean, isNumber, isString, isUndefined } from '../utils';

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
   * critical
   */
  if (hasOwnProperty(ios, 'critical')) {
    if (!isBoolean(ios.critical)) {
      throw new Error("'notification.ios.critical' must be a boolean value if specified.");
    } else {
      out.critical = ios.critical;
    }
  }

  /**
   * criticalVolume
   */
  if (hasOwnProperty(ios, 'criticalVolume')) {
    if (!isNumber(ios.criticalVolume)) {
      throw new Error("'notification.ios.criticalVolume' must be a number value if specified.");
    } else {
      if (ios.criticalVolume < 0 || ios.criticalVolume > 1) {
        throw new Error(
          "'notification.ios.criticalVolume' must be a float value between 0.0 and 1.0.",
        );
      }
      out.criticalVolume = ios.criticalVolume;
    }
  }

  /**
   * sound
   */
  if (hasOwnProperty(ios, 'sound')) {
    if (isString(ios.sound)) {
      out.sound = ios.sound;
    } else {
      throw new Error("'notification.sound' must be a string value if specified.");
    }
  }

  /**
   * badgeCount
   */
  if (hasOwnProperty(ios, 'badgeCount')) {
    if (!isNumber(ios.badgeCount) || ios.badgeCount < 0) {
      throw new Error("'notification.ios.badgeCount' expected a number value >=0.");
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
