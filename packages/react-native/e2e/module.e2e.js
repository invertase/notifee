/*
 * Copyright (c) 2016-present Invertase Limited
 */

describe('notifee', () => {
  it.only('playground', async () => {
    // notifee.AndroidColor.RED.should.equal('red');

    // await Utils.sleep(2000);

    notifee.registerForegroundService(data => {
      return new Promise(() => {
        console.log('registerForegroundService', data)
      });
    });

    const channelId = await notifee.createChannel({
      id: 'test',
      name: 'Testing Channel!!!',
      importance: notifee.AndroidImportance.HIGH,
      badge: true,
    });

    console.log(channelId);

    const config = {
      title: 'Uploading image...',
      // subtitle: 'Barrrr',
      // body: 'Hello World ',
      data: {
        foo: 'bar',
        bar: '123',
      },
      android: {
        // actions: [
        //   {
        //     title: 'First',
        //     icon: 'https://invertase.io/icons/icon-48x48.png',
        //     contextual: true,
        //     allowGeneratedReplies: true,
        //     showsUserInterface: true,
        //     remoteInput: {
        //       choices: ['Foo', 'Bar'],
        //     },
        //   },
        //   {
        //     title: 'Middle',
        //     icon: 'https://invertase.io/icons/icon-48x48.png',
        //     contextual: true,
        //     allowGeneratedReplies: true,
        //     showsUserInterface: false,
        //   },
        //   {
        //     title: 'Last',
        //     icon: 'https://invertase.io/icons/icon-48x48.png',
        //     contextual: true,
        //     allowGeneratedReplies: true,
        //     showsUserInterface: true,
        //   },
        // ],
        asForegroundService: false,
        pressAction: false,
        fullScreenAction: {
          id: 'foo',
        },
        autoCancel: false,
        // largeIcon: 'https://invertase.io/icons/icon-48x48.png',
        badgeIconType: notifee.AndroidBadgeIconType.SMALL,
        channelId,
        // category: notifee.AndroidCategory.CALL,
        color: '#ff69b4',
        colorized: true,
        // bubble: {
        //   icon: 'https://static.invertase.io/assets/React-Native-Firebase.png',
        //   autoExpand: true,
        // },
        // number: 13,
        // ongoing: true,
        onlyAlertOnce: false,
        progress: {
          // max: 10,
          // current: 2,
          indeterminate: true,
        },
        shortcutId: '123',
        // showTimestamp: true,
        // style: {
        //   type: notifee.AndroidStyle.BIGTEXT,
        //   text: 'Foooo Foooo Foooo Foooo Foooo Foooo \n \n Foooo Foooo Foooo Foooo Foooo Foooo Foooo Foooo Foooo Foooo Foooo ',
        //   title: 'BigText',
        // },
        // style: {
        //   type: notifee.AndroidStyle.BIGPICTURE,
        //   picture:
        //     'https://images.unsplash.com/photo-1568526381923-caf3fd520382?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80',
        //   title: 'BigPicture',
        // },
        // style: {
        //   type: notifee.AndroidStyle.INBOX,
        //   // lines: [],
        //   lines: [
        //     'Hello Elliot',
        //     'Thank you for contacting us blah blah blah  blah blah blah  blah blah blah  blah blah blah',
        //     'Three',
        //     'Four',
        //     'Five',
        //     'Six',
        //     'Seven',
        //     'Eight',
        //     'Nine',
        //     'Ten',
        //     'Eleven',
        //   ],
        //   title: 'Google Fit - Fitness Tracking',
        // },
        // style: {
        //   type: notifee.AndroidStyle.MESSAGING,
        //   // title: 'Group Chat',
        //   group: true,
        //   person: {
        //     id: 'elliot',
        //     name: 'Elliot',
        //   },
        //   messages: [
        //     {
        //       text: 'Hey there',
        //       timestamp: Date.now() - 30000,
        //     },
        //     {
        //       text: 'Doopy do',
        //       timestamp: Date.now() - 20000,
        //       person: {
        //         id: 'bot',
        //         name: 'Mike',
        //         bot: true,
        //         icon: 'https://static.invertase.io/assets/React-Native-Firebase.png',
        //       },
        //     },
        //     {
        //       text: 'Dooby Do',
        //       timestamp: Date.now() - 20000,
        //       person: {
        //         id: 'bot2',
        //         name: 'Dave Bob',
        //         // bot: true,
        //         important: true,
        //         uri: 'content://com.android.contacts/contact/1',
        //       },
        //     },
        //     {
        //       text: 'Awks..',
        //       timestamp: Date.now() - 10000,
        //     },
        //   ],
        // },
        ticker: 'Elliot Smells',
        // timeoutAfter: 8000,
        chronometerDirection: 'down',
        // showChronometer: true,
        timestamp: Date.now() - 20000,
        tag: 'elliot',
      },
    };

    const id = await notifee.displayNotification(config);

    await Utils.sleep(5000);

    await notifee.cancelNotification(id);

    await Utils.sleep(900000);

    // await Utils.sleep(2000);

    // await notifee.createChannelGroup({
    //   id: '123',
    //   name: 'Testing Group',
    //   description: 'Group description',
    // });
    //
    // const id = await notifee.createChannel({
    //   id: 'testing1234',
    //   name: 'Testing Channel!!!',
    //   description: 'Foo bar baz',
    //   importance: notifee.AndroidImportance.LOW,
    //   visibility: notifee.AndroidVisibility.SECRET,
    //   badge: false,
    //   // groupId: '123',
    // });

    // await notifee.openNotificationSettings('testing1234234234');

    // console.log('GOOOOOOOOOO');
    // await Utils.sleep(15000);

    // const channel = await notifee.getChannel(id);
    // const group = await notifee.getChannelGroup('123');
    //
    // console.log(channel);

    // console.log('Channel ID', foo);
    //
    // const channels = await notifee.getChannels();
    //
    // console.log(channels);
    // const id = await notifee.displayNotification({
    //   title: 'Notifee',
    //   body: 'Hello World',
    //   android: {
    //     channelId: 'testing',
    //     // color: notifee.AndroidColor.GREEN,
    //     // colorized: true,
    //     // priority: notifee.AndroidPriority.MAX,
    //     // ongoing: true,
    //     // style: {
    //     //   type: notifee.AndroidStyle.BIGTEXT,
    //     //   text: 'Foooo',
    //     // }
    //     // showTimestamp: true,
    //     // // showChronometer: true,
    //     // timestamp: Date.now() - 240000,
    //   },
    // });
  });

  it('exports ts enums as js values', () => {
    notifee.AndroidColor.RED.should.equal('red');
  });

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
      await notifee.setBadge(123);
      const value = await notifee.getBadge();
      value.should.eql(123);
    });
  });

  xdescribe('setBadge()', () => {
    it('sets a value', async () => {
      await notifee.setBadge(234);
      const value = await notifee.getBadge();
      value.should.eql(243);
    });

    it('removes the badge', async () => {
      await notifee.setBadge(null);
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
