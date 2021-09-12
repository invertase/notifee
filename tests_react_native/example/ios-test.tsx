/*
 *  Copyright (c) 2016-present Invertase Limited
 */

import React from 'react';
import { AppRegistry, Button, Text, View } from 'react-native';

import '@react-native-firebase/messaging';
import '@react-native-firebase/functions';

import Notifee, { EventType } from '@notifee/react-native';

Notifee.onForegroundEvent(async event => {
  switch (event.type) {
    case EventType.UNKNOWN:
      break;
    case EventType.DISMISSED:
      break;
    case EventType.PRESS:
      console.warn('PRESS', {
        id: event.detail.notification ? event.detail.notification.id : 'ERROR_MISSING_NOTIFICATION',
        action_id: event.detail.pressAction ? event.detail.pressAction.id : 'ERROR_MISSING_ID',
      });
      break;
    case EventType.DELIVERED:
      console.warn('DELIVERED', {
        id: event.detail.notification ? event.detail.notification.id : 'ERROR_MISSING_NOTIFICATION',
      });
      break;
    case EventType.APP_BLOCKED:
      break;
    case EventType.CHANNEL_BLOCKED:
      break;
    case EventType.CHANNEL_GROUP_BLOCKED:
      break;
    case EventType.ACTION_PRESS:
      if (event.detail.input) {
        console.warn('ACTION_PRESS_WITH_INPUT', {
          id: event.detail.notification
            ? event.detail.notification.id
            : 'ERROR_MISSING_NOTIFICATION',
          action_id: event.detail.pressAction ? event.detail.pressAction.id : 'ERROR_MISSING_ID',
          action_input: event.detail.input,
        });
      } else {
        console.warn('ACTION_PRESS', {
          action_id: event.detail.pressAction ? event.detail.pressAction.id : 'ERROR_MISSING_ID',
        });
      }
  }
  console.warn(event);
});

(async (): Promise<void> => {
  const initialNotification = await Notifee.getInitialNotification();
  console.warn('INITIAL_NOTIFICATION', initialNotification);
})();

async function testRunner(): Promise<void> {
  // const functionRunner = firebase.functions().httpsCallable('testFunctionDefaultRegion');
  // const response = await functionRunner();
  // console.warn(response);
  await Notifee.setNotificationCategories([
    {
      id: 'test_category',
      actions: [
        {
          id: 'input_lol',
          title: 'Add Input',
          input: true,
        },
        {
          id: 'button',
          title: 'press me',
          foreground: true,
          destructive: true,
        },
      ],
    },
  ]);
  await Notifee.requestPermission();
  await Notifee.displayNotification({
    title: 'hello-test',
    body: 'world-test',
    ios: {
      categoryId: 'test_category',
    },
  });

  await Notifee.displayNotification({
    title: undefined,
    body: undefined,
  });

  await Notifee.displayNotification({
    title: '',
    body: '',
  });

  await Notifee.displayNotification({});
  console.log('Pressed');
}

function TestComponent(): any {
  return (
    <View
      // eslint-disable-next-line react-native/no-inline-styles
      style={{ flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: 'pizza' }}
    >
      <Text>Test Component</Text>
      <Button
        title={`Press Mee`}
        // style={{ width: 300, height: 100 }}
        onPress={async (): Promise<void> => {
          await testRunner();
        }}
      />
    </View>
  );
}

AppRegistry.registerComponent('testing', () => TestComponent);
