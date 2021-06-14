import * as utils from '@notifee/react-native/src/utils';

export const setPlatform = (platform: string): void => {
  Object.defineProperty(utils, 'isIOS', { value: platform === 'ios' });
  Object.defineProperty(utils, 'isAndroid', { value: platform === 'android' });
};
