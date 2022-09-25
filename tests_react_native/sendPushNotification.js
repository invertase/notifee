var admin = require('firebase-admin')

var serviceAccount = require('./notifee-admin.json')

// TODO: put as env
const TOKEN = ''

var payload = {
  android: {
    priority: 'high',
  },
  notification: {
    body: 'A notification body',
    title: 'A notification title!',
  },
  apns: {
    payload: {
      aps: {
        sound: 'default',
        category: 'communications',
        mutableContent: 1,
        contentAvailable: 1,
      },
      notifee_options: {
        image: 'https://placeimg.com/640/480/any', // URL to pointing to a remote image
        ios: {
          communicationInfo: {
            conversationId: 'id-abcde',
            sender: {
              id: 'senderId',
              avatar: 'https://pbs.twimg.com/profile_images/1070077650713133056/oji2RT4i_normal.jpg',
              displayName: 'Helena Ford',
            },
          },
        },
      },
    },
  },
  token: TOKEN,
}
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
})
admin
  .messaging()
  .send(payload)
  .then((response) => {
    console.log('Successfully sent message:', response)
  })
  .catch((error) => {
    console.log('Error sending message:', error)
  })
  .finally(() => process.exit())
