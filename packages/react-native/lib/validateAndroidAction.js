/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { isArray, isBoolean, isObject, isString, hasOwnProperty } from './utils';

import AndroidSemanticAction from './AndroidSemanticAction';

export default function validateAndroidAction(action) {
  if (!isObject(action)) {
    throw new Error("'action' expected an object value.");
  }

  if (!isString(action.key) || !action.key) {
    throw new Error("'action.key' expected a string value.");
  }

  if (!isString(action.key) || !action.icon) {
    throw new Error("'action.icon' expected a string value.");
  }

  // required?
  if (!isString(action.title) || !action.title) {
    throw new Error("'action.title' expected a string value.");
  }

  const out = {
    key: action.key,
    icon: action.icon,
    title: action.title,
  };

  if (hasOwnProperty(action, 'allowGeneratedReplies')) {
    if (!isBoolean(action.allowGeneratedReplies)) {
      throw new Error("'action.allowGeneratedReplies' expected a boolean value.");
    }

    out.allowGeneratedReplies = action.allowGeneratedReplies;
  }

  if (hasOwnProperty(action, 'remoteInputs')) {
    if (!isArray(action.remoteInputs)) {
      throw new Error("'action.remoteInputs' expected an array of AndroidRemoteInput.");
    }

    // todo validate remote input

    out.remoteInputs = action.remoteInputs;
  }

  if (hasOwnProperty(action, 'semanticAction')) {
    if (!Object.values(AndroidSemanticAction).includes(action.semanticAction)) {
      throw new Error("'action.semanticAction' expected an AndroidSemanticAction.");
    }

    out.semanticAction = action.semanticAction;
  }

  if (hasOwnProperty(action, 'showsUserInterface')) {
    if (!isBoolean(action.showsUserInterface)) {
      throw new Error("'action.showsUserInterface' expected a boolean value.");
    }

    out.showsUserInterface = action.showsUserInterface;
  }

  return out;
}
