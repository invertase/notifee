import {
  validateAndroidBigPictureStyle,
  validateAndroidBigTextStyle,
  validateAndroidCallStyle,
  validateAndroidInboxStyle,
  validateAndroidMessagingStyle,
  validateAndroidMessagingStyleMessage,
  validateAndroidPerson,
} from '@notifee/react-native/src/validators/validateAndroidStyle';
import {
  AndroidBigPictureStyle,
  AndroidBigTextStyle,
  AndroidInboxStyle,
  AndroidMessagingStyle,
  AndroidMessagingStyleMessage,
  AndroidPerson,
  AndroidStyle,
  AndroidCallStyle,
  AndroidCallType,
} from '@notifee/react-native/src/types/NotificationAndroid';

const testText = 'test-text';
const testTitle = 'test-title';
const testSummary = 'test-summary';
const testLines = ['test-lines'];
const testId = 'test-id';
const testName = 'test-name';
const testBot = true;
const testImportant = true;
const testIcon = 'test-icon';
const testUri = 'test-uri';
const testTimestamp = 1234;
const testGroup = true;
const testPerson: AndroidPerson = {
  name: 'person',
  bot: false,
  important: false,
};
const testMessages: AndroidMessagingStyleMessage[] = [
  {
    text: testText,
    timestamp: testTimestamp,
  },
];

