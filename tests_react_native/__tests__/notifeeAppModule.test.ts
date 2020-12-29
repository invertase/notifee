import Notifee from '@notifee/react-native';

describe('Notifee App Module', () => {
  test('Module is defined on import', () => {
    expect(Notifee).toBeDefined();
  });
  test('Version from module package.json matches SDK_VERSION', () => {
    // eslint-disable-next-line @typescript-eslint/no-var-requires
    const notifeePackageJSON = require('@notifee/react-native/package.json');
    expect(Notifee.SDK_VERSION).toEqual(notifeePackageJSON.version);
  });
});
