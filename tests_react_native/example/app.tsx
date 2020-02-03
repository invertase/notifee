/*
 *  Copyright (c) 2016-present Invertase Limited
 */

/* eslint-disable no-console */
import React, { useEffect } from 'react';
import { AppRegistry, Button, ScrollView, StyleSheet, Text, View } from 'react-native';

import firebase from '@react-native-firebase/app';
import '@react-native-firebase/messaging';

import Notifee, {
  AndroidChannel,
  AndroidImportance,
  EventType,
  Notification,
} from '@notifee/react-native';

import { notifications } from './notifications';
import { FirebaseMessagingTypes } from '@react-native-firebase/messaging';

type RemoteMessage = FirebaseMessagingTypes.RemoteMessage;

const colors: { [key: string]: string } = {
  custom_sound: '#f449ee',
  high: '#f44336',
  default: '#2196f3',
  low: '#ffb300',
  min: '#9e9e9e',
};

const channels: AndroidChannel[] = [
  {
    name: 'High Importance',
    id: 'high',
    importance: AndroidImportance.HIGH,
    // sound: 'hollow',
  },
  {
    name: '🐴 Sound',
    id: 'custom_sound',
    importance: AndroidImportance.HIGH,
    sound: 'horse.mp3',
  },
  {
    name: 'Default Importance',
    id: 'default',
    importance: AndroidImportance.DEFAULT,
  },
  {
    name: 'Low Importance',
    id: 'low',
    importance: AndroidImportance.LOW,
  },
  {
    name: 'Min Importance',
    id: 'min',
    importance: AndroidImportance.MIN,
  },
];

function onMessage(message: RemoteMessage): void {
  console.log('New FCM Message', message);
}

firebase.messaging().setBackgroundMessageHandler(onMessage);

function Root(): any {
  const [id, setId] = React.useState<string | null>(null);

  async function init(): Promise<void> {
    // await firebase.messaging().registerForRemoteNotifications();
    // const fcmToken = await firebase.messaging().getToken();
    // console.log({ fcmToken });
    // firebase.messaging().onMessage(onMessage);
    // const initialNotification = await notifee.getInitialNotification();
    // console.log({ initialNotification });
    await Promise.all(channels.map($ => Notifee.createChannel($)));
  }

  useEffect(() => {
    init().catch(console.error);
  }, []);

  function displayNotification(notification: Notification, channelId: string): void {
    if (!notification.android) notification.android = {};
    notification.android.channelId = channelId;

    if (Array.isArray(notification)) {
      Promise.all(notification.map($ => Notifee.displayNotification($))).catch(console.error);
    } else {
      Notifee.displayNotification(notification)
        .then(id => setId(id))
        .catch(console.error);
    }
  }

  return (
    <ScrollView style={[styles.container]}>
      {id != null && (
        <View>
          <Button
            title={`Cancel ${id}`}
            onPress={(): void => {
              if (id != null) Notifee.cancelNotification(id);
            }}
          />
        </View>
      )}
      {notifications.map(({ key, notification }): any => (
        <View key={key} style={styles.rowItem}>
          <Text style={styles.header}>{key}</Text>
          <View style={styles.row}>
            {channels.map(channel => (
              <View key={channel.id + key} style={styles.rowItem}>
                <Button
                  title={`>`}
                  onPress={(): any => displayNotification(notification, channel.id)}
                  color={colors[channel.id]}
                />
              </View>
            ))}
          </View>
        </View>
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  header: {
    fontSize: 16,
    padding: 8,
    marginBottom: 8,
    fontWeight: 'bold',
  },
  row: {
    flexDirection: 'row',
    marginBottom: 8,
    paddingBottom: 20,
    borderBottomColor: '#c1c1c1',
    borderBottomWidth: 1,
  },
  rowItem: {
    flex: 1,
    justifyContent: 'center',
    marginHorizontal: 8,
  },
});

Notifee.onEvent(async event => {
  const { type, headless, detail } = event;

  let eventTypeString;

  switch (type) {
    case EventType.UNKNOWN:
      eventTypeString = 'UNKNOWN';
      console.log('Notification Id', detail.notification?.id);
      break;
    case EventType.DISMISSED:
      eventTypeString = 'DISMISSED';
      console.log('Notification Id', detail.notification?.id);
      break;
    case EventType.PRESS:
      eventTypeString = 'PRESS';
      console.log('Action ID', detail.pressAction?.id || 'N/A');
      break;
    case EventType.ACTION_PRESS:
      eventTypeString = 'ACTION_PRESS';
      console.log('Action ID', detail.pressAction?.id || 'N/A');
      break;
    case EventType.DELIVERED:
      eventTypeString = 'DELIVERED';
      console.log('Notification Id', detail.notification?.id);
      break;
    case EventType.APP_BLOCKED:
      eventTypeString = 'APP_BLOCKED';
      console.log('Blocked', detail.blocked);
      break;
    case EventType.CHANNEL_BLOCKED:
      eventTypeString = 'CHANNEL_BLOCKED';
      console.log('Channel', detail.channel);
      break;
    case EventType.CHANNEL_GROUP_BLOCKED:
      eventTypeString = 'CHANNEL_GROUP_BLOCKED';
      console.log('Channel Group', detail.channelGroup);
      break;
    case EventType.SCHEDULED:
      eventTypeString = 'SCHEDULED';
      break;
    default:
      eventTypeString = 'UNHANDLED_NATIVE_EVENT';
  }

  if (headless) {
    console.warn(`Received a ${eventTypeString} event in headless mode.`);
  } else {
    console.warn(`Received a ${eventTypeString} event in JS mode.`);
  }
});

// Notifee.registerForegroundService(notification => {
//   return new Promise(resolve => {
//     Notifee.onEvent(({ detail }) => {
//       console.log(detail);
//       if (detail?.pressAction?.id === 'stop') {
//         return resolve();
//       }
//     });
//   });
// });

AppRegistry.registerComponent('testing', () => Root);

function TestComponent(): any {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Test Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('test_component', () => TestComponent);
