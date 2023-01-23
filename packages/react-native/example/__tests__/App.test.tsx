/**
 * @format
 */

import 'react-native';
import React from 'react';
import App from '../App';

import {render} from '@testing-library/react-native';

it('renders correctly', async () => {
  const appQueries = render(<App />);

  expect(appQueries.findByText('Test Notifee API Action')).not.toBeNull();
});
