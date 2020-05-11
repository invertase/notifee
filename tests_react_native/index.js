import React from 'react';
import { AppRegistry } from 'react-native';

// TODO switch to example/app once stable - PR #6
import App from './app';

function TestApp() {
  return <App />;
}

AppRegistry.registerComponent('testing', () => TestApp);
