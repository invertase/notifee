import { IOSInput } from '..';
import { hasOwnProperty, isObject, isString, isUndefined } from '../utils';

export default function validateIOSInput(input?: IOSInput): IOSInput {
  const out: IOSInput = {};

  if (!input) {
    return out;
  }

  if (!isObject(input)) {
    throw new Error('expected an object value.');
  }

  if (hasOwnProperty(input, 'button') && !isUndefined(input.button)) {
    if (!isString(input.button)) {
      throw new Error("'button' expected a string value.");
    }

    out.button = input.button;
  }

  if (hasOwnProperty(input, 'placeholder') && !isUndefined(input.placeholder)) {
    if (!isString(input.placeholder)) {
      throw new Error("'placeholder' expected a string value.");
    }

    out.placeholder = input.placeholder;
  }

  return out;
}
