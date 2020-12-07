/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { objectHasProperty, isArray, isBoolean, isObject, isString } from '../utils';
import { isValidColor, isValidVibratePattern } from './validate';

import { AndroidImportance } from '../types/NotificationAndroid';
import { AndroidChannel, AndroidVisibility } from '../types/NotificationAndroid';

export default function validateAndroidChannel(channel: AndroidChannel): AndroidChannel {
  if (!isObject(channel)) {
    throw new Error("'channel' expected an object value.");
  }

  /**
   * id
   */
  if (!isString(channel.id)) {
    throw new Error("'channel.id' expected a string value.");
  }

  // empty check
  if (!channel.id) {
    throw new Error("'channel.id' expected a valid string id.");
  }

  /**
   * name
   */
  if (!isString(channel.name)) {
    throw new Error("'channel.name' expected a string value.");
  }

  // empty check
  if (!channel.name) {
    throw new Error("'channel.name' expected a valid channel name.");
  }

  /**
   * Defaults
   */
  const out: AndroidChannel = {
    id: channel.id,
    name: channel.name,
    bypassDnd: false,
    lights: true,
    vibration: true,
    badge: true,
    importance: AndroidImportance.DEFAULT,
    visibility: AndroidVisibility.PRIVATE,
  };

  /**
   * badge
   */
  if (objectHasProperty(channel, 'badge')) {
    if (!isBoolean(channel.badge)) {
      throw new Error("'channel.badge' expected a boolean value.");
    }

    out.badge = channel.badge;
  }

  /**
   * bypassDnd
   */
  if (objectHasProperty(channel, 'bypassDnd')) {
    if (!isBoolean(channel.bypassDnd)) {
      throw new Error("'channel.bypassDnd' expected a boolean value.");
    }

    out.bypassDnd = channel.bypassDnd;
  }

  /**
   * description
   */
  if (objectHasProperty(channel, 'description')) {
    if (!isString(channel.description)) {
      throw new Error("'channel.description' expected a string value.");
    }

    out.description = channel.description;
  }

  /**
   * lights
   */
  if (objectHasProperty(channel, 'lights')) {
    if (!isBoolean(channel.lights)) {
      throw new Error("'channel.lights' expected a boolean value.");
    }

    out.lights = channel.lights;
  }

  /**
   * vibration
   */
  if (objectHasProperty(channel, 'vibration')) {
    if (!isBoolean(channel.vibration)) {
      throw new Error("'channel.vibration' expected a boolean value.");
    }

    out.vibration = channel.vibration;
  }

  /**
   * groupId
   */
  if (objectHasProperty(channel, 'groupId')) {
    if (!isString(channel.groupId)) {
      throw new Error("'channel.groupId' expected a string value.");
    }

    out.groupId = channel.groupId;
  }

  /**
   * importance
   */
  if (objectHasProperty(channel, 'importance') && channel.importance !== undefined) {
    if (!Object.values(AndroidImportance).includes(channel.importance)) {
      throw new Error("'channel.importance' expected an Importance value.");
    }

    out.importance = channel.importance;
  }

  /**
   * lightColor
   */
  if (objectHasProperty(channel, 'lightColor') && channel.lightColor !== undefined) {
    if (!isString(channel.lightColor)) {
      throw new Error("'channel.lightColor' expected a string value.");
    }

    if (!isValidColor(channel.lightColor)) {
      throw new Error(
        "'channel.lightColor' invalid color. Expected an AndroidColor or hexadecimal string value",
      );
    }

    out.lightColor = channel.lightColor;
  }

  /**
   * visibility
   */
  if (objectHasProperty(channel, 'visibility') && channel.visibility !== undefined) {
    if (!Object.values(AndroidVisibility).includes(channel.visibility)) {
      throw new Error("'channel.visibility' expected visibility to be an AndroidVisibility value.");
    }

    out.visibility = channel.visibility;
  }

  /**
   * sound
   */
  if (objectHasProperty(channel, 'sound') && channel.sound !== undefined) {
    if (!isString(channel.sound)) {
      throw new Error("'channel.sound' expected a string value.");
    }

    out.sound = channel.sound;
  }

  /**
   * vibrationPattern
   */
  if (objectHasProperty(channel, 'vibrationPattern') && channel.vibrationPattern !== undefined) {
    if (!isArray(channel.vibrationPattern)) {
      throw new Error("'channel.vibrationPattern' expected an array.");
    }

    if (!isValidVibratePattern(channel.vibrationPattern)) {
      throw new Error(
        "'channel.vibrationPattern' expected an array containing an even number of positive values.",
      );
    }

    out.vibrationPattern = channel.vibrationPattern;
  }

  return out;
}
