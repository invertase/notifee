/*
 *  Copyright (c) 2016-present Invertase Limited
 */

import React, { useEffect } from 'react';
import {
  Alert,
  AppRegistry,
  Button,
  SafeAreaView,
  ScrollView,
  StyleSheet,
  Text,
  View,
} from 'react-native';

import Notifee, {
  AndroidChannel,
  AndroidImportance,
  Notification,
  EventType,
  Event,
  AuthorizationStatus,
  TimestampTrigger,
  RepeatFrequency,
  AndroidCategory,
  AndroidStyle,
  AndroidLaunchActivityFlag,
} from '@notifee/react-native';

import { FirebaseMessagingTypes } from '@react-native-firebase/messaging';

export const notifications: { key: string; notification: Notification | Notification[] }[] = [
  {
    key: 'Empty',
    notification: {
      android: {
        channelId: 'custom_sound',
        pressAction: {
          id: 'default',
        },
      },
      ios: {
        sound: 'media/kick.wav',
      },
    },
  },
  {
    key: 'FullScreenAction',
    notification: {
      title: 'Full-screen',
      android: {
        asForegroundService: false,
        channelId: 'high',
        autoCancel: false,
        category: AndroidCategory.CALL,
        importance: AndroidImportance.HIGH,
        fullScreenAction: {
          id: 'default',
          mainComponent: 'full_screen',
        },
      },
    },
  },
  {
    key: 'Basic',
    notification: {
      id: 'basic',
      title: '<p style="color: #4caf50;"><b>Styled HTMLTitle</span></p></b></p> &#128576;',
      android: {
        channelId: 'high',
        pressAction: {
          id: 'default',
        },
      },
      ios: {},
    },
  },
  {
    key: 'Tag',
    notification: {
      id: 'tag',
      title: 'With tag',
      android: {
        channelId: 'high',
        pressAction: {
          id: 'default',
        },
        tag: 'example-tag',
      },
      ios: {},
    },
  },
  {
    key: 'Subtitle',
    notification: {
      title: 'Title',
      body: 'Body of the notification',
      subtitle: 'Subtitle text',
      android: {
        channelId: 'foo',
      },
      ios: {
        sound: 'Beacon',
      },
    },
  },

  {
    key: 'Color',
    notification: {
      title: 'Color',
      body: 'Only the small icon should change color',
      android: {
        smallIcon: 'ic_small_icon',
        color: '#553c9a',
        channelId: 'foo',
      },
      ios: {
        critical: true,
        criticalVolume: 1.0,
        sound: 'Beacon',
      },
    },
  },

  {
    key: 'Actions (event only)',
    notification: {
      title: 'Actions',
      body: 'Notification with actions',
      ios: {
        categoryId: 'actions',
      },
      android: {
        pressAction: {
          id: 'default',
        },
        autoCancel: true,
        color: '#9c27b0',
        onlyAlertOnce: true,
        // inputHistory: ['Hey', 'Food tonight?'],
        actions: [
          {
            title: 'Reply, Open & Cancel',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'default',
            },
            input: true,
            // input: {
            //   choices: ['Hey'],
            //   allowFreeFormInput: true,
            //   editableChoices: true,
            //   placeholder: 'Reply...',
            // },
          },
          {
            title: 'Open & Cancel',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'second_action',
              mainComponent: 'test_component',
            },
          },
          {
            title: 'Nothing',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'third_action',
            },
          },
        ],
        channelId: 'high',
      },
    },
  },
  {
    key: 'Service',
    notification: {
      title: 'Background Task',
      body: 'Doing some work...',
      android: {
        autoCancel: true,
        color: '#9c27b0',
        onlyAlertOnce: true,
        asForegroundService: true,
        progress: {
          max: 120,
          current: 0,
        },
        actions: [
          {
            title: 'Stop',
            icon: 'https://invertase.io/icons/icon-48x48.png',
            pressAction: {
              id: 'stop',
            },
          },
        ],
        channelId: 'foo',
      },
      ios: {
        categoryId: 'stop',
      },
    },
  },
  {
    key: 'Big Picture Style',
    notification: {
      title: 'Big Picture Style',
      body: 'Expand for a cat',
      android: {
        channelId: 'foo',
        style: {
          type: AndroidStyle.BIGPICTURE,
          picture: 'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
        },
      },
      ios: {
        attachments: [
          {
            id: 'image',
            url: 'media/cat.png',
            thumbnailHidden: true,
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
  },
  {
    key: 'iOS Video',
    notification: {
      title: 'iOS Attachments: Video',
      body: 'Expand to see a video',
      ios: {
        attachments: [
          {
            id: 'video',
            url: 'media/dog.mp4',
            thumbnailTime: 1,
          },
        ],
      },
    },
  },
  {
    key: 'iOS GIF',
    notification: {
      title: 'iOS Attachments: GIF',
      body: 'Expand to see a gif',
      ios: {
        attachments: [
          {
            id: 'gif',
            url: 'media/back.gif',
            thumbnailTime: 6,
          },
        ],
      },
    },
  },
  {
    key: 'Android Launch Activity Flags',
    notification: {
      title: 'Testing SINGLE_TOP launch.',
      body: 'Expand for a cat!',
      android: {
        channelId: 'foo',
        pressAction: {
          id: 'default',
          launchActivity: 'default',
          launchActivityFlags: [AndroidLaunchActivityFlag.SINGLE_TOP],
        },
        lights: ['#ffffff', 25, 50],
        style: {
          type: AndroidStyle.BIGPICTURE,
          picture: 'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png',
        },
      },
    },
  },
  {
    key: 'Chronometer', // See git issue #53
    notification: {
      android: {
        color: '#9c27b0',
        channelId: 'foo',
        showChronometer: true,
        chronometerDirection: 'down',
        timestamp: Date.now() + 200000,
      },
    },
  },
  {
    key: 'Dismiss', // See git issue #62
    notification: [
      {
        id: 'personal',
        title: 'Personal Chat',
        android: {
          channelId: 'default',
          groupSummary: true,
          groupId: 'Personal',
        },
      },
      {
        title: 'Dismiss',
        body: 'Notification with dismiss',
        ios: {
          categoryId: 'dismiss',
        },
        android: {
          autoCancel: true,
          color: '#9c27b0',
          onlyAlertOnce: true,
          actions: [
            {
              title: 'Dismiss',
              icon: 'https://invertase.io/icons/icon-48x48.png',
              pressAction: {
                id: 'dismiss',
              },
            },
          ],
          channelId: 'default',
          groupId: 'Personal',
        },
      },
    ],
  },
];

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
  console.log('New FCM Message', message.messageId);
  await Notifee.displayNotification({
    title: 'onMessage',
    body: `with message ${message.messageId}`,
    android: { channelId: 'default', tag: 'hello1' },
  });
}

async function onBackgroundMessage(message: RemoteMessage): Promise<void> {
  console.log('onBackgroundMessage New FCM Message', message);
  // await Notifee.displayNotification({
  //   title: 'onMessage',
  //   body: `with message ${message.messageId}`,
  //   android: { channelId: 'default', tag: 'hello1' },
  // });
}

function Root(): any {
  const [id, setId] = React.useState<string | null>(null);

  async function init(): Promise<void> {
    // const fcmToken = await firebase.messaging().getToken();
    // console.log({ fcmToken });
    // firebase.messaging().onMessage(onMessage);

    const initialNotification = await Notifee.getInitialNotification();
    console.log('init: ', { initialNotification });
    await Promise.all(channels.map($ => Notifee.createChannel($)));
    await Notifee.setNotificationCategories([
      {
        id: 'actions',
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
      {
        id: 'stop',
        actions: [
          {
            id: 'stop',
            title: 'Dismiss',
          },
        ],
      },
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
    if (currentPermissions.authorizationStatus !== AuthorizationStatus.AUTHORIZED) {
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
      if (!notification.android) {
        notification.android = {};
      }
      notification.android.channelId = channelId;

      const date = new Date(Date.now());
      date.setSeconds(date.getSeconds() + 15);
      const trigger: TimestampTrigger = {
        type: 0,
        timestamp: date.getTime(),
        alarmManager: true,
        repeatFrequency: RepeatFrequency.HOURLY,
      };
      Notifee.createTriggerNotification(notification, trigger)
        .then(notificationId => setId(notificationId))
        .catch(console.error);
    }
  }

  return (
    <SafeAreaView style={[styles.container]}>
      <ScrollView style={[styles.container]}>
        <View>
          <Button
            title="Request Permission"
            onPress={async (): Promise<void> => {
              console.log(await Notifee.requestPermission());
            }}
          />
          <Button
            title={'get notification settings'}
            onPress={async (): Promise<void> => {
              const notificationSettings = await Notifee.getNotificationSettings();
              console.log('notifications Settings : ', JSON.stringify(notificationSettings));
              Alert.alert(
                'Notification Settings',
                JSON.stringify(notificationSettings),
                [
                  {
                    text: 'Cancel',
                    onPress: () => {
                      //no-Op
                    },
                    style: 'cancel',
                  },
                ],
                {
                  cancelable: true,
                },
              );
            }}
          />
          <Button
            title={'get delivered notifications'}
            onPress={async (): Promise<void> => {
              const displayedNotifications = await Notifee.getDisplayedNotifications();
              console.log('notifications: ', displayedNotifications?.[0]?.notification?.android);
            }}
          />
          <Button
            title={'get trigger notifications'}
            onPress={async (): Promise<void> => {
              const triggerNotifications = await Notifee.getTriggerNotifications();
              console.log('trigger notifications: ', triggerNotifications);
            }}
          />
          <Button
            title={'get power manager info'}
            onPress={async (): Promise<void> => {
              console.log(await Notifee.getPowerManagerInfo());
            }}
          />
          <Button
            title={'open power manager '}
            onPress={async (): Promise<void> => {
              console.log(await Notifee.openPowerManagerSettings());
            }}
          />
          <Button
            title={'open alarm special access settings'}
            onPress={async (): Promise<void> => {
              console.log(await Notifee.openAlarmPermissionSettings());
            }}
          />
          {id != null && (
            <>
              <Button
                title={`Cancel ${id}`}
                onPress={(): void => {
                  Notifee.cancelNotification(id, 'example-tag');
                }}
              />
              <Button
                title={`Cancel trigger ${id}`}
                onPress={(): void => {
                  if (id != null) {
                    Notifee.cancelTriggerNotification(id);
                  }
                }}
              />
              <Button
                title={`Cancel displayed ${id}`}
                onPress={async () => {
                  if (id != null) {
                    await Notifee.cancelDisplayedNotification(id);
                  }
                }}
              />
              <Button
                title={'Cancel all '}
                onPress={async () => {
                  await Notifee.cancelDisplayedNotifications([id]);
                }}
              />
            </>
          )}
          <Button
            title={'get channels'}
            onPress={async (): Promise<void> => {
              const buttonChannels = await Notifee.getChannels();
              buttonChannels.forEach(res => {
                if (res.id === 'custom-vibrations') {
                  console.log('res', res);
                }
              });
            }}
          />
          <Button
            title={'get channels'}
            onPress={async (): Promise<void> => {
              const triggerNotifications = await Notifee.getTriggerNotifications();
              console.log('triggerNotifications', triggerNotifications);
            }}
          />
        </View>
        {notifications.map(({ key, notification }): any => (
          <View key={key} style={styles.rowItem}>
            <Text style={styles.header}>{key}</Text>
            <View style={styles.row}>
              {channels.map(channel => (
                <View key={channel.id + key} style={styles.rowItem}>
                  <Button
                    title={'>.'}
                    onPress={(): any => displayNotification(notification, channel.id)}
                    color={colors[channel.id]}
                  />
                </View>
              ))}
            </View>
          </View>
        ))}
      </ScrollView>
    </SafeAreaView>
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
    case EventType.TRIGGER_NOTIFICATION_CREATED:
      eventTypeString = 'TRIGGER_NOTIFICATION_CREATED';
      console.log('Trigger Notification');
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

      if (type !== EventType.ACTION_PRESS) {
        return;
      }
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
  useEffect(() => {
    (async () => {
      const initialNotification = await Notifee.getInitialNotification();
      console.log('TestComponent initialNotification', initialNotification);
    })();
  }, []);
  return (
    // eslint-disable-next-line react-native/no-inline-styles
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>Test Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('test_component', () => TestComponent);

function FullScreenComponent(): any {
  return (
    // eslint-disable-next-line react-native/no-inline-styles
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>FullScreen Component</Text>
    </View>
  );
}

AppRegistry.registerComponent('full_screen', () => FullScreenComponent);

export default Root;

const appName = "Notifee";

AppRegistry.registerComponent(appName, () => Root);
AppRegistry.runApplication(appName, {
  // Mount the react-native app in the "root" div of index.html
  rootTag: document.getElementById("root"),
});
