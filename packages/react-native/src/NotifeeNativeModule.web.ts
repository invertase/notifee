import { EventEmitter, NativeEventEmitter, NativeModulesStatic } from 'react-native';

export interface NativeModuleConfig {
  version: string;
  nativeModuleName: string;
  nativeEvents: string[];
}

export default class NotifeeNativeModule {
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore unused value
  private readonly _moduleConfig: NativeModuleConfig;

  public constructor(config: NativeModuleConfig) {
    this._moduleConfig = Object.assign({}, config);
  }

  public get emitter(): EventEmitter {
    return new NativeEventEmitter();
  }

  public get native(): NativeModulesStatic {
    return {};
  }
}
