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
  AndroidStyle,
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
      return new Promise(async (resolve, reject) => {
        const unsubscribe = notifee.onForegroundEvent((event: Event) => {
          try {
            if (event.type === EventType.DELIVERED) {
              expect(event.detail.notification).not.equal(undefined);
              if (event.detail.notification) {
                expect(event.detail.notification.title).equals('Hello');
                expect(event.detail.notification.body).equals('World');
              }
              unsubscribe();
              resolve();
            }
          } catch (e) {
            unsubscribe();
            reject(e);
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
      return new Promise(async (resolve, reject) => {
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
          })
          .catch(e => {
            reject(e);
          });
      });
    });

    spec.it(
      'displays a notification with AndroidStyle.BIGPICTURE and largeIcon as null',
      async function () {
        const testId = 'test-id';
        const testLargeIcon = 'test-large-icon';
        const testBigPicture = 'test-picture';

        if (Platform.OS === 'ios') {
          return;
        }

        return new Promise(async (resolve, reject) => {
          const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
            try {
              expect(event.type).equals(EventType.DELIVERED);
              expect(event.detail.notification?.id).equals(testId);

              // Check largeIcon is null
              const displayNotifications = await notifee.getDisplayedNotifications();
              const displayNotification = displayNotifications?.[0];
              expect(displayNotification?.id).equals(testId);
              expect(displayNotification.notification.android.largeIcon).equals(testLargeIcon);
              expect(displayNotification.notification.android.style.type).equals(
                AndroidStyle.BIGPICTURE,
              );

              if (displayNotification.notification.android.style.type === AndroidStyle.BIGPICTURE) {
                expect(displayNotification.notification.android.style.picture).equals(
                  testBigPicture,
                );

                expect(displayNotification.notification.android.style.largeIcon).null;
              }

              unsubscribe();
              resolve();
            } catch (e) {
              unsubscribe();
              reject(e);
            }
          });

          await notifee
            .displayNotification({
              id: testId,
              title: '',
              body: '',
              android: {
                channelId: 'high',
                largeIcon: testLargeIcon,
                style: {
                  type: AndroidStyle.BIGPICTURE,
                  largeIcon: null,
                  picture: testBigPicture,
                },
              },
            })
            .then(id => {
              expect(id).equals(id);
            })
            .catch(e => {
              reject(e);
            });
        });
      },
    );

    spec.describe('displayNotification with pressAction', function () {
      spec.it('displays a notification with a pressAction with id `default`', async function () {
        return new Promise(async (resolve, reject) => {
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
            })
            .catch(e => {
              reject(e);
            });
        });
      });

      spec.it('silently fails if `launchActivity` does not exist', async function () {
        return new Promise(async (resolve, reject) => {
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
            })
            .catch(e => {
              reject(e);
            });
        });
      });
    });

    spec.describe('displayNotification with quick actions', function () {
      spec.it(
        'displays a notification with a quick action with input set to true',
        async function () {
          return new Promise(async (resolve, reject) => {
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
              })
              .catch(e => {
                reject(e);
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
          // FIXME on iOS this has notification parts missing, see #191
          if (Platform.OS === 'ios') {
            return;
          }

          return new Promise(async (resolve, reject) => {
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

            let receivedCreatedEvent = false;

            const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
              try {
                // We get a trigger created event first on android...
                if (Platform.OS === 'android' && !receivedCreatedEvent) {
                  expect(event.type).equals(EventType.TRIGGER_NOTIFICATION_CREATED);
                  receivedCreatedEvent = true;
                  return;
                }

                // ...then we get the trigger
                expect(event.type).equals(EventType.DELIVERED);
                expect(event.detail.notification).not.equal(undefined);
                if (event.detail.notification) {
                  expect(event.detail.notification.title).equals(notification.title);
                  expect(event.detail.notification.body).equals(notification.body);
                }

                // Check next trigger has not been set
                const triggerNotificationIds = await notifee.getTriggerNotificationIds();
                expect(triggerNotificationIds.length).equals(0);
                unsubscribe();
                resolve();
              } catch (e) {
                unsubscribe();
                reject(e);
              }
            });

            await notifee.createTriggerNotification(notification, trigger);
          });
        });

        spec.it('repeating', async function () {
          // FIXME on iOS this has notification parts missing, see #191
          if (Platform.OS === 'ios') {
            return;
          }

          return new Promise(async (resolve, reject) => {
            const timestamp = new Date(Date.now());
            timestamp.setSeconds(timestamp.getSeconds() + 1);
            const trigger: TimestampTrigger = {
              type: TriggerType.TIMESTAMP,
              timestamp: timestamp.getTime(),
              alarmManager: true,
              repeatFrequency: RepeatFrequency.HOURLY,
            };

            const notification = {
              id: `alarm-manager-repeating-${timestamp.getTime()}`,
              title: 'AlarmManager',
              body: 'Repeating',
              android: {
                channelId: 'high',
              },
            };

            let receivedCreatedEvent = false;
            let receivedTriggerEvent = false;

            const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
              try {
                // We get a trigger created event first on android...
                if (Platform.OS === 'android' && !receivedCreatedEvent) {
                  expect(event.type).equals(EventType.TRIGGER_NOTIFICATION_CREATED);
                  receivedCreatedEvent = true;
                  return;
                }

                // ...then we get the trigger
                expect(event.type).equals(EventType.DELIVERED);
                expect(event.detail.notification).not.equal(undefined);
                if (event.detail.notification) {
                  expect(event.detail.notification.title).equals(notification.title);
                  expect(event.detail.notification.body).equals(notification.body);
                }
                receivedTriggerEvent = true;

                // Check next trigger has been set
                const triggerNotificationIds = await notifee.getTriggerNotificationIds();

                expect(triggerNotificationIds.length).equals(1);
                expect(triggerNotificationIds.includes(notification.id)).equals(true);
                unsubscribe();
                await notifee.cancelTriggerNotifications();
                resolve();
              } catch (e) {
                unsubscribe();
                await notifee.cancelTriggerNotifications();
                reject(e);
              }
            });

            notifee.createTriggerNotification(notification, trigger);

            // Make sure we receive the trigger within a reasonable time
            setTimeout(async () => {
              try {
                expect(receivedTriggerEvent).equals(true);
                resolve();
              } catch (e) {
                reject(new Error('Did not receive trigger in a reasonable amount of time'));
              } finally {
                unsubscribe();
                notifee.cancelTriggerNotifications();
              }
            }, 600000);
          });
        });
      });
    });
  });
}
