/*
 * Copyright (c) 2016-present Invertase Limited
 */

import {
  AndroidBigPictureStyle,
  AndroidBigTextStyle,
  AndroidInboxStyle,
  AndroidStyle,
} from '../../types/NotificationAndroid';
import { hasOwnProperty, isArray, isString } from '../utils';

/**
 * Validates a BigPictureStyle
 */
export function validateAndroidBigPictureStyle(
  style: AndroidBigPictureStyle,
): AndroidBigPictureStyle {
  if (!isString(style.picture) || !style.picture) {
    throw new Error(
      "'notification.android.style' BigPictureStyle: 'picture' expected a valid string value.",
    );
  }

  // Defaults
  const out: AndroidBigPictureStyle = {
    type: AndroidStyle.BIGPICTURE,
    picture: style.picture,
  };

  if (hasOwnProperty(style, 'largeIcon')) {
    if (!isString(style.largeIcon)) {
      throw new Error(
        "'notification.android.style' BigPictureStyle: 'largeIcon' expected a string value.",
      );
    }

    out.largeIcon = style.largeIcon;
  }

  if (hasOwnProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error(
        "'notification.android.style' BigPictureStyle: 'title' expected a string value.",
      );
    }

    out.title = style.title;
  }

  if (hasOwnProperty(style, 'summary')) {
    if (!isString(style.summary)) {
      throw new Error(
        "'notification.android.style' BigPictureStyle: 'summary' expected a string value.",
      );
    }

    out.summary = style.summary;
  }

  return out;
}

/**
 * Validates a BigTextStyle
 */
export function validateAndroidBigTextStyle(style: AndroidBigTextStyle): AndroidBigTextStyle {
  if (!isString(style.text) || !style.text) {
    throw new Error(
      "'notification.android.style' BigTextStyle: 'text' expected a valid string value.",
    );
  }

  // Defaults
  const out: AndroidBigTextStyle = {
    type: AndroidStyle.BIGTEXT,
    text: style.text,
  };

  if (hasOwnProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error(
        "'notification.android.style' BigTextStyle: 'title' expected a string value.",
      );
    }

    out.title = style.title;
  }

  if (hasOwnProperty(style, 'summary')) {
    if (!isString(style.summary)) {
      throw new Error(
        "'notification.android.style' BigTextStyle: 'summary' expected a string value.",
      );
    }

    out.summary = style.summary;
  }

  return out;
}

/**
 * Validates a InboxStyle
 */
export function validateAndroidInboxStyle(style: AndroidInboxStyle): AndroidInboxStyle {
  if (!isArray(style.lines)) {
    throw new Error("'notification.android.style' InboxStyle: 'lines' expected an array.");
  }

  for (let i = 0; i < style.lines.length; i++) {
    const line = style.lines[i];

    if (!isString(line)) {
      throw new Error(
        `'notification.android.style' InboxStyle: 'lines' expected a string value at array index ${i}.`,
      );
    }
  }

  const out: AndroidInboxStyle = {
    type: AndroidStyle.INBOX,
    lines: style.lines,
  };

  if (hasOwnProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error("'notification.android.style' InboxStyle: 'title' expected a string value.");
    }

    out.title = style.title;
  }

  if (hasOwnProperty(style, 'summary')) {
    if (!isString(style.summary)) {
      throw new Error(
        "'notification.android.style' InboxStyle: 'summary' expected a string value.",
      );
    }

    out.summary = style.summary;
  }

  return out;
}
