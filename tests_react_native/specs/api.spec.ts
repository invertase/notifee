import { expect } from 'chai';
import { TestScope } from 'cavy';
import notifee, {
  AndroidChannel,
  EventType,
  Event,
  AndroidImportance,
  TriggerType,
  Notification,
} from '@notifee/react-native';

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
      const date = Date.now();
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
          timestamp: new Date(date).getTime(),
        },
      );

      const notifications = await notifee.getDisplayedNotifications();

      expect(notifications.length).equals(1);
      console.log('notifications', notifications);

      const notification = notifications[0];
      expect(notification.date);
      expect(notification.id).equals(payload.id);
      expect(notification.notification).equals(payload);
      expect(notification.trigger).to.be.null;
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
      const date = Date.now();
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
          timestamp: new Date(date).getTime(),
        },
      );

      const triggerNotifications = await notifee.getTriggerNotifications();

      expect(triggerNotifications.length).equals(1);
      console.log('notifications', triggerNotifications);

      const triggerNotification = triggerNotifications[0];
      expect(triggerNotification.notification).equals(payload);
      expect(triggerNotification.trigger).to.be.null;
    });
  });

  spec.describe('Cancel Notifications', function () {
    spec.it('cancels all notifications and resets badge count to 0', async function () {
      return new Promise(async resolve => {
        const unsubscribe = notifee.onForegroundEvent(async (event: Event) => {
          if (event.type === EventType.DELIVERED) {
            expect(event.detail.notification).not.equal(undefined);
            if (event.detail.notification) {
              expect(event.detail.notification.title).equals('Hello');
              expect(event.detail.notification.body).equals('World');
            }

            const initialBadgeCount = await notifee.getBadgeCount();
            expect(initialBadgeCount).equals(1);

            await notifee.cancelAllNotifications();

            const lastBadgeCount = await notifee.getBadgeCount();
            expect(lastBadgeCount).equals(0);

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
          ios: {
            badgeCount: 1,
          },
        });
      });
    });

    spec.it('cancels all notifications by id', async function () {
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

      const date = Date.now();
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
          timestamp: new Date(date).getTime(),
        },
      );

      // Before
      let notifications = await notifee.getDisplayedNotifications();
      let triggerNotificationIds = await notifee.getTriggerNotificationIds();
      expect(notifications.length + triggerNotificationIds.length).equals(2);

      // Test
      await notifee.cancelAllNotifications(['hello', 'trigger']);

      // After
      notifications = await notifee.getDisplayedNotifications();
      triggerNotificationIds = await notifee.getTriggerNotificationIds();
      expect(notifications.length + triggerNotificationIds.length).equals(0);
    });

    spec.it('cancels display notifications by id', async function () {
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

      // Test
      await notifee.cancelDisplayedNotifications(['hello']);

      // After
      notifications = await notifee.getDisplayedNotifications();
      expect(notifications?.length).equals(0);
    });

    spec.it('cancels trigger notifications by id', async function () {
      const date = Date.now();
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
          timestamp: new Date(date).getTime(),
        },
      );

      // Before
      let notifications = await notifee.getTriggerNotificationIds();
      expect(notifications.length).equals(1);

      // Test
      await notifee.cancelTriggerNotifications(['trigger']);

      // After
      notifications = await notifee.getTriggerNotificationIds();
      expect(notifications.length).equals(0);
    });
  });

  spec.describe('Channels', function () {
    spec.it('isChannelBlocked() returns correct response', async function () {
      // TODO: figure out how to test when channel is blocked
      expect(await notifee.isChannelBlocked('high')).to.be.true;

      //Should return false if channel does not exist
      expect(await notifee.isChannelBlocked('unknown channel')).to.be.false;
    });

    spec.it('isChannelCreated() returns correct response', async function () {
      expect(await notifee.isChannelCreated('high')).to.be.true;

      //Should return false if channel does not exist
      expect(await notifee.isChannelCreated('unknown channel')).to.be.false;
    });
  });
}
