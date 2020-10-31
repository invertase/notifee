/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationPressAction } from '../types/Notification';
import { isObject, isString, isUndefined, isArray, isNumber } from '../utils';

const LAUNCH_ACTIVITY_DEFAULT_VALUE = 'default';
const PRESS_ACTION_DEFAULT_VALUE = 'default';

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
  } else if (pressAction.id === PRESS_ACTION_DEFAULT_VALUE) {
    // Set default value for launchActivity
    out.launchActivity = LAUNCH_ACTIVITY_DEFAULT_VALUE;
  }

  if (!isUndefined(pressAction.launchActivityFlags)) {
    if (!isArray(pressAction.launchActivityFlags)) {
      throw new Error(
        "'launchActivityFlags' must be an array of `AndroidLaunchActivityFlag` values.",
      );
    }

    // quick sanity check on first item only
    if (pressAction.launchActivityFlags.length) {
      if (!isNumber(pressAction.launchActivityFlags[0])) {
        throw new Error(
          "'launchActivityFlags' must be an array of `AndroidLaunchActivityFlag` values.",
        );
      }
    }

    out.launchActivityFlags = pressAction.launchActivityFlags;
  }

  if (!isUndefined(pressAction.mainComponent)) {
    if (!isString(pressAction.mainComponent)) {
      throw new Error("'mainComponent' expected a string value.");
    }

    out.mainComponent = pressAction.mainComponent;
  }

  return out;
}
