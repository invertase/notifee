/*
 * Copyright (c) 2016-present Invertase Limited
 */

import {
  hasOwnProperty,
  isArray,
  isBoolean,
  isNumber,
  isObject,
  isString,
  isUndefined,
} from '../utils';

import {
  AndroidBadgeIconType,
  AndroidDefaults,
  AndroidStyle,
  AndroidCategory,
  AndroidGroupAlertBehavior,
  AndroidPriority,
  AndroidVisibility,
  NotificationAndroid,
  AndroidProgress,
  AndroidBubble,
} from '../../types/NotificationAndroid';

import {
  isValidColor,
  isValidLightPattern,
  isValidRemoteInputHistory,
  isValidTimestamp,
  isValidVibratePattern,
} from './validate';

import validateAndroidAction from './validateAndroidAction';
import {
  validateAndroidBigPictureStyle,
  validateAndroidBigTextStyle,
  validateAndroidInboxStyle,
  validateAndroidMessagingStyle,
} from './validateAndroidStyle';
import validateAndroidOnPressAction from './validateAndroidPressAction';

export default function validateAndroidNotification(
  android?: NotificationAndroid,
): NotificationAndroid {
  // Notification default values
  const out: NotificationAndroid = {
    autoCancel: true,
    asForegroundService: false,
    badgeIconType: AndroidBadgeIconType.LARGE,
    colorized: false,
    chronometerDirection: 'up',
    groupAlertBehavior: AndroidGroupAlertBehavior.ALL,
    groupSummary: false,
    ongoing: false,
    onlyAlertOnce: false,
    priority: AndroidPriority.DEFAULT,
    showTimestamp: false,
    smallIcon: ['ic_launcher', -1],
    showChronometer: false,
    visibility: AndroidVisibility.PRIVATE,
  };

  if (isUndefined(android)) {
    return out;
  }

  if (!isUndefined(android) && !isObject(android)) {
    throw new Error("'notification.android' expected an object value.");
  }

  /**
   * actions
   */
  if (hasOwnProperty(android, 'actions') && android.actions != undefined) {
    // if (!isArray(android.actions)) {
    //   throw new Error("'notification.android.actions' expected an array of AndroidAction types.");
    // }
    //
    // const actions = [];
    // try {
    //   for (let i = 0; i < android.actions.length; i++) {
    //     actions.push(validateAndroidAction(android.actions[i]));
    //   }
    // } catch (e) {
    //   throw new Error(`'notification.android.actions' invalid AndroidAction. ${e.message}.`);
    // }

    // if (actions.length) {
    out.actions = android.actions;
    // }
  }

  /**
   * asForegroundService
   */
  if (hasOwnProperty(android, 'asForegroundService')) {
    if (!isBoolean(android.asForegroundService)) {
      throw new Error("'notification.android.asForegroundService' expected a boolean value.");
    }

    out.asForegroundService = android.asForegroundService;
  }

  /**
   * autoCancel
   */
  if (hasOwnProperty(android, 'autoCancel')) {
    if (!isBoolean(android.autoCancel)) {
      throw new Error("'notification.android.autoCancel' expected a boolean value.");
    }

    out.autoCancel = android.autoCancel;
  }

  /**
   * badgeIconType
   */
  if (hasOwnProperty(android, 'badgeIconType') && !isUndefined(android.badgeIconType)) {
    if (!Object.values(AndroidBadgeIconType).includes(android.badgeIconType)) {
      throw new Error(
        "'notification.android.badgeIconType' expected a valid AndroidBadgeIconType.",
      );
    }

    out.badgeIconType = android.badgeIconType;
  }

  /**
   * bubble
   */
  if (hasOwnProperty(android, 'bubble') && !isUndefined(android.bubble)) {
    if (!isObject(android.bubble)) {
      throw new Error("'notification.android.bubble' expected an object value.");
    }

    if (!isString(android.bubble.icon) || !android.bubble.icon) {
      throw new Error("'notification.android.bubble.icon' expected a non-empty string value.");
    }

    const bubbleOut: AndroidBubble = {
      icon: android.bubble.icon,
    };

    if (hasOwnProperty(android.bubble, 'height') && !isUndefined(android.bubble.height)) {
      if (!isNumber(android.bubble.height) || android.bubble.height < 1) {
        throw new Error("'notification.android.bubble.height' expected a signed integer value.");
      }

      bubbleOut.height = android.bubble.height;
    }

    if (hasOwnProperty(android.bubble, 'autoExpand')) {
      if (!isBoolean(android.bubble.autoExpand)) {
        throw new Error("'notification.android.bubble.autoExpand' expected a boolean value.");
      }

      bubbleOut.autoExpand = android.bubble.autoExpand;
    }

    if (hasOwnProperty(android.bubble, 'suppressNotification')) {
      if (!isBoolean(android.bubble.suppressNotification)) {
        throw new Error(
          "'notification.android.bubble.suppressNotification' expected a boolean value.",
        );
      }

      bubbleOut.suppressNotification = android.bubble.suppressNotification;
    }

    out.bubble = bubbleOut;
  }

  /**
   * category
   */
  if (hasOwnProperty(android, 'category') && !isUndefined(android.category)) {
    if (!Object.values(AndroidCategory).includes(android.category)) {
      throw new Error("'notification.android.category' expected a valid AndroidCategory.");
    }

    out.category = android.category;
  }

  /**
   * channelId
   */
  if (!isString(android.channelId)) {
    throw new Error("'notification.android.channelId' expected a string value.");
  }

  out.channelId = android.channelId;

  /**
   * color
   */
  if (hasOwnProperty(android, 'color') && !isUndefined(android.color)) {
    if (!isString(android.color)) {
      throw new Error("'notification.android.color' expected a string value.");
    }

    if (!isValidColor(android.color)) {
      throw new Error(
        "'notification.android.color' invalid color. Expected an AndroidColor or hexadecimal string value.",
      );
    }

    out.color = android.color;
  }

  /**
   * colorized
   */
  if (hasOwnProperty(android, 'colorized')) {
    if (!isBoolean(android.colorized)) {
      throw new Error("'notification.android.colorized' expected a boolean value.");
    }

    out.colorized = android.colorized;
  }

  /**
   * chronometerDirection
   */
  if (hasOwnProperty(android, 'chronometerDirection')) {
    if (!isString(android.chronometerDirection)) {
      throw new Error("'notification.android.chronometerDirection' expected a string value.");
    }

    if (android.chronometerDirection !== 'up' && android.chronometerDirection !== 'down') {
      throw new Error(`'notification.android.chronometerDirection' must be one of "up" or "down".`);
    }

    out.chronometerDirection = android.chronometerDirection;
  }

  /**
   * defaults
   */
  if (hasOwnProperty(android, 'defaults') && !isUndefined(android.defaults)) {
    if (!isArray(android.defaults)) {
      throw new Error("'notification.android.defaults' expected an array.");
    }

    if (android.defaults.length === 0) {
      throw new Error(
        "'notification.android.defaults' expected an array containing AndroidDefaults.",
      );
    }

    const defaults = Object.values(AndroidDefaults);

    for (let i = 0; i < android.defaults.length; i++) {
      if (!defaults.includes(android.defaults[i])) {
        throw new Error(
          "'notification.android.defaults' invalid array value, expected a AndroidDefaults value.",
        );
      }
    }

    out.defaults = android.defaults;
  }

  if (hasOwnProperty(android, 'fullScreenAction')) {
    out.fullScreenAction = android.fullScreenAction;
  }

  /**
   * group
   */
  if (hasOwnProperty(android, 'group')) {
    if (!isString(android.group)) {
      throw new Error("'notification.android.group' expected a string value.");
    }

    out.group = android.group;
  }

  /**
   * groupAlertBehavior
   */
  if (hasOwnProperty(android, 'groupAlertBehavior') && !isUndefined(android.groupAlertBehavior)) {
    if (!Object.values(AndroidGroupAlertBehavior).includes(android.groupAlertBehavior)) {
      throw new Error(
        "'notification.android.groupAlertBehavior' expected a valid AndroidGroupAlertBehavior.",
      );
    }

    out.groupAlertBehavior = android.groupAlertBehavior;
  }

  /**
   * groupSummary
   */
  if (hasOwnProperty(android, 'groupSummary')) {
    if (!isBoolean(android.groupSummary)) {
      throw new Error("'notification.android.groupSummary' expected a boolean value.");
    }

    out.groupSummary = android.groupSummary;
  }

  /**
   * largeIcon
   */
  if (hasOwnProperty(android, 'largeIcon')) {
    if (!isString(android.largeIcon) || !android.largeIcon) {
      throw new Error("'notification.android.largeIcon' expected a string value.");
    }

    out.largeIcon = android.largeIcon;
  }

  /**
   * lights
   */
  if (hasOwnProperty(android, 'lights') && !isUndefined(android.lights)) {
    if (!isArray(android.lights)) {
      throw new Error(
        "'notification.android.lights' expected an array value containing the color, on ms and off ms.",
      );
    }

    const [valid, property] = isValidLightPattern(android.lights);

    if (!valid) {
      switch (property) {
        case 'color':
          throw new Error(
            "'notification.android.lights' invalid color. Expected an AndroidColor or hexadecimal string value.",
          );
        case 'onMs':
          throw new Error(
            `'notification.android.lights\' invalid "on" millisecond value, expected a number greater than 0.`,
          );
        case 'offMs':
          throw new Error(
            `notification.android.lights\' invalid "off" millisecond value, expected a number greater than 0.`,
          );
      }
    }

    out.lights = android.lights;
  }

  /**
   * number
   */
  if (hasOwnProperty(android, 'number')) {
    if (!isNumber(android.number)) {
      throw new Error("'notification.android.number' expected a number value.");
    }

    out.number = android.number;
  }

  /**
   * ongoing
   */
  if (hasOwnProperty(android, 'ongoing')) {
    if (!isBoolean(android.ongoing)) {
      throw new Error("'notification.android.ongoing' expected a boolean value.");
    }

    out.ongoing = android.ongoing;
  }

  /**
   * onlyAlertOnce
   */
  if (hasOwnProperty(android, 'onlyAlertOnce')) {
    if (!isBoolean(android.onlyAlertOnce)) {
      throw new Error("'notification.android.onlyAlertOnce' expected a boolean value.");
    }

    out.onlyAlertOnce = android.onlyAlertOnce;
  }

  /**
   * onPressAction
   */
  if (hasOwnProperty(android, 'onPressAction')) {
    if (isBoolean(android.onPressAction)) {
      // Only set if true
      if (android.onPressAction) {
        out.onPressAction = {
          id: 'default',
        };
      }
    } else if (isObject(android.onPressAction)) {
      try {
        out.onPressAction = validateAndroidOnPressAction(android.onPressAction);
      } catch (e) {
        throw new Error(`'notification.android.onPressAction' ${e.message}`);
      }
    } else {
      throw new Error("'notification.android.onPressAction' expected a boolean or object value.");
    }
  }

  /**
   * priority
   */
  if (hasOwnProperty(android, 'priority') && !isUndefined(android.priority)) {
    if (!Object.values(AndroidPriority).includes(android.priority)) {
      throw new Error("'notification.android.priority' expected a valid AndroidPriority.");
    }

    out.priority = android.priority;
  }

  /**
   * progress
   */
  if (hasOwnProperty(android, 'progress') && !isUndefined(android.progress)) {
    if (!isObject(android.progress)) {
      throw new Error("'notification.android.progress' expected an object value.");
    }

    const progress: AndroidProgress = {
      indeterminate: false,
    };

    if (hasOwnProperty(android.progress, 'indeterminate')) {
      if (!isBoolean(android.progress.indeterminate)) {
        throw new Error("'notification.android.progress.indeterminate' expected a boolean value.");
      }

      progress.indeterminate = android.progress.indeterminate;
    }

    if (!isUndefined(android.progress.max)) {
      if (!isNumber(android.progress.max) || android.progress.max < 0) {
        throw new Error("'notification.android.progress.max' expected a positive number value.");
      }

      if (isUndefined(android.progress.current)) {
        throw new Error(
          "'notification.android.progress.max' when providing a max value, you must also specify a current value.",
        );
      }

      progress.max = android.progress.max;
    }

    if (!isUndefined(android.progress.current)) {
      if (!isNumber(android.progress.current) || android.progress.current < 0) {
        throw new Error(
          "'notification.android.progress.current' expected a positive number value.",
        );
      }

      if (isUndefined(android.progress.max)) {
        throw new Error(
          "'notification.android.progress.current' when providing a current value, you must also specify a `max` value.",
        );
      }

      progress.current = android.progress.current;
    }

    // We have a max/current value
    if (!isUndefined(progress.max) && !isUndefined(progress.current)) {
      if (progress.current > progress.max) {
        throw new Error(
          "'notification.android.progress' the current value cannot be greater than the max value.",
        );
      }
    }

    out.progress = progress;
  }

  /**
   * remoteInputHistory
   */
  if (hasOwnProperty(android, 'remoteInputHistory') && !isUndefined(android.remoteInputHistory)) {
    if (
      !isArray(android.remoteInputHistory) ||
      !isValidRemoteInputHistory(android.remoteInputHistory)
    ) {
      throw new Error(
        "'notification.android.remoteInputHistory' expected an array of string values.",
      );
    }

    out.remoteInputHistory = android.remoteInputHistory;
  }

  /**
   * shortcutId
   */
  if (hasOwnProperty(android, 'shortcutId')) {
    if (!isString(android.shortcutId)) {
      throw new Error("'notification.android.shortcutId' expected a string value.");
    }

    out.shortcutId = android.shortcutId;
  }

  /**
   * showTimestamp
   */
  if (hasOwnProperty(android, 'showTimestamp')) {
    if (!isBoolean(android.showTimestamp)) {
      throw new Error("'notification.android.showTimestamp' expected a boolean value.");
    }

    out.showTimestamp = android.showTimestamp;
  }

  /**
   * smallIcon
   */
  if (hasOwnProperty(android, 'smallIcon') && !isUndefined(android.smallIcon)) {
    if (isArray(android.smallIcon)) {
      const [icon, level] = android.smallIcon as [string, number];

      if (!isString(icon) || !icon) {
        throw new Error("'notification.android.smallIcon' expected icon to be a string.");
      }

      if (!isNumber(level) || level < 0) {
        throw new Error("'notification.android.smallIcon' expected level to be a positive number.");
      }

      out.smallIcon = [icon, level];
    } else if (isString(android.smallIcon)) {
      out.smallIcon = [android.smallIcon as string, -1];
    } else {
      throw new Error(
        "'notification.android.smallIcon' expected an array containing icon with level or string value.",
      );
    }
  }

  /**
   * sortKey
   */
  if (hasOwnProperty(android, 'sortKey')) {
    if (!isString(android.sortKey)) {
      throw new Error("'notification.android.sortKey' expected a string value.");
    }

    out.sortKey = android.sortKey;
  }

  /**
   * style
   */

  if (hasOwnProperty(android, 'style') && !isUndefined(android.style)) {
    if (!isObject(android.style)) {
      throw new Error("'notification.android.style' expected an object value.");
    }

    switch (android.style.type) {
      case AndroidStyle.BIGPICTURE:
        out.style = validateAndroidBigPictureStyle(android.style);
        break;
      case AndroidStyle.BIGTEXT:
        out.style = validateAndroidBigTextStyle(android.style);
        break;
      case AndroidStyle.INBOX:
        out.style = validateAndroidInboxStyle(android.style);
        break;
      case AndroidStyle.MESSAGING:
        out.style = validateAndroidMessagingStyle(android.style);
        break;
      default:
        throw new Error(
          "'notification.android.style' style type must be one of AndroidStyle.BIGPICTURE, AndroidStyle.BIGTEXT, AndroidStyle.INBOX or AndroidStyle.MESSAGING.",
        );
    }
  }

  /**
   * tag
   * TODO not sure what this is?
   */
  if (hasOwnProperty(android, 'tag') && android.tag != undefined) {
    if (!isString(android.tag)) {
      throw new Error("'notification.android.tag' expected a string value.");
    }

    if (android.tag.includes('|')) {
      throw new Error(`'notification.android.tag' tag cannot contain the "|" (pipe) character.`);
    }

    out.tag = android.tag;
  }

  /**
   * ticker
   */
  if (hasOwnProperty(android, 'ticker')) {
    if (!isString(android.ticker)) {
      throw new Error("'notification.android.ticker' expected a string value.");
    }

    out.ticker = android.ticker;
  }

  /**
   * timeoutAfter
   */
  if (hasOwnProperty(android, 'timeoutAfter') && android.timeoutAfter != undefined) {
    if (!isNumber(android.timeoutAfter)) {
      throw new Error("'notification.android.timeoutAfter' expected a number value.");
    }

    if (!isValidTimestamp(android.timeoutAfter)) {
      throw new Error("'notification.android.timeoutAfter' invalid millisecond timestamp.");
    }

    out.timeoutAfter = android.timeoutAfter;
  }

  /**
   * showChronometer
   */
  if (hasOwnProperty(android, 'showChronometer')) {
    if (!isBoolean(android.showChronometer)) {
      throw new Error("'notification.android.showChronometer' expected a boolean value.");
    }

    out.showChronometer = android.showChronometer;
  }

  /**
   * vibrate
   * TODO this should be on channels now?
   */
  // if (hasOwnProperty(android, 'vibrate')) {
  //   if (!isBoolean(android.vibrate)) {
  //     throw new Error("'notification.android.vibrate' expected a boolean value.");
  //   }
  //
  //   out.vibrate = android.vibrate;
  // }

  /**
   * vibrationPattern
   */
  if (hasOwnProperty(android, 'vibrationPattern') && android.vibrationPattern != undefined) {
    if (!isArray(android.vibrationPattern) || !isValidVibratePattern(android.vibrationPattern)) {
      throw new Error(
        "'notification.android.vibrationPattern' expected an array containing an even number of positive values.",
      );
    }

    out.vibrationPattern = android.vibrationPattern;
  }

  /**
   * visibility
   */
  if (hasOwnProperty(android, 'visibility') && android.visibility != undefined) {
    if (!Object.values(AndroidVisibility).includes(android.visibility)) {
      throw new Error(
        "'notification.android.visibility' expected a valid AndroidVisibility value.",
      );
    }

    out.visibility = android.visibility;
  }

  /**
   * timestamp
   */
  if (hasOwnProperty(android, 'timestamp') && android.timestamp != undefined) {
    if (!isNumber(android.timestamp)) {
      throw new Error("'notification.android.timestamp' expected a number value.");
    }

    if (!isValidTimestamp(android.timestamp)) {
      throw new Error(
        "'notification.android.timestamp' invalid millisecond timestamp, date must be in the future.",
      );
    }

    out.timestamp = android.timestamp;
  }

  return out;
}
