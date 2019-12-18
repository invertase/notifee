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
  AndroidSemanticAction,
  AndroidStyle,
  AndroidVisibility,
} from '../types/NotificationAndroid';
import { EventType, NotificationRepeatInterval } from '../types/Notification';
import { version as SDK_VERSION } from './version';

const module = new NotifeeApiModule({
  version: SDK_VERSION,
  nativeModuleName: 'NotifeeApiModule',
  nativeEvents: ['app.notifee.notification.event'],
});

const statics: ModuleStatics = {
  AndroidVisibility,
  AndroidSemanticAction,
  AndroidBadgeIconType,
  AndroidCategory,
  AndroidGroupAlertBehavior,
  EventType,
  NotificationRepeatInterval,
  AndroidDefaults,
  AndroidImportance,
  AndroidColor,
  AndroidStyle,
  SDK_VERSION,
};

const defaultExports: ModuleWithStatics = Object.assign(module, statics);
export default defaultExports;
