import React from 'react';
import { AppRegistry } from 'react-native';
import { Tester, TestHookStore } from 'cavy';
import defaultReporter from 'cavy/src/reporter';
import NativeReporter from 'cavy-native-reporter';

import App from './app';
import { ModuleSpec } from './specs/module.spec';

const testHookStore = new TestHookStore();

function TestApp() {
  return (
    <Tester
      specs={[ModuleSpec]}
      store={testHookStore}
      reporter={async report => {
        await NativeReporter.reporter(report);
        await defaultReporter(report);
      }}
    >
      <App />
    </Tester>
  );
}

AppRegistry.registerComponent('testing', () => TestApp);
