/*
 * Copyright (c) 2016-present Invertase Limited
 */

/* eslint-disable */

// eslint-disable-next-line @typescript-eslint/no-var-requires
const { resolve, join } = require('path');

// eslint-disable-next-line @typescript-eslint/no-var-requires
const blacklist = require('metro-config/src/defaults/blacklist');

const rootDir = resolve(__dirname, '..');

const config = {
  projectRoot: __dirname,
  resolver: {
    useWatchman: !process.env.TEAMCITY_VERSION,
    blackListRE: blacklist([
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
