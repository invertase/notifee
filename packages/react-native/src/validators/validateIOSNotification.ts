/*
 * Copyright (c) 2016-present Invertase Limited
 */

import {
  NotificationIOS,
  IOSForegroundPresentationOptions,
  IOSNotificationAttachment,
} from '../types/NotificationIOS';
import {
  objectHasProperty,
  isBoolean,
  isNumber,
  isString,
  isUndefined,
  isObject,
  isArray,
  isAndroid,
} from '../utils';
import validateIOSCommunicationInfo from './iosCommunicationInfo/validateIOSCommunicationInfo';
import validateIOSAttachment from './validateIOSAttachment';

export default function validateIOSNotification(ios?: NotificationIOS): NotificationIOS {
  const out: NotificationIOS & {
    foregroundPresentationOptions: IOSForegroundPresentationOptions;
  } = {
    foregroundPresentationOptions: {
      alert: true,
      badge: true,
      sound: true,
      banner: true,
      list: true,
    },
  };

  if (isUndefined(ios)) {
    return out;
  }

  /* Skip validating if Android in release */
  if (isAndroid && !__DEV__) return out;

  /**
   * attachments
   */
  if (objectHasProperty(ios, 'attachments')) {
    if (!isArray(ios.attachments)) {
      throw new Error("'notification.ios.attachments' expected an array value.");
    }

    const attachments: IOSNotificationAttachment[] = [];

    for (let i = 0; i < ios.attachments.length; i++) {
      try {
        attachments.push(validateIOSAttachment(ios.attachments[i]));
      } catch (e: any) {
        throw new Error(
          `'notification.ios.attachments' invalid IOSNotificationAttachment. ${e.message}.`,
        );
      }
    }

    if (attachments.length) {
      out.attachments = attachments;
    }
  }

  /**
   * communicationInfo
   */
  if (objectHasProperty(ios, 'communicationInfo') && !isUndefined(ios.communicationInfo)) {
    try {
      out.communicationInfo = validateIOSCommunicationInfo(ios.communicationInfo);
    } catch (e: any) {
      throw new Error(`'ios.communicationInfo' ${e.message}`);
    }
  }

  /**
   * interruptionLevel
   */
  if (objectHasProperty(ios, 'interruptionLevel')) {
    if (
      isString(ios.interruptionLevel) &&
      ['active', 'critical', 'passive', 'timeSensitive'].includes(ios.interruptionLevel)
    ) {
      out.interruptionLevel = ios.interruptionLevel;
    } else {
      throw new Error(
        "'notification.ios.interruptionLevel' must be a string value: 'active','critical','passive','timeSensitive'.",
      );
    }
  }

  /**
   * critical
   */
  if (objectHasProperty(ios, 'critical')) {
    if (!isBoolean(ios.critical)) {
      throw new Error("'notification.ios.critical' must be a boolean value if specified.");
    } else {
      out.critical = ios.critical;
    }
  }

  /**
   * criticalVolume
   */
  if (objectHasProperty(ios, 'criticalVolume')) {
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
  if (objectHasProperty(ios, 'sound')) {
    if (isString(ios.sound)) {
      out.sound = ios.sound;
    } else {
      throw new Error("'notification.sound' must be a string value if specified.");
    }
  }

  /**
   * badgeCount
   */
  if (objectHasProperty(ios, 'badgeCount')) {
    if (!isNumber(ios.badgeCount) || ios.badgeCount < 0) {
      throw new Error("'notification.ios.badgeCount' expected a number value >=0.");
    }

    out.badgeCount = ios.badgeCount;
  }

  /**
   * categoryId
   */
  if (objectHasProperty(ios, 'categoryId')) {
    if (!isString(ios.categoryId)) {
      throw new Error("'notification.ios.categoryId' expected a of string value");
    }

    out.categoryId = ios.categoryId;
  }

  /**
   * groupId
   */
  if (objectHasProperty(ios, 'threadId')) {
    if (!isString(ios.threadId)) {
      throw new Error("'notification.ios.threadId' expected a string value.");
    }

    out.threadId = ios.threadId;
  }

  /**
   * summaryArgument
   */
  if (objectHasProperty(ios, 'summaryArgument')) {
    if (!isString(ios.summaryArgument)) {
      throw new Error("'notification.ios.summaryArgument' expected a string value.");
    }

    out.summaryArgument = ios.summaryArgument;
  }

  /**
   * summaryArgumentCount
   */
  if (objectHasProperty(ios, 'summaryArgumentCount')) {
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
  if (objectHasProperty(ios, 'launchImageName')) {
    if (!isString(ios.launchImageName)) {
      throw new Error("'notification.ios.launchImageName' expected a string value.");
    }

    out.launchImageName = ios.launchImageName;
  }

  /**
   * sound
   */
  if (objectHasProperty(ios, 'sound')) {
    if (!isString(ios.sound)) {
      throw new Error("'notification.ios.sound' expected a string value.");
    }

    out.sound = ios.sound;
  }

  /**
   * ForegroundPresentationOptions
   */
  if (objectHasProperty(ios, 'foregroundPresentationOptions')) {
    if (!isObject(ios.foregroundPresentationOptions)) {
      throw new Error(
        "'notification.ios.foregroundPresentationOptions' expected a valid IOSForegroundPresentationOptions object.",
      );
    }

    if (
      objectHasProperty<IOSForegroundPresentationOptions>(
        ios.foregroundPresentationOptions,
        'alert',
      )
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.alert)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.alert' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.alert = ios.foregroundPresentationOptions.alert;
    }

    if (
      objectHasProperty<IOSForegroundPresentationOptions>(
        ios.foregroundPresentationOptions,
        'sound',
      )
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.sound)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.sound' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.sound = ios.foregroundPresentationOptions.sound;
    }

    if (
      objectHasProperty<IOSForegroundPresentationOptions>(
        ios.foregroundPresentationOptions,
        'badge',
      )
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.badge)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.badge' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.badge = ios.foregroundPresentationOptions.badge;
    }

    if (
      objectHasProperty<IOSForegroundPresentationOptions>(
        ios.foregroundPresentationOptions,
        'banner',
      )
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.banner)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.banner' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.banner = ios.foregroundPresentationOptions.banner;
    }

    if (
      objectHasProperty<IOSForegroundPresentationOptions>(ios.foregroundPresentationOptions, 'list')
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.list)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.list' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.list = ios.foregroundPresentationOptions.list;
    }
  }

  return out;
}
