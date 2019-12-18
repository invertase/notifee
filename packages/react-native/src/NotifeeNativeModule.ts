/*
 * Copyright (c) 2016-present Invertase Limited
 */

import NotifeeJSEventEmitter from './NotifeeJSEventEmitter';
import {
  EventEmitter,
  EventSubscriptionVendor,
  NativeEventEmitter,
  NativeModules,
  NativeModulesStatic,
} from 'react-native';
import { NativeModuleConfig } from './types';
import { NotifeeJsonConfig } from '../types/Library';

export default class NotifeeNativeModule {
  private readonly _moduleConfig: NativeModuleConfig;
  private _nativeModule: NativeModulesStatic | null;
  private _nativeEmitter: NativeEventEmitter;
  private _notifeeConfig: NotifeeJsonConfig | null;

  public constructor(config: NativeModuleConfig) {
    this._nativeModule = null;
    this._notifeeConfig = null;
    this._moduleConfig = Object.assign({}, config);
    this._nativeEmitter = new NativeEventEmitter(this.native as EventSubscriptionVendor);
    for (let i = 0; i < config.nativeEvents.length; i++) {
      const eventName = config.nativeEvents[i];
      this._nativeEmitter.addListener(eventName, (payload: any) => {
        console.log('!!EVENT!! ' + eventName);
        this.emitter.emit(eventName, payload);
      });
    }
  }

  public get config(): NotifeeJsonConfig {
    if (this._notifeeConfig) {
      return this._notifeeConfig;
    }

    this._notifeeConfig = JSON.parse(this.native.NOTIFEE_RAW_JSON);

    return this._notifeeConfig as NotifeeJsonConfig;
  }

  public get emitter(): EventEmitter {
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
