/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { Platform } from 'react-native';

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

export const hasNotificationSupport = (): boolean => {
  if (!('Notification' in window) || !Notification.requestPermission) {
    return false;
  }

  // don't test for `new Notification` if permission has already been granted
  // otherwise this sends a real notification on supported browsers
  if (Notification.permission !== 'granted') {
    try {
      new Notification('');
    } catch (e: any) {
      if (e.name === 'TypeError') {
        return false;
      }
    }
  }

  return true;
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
