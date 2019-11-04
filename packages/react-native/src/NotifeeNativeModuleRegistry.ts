/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { EventSubscription, NativeModules, NativeModulesStatic, Platform } from 'react-native';

import { CORE_NATIVE_MODULE_NAME } from './NotifeeConstants';
import NotifeeNativeError from './NotifeeNativeError';
import NotifeeNativeEventEmitter from './NotifeeNativeEventEmitter';
import SharedEventEmitter from './NotifeeJSEventEmitter';
import { NativeModuleConfig } from './types';

const NATIVE_MODULE_REGISTRY: {
  [key: string]: NativeModulesStatic;
} = {};
const NATIVE_MODULE_EVENT_SUBSCRIPTIONS: {
  [key: string]: EventSubscription;
} = {};

/**
 * Wraps a native module method to provide custom Error classes.
 * @param method
 * @returns {Function}
 */
function nativeModuleMethodWrapped(method: Function) {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  return (...args: any[]): Promise<any> | any => {
    const possiblePromise = method(...args);

    if (possiblePromise && possiblePromise.then) {
      const jsStack = new Error().stack;
      // TODO nativeError type
      return possiblePromise.catch((nativeError: any) =>
        Promise.reject(new NotifeeNativeError(nativeError, jsStack)),
      );
    }

    return possiblePromise;
  };
}

/**
 * Wraps all native method calls
 *
 * @param NativeModule
 */
function nativeModuleWrapped(NativeModule: NativeModulesStatic): NativeModulesStatic {
  const native = {} as NativeModulesStatic;

  if (!NativeModule) {
    return NativeModule;
  }

  const properties = Object.keys(NativeModule);

  for (let i = 0, len = properties.length; i < len; i++) {
    const property = properties[i];
    if (typeof NativeModule[property] === 'function') {
      native[property] = nativeModuleMethodWrapped(NativeModule[property]);
    } else {
      native[property] = NativeModule[property];
    }
  }

  return native;
}

/**
 * Subscribe to a native event for js side distribution
 * @param eventName
 * @private
 */
function subscribeToNativeModuleEvent(eventName: string): void {
  if (!NATIVE_MODULE_EVENT_SUBSCRIPTIONS[eventName]) {
    NATIVE_MODULE_EVENT_SUBSCRIPTIONS[eventName] = NotifeeNativeEventEmitter.addListener(
      eventName,
      (event: object) => {
        SharedEventEmitter.emit(eventName, event);
      },
    );
  }
}

/**
 * Help text for missing native module.
 *
 * @returns {string}
 */
function getMissingModuleHelpText(nativeModuleName: string): string {
  if (Platform.OS === 'ios') {
    return (
      `You attempted to use Notifee but its not installed natively on your iOS project. (${nativeModuleName})` +
      '\r\n\r\nEnsure you have auto-linking enabled in your Podfile, re-installed your Pods & rebuilt your app.' +
      '\r\n\r\nSee http://notifee.app/docs/installation for full setup instructions.'
    );
  }

  return (
    `You attempted to use Notifee but its not installed natively on your Android project. (${nativeModuleName})` +
    '\r\n\r\nEnsure you have auto-linking enabled in your gradle files and have rebuilt your app.' +
    '\r\n\r\nSee http://notifee.app/docs/installation for full setup instructions.'
  );
}

/**
 * Initialises and wraps all the native module methods.
 *
 * @returns {*}
 * @param config
 */
function initialiseNativeModule(config: NativeModuleConfig): NativeModulesStatic {
  const nativeModuleCopy = {} as NativeModulesStatic;
  const { nativeEvents, nativeModuleName } = config;
  const nativeModule = NativeModules[nativeModuleName];

  if (!nativeModule) {
    throw new Error(getMissingModuleHelpText(CORE_NATIVE_MODULE_NAME));
  }

  Object.assign(nativeModuleCopy, nativeModuleWrapped(nativeModule));

  for (let i = 0, len = nativeEvents.length; i < len; i++) {
    subscribeToNativeModuleEvent(nativeEvents[i]);
  }

  Object.freeze(nativeModuleCopy);
  NATIVE_MODULE_REGISTRY[nativeModuleName] = nativeModuleCopy;
  return NATIVE_MODULE_REGISTRY[nativeModuleName];
}

/**
 * Gets a wrapped native module instance.
 * Will attempt to create a new instance if non previously created.
 *
 * @returns {*}
 * @param moduleConfig
 */
export function getNativeModule(moduleConfig: NativeModuleConfig): NativeModulesStatic {
  const { nativeModuleName } = moduleConfig;

  if (NATIVE_MODULE_REGISTRY[nativeModuleName]) {
    return NATIVE_MODULE_REGISTRY[nativeModuleName];
  }

  return initialiseNativeModule(moduleConfig);
}

/**
 * Custom wrapped core native module module.
 */
export function getCoreModule(): NativeModulesStatic {
  if (NATIVE_MODULE_REGISTRY[CORE_NATIVE_MODULE_NAME]) {
    return NATIVE_MODULE_REGISTRY[CORE_NATIVE_MODULE_NAME];
  }

  const nativeModule = NativeModules[CORE_NATIVE_MODULE_NAME];
  if (!nativeModule) {
    throw new Error(getMissingModuleHelpText(CORE_NATIVE_MODULE_NAME));
  }

  NATIVE_MODULE_REGISTRY[CORE_NATIVE_MODULE_NAME] = nativeModuleWrapped(nativeModule);
  return NATIVE_MODULE_REGISTRY[CORE_NATIVE_MODULE_NAME];
}
