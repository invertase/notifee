/*
 * Copyright (c) 2016-present Invertase Limited
 */

describe('notifee', () => {
  it('calls cancelAllNotifications without throwing', () => {
    // todo
  });

  describe('cancelNotification()', () => {
    it('throws if notificationId is not a string', () => {
      try {
        notifee.cancelNotification(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        e.message.should.containEql("'notificationId' expected a string value");
        return Promise.resolve();
      }
    });

    it('cancels a notification', () => {
      // todo
    });
  });

  describe('createChannel()', () => {
    it('throws if channel is invalid', () => {
      try {
        notifee.createChannel(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        return Promise.resolve();
      }
    });

    it('creates a channel', () => {
      // todo
    });
  });

  describe('createChannels()', () => {
    it('throws if channels is not an array', () => {
      try {
        notifee.createChannels({});
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        e.message.should.containEql("'channels' expected an array of AndroidChannel");
        return Promise.resolve();
      }
    });

    it('throws if channels are invalid', () => {
      try {
        notifee.createChannels([{}]);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        e.message.should.containEql("'channels' a channel is invalid:");
        return Promise.resolve();
      }
    });

    it('creates a channel', () => {
      // todo
    });
  });

  describe('createChannelGroup()', () => {
    it('throws if channel group is invalid', () => {
      try {
        notifee.createChannelGroup(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        return Promise.resolve();
      }
    });

    it('creates a channel group', () => {
      // todo
    });
  });

  describe('createChannelGroups()', () => {
    it('throws if channel groups is not an array', () => {
      try {
        notifee.createChannelGroups({});
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        e.message.should.containEql("'channelGroups' expected an array of AndroidChannelGroup");
        return Promise.resolve();
      }
    });

    it('throws if channel groups are invalid', () => {
      try {
        notifee.createChannelGroups([{}]);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        e.message.should.containEql("'channelGroups' a channel group is invalid:");
        return Promise.resolve();
      }
    });

    it('creates channel groups', () => {
      // todo
    });
  });

  describe('deleteChannel', () => {
    it('throws if channel id is not a string', () => {
      try {
        notifee.deleteChannel(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        e.message.should.containEql("'channelId' expected a string value");
        return Promise.resolve();
      }
    });

    it('deletes a channel', () => {
      // todo
    });
  });

  describe('deleteChannelGroup', () => {
    it('throws if channel group id is not a string', () => {
      try {
        notifee.deleteChannelGroup(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        e.message.should.containEql("'channelGroupId' expected a string value");
        return Promise.resolve();
      }
    });

    it('deletes a channel group', () => {
      // todo
    });
  });

  describe('displayNotification()', () => {
    it('throws if notification is invalid', () => {
      try {
        notifee.displayNotification('foobar');
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // has own tests
        return Promise.resolve();
      }
    });

    it('displays a notification', () => {
      // todo
    });
  });

  xdescribe('getBadge()', () => {
    it('gets a value', async () => {
      await notifeeExports.setBadge(123);
      const value = await notifee.getBadge();
      value.should.eql(123);
    });
  });

  xdescribe('setBadge()', () => {
    it('sets a value', async () => {
      await notifeeExports.setBadge(234);
      const value = await notifee.getBadge();
      value.should.eql(243);
    });

    it('removes the badge', async () => {
      await notifeeExports.setBadge(null);
      const value = await notifee.getBadge();
      should.equal(value, null);
    });
  });

  describe('getInitialNotification()', () => {
    // todo
  });

  describe('getScheduledNotifications()', () => {
    // todo
  });

  describe('onNotificationDisplayed()', () => {
    it('throws if function not provided', () => {
      try {
        notifee.onNotificationDisplayed('foobar');
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        e.message.should.containEql("'observer' expected a function");
        return Promise.resolve();
      }
    });
  });

  describe('onNotificationOpened()', () => {
    it('throws if function not provided', () => {
      try {
        notifee.onNotificationOpened('foobar');
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        e.message.should.containEql("'observer' expected a function");
        return Promise.resolve();
      }
    });
  });

  describe('removeAllDeliveredNotifications()', () => {
    // todo
  });

  describe('removeDeliveredNotification()', () => {
    it('throws if id is invalid', () => {
      try {
        notifee.removeDeliveredNotification(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        e.message.should.containEql("'notificationId' expected a string value");
        return Promise.resolve();
      }
    });

    it('removes delivered notification', () => {
      // todo
    });
  });

  describe('scheduleNotification()', () => {
    it('throws if notification is invalid', () => {
      try {
        notifee.scheduleNotification(123);
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // own tests
        return Promise.resolve();
      }
    });

    it('throws if schedule is invalid', () => {
      try {
        notifee.scheduleNotification(
          {
            body: 'foobar',
          },
          'foo',
        );
        return Promise.reject(new Error('Did not throw Error'));
      } catch (e) {
        // own tests
        return Promise.resolve();
      }
    });

    it('schedules a notification', () => {
      // todo
    });
  });
});
