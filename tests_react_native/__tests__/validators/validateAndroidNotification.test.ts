import validateAndroidNotification from '@notifee/react-native/src/validators/validateAndroidNotification';
import {
  NotificationAndroid,
  AndroidVisibility,
  AndroidBadgeIconType,
  AndroidDefaults,
  AndroidGroupAlertBehavior,
  AndroidAction,
  AndroidCategory,
  AndroidProgress,
  AndroidImportance,
} from '@notifee/react-native/src/types/NotificationAndroid';
import { NotificationPressAction } from '@notifee/react-native/src/types/Notification';

describe('Validate Android Notification', () => {
  describe('validateAndroidNotification()', () => {
    const pressAction: NotificationPressAction = { id: 'id' };
    const androidProgress: AndroidProgress = { indeterminate: false };

    const androidAction: AndroidAction = {
      pressAction,
      title: 'title',
    };

    test('returns valid ', () => {
      const androidInput: NotificationAndroid = {
        autoCancel: true,
        asForegroundService: false,
        lightUpScreen: false,
        badgeIconType: AndroidBadgeIconType.LARGE,
        colorized: false,
        chronometerDirection: 'up',
        defaults: [AndroidDefaults.ALL],
        groupAlertBehavior: AndroidGroupAlertBehavior.ALL,
        groupSummary: false,
        localOnly: false,
        loopSound: false,
        ongoing: false,
        onlyAlertOnce: false,
        importance: AndroidImportance.DEFAULT,
        showTimestamp: false,
        smallIcon: 'ic_launcher',
        showChronometer: false,
        visibility: AndroidVisibility.PRIVATE,
        channelId: 'channelId',
        actions: [androidAction],
        category: AndroidCategory.ALARM,
        badgeCount: 0,
        color: 'blue',
        groupId: 'groupId',
        inputHistory: ['test'],
        largeIcon: 'largeIcon',
        lights: ['blue', 1, 1],
        progress: androidProgress,
        smallIconLevel: 1,
        sortKey: 'sortkey',
        tag: 'tag',
        ticker: 'ticker',
        timeoutAfter: 5,
        vibrationPattern: [1, 1],
        timestamp: 111,
        sound: 'sound',
      };

      const $ = validateAndroidNotification(androidInput);
      expect($.autoCancel).toEqual(true);
      expect($.asForegroundService).toEqual(false);
      expect($.lightUpScreen).toEqual(false);
      expect($.badgeIconType).toEqual(AndroidBadgeIconType.LARGE);
      expect($.colorized).toEqual(false);
      expect($.chronometerDirection).toEqual('up');
      expect($.defaults).toEqual([AndroidDefaults.ALL]);
      expect($.groupAlertBehavior).toEqual(AndroidGroupAlertBehavior.ALL);
      expect($.groupSummary).toEqual(false);
      expect($.ongoing).toEqual(false);
      expect($.onlyAlertOnce).toEqual(false);
      expect($.importance).toEqual(AndroidImportance.DEFAULT);
      expect($.showTimestamp).toEqual(false);
      expect($.smallIcon).toEqual('ic_launcher');
      expect($.showChronometer).toEqual(false);
      expect($.visibility).toEqual(AndroidVisibility.PRIVATE);
      expect($.channelId).toEqual('channelId');
      expect($.color).toEqual('blue');
      expect($.groupId).toEqual('groupId');
      expect($.inputHistory).toEqual(['test']);
      expect($.largeIcon).toEqual('largeIcon');
      expect($.lights).toEqual(['blue', 1, 1]);
      expect($.smallIconLevel).toEqual(1);
      expect($.sortKey).toEqual('sortkey');
      expect($.tag).toEqual('tag');
      expect($.ticker).toEqual('ticker');
      expect($.timeoutAfter).toEqual(5);
      expect($.vibrationPattern).toEqual([1, 1]);
      expect($.timestamp).toEqual(111);
      expect($.sound).toEqual('sound');
    });

    test('returns default values when no params provided ', () => {
      const $ = validateAndroidNotification();

      expect($.autoCancel).toEqual(true);
      expect($.asForegroundService).toEqual(false);
      expect($.lightUpScreen).toEqual(false);
      expect($.badgeIconType).toEqual(AndroidBadgeIconType.LARGE);
      expect($.colorized).toEqual(false);
      expect($.chronometerDirection).toEqual('up');
      expect($.defaults).toEqual([AndroidDefaults.ALL]);
      expect($.groupAlertBehavior).toEqual(AndroidGroupAlertBehavior.ALL);
      expect($.groupSummary).toEqual(false);
      expect($.ongoing).toEqual(false);
      expect($.onlyAlertOnce).toEqual(false);
      expect($.importance).toEqual(AndroidImportance.DEFAULT);
      expect($.showTimestamp).toEqual(false);
      expect($.smallIcon).toEqual('ic_launcher');
      expect($.showChronometer).toEqual(false);
      expect($.visibility).toEqual(AndroidVisibility.PRIVATE);
    });

    test('throws an error with an invalid param ', () => {
      expect(() => validateAndroidNotification([] as any)).toThrowError(
        "'notification.android' expected an object value.",
      );
    });

    test('throws an error when actions is not a valid array', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        actions: {} as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.actions' expected an array of AndroidAction types.",
      );
    });

    test('throws an error when actions contains an invalid android action', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        actions: ['test'] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.actions' invalid AndroidAction. 'action' expected an object value..",
      );
    });

    test('throws an error when asForegroundService is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        asForegroundService: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.asForegroundService' expected a boolean value.",
      );
    });

    test('throws an error when lightUpScreen is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        lightUpScreen: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.lightUpScreen' expected a boolean value.",
      );
    });

    test('throws an error when autoCancel is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        autoCancel: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.autoCancel' expected a boolean value.",
      );
    });
    test('throws an error when badgeCount is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        badgeCount: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.badgeCount' expected a number value.",
      );
    });

    test('throws an error when badgeIconType is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        badgeIconType: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.badgeIconType' expected a valid AndroidBadgeIconType.",
      );
    });

    test('throws an error when category is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        category: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.category' expected a valid AndroidCategory.",
      );
    });

    test('throws an error when color is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        color: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.color' expected a string value.",
      );
    });

    test('throws an error when color is an invalid selection', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        color: 'unknown',
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.color' invalid color. Expected an AndroidColor or hexadecimal string value.",
      );
    });

    test('throws an error when colorized is invalud', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        colorized: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.colorized' expected a boolean value.",
      );
    });

    test('throws an error when chronometerDirection is invalud', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        chronometerDirection: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.chronometerDirection' expected a string value.",
      );
    });

    test('throws an error when chronometerDirection is not up or down', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        chronometerDirection: '' as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        `'notification.android.chronometerDirection' must be one of "up" or "down".`,
      );
    });

    test('throws an error when defaults is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        defaults: {} as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.defaults' expected an array.",
      );
    });

    test('throws an error when defaults is an empty array', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        defaults: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.defaults' expected an array containing AndroidDefaults.",
      );
    });

    test('throws an error when groupId is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        groupId: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.groupId' expected a string value.",
      );
    });

    test('throws an error when groupAlertBehavior contains an invalid groupAlertBehavior', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        groupAlertBehavior: ['test'] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.groupAlertBehavior' expected a valid AndroidGroupAlertBehavior.",
      );
    });

    test('throws an error when groupSummary is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        groupSummary: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.groupSummary' expected a boolean value.",
      );
    });

    test('throws an error when inputHistory is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        inputHistory: {} as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.inputHistory' expected an array of string values.",
      );
    });

    test('throws an error when largeIcon is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        largeIcon: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.largeIcon' expected a React Native ImageResource value or a valid string URL.",
      );
    });

    test('throws an error when lights is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        lights: {} as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.lights' expected an array value containing the color, on ms and off ms.",
      );
    });

    test('throws an error when lights color property is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        lights: ['unknown' as any, 0, 0],
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.lights' invalid color. Expected an AndroidColor or hexadecimal string value.",
      );
    });

    test('throws an error when lights on property is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        lights: ['blue', -1, 0],
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        `'notification.android.lights' invalid "on" millisecond value, expected a number greater than 0.`,
      );
    });

    test('throws an error when lights off property is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        lights: ['blue', 1, -1],
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        `notification.android.lights' invalid "off" millisecond value, expected a number greater than 0.`,
      );
    });

    test('throws an error when localOnly is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        localOnly: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.localOnly' expected a boolean value.",
      );
    });

    test('throws an error when ongoing is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        ongoing: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.ongoing' expected a boolean value.",
      );
    });

    test('throws an error when onlyAlertOnce is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        onlyAlertOnce: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.onlyAlertOnce' expected a boolean value.",
      );
    });

    test('throws an error when pressAction is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        pressAction: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.pressAction' 'pressAction' expected an object value.",
      );
    });

    test('throws an error when importance value is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        importance: '' as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.importance' expected a valid Importance.",
      );
    });

    test('throws an error when progress is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress' expected an object value.",
      );
    });

    test('throws an error when progress indeterminate is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress: { indeterminate: [] as any },
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress.indeterminate' expected a boolean value.",
      );
    });

    test('throws an error when progress max is a negative number', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress: { max: -1 },
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress.max' expected a positive number value.",
      );
    });

    test('throws an error when current is a negative number', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress: { current: -1 },
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress.current' expected a positive number value.",
      );
    });

    test('throws an error when no max value has been provided with a current value', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress: { current: 1, max: undefined as any },
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress.current' when providing a current value, you must also specify a `max` value.",
      );
    });

    test('throws an error when current value is more than max', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress: { current: 3, max: 2 },
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress' the current value cannot be greater than the max value.",
      );
    });

    test('throws an error when progress showTimestamp is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        showTimestamp: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.showTimestamp' expected a boolean value.",
      );
    });

    test('throws an error when progress smallIcon is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        smallIcon: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.smallIcon' expected value to be a string.",
      );
    });

    test('throws an error when progress sortKey is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        sortKey: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.sortKey' expected a string value.",
      );
    });

    test('throws an error when progress style is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        style: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.style' expected an object value.",
      );
    });

    test('throws an error when channel is an invalid type', () => {
      const channelGroup: NotificationAndroid = {
        channelId: {} as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.channelId' expected a string value.",
      );
    });

    test('throws an error when tag is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        tag: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.tag' expected a string value.",
      );
    });

    test('throws an error when tag contains a pipe operator', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        tag: '|',
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        `'notification.android.tag' tag cannot contain the "|" (pipe) character.`,
      );
    });

    test('throws an error when ticker is invalid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        ticker: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.ticker' expected a string value.",
      );
    });

    test('throws an error when timeoutAfter is non numeric', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        timeoutAfter: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.timeoutAfter' expected a number value.",
      );
    });

    test('throws an error when timeoutAfter is an invalid timestamp', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        timeoutAfter: -1,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.timeoutAfter' invalid millisecond timestamp.",
      );
    });

    test('throws an error when showChronometer is an invalid boolean', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        showChronometer: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.showChronometer' expected a boolean value.",
      );
    });

    test('throws an error when vibrationPattern is an invalid type', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        vibrationPattern: {} as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.vibrationPattern' expected an array containing an even number of positive values.",
      );
    });

    test('throws an error when vibrationPattern is an invalid vibration pattern', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        vibrationPattern: ['test', -1, -1] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.vibrationPattern' expected an array containing an even number of positive values.",
      );
    });

    test('throws an error when visibility is an invalid type', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        visibility: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.visibility' expected a valid AndroidVisibility value.",
      );
    });

    test('throws an error when timestamp is not valid', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        timestamp: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.timestamp' expected a number value.",
      );
    });

    test('throws an error when timestamp is a not a future date', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        timestamp: -1,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.timestamp' invalid millisecond timestamp, date must be a positive number",
      );
    });

    test('throws an error when sound is an invalid type', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        sound: [] as any,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.sound' expected a valid sound string.",
      );
    });

    test('throws an error when defaults is an invalid type', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        defaults: ['' as any],
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.defaults' invalid array value, expected an AndroidDefaults value.",
      );
    });

    test('throws an error when providing a max value for progress without setting a current value', () => {
      const progress: AndroidProgress = {
        indeterminate: false,
        max: 10,
        current: undefined as any,
      };

      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        progress,
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.progress.max' when providing a max value, you must also specify a current value.",
      );
    });

    test('throws an error when defaults is an invalid smallIconLevel', () => {
      const channelGroup: NotificationAndroid = {
        channelId: 'channelId',
        smallIconLevel: {} as any,
        smallIcon: 'smallIcon',
      };

      expect(() => validateAndroidNotification(channelGroup)).toThrowError(
        "'notification.android.smallIconLevel' expected value to be a number.",
      );
    });
  });
});
