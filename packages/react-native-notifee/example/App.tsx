/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {Component, useEffect} from 'react';
import {AppRegistry, Platform, StyleSheet, Text, View} from 'react-native';
import notifee, {
  EventType,
  IOSAuthorizationStatus,
} from '@notifee/react-native';
import {Content} from './src/content';
import {categories} from './src/utils/categories';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

notifee.onBackgroundEvent(async ({type, detail}) => {
  const {notification, pressAction} = detail;
  console.log(
    `[onBackgroundEvent] notification id: ${notification?.id},  event type: ${EventType[type]}, press action: ${pressAction?.id}`,
  );
  // Check if the user pressed the "Mark as read" action
  if (
    type === EventType.ACTION_PRESS &&
    pressAction?.id === 'first_action_reply'
  ) {
    console.log('[onBackgroundEvent] ACTION_PRESS: first_action_reply');

    // Remove the notification
    if (notification?.id) {
      await notifee.cancelNotification(notification?.id);
    }
  }
});

function App() {
  const requestUserPermission = async () => {
    const settings = await notifee.requestPermission();

    if (settings.authorizationStatus >= IOSAuthorizationStatus.AUTHORIZED) {
      console.log('Permission settings:', settings);
    } else {
      console.log('User declined permissions');
    }
  };

  // Subscribe to events
  useEffect(() => {
    notifee.setNotificationCategories(categories);
    return notifee.onForegroundEvent(async ({type, detail}) => {
      const {notification, pressAction} = detail;
      const pressActionLabel = pressAction
        ? `, press action: ${pressAction?.id}`
        : '';
      console.log(
        `[onForegroundEvent] notification id: ${notification?.id},  event type: ${EventType[type]}${pressActionLabel}`,
      );

      switch (type) {
        case EventType.DISMISSED:
          console.log(
            '[onForegroundEvent] User dismissed notification',
            notification,
          );
          break;
        case EventType.PRESS:
          console.log(
            '[onForegroundEvent] User pressed notification',
            notification,
          );
          break;
        case EventType.ACTION_PRESS:
          console.log(
            '[onForegroundEvent] User pressed an action',
            notification,
            detail.pressAction,
          );

          if (detail.pressAction?.id === 'first_action_reply') {
            // perform any server calls here and cancel notification
            if (notification?.id) {
              await notifee.cancelDisplayedNotification(notification.id);
            }
          }
          break;
      }
    });
  }, []);

  useEffect(() => {
    (async () => {
      await requestUserPermission();
    })();
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.welcome}>Notifee Demo</Text>
      <Text style={styles.instructions}>To get started, edit content.tsx</Text>
      <Text style={styles.instructions}>{instructions}</Text>
      <Text style={styles.instructions}>Notifee Version</Text>
      <Text style={styles.instructions}>{notifee.SDK_VERSION}</Text>
      <Content />
    </View>
  );
}

export default App;

function FullScreenComponent(): any {
  return (
    // eslint-disable-next-line react-native/no-inline-styles
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>FullScreen Launch Activity</Text>
    </View>
  );
}

AppRegistry.registerComponent('custom', () => FullScreenComponent);

function FullScreenMainComponent(): any {
  return (
    // eslint-disable-next-line react-native/no-inline-styles
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>FullScreen Main Component</Text>
    </View>
  );
}

AppRegistry.registerComponent(
  'full-screen-main-component',
  () => FullScreenMainComponent,
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
