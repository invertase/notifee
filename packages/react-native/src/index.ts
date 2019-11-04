/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { version as SDK_VERSION } from './version';

import { Module } from '../types/Module';
import { ModuleWithStatics } from '../types/ModuleWithStatics';
import { NotificationRepeatInterval } from '../types/Notification';
import {
  AndroidBadgeIconType,
  AndroidCategory,
  AndroidColor,
  AndroidDefaults,
  AndroidGroupAlertBehavior,
  AndroidImportance,
  AndroidPriority,
  AndroidSemanticAction,
  AndroidStyle,
  AndroidVisibility,
} from '../types/NotificationAndroid';

// TODO
const module = {} as Module;

const defaultExports: ModuleWithStatics = Object.assign(module, {
  AndroidVisibility,
  AndroidSemanticAction,
  AndroidBadgeIconType,
  AndroidCategory,
  AndroidGroupAlertBehavior,
  AndroidPriority,
  NotificationRepeatInterval,
  AndroidDefaults,
  AndroidImportance,
  AndroidColor,
  AndroidStyle,
  SDK_VERSION,
});

export default defaultExports;

