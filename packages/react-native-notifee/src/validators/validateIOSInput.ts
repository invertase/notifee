import { IOSInput } from '../types/NotificationIOS';
import { checkForProperty, isBoolean, isObject, isString, isUndefined } from '../utils';

export default function validateIOSInput(input?: IOSInput): IOSInput {
  const out: IOSInput = {};

  // default value
  if (!input) {
    return out;
  }

  // if true, empty object
  if (isBoolean(input)) {
    return out;
  }

  if (!isObject(input)) {
    throw new Error('expected an object value.');
  }

  if (checkForProperty(input, 'buttonText') && !isUndefined(input.buttonText)) {
    if (!isString(input.buttonText)) {
      throw new Error("'buttonText' expected a string value.");
    }

    out.buttonText = input.buttonText;
  }

  if (checkForProperty(input, 'placeholderText') && !isUndefined(input.placeholderText)) {
    if (!isString(input.placeholderText)) {
      throw new Error("'placeholderText' expected a string value.");
    }

    out.placeholderText = input.placeholderText;
  }

  return out;
}
