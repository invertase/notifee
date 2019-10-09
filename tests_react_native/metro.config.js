/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
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
          if (name && name.startsWith && name.startsWith('@notifications')) {
            const packageName = name.replace('@notifications/', '');
            const replacedPkgName = join(__dirname, `../packages/${packageName}`);
            console.log(replacedPkgName);
            return replacedPkgName;
          }
          return join(__dirname, `node_modules/${name}`);
        },
      },
    ),
  },
  watchFolders: [
    // resolve(__dirname, '.'),
    resolve(__dirname, './../packages/react-native'),
    resolve(__dirname, './../packages/jet'),
  ],
};

module.exports = config;
