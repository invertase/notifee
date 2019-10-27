/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { hasOwnProperty, isBoolean, isNumber, isObject } from './core/utils';
import AndroidRepeatInterval from './AndroidRepeatInterval';

export default function validateSchedule(schedule) {
  if (!isObject(schedule)) {
    throw new Error("'schedule' expected an object value.");
  }

  if (!isNumber(schedule.fireDate)) {
    throw new Error("'schedule.fireDate' expected a number value.");
  }

  const now = Date.now();

  if (schedule.fireDate <= now) {
    throw new Error("'schedule.fireDate' date must be in the future.");
  }

  const out = {
    fireDate: schedule.fireDate,
    exact: false,
  };

  if (hasOwnProperty(schedule, 'exact')) {
    if (!isBoolean(schedule.exact)) {
      throw new Error("'schedule.exact' expected a boolean value.");
    }

    out.exact = schedule.exact;
  }

  if (hasOwnProperty(schedule, 'repeatInterval')) {
    if (
      schedule.repeatInterval !== AndroidRepeatInterval.MINUTE ||
      schedule.repeatInterval !== AndroidRepeatInterval.HOUR ||
      schedule.repeatInterval !== AndroidRepeatInterval.DAY ||
      schedule.repeatInterval !== AndroidRepeatInterval.WEEK
    ) {
      throw new Error("'schedule.repeatInterval' expected a valid AndroidRepeatInterval.");
    }

    out.repeatInterval = schedule.repeatInterval;
  }

  return out;
}
