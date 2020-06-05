/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { AndroidColor } from '../types/NotificationAndroid';
import { isNumber } from '../utils';

/**
 * Validates any hexadecimal (optional transparency)
 * @param color
 * @returns {boolean}
 */
export function isValidColor(color: string): boolean {
  if (Object.values(AndroidColor).includes(color as AndroidColor)) {
    return true;
  }

  if (!color.startsWith('#')) {
    return false;
  }

  // exclude #
  const length = color.length - 1;
  return length === 6 || length === 8;
}

/**
 * Checks the timestamp is at some point in the future.
 * @param timestamp
 * @returns {boolean}
 */
export function isValidTimestamp(timestamp: number): boolean {
  return timestamp > 0;
}

/**
 * Ensures all values in the pattern are valid
 * @param pattern {array}
 */
export function isValidVibratePattern(pattern: number[]): boolean {
  if (pattern.length % 2 !== 0) {
    return false;
  }
  for (let i = 0; i < pattern.length; i++) {
    const ms = pattern[i];
    if (!isNumber(ms)) {
      return false;
    }
    if (ms <= 0) {
      return false;
    }
  }
  return true;
}

/**
 * Ensures a given light pattern is valid
 * @param pattern {array}
 */
type LightPattern = [string, number, number];
type ValidLightPattern = [boolean] | [boolean, 'color' | 'onMs' | 'offMs'];

export function isValidLightPattern(pattern: LightPattern): ValidLightPattern {
  const [color, onMs, offMs] = pattern;

  if (!isValidColor(color)) {
    return [false, 'color'];
  }

  if (!isNumber(onMs)) {
    return [false, 'onMs'];
  }

  if (!isNumber(offMs)) {
    return [false, 'offMs'];
  }
  if (onMs < 1) {
    return [false, 'onMs'];
  }
  if (offMs < 1) {
    return [false, 'offMs'];
  }

  return [true];
}
