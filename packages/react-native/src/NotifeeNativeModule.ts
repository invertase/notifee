/*
 * Copyright (c) 2016-present Invertase Limited
 */

import NotifeeJSEventEmitter from './NotifeeJSEventEmitter';
import { EventEmitter, NativeModules, NativeModulesStatic } from 'react-native';
import { NativeModuleConfig } from './types';
import { NotifeeJsonConfig } from '../types/Library';

export default class NotifeeNativeModule {
  private readonly _moduleConfig: NativeModuleConfig;
  private _nativeModule: NativeModulesStatic | null;
  private _notifeeConfig: NotifeeJsonConfig | null;

  public constructor(config: NativeModuleConfig) {
    this._nativeModule = null;
    this._notifeeConfig = null;
    this._moduleConfig = Object.assign({}, config);
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
