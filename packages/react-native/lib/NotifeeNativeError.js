/*
 * Copyright (c) 2016-present Invertase Limited
 */

export default class NotifeeNativeError extends Error {
  static fromEvent(errorEvent, stack) {
    return new NotifeeNativeError({ userInfo: errorEvent }, stack || new Error().stack);
  }

  constructor(nativeError, jsStack) {
    super();
    const { userInfo } = nativeError;

    Object.defineProperty(this, 'code', {
      enumerable: false,
      value: `${userInfo.code || 'unknown'}`,
    });

    Object.defineProperty(this, 'message', {
      enumerable: false,
      value: `[${this.code}] ${userInfo.message || nativeError.message}`,
    });

    Object.defineProperty(this, 'jsStack', {
      enumerable: false,
      value: jsStack,
    });

    Object.defineProperty(this, 'userInfo', {
      enumerable: false,
      value: userInfo,
    });

    Object.defineProperty(this, 'nativeErrorCode', {
      enumerable: false,
      value: userInfo.nativeErrorCode || null,
    });

    Object.defineProperty(this, 'nativeErrorMessage', {
      enumerable: false,
      value: userInfo.nativeErrorMessage || null,
    });

    this.stack = this.getStackWithMessage(`NativeError: ${this.message}`);
  }

  /**
   * Build a stack trace that includes JS stack prior to calling the native method.
   *
   * @returns {string}
   */
  getStackWithMessage(message) {
    return [message, ...this.jsStack.split('\n').slice(2, 13)].join('\n');
  }
}
