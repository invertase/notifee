---
title: License Keys
description: Release builds require a valid license key. Learn how to add yours to the project.
next: /react-native/docs/release-notes
previous: /react-native/docs/installation
---

Notifee is a licensed product and requires a valid license key to work when your application has been built
in release mode.

> Non-Profit organisations, free Open Source Applications & React Native Firebase core contributors can [request a free license](/contact#license)!

If an invalid license key is provided, your application will still continue to work as normal. However, all features provided by notifee
will not work.

Notifee will deem a license key to be invalid under the following scenarios:

- No key is provided in the `notifee.config.json` file (see below).
- [Android] The Application ID does not match the provided ID when generating the key.
- [iOS] The Bundle ID does not match the provided ID when generating the key.
- The key is no longer valid (e.g. key has been removed via the dashboard).

## Creating a key

To create a key, you need to have [purchased a license](/purchase) or be assigned to a team with a license.

Once a license has been purchased, navigate to the license page on your [account dashboard](/account/licenses). Here, you
are able to create two types of keys; Primary & Secondary. Choose the key type you wish to use with your application:

| Key Type       |                                                                                                                             |
| -------------- | --------------------------------------------------------------------------------------------------------------------------- |
| Primary Keys   | Used on production release build applications.                                                                              |
| Secondary Keys | Used on release build applications which are not intended for production use, for example, staging or testing applications. |

> When creating a key, ensure that the Application ID (Android) or Bundle ID (iOS) matches that of your application.

Secondary keys are subject to more frequent checks on their validation. They are designed for non-production applications
only and may unexpectedly be invalidated if used on high traffic applications.

## Using a key

To use a license key with your application, first create a file called `notifee.config.json` in the root of your project.
This file accepts keys for both Android & iOS. Copy and add the key to this file, for example:

```json
{
  "android": {
    "license": "XXXX.YYYY.ZZZZ"
  },
  "ios": {
    "license": "XXXX.YYYY.ZZZZ"
  }
}
```

Now rebuild your project, the keys will be validated when the application is launched:

```bash
# For iOS
npx react-native run-ios

# For Android
npx react-native run-android
```
