/* eslint-disable @typescript-eslint/no-explicit-any */
/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { EmitterSubscription, NativeEventEmitter, NativeModules } from 'react-native';
import { CORE_NATIVE_MODULE_NAME } from './NotifeeConstants';

const coreModule = NativeModules[CORE_NATIVE_MODULE_NAME];

class NotifeeNativeEventEmitter extends NativeEventEmitter {
  private ready: boolean;

  constructor() {
    super(coreModule);
    this.ready = false;
  }

  addListener(
    eventType: string,
    listener: (...args: any) => any,
    context?: any,
  ): EmitterSubscription {
    if (!this.ready) {
      coreModule.eventsNotifyReady(true);
      this.ready = true;
    }

    coreModule.eventsAddListener(eventType);
    return super.addListener(`notifee_${eventType}`, listener, context);
  }

  removeAllListeners(eventType: string): void {
    coreModule.eventsRemoveListener(eventType, true);
    super.removeAllListeners(`notifee_${eventType}`);
  }

  removeSubscription(subscription: EmitterSubscription): void {
    coreModule.eventsRemoveListener(subscription.eventType.replace('notifee_', ''), false);
    super.removeSubscription(subscription);
  }
}

export default new NotifeeNativeEventEmitter();
