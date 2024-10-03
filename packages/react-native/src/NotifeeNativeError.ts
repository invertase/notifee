/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NativeError } from './types/Library';

export default class NotifeeNativeError extends Error implements NativeError {
  public readonly code: string;
  public readonly nativeErrorCode: string;
  public readonly nativeErrorMessage: string;
  private readonly jsStack: string;

  //  TODO native error type
  constructor(nativeError: any, jsStack = '') {
    super();
    const { userInfo } = nativeError;

    this.code = `${userInfo.code || 'unknown'}`;
    Object.defineProperty(this, 'code', {
      enumerable: false,
      value: `${userInfo.code || 'unknown'}`,
    });

    this.jsStack = jsStack;
    Object.defineProperty(this, 'jsStack', {
      enumerable: false,
      value: jsStack,
    });

    Object.defineProperty(this, 'message', {
      enumerable: false,
      value: `[${this.code}] ${userInfo.message || nativeError.message}`,
    });

    Object.defineProperty(this, 'userInfo', {
      enumerable: false,
      value: userInfo,
    });

    this.nativeErrorCode = userInfo.nativeErrorCode || null;
    Object.defineProperty(this, 'nativeErrorCode', {
      enumerable: false,
      value: userInfo.nativeErrorCode || null,
    });

    this.nativeErrorMessage = userInfo.nativeErrorMessage || null;
    Object.defineProperty(this, 'nativeErrorMessage', {
      enumerable: false,
      value: userInfo.nativeErrorMessage || null,
    });

    this.stack = this.getStackWithMessage(`NativeError: ${this.message}`);
  }

  // todo errorEvent type
  static fromEvent(errorEvent: any, stack?: string): NotifeeNativeError {
    return new NotifeeNativeError({ userInfo: errorEvent }, stack || new Error().stack);
  }

  /**
   * Build a stack trace that includes JS stack prior to calling the native method.
   *
   * @returns {string}
   */
  getStackWithMessage(message: string): string {
    return [message, ...this.jsStack.split('\n').slice(2, 13)].join('\n');
  }
}
