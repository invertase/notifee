/*
 *  Copyright (c) 2016-present Invertase Limited
 */

import React, { useEffect } from 'react';
import { AppRegistry, Button, ScrollView, StyleSheet, Text, View } from 'react-native';

import firebase from '@react-native-firebase/app';
import '@react-native-firebase/messaging';

import Notifee, {
  AndroidChannel,
  AndroidImportance,
  Notification,
  EventType,
  Event,
  IOSAuthorizationStatus,
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

async function onMessage(message: RemoteMessage): Promise<void> {
  console.log('New FCM Message', message);
  Notifee.displayNotification({
    id: message.collapseKey,
    title: 'hello',
    body: 'world',
    android: { channelId: 'default', tag: 'hello1' },
  });
}

firebase.messaging().setBackgroundMessageHandler(onMessage);
function Root(): any {
  const [id, setId] = React.useState<string | null>(null);

  async function init(): Promise<void> {
    const fcmToken = await firebase.messaging().getToken();
    console.log({ fcmToken });
    firebase.messaging().onMessage(onMessage);

    const initialNotification = await Notifee.getInitialNotification();
    console.log({ initialNotification });
    await Promise.all(channels.map($ => Notifee.createChannel($)));
    await Notifee.setNotificationCategories([
      {
        id: 'actions1',
        actions: [
          {
            id: 'like',
            title: 'Like Post',
          },
          {
            id: 'dislike',
            title: 'Dislike Post',
          },
        ],
      },
    ]);
    await Notifee.setNotificationCategories([
      {
        id: 'stop',
        actions: [
          {
            id: 'stop',
            title: 'Dismiss',
          },
        ],
      },
    ]);
    await Notifee.setNotificationCategories([
      {
        id: 'dismiss',
        actions: [
          {
            id: 'dismiss',
            title: 'Dismiss',
          },
        ],
      },
    ]);
  }

  useEffect(() => {
    init().catch(console.error);
  }, []);

  async function displayNotification(
    notification: Notification | Notification[],
    channelId: string,
  ): Promise<void> {
    let currentPermissions = await Notifee.getNotificationSettings();
    if (currentPermissions.authorizationStatus !== IOSAuthorizationStatus.AUTHORIZED) {
      await Notifee.requestPermission({ sound: true, criticalAlert: true }).then(props =>
        console.log('fullfilled,', props),
      );
    }
    currentPermissions = await Notifee.getNotificationSettings();
    console.log('currentPermissions', currentPermissions);
    await Notifee.setNotificationCategories([
      {
        id: 'stop',
        actions: [
          {
            id: 'stop',
            title: 'Dismiss',
          },
        ],
      },
    ]);
    if (Array.isArray(notification)) {
      Promise.all(notification.map($ => Notifee.displayNotification($))).catch(console.error);
    } else {
      if (!notification.android) notification.android = {};
      notification.android.channelId = channelId;

      const date = new Date(Date.now());
      date.setSeconds(date.getSeconds() + 5);
      Notifee.displayNotification(notification)
        .then(notificationId => setId(notificationId))
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
          <Button
            title={`Cancel trigger ${id}`}
            onPress={(): void => {
              if (id != null) Notifee.cancelTriggerNotification(id);
            }}
          />
          <Button
            title={`Cancel displayed ${id}`}
            onPress={(): void => {
              if (id != null) Notifee.cancelDisplayedNotification(id);
            }}
          />
          <Button
            title={`get notifications`}
            onPress={async (): Promise<void> => {
              const ids = await Notifee.getTriggerNotificationIds();
              console.log(ids);
            }}
          />
          <Button
            title={`get power manager info`}
            onPress={async (): Promise<void> => {
              console.log(await Notifee.getPowerManagerInfo());
            }}
          />
          <Button
            title={`open power manager `}
            onPress={async (): Promise<void> => {
              console.log(await Notifee.openPowerManagerSettings());
            }}
          />
          {/* <Button
            title={`cancel notification`}
            onPress={async () => {
              await Notifee.cancelNotification(id);
            }}
          />
           <Button
            title={`cancel trigger notification`}
            onPress={async () => {
              await Notifee.cancelTriggerNotification(id);
       
            }}
          /> */}
          <Button
            title={`get channels`}
            onPress={async (): Promise<void> => {
              const buttonChannels = await Notifee.getChannels();
              buttonChannels.forEach(res => {
                if (res.id === 'custom-vibrationsouttt') {
                  console.log('res', res);
                }
              });
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
                  title={`>.`}
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
      console.warn(`Received a ${eventTypeString} ${state} event in JS mode.`, event);
      break;
    case EventType.ACTION_PRESS:
      eventTypeString = 'ACTION_PRESS';
      console.log('Action ID', detail.pressAction?.id || 'N/A');
      break;
    case EventType.DELIVERED:
      eventTypeString = 'DELIVERED';
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

  console.warn(`Received a ${eventTypeString} ${state} event in JS mode.`, event);
  console.warn(JSON.stringify(event));
}

Notifee.onForegroundEvent(event => {
  logEvent('Foreground', event);
});

Notifee.onBackgroundEvent(async ({ type, detail }) => {
  console.log('onBackgroundEvent');
  logEvent('Background', { type, detail });

  const { notification, pressAction } = detail;

  // Check if the user pressed a cancel action
  if (
    type === EventType.ACTION_PRESS &&
    ['first_action', 'second_action'].includes(pressAction?.id || 'N/A')
  ) {
    // Remove the notification
    await Notifee.cancelNotification(notification?.id || 'N/A');
    console.warn('Notification Cancelled', pressAction?.id);
  }
});

Notifee.registerForegroundService(notification => {
  console.warn('Foreground service started.', notification);
  return new Promise(resolve => {
    /**
     * Cancel the notification and resolve the service promise so the Headless task quits.
     */
    async function stopService(id?: string): Promise<void> {
      console.warn('Stopping service, using notification id: ' + id);
      clearInterval(interval);
      if (id) {
        await Notifee.cancelNotification(id);
      }
      return resolve();
    }

    /**
     * Cancel our long running task if the user presses the 'stop' action.
     */
    async function handleStopActionEvent({ type, detail }: Event): Promise<void> {
      console.log('handleStopActionEvent1 type:', type, 'pressactionid', detail?.pressAction?.id);

      if (type !== EventType.ACTION_PRESS) return;
      console.log('handleStopActionEvent2 type:', type, 'pressactionid', detail?.pressAction?.id);

      if (detail?.pressAction?.id === 'stop') {
        console.warn('Stop action was pressed');
        await stopService(detail.notification?.id);
      }
    }

    Notifee.onForegroundEvent(handleStopActionEvent);
    Notifee.onBackgroundEvent(handleStopActionEvent);

    // A fake progress updater.
    let current = 1;
    const interval = setInterval(async () => {
      notification.android = {
        progress: { current: current },
      };
      Notifee.displayNotification(notification);
      current++;
    }, 125);

    setTimeout(async () => {
      clearInterval(interval);
      console.warn('Background work has completed.');
      await stopService(notification.id);
    }, 15000);
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

// AppRegistry.registerComponent('testing', () => Root);

function TestComponent(): any {
  return (
    // eslint-disable-next-line react-native/no-inline-styles
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Test Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('test_component', () => TestComponent);

export default Root;
