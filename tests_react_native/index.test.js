import React from 'react';
import { AppRegistry } from 'react-native';
import { Tester, TestHookStore } from 'cavy';
import NativeReporter from 'cavy-native-reporter';

import App from './example/app';
import { NotificationSpec } from './specs/notification.spec';
import { ApiSpec } from './specs/api.spec';

const testHookStore = new TestHookStore();

function TestApp() {
  return (
    <Tester
      specs={[NotificationSpec, ApiSpec]}
      store={testHookStore}
      customReporter={NativeReporter.reporter}
    >
      <App />
    </Tester>
  );
}

AppRegistry.registerComponent('testing', () => TestApp);
