import {
  TriggerType,
  TimestampTrigger,
  IntervalTrigger,
  TimeUnit,
} from '@notifee/react-native';

type TriggersItems = {
  timestamp: () => TimestampTrigger;
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
  interval: () => ({
    timeUnit: TimeUnit.SECONDS,
    type: TriggerType.INTERVAL,
    interval: interval,
  }),
};
