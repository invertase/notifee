const path = require('path');

const appSrc = path.resolve(__dirname, 'example');

module.exports = {
  paths: function (paths) {
    paths.appSrc = appSrc;
    paths.appIndexJs = path.resolve(appSrc, 'index.ts');
    return paths;
  },
  webpack: function (config) {
    const shims = path.resolve(appSrc, 'shims');
    config.resolve.alias['@react-native-firebase/app$'] = path.resolve(
      shims,
      'firebase-app-web.ts',
    );
    config.resolve.alias['@react-native-firebase/messaging$'] = path.resolve(
      shims,
      'firebase-messaging-web.ts',
    );
    return config;
  },
};
