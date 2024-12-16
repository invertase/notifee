import { AppRegistry } from 'react-native';
import '@react-native-firebase/messaging';
import firebase from '@react-native-firebase/app';
import notifee from '@notifee/react-native';

import App from './example/app';

firebase.messaging().setBackgroundMessageHandler(async message => {
  console.log('onBackgroundMessage New FCM Message', message);
});

notifee.onBackgroundEvent(async event => {
  console.log('notifee.onBackgroundEvent triggered: ' + JSON.stringify(event));
});

AppRegistry.registerComponent('testing', () => App);
