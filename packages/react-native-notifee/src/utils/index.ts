/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Platform } from 'react-native';

export * from './id';
export * from './validate';

export function isError(value: object): boolean {
  if (Object.prototype.toString.call(value) === '[object Error]') {
    return true;
  }

  return value instanceof Error;
}

export function hasOwnProperty<T>(
  target: T,
  property: string | number | symbol,
): property is keyof T {
  return Object.hasOwnProperty.call(target, property);
}

export const isIOS = Platform.OS === 'ios';

export const isAndroid = Platform.OS === 'android';

export function noop(): void {
  // noop-🐈
}
