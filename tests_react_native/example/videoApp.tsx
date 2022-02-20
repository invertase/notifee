/*
 *  Copyright (c) 2016-present Invertase Limited
 */

import React, { useEffect } from 'react';
import {
  ScrollView,
  StyleSheet,
  Text,
  View,
  Dimensions,
  Image,
  TouchableOpacity,
} from 'react-native';

import firebase from '@react-native-firebase/app';
import { FirebaseMessagingTypes } from '@react-native-firebase/messaging';

import Notifee, { EventType, Event, AuthorizationStatus } from '@notifee/react-native';

type RemoteMessage = FirebaseMessagingTypes.RemoteMessage;

async function onMessage(message: RemoteMessage): Promise<void> {
  console.log('New FCM Message', message);

  if (message.data) {
    Notifee.displayNotification(JSON.parse(message.data?.notifee));
  }
}

// @ts-ignore FIXME what is Root and why doesn't typescript like it?
// eslint-disable-next-line @typescript-eslint/no-unused-vars
function Root(): any {
  // const [id, setId] = React.useState<string | null>(null);

  async function init(): Promise<void> {
    const fcmToken = await firebase.messaging().getToken();
    console.log({ fcmToken });
    firebase.messaging().onMessage(onMessage);
  }

  const styles = StyleSheet.create({
    container: {
      height: Dimensions.get('window').height,
    },
    body: {
      flex: 1,
      justifyContent: 'center',
      alignContent: 'center',
      flexDirection: 'column',
      alignItems: 'center',
      textAlign: 'center',
    },
    imageContainer: { justifyContent: 'center', alignContent: 'center', marginBottom: 60 },
    logo: {
      width: 100,
      height: 100,
    },
    button: {
      justifyContent: 'center',
      alignContent: 'center',
    },
    titleText: {
      fontSize: 20,
      fontWeight: 'bold',
      textAlign: 'center',
    },
    alertText: {
      color: '#2c8be6',
    },
  });

  useEffect(() => {
    init().catch(console.error);
  }, []);

  return (
    <ScrollView style={[styles.container]} contentContainerStyle={styles.body}>
      <View style={styles.body}>
        <View style={styles.imageContainer}>
          <Image style={styles.logo} source={require('../assets/notifee-logo.png')} />
          <Text style={styles.titleText}>Notifee</Text>
        </View>
        <TouchableOpacity
          onPress={async (): Promise<void> => {
            const currentPermissions = await Notifee.getNotificationSettings();
            if (currentPermissions.authorizationStatus !== AuthorizationStatus.AUTHORIZED) {
              await Notifee.requestPermission();
            }
            Notifee.displayNotification({
              // @ts-ignore FIXME what is key and why doesn't typescript like this line?
              key: 'Big Picture Style',
              notification: {
                title: 'Big Picture Style',
                body: 'Expand for a cat',
                ios: {
                  attachments: [
                    {
                      id: 'image',
                      url: 'https://github.githubassets.com/images/modules/open_graph/github-mark.png',
                      thumbnailHidden: false,
                      thumbnailClippingRect: {
                        x: 0.1,
                        y: 0.1,
                        width: 0.1,
                        height: 0.1,
                      },
                    },
                  ],
                },
              },
            });
            // Alert.alert(
            //   'Restrictions Detected',
            //   'To ensure notifications are delivered, please disable battery optimization for the app.',
            //   [
            //     // 3. launch intent to navigate the user to the appropriate screen
            //     {
            //       text: 'OK, open settings',
            //       onPress: () => Notifee.openBatteryOptimizationSettings(),
            //     },
            //     {
            //       text: 'Cancel',
            //       onPress: () => console.log('Cancel Pressed'),
            //       style: 'cancel',
            //     },
            //   ],
            //   { cancelable: false },
            // );
          }}
        >
          <View style={styles.button}>
            {/* eslint-disable-next-line react-native/no-inline-styles */}
            <Text style={{ color: '#2c8be6' }}>Display Notification</Text>
          </View>
        </TouchableOpacity>
      </View>
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
    case EventType.TRIGGER_NOTIFICATION_CREATED:
      eventTypeString = 'TRIGGER_NOTIFICATION_CREATED';
      console.log('Trigger Notification');
      break;
    default:
      eventTypeString = 'UNHANDLED_NATIVE_EVENT';
  }

  console.warn(`Received a ${eventTypeString} ${state} event in JS mode.`);
  // console.warn(JSON.stringify(event));
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
    ['first_action', 'second_action'].includes(pressAction?.id || 'N/A')
  ) {
    // Remove the notification
    await Notifee.cancelNotification(notification?.id || 'N/A');
    console.warn('Notification Cancelled', pressAction?.id);
  }
});

Notifee.registerForegroundService(notification => {
  console.warn('Foreground service started.');
  return new Promise(resolve => {
    /**
     * Cancel the notification and resolve the service promise so the Headless task quits.
     */
    async function stopService(): Promise<void> {
      console.warn('Stopping service.');
      if (notification.id) {
        await Notifee.cancelNotification(notification?.id);
      }
      return resolve();
    }

    /**
     * Cancel our long running task if the user presses the 'stop' action.
     */
    async function handleStopActionEvent({ type, detail }: Event): Promise<void> {
      if (type !== EventType.ACTION_PRESS) return;
      if (detail?.pressAction?.id === 'stop') {
        console.warn('Stop action was pressed');
        await stopService();
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
      await Notifee.displayNotification(notification);
      current++;
    }, 125);

    setTimeout(async () => {
      clearInterval(interval);
      console.warn('Background work has completed.');
      await stopService();
    }, 15000);
  });
});
