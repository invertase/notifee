[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidLaunchActivityFlag

# Enumeration: AndroidLaunchActivityFlag

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidLaunchActivityFlag

An enum representing the various flags that can be passed along to `launchActivityFlags` on `NotificationPressAction`.

These flags are added to the Android [Intent](https://developer.android.com/reference/android/content/Intent.html) that launches your activity.

These are only required if you need to customise the behaviour of your activities, in most cases you might not need these.

**`platform`** android

## Table of contents

### Enumeration members

- [BROUGHT\_TO\_FRONT](types_NotificationAndroid.AndroidLaunchActivityFlag.md#brought_to_front)
- [CLEAR\_TASK](types_NotificationAndroid.AndroidLaunchActivityFlag.md#clear_task)
- [CLEAR\_TOP](types_NotificationAndroid.AndroidLaunchActivityFlag.md#clear_top)
- [CLEAR\_WHEN\_TASK\_RESET](types_NotificationAndroid.AndroidLaunchActivityFlag.md#clear_when_task_reset)
- [EXCLUDE\_FROM\_RECENTS](types_NotificationAndroid.AndroidLaunchActivityFlag.md#exclude_from_recents)
- [FORWARD\_RESULT](types_NotificationAndroid.AndroidLaunchActivityFlag.md#forward_result)
- [LAUNCHED\_FROM\_HISTORY](types_NotificationAndroid.AndroidLaunchActivityFlag.md#launched_from_history)
- [LAUNCH\_ADJACENT](types_NotificationAndroid.AndroidLaunchActivityFlag.md#launch_adjacent)
- [MATCH\_EXTERNAL](types_NotificationAndroid.AndroidLaunchActivityFlag.md#match_external)
- [MULTIPLE\_TASK](types_NotificationAndroid.AndroidLaunchActivityFlag.md#multiple_task)
- [NEW\_DOCUMENT](types_NotificationAndroid.AndroidLaunchActivityFlag.md#new_document)
- [NEW\_TASK](types_NotificationAndroid.AndroidLaunchActivityFlag.md#new_task)
- [NO\_ANIMATION](types_NotificationAndroid.AndroidLaunchActivityFlag.md#no_animation)
- [NO\_HISTORY](types_NotificationAndroid.AndroidLaunchActivityFlag.md#no_history)
- [NO\_USER\_ACTION](types_NotificationAndroid.AndroidLaunchActivityFlag.md#no_user_action)
- [PREVIOUS\_IS\_TOP](types_NotificationAndroid.AndroidLaunchActivityFlag.md#previous_is_top)
- [REORDER\_TO\_FRONT](types_NotificationAndroid.AndroidLaunchActivityFlag.md#reorder_to_front)
- [RESET\_TASK\_IF\_NEEDED](types_NotificationAndroid.AndroidLaunchActivityFlag.md#reset_task_if_needed)
- [RETAIN\_IN\_RECENTS](types_NotificationAndroid.AndroidLaunchActivityFlag.md#retain_in_recents)
- [SINGLE\_TOP](types_NotificationAndroid.AndroidLaunchActivityFlag.md#single_top)
- [TASK\_ON\_HOME](types_NotificationAndroid.AndroidLaunchActivityFlag.md#task_on_home)

## Enumeration members

### BROUGHT\_TO\_FRONT

• **BROUGHT\_TO\_FRONT** = `8`

See [FLAG_ACTIVITY_BROUGHT_TO_FRONT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_BROUGHT_TO_FRONT) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1309](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1309)

___

### CLEAR\_TASK

• **CLEAR\_TASK** = `16`

See [FLAG_ACTIVITY_CLEAR_TASK](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_TASK) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1349](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1349)

___

### CLEAR\_TOP

• **CLEAR\_TOP** = `4`

See [FLAG_ACTIVITY_CLEAR_TOP](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_TOP) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1289](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1289)

___

### CLEAR\_WHEN\_TASK\_RESET

• **CLEAR\_WHEN\_TASK\_RESET** = `11`

See [FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1324](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1324)

___

### EXCLUDE\_FROM\_RECENTS

• **EXCLUDE\_FROM\_RECENTS** = `7`

See [FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1304](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1304)

___

### FORWARD\_RESULT

• **FORWARD\_RESULT** = `5`

See [FLAG_ACTIVITY_FORWARD_RESULT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_FORWARD_RESULT) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1294](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1294)

___

### LAUNCHED\_FROM\_HISTORY

• **LAUNCHED\_FROM\_HISTORY** = `10`

See [FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1319](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1319)

___

### LAUNCH\_ADJACENT

• **LAUNCH\_ADJACENT** = `19`

See [FLAG_ACTIVITY_LAUNCH_ADJACENT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_LAUNCH_ADJACENT) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1364](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1364)

___

### MATCH\_EXTERNAL

• **MATCH\_EXTERNAL** = `20`

See [FLAG_ACTIVITY_MATCH_EXTERNAL](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_MATCH_EXTERNAL) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1369](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1369)

___

### MULTIPLE\_TASK

• **MULTIPLE\_TASK** = `3`

See [FLAG_ACTIVITY_MULTIPLE_TASK](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_MULTIPLE_TASK) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1284](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1284)

___

### NEW\_DOCUMENT

• **NEW\_DOCUMENT** = `12`

See [FLAG_ACTIVITY_NEW_DOCUMENT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NEW_DOCUMENT) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1329](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1329)

___

### NEW\_TASK

• **NEW\_TASK** = `2`

See [FLAG_ACTIVITY_NEW_TASK](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NEW_TASK) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1279](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1279)

___

### NO\_ANIMATION

• **NO\_ANIMATION** = `15`

See [FLAG_ACTIVITY_NO_ANIMATION](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NO_ANIMATION) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1344](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1344)

___

### NO\_HISTORY

• **NO\_HISTORY** = `0`

See [FLAG_ACTIVITY_NO_HISTORY](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NO_HISTORY) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1269](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1269)

___

### NO\_USER\_ACTION

• **NO\_USER\_ACTION** = `13`

See [FLAG_ACTIVITY_NO_USER_ACTION](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_NO_USER_ACTION) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1334](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1334)

___

### PREVIOUS\_IS\_TOP

• **PREVIOUS\_IS\_TOP** = `6`

See [FLAG_ACTIVITY_PREVIOUS_IS_TOP](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_PREVIOUS_IS_TOP) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1299](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1299)

___

### REORDER\_TO\_FRONT

• **REORDER\_TO\_FRONT** = `14`

See [FLAG_ACTIVITY_REORDER_TO_FRONT](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_REORDER_TO_FRONT) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1339](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1339)

___

### RESET\_TASK\_IF\_NEEDED

• **RESET\_TASK\_IF\_NEEDED** = `9`

See [FLAG_ACTIVITY_RESET_TASK_IF_NEEDED](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_RESET_TASK_IF_NEEDED) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1314](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1314)

___

### RETAIN\_IN\_RECENTS

• **RETAIN\_IN\_RECENTS** = `18`

See [FLAG_ACTIVITY_RETAIN_IN_RECENTS](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_RETAIN_IN_RECENTS) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1359](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1359)

___

### SINGLE\_TOP

• **SINGLE\_TOP** = `1`

See [FLAG_ACTIVITY_SINGLE_TOP](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_SINGLE_TOP) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1274](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1274)

___

### TASK\_ON\_HOME

• **TASK\_ON\_HOME** = `17`

See [FLAG_ACTIVITY_TASK_ON_HOME](https://developer.android.com/reference/android/content/Intent.html#FLAG_ACTIVITY_TASK_ON_HOME) on the official Android documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:1354](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1354)
