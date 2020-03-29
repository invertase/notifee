/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationPressAction } from '../types/Notification';
import { isObject, isString, isUndefined } from '../utils';

export default function validateAndroidPressAction(
  pressAction: NotificationPressAction,
): NotificationPressAction {
  if (!isObject(pressAction)) {
    throw new Error("'pressAction' expected an object value.");
  }

  if (!isString(pressAction.id) || pressAction.id.length === 0) {
    throw new Error("'id' expected a non-empty string value.");
  }

  const out: NotificationPressAction = {
    id: pressAction.id,
  };

  if (!isUndefined(pressAction.launchActivity)) {
    if (!isString(pressAction.launchActivity)) {
      throw new Error("'launchActivity' expected a string value.");
    }

    out.launchActivity = pressAction.launchActivity;
  }

  if (!isUndefined(pressAction.mainComponent)) {
    if (!isString(pressAction.mainComponent)) {
      throw new Error("'mainComponent' expected a string value.");
    }

    out.mainComponent = pressAction.mainComponent;
  }

  return out;
}
