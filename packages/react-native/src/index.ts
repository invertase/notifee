/*
 * Copyright (c) 2016-present Invertase Limited
 */

import NotifeeApiModule from './NotifeeApiModule';
import { ModuleStatics, ModuleWithStatics } from '../types/Module';
import { version as SDK_VERSION } from './version';

const module = new NotifeeApiModule({
  version: SDK_VERSION,
  nativeModuleName: 'NotifeeApiModule',
  nativeEvents: ['app.notifee.notification.event'],
});

const statics: ModuleStatics = {
  SDK_VERSION,
};

const defaultExports: ModuleWithStatics = Object.assign(module, statics);
export default defaultExports;

export * from '../types/Library';
export * from '../types/Notification';
export * from '../types/NotificationIOS';
export * from '../types/NotificationAndroid';
