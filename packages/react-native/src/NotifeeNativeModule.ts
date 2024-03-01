/*
 * Copyright (c) 2016-present Invertase Limited
 */

import NotifeeJSEventEmitter from './NotifeeJSEventEmitter';
import {
  EventSubscription,
  NativeEventEmitter,
  NativeModules,
  NativeModulesStatic,
} from 'react-native';

export interface NativeModuleConfig {
  version: string;
  nativeModuleName: string;
  nativeEvents: string[];
}

export default class NotifeeNativeModule {
  private readonly _moduleConfig: NativeModuleConfig;
  private _nativeModule: NativeModulesStatic | null;
  private _nativeEmitter: NativeEventEmitter;

  public constructor(config: NativeModuleConfig) {
    this._nativeModule = null;
    this._moduleConfig = Object.assign({}, config);
    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore - change here needs resolution https://github.com/DefinitelyTyped/DefinitelyTyped/pull/49560/files
    this._nativeEmitter = new NativeEventEmitter(this.native as EventSubscription['subscriber']);
    for (let i = 0; i < config.nativeEvents.length; i++) {
      const eventName = config.nativeEvents[i];
      this._nativeEmitter.addListener(eventName, (payload: any) => {
        this.emitter.emit(eventName, payload);
      });
    }
  }

  public get emitter() {
    return NotifeeJSEventEmitter;
  }

  public get native(): NativeModulesStatic {
    if (this._nativeModule) {
      return this._nativeModule;
    }

    this._nativeModule = NativeModules[this._moduleConfig.nativeModuleName];
    if (this._nativeModule == null) {
      throw new Error('Notifee native module not found.');
    }

    return this._nativeModule;
  }
}
