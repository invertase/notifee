/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Platform } from 'react-native';
import { AuthorizationStatus, NotificationSettings } from "../types/Notification";
import { AndroidNotificationSetting } from "../types/NotificationAndroid";

export * from './id';
export * from './validate';

/* eslint-disable-next-line @typescript-eslint/ban-types */
export function isError(value: object): boolean {
  if (Object.prototype.toString.call(value) === '[object Error]') {
    return true;
  }

  return value instanceof Error;
}

export function objectHasProperty<T>(
  target: T,
  property: string | number | symbol,
): property is keyof T {
  return Object.hasOwnProperty.call(target, property);
}

export const isIOS = Platform.OS === 'ios';

export const isAndroid = Platform.OS === 'android';

export const isWeb = Platform.OS === 'web';

export const defaultNotificationSettings: NotificationSettings = {
  authorizationStatus: AuthorizationStatus.NOT_DETERMINED,
  android: {
    alarm: AndroidNotificationSetting.ENABLED,
  },
  ios: {
    alert: 1,
    badge: 1,
    criticalAlert: 1,
    showPreviews: 1,
    sound: 1,
    carPlay: 1,
    lockScreen: 1,
    announcement: 1,
    notificationCenter: 1,
    inAppNotificationSettings: 1,
    authorizationStatus: AuthorizationStatus.NOT_DETERMINED,
  },
  web: {}
}

export function noop(): void {
  // noop-🐈
}
export const kReactNativeNotifeeForegroundServiceHeadlessTask =
  'app.notifee.foreground-service-headless-task';

export const kReactNativeNotifeeNotificationEvent = 'app.notifee.notification-event';

export const kReactNativeNotifeeNotificationBackgroundEvent =
  'app.notifee.notification-event-background';

export enum NotificationType {
  ALL = 0,
  DISPLAYED = 1,
  TRIGGER = 2,
}
