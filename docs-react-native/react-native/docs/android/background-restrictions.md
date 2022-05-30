---
title: Background Restrictions
description: Understand and manage background restrictions on Android devices.
next: /
previous: /
---

Android devices can restrict your app while it's in the background to preserve battery life. This can prevent trigger notifications from being displayed. 

There are numerous factors that can cause your app to be killed which varies between device manufacturers and android versions (see [dontkillmyapp.com](https://dontkillmyapp.com/)).

Notifee provides a set of helper endpoints to exempt your app from these restrictions.

It's important to note that these endpoints should be used wisely; if your notifications can be deferred, it's recommended to keep the default battery optimization and power settings.

# Battery Optimization

On newer Android devices, apps can, by default, have `Battery Optimization` enabled which makes them susceptible to these power restrictions. It is possible for the user to disable this feature for your app.

<Vimeo id="android-battery-optimization" caption="Android Disable Battery Optimization" />

On Android devices using 6.0 or higher (API level 23) there are two methods to help achieve this, `isBatteryOptimizationEnabled` and `openBatteryOptimizationSettings`.

Below is a snippet on how to use these methods:

```js
// 1. checks if battery optimization is enabled
const batteryOptimizationEnabled = await notifee.isBatteryOptimizationEnabled();
if (batteryOptimizationEnabled) {
  // 2. ask your users to disable the feature
  Alert.alert(
      'Restrictions Detected',
      'To ensure notifications are delivered, please disable battery optimization for the app.',
      [
        // 3. launch intent to navigate the user to the appropriate screen
        {
          text: 'OK, open settings',
          onPress: async () => await notifee.openBatteryOptimizationSettings(),
        },
        {
          text: "Cancel",
          onPress: () => console.log("Cancel Pressed"),
          style: "cancel"
        },
      ],
      { cancelable: false }
    );
};
```

# Power Manager

Depending on the device, there will be a series of steps the user can take to avoid your app being killed.

Call `getPowerManagerInfo()` to retrieve information on the device and its power management configuration. An instance of `PowerManagerInfo` will be returned with an `activity` property, this will be the name of the Android settings screen the user will be navigated to via `openPowerManagerSettings()`.

For [google](https://dontkillmyapp.com/google) devices, such as Pixel 2a, `activity` will be null. For these devices there's not much else the user can do other than turning off battery optimizations. 

Below is a snippet on how to use these methods:

```js
// 1. get info on the device and the Power Manager settings
const powerManagerInfo = await notifee.getPowerManagerInfo();
if (powerManagerInfo.activity) {
  // 2. ask your users to adjust their settings
  Alert.alert(
      'Restrictions Detected',
      'To ensure notifications are delivered, please adjust your settings to prevent the app from being killed',
      [
        // 3. launch intent to navigate the user to the appropriate screen
        {
          text: 'OK, open settings',
          onPress: async () => await notifee.openPowerManagerSettings(),
        },
        {
          text: "Cancel",
          onPress: () => console.log("Cancel Pressed"),
          style: "cancel"
        },
      ],
      { cancelable: false }
    );
};
```

Supported manufactures and their possible settings screens:

| Manufacturer                                                           | Activities          
| --------------------------------------------------------------------- | --------------------- | ------------------------------------------------------- |
| Asus                                                          | PowerSaverSettings,   AutoStartActivity,   FunctionActivity                             |
| samsung                                                          | BatteryActivity 
| huawei                                                           | ProtectActivity,     StartupNormalAppListActivity,             StartupAppControlActivity |     
| xiaomi | AutoStartManagementActivity                   |           
| Letv                                                             | AutobootManageActivity                
| oppo                                                             |  StartupAppListActivity, PowerUsageModelActivity, PowerSaverModeActivity, PowerConsumptionActivity                 | 
| vivo                                                             | AddWhiteListActivity, BgStartUpManagerActivity, BgStartUpManager                 |            
| nokia                                                             | PowerSaverExceptionActivity                         
| oneplus                                                             | ChainLaunchAppListActivity                 
| meizu                                                             | SHOW_APPSEC                   
| htc | LandingPageActivity                 

### Samsung Devices

Samsung devices have their own custom Power Manager called 'Device Care'. 
For example, a Samsung device on Android 10, the user will be navigated to the Battery menu of Device Care. You could ask your users to tap 'App power management', and then tap 'Apps that won't be put to sleep', and add your app to the list.

# Testing

It's possible to test your app under a variety of conditions to see how your notifications behave with several [adb commands](https://developer.android.com/about/versions/pie/power#testing). For example, run `adb shell settings put global low_power 1` to test your app while the device/emulator has low power.