/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { hasOwnProperty, isObject, isString } from '../utils';
import { AndroidChannelGroup } from '../../types/NotificationAndroid';

export default function validateAndroidChannelGroup(
  group: AndroidChannelGroup,
): AndroidChannelGroup {
  if (!isObject(group)) {
    throw new Error("'group' expected an object value.");
  }

  /**
   * channelGroupId
   */
  if (!isString(group.channelGroupId) || !group.channelGroupId) {
    throw new Error("'group.channelGroupId' expected a string value.");
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
    channelGroupId: group.channelGroupId,
    name: group.name,
  };

  /**
   * description
   */
  if (hasOwnProperty(group, 'description')) {
    if (!isString(group.description)) {
      throw new Error("'group.description' expected a string value.");
    }

    out.description = group.description;
  }

  return out;
}
