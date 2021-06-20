import {
  TriggerType,
  TimestampTrigger,
  IntervalTrigger,
  TimeUnit,
  RepeatFrequency,
} from '@notifee/react-native';

type TriggersItems = {
  timestamp: () => TimestampTrigger;
  timestampWithAlarmManager: () => TimestampTrigger;
  timestampWithAlarmManagerRepeating: () => TimestampTrigger;
  interval: () => IntervalTrigger;
};

/* Timestamp Date */
const getTimestamp = () => {
  const timestampDate = new Date(Date.now());
  timestampDate.setSeconds(timestampDate.getSeconds() + 5);
  return timestampDate.getTime();
};

/* Interval */
const interval = 60;

export const triggers: TriggersItems = {
  timestamp: () => ({
    timestamp: getTimestamp(),
    type: TriggerType.TIMESTAMP,
  }),
  timestampWithAlarmManager: () => ({
    timestamp: getTimestamp(),
    type: TriggerType.TIMESTAMP,
    alarmManager: {
      allowWhileIdle: true,
    },
  }),
  timestampWithAlarmManagerRepeating: () => ({
    timestamp: getTimestamp(),
    type: TriggerType.TIMESTAMP,
    repeatFrequency: RepeatFrequency.HOURLY,
    alarmManager: {
      allowWhileIdle: true,
    },
  }),
  interval: () => ({
    timeUnit: TimeUnit.SECONDS,
    type: TriggerType.INTERVAL,
    interval: interval,
  }),
};
