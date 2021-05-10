import validateIOSAttachment, {
  validateThumbnailClippingRect,
} from '@notifee/react-native/src/validators/validateIOSAttachment';
import {
  IOSAttachmentThumbnailClippingRect,
  IOSNotificationAttachment,
} from '@notifee/react-native/src/types/NotificationIOS';

const testUrl = 'test-url';
const testId = 'test-id';
const testThumbnailHidden = true;
const testTypeHint = '.jpeg';
const testThumbnailTime = 11;
const testThumbnailClippingRect = {
  x: 10,
  y: 10,
  width: 10,
  height: 10,
};

describe('Validate IOS Attachment', () => {
  describe('validateIOSAttachment()', () => {
    test('returns valid ', () => {
      const attachment: IOSNotificationAttachment = {
        url: testUrl,
        id: testId,
        thumbnailClippingRect: testThumbnailClippingRect,
        thumbnailTime: testThumbnailTime,
        typeHint: testTypeHint,
        thumbnailHidden: testThumbnailHidden,
      };

      const $ = validateIOSAttachment(attachment);

      expect($.url).toEqual(testUrl);
      expect($.id).toEqual(testId);
      expect($.thumbnailClippingRect).toEqual(testThumbnailClippingRect);
      expect($.thumbnailHidden).toEqual(testThumbnailHidden);
      expect($.thumbnailTime).toEqual(testThumbnailTime);
      expect($.typeHint).toEqual(testTypeHint);
    });

    test('returns invalid when no value is provided', () => {
      expect(() => validateIOSAttachment(null as any)).toThrowError(
        "'attachment' expected an object value.",
      );
    });

    test('returns invalid when an invalid url property is provided', () => {
      const notification: IOSNotificationAttachment = {
        url: '',
      };

      expect(() => validateIOSAttachment(notification)).toThrowError(
        "'attachment.url' expected a React Native ImageResource value or a valid string URL.",
      );
    });

    test('returns invalid when an invalid id property is provided', () => {
      const notification: IOSNotificationAttachment = {
        url: testUrl,
        id: null as any,
      };

      expect(() => validateIOSAttachment(notification)).toThrowError(
        "'attachment.id' expected a string value.",
      );
    });

    test('returns invalid when an invalid typeHint property is provided', () => {
      const notification: IOSNotificationAttachment = {
        url: testUrl,
        id: testId,
        typeHint: 1 as any,
      };

      expect(() => validateIOSAttachment(notification)).toThrowError(
        "'attachment.typeHint' expected a string value.",
      );
    });

    test('returns invalid when an invalid thumbnailHidden property is provided', () => {
      const notification: IOSNotificationAttachment = {
        url: testUrl,
        id: testId,
        typeHint: testTypeHint,
        thumbnailHidden: '' as any,
      };

      expect(() => validateIOSAttachment(notification)).toThrowError(
        "'attachment.thumbnailHidden' must be a boolean value if specified.",
      );
    });

    test('returns invalid when an invalid thumbnailTime property is provided', () => {
      const notification: IOSNotificationAttachment = {
        url: testUrl,
        id: testId,
        typeHint: testTypeHint,
        thumbnailHidden: testThumbnailHidden,
        thumbnailTime: '11' as any,
      };

      expect(() => validateIOSAttachment(notification)).toThrowError(
        "'attachment.thumbnailTime' must be a number value if specified.",
      );
    });

    describe('validateThumbnailClippingRect()', () => {
      const testX = 1;
      const testY = 1;
      const testWidth = 1;
      const testHeight = 1;
      test('returns valid when a valid value is provided', () => {
        const $: IOSAttachmentThumbnailClippingRect = {
          x: testX,
          y: testY,
          width: testWidth,
          height: testHeight,
        };

        expect($.x).toEqual(testX);
        expect($.y).toEqual(testY);
        expect($.width).toEqual(testWidth);
        expect($.height).toEqual(testHeight);
      });

      test('returns invalid when an invalid x property is provided', () => {
        const $: IOSAttachmentThumbnailClippingRect = {
          x: '1' as any,
          y: testY,
          width: testWidth,
          height: testHeight,
        };

        expect(() => validateThumbnailClippingRect($)).toThrowError(
          "'thumbnailClippingRect.x' expected a number value.",
        );
      });

      test('returns invalid when an invalid y property is provided', () => {
        const $: IOSAttachmentThumbnailClippingRect = {
          x: testX,
          y: '1' as any,
          width: testWidth,
          height: testHeight,
        };

        expect(() => validateThumbnailClippingRect($)).toThrowError(
          "'thumbnailClippingRect.y' expected a number value.",
        );
      });

      test('returns invalid when an invalid width property is provided', () => {
        const $: IOSAttachmentThumbnailClippingRect = {
          x: testX,
          y: testY,
          width: '1' as any,
          height: testHeight,
        };

        expect(() => validateThumbnailClippingRect($)).toThrowError(
          "'thumbnailClippingRect.width' expected a number value.",
        );
      });

      test('returns invalid when an invalid height property is provided', () => {
        const $: IOSAttachmentThumbnailClippingRect = {
          x: testX,
          y: testY,
          width: testWidth,
          height: '1' as any,
        };

        expect(() => validateThumbnailClippingRect($)).toThrowError(
          "'thumbnailClippingRect.height' expected a number value.",
        );
      });
    });
  });
});
