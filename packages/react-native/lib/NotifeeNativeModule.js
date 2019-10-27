/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { getCoreModule, getNativeModule } from './NotifeeNativeModuleRegistry';
import NotifeeJSEventEmitter from './NotifeeJSEventEmitter';

let notifeeConfigJson = null;

export default class NotifeeNativeModule {
  constructor(config) {
    this._nativeModule = null;
    this._config = Object.assign({}, config);
  }

  get notifeeConfigJson() {
    if (notifeeConfigJson) {
      return notifeeConfigJson;
    }
    notifeeConfigJson = JSON.parse(getCoreModule().NOTIFEE_RAW_JSON);
    return notifeeConfigJson;
  }

  get emitter() {
    return NotifeeJSEventEmitter;
  }

  get native() {
    if (this._nativeModule) {
      return this._nativeModule;
    }
    this._nativeModule = getNativeModule(this);
    return this._nativeModule;
  }
}

// Instance of checks don't work once compiled
NotifeeNativeModule.__extended__ = {};
