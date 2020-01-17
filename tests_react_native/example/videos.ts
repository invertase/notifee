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
};
