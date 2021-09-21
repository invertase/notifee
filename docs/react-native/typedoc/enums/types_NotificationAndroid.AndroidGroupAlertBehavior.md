[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / AndroidGroupAlertBehavior

# Enumeration: AndroidGroupAlertBehavior

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).AndroidGroupAlertBehavior

Enum used to describe how a notification alerts the user when it apart of a group.

View the [Grouping & Sorting](/react-native/docs/android/grouping-and-sorting#group-behaviour) documentation to
learn more.

**`platform`** android

## Table of contents

### Enumeration members

- [ALL](types_NotificationAndroid.AndroidGroupAlertBehavior.md#all)
- [CHILDREN](types_NotificationAndroid.AndroidGroupAlertBehavior.md#children)
- [SUMMARY](types_NotificationAndroid.AndroidGroupAlertBehavior.md#summary)

## Enumeration members

### ALL

• **ALL** = `0`

All notifications will alert.

#### Defined in

[src/types/NotificationAndroid.ts:1137](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1137)

___

### CHILDREN

• **CHILDREN** = `2`

Children of a group will alert the user. The summary notification will not alert when displayed.

#### Defined in

[src/types/NotificationAndroid.ts:1147](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1147)

___

### SUMMARY

• **SUMMARY** = `1`

Only the summary notification will alert the user when displayed. The children of the group will not alert.

#### Defined in

[src/types/NotificationAndroid.ts:1142](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L1142)
