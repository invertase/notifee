import React from 'react';
import { AppRegistry } from 'react-native';
import { Tester, TestHookStore } from 'cavy';
import NativeReporter from 'cavy-native-reporter';

import App from './example/app';
import { NotificationSpec } from './specs/notification.spec';

const testHookStore = new TestHookStore();

function TestApp() {
  return (
    <Tester
      specs={[NotificationSpec]}
      store={testHookStore}
      customReporter={NativeReporter.reporter}
    >
      <App />
    </Tester>
  );
}

AppRegistry.registerComponent('testing', () => TestApp);
