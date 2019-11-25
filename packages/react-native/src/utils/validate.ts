/*
 * Copyright (c) 2016-present Invertase Limited
 */

export function isNull(value: any): boolean {
  return value === null;
}

export function isObject(value: any): boolean {
  return value ? typeof value === 'object' && !Array.isArray(value) && !isNull(value) : false;
}

/**
 * Simple is date check
 * https://stackoverflow.com/a/44198641
 * @param value
 * @returns {boolean}
 */
export function isDate(value: any): boolean {
  // use the global isNaN() and not Number.isNaN() since it will validate an Invalid Date
  return value && Object.prototype.toString.call(value) === '[object Date]' && !isNaN(value);
}

export function isFunction(value: any): boolean {
  return value ? typeof value === 'function' : false;
}

export function isString(value: any): boolean {
  return typeof value === 'string';
}

export function isNumber(value: any): boolean {
  return typeof value === 'number';
}

export function isFinite(value: any): boolean {
  return Number.isFinite(value);
}

export function isInteger(value: any): boolean {
  return Number.isInteger(value);
}

export function isBoolean(value: any): boolean {
  return typeof value === 'boolean';
}

export function isArray(value: any): boolean {
  return Array.isArray(value);
}

export function isUndefined(value: any): boolean {
  return value == undefined;
}

export function objectKeyValuesAreStrings(object: object): boolean {
  if (!isObject(object)) {
    return false;
  }

  const entries = Object.entries(object);

  for (let i = 0; i < entries.length; i++) {
    const [key, value] = entries[i];
    if (!isString(key) || !isString(value)) {
      return false;
    }
  }

  return true;
}

/**
 * /^[a-zA-Z0-9_]+$/
 *
 * @param value
 * @returns {boolean}
 */
const AlphaNumericUnderscore = /^[a-zA-Z0-9_]+$/;

export function isAlphaNumericUnderscore(value: string): boolean {
  return AlphaNumericUnderscore.test(value);
}

/**
 * URL test
 * @param url
 * @returns {boolean}
 */
const IS_VALID_URL_REGEX = /^(http|https):\/\/[^ "]+$/;

export function isValidUrl(url: string): boolean {
  return IS_VALID_URL_REGEX.test(url);
}

/**
 * Array includes
 *
 * @param value
 * @param oneOf
 * @returns {boolean}
 */
export function isOneOf(value: string | number, oneOf: (string | number)[]): boolean {
  if (!isArray(oneOf)) {
    return false;
  }
  return oneOf.includes(value);
}
