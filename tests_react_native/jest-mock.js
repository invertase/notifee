/* eslint-disable no-undef */

jest.mock('react-native', () => {
  const RN = jest.requireActual('react-native');

  RN.NativeModules.NotifeeApiModule = {
    addListener: () => jest.fn(),
  };

  RN.Platform = jest.fn().mockImplementation(() => ({
    ...RN.Platform,
    OS: 'android',
    Version: 123,
  }));
  return RN;
});
