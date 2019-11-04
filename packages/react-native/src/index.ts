/*
 * Copyright (c) 2016-present Invertase Limited
 */

import NotifeeApiModule from './NotifeeApiModule';
import { ModuleStatics, ModuleWithStatics } from '../types/Module';
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
import { NotificationRepeatInterval } from '../types/Notification';
import { version as SDK_VERSION } from './version';

const module = new NotifeeApiModule({
  version: SDK_VERSION,
  nativeModuleName: 'NotifeeApiModule',
  nativeEvents: [],
});

const statics: ModuleStatics = {
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
};

const defaultExports: ModuleWithStatics = Object.assign(module, statics);
export default defaultExports;
