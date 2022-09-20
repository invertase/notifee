/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { objectHasProperty, isObject, isString, isUndefined } from '../utils';

import { AndroidAction } from '../types/NotificationAndroid';
import validateAndroidPressAction from './validateAndroidPressAction';
import validateAndroidInput from './validateAndroidInput';

export default function validateAndroidAction(action: AndroidAction): AndroidAction {
  if (!isObject(action)) {
    throw new Error("'action' expected an object value.");
  }

  if (!isString(action.title) || !action.title) {
    throw new Error("'action.title' expected a string value.");
  }

  let pressAction;

  try {
    pressAction = validateAndroidPressAction(action.pressAction);
  } catch (e: any) {
    throw new Error(`'action' ${e.message}.`);
  }

  const out: AndroidAction = {
    title: action.title,
    pressAction,
  };

  if (objectHasProperty(action, 'icon') && !isUndefined(action.icon)) {
    if (!isString(action.icon) || !action.icon) {
      throw new Error("'action.icon' expected a string value.");
    }

    out.icon = action.icon;
  }

  if (objectHasProperty(action, 'input') && !isUndefined(action.input)) {
    if (action.input === true) {
      out.input = validateAndroidInput();
    } else {
      try {
        out.input = validateAndroidInput(action.input);
      } catch (e: any) {
        throw new Error(`'action.input' ${e.message}.`);
      }
    }
  }

  return out;
}
