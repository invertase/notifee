[@notifee/react-native](../README.md) / [Modules](../modules.md) / [types/NotificationAndroid](../modules/types_NotificationAndroid.md) / NotificationAndroid

# Interface: NotificationAndroid

[types/NotificationAndroid](../modules/types_NotificationAndroid.md).NotificationAndroid

The interface for Android specific options which are applied to a notification.

To learn more about Android notifications, view the [Android](/react-native/docs/android/introduction)
documentation for full examples and usage.

**`platform`** android

## Table of contents

### Properties

- [actions](types_NotificationAndroid.NotificationAndroid.md#actions)
- [asForegroundService](types_NotificationAndroid.NotificationAndroid.md#asforegroundservice)
- [autoCancel](types_NotificationAndroid.NotificationAndroid.md#autocancel)
- [badgeCount](types_NotificationAndroid.NotificationAndroid.md#badgecount)
- [badgeIconType](types_NotificationAndroid.NotificationAndroid.md#badgeicontype)
- [category](types_NotificationAndroid.NotificationAndroid.md#category)
- [channelId](types_NotificationAndroid.NotificationAndroid.md#channelid)
- [chronometerDirection](types_NotificationAndroid.NotificationAndroid.md#chronometerdirection)
- [circularLargeIcon](types_NotificationAndroid.NotificationAndroid.md#circularlargeicon)
- [color](types_NotificationAndroid.NotificationAndroid.md#color)
- [colorized](types_NotificationAndroid.NotificationAndroid.md#colorized)
- [defaults](types_NotificationAndroid.NotificationAndroid.md#defaults)
- [fullScreenAction](types_NotificationAndroid.NotificationAndroid.md#fullscreenaction)
- [groupAlertBehavior](types_NotificationAndroid.NotificationAndroid.md#groupalertbehavior)
- [groupId](types_NotificationAndroid.NotificationAndroid.md#groupid)
- [groupSummary](types_NotificationAndroid.NotificationAndroid.md#groupsummary)
- [importance](types_NotificationAndroid.NotificationAndroid.md#importance)
- [inputHistory](types_NotificationAndroid.NotificationAndroid.md#inputhistory)
- [largeIcon](types_NotificationAndroid.NotificationAndroid.md#largeicon)
- [lights](types_NotificationAndroid.NotificationAndroid.md#lights)
- [localOnly](types_NotificationAndroid.NotificationAndroid.md#localonly)
- [ongoing](types_NotificationAndroid.NotificationAndroid.md#ongoing)
- [onlyAlertOnce](types_NotificationAndroid.NotificationAndroid.md#onlyalertonce)
- [pressAction](types_NotificationAndroid.NotificationAndroid.md#pressaction)
- [progress](types_NotificationAndroid.NotificationAndroid.md#progress)
- [showChronometer](types_NotificationAndroid.NotificationAndroid.md#showchronometer)
- [showTimestamp](types_NotificationAndroid.NotificationAndroid.md#showtimestamp)
- [smallIcon](types_NotificationAndroid.NotificationAndroid.md#smallicon)
- [smallIconLevel](types_NotificationAndroid.NotificationAndroid.md#smalliconlevel)
- [sortKey](types_NotificationAndroid.NotificationAndroid.md#sortkey)
- [sound](types_NotificationAndroid.NotificationAndroid.md#sound)
- [style](types_NotificationAndroid.NotificationAndroid.md#style)
- [tag](types_NotificationAndroid.NotificationAndroid.md#tag)
- [ticker](types_NotificationAndroid.NotificationAndroid.md#ticker)
- [timeoutAfter](types_NotificationAndroid.NotificationAndroid.md#timeoutafter)
- [timestamp](types_NotificationAndroid.NotificationAndroid.md#timestamp)
- [vibrationPattern](types_NotificationAndroid.NotificationAndroid.md#vibrationpattern)
- [visibility](types_NotificationAndroid.NotificationAndroid.md#visibility)

## Properties

### actions

• `Optional` **actions**: [`AndroidAction`](types_NotificationAndroid.AndroidAction.md)[]

An array of [AndroidAction](/react-native/reference/androidaction) interfaces.

Adds quick actions to a notification. Quick Actions enable users to interact with your application
directly from the notification body, providing an overall greater user experience.

View the [Quick Actions](/react-native/docs/android/interaction#quick-actions) documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:24](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L24)

___

### asForegroundService

• `Optional` **asForegroundService**: `boolean`

When set to `true` this notification will be shown as a foreground service.

The application can only display one foreground service notification at once. If a
foreground service notification is already running and a new notification with this flag set to
`true` is provided, the service will stop the existing service and start a new one.

Ensure a foreground service runner function has been provided to `registerForegroundService`.
Without one, the notification will not be displayed.

View the [Foreground Service](/react-native/docs/android/foreground-service) documentation for more information.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:40](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L40)

___

### autoCancel

• `Optional` **autoCancel**: `boolean`

Setting this flag will make it so the notification is automatically canceled when the user
presses it in the panel.

By default when the user taps a notification it is automatically removed from the notification
panel. Setting this to `false` will keep the notification in the panel.

If `false`, the notification will persist in the notification panel after being pressed. It will
remain there until the user removes it (e.g. swipes away) or is cancelled via
[`cancelNotification`](/react-native/reference/cancelNotification).

Defaults to `true`.

#### Defined in

[src/types/NotificationAndroid.ts:55](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L55)

___

### badgeCount

• `Optional` **badgeCount**: `number`

Overrides the current number of active notifications shown on the device.

If no number is provided, the system displays the current number of active notifications.

#### Defined in

[src/types/NotificationAndroid.ts:62](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L62)

___

### badgeIconType

• `Optional` **badgeIconType**: [`AndroidBadgeIconType`](../enums/types_NotificationAndroid.AndroidBadgeIconType.md)

Sets the type of badge used when the notification is being displayed in badge mode.

View the [Badges](/react-native/docs/android/appearance#badges) documentation for more information
and usage examples.

Defaults to `AndroidBadgeIconType.LARGE`.

**`platform`** android API Level >= 26

#### Defined in

[src/types/NotificationAndroid.ts:74](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L74)

___

### category

• `Optional` **category**: [`AndroidCategory`](../enums/types_NotificationAndroid.AndroidCategory.md)

Assigns the notification to a category. Use the one which best describes the notification.

The category may be used by the device for ranking and filtering. It has no visual or behavioural
impact.

#### Defined in

[src/types/NotificationAndroid.ts:82](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L82)

___

### channelId

• `Optional` **channelId**: `string`

Specifies the `AndroidChannel` which the notification will be delivered on.

On Android 8.0 (API 26) the channel ID is required. Providing a invalid channel ID will throw
an error. View the [Channels & Groups](/react-native/docs/android/channels) documentation for
more information and usage examples.

#### Defined in

[src/types/NotificationAndroid.ts:91](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L91)

___

### chronometerDirection

• `Optional` **chronometerDirection**: ``"up"`` \| ``"down"``

If `showChronometer` is `true`, the direction of the chronometer can be changed to count down instead of up.

Has no effect if `showChronometer` is `false`.

Defaults to `up`.

#### Defined in

[src/types/NotificationAndroid.ts:124](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L124)

___

### circularLargeIcon

• `Optional` **circularLargeIcon**: `boolean`

Whether the large icon should be circular.

If `true`, the large icon will be rounded in the shape of a circle.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:199](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L199)

___

### color

• `Optional` **color**: `string`

Set an custom accent color for the notification. If not provided, the default notification
system color will be used.

The color can be a predefined system `AndroidColor` or [hexadecimal](https://gist.github.com/lopspower/03fb1cc0ac9f32ef38f4).

View the [Color](/react-native/docs/android/appearance#color) documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:101](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L101)

___

### colorized

• `Optional` **colorized**: `boolean`

When `asForegroundService` is `true`, the notification will use the provided `color` property
to set a background color on the notification. This property has no effect when `asForegroundService`
is `false`.

This should only be used for high priority ongoing tasks like navigation, an ongoing call,
or other similarly high-priority events for the user.

View the [Foreground Service](/react-native/docs/android/foreground-service) documentation for more information.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:115](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L115)

___

### defaults

• `Optional` **defaults**: [`AndroidDefaults`](../enums/types_NotificationAndroid.AndroidDefaults.md)[]

For devices without notification channel support, this property sets the default behaviour
for a notification.

On API Level >= 26, this has no effect.

See [AndroidDefaults](/react-native/reference/androiddefaults) for more information.

**`platform`** android API Level < 26

#### Defined in

[src/types/NotificationAndroid.ts:136](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L136)

___

### fullScreenAction

• `Optional` **fullScreenAction**: [`NotificationFullScreenAction`](types_Notification.NotificationFullScreenAction.md)

The `fullScreenAction` property allows you to show a custom UI
in full screen when the notification is displayed.

View the [FullScreenAction](/react-native/docs/android/behaviour#full-screen-action) documentation to learn
more.

#### Defined in

[src/types/NotificationAndroid.ts:263](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L263)

___

### groupAlertBehavior

• `Optional` **groupAlertBehavior**: [`AndroidGroupAlertBehavior`](../enums/types_NotificationAndroid.AndroidGroupAlertBehavior.md)

Sets the group alert behavior for this notification. Use this method to mute this notification
if alerts for this notification's group should be handled by a different notification. This is
only applicable for notifications that belong to a `groupId`. This must be called on all notifications
you want to mute. For example, if you want only the summary of your group to make noise, all
children in the group should have the group alert behavior `AndroidGroupAlertBehavior.SUMMARY`.

View the [Android Grouping & Sorting guide](/react-native/docs/android/grouping-and-sorting#group-behaviour)
documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:159](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L159)

___

### groupId

• `Optional` **groupId**: `string`

Set this notification to be part of a group of notifications sharing the same key. Grouped notifications may
display in a cluster or stack on devices which support such rendering.

On some devices, the system may automatically group notifications.

View the [Android Grouping & Sorting guide](/react-native/docs/android/grouping-and-sorting) documentation to
learn more.

#### Defined in

[src/types/NotificationAndroid.ts:147](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L147)

___

### groupSummary

• `Optional` **groupSummary**: `boolean`

Whether this notification should be a group summary.

If `true`, Set this notification to be the group summary for a group of notifications. Grouped notifications may display in
a cluster or stack on devices which support such rendering. Requires a `groupId` key to be set.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:169](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L169)

___

### importance

• `Optional` **importance**: [`AndroidImportance`](../enums/types_NotificationAndroid.AndroidImportance.md)

Set a notification importance for devices without channel support.

Devices using Android API Level < 26 have no channel support, meaning incoming notifications
won't be assigned an importance level from the channel. If your application supports devices
without channel support, set this property to directly assign an importance level to the incoming
notification.

Defaults to `AndroidImportance.DEFAULT`.

View the [Appearance](/react-native/docs/android/appearance#importance) documentation to learn
more.

**`platform`** android API Level < 26

#### Defined in

[src/types/NotificationAndroid.ts:280](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L280)

___

### inputHistory

• `Optional` **inputHistory**: `string`[]

The local user input history for this notification.

Input history is shown on supported devices below the main notification body. History of the
users input with the notification should be shown when receiving action input by updating
the existing notification. It is recommended to clear the history when it is no longer
relevant (e.g. someone has responded to the users input).

#### Defined in

[src/types/NotificationAndroid.ts:179](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L179)

___

### largeIcon

• `Optional` **largeIcon**: `string` \| `number` \| `object`

A local file path using the 'require()' method or a remote http to the picture to display.

Sets a large icon on the notification.

View the [Android Appearance](/react-native/docs/android/appearance#large-icons) documentation to learn
more about this property.

#### Defined in

[src/types/NotificationAndroid.ts:190](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L190)

___

### lights

• `Optional` **lights**: [`string`, `number`, `number`]

Sets the color and frequency of the light pattern. This only has effect on supported devices.

The option takes an array containing a hexadecimal color value or predefined `AndroidColor`,
along with the number of milliseconds to show the light, and the number of milliseconds to
turn off the light. The light frequency pattern is repeated.

View the [Lights](/react-native/docs/android/behaviour#lights) documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:210](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L210)

___

### localOnly

• `Optional` **localOnly**: `boolean`

Sets whether the notification will only appear on the local device.

Users who have connected devices which support notifications (such as a smart watch) will
receive an alert for the notification on that device. If set to `true`, the notification will
only alert on the main device.

Defaults to `false`.

#### Defined in

[src/types/NotificationAndroid.ts:221](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L221)

___

### ongoing

• `Optional` **ongoing**: `boolean`

Set whether this is an on-going notification.

Setting this value to `true` changes the default behaviour of a notification:

- Ongoing notifications are sorted above the regular notifications in the notification panel.
- Ongoing notifications do not have an 'X' close button, and are not affected by the "Clear all" button.

View the [Ongoing](/react-native/docs/android/behaviour#ongoing) documentation for more information.

#### Defined in

[src/types/NotificationAndroid.ts:233](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L233)

___

### onlyAlertOnce

• `Optional` **onlyAlertOnce**: `boolean`

Notifications with the same `id` will only show a single instance at any one time on your device,
however will still alert the user (for example, by making a sound).

If this flag is set to `true`, notifications with the same `id` will only alert the user once whilst
the notification is visible.

This property is commonly used when frequently updating a notification (such as updating the progress bar).

#### Defined in

[src/types/NotificationAndroid.ts:244](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L244)

___

### pressAction

• `Optional` **pressAction**: [`NotificationPressAction`](types_Notification.NotificationPressAction.md)

By default notifications have no behaviour when a user presses them. The
`pressAction` property allows you to set what happens when a user presses
the notification.

View the [Interaction](/react-native/docs/android/interaction) documentation to learn
more.

#### Defined in

[src/types/NotificationAndroid.ts:254](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L254)

___

### progress

• `Optional` **progress**: [`AndroidProgress`](types_NotificationAndroid.AndroidProgress.md)

A notification can show current progress of a task. The progress state can either be fixed or
indeterminate (unknown).

View the [Progress Indicators](/react-native/docs/android/progress-indicators) documentation
to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:289](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L289)

___

### showChronometer

• `Optional` **showChronometer**: `boolean`

Shows a counting timer on the notification, useful for on-going notifications such as a phone call.

If no `timestamp` is provided, a counter will display on the notification starting from 00:00. If a `timestamp` is
provided, the number of hours/minutes/seconds since that have elapsed since that value will be shown instead.

Defaults to `false`.

View the [Timers](/react-native/docs/android/timers#timers) documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:364](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L364)

___

### showTimestamp

• `Optional` **showTimestamp**: `boolean`

Sets whether the `timestamp` provided is shown in the notification.

Setting this field is useful for notifications which are more informative with a timestamp,
such as an E-Mail.

If no `timestamp` is set, this field has no effect.

View the [Timestamps](/react-native/docs/android/timers#timestamps) documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:301](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L301)

___

### smallIcon

• `Optional` **smallIcon**: `string`

The small icon to show in the heads-up notification.

View the [Icons](/react-native/docs/android/appearance#small-icons) documentation to learn
more.

#### Defined in

[src/types/NotificationAndroid.ts:309](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L309)

___

### smallIconLevel

• `Optional` **smallIconLevel**: `number`

An additional level parameter for when the icon is an instance of a Android `LevelListDrawable`.

#### Defined in

[src/types/NotificationAndroid.ts:314](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L314)

___

### sortKey

• `Optional` **sortKey**: `string`

Set a sort key that orders this notification among other notifications from the same package.
This can be useful if an external sort was already applied and an app would like to preserve
this. Notifications will be sorted lexicographically using this value, although providing
different priorities in addition to providing sort key may cause this value to be ignored.

If a `groupId` has been set, the sort key can also be used to order members of a notification group.

View the [Android Grouping & Sorting](/react-native/docs/android/grouping-and-sorting#sorting)
documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:327](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L327)

___

### sound

• `Optional` **sound**: `string`

Overrides the sound the notification is displayed with.

The default value is to play no sound. To play the default system sound use 'default'.

This setting has no behaviour on Android after API level version 26, instead you can set the
sound on the notification channels.

View the [Sound](/react-native/docs/android/behaviour#sound) documentation for more information.

**`platform`** android API Level < 26

#### Defined in

[src/types/NotificationAndroid.ts:416](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L416)

___

### style

• `Optional` **style**: [`AndroidBigPictureStyle`](types_NotificationAndroid.AndroidBigPictureStyle.md) \| [`AndroidBigTextStyle`](types_NotificationAndroid.AndroidBigTextStyle.md) \| [`AndroidInboxStyle`](types_NotificationAndroid.AndroidInboxStyle.md) \| [`AndroidMessagingStyle`](types_NotificationAndroid.AndroidMessagingStyle.md)

Styled notifications provide users with more informative content and additional functionality.

Android supports different styles, however only one can be used with a notification.

View the [Styles](/react-native/docs/android/styles) documentation to learn more
view usage examples.

#### Defined in

[src/types/NotificationAndroid.ts:337](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L337)

___

### tag

• `Optional` **tag**: `string`

Sets a tag on the notification.

Tags can be used to query groups notifications by the tag value. Setting a tag has no
impact on the notification itself.

#### Defined in

[src/types/NotificationAndroid.ts:390](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L390)

___

### ticker

• `Optional` **ticker**: `string`

Text that summarizes this notification for accessibility services. As of the Android L release, this
text is no longer shown on screen, but it is still useful to accessibility services
(where it serves as an audible announcement of the notification's appearance).

Ticker text does not show in the notification.

#### Defined in

[src/types/NotificationAndroid.ts:346](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L346)

___

### timeoutAfter

• `Optional` **timeoutAfter**: `number`

Sets the time in milliseconds at which the notification should be
automatically cancelled once displayed, if it is not already cancelled.

#### Defined in

[src/types/NotificationAndroid.ts:352](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L352)

___

### timestamp

• `Optional` **timestamp**: `number`

The timestamp in milliseconds for this notification. Notifications in the panel are sorted by this time.

The timestamp can be used with other properties to change the behaviour of a notification:

- Use with `showTimestamp` to show the timestamp to the users.
- Use with `showChronometer` to create a on-going timer.

View the [Timers](/react-native/docs/android/timers) documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:402](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L402)

___

### vibrationPattern

• `Optional` **vibrationPattern**: `number`[]

Sets the vibration pattern the notification uses when displayed. Must be an even amount of numbers.

View the [Vibration](/react-native/docs/android/behaviour#vibration) documentation to learn more.

#### Defined in

[src/types/NotificationAndroid.ts:371](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L371)

___

### visibility

• `Optional` **visibility**: [`AndroidVisibility`](../enums/types_NotificationAndroid.AndroidVisibility.md)

Sets the visibility for this notification. This may be used for apps which show user
sensitive information (e.g. a banking app).

Defaults to `AndroidVisibility.PRIVATE`.

View the [Visibility](/react-native/docs/android/appearance#visibility) documentation to learn
more.

#### Defined in

[src/types/NotificationAndroid.ts:382](https://github.com/notifee/react-native-notifee/blob/ee86b51/src/types/NotificationAndroid.ts#L382)
