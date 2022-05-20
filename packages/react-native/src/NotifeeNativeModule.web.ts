import { EventEmitter, NativeEventEmitter, NativeModulesStatic } from 'react-native';
import {
  AuthorizationStatus,
  DisplayedNotification,
  Notification,
  TriggerNotification,
} from './types/Notification';
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
    notification: DisplayedNotification;
    nativeNotification: globalThis.Notification;
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

    const displayNotification = async (notification: Notification) => {
      const displayedNotification: DisplayedNotification = {
        id: notification.id,
        date: new Date().toISOString(),
        notification,
      };

      if (sw) {
        await sw.showNotification(notification.title ?? '', {
          ...notification.web,
          body: formatNotificationBody(notification.subtitle, notification.body),
          data: { ...notification.data, displayedNotification },
        });
      } else if (hasNotificationSupport) {
        const nativeNotification = new window.Notification(notification.title ?? '', {
          ...notification.web,
          body: formatNotificationBody(notification.subtitle, notification.body),
          data: notification.data,
        });
        this.displayedNotifications.push({
          notification: displayedNotification,
          nativeNotification,
        });
      }
    };

    const getDisplayedNotifications = async () => {
      if (sw) {
        return new Promise(resolve => {
          const listener = (evt: MessageEvent) => {
            resolve(JSON.parse(evt.data.notifications));
            console.log(JSON.parse(evt.data.notifications));
            navigator.serviceWorker.removeEventListener('message', listener);
          };
          navigator.serviceWorker.addEventListener('message', listener);
          navigator.serviceWorker.ready.then(reg => {
            reg.active?.postMessage({ notifee: 'get_displayed_notifications' });
          });
        });
      } else if (hasNotificationSupport) {
        return this.displayedNotifications.map(
          displayedNotification => displayedNotification.notification,
        );
      }
    };

    const cancelDisplayedNotification = async (notificationId: string) => {
      if (sw) {
        const reg = await navigator.serviceWorker.ready;
        reg.active?.postMessage({ notifee: 'cancel_displayed_notification', notificationId });
      } else if (hasNotificationSupport) {
        const notification = this.displayedNotifications.find(
          displayedNotification => displayedNotification.notification.id === notificationId,
        );
        notification?.nativeNotification?.close();
        this.displayedNotifications = this.displayedNotifications.filter(
          displayedNotification => displayedNotification.notification.id !== notificationId,
        );
      }
    };

    const cancelDisplayedNotificationsWithIds = async (notificationIds: string[]) => {
      if (sw) {
        const reg = await navigator.serviceWorker.ready;
        reg.active?.postMessage({ notifee: 'cancel_notifications_with_ids', notificationIds });
      } else if (hasNotificationSupport) {
        const promises = this.displayedNotifications
          .filter(displayedNotification =>
            notificationIds.includes(displayedNotification.notification.id!),
          )
          .map(displayedNotification =>
            cancelDisplayedNotification(displayedNotification.notification.id!),
          );
        await Promise.all(promises);
      }
    };

    const cancelDisplayedNotifications = async () => {
      if (sw) {
        const reg = await navigator.serviceWorker.ready;
        reg.active?.postMessage({ notifee: 'cancel_displayed_notifications' });
      } else if (hasNotificationSupport) {
        const promises = this.displayedNotifications.map(displayedNotification =>
          cancelDisplayedNotification(displayedNotification.notification.id!),
        );
        await Promise.all(promises);
      }
    };

    const createTriggerNotification = async (notification: Notification, trigger: Trigger) => {
      if (trigger.type === TriggerType.TIMESTAMP) {
        const timeout = setTimeout(() => {
          this.pendingNotifications = this.pendingNotifications.filter(
            pendingNotification => pendingNotification.notification !== notification,
          );
          displayNotification(notification);
        }, trigger.timestamp - Date.now());

        this.pendingNotifications.push({ notification, trigger, timeout });
      }
    };

    const getTriggerNotifications = () => {
      return Promise.resolve(
        this.pendingNotifications.map(
          (pendingNotification): TriggerNotification => ({
            trigger: pendingNotification.trigger,
            notification: pendingNotification.notification,
          }),
        ),
      );
    };

    const getTriggerNotificationIds = () => {
      return Promise.resolve(
        this.pendingNotifications.map(pendingNotification => pendingNotification.notification.id!),
      );
    };

    const cancelTriggerNotification = (notificationId: string) => {
      const notification = this.pendingNotifications.find(
        pendingNotification => pendingNotification.notification.id === notificationId,
      );

      if (notification?.timeout !== undefined) clearTimeout(notification.timeout);

      this.pendingNotifications = this.pendingNotifications.filter(
        pendingNotification => pendingNotification !== notification,
      );
      return Promise.resolve();
    };

    const cancelTriggerNotificationsWithIds = async (notificationIds: string[]) => {
      const promises = this.pendingNotifications
        .filter(pendingNotification =>
          notificationIds.includes(pendingNotification.notification.id!),
        )
        .map(pendingNotification =>
          cancelTriggerNotification(pendingNotification.notification.id!),
        );

      await Promise.all(promises);
    };

    const cancelTriggerNotifications = async () => {
      const promises = this.pendingNotifications.map(pendingNotification =>
        cancelTriggerNotification(pendingNotification.notification.id!),
      );
      await Promise.all(promises);
    };

    const cancelNotification = async (notificationId: string) => {
      await Promise.all([
        cancelDisplayedNotification(notificationId),
        cancelTriggerNotification(notificationId),
      ]);
    };

    const cancelAllNotificationsWithIds = async (notificationIds: string[]) => {
      await Promise.all([
        cancelDisplayedNotificationsWithIds(notificationIds),
        cancelTriggerNotificationsWithIds(notificationIds),
      ]);
    };

    const cancelAllNotifications = async () => {
      await Promise.all([cancelDisplayedNotifications(), cancelTriggerNotifications()]);
    };

    return {
      requestPermission,
      getNotificationSettings,
      displayNotification,
      getDisplayedNotifications,
      cancelDisplayedNotification,
      cancelDisplayedNotificationsWithIds,
      cancelDisplayedNotifications,
      createTriggerNotification,
      getTriggerNotifications,
      getTriggerNotificationIds,
      cancelTriggerNotification,
      cancelTriggerNotificationsWithIds,
      cancelTriggerNotifications,
      cancelNotification,
      cancelAllNotificationsWithIds,
      cancelAllNotifications,
    };
  }

  private static get hasServiceWorkerSupport() {
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
