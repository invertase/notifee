/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { hasOwnProperty, isBoolean, isNumber, isObject } from './utils';
import { NotificationRepeatInterval } from '../../types/Notification';

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
      schedule.repeatInterval !== NotificationRepeatInterval.MINUTE ||
      schedule.repeatInterval !== NotificationRepeatInterval.HOUR ||
      schedule.repeatInterval !== NotificationRepeatInterval.DAY ||
      schedule.repeatInterval !== NotificationRepeatInterval.WEEK
    ) {
      throw new Error("'schedule.repeatInterval' expected a valid NotificationRepeatInterval.");
    }

    out.repeatInterval = schedule.repeatInterval;
  }

  return out;
}
