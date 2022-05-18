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

    if ('serviceWorker' in navigator) {
      navigator.serviceWorker.register('notifee-sw.js').then(serviceWorkerRegistration => {
        this.sw = serviceWorkerRegistration;
        console.log('serviceworker registered')
      }).catch(() => {
        console.log('couldn\'t register serviceworker using browser notifications')
      })
    } else {
      console.log('your browser doesnt support service workers browser notifications')
    }
  }

  public get emitter(): EventEmitter {
    return new NativeEventEmitter();
  }

  public get native(): NativeModulesStatic {
    return {};
  }
}
