/* eslint-disable no-undef */
import * as ReactNative from 'react-native';

jest.doMock('react-native', () => {
  return Object.setPrototypeOf(
    {
      Platform: {
        OS: 'android',
        select: () => {
          /* do nothing */
        },
      },
      NativeModules: {
        ...ReactNative.NativeModules,
        NotifeeApiModule: {
          addListener: jest.fn(),
          eventsAddListener: jest.fn(),
          eventsNotifyReady: jest.fn(),
        },
      },
    },
    ReactNative,
  );
});
