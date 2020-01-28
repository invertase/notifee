/*
 * Copyright (c) 2016-present Invertase Limited
 */

/**
 * An Error that has occurred in native Android or iOS code converted into a JavaScript Error.
 */
export interface NativeError extends Error {
  /**
   * Error code, e.g. `invalid-parameter`
   */
  readonly code: string;

  /**
   * Error message
   */
  readonly message: string;

  /**
   * The native returned error code, different per platform
   */
  readonly nativeErrorCode: string | number;

  /**
   * The native returned error message, different per platform
   */
  readonly nativeErrorMessage: string;
}
