import { ConfigPlugin, withPlugins } from '@expo/config-plugins';

import withNotifeeProjectGradlePlugin from './withNotifeeProjectGradlePlugin';

const withNotifee: ConfigPlugin = config => {
  return withPlugins(config, [withNotifeeProjectGradlePlugin]);
};

export default withNotifee;
