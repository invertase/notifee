/* eslint-disable no-console */
const jet = require('@notifee/jet/platform/node');
const detox = require('detox');
const { requirePackageTests } = require('./helpers');
const { detox: detoxConfig } = require('../package.json');

detoxConfig.configurations['android.emu.debug'].device.avdName =
  process.env.ANDROID_AVD_NAME ||
  'TestingAVD_29' || // TODO change me/switch dynamically on CI / locally
  detoxConfig.configurations['android.emu.debug'].name;

requirePackageTests('react-native');

before(async () => {
  await detox.init(detoxConfig);
  await require('@notifee/tools-android-dumpsys-parser/lib/helper')();
  await jet.init();
});

beforeEach(async function beforeEach() {
  if (jet.context && jet.root && jet.root.setState) {
    jet.root.setState({
      currentTest: this.currentTest,
    });
  }

  const retry = this.currentTest.currentRetry();

  if (retry > 0) {
    if (retry === 1) {
      console.log('');
      console.warn('⚠️ A test failed:');
      console.warn(`️   ->  ${this.currentTest.title}`);
    }

    if (retry > 1) {
      console.warn(`   🔴  Retry #${retry - 1} failed...`);
    }

    console.warn(`️   ->  Retrying in ${1 * retry} seconds ... (${retry})`);
    await Utils.sleep(2000 * retry);
  }
});

after(async () => {
  console.log(' ✨ Tests Complete ✨ ');
  await device.terminateApp();
});
