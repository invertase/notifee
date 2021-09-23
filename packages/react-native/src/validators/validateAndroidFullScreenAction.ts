/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NotificationFullScreenAction } from '../types/Notification';
import { isObject, isString, isUndefined, isArray, isNumber } from '../utils';

const LAUNCH_ACTIVITY_DEFAULT_VALUE = 'default';
const PRESS_ACTION_DEFAULT_VALUE = 'default';

export default function validateAndroidFullScreenAction(
  fullScreenAction: NotificationFullScreenAction,
): NotificationFullScreenAction {
  if (!isObject(fullScreenAction)) {
    throw new Error("'fullScreenAction' expected an object value.");
  }

  if (!isString(fullScreenAction.id) || fullScreenAction.id.length === 0) {
    throw new Error("'id' expected a non-empty string value.");
  }

  const out: NotificationFullScreenAction = {
    id: fullScreenAction.id,
  };

  if (!isUndefined(fullScreenAction.launchActivity)) {
    if (!isString(fullScreenAction.launchActivity)) {
      throw new Error("'launchActivity' expected a string value.");
    }

    out.launchActivity = fullScreenAction.launchActivity;
  } else if (fullScreenAction.id === PRESS_ACTION_DEFAULT_VALUE) {
    // Set default value for launchActivity
    out.launchActivity = LAUNCH_ACTIVITY_DEFAULT_VALUE;
  }

  if (!isUndefined(fullScreenAction.launchActivityFlags)) {
    if (!isArray(fullScreenAction.launchActivityFlags)) {
      throw new Error(
        "'launchActivityFlags' must be an array of `AndroidLaunchActivityFlag` values.",
      );
    }

    // quick sanity check on first item only
    if (fullScreenAction.launchActivityFlags.length) {
      if (!isNumber(fullScreenAction.launchActivityFlags[0])) {
        throw new Error(
          "'launchActivityFlags' must be an array of `AndroidLaunchActivityFlag` values.",
        );
      }
    }

    out.launchActivityFlags = fullScreenAction.launchActivityFlags;
  }

  if (!isUndefined(fullScreenAction.mainComponent)) {
    if (!isString(fullScreenAction.mainComponent)) {
      throw new Error("'mainComponent' expected a string value.");
    }

    out.mainComponent = fullScreenAction.mainComponent;
  }

  return out;
}
