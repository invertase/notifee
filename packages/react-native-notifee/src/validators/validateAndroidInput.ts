/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { AndroidInput } from '../types/NotificationAndroid';
import { checkForProperty, isArrayOfStrings, isBoolean, isString } from '../utils';

export default function validateAndroidInput(input?: AndroidInput): AndroidInput {
  const out: AndroidInput = {
    allowFreeFormInput: true,
    allowGeneratedReplies: true,
  };

  if (!input) {
    return out;
  }

  if (checkForProperty(input, 'allowFreeFormInput')) {
    if (!isBoolean(input.allowFreeFormInput)) {
      throw new Error("'input.allowFreeFormInput' expected a boolean value.");
    }

    out.allowFreeFormInput = input.allowFreeFormInput;
  }

  if (checkForProperty(input, 'allowGeneratedReplies')) {
    if (!isBoolean(input.allowGeneratedReplies)) {
      throw new Error("'input.allowGeneratedReplies' expected a boolean value.");
    }

    out.allowGeneratedReplies = input.allowGeneratedReplies;
  }

  if (!out.allowFreeFormInput && (!input.choices || input.choices.length === 0)) {
    throw new Error("'input.allowFreeFormInput' when false, you must provide at least one choice.");
  }

  if (checkForProperty(input, 'choices')) {
    if (!isArrayOfStrings(input.choices) || input.choices.length === 0) {
      throw new Error("'input.choices' expected an array of string values.");
    }

    out.choices = input.choices;
  }

  if (checkForProperty(input, 'editableChoices')) {
    if (!isBoolean(input.editableChoices)) {
      throw new Error("'input.editableChoices' expected a boolean value.");
    }

    out.editableChoices = input.editableChoices;
  }

  if (checkForProperty(input, 'placeholder')) {
    if (!isString(input.placeholder)) {
      throw new Error("'input.placeholder' expected a string value.");
    }

    out.placeholder = input.placeholder;
  }

  if (out.editableChoices == true && !out.allowFreeFormInput) {
    throw new Error("'input.editableChoices' when true, allowFreeFormInput must also be true.");
  }

  return out;
}