describe('Validate Android Style', () => {
  describe('validateAndroidBigPictureStyle()', () => {
    test('returns valid ', () => {
      const pictureStyle: AndroidBigPictureStyle = {
        type: AndroidStyle.BIGPICTURE,
        picture: 'picture',
        title: 'title',
        largeIcon: 'largeIcon',
        summary: 'summary',
      };

      const $ = validateAndroidBigPictureStyle(pictureStyle);
      expect($.type).toEqual(AndroidStyle.BIGPICTURE);
      expect($.picture).toEqual('picture');
      expect($.title).toEqual('title');
      expect($.largeIcon).toEqual('largeIcon');
      expect($.summary).toEqual('summary');
    });

    test('returns valid when largeIcon is null', () => {
      const pictureStyle: AndroidBigPictureStyle = {
        type: AndroidStyle.BIGPICTURE,
        picture: 'picture',
        title: 'title',
        largeIcon: null,
        summary: 'summary',
      };

      const $ = validateAndroidBigPictureStyle(pictureStyle);
      expect($.type).toEqual(AndroidStyle.BIGPICTURE);
      expect($.picture).toEqual('picture');
      expect($.title).toEqual('title');
      expect($.largeIcon).toBeNull();
      expect($.summary).toEqual('summary');
    });

    test('throws an error with an invalid largeIcon param', () => {
      const pictureStyle: AndroidBigPictureStyle = {
        type: AndroidStyle.BIGPICTURE,
        picture: 'picture',
        largeIcon: [] as any,
      };

      expect(() => validateAndroidBigPictureStyle(pictureStyle)).toThrowError(
        "'notification.android.style' BigPictureStyle: 'largeIcon' expected a React Native ImageResource value or a valid string URL.",
      );
    });

    test('throws an error with an invalid title param', () => {
      const pictureStyle: AndroidBigPictureStyle = {
        type: AndroidStyle.BIGPICTURE,
        picture: 'picture',
        title: [] as any,
      };

      expect(() => validateAndroidBigPictureStyle(pictureStyle)).toThrowError(
        "'notification.android.style' BigPictureStyle: 'title' expected a string value.",
      );
    });

    test('throws an error with an invalid summary param', () => {
      const pictureStyle: AndroidBigPictureStyle = {
        type: AndroidStyle.BIGPICTURE,
        picture: 'picture',
        summary: [] as any,
      };

      expect(() => validateAndroidBigPictureStyle(pictureStyle)).toThrowError(
        "'notification.android.style' BigPictureStyle: 'summary' expected a string value.",
      );
    });
  });

  describe('validateAndroidBigTextStyle()', () => {
    test('returns valid ', () => {
      const style: AndroidBigTextStyle = {
        type: AndroidStyle.BIGTEXT,
        text: testText,
        title: testTitle,
        summary: testSummary,
      };

      const $ = validateAndroidBigTextStyle(style);
      expect($.type).toEqual(AndroidStyle.BIGTEXT);
      expect($.text).toEqual(testText);
      expect($.title).toEqual(testTitle);
      expect($.summary).toEqual(testSummary);
    });

    test('throws an error with an invalid text param', () => {
      const style: AndroidBigTextStyle = {
        type: AndroidStyle.BIGTEXT,
        text: [] as any,
      };

      expect(() => validateAndroidBigTextStyle(style)).toThrowError(
        "'notification.android.style' BigTextStyle: 'text' expected a valid string value.",
      );
    });

    test('throws an error with an invalid title param', () => {
      const style: AndroidBigTextStyle = {
        type: AndroidStyle.BIGTEXT,
        text: testText,
        title: [] as any,
      };

      expect(() => validateAndroidBigTextStyle(style)).toThrowError(
        "'notification.android.style' BigTextStyle: 'title' expected a string value.",
      );
    });

    test('throws an error with an invalid summary param', () => {
      const style: AndroidBigTextStyle = {
        type: AndroidStyle.BIGTEXT,
        text: testText,
        title: testTitle,
        summary: [] as any,
      };

      expect(() => validateAndroidBigTextStyle(style)).toThrowError(
        "'notification.android.style' BigTextStyle: 'summary' expected a string value.",
      );
    });
  });

  describe('validateAndroidInboxStyle()', () => {
    test('returns valid ', () => {
      const style: AndroidInboxStyle = {
        type: AndroidStyle.INBOX,
        lines: testLines,
        title: testTitle,
        summary: testSummary,
      };

      const $ = validateAndroidInboxStyle(style);
      expect($.type).toEqual(AndroidStyle.INBOX);
      expect($.lines).toEqual(testLines);
      expect($.title).toEqual(testTitle);
      expect($.summary).toEqual(testSummary);
    });

    test('throws an error with an invalid lines param', () => {
      const style: AndroidInboxStyle = {
        type: AndroidStyle.INBOX,
        lines: '' as any,
      };

      expect(() => validateAndroidInboxStyle(style)).toThrowError(
        "'notification.android.style' InboxStyle: 'lines' expected an array.",
      );
    });

    test('throws an error with an invalid lines child param', () => {
      const style: AndroidInboxStyle = {
        type: AndroidStyle.INBOX,
        lines: [1 as any],
      };

      expect(() => validateAndroidInboxStyle(style)).toThrowError(
        `'notification.android.style' InboxStyle: 'lines' expected a string value at array index 0.`,
      );
    });

    test('throws an error with an invalid title param', () => {
      const style: AndroidInboxStyle = {
        type: AndroidStyle.INBOX,
        lines: testLines,
        title: [] as any,
      };

      expect(() => validateAndroidInboxStyle(style)).toThrowError(
        "'notification.android.style' InboxStyle: 'title' expected a string value.",
      );
    });

    test('throws an error with an invalid summary param', () => {
      const style: AndroidInboxStyle = {
        type: AndroidStyle.INBOX,
        lines: testLines,
        title: testText,
        summary: [] as any,
      };

      expect(() => validateAndroidInboxStyle(style)).toThrowError(
        "'notification.android.style' InboxStyle: 'summary' expected a string value.",
      );
    });
  });

  describe('validateAndroidPerson()', () => {
    test('returns valid ', () => {
      const person: AndroidPerson = {
        id: testId,
        name: testName,
        bot: testBot,
        important: testImportant,
        icon: testIcon,
        uri: testUri,
      };

      const $ = validateAndroidPerson(person);
      expect($.id).toEqual(testId);
      expect($.name).toEqual(testName);
      expect($.bot).toEqual(testBot);
      expect($.important).toEqual(testImportant);
      expect($.icon).toEqual(testIcon);
      expect($.uri).toEqual(testUri);
    });

    test('throws an error with an invalid name param', () => {
      const person: AndroidPerson = {
        name: [] as any,
      };

      expect(() => validateAndroidPerson(person)).toThrowError(
        "'person.name' expected a string value.",
      );
    });

    test('throws an error with an invalid id param', () => {
      const person: AndroidPerson = {
        name: testName,
        id: null as any,
      };

      expect(() => validateAndroidPerson(person)).toThrowError(
        "'person.id' expected a string value.",
      );
    });

    test('throws an error with an invalid bot param', () => {
      const person: AndroidPerson = {
        name: testName,
        id: testId,
        bot: 2 as any,
      };

      expect(() => validateAndroidPerson(person)).toThrowError(
        "'person.bot' expected a boolean value.",
      );
    });

    test('throws an error with an invalid important param', () => {
      const person: AndroidPerson = {
        name: testName,
        id: testId,
        bot: testBot,
        important: 2 as any,
      };

      expect(() => validateAndroidPerson(person)).toThrowError(
        "'person.important' expected a boolean value.",
      );
    });

    test('throws an error with an invalid icon param', () => {
      const person: AndroidPerson = {
        name: testName,
        id: testId,
        bot: testBot,
        important: testImportant,
        icon: 2 as any,
      };

      expect(() => validateAndroidPerson(person)).toThrowError(
        "'person.icon' expected a string value.",
      );
    });

    test('throws an error with an invalid uri param', () => {
      const person: AndroidPerson = {
        name: testName,
        id: testId,
        bot: testBot,
        important: testImportant,
        icon: testIcon,
        uri: 2 as any,
      };

      expect(() => validateAndroidPerson(person)).toThrowError(
        "'person.uri' expected a string value.",
      );
    });
  });

  describe('validateAndroidMessagingStyleMessage()', () => {
    test('returns valid ', () => {
      const message: AndroidMessagingStyleMessage = {
        text: testText,
        timestamp: testTimestamp,
        person: testPerson,
      };

      const $ = validateAndroidMessagingStyleMessage(message);
      expect($.text).toEqual(testText);
      expect($.timestamp).toEqual(testTimestamp);
      expect($.person).toEqual(testPerson);
    });

    test('throws an error with an invalid text param', () => {
      const message: AndroidMessagingStyleMessage = {
        text: [] as any,
        timestamp: testTimestamp,
      };

      expect(() => validateAndroidMessagingStyleMessage(message)).toThrowError(
        "'message.text' expected a string value.",
      );
    });

    test('throws an error with an invalid timestamp param', () => {
      const message: AndroidMessagingStyleMessage = {
        text: testText,
        timestamp: [] as any,
      };

      expect(() => validateAndroidMessagingStyleMessage(message)).toThrowError(
        "'message.timestamp' expected a number value.",
      );
    });

    test('throws an error with an invalid person param', () => {
      const message: AndroidMessagingStyleMessage = {
        text: testText,
        timestamp: testTimestamp,
        person: [] as any,
      };

      expect(() => validateAndroidMessagingStyleMessage(message)).toThrowError(
        `'message.person' is invalid.`,
      );
    });
  });

  // TODO
  describe('validateAndroidMessagingStyle()', () => {
    test('returns valid ', () => {
      const style: AndroidMessagingStyle = {
        type: AndroidStyle.MESSAGING,
        person: testPerson,
        title: testTitle,
        group: testGroup,
        messages: testMessages,
      };

      const $ = validateAndroidMessagingStyle(style);
      expect($.type).toEqual(AndroidStyle.MESSAGING);
      expect($.person).toEqual(testPerson);
      expect($.group).toEqual(testGroup);
      expect($.title).toEqual(testTitle);
      expect($.messages).toEqual(testMessages);
    });

    test('throws an error with an invalid person param', () => {
      const style: AndroidMessagingStyle = {
        type: AndroidStyle.MESSAGING,
        person: [] as any,
        messages: testMessages,
      };

      expect(() => validateAndroidMessagingStyle(style)).toThrowError(
        "'notification.android.style' MessagingStyle: 'person' an object value.",
      );
    });

    test('throws an error with an invalid messages param', () => {
      const style: AndroidMessagingStyle = {
        type: AndroidStyle.MESSAGING,
        person: testPerson,
        messages: '' as any,
      };

      expect(() => validateAndroidMessagingStyle(style)).toThrowError(
        "'notification.android.style' MessagingStyle: 'messages' expected an array value.",
      );
    });

    test('throws an error with an invalid messages child param', () => {
      const style: AndroidMessagingStyle = {
        type: AndroidStyle.MESSAGING,
        person: testPerson,
        messages: ['1' as any],
      };

      expect(() => validateAndroidMessagingStyle(style)).toThrowError(
        `'notification.android.style' MessagingStyle: invalid message at index 0`,
      );
    });

    test('throws an error with an invalid title param', () => {
      const style: AndroidMessagingStyle = {
        type: AndroidStyle.MESSAGING,
        person: testPerson,
        messages: testMessages,
        title: [] as any,
      };

      expect(() => validateAndroidMessagingStyle(style)).toThrowError(
        "'notification.android.style' MessagingStyle: 'title' expected a string value.",
      );
    });

    test('throws an error with an invalid group param', () => {
      const style: AndroidMessagingStyle = {
        type: AndroidStyle.MESSAGING,
        person: testPerson,
        messages: testMessages,
        title: testTitle,
        group: 'true' as any,
      };

      expect(() => validateAndroidMessagingStyle(style)).toThrowError(
        "'notification.android.style' MessagingStyle: 'group' expected a boolean value.",
      );
    });
  });

  describe('validateAndroidCallStyle()', () => {
    test('returns valid minimal incoming style', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.INCOMING,
        },
      };

      const $ = validateAndroidCallStyle(style);
      expect($.callTypeActions).toEqual({
        callType: AndroidCallType.INCOMING,
        answerAction: { pressAction: { id: 'answer' } },
        declineAction: { pressAction: { id: 'decline' } },
      });
    });

    test('returns valid ongoing incoming style', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.ONGOING,
        },
      };

      const $ = validateAndroidCallStyle(style);
      expect($.callTypeActions).toEqual({
        callType: 2,
        hangUpAction: { pressAction: { id: 'hangUp' } },
      });
    });

    test('returns valid ongoing screening style', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.SCREENING,
        },
      };

      const $ = validateAndroidCallStyle(style);
      expect($.callTypeActions).toEqual({
        callType: 3,
        answerAction: { pressAction: { id: 'answer' } },
        hangUpAction: { pressAction: { id: 'hangUp' } },
      });
    });

    test('returns valid style with custom pressActionID', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.INCOMING,
          answerAction: { pressAction: { id: 'customAnswer' } },
          declineAction: { pressAction: { id: 'customDecline' } },
        },
      };

      const $ = validateAndroidCallStyle(style);
      expect($.callTypeActions).toEqual({
        answerAction: { pressAction: { id: 'customAnswer' } },
        callType: AndroidCallType.INCOMING,
        declineAction: { pressAction: { id: 'customDecline' } },
      });
    });

    test('throws an error with an invalid person', () => {
      const style: AndroidCallStyle = {
        person: 'test' as any,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.INCOMING,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'notification.android.style' CallStyle: 'person' expected an object value.",
      );
    });

    test('throws an error with an invalid person name', () => {
      const style: AndroidCallStyle = {
        person: {} as AndroidPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.INCOMING,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'notification.android.style' CallStyle: 'person.name' expected a string value..",
      );
    });

    test('throws an error with an invalid integer call type value', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: 0 as AndroidCallType,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'callType' expected a value of 1, 2 or 3.",
      );
    });

    test('throws an error with an invalid call type', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: 'test' as any,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'callType' expected a number value.",
      );
    });

    test('throws an error with an invalid call type actions', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: 1 as any,
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'notification.android.style' CallStyle: 'callTypeActions' expected an object value.",
      );
    });

    test('throws an error with an invalid answer press actions for incoming type', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.INCOMING,
          answerAction: {} as any,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'action' Cannot read properties of undefined (reading 'id').",
      );
    });
    test('throws an error with an invalid decline press actions for incoming type', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.INCOMING,
          declineAction: {} as any,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'action' Cannot read properties of undefined (reading 'id').",
      );
    });
    test('throws an error with an invalid hangUp press actions for ongoing type', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.ONGOING,
          hangUpAction: {} as any,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'action' Cannot read properties of undefined (reading 'id').",
      );
    });
    test('throws an error with an invalid answer press actions for Screening type', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.SCREENING,
          answerAction: {} as any,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'action' Cannot read properties of undefined (reading 'id').",
      );
    });
    test('throws an error with an invalid hangUp press actions for Screening type', () => {
      const style: AndroidCallStyle = {
        person: testPerson,
        type: AndroidStyle.CALL,
        callTypeActions: {
          callType: AndroidCallType.SCREENING,
          hangUpAction: {} as any,
        },
      };

      expect(() => validateAndroidCallStyle(style)).toThrowError(
        "'action' Cannot read properties of undefined (reading 'id').",
      );
    });
  });
});
