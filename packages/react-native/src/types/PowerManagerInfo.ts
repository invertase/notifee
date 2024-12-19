/*
 * Copyright (c) 2016-present Invertase Limited.
 */

/**
 * The interface that represents the information returned from `getPowerManagerInfo()`.
 *
 * View the [Background Restrictions](/react-native/android/background-restrictions) documentation to learn more.
 *
 * @platform android
 */
export interface PowerManagerInfo {
  /**
   * The device manufacturer.
   *
   * For example, Samsung.
   */
  manufacturer?: string;

  /**
   * The device model.
   *
   * For example, Galaxy S8
   */
  model?: string;

  /**
   * The Android version
   *
   * For example, Android 10
   */
  version?: string;

  /**
   * The activity that the user will be navigated to if `openPowerManagerSettings()` is called.
   *
   * Use this as an indicator of what steps the user may have to perform,
   * in-order to prevent your app from being killed.
   *
   * If no activity can be found, value will be null.
   */
  activity?: string | null;
}
