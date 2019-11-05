/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { AndroidStyle } from '../../types/NotificationAndroid';
import { hasOwnProperty, isString } from './utils';

/**
 * Validates a BigPictureStyle
 * @param style
 * @returns {{type: *, picture: *}}
 */
export function validateAndroidBigPictureStyle(style) {
  if (!isString(style.picture) || !style.picture) {
    throw new Error(
      "'notification.android.style' BigPictureStyle: 'picture' expected a valid string value.",
    );
  }

  // Defaults
  const out = {
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
 *
 * @param style
 */
export function validateAndroidBigTextStyle(style) {
  if (!isString(style.text) || !style.text) {
    throw new Error(
      "'notification.android.style' BigTextStyle: 'text' expected a valid string value.",
    );
  }

  // Defaults
  const out = {
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
