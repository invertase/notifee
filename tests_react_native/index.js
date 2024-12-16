import { AppRegistry } from 'react-native';
import notifee from '@notifee/react-native';

import App from './example/app';

notifee.onBackgroundEvent(async event => {
  console.log('notifee.onBackgroundEvent triggered: ' + JSON.stringify(event));
});

AppRegistry.registerComponent('testing', () => App);
