/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { objectHasProperty, isNumber, isObject, isValidEnum } from '../utils';
import { Trigger, TimeUnit, TimeTrigger, TriggerType } from '../types/Trigger';

const MINIMUM_INTERVAL = 15;

function isMinimumInterval(interval: number, timeUnit: any): boolean {
  switch (timeUnit) {
    case TimeUnit.SECONDS:
      return interval / 60 >= MINIMUM_INTERVAL;
    case TimeUnit.MINUTES:
      return interval >= MINIMUM_INTERVAL;
    case TimeUnit.HOURS:
      return interval >= 1;
    case TimeUnit.DAYS:
      return interval >= 1;
  }
  return true;
}

export default function validateTrigger(trigger: Trigger): Trigger {
  if (!isObject(trigger)) {
    throw new Error("'trigger' expected an object value.");
  }

  switch (trigger.type) {
    case TriggerType.TIME:
      return validateTimeTrigger(trigger);
    default:
      throw new Error('Unknown trigger type');
  }
}

function validateTimeTrigger(trigger: TimeTrigger): TimeTrigger {
  if (!isNumber(trigger.timestamp)) {
    throw new Error("'trigger.timestamp' expected a number value.");
  }

  const now = Date.now();

  if (trigger.timestamp <= now) {
    throw new Error("'trigger.timestamp' date must be in the future.");
  }

  const out: TimeTrigger = {
    type: trigger.type,
    timestamp: trigger.timestamp,
    repeatInterval: -1,
    repeatIntervalTimeUnit: TimeUnit.MINUTES,
  };

  if (objectHasProperty(trigger, 'repeatInterval')) {
    if (!isNumber(trigger.repeatInterval)) {
      throw new Error("'trigger.repeatInterval' must be a number value.");
    }

    if (!Number.isInteger(trigger.repeatInterval)) {
      throw new Error("'trigger.repeatInterval' must be a integer value.");
    }

    if (objectHasProperty(trigger, 'repeatIntervalTimeUnit')) {
      if (!isValidEnum(trigger.repeatIntervalTimeUnit, TimeUnit)) {
        throw new Error("'trigger.repeatIntervalTimeUnit' expected a TimeUnit value.");
      }
      out.repeatIntervalTimeUnit = trigger.repeatIntervalTimeUnit;
    }

    if (!isMinimumInterval(trigger.repeatInterval, out.repeatIntervalTimeUnit)) {
      throw new Error("'trigger.repeatInterval' expected to be at least 15 minutes.");
    }

    out.repeatInterval = trigger.repeatInterval;
  }
  return out;
}
