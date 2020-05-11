import React from 'react';
import { AppRegistry } from 'react-native';
import { Tester, TestHookStore } from 'cavy';
import defaultReporter from 'cavy/src/reporter';
import NativeReporter from 'cavy-native-reporter';

import App from './app';
import { NotificationSpec } from './specs/notification.spec';

const testHookStore = new TestHookStore();

function TestApp() {
  return (
    <Tester
      specs={[NotificationSpec]}
      store={testHookStore}
      reporter={async report => {
        await defaultReporter(report);
        await NativeReporter.reporter(report);
      }}
    >
      <App />
    </Tester>
  );
}

AppRegistry.registerComponent('testing', () => TestApp);
