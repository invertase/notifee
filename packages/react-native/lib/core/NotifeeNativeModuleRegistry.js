/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NativeModules, Platform } from 'react-native';

import { CORE_NATIVE_MODULE_NAME } from './NotifeeConstants';
import NotifeeNativeError from './NotifeeNativeError';
import NotifeeNativeEventEmitter from './NotifeeNativeEventEmitter';
import SharedEventEmitter from './SharedEventEmitter';

const NATIVE_MODULE_REGISTRY = {};
const NATIVE_MODULE_EVENT_SUBSCRIPTIONS = {};

/**
 * Wraps a native module method to provide custom Error classes.
 * @param method
 * @returns {Function}
 */
function nativeModuleMethodWrapped(method) {
  return (...args) => {
    const possiblePromise = method(...args);

    if (possiblePromise && possiblePromise.then) {
      const jsStack = new Error().stack;
      return possiblePromise.catch(nativeError =>
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
function nativeModuleWrapped(NativeModule) {
  const native = {};

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
 * Initialises and wraps all the native module methods.
 *
 * @param module
 * @returns {*}
 */
function initialiseNativeModule(module) {
  const multiModuleRoot = {};
  const config = module._config;
  const { nativeEvents, nativeModuleName } = config;
  const multiModule = Array.isArray(nativeModuleName);
  const key = multiModule ? nativeModuleName.join(':') : nativeModuleName;
  const nativeModuleNames = multiModule ? nativeModuleName : [nativeModuleName];

  for (let i = 0; i < nativeModuleNames.length; i++) {
    const nativeModule = NativeModules[nativeModuleNames[i]];
    if (!multiModule && !nativeModule) {
      throw new Error(getMissingModuleHelpText(nativeModuleNames[i]));
    }

    if (multiModule) {
      multiModuleRoot[nativeModuleNames[i]] = !!nativeModule;
    }

    Object.assign(multiModuleRoot, nativeModuleWrapped(nativeModule));
  }

  if (nativeEvents && nativeEvents.length) {
    for (let i = 0, len = nativeEvents.length; i < len; i++) {
      subscribeToNativeModuleEvent(nativeEvents[i]);
    }
  }

  Object.freeze(multiModuleRoot);
  NATIVE_MODULE_REGISTRY[key] = multiModuleRoot;
  return NATIVE_MODULE_REGISTRY[key];
}

/**
 * Subscribe to a native event for js side distribution
 * @param eventName
 * @private
 */
function subscribeToNativeModuleEvent(eventName) {
  if (!NATIVE_MODULE_EVENT_SUBSCRIPTIONS[eventName]) {
    NotifeeNativeEventEmitter.addListener(eventName, event => {
      SharedEventEmitter.emit(eventName, event);
    });

    NATIVE_MODULE_EVENT_SUBSCRIPTIONS[eventName] = true;
  }
}

/**
 * Help text for missing native module.
 * @returns {string}
 */
function getMissingModuleHelpText(nativeModule) {
  if (Platform.OS === 'ios') {
    return (
      `You attempted to use Notifee but its not installed natively on your iOS project. (${nativeModule})` +
      '\r\n\r\nEnsure you have auto-linking enabled in your Podfile, re-installed your Pods & rebuilt your app.' +
      '\r\n\r\nSee http://notifee.app/docs/installation for full setup instructions.'
    );
  }

  return (
    `You attempted to use Notifee but its not installed natively on your Android project. (${nativeModule})` +
    '\r\n\r\nEnsure you have auto-linking enabled in your gradle files and have rebuilt your app.' +
    '\r\n\r\nSee http://notifee.app/docs/installation for full setup instructions.'
  );
}

/**
 * Gets a wrapped native module instance.
 * Will attempt to create a new instance if non previously created.
 *
 * @param module
 * @returns {*}
 */
export function getNativeModule(module) {
  const config = module._config;
  const { nativeModuleName } = config;
  const multiModule = Array.isArray(nativeModuleName);
  const key = multiModule ? nativeModuleName.join(':') : nativeModuleName;

  if (NATIVE_MODULE_REGISTRY[key]) {
    return NATIVE_MODULE_REGISTRY[key];
  }

  return initialiseNativeModule(module);
}

/**
 * Custom wrapped core native module module.
 */
export function getCoreModule() {
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
