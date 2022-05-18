export interface NotificationWeb {
  actions?: NotificationAction[];

  badge?: string;

  dir?: NotificationDirection;

  icon?: string;

  image?: string;

  lang?: string;

  renotify?: boolean;

  requireInteraction?: boolean;

  silent?: boolean;

  tag?: string;

  timestamp?: EpochTimeStamp;

  vibrate?: VibratePattern;
}
