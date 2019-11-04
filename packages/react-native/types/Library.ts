/**
 * An interface describing the Android specific configuration properties for the `notifee.config.json` file.
 */
export interface NotifeeJsonConfigAndroid {
  licenseKey: string;
  // TODO
}

/**
 * An interface describing the iOS specific configuration properties for the `notifee.config.json` file.
 */
export interface NotifeeJsonConfigIOS {
  licenseKey: string;
  // TODO
}

/**
 * An interface describing the contents of a `notifee.config.json` file.
 */
export interface NotifeeJsonConfig {
  android?: NotifeeJsonConfigAndroid;
  ios?: NotifeeJsonConfigIOS;
}

/**
 * An Error that has occurred in native Android or iOS code converted into a JavaScript Error.
 */
export interface NativeError extends Error {
  /**
   * Firebase error code, e.g. `auth/invalid-email`
   */
  readonly code: string;

  /**
   * Firebase error message
   */
  readonly message: string;

  /**
   * The native sdks returned error code, different per platform
   */
  readonly nativeErrorCode: string | number;

  /**
   * The native sdks returned error message, different per platform
   */
  readonly nativeErrorMessage: string;
}
