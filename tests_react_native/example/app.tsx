/*
 *  Copyright (c) 2016-present Invertase Limited
 */

/* eslint-disable no-console */
import React, { useEffect } from 'react';
import { AppRegistry, Button, ScrollView, StyleSheet, Text, View } from 'react-native';

import firebase from '@react-native-firebase/app';
import '@react-native-firebase/messaging';

import Notifee from '@notifee/react-native';

import { notifications } from './notifications';
import { FirebaseMessagingTypes } from '@react-native-firebase/messaging';
import { Notification, NotificationEventType } from '@notifee/react-native/lib/types/Notification';

type RemoteMessage = FirebaseMessagingTypes.RemoteMessage;

const colors: { [key: string]: string } = {
  high: '#f44336',
  default: '#2196f3',
  low: '#ffb300',
  min: '#9e9e9e',
};

const channels = [
  {
    name: 'High Importance',
    id: 'high',
    importance: Notifee.AndroidImportance.HIGH,
  },
  {
    name: 'Default Importance',
    id: 'default',
    importance: Notifee.AndroidImportance.DEFAULT,
  },
  {
    name: 'Low Importance',
    id: 'low',
    importance: Notifee.AndroidImportance.LOW,
  },
  {
    name: 'Min Importance',
    id: 'min',
    importance: Notifee.AndroidImportance.MIN,
  },
];

Notifee.onEvent(({ type, detail, headless }) => {
  console.log('onEvent', { type }, detail, { headless });
  if (type === NotificationEventType.ACTION_PRESS) {
    const notification = detail.notification;

    if (detail.action.id === 'first_action') {
      notification.android.actions[0].title = 'Thanks';
      Notifee.displayNotification(notification).then(() => console.log('Updated'));
    }
  }

  return Promise.resolve();
});

function onMessage(message: RemoteMessage): void {
  console.log('New FCM Message', message);
}

firebase.messaging().setBackgroundMessageHandler(onMessage);

function Root(): any {
  const [id, setId] = React.useState<string | null>(null);

  async function init(): Promise<void> {
    const fcmToken = await firebase.messaging().getToken();
    console.log({ fcmToken });
    firebase.messaging().onMessage(onMessage);
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

AppRegistry.registerComponent('testing', () => Root);

function TestComponent(): any {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Test Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('test_component', () => TestComponent);
