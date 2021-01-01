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

## Supporting Multiple Environments

On Android, if you have multiple license keys, one per product flavor and/or build type, you can structure your `notifee.config.json` like below:

```json
{
  "android": {
    "debug": {
      "license": "debug.license"
    },
    "staging": {
       "license": "staging.license"
    },
     "production": {
       "license": "production.license"
    },
    "full": {
      "debug": {
        "license": "full.debug.license"
      },
      "license": "full.license"
    },
    "license": "android.license"
  },
  "ios": {...}
}
```

If a license key isn't found for a product flavour or build type, it will fallback to the license key in the next level up. For example, a full debug build will fallback to `full.license` if `full.debug.license` wasn't specified. If there was no license specified for `full.license`, it will fallback to `android.license`.

## Debugging

### Android

Notifee will output logs during the license verification process to help determine if your config is setup correctly and if your license is valid.

A positive debug message is printed if the verification is successful, or an error message will be printed if unsuccessful.

Example log output when license verification succeeds :
```bash
D/NOTIFEE: (License): Local verification started.
D/NOTIFEE: (License): Local verification succeeded for your application with package name com.app.dev and license key ending in ATnPwEZoN2
```

Example log output when license verification fails:
- `notifee.config.json` missing:
```bash
D/NOTIFEE: (License): Local verification started.
E/NOTIFEE: (License): No license was found. Please ensure you have a created a 'notifee.config.json' file at the root of your project with a valid license key.
```

- License key is invalid:
```bash
D/NOTIFEE: (License): Local verification started.
E/NOTIFEE: (License): Your license key ending in ATnPwEZoWe is invalid. Please ensure you have a valid license key.
```
- License key is for the wrong application:
```bash
D/NOTIFEE: (License): Local verification started.
E/NOTIFEE: (License): Your license key ending in ATnPwEZoWe is not for this application, expected application to be com.notifee.app.dev but found com.notifee.app.staging
```

- License key is for the wrong platform:
```bash
D/NOTIFEE: (License): Local verification started.
E/NOTIFEE: (License): Your license key ending in ATnPwEZoP1 is not for this platform (Android):
```

Helpful Debugging Commands:
 - To turn on Android debug logs during release mode:

`adb shell setprop log.tag.NOTIFEE DEBUG`
- To quickly view Android logs in the terminal:

`adb logcat '*:S' NOTIFEE:D`
