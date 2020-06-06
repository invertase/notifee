import { validateAndroidBigPictureStyle } from '@notifee/react-native/src/validators/validateAndroidStyle';
import {
  AndroidBigPictureStyle,
  AndroidStyle,
} from '@notifee/react-native/src/types/NotificationAndroid';

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
});
