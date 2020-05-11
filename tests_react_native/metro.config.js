/*
 * Copyright (c) 2016-present Invertase Limited
 */

const { resolve, join } = require('path');

const { createBlacklist } = require('metro');

const rootDir = resolve(__dirname, '..');

const config = {
  projectRoot: __dirname,
  resolver: {
    useWatchman: !process.env.TEAMCITY_VERSION,
    blackListRE: createBlacklist([
      /.*\/__fixtures__\/.*/,
      /.*\/template\/project\/node_modules\/react-native\/.*/,
      new RegExp(`^${escape(resolve(rootDir, 'docs'))}\\/.*$`),
      new RegExp(`^${escape(resolve(rootDir, 'tests_react_native/ios'))}\\/.*$`),
      new RegExp(`^${escape(resolve(rootDir, 'tests_react_native/e2e'))}\\/.*$`),
      new RegExp(`^${escape(resolve(rootDir, 'tests_react_native/android'))}\\/.*$`),
      new RegExp(`^${escape(resolve(rootDir, 'tests_react_native/functions'))}\\/.*$`),
    ]),
    extraNodeModules: new Proxy(
      {},
      {
        get: (target, name) => {
          if (typeof name !== 'string') {
            return target[name];
          }
          if (name && name.startsWith && name.startsWith('@notifee')) {
            const packageName = name.replace('@notifee/', '');
            const replacedPkgName = join(__dirname, `../packages/${packageName}`);
            console.log(replacedPkgName);
            return replacedPkgName;
          }
          return join(__dirname, `node_modules/${name}`);
        },
      },
    ),
  },
  watchFolders: [resolve(__dirname, './../packages/react-native')],
};

module.exports = config;
