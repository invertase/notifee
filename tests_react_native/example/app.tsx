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
  Importance,
  Notification,
  EventType,
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
    importance: Importance.HIGH,
    // sound: 'hollow',
  },
  {
    name: '🐴 Sound',
    id: 'custom_sound',
    importance: Importance.HIGH,
    sound: 'horse.mp3',
  },
  {
    name: 'Default Importance',
    id: 'default',
    importance: Importance.DEFAULT,
  },
  {
    name: 'Low Importance',
    id: 'low',
    importance: Importance.LOW,
  },
  {
    name: 'Min Importance',
    id: 'min',
    importance: Importance.MIN,
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

function logEvent(state: string, event: any): void {
  const { type, detail } = event;

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
    default:
      eventTypeString = 'UNHANDLED_NATIVE_EVENT';
  }

  console.warn(`Received a ${eventTypeString} ${state} event in JS mode.`);
}

Notifee.onForegroundEvent(event => {
  logEvent('Foreground', event);
});

Notifee.onBackgroundEvent(async ({ type, detail }) => {
  logEvent('Background', { type, detail });

  const { notification, pressAction } = detail;

  // Check if the user pressed a cancel action
  if (
    type === EventType.ACTION_PRESS &&
    ['first_action', 'second_action'].includes(pressAction?.id)
  ) {
    // Remove the notification
    await Notifee.cancelNotification(notification.id);
    console.warn('Notification Cancelled', pressAction.id);
  }
});

Notifee.registerForegroundService(notification => {
  return new Promise(resolve => {
    Notifee.onForegroundEvent(async ({ type, detail }) => {
      logEvent('Foreground Service', { type, detail });

      if (detail?.pressAction?.id === 'stop') {
        console.warn('Notification Service Stopped for', notification.id);
        await Notifee.cancelNotification(notification.id);
        return resolve();
      }
    });
  });
});

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

AppRegistry.registerComponent('testing', () => Root);

function TestComponent(): any {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Test Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('test_component', () => TestComponent);
