/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { objectHasProperty, isObject, isString } from '../utils';
import { AndroidChannelGroup } from '../types/NotificationAndroid';

export default function validateAndroidChannelGroup(
  group: AndroidChannelGroup,
): AndroidChannelGroup {
  if (!isObject(group)) {
    throw new Error("'group' expected an object value.");
  }

  /**
   * id
   */
  if (!isString(group.id) || !group.id) {
    throw new Error("'group.id' expected a string value.");
  }

  /**
   * name
   */
  if (!isString(group.name) || !group.name) {
    throw new Error("'group.name' expected a string value.");
  }

  /**
   * Defaults
   */
  const out: AndroidChannelGroup = {
    id: group.id,
    name: group.name,
  };

  /**
   * description
   */
  if (objectHasProperty(group, 'description')) {
    if (!isString(group.description)) {
      throw new Error("'group.description' expected a string value.");
    }

    out.description = group.description;
  }

  return out;
}
