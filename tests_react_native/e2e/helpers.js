/* eslint-disable no-console */
const { resolve } = require('path');
const { existsSync } = require('fs');
const requireAll = require('require-all');

function requirePackageTests(packageName) {
  const e2eDir = `./../packages/${packageName}/e2e`;
  if (existsSync(e2eDir)) {
    console.log(`Loaded tests from ${resolve(e2eDir)}/*`);
    requireAll({
      dirname: resolve(e2eDir),
      filter: /(.+e2e)\.js$/,
      excludeDirs: /^\.(git|svn)$/,
      recursive: true,
    });
  } else {
    console.log(`No tests directory found for ${e2eDir}/*`);
  }
}

Object.defineProperty(global, 'A2A', {
  get() {
    return require('a2a');
  },
});

Object.defineProperty(global, 'firebase', {
  get() {
    return jet.module;
  },
});

Object.defineProperty(global, 'NativeModules', {
  get() {
    return jet.NativeModules;
  },
});

Object.defineProperty(global, 'NativeEventEmitter', {
  get() {
    return jet.NativeEventEmitter;
  },
});

global.isCI = !!process.env.CI;

require('./testing_api')(global);

module.exports.requirePackageTests = requirePackageTests;
