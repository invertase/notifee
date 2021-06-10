import { expect } from 'chai';
import { TestScope } from 'cavy';
import notifee, {
  AndroidChannel,
  EventType,
  Event,
  AndroidImportance,
} from '@notifee/react-native';

export function NotificationSpec(spec: TestScope): void {
  spec.beforeEach(async () => {
    const channels: AndroidChannel[] = [
      {
        name: 'High Importance',
        id: 'high',
        importance: AndroidImportance.HIGH,
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

    await notifee.createChannels(channels);
    // TODO doesn't work in tests as blocks with a UI prompt
    await notifee.requestPermission();
  });

  spec.describe('displayNotification', function () {
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
}
