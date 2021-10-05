import { expect } from 'chai';
import { TestScope } from 'cavy';
import notifee, {
  AndroidChannel,
  EventType,
  Event,
  AndroidImportance,
  TimestampTrigger,
  TriggerType,
  RepeatFrequency,
} from '@notifee/react-native';
import { Platform } from 'react-native';

export function NotificationSpec(spec: TestScope): void {
  spec.beforeEach(async () => {
    const channels: AndroidChannel[] = [
      {
        name: 'High Importance',
        id: 'high',
        importance: AndroidImportance.HIGH,
      },
      {
        name: 'New 🐴 Sound',
        id: 'new_custom_sound',
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

    await notifee.createChannels(channels);
    // TODO doesn't work in tests as blocks with a UI prompt
    await notifee.requestPermission();
    // Clear notifications between tests
    await notifee.cancelAllNotifications();
  });

  spec.describe('displayNotification', function () {
    spec.it('configures custom sounds correctly', async function () {
      const customSoundChannel = await notifee.getChannel('new_custom_sound');
      console.warn('customSoundChannel looks like: ' + JSON.stringify(customSoundChannel));
      if (Platform.OS === 'android') {
        expect(customSoundChannel.soundURI).contains('horse.mp3');
        expect(customSoundChannel.sound).equals('horse.mp3');
      }
    });

    spec.it('displays a notification', async function () {
      return new Promise(async resolve => {
        const unsubscribe = notifee.onForegroundEvent((event: Event) => {
          if (event.type === EventType.DELIVERED) {
            expect(event.detail.notification).not.equal(undefined);
            if (event.detail.notification) {
              expect(event.detail.notification.title).equals('Hello');
              expect(event.detail.notification.body).equals('World');
            }
            unsubscribe();
            resolve();
          }
        });

        return notifee.displayNotification({
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
        });
      });
    });

    spec.it('displays a empty notification', async function () {
      return new Promise(async resolve => {
        return notifee
          .displayNotification({
            title: undefined,
            body: undefined,
            android: {
              channelId: 'high',
            },
          })
          .then(id => {
            expect(id).equals(id);
            resolve();
          });
      });
    });

    spec.it('displays a empty notification', async function () {
      return new Promise(async resolve => {
        return notifee
          .displayNotification({
            title: '',
            body: '',
            android: {
              channelId: 'high',
            },
          })
          .then(id => {
            expect(id).equals(id);
            resolve();
          });
      });
    });

    spec.describe('displayNotification with pressAction', function () {
      spec.it('displays a notification with a pressAction with id `default`', async function () {
        return new Promise(async resolve => {
          return notifee
            .displayNotification({
              title: '',
              body: '',
              android: {
                channelId: 'high',
                pressAction: {
                  id: 'default',
                },
              },
            })
            .then(id => {
              expect(id).equals(id);
              resolve();
            });
        });
      });

      spec.it('silently fails if `launchActivity` does not exist', async function () {
        return new Promise(async resolve => {
          return notifee
            .displayNotification({
              title: '',
              body: '',
              android: {
                channelId: 'high',
                pressAction: {
                  id: 'default',
                  launchActivity: 'com.app.invalid',
                },
              },
            })
            .then(id => {
              expect(id).equals(id);
              resolve();
            });
        });
      });
    });

    spec.describe('displayNotification with quick actions', function () {
      spec.it(
        'displays a notification with a quick action with input set to true',
        async function () {
          return new Promise(async resolve => {
            return notifee
              .displayNotification({
                title: '',
                body: '',
                android: {
                  channelId: 'high',
                  actions: [
                    {
                      title: 'First Action',
                      pressAction: {
                        id: 'first_action',
                      },
                      input: true,
                    },
                  ],
                },
              })
              .then(id => {
                expect(id).equals(id);
                resolve();
              });
          });
        },
      );
    });
  });

  spec.describe('createTriggerNotification', function () {
    spec.describe('timestampTrigger', function () {
      spec.describe('alarmManager', function () {
        spec.it('not repeating', async function () {
          return new Promise(async resolve => {
            const timestamp = new Date(Date.now());
            timestamp.setSeconds(timestamp.getSeconds() + 1);
            const trigger: TimestampTrigger = {
              type: TriggerType.TIMESTAMP,
              timestamp: timestamp.getTime(),
              alarmManager: true,
            };

            const notification = {
              id: `alarm-manger-${timestamp.getTime()}`,
              title: 'AlarmManager',
              body: 'Not Repeating',
              android: {
                channelId: 'high',
              },
            };

            const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
              if (event.type === EventType.DELIVERED) {
                expect(event.detail.notification).not.equal(undefined);
                if (event.detail.notification) {
                  expect(event.detail.notification.title).equals(notification.title);
                  expect(event.detail.notification.body).equals(notification.body);
                }

                // Check next trigger has been set
                const triggerNotificationIds = await notifee.getTriggerNotificationIds();
                expect(triggerNotificationIds.length).equals(0);

                unsubscribe();
                resolve();
              }
            });

            await notifee.createTriggerNotification(notification, trigger);
          });
        });

        spec.it('repeating', async function () {
          return new Promise(async resolve => {
            const timestamp = new Date(Date.now());
            timestamp.setSeconds(timestamp.getSeconds() + 1);
            const trigger: TimestampTrigger = {
              type: TriggerType.TIMESTAMP,
              timestamp: timestamp.getTime(),
              alarmManager: true,
              repeatFrequency: RepeatFrequency.HOURLY,
            };

            const notification = {
              id: `alarm-manger-repeating-${timestamp.getTime()}`,
              title: 'AlarmManager',
              body: 'Repeating',
              android: {
                channelId: 'high',
              },
            };

            const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
              if (event.type === EventType.DELIVERED) {
                expect(event.detail.notification).not.equal(undefined);
                if (event.detail.notification) {
                  expect(event.detail.notification.title).equals(notification.title);
                  expect(event.detail.notification.body).equals(notification.body);
                }

                // Check next trigger has been set
                const triggerNotificationIds = await notifee.getTriggerNotificationIds();

                expect(triggerNotificationIds.length).equals(1);
                expect(triggerNotificationIds.includes(notification.id)).equals(true);

                await notifee.cancelTriggerNotifications();

                unsubscribe();
                resolve();
              }
            });

            return notifee.createTriggerNotification(notification, trigger);
          });
        });
      });
    });
  });
}
