export default {
  'android-large-icon': {
    title: 'New message',
    body: 'A new message has been received from John Doe.',
    android: {
      channelId: 'high',
      number: 1,
      largeIcon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
    },
  },
  'android-badge': {
    title: 'New message',
    body: 'You have 1 new message.',
    android: {
      channelId: 'high',
      number: 1,
      largeIcon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
    },
  },
  'android-importance-high': {
    title: 'High Priority Message',
    body: 'A new high priority message has been sent!',
    android: {
      channelId: 'high',
    },
  },
  'android-importance-default': {
    title: 'New Message',
    body: 'A new message has been sent!',
    android: {
      channelId: 'default',
    },
  },
  'android-importance-low': {
    title: 'Low Importance Message',
    body: 'A new low importance message has been sent!',
    android: {
      channelId: 'low',
    },
  },
  'android-importance-min': {
    title: 'Minimum Importance Message',
    body: 'A new minimum importance message has been sent!',
    android: {
      channelId: 'min',
    },
  },
  'android-color': {
    title: 'A Red Notification',
    android: {
      channelId: 'default',
      color: 'red',
      visibility: -1,
      progress: {
        indeterminate: true,
      },
    },
  },
  'android-text-styling': {
    title: '<p style="color: #4caf50;"><b>Styled HTMLTitle</span></p></b></p> &#128576;',
    subtitle: '&#129395;',
    body: 'The <p style="text-decoration: line-through">body can</p> also be <p style="color: #ffffff; background-color: #9c27b0"><i>styled too</i></p> &#127881;!',
    android: {
      channelId: 'default',
      color: '#4caf50',
      actions: [
        {
          title: '<b>Dance</b> &#128111;',
          pressAction: { id: 'foo' },
        },
        {
          title: '<p style="color: #f44336;"><b>Cry</b> &#128557;</p>',
          pressAction: { id: 'foo' },
        },
      ],
    },
  },
  'android-ongoing': {
    title: 'An ongoing notification',
    android: {
      channelId: 'default',
      ongoing: true,
      progress: {
        indeterminate: true,
      },
    },
  },
  'android-actions-showcase': {
    id: 'android-actions-showcase',
    title: 'New message',
    body: 'You have a new message from Sarah Lane',
    subtitle: 'Sarah Lane',
    showTimestamp: true,
    android: {
      channelId: 'default',
      color: '#553C9A',
      largeIcon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
      style: {
        subtitle: 'Chat with Sarah',
        type: 3,
        person: {
          name: 'John Doe',
          icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/male.png',
        },
        messages: [
          {
            text: 'Hey, fancy food later?',
            timestamp: Date.now() - 60000,
          },
          {
            text: 'Sure, what time?',
            timestamp: Date.now() - 30000,
            person: {
              name: 'Sarah Lane',
              icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
            },
          },
        ],
      },
      actions: [
        {
          title: 'Reply',
          icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
          pressAction: {
            id: 'reply',
          },
          input: {
            allowFreeFormInput: true,
            choices: ['13:00', '13:30', '14:00'],
            editableChoices: true,
          },
        },
        {
          title: 'Mask as Read',
          icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
          pressAction: {
            id: 'mark',
          },
        },
      ],
    },
  },
  'android-style-bigpicture': {
    title: 'Photo successfully upload',
    body: 'Your photo named "Sunset in Paradise" was successfully uploaded.',
    subtitle: 'Upload',
    android: {
      channelId: 'default',
      color: '#2196f3',
      style: {
        type: 0,
        picture:
          'https://images.unsplash.com/photo-1460627390041-532a28402358?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80',
      },
      actions: [
        {
          title: 'Share',
          pressAction: {
            id: 'share',
          },
        },
        {
          title: 'Edit',
          pressAction: {
            id: 'edit',
          },
        },
      ],
    },
  },
  'android-style-bigtext': {
    title: 'Sarah Lane',
    body: 'Movie night',
    subtitle: 'Sarah Lane',
    android: {
      channelId: 'default',
      showTimestamp: true,
      timestamp: Date.now() - 480000,
      color: '#009688',
      largeIcon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
      style: {
        type: 1,
        title: 'Movie night',
        text: `Hey, do you have any plans for tonight? I was thinking a few of us could go watch a movie at the theater nearby since there won't be much going on for the next couple of weeks. There are some great options at 6 and 7pm, but whatever works best for you. If you have any suggestions for dinner beforehand hit reply!`,
      },
      actions: [
        {
          title: 'Reply',
          pressAction: {
            id: 'reply',
          },
        },
        {
          title: 'Archive',
          pressAction: {
            id: 'archive',
          },
        },
      ],
    },
  },
  'android-style-inbox': {
    subtitle: '7 Messages',
    title: '<b>Group Catchup</b> &#128588;',
    android: {
      channelId: 'default',
      showTimestamp: true,
      color: '#2196f3',
      style: {
        type: 2,
        summary: '+1 more',
        lines: [
          '<b>Jessica:</b>  Are you all available later for a catchup?',
          '<p style="color: #2196f3;"><b>You:</b></p>  Sorry I am out this evening &#128546;',
          '<b>Jessica:</b>  No problem, Dave.',
          '<b>Sarah:</b>  I am, what time are you thinking? The earlier the better for me!',
          '<b>John:</b>  Ditto, earlier is best too.',
          '<b>Jessica:</b>  Ok great! Lets go for 6pm downtown?',
          '<b>David:</b>  Enjoy &#128512;',
          '<b>Sarah:</b>  Awesome! &#128588;',
          '<b>John~</b>  See you there.',
        ],
      },
    },
  },
  'android-style-messaging': {
    title: 'Sarah Lane',
    body: 'Great thanks, food later?',
    subtitle: 'Chat with Sarah',
    android: {
      channelId: 'default',
      color: '#2196f3',
      actions: [
        {
          title: 'Reply',
          pressAction: {
            id: 'reply',
          },
        },
        {
          title: 'Archive',
          pressAction: {
            id: 'archive',
          },
        },
      ],
      style: {
        type: 3,
        person: {
          name: 'John Doe',
          icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/male.png',
        },
        messages: [
          {
            text: 'Hey, how are you?',
            timestamp: Date.now(), // Now
            person: {
              name: 'Sarah Lane',
              icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
            },
          },
          {
            text: `I'm great, how about you?`,
            timestamp: Date.now() - 600000, // 10 minutes ago
          },
          {
            text: 'Good thanks! Food later?',
            timestamp: Date.now(), // Now
            person: {
              name: 'Sarah Lane',
              icon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
            },
          },
        ],
      },
    },
  },
  'android-progress-summary': {
    title: 'Uploading',
    body: `Your image "Sunrise" is uploading...`,
    android: {
      channelId: 'default',
      color: '#2196f3',
      style: {
        type: 0,
        picture:
          'https://images.unsplash.com/photo-1500382017468-9049fed747ef?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1189&q=80',
      },
      progress: {
        indeterminate: true,
      },
    },
  },
  'android-progress-updating': {
    title: 'Uploading',
    body: `Your image is uploading...`,
    subtitle: '0%',
    android: {
      channelId: 'default',
      color: '#2196f3',
      onlyAlertOnce: true,
      progress: {
        max: 10,
        current: 0,
      },
    },
  },
  'android-group-summary': {
    title: 'Emails',
    subtitle: 'Emails',
    android: {
      channelId: 'default',
      color: '#2196f3',
      groupSummary: true,
      showTimestamp: true,
      group: '123',
    },
  },
  'android-group': {
    title: 'New Email',
    subtitle: 'Unread',
    body: 'Tap to open your email.',
    android: {
      channelId: 'default',
      largeIcon: 'https://www.stickpng.com/assets/images/584856bce0bb315b0f7675ad.png',
      color: '#2196f3',
      group: '123',
      actions: [
        {
          title: 'Reply',
          pressAction: { id: '123' },
        },
        {
          title: 'Mark as Read',
          pressAction: { id: '123' },
        },
      ],
    },
  },
  'android-timestamp': {
    title: 'Message from Sarah Lane',
    body: 'Tap to view your unread message from Sarah.',
    subtitle: 'Messages',
    android: {
      channelId: 'default',
      color: '#2196f3',
      largeIcon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/female.png',
      timestamp: Date.now() - 480000,
      showTimestamp: true,
    },
  },
  'android-chronometer': {
    title: '&#128222; Calling John Doe',
    body: 'Tap to view contact.',
    subtitle: 'Calls',
    android: {
      channelId: 'default',
      color: '#2196f3',
      largeIcon: 'https://storage.googleapis.com/static.invertase.io/assets/avatars/male.png',
      showChronometer: true,
      actions: [
        {
          title: 'Hang Up',
          pressAction: { id: '123' },
        },
      ],
    },
  },
  'android-chronometer-down': {
    title: '&#11088; Claim Your Prize &#11088;',
    body: 'Tap to claim your time limited prize! Hurry! &#10024;',
    subtitle: 'Prizes',
    android: {
      channelId: 'default',
      color: '#2196f3',
      showChronometer: true,
      chronometerDirection: 'down',
      timestamp: Date.now() + 300000,
    },
  },
};
