<p align="center">
  <a href="https://notifee.app">
    <img width="160px" src="https://notifee.app/logo-icon.png"><br/>
  </a>
  <h2 align="center">Notifee - React Native</h2>
</p>

---

A feature rich Android & iOS notifications library for React Native.

[> Learn More](https://notifee.app/)

## Installation

```bash
yarn add @notifee/react-native
```

## Documentation

- [Overview](https://notifee.app/react-native/docs/overview)
- [Licensing](https://notifee.app/react-native/docs/license-keys)
- [Reference](https://notifee.app/react-native/reference)

### Android

The APIs for Android allow for creating rich, styled and highly interactive notifications. Below you'll find guides that cover the supported Android features.

| Topic                                                                                    |                                                                                                                                   |
| ---------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| [Appearance](https://notifee.app/react-native/docs/android/appearance)                   | Change the appearance of a notification; icons, colors, visibility etc.                                                           |
| [Behaviour](https://notifee.app/react-native/docs/android/behaviour)                     | Customize how a notification behaves when it is delivered to a device; sound, vibration, lights etc.                              |
| [Channels & Groups](https://notifee.app/react-native/docs/android/channels)              | Organize your notifications into channels & groups to allow users to control how notifications are handled on their device        |
| [Foreground Service](https://notifee.app/react-native/docs/android/foreground-service)   | Long running background tasks can take advantage of a Android Foreground Services to display an on-going, prominent notification. |
| [Grouping & Sorting](https://notifee.app/react-native/docs/android/grouping-and-sorting) | Group and sort related notifications in a single notification pane.                                                               |
| [Interaction](https://notifee.app/react-native/docs/android/interaction)                 | Allow users to interact with your application directly from the notification with actions.                                        |
| [Progress Indicators](https://notifee.app/react-native/docs/android/progress-indicators) | Show users a progress indicator of an on-going background task, and learn how to keep it updated.                                 |
| [Styles](https://notifee.app/react-native/docs/android/styles)                           | Style notifications to show richer content, such as expandable images/text, or message conversations.                             |
| [Timers](https://notifee.app/react-native/docs/android/timers)                           | Display counting timers on your notification, useful for on-going tasks such as a phone call, or event time remaining.            |

### iOS

Below you'll find guides that cover the supported iOS features.

| Topic                                                             |                                                                          |
| ----------------------------------------------------------------- | ------------------------------------------------------------------------ |
| [Appearance](https://notifee.app/react-native/docs/ios/appearance)           | Change now the notification is displayed to your users.       |
| [Behaviour](https://notifee.app/react-native/docs/ios/behaviour)            | Control how notifications behave when they are displayed to a device; sound, crtitial alerts etc.  |
| [Categories](https://notifee.app/react-native/docs/ios/categories) | Create & assign categories to notifications.          |
| [Interaction](https://notifee.app/react-native/docs/ios/interaction)                 | Handle user interaction with your notifications. |                                                    |
| [Permissions](https://notifee.app/react-native/docs/ios/permissions)                 | Request permission from your application users to display notifications. |                                                    |

### Jest Testing

To run jest tests after integrating this module, you will need to mock out the native parts of Notifee or you will get an error that looks like:

```
 ● Test suite failed to run

    Notifee native module not found.

      59 |     this._nativeModule = NativeModules[this._moduleConfig.nativeModuleName];
      60 |     if (this._nativeModule == null) {
    > 61 |       throw new Error('Notifee native module not found.');
         |             ^
      62 |     }
      63 |
      64 |     return this._nativeModule;
```

We distribute a generic mock file you may use so that your jest tests will work successfully.

In your Jest config file, add an entry to the `setupFilesAfterEnv` array, perhaps like so if you use the `jest.config.js` configuration style:

```javascript
module.exports = {
  // ... other directives
  setupFilesAfterEnv: [
    'node_modules/@notifee/react-native/jest-mock.js'
  ],
  // ... other directives
}
```

## License

- See [LICENSE](/LICENSE)

---

<p>
  <img align="left" width="50px" src="https://static.invertase.io/assets/invertase-logo-small.png">
  <p align="left">
    Built and maintained with 💛 by <a href="https://invertase.io">Invertase</a>.
  </p>
</p>

---
