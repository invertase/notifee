/*
 * Copyright (c) 2016-present Invertase Limited
 */
import { Image } from 'react-native';

import {
  objectHasProperty,
  isArray,
  isArrayOfStrings,
  isBoolean,
  isNumber,
  isObject,
  isString,
  isUndefined,
  isIOS,
} from '../utils';

import { AndroidImportance } from '../types/NotificationAndroid';
import {
  AndroidBadgeIconType,
  AndroidCategory,
  AndroidDefaults,
  AndroidForegroundServiceType,
  AndroidFlags,
  AndroidGroupAlertBehavior,
  AndroidProgress,
  AndroidStyle,
  AndroidVisibility,
  NotificationAndroid,
} from '../types/NotificationAndroid';

import {
  isValidColor,
  isValidLightPattern,
  isValidTimestamp,
  isValidVibratePattern,
} from './validate';

import {
  validateAndroidBigPictureStyle,
  validateAndroidBigTextStyle,
  validateAndroidInboxStyle,
  validateAndroidMessagingStyle,
} from './validateAndroidStyle';
import validateAndroidPressAction from './validateAndroidPressAction';
import validateAndroidFullScreenAction from './validateAndroidFullScreenAction';
import validateAndroidAction from './validateAndroidAction';

export default function validateAndroidNotification(
  android?: NotificationAndroid,
): NotificationAndroid {
  // Notification default values
  const out: NotificationAndroid = {
    autoCancel: true,
    asForegroundService: false,
    lightUpScreen: false,
    badgeIconType: AndroidBadgeIconType.LARGE,
    colorized: false,
    chronometerDirection: 'up',
    defaults: [AndroidDefaults.ALL],
    groupAlertBehavior: AndroidGroupAlertBehavior.ALL,
    groupSummary: false,
    localOnly: false,
    ongoing: false,
    loopSound: false,
    onlyAlertOnce: false,
    importance: AndroidImportance.DEFAULT,
    showTimestamp: false,
    smallIcon: 'ic_launcher',
    showChronometer: false,
    visibility: AndroidVisibility.PRIVATE,
    circularLargeIcon: false,
  };

  /* Skip validating if iOS in release */
  if (isIOS && !__DEV__) return out;

  if (isUndefined(android)) {
    return out;
  }

  if (!isUndefined(android) && !isObject(android)) {
    throw new Error("'notification.android' expected an object value.");
  }

  /**
   * actions
   */
  if (objectHasProperty(android, 'actions') && android.actions !== undefined) {
    if (!isArray(android.actions)) {
      throw new Error("'notification.android.actions' expected an array of AndroidAction types.");
    }

    const actions = [];
    try {
      for (let i = 0; i < android.actions.length; i++) {
        actions.push(validateAndroidAction(android.actions[i]));
      }
    } catch (e: any) {
      throw new Error(`'notification.android.actions' invalid AndroidAction. ${e.message}.`);
    }

    if (actions.length) {
      out.actions = actions;
    }
  }

  /**
   * asForegroundService
   */
  if (objectHasProperty(android, 'asForegroundService')) {
    if (!isBoolean(android.asForegroundService)) {
      throw new Error("'notification.android.asForegroundService' expected a boolean value.");
    }

    out.asForegroundService = android.asForegroundService;
  }

  /**
   * lightUpScreen
   */
  if (objectHasProperty(android, 'lightUpScreen')) {
    if (!isBoolean(android.lightUpScreen)) {
      throw new Error("'notification.android.lightUpScreen' expected a boolean value.");
    }

    out.lightUpScreen = android.lightUpScreen;
  }

  /**
   * autoCancel
   */
  if (objectHasProperty(android, 'autoCancel')) {
    if (!isBoolean(android.autoCancel)) {
      throw new Error("'notification.android.autoCancel' expected a boolean value.");
    }

    out.autoCancel = android.autoCancel;
  }

  /**
   * badgeCount
   */
  if (objectHasProperty(android, 'badgeCount')) {
    if (!isNumber(android.badgeCount)) {
      throw new Error("'notification.android.badgeCount' expected a number value.");
    }

    out.badgeCount = android.badgeCount;
  }

  /**
   * badgeIconType
   */
  if (objectHasProperty(android, 'badgeIconType') && !isUndefined(android.badgeIconType)) {
    if (!Object.values(AndroidBadgeIconType).includes(android.badgeIconType)) {
      throw new Error(
        "'notification.android.badgeIconType' expected a valid AndroidBadgeIconType.",
      );
    }

    out.badgeIconType = android.badgeIconType;
  }

  /**
   * category
   */
  if (objectHasProperty(android, 'category') && !isUndefined(android.category)) {
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
  if (objectHasProperty(android, 'color') && !isUndefined(android.color)) {
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
  if (objectHasProperty(android, 'colorized')) {
    if (!isBoolean(android.colorized)) {
      throw new Error("'notification.android.colorized' expected a boolean value.");
    }

    out.colorized = android.colorized;
  }

  /**
   * chronometerDirection
   */
  if (objectHasProperty(android, 'chronometerDirection')) {
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
  if (objectHasProperty(android, 'defaults') && !isUndefined(android.defaults)) {
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
          "'notification.android.defaults' invalid array value, expected an AndroidDefaults value.",
        );
      }
    }

    out.defaults = android.defaults;
  }

  /**
   * groupId
   */
  if (objectHasProperty(android, 'groupId')) {
    if (!isString(android.groupId)) {
      throw new Error("'notification.android.groupId' expected a string value.");
    }

    out.groupId = android.groupId;
  }

  /**
   * groupAlertBehavior
   */
  if (
    objectHasProperty(android, 'groupAlertBehavior') &&
    !isUndefined(android.groupAlertBehavior)
  ) {
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
  if (objectHasProperty(android, 'groupSummary')) {
    if (!isBoolean(android.groupSummary)) {
      throw new Error("'notification.android.groupSummary' expected a boolean value.");
    }

    out.groupSummary = android.groupSummary;
  }

  if (objectHasProperty(android, 'inputHistory')) {
    if (!isArrayOfStrings(android.inputHistory)) {
      throw new Error("'notification.android.inputHistory' expected an array of string values.");
    }

    out.inputHistory = android.inputHistory;
  }

  /**
   * largeIcon
   */
  if (objectHasProperty(android, 'largeIcon')) {
    if (
      (!isNumber(android.largeIcon) &&
        !isString(android.largeIcon) &&
        !isObject(android.largeIcon)) ||
      (isString(android.largeIcon) && !android.largeIcon.length)
    ) {
      throw new Error(
        "'notification.android.largeIcon' expected a React Native ImageResource value or a valid string URL.",
      );
    }

    if (isNumber(android.largeIcon) || isObject(android.largeIcon)) {
      const image = Image.resolveAssetSource(android.largeIcon);
      out.largeIcon = image.uri;
    } else {
      out.largeIcon = android.largeIcon;
    }

    if (isBoolean(android.circularLargeIcon)) {
      out.circularLargeIcon = android.circularLargeIcon;
    }
  }

  /**
   * lights
   */
  if (objectHasProperty(android, 'lights') && !isUndefined(android.lights)) {
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
            `'notification.android.lights' invalid "on" millisecond value, expected a number greater than 0.`,
          );
        case 'offMs':
          throw new Error(
            `'notification.android.lights' invalid "off" millisecond value, expected a number greater than 0.`,
          );
      }
    }

    out.lights = android.lights;
  }

  /**
   * localOnly
   */
  if (objectHasProperty(android, 'localOnly')) {
    if (!isBoolean(android.localOnly)) {
      throw new Error("'notification.android.localOnly' expected a boolean value.");
    }

    out.localOnly = android.localOnly;
  }

  /**
   * ongoing
   */
  if (objectHasProperty(android, 'ongoing')) {
    if (!isBoolean(android.ongoing)) {
      throw new Error("'notification.android.ongoing' expected a boolean value.");
    }

    out.ongoing = android.ongoing;
  }

  /**
   * loopSound
   */
  if (objectHasProperty(android, 'loopSound')) {
    if (!isBoolean(android.loopSound)) {
      throw new Error("'notification.android.loopSound' expected a boolean value.");
    }

    out.loopSound = android.loopSound;
  }

  /**
   * foregroundServiceTypes
   */
  if (
    objectHasProperty(android, 'foregroundServiceTypes') &&
    !isUndefined(android.foregroundServiceTypes)
  ) {
    if (!isArray(android.foregroundServiceTypes)) {
      throw new Error("'notification.android.foregroundServiceTypes' expected an array.");
    }

    if (android.foregroundServiceTypes.length === 0) {
      throw new Error(
        "'notification.android.foregroundServiceTypes' expected a non empty array containing AndroidForegroundServiceType.",
      );
    }

    const defaults = Object.values(AndroidForegroundServiceType);

    for (let i = 0; i < android.foregroundServiceTypes.length; i++) {
      if (!defaults.includes(android.foregroundServiceTypes[i])) {
        throw new Error(
          "'notification.android.foregroundServiceTypes' invalid array value, expected an AndroidForegroundServiceType value.",
        );
      }
    }

    out.foregroundServiceTypes = android.foregroundServiceTypes;
  }

  /**
   * flags
   */
  if (objectHasProperty(android, 'flags') && !isUndefined(android.flags)) {
    if (!isArray(android.flags)) {
      throw new Error("'notification.android.flags' expected an array.");
    }

    if (android.flags.length === 0) {
      throw new Error("'notification.android.flags' expected an array containing AndroidDefaults.");
    }

    const defaults = Object.values(AndroidFlags);

    for (let i = 0; i < android.flags.length; i++) {
      if (!defaults.includes(android.flags[i])) {
        throw new Error(
          "'notification.android.flags' invalid array value, expected an AndroidFlags value.",
        );
      }
    }

    out.flags = android.flags;
  }

  /**
   * onlyAlertOnce
   */
  if (objectHasProperty(android, 'onlyAlertOnce')) {
    if (!isBoolean(android.onlyAlertOnce)) {
      throw new Error("'notification.android.onlyAlertOnce' expected a boolean value.");
    }

    out.onlyAlertOnce = android.onlyAlertOnce;
  }

  /**
   * pressAction
   */
  if (objectHasProperty(android, 'pressAction') && !isUndefined(android.pressAction)) {
    try {
      out.pressAction = validateAndroidPressAction(android.pressAction);
    } catch (e: any) {
      throw new Error(`'notification.android.pressAction' ${e.message}`);
    }
  }

  /**
   * fullScreenAction
   */
  if (objectHasProperty(android, 'fullScreenAction') && !isUndefined(android.fullScreenAction)) {
    try {
      out.fullScreenAction = validateAndroidFullScreenAction(android.fullScreenAction);
    } catch (e: any) {
      throw new Error(`'notification.android.fullScreenAction' ${e.message}`);
    }
  }

  /**
   * importance
   */
  if (objectHasProperty(android, 'importance') && !isUndefined(android.importance)) {
    if (!Object.values(AndroidImportance).includes(android.importance)) {
      throw new Error("'notification.android.importance' expected a valid Importance.");
    }

    out.importance = android.importance;
  }

  /**
   * progress
   */
  if (objectHasProperty(android, 'progress') && !isUndefined(android.progress)) {
    if (!isObject(android.progress)) {
      throw new Error("'notification.android.progress' expected an object value.");
    }

    const progress: AndroidProgress = {
      indeterminate: false,
    };

    if (objectHasProperty(android.progress, 'indeterminate')) {
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
   * showTimestamp
   */
  if (objectHasProperty(android, 'showTimestamp')) {
    if (!isBoolean(android.showTimestamp)) {
      throw new Error("'notification.android.showTimestamp' expected a boolean value.");
    }

    out.showTimestamp = android.showTimestamp;
  }

  /**
   * smallIcon
   */
  if (objectHasProperty(android, 'smallIcon') && !isUndefined(android.smallIcon)) {
    if (!isString(android.smallIcon)) {
      throw new Error("'notification.android.smallIcon' expected value to be a string.");
    }

    out.smallIcon = android.smallIcon;
  }

  /**
   * smallIconLevel
   */
  if (objectHasProperty(android, 'smallIconLevel') && !isUndefined(android.smallIcon)) {
    if (!isNumber(android.smallIconLevel)) {
      throw new Error("'notification.android.smallIconLevel' expected value to be a number.");
    }

    out.smallIconLevel = android.smallIconLevel;
  }

  /**
   * sortKey
   */
  if (objectHasProperty(android, 'sortKey')) {
    if (!isString(android.sortKey)) {
      throw new Error("'notification.android.sortKey' expected a string value.");
    }

    out.sortKey = android.sortKey;
  }

  /**
   * style
   */

  if (objectHasProperty(android, 'style') && !isUndefined(android.style)) {
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
   */
  if (objectHasProperty(android, 'tag') && android.tag !== undefined) {
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
  if (objectHasProperty(android, 'ticker')) {
    if (!isString(android.ticker)) {
      throw new Error("'notification.android.ticker' expected a string value.");
    }

    out.ticker = android.ticker;
  }

  /**
   * timeoutAfter
   */
  if (objectHasProperty(android, 'timeoutAfter') && android.timeoutAfter !== undefined) {
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
  if (objectHasProperty(android, 'showChronometer')) {
    if (!isBoolean(android.showChronometer)) {
      throw new Error("'notification.android.showChronometer' expected a boolean value.");
    }

    out.showChronometer = android.showChronometer;
  }

  /**
   * vibrationPattern
   */
  if (objectHasProperty(android, 'vibrationPattern') && android.vibrationPattern !== undefined) {
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
  if (objectHasProperty(android, 'visibility') && android.visibility !== undefined) {
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
  if (objectHasProperty(android, 'timestamp') && android.timestamp !== undefined) {
    if (!isNumber(android.timestamp)) {
      throw new Error("'notification.android.timestamp' expected a number value.");
    }

    if (!isValidTimestamp(android.timestamp)) {
      throw new Error(
        "'notification.android.timestamp' invalid millisecond timestamp, date must be a positive number",
      );
    }

    out.timestamp = android.timestamp;
  }

  /**
   * sound
   */
  if (objectHasProperty(android, 'sound') && android.sound !== undefined) {
    if (!isString(android.sound)) {
      throw new Error("'notification.sound' expected a valid sound string.");
    }

    out.sound = android.sound;
  }

  return out;
}
