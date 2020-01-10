/*
 *  Copyright (c) 2016-present Invertase Limited
 */

/* eslint-disable no-console */
import React, { useEffect } from 'react';
import { AppRegistry, ScrollView, StyleSheet, Text, View, Button } from 'react-native';

import firebase from '@react-native-firebase/app';
import '@react-native-firebase/messaging';

import notifee from '@notifee/react-native';

import notifications from './notifications';

const colors = {
  high: '#f44336',
  default: '#2196f3',
  low: '#ffb300',
  min: '#9e9e9e',
};

const channels = [
  {
    name: 'High Importance',
    id: 'high',
    importance: notifee.AndroidImportance.HIGH,
  },
  {
    name: 'Default Importance',
    id: 'default',
    importance: notifee.AndroidImportance.DEFAULT,
  },
  {
    name: 'Low Importance',
    id: 'low',
    importance: notifee.AndroidImportance.LOW,
  },
  {
    name: 'Min Importance',
    id: 'min',
    importance: notifee.AndroidImportance.MIN,
  },
];

notifee.onEvent((eventType, event, headless) => {
  console.log('onEvent', { eventType }, event, { headless });

  if (eventType === 2) {
    const notification = event.notification;

    if (event.action.id === 'first_action') {
      notification.android.actions[0].title = 'Thanks';
      notifee.displayNotification(notification).then(() => console.log('Updated'));
    }
  }

  return Promise.resolve();
});

function onMessage(message) {
  console.log('New FCM Message', message);
}

firebase.messaging().setBackgroundMessageHandler(onMessage);

function Root() {
  const [id, setId] = React.useState(null);

  async function init() {
    const fcmToken = await firebase.messaging().getToken();
    console.log({ fcmToken });
    firebase.messaging().onMessage(onMessage);
    // const initialNotification = await notifee.getInitialNotification();
    // console.log({ initialNotification });
    await Promise.all(channels.map($ => notifee.createChannel($)));
  }

  useEffect(() => {
    init().catch(console.error);
  }, []);

  function displayNotification(notification, channelId) {
    notification.android.channelId = channelId;

    if (Array.isArray(notification)) {
      Promise.all(notification.map($ => notifee.displayNotification($))).catch(console.error);
    } else {
      notifee
        .displayNotification(notification)
        .then(id => setId(id))
        .catch(console.error);
    }
  }

  return (
    <ScrollView style={[styles.container]}>
      {!!id && (
        <View>
          <Button title={`Cancel ${id}`} onPress={() => notifee.cancelNotification(id)} />
        </View>
      )}
      <View style={styles.row}>
        <View style={{ flex: 1 }} />
        {channels.map(channel => (
          <View key={channel.id} style={{ flex: 1, alignItems: 'center' }}>
            <Text>{channel.id.toUpperCase()}</Text>
          </View>
        ))}
      </View>
      {notifications.map(({ key, notification }) => (
        <View key={key} style={styles.row}>
          <View style={styles.rowItem}>
            <Text style={{ fontSize: 13 }}>{key}</Text>
          </View>
          {channels.map(channel => (
            <View key={channel.id + key} style={styles.rowItem}>
              <Button
                title={`!`}
                onPress={() => displayNotification(notification, channel.id)}
                color={colors[channel.id]}
              />
            </View>
          ))}
        </View>
      ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  row: {
    flexDirection: 'row',
    marginHorizontal: 8,
    marginTop: 8,
  },
  rowItem: {
    flex: 1,
    justifyContent: 'center',
    marginHorizontal: 2,
  },
});

AppRegistry.registerComponent('testing', () => Root);

function TestComponent() {
  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Test Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('test_component', () => TestComponent);

