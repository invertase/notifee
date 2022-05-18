import { EventEmitter, NativeEventEmitter, NativeModulesStatic } from 'react-native';
import { AuthorizationStatus, Notification } from './types/Notification';

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

  public get native(): NativeModulesStatic {
    const sw = this.sw;
    const hasNotificationSupport = NotifeeNativeModule.hasNotificationSupport;
    const formatNotificationBody = NotifeeNativeModule.formatNotificationBody;

    return {
      requestPermission: (): Promise<AuthorizationStatus> => {
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
      },
      displayNotification: (notification: Notification): Promise<void> => {
        if (sw) {
          return sw.showNotification(notification.title ?? '', {
            body: formatNotificationBody(notification.subtitle, notification.body),
            data: notification.data,
          });
        } else if (hasNotificationSupport) {
          new Notification(notification.title ?? '', {
            body: formatNotificationBody(notification.subtitle, notification.body),
            data: notification.data,
          });
        }
        return Promise.resolve();
      },
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
