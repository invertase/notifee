/*
 * Copyright (c) 2016-present Invertase Limited
 */
import { Image } from 'react-native';
import {
  AndroidBigPictureStyle,
  AndroidBigTextStyle,
  AndroidInboxStyle,
  AndroidMessagingStyle,
  AndroidMessagingStyleMessage,
  AndroidPerson,
  AndroidStyle,
} from '../types/NotificationAndroid';
import { objectHasProperty, isArray, isBoolean, isNumber, isObject, isString } from '../utils';

/**
 * Validates a BigPictureStyle
 */
export function validateAndroidBigPictureStyle(
  style: AndroidBigPictureStyle,
): AndroidBigPictureStyle {
  if (
    (!isString(style.picture) && !isNumber(style.picture) && !isObject(style.picture)) ||
    (isString(style.picture) && !style.picture.length)
  ) {
    throw new Error(
      "'notification.android.style' BigPictureStyle: 'picture' expected a number or object created using the 'require()' method or a valid string URL.",
    );
  }

  // Defaults
  const out: AndroidBigPictureStyle = {
    type: AndroidStyle.BIGPICTURE,
    picture: style.picture,
  };

  if (isNumber(style.picture) || isObject(style.picture)) {
    const image = Image.resolveAssetSource(style.picture);

    out.picture = image.uri;
  }

  if (objectHasProperty(style, 'largeIcon')) {
    if (
      style.largeIcon !== null &&
      !isString(style.largeIcon) &&
      !isNumber(style.largeIcon) &&
      !isObject(style.largeIcon)
    ) {
      throw new Error(
        "'notification.android.style' BigPictureStyle: 'largeIcon' expected a React Native ImageResource value or a valid string URL.",
      );
    }
    if (isNumber(style.largeIcon) || isObject(style.largeIcon)) {
      const image = Image.resolveAssetSource(style.largeIcon);

      out.largeIcon = image.uri;
    } else {
      out.largeIcon = style.largeIcon;
    }
  }

  if (objectHasProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error(
        "'notification.android.style' BigPictureStyle: 'title' expected a string value.",
      );
    }

    out.title = style.title;
  }

  if (objectHasProperty(style, 'summary')) {
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

  if (objectHasProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error(
        "'notification.android.style' BigTextStyle: 'title' expected a string value.",
      );
    }

    out.title = style.title;
  }

  if (objectHasProperty(style, 'summary')) {
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

  if (objectHasProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error("'notification.android.style' InboxStyle: 'title' expected a string value.");
    }

    out.title = style.title;
  }

  if (objectHasProperty(style, 'summary')) {
    if (!isString(style.summary)) {
      throw new Error(
        "'notification.android.style' InboxStyle: 'summary' expected a string value.",
      );
    }

    out.summary = style.summary;
  }

  return out;
}

/**
 * Validates an AndroidPerson
 */
export function validateAndroidPerson(person: AndroidPerson): AndroidPerson {
  if (!isString(person.name)) {
    throw new Error("'person.name' expected a string value.");
  }

  const out: AndroidPerson = {
    name: person.name,
    bot: false,
    important: false,
  };

  if (objectHasProperty(person, 'id')) {
    if (!isString(person.id)) {
      throw new Error("'person.id' expected a string value.");
    }

    out.id = person.id;
  }

  if (objectHasProperty(person, 'bot')) {
    if (!isBoolean(person.bot)) {
      throw new Error("'person.bot' expected a boolean value.");
    }

    out.bot = person.bot;
  }

  if (objectHasProperty(person, 'important')) {
    if (!isBoolean(person.important)) {
      throw new Error("'person.important' expected a boolean value.");
    }

    out.important = person.important;
  }

  if (objectHasProperty(person, 'icon')) {
    if (!isString(person.icon)) {
      throw new Error("'person.icon' expected a string value.");
    }

    out.icon = person.icon;
  }

  if (objectHasProperty(person, 'uri')) {
    if (!isString(person.uri)) {
      throw new Error("'person.uri' expected a string value.");
    }

    out.uri = person.uri;
  }

  return out;
}

export function validateAndroidMessagingStyleMessage(
  message: AndroidMessagingStyleMessage,
): AndroidMessagingStyleMessage {
  //text, timestamp, person
  if (!isString(message.text)) {
    throw new Error("'message.text' expected a string value.");
  }

  if (!isNumber(message.timestamp)) {
    throw new Error("'message.timestamp' expected a number value.");
  }

  const out: AndroidMessagingStyleMessage = {
    text: message.text,
    timestamp: message.timestamp,
  };

  if (objectHasProperty(message, 'person') && message.person !== undefined) {
    try {
      out.person = validateAndroidPerson(message.person);
    } catch (e: any) {
      throw new Error(`'message.person' is invalid. ${e.message}`);
    }
  }

  return out;
}

/**
 * Validates a MessagingStyle
 */
export function validateAndroidMessagingStyle(style: AndroidMessagingStyle): AndroidMessagingStyle {
  if (!isObject(style.person)) {
    throw new Error("'notification.android.style' MessagingStyle: 'person' an object value.");
  }

  let person;
  const messages: AndroidMessagingStyleMessage[] = [];

  try {
    person = validateAndroidPerson(style.person);
  } catch (e: any) {
    throw new Error(`'notification.android.style' MessagingStyle: ${e.message}.`);
  }

  if (!isArray(style.messages)) {
    throw new Error(
      "'notification.android.style' MessagingStyle: 'messages' expected an array value.",
    );
  }

  for (let i = 0; i < style.messages.length; i++) {
    try {
      messages.push(validateAndroidMessagingStyleMessage(style.messages[i]));
    } catch (e: any) {
      throw new Error(
        `'notification.android.style' MessagingStyle: invalid message at index ${i}. ${e.message}`,
      );
    }
  }

  const out: AndroidMessagingStyle = {
    type: AndroidStyle.MESSAGING,
    person,
    messages,
    group: false,
  };

  if (objectHasProperty(style, 'title')) {
    if (!isString(style.title)) {
      throw new Error(
        "'notification.android.style' MessagingStyle: 'title' expected a string value.",
      );
    }

    out.title = style.title;
  }

  if (objectHasProperty(style, 'group')) {
    if (!isBoolean(style.group)) {
      throw new Error(
        "'notification.android.style' MessagingStyle: 'group' expected a boolean value.",
      );
    }

    out.group = style.group;
  }

  return out;
}
