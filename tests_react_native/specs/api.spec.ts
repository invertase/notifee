/* eslint-disable jest/valid-expect */
import { expect } from 'chai';
import { TestScope } from 'cavy';
import notifee, {
  AndroidChannel,
  EventType,
  Event,
  AndroidImportance,
  TriggerType,
  Notification,
  TimestampTrigger,
} from '@notifee/react-native';
import { Platform } from 'react-native';

export function ApiSpec(spec: TestScope): void {
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
    // Clear notifications between tests
    await notifee.cancelAllNotifications();
  });

  spec.describe('Get Notifications', function () {
    spec.it('get displayed notifications', async function () {
      const payload: Notification = {
        id: 'hello',
        title: 'Displayed',
        body: 'Notifications',
        android: {
          channelId: 'high',
        },
        ios: {
          badgeCount: 1,
        },
      };

      await notifee.displayNotification(payload);
      const date = new Date(Date.now());
      date.setSeconds(date.getSeconds() + 10);
      await notifee.createTriggerNotification(
        {
          id: 'trigger',
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
          ios: {
            threadId: 'group',
          },
        },
        {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
        },
      );

      const notifications = await notifee.getDisplayedNotifications();

      expect(notifications.length).equals(1);
      const notification = notifications[0];

      expect(notification.date).is.not.null;

      expect(notification.id).is.not.null;
      expect(notification.notification.title).equals(payload.title);
      expect(notification.notification.subtitle).equals(payload.subtitle);

      expect(notification.notification.body).equals(payload.body);
      if (Platform.OS === 'android')
        expect(notification.notification.android?.channelId).equals(payload.android?.channelId);

      if (Platform.OS === 'ios')
        expect(notification.notification.ios?.threadId).equals(payload.ios?.threadId);
    });

    spec.it('get trigger notifications', async function () {
      const payload: Notification = {
        id: 'hello',
        title: 'Hello',
        body: 'World',
        android: {
          channelId: 'high',
        },
        ios: {
          badgeCount: 1,
        },
      };

      await notifee.displayNotification(payload);
      const date = new Date(Date.now());
      date.setSeconds(date.getSeconds() + 10);
      const timestamp = date.getTime();
      await notifee.createTriggerNotification(
        {
          id: 'trigger',
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
        },
        {
          type: TriggerType.TIMESTAMP,
          timestamp,
        },
      );

      const triggerNotifications = await notifee.getTriggerNotifications();
      expect(triggerNotifications.length).equals(1);

      const triggerNotification = triggerNotifications[0];
      expect(triggerNotification.notification.title).equals(payload.title);

      expect(triggerNotification.trigger).to.not.be.null;

      const timestampTrigger = triggerNotification.trigger as TimestampTrigger;
      expect(timestampTrigger.type).equals(TriggerType.TIMESTAMP);
      expect(timestampTrigger.timestamp).equals(timestamp);
      expect(timestampTrigger.repeatFrequency).equals(-1);
      expect(timestampTrigger.alarmManager).equals(undefined);
    });
  });

  spec.describe('Cancel Notifications', function () {
    spec.it('cancels all notifications and resets badge count to 0', async function () {
      return new Promise(async (resolve, reject) => {
        const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
          console.warn('We are checking: ' + JSON.stringify(event));
          try {
            if (event.type === EventType.DELIVERED) {
              if (event.detail?.notification?.id !== 'on-foreground') {
                // skip
                return;
              }

              expect(event.detail.notification).not.equal(undefined);
              if (event.detail.notification) {
                expect(event.detail.notification.title).equals('Hello');
                expect(event.detail.notification.body).equals('World');
              }

              // Only check badge count for ios
              if (Platform.OS === 'ios') {
                const initialBadgeCount = await notifee.getBadgeCount();
                expect(initialBadgeCount).equals(1);

                await notifee.cancelAllNotifications();
                await notifee.setBadgeCount(0);

                const lastBadgeCount = await notifee.getBadgeCount();
                expect(lastBadgeCount).equals(0);
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
          id: 'on-foreground',
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
          ios: {
            badgeCount: 1,
          },
        });
      });
    });

    spec.it('cancels display notifications by id', async function () {
      return new Promise(async (resolve, reject) => {
        await notifee.displayNotification({
          id: 'hello',
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
          ios: {
            badgeCount: 1,
          },
        });

        // Before
        let notifications = await notifee.getDisplayedNotifications();
        expect(notifications?.length).equals(1);

        // Test;
        await notifee.cancelDisplayedNotifications(['hello']);

        // TODO: find out why there needs to be a delay on Android
        setTimeout(async () => {
          try {
            // After
            notifications = await notifee.getDisplayedNotifications();
            expect(notifications?.length).equals(0);
            resolve();
          } catch (e) {
            reject('Cancelled notification still present even after a delay.');
          }
        }, 5000);
      });
    });

    spec.it('cancels trigger notifications by id', async function () {
      const date = new Date(Date.now());
      date.setSeconds(date.getSeconds() + 10);
      await notifee.createTriggerNotification(
        {
          id: 'trigger',
          title: 'Hello',
          body: 'World',
          android: {
            channelId: 'high',
          },
          ios: {
            threadId: 'group',
          },
        },
        {
          type: TriggerType.TIMESTAMP,
          timestamp: date.getTime(),
        },
      );

      // Before
      let notifications = await notifee.getTriggerNotificationIds();
      expect(notifications.length).equals(1);

      // // Test

      await notifee.cancelTriggerNotifications(['trigger']);

      // // After
      notifications = await notifee.getTriggerNotificationIds();
      expect(notifications.length).equals(0);
    });
  });

  spec.describe('Channels', function () {
    spec.it('isChannelBlocked() returns correct response', async function () {
      // TODO: figure out how to test when channel is blocked
      expect(await notifee.isChannelBlocked('high')).to.be.false;

      //Should return false if channel does not exist
      expect(await notifee.isChannelBlocked('unknown channel')).to.be.false;
    });

    spec.it('isChannelCreated() returns correct response', async function () {
      if (Platform.OS === 'ios') {
        expect(await notifee.isChannelCreated('high')).to.be.true;
        return;
      }

      expect(await notifee.isChannelCreated('high')).to.be.true;

      // Should return false if channel does not exist
      expect(await notifee.isChannelCreated('unknown channel')).to.be.false;
    });
  });

  spec.describe('Permissions', function () {
    spec.it('requestPermissions() returns correct response', async function () {
      if (Platform.OS === 'ios') {
        // Skip, unable to test on iOS simulator
        return;
      }

      const settings = await notifee.requestPermission();

      expect(settings.authorizationStatus).equals(1);
      expect(settings.ios.authorizationStatus).equals(1);
    });

    spec.it('getNotificationSettings() returns correct response', async function () {
      if (Platform.OS === 'ios') {
        // Skip, unable to test on iOS simulator
        return;
      }

      const settings = await notifee.getNotificationSettings();

      expect(settings.authorizationStatus).equals(1);
      expect(settings.ios.authorizationStatus).equals(1);
    });
  });
}
