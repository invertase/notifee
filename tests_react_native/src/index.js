import React from 'react';
import { AppRegistry, Text } from 'react-native';

const App = () => <Text style={{ fontSize: 52 }}>Hi, Notifee</Text>;

const appName = 'Notifee';

AppRegistry.registerComponent(appName, () => App);
AppRegistry.runApplication(appName, {
  rootTag: document.getElementById('root'),
});
