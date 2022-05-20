import { EventEmitter, NativeEventEmitter, NativeModulesStatic } from 'react-native';
import { AuthorizationStatus, Notification } from './types/Notification';
import { Trigger, TriggerType } from './types/Trigger';

export interface NativeModuleConfig {
  version: string;
  nativeModuleName: string;
  nativeEvents: string[];
}

export default class NotifeeNativeModule {
  private readonly _moduleConfig: NativeModuleConfig;

  private sw: ServiceWorkerRegistration | undefined;

  public constructor(config: NativeModuleConfig) {
    this._moduleConfig = Object.assign({}, config);

    if (NotifeeNativeModule.hasServiceWorkerSupport) {
      navigator.serviceWorker
        .register('notifee-sw.js')
        .then(serviceWorkerRegistration => {
          this.sw = serviceWorkerRegistration;
          console.log('Service worker "notifee-sw.js" is registered.');
        })
        .catch(e => {
          console.error(e);
          console.log(
            'The service worker could not be registered. Using the browser Notification.\n' +
              'Browser Notification does not include all features, to unlock Notifee full power add `notifee-sw.js` service worker.\n' +
              'Learn how to add "notifee-sw.js": https://notifee.com',
          );
        });
    } else {
      console.log('Your browser does not support service workers. Using the browser Notification.');
    }
  }

  public get emitter(): EventEmitter {
    return new NativeEventEmitter();
  }

  private displayedNotifications: Array<{
    notification: Notification;
    nativeNotification?: globalThis.Notification;
  }> = [];

  private pendingNotifications: Array<{
    notification: Notification;
    trigger: Trigger;
    timeout?: NodeJS.Timeout;
  }> = [];

  public get native(): NativeModulesStatic {
    const sw = this.sw;
    const hasNotificationSupport = NotifeeNativeModule.hasNotificationSupport;
    const formatNotificationBody = NotifeeNativeModule.formatNotificationBody;

    const requestPermission = (): Promise<AuthorizationStatus> => {
      if (!hasNotificationSupport) return Promise.resolve(AuthorizationStatus.NOT_DETERMINED);

      return window.Notification.requestPermission().then(permission => {
        switch (permission) {
          case 'default':
            return AuthorizationStatus.NOT_DETERMINED;
          case 'denied':
            return AuthorizationStatus.DENIED;
          case 'granted':
            return AuthorizationStatus.AUTHORIZED;
        }
      });
    };

    const getNotificationSettings = (): AuthorizationStatus => {
      if (!hasNotificationSupport) return AuthorizationStatus.NOT_DETERMINED;

      switch (window.Notification.permission) {
        case 'default':
          return AuthorizationStatus.NOT_DETERMINED;
        case 'denied':
          return AuthorizationStatus.DENIED;
        case 'granted':
          return AuthorizationStatus.AUTHORIZED;
      }
    };

    const displayNotification = (notification: Notification): Promise<void> => {
      if (sw) {
        return sw.showNotification(notification.title ?? '', {
          ...notification.web,
          body: formatNotificationBody(notification.subtitle, notification.body),
          data: notification.data,
        });
      } else if (hasNotificationSupport) {
        const nativeNotification = new window.Notification(notification.title ?? '', {
          ...notification.web,
          body: formatNotificationBody(notification.subtitle, notification.body),
          data: notification.data,
        });
        this.displayedNotifications.push({ notification, nativeNotification });
      }
      return Promise.resolve();
    };

    const cancelDisplayedNotification = (notificationId: string): Promise<void> => {
      if (sw) {
      } else if (hasNotificationSupport) {
        const notification = this.displayedNotifications.find(
          $ => $.notification.id === notificationId,
        );
        notification?.nativeNotification?.close();
      }
      return Promise.resolve();
    };

    const cancelDisplayedNotificationsWithIds = (notificationIds: string[]): Promise<void> => {
      this.displayedNotifications
        .filter($ => $.notification.id && notificationIds.includes($.notification.id))
        .forEach($ => cancelDisplayedNotification($.notification.id!));

      return Promise.resolve();
    };

    const cancelDisplayedNotifications = (): Promise<void> => {
      this.displayedNotifications.forEach($ => {
        cancelTriggerNotification($.notification.id!);
      });
      return Promise.resolve();
    };

    const createTriggerNotification = (
      notification: Notification,
      trigger: Trigger,
    ): Promise<void> => {
      if (trigger.type === TriggerType.TIMESTAMP) {
        const timeout = setTimeout(() => {
          this.pendingNotifications = this.pendingNotifications.filter(
            $ => $.notification !== notification,
          );
          return displayNotification(notification);
        }, trigger.timestamp - Date.now());

        this.pendingNotifications.push({ notification, trigger, timeout });
      }
      return Promise.resolve();
    };

    const cancelTriggerNotification = (notificationId: string): Promise<void> => {
      if (sw) {
      } else if (hasNotificationSupport) {
        const notification = this.pendingNotifications.find(
          $ => $.notification.id === notificationId,
        );
        if (notification?.timeout !== undefined) {
          clearTimeout(notification.timeout);
          this.pendingNotifications = this.pendingNotifications.filter(
            $ => $.notification.id !== notificationId,
          );
        }
      }
      return Promise.resolve();
    };

    const cancelTriggerNotificationsWithIds = (notificationIds: string[]): Promise<void> => {
      this.pendingNotifications
        .filter($ => $.notification.id && notificationIds.includes($.notification.id))
        .forEach($ => cancelTriggerNotification($.notification.id!));
      return Promise.resolve();
    };

    const cancelTriggerNotifications = (): Promise<void> => {
      this.pendingNotifications.forEach($ => {
        cancelTriggerNotification($.notification.id!);
      });

      return Promise.resolve();
    };

    const cancelNotification = (notificationId: string): Promise<void> => {
      const pending = this.pendingNotifications.find($ => $.notification.id === notificationId);
      if (pending) {
        return cancelTriggerNotification(notificationId);
      }

      const displayed = this.displayedNotifications.find($ => $.notification.id === notificationId);
      if (displayed) {
        return cancelDisplayedNotification(notificationId);
      }

      return Promise.resolve();
    };

    const cancelAllNotificationsWithIds = (notificationIds: string[]): Promise<void> => {
      cancelDisplayedNotificationsWithIds(notificationIds);
      cancelTriggerNotificationsWithIds(notificationIds);
      return Promise.resolve();
    };

    const cancelAllNotifications = (): Promise<void> => {
      cancelDisplayedNotifications();
      cancelTriggerNotifications();
      return Promise.resolve();
    };

    return {
      requestPermission,
      getNotificationSettings,
      displayNotification,
      cancelDisplayedNotification,
      cancelDisplayedNotificationsWithIds,
      cancelDisplayedNotifications,
      createTriggerNotification,
      cancelTriggerNotification,
      cancelTriggerNotificationsWithIds,
      cancelTriggerNotifications,
      cancelNotification,
      cancelAllNotificationsWithIds,
      cancelAllNotifications,
    };
  }

  private static get hasServiceWorkerSupport() {
    return false;
    return 'serviceWorker' in navigator;
  }

  private static get hasNotificationSupport() {
    return 'Notification' in window;
  }

  private static formatNotificationBody(subtitle?: string, body?: string) {
    let txt = '';
    if (subtitle) txt += subtitle;
    if (subtitle && body) txt += '\n';
    if (body) txt += body;
    return txt;
  }
}
