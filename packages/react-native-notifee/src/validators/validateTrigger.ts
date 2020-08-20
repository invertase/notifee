/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { objectHasProperty, isNumber, isObject, isValidEnum } from '../utils';
import {
  Trigger,
  TimeUnit,
  TimestampTrigger,
  IntervalTrigger,
  TriggerType,
} from '../types/Trigger';

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
    case TriggerType.TIMESTAMP:
      return validateTimestampTrigger(trigger);
    case TriggerType.INTERVAL:
      return validateIntervalTrigger(trigger);
    default:
      throw new Error('Unknown trigger type');
  }
}

function validateTimestampTrigger(trigger: TimestampTrigger): TimestampTrigger {
  if (!isNumber(trigger.timestamp)) {
    throw new Error("'trigger.timestamp' expected a number value.");
  }

  const now = Date.now();
  if (trigger.timestamp <= now) {
    throw new Error("'trigger.timestamp' date must be in the future.");
  }

  return {
    type: trigger.type,
    timestamp: trigger.timestamp,
  };
}

function validateIntervalTrigger(trigger: IntervalTrigger): IntervalTrigger {
  if (!isNumber(trigger.interval)) {
    throw new Error("'trigger.interval' expected a number value.");
  }

  const out: IntervalTrigger = {
    type: trigger.type,
    interval: trigger.interval,
    timeUnit: TimeUnit.SECONDS,
  };

  if (objectHasProperty(trigger, 'timeUnit')) {
    if (!isValidEnum(trigger.timeUnit, TimeUnit)) {
      throw new Error("'trigger.timeUnit' expected a TimeUnit value.");
    }
    out.timeUnit = trigger.timeUnit;
  }

  if (!isMinimumInterval(trigger.interval, out.timeUnit)) {
    throw new Error("'trigger.interval' expected to be at least 15 minutes.");
  }

  return out;
}
