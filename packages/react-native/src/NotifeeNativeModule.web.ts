import { EventEmitter, NativeEventEmitter, NativeModulesStatic } from 'react-native';

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
      navigator.serviceWorker.register('notifee-sw.js').then(serviceWorkerRegistration => {
        this.sw = serviceWorkerRegistration;
        console.log('Service worker "notifee-sw.js" is registered.')
      }).catch((e) => {
        console.error(e)
        console.log('The service worker could not be registered. Using the browser Notification.\n' +
          'Browser Notification does not include all features, to unlock Notifee full power add `notifee-sw.js` service worker.\n' +
          'Learn how to add "notifee-sw.js": https://notifee.com');
      })
    } else {
      console.log('Your browser does not support service workers. Using the browser Notification.')
    }
  }

  public get emitter(): EventEmitter {
    return new NativeEventEmitter();
  }

  public get native(): NativeModulesStatic {
    const hasNotificationSupport = NotifeeNativeModule.hasNotificationSupport

    return {};
  }

  private static get hasServiceWorkerSupport() {
    return 'serviceWorker' in navigator
  }

  private static get hasNotificationSupport() {
    return 'Notification' in window
  }
}
