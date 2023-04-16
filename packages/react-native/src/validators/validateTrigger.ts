/*
 * Copyright (c) 2016-present Invertase Limited
 */

import {
  objectHasProperty,
  isNumber,
  isObject,
  isValidEnum,
  isUndefined,
  isBoolean,
} from '../utils';
import {
  Trigger,
  TimeUnit,
  RepeatFrequency,
  TimestampTrigger,
  IntervalTrigger,
  TriggerType,
  TimestampTriggerAlarmManager,
  AlarmType,
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

  const out: TimestampTrigger = {
    type: trigger.type,
    timestamp: trigger.timestamp,
    repeatFrequency: -1,
  };

  if (objectHasProperty(trigger, 'repeatFrequency') && !isUndefined(trigger.repeatFrequency)) {
    if (!isValidEnum(trigger.repeatFrequency, RepeatFrequency)) {
      throw new Error("'trigger.repeatFrequency' expected a RepeatFrequency value.");
    }

    out.repeatFrequency = trigger.repeatFrequency;
  }

  if (objectHasProperty(trigger, 'alarmManager') && !isUndefined(trigger.alarmManager)) {
    if (isBoolean(trigger.alarmManager)) {
      if (trigger.alarmManager) {
        out.alarmManager = validateTimestampAlarmManager();
      }
    } else {
      try {
        out.alarmManager = validateTimestampAlarmManager(trigger.alarmManager);
      } catch (e: any) {
        throw new Error(`'trigger.alarmManager' ${e.message}.`);
      }
    }
  }

  return out;
}

function validateTimestampAlarmManager(
  alarmManager?: TimestampTriggerAlarmManager,
): TimestampTriggerAlarmManager {
  const out: TimestampTriggerAlarmManager = {
    type: AlarmType.SET_EXACT,
  };
  if (!alarmManager) {
    return out;
  }
  if (isBoolean(alarmManager.allowWhileIdle) && alarmManager.allowWhileIdle) {
    out.type = AlarmType.SET_EXACT_AND_ALLOW_WHILE_IDLE;
  }

  if (objectHasProperty(alarmManager, 'type') && !isUndefined(alarmManager.type)) {
    if (!isValidEnum(alarmManager.type, AlarmType)) {
      throw new Error("'alarmManager.type' expected a AlarmType value.");
    }
    out.type = alarmManager.type;
  }

  return out;
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

  if (objectHasProperty(trigger, 'timeUnit') && !isUndefined(trigger.timeUnit)) {
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
