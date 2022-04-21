import withNotifee from '../src';
import { ExpoConfig } from '@expo/config-types';

const dummyConfig: ExpoConfig = {
  name: 'dummy',
  slug: 'dummy',
  _internal: {
    projectRoot: 'dummy-path',
  },
};

describe('Expo plugin', () => {
  test('should run the plugin without error', () => {
    const resultConfig = withNotifee(dummyConfig);
    expect(resultConfig).toMatchSnapshot();
  });
});
