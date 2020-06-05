/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Importance } from '../types/Notification';
import { NotificationIOS, ForegroundPresentationOptionsIOS } from '../types/NotificationIOS';
import { checkForProperty, isBoolean, isNumber, isString, isUndefined, isObject } from '../utils';

export default function validateIOSNotification(ios?: NotificationIOS): NotificationIOS {
  const out: NotificationIOS & {
    foregroundPresentationOptions: ForegroundPresentationOptionsIOS;
  } = {
    importance: Importance.DEFAULT,
    foregroundPresentationOptions: { alert: false, badge: true, sound: false },
  };

  if (isUndefined(ios)) {
    return out;
  }

  /**
   * attachments
   *
   * TODO attachments
   */
  if (checkForProperty(ios, 'attachments')) {
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
  if (checkForProperty(ios, 'critical')) {
    if (!isBoolean(ios.critical)) {
      throw new Error("'notification.ios.critical' must be a boolean value if specified.");
    } else {
      out.critical = ios.critical;
    }
  }

  /**
   * criticalVolume
   */
  if (checkForProperty(ios, 'criticalVolume')) {
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
  if (checkForProperty(ios, 'sound')) {
    if (isString(ios.sound)) {
      out.sound = ios.sound;
    } else {
      throw new Error("'notification.sound' must be a string value if specified.");
    }
  }

  /**
   * badgeCount
   */
  if (checkForProperty(ios, 'badgeCount')) {
    if (!isNumber(ios.badgeCount) || ios.badgeCount < 0) {
      throw new Error("'notification.ios.badgeCount' expected a number value >=0.");
    }

    out.badgeCount = ios.badgeCount;
  }

  /**
   * categoryId
   */
  if (checkForProperty(ios, 'categoryId')) {
    if (!isString(ios.categoryId)) {
      throw new Error("'notification.ios.categoryId' expected a of string value");
    }

    out.categoryId = ios.categoryId;
  }

  /**
   * groupId
   */
  if (checkForProperty(ios, 'threadId')) {
    if (!isString(ios.threadId)) {
      throw new Error("'notification.ios.threadId' expected a string value.");
    }

    out.threadId = ios.threadId;
  }

  /**
   * summaryArgument
   */
  if (checkForProperty(ios, 'summaryArgument')) {
    if (!isString(ios.summaryArgument)) {
      throw new Error("'notification.ios.summaryArgument' expected a string value.");
    }

    out.summaryArgument = ios.summaryArgument;
  }

  /**
   * summaryArgumentCount
   */
  if (checkForProperty(ios, 'summaryArgumentCount')) {
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
  if (checkForProperty(ios, 'launchImageName')) {
    if (!isString(ios.launchImageName)) {
      throw new Error("'notification.ios.launchImageName' expected a string value.");
    }

    out.launchImageName = ios.launchImageName;
  }

  /**
   * importance
   */
  if (checkForProperty(ios, 'importance') && !isUndefined(ios.importance)) {
    if (!Object.values(Importance).includes(ios.importance)) {
      throw new Error("'notification.ios.importance' expected a valid Importance.");
    }

    out.importance = ios.importance;
  }

  /**
   * sound
   */
  if (checkForProperty(ios, 'sound')) {
    if (!isString(ios.sound)) {
      throw new Error("'notification.ios.sound' expected a string value.");
    }

    out.sound = ios.sound;
  }

  /**
   * ForegroundPresentationOptions
   */
  if (checkForProperty(ios, 'foregroundPresentationOptions')) {
    if (!isObject(ios.foregroundPresentationOptions)) {
      throw new Error(
        "'notification.ios.foregroundPresentationOptions' expected a valid ForegroundPresentationOptionsIOS object.",
      );
    }

    if (
      checkForProperty<ForegroundPresentationOptionsIOS>(ios.foregroundPresentationOptions, 'alert')
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.alert)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.alert' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.alert = ios.foregroundPresentationOptions.alert;
    }

    if (
      checkForProperty<ForegroundPresentationOptionsIOS>(ios.foregroundPresentationOptions, 'sound')
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.sound)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.sound' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.sound = ios.foregroundPresentationOptions.sound;
    }

    if (
      checkForProperty<ForegroundPresentationOptionsIOS>(ios.foregroundPresentationOptions, 'badge')
    ) {
      if (!isBoolean(ios.foregroundPresentationOptions.badge)) {
        throw new Error(
          "'notification.ios.foregroundPresentationOptions.badge' expected a boolean value.",
        );
      }

      out.foregroundPresentationOptions.badge = ios.foregroundPresentationOptions.badge;
    }
  }

  return out;
}
