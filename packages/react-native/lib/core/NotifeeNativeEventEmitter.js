/*
 * Copyright (c) 2016-present Invertase Limited
 */

import { NativeEventEmitter, NativeModules } from 'react-native';
import { CORE_NATIVE_MODULE_NAME } from './NotifeeConstants';

const coreModule = NativeModules[CORE_NATIVE_MODULE_NAME];

class NotifeeNativeEventEmitter extends NativeEventEmitter {
  constructor() {
    super(coreModule);
    this.ready = false;
  }

  addListener(eventType, listener, context) {
    if (!this.ready) {
      coreModule.eventsNotifyReady(true);
      this.ready = true;
    }

    coreModule.eventsAddListener(eventType);
    return super.addListener(`notifee_${eventType}`, listener, context);
  }

  removeAllListeners(eventType) {
    coreModule.eventsRemoveListener(eventType, true);
    super.removeAllListeners(`notifee_${eventType}`);
  }

  removeSubscription(subscription) {
    coreModule.eventsRemoveListener(subscription.eventType.replace('notifee_'), false);
    super.removeSubscription(subscription);
  }
}

export default new NotifeeNativeEventEmitter();
