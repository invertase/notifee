/*
 * Copyright (c) 2016-present Invertase Limited
 */

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import { Image } from 'react-native';

import {
  generateId,
  isBoolean,
  isNumber,
  isObject,
  isString,
  isUndefined,
  objectHasProperty,
} from '../utils';
import {
  IOSAttachmentThumbnailClippingRect,
  IOSNotificationAttachment,
} from '../types/NotificationIOS';

export default function validateIOSAttachment(
  attachment: IOSNotificationAttachment,
): IOSNotificationAttachment {
  if (!isObject(attachment)) {
    throw new Error("'attachment' expected an object value.");
  }

  if (
    (!isString(attachment.url) && !isNumber(attachment.url) && !isObject(attachment.url)) ||
    (isString(attachment.url) && !attachment.url.length)
  ) {
    throw new Error(
      "'attachment.url' expected a React Native ImageResource value or a valid string URL.",
    );
  }

  const out: IOSNotificationAttachment = {
    url: attachment.url,
    thumbnailHidden: false,
  };

  if (isNumber(attachment.url) || isObject(attachment.url)) {
    const image = Image.resolveAssetSource(attachment.url);
    out.url = image.uri;
  }

  if (objectHasProperty(attachment, 'id') && !isUndefined(attachment.id)) {
    if (!isString(attachment.id)) {
      throw new Error("'attachment.id' expected a string value.");
    }
    out.id = attachment.id;
  } else {
    out.id = generateId();
  }

  if (objectHasProperty(attachment, 'typeHint') && !isUndefined(attachment.typeHint)) {
    if (!isString(attachment.typeHint)) {
      throw new Error("'attachment.typeHint' expected a string value.");
    }

    out.typeHint = attachment.typeHint;
  }

  if (
    objectHasProperty(attachment, 'thumbnailClippingRect') &&
    !isUndefined(attachment.thumbnailClippingRect)
  ) {
    try {
      out.thumbnailClippingRect = validateThumbnailClippingRect(attachment.thumbnailClippingRect);
    } catch (e: any) {
      throw new Error(`'attachment.thumbnailClippingRect' is invalid. ${e.message}`);
    }
  }

  if (
    objectHasProperty(attachment, 'thumbnailHidden') &&
    !isUndefined(attachment.thumbnailHidden)
  ) {
    if (!isBoolean(attachment.thumbnailHidden)) {
      throw new Error("'attachment.thumbnailHidden' must be a boolean value if specified.");
    }

    out.thumbnailHidden = attachment.thumbnailHidden;
  }

  if (objectHasProperty(attachment, 'thumbnailTime') && !isUndefined(attachment.thumbnailTime)) {
    if (!isNumber(attachment.thumbnailTime)) {
      throw new Error("'attachment.thumbnailTime' must be a number value if specified.");
    } else {
      out.thumbnailTime = attachment.thumbnailTime;
    }
  }

  return out;
}

/**
 * Validates a ThumbnailClippingRect
 */
export function validateThumbnailClippingRect(
  thumbnailClippingRect: IOSAttachmentThumbnailClippingRect,
): IOSAttachmentThumbnailClippingRect {
  if (objectHasProperty(thumbnailClippingRect, 'x')) {
    if (!isNumber(thumbnailClippingRect.x)) {
      throw new Error("'thumbnailClippingRect.x' expected a number value.");
    }
  }

  if (objectHasProperty(thumbnailClippingRect, 'y')) {
    if (!isNumber(thumbnailClippingRect.y)) {
      throw new Error("'thumbnailClippingRect.y' expected a number value.");
    }
  }

  if (objectHasProperty(thumbnailClippingRect, 'width')) {
    if (!isNumber(thumbnailClippingRect.width)) {
      throw new Error("'thumbnailClippingRect.width' expected a number value.");
    }
  }

  if (objectHasProperty(thumbnailClippingRect, 'height')) {
    if (!isNumber(thumbnailClippingRect.height)) {
      throw new Error("'thumbnailClippingRect.height' expected a number value.");
    }
  }

  // Defaults
  return {
    x: thumbnailClippingRect.x,
    y: thumbnailClippingRect.y,
    height: thumbnailClippingRect.height,
    width: thumbnailClippingRect.width,
  };
}
