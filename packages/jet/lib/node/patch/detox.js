const ws = require('./../ws');
const ready = require('./../ready');
const timing = require('./../timing');

// TODO: Salakar: all of this will go in first release
// TODO: Salakar: jet global will provide it's own methods that internally call these

let detox;
try {
  detox = require('detox');
} catch (e) {
  // ignore
}

async function tryAtMost(userPromise, maxRetries = 3, retryInterval = 5000, _tempPromiseDeferred) {
  const tempPromise = _tempPromiseDeferred || Promise.defer();
  const { resolve, reject, promise } = tempPromise;
  if (maxRetries <= 0) {
    return reject(new Error('Failed to execute promise in the allotted time & retries.'));
  }

  const timeout = setTimeout(function() {
    console.error(new Error('Retrying promise as it did not respond in time'));
    tryAtMost(userPromise, maxRetries - 1, retryInterval, tempPromise);
  }, retryInterval);

  process.nextTick(async () => {
    const result = await userPromise;
    resolve(result);
    clearTimeout(timeout);
    console.log('Promise resolved!!');
  });

  return promise;
}

if (detox) {
  /* ---------------------
   *   DEVICE OVERRIDES
   * --------------------- */
  // TODO: Salakar: all of this will go in first release
  // TODO: Salakar: jet global will provide it's own methods that internally call these
  console.log('Proxying detox ->>>');

  let device;
  Object.defineProperty(global, 'device', {
    get() {
      return device;
    },
    set(originalDevice) {
      // device.reloadReactNative({ ... })
      // todo detoxOriginalReloadReactNative currently broken it seems
      // const detoxOriginalReloadReactNative = originalDevice.reloadReactNative.bind(originalDevice);
      originalDevice.reloadReactNative = async () => {
        ready.reset();
        global.jet.reload();
        return ready.wait();
      };
      // TODO: Salakar: all of this will go in first release
      // TODO: Salakar: jet global will provide it's own methods that internally call these

      // device.launchApp({ ... })
      const detoxOriginalLaunchApp = originalDevice.launchApp.bind(originalDevice);
      originalDevice.launchApp = async options => {
        if (options && options.newInstance) {
          ready.reset();
        }
        console.dir('>>> LAUNCH APP BEFORE');
        const result = await tryAtMost(detoxOriginalLaunchApp(options), 3, 5000);
        console.dir('>>> LAUNCH APP AFTER');
        console.dir('>>> JET WAIT BEFORE');

        await ready.wait();
        console.dir('>>> JET WAIT AFTER');
        return result;
      };

      device = originalDevice;
      return originalDevice;
    },
  });
  // TODO: Salakar: all of this will go in first release
  // TODO: Salakar: jet global will provide it's own methods that internally call these

  /* -------------------
   *   DETOX OVERRIDES
   * ------------------- */

  // detox.init()
  const detoxOriginalInit = detox.init.bind(detox);
  detox.init = async (...args) => {
    ready.reset();
    await detoxOriginalInit(...args);
    return ready.wait();
  };
  // TODO: Salakar: all of this will go in first release
  // TODO: Salakar: jet global will provide it's own methods that internally call these

  // detox.cleanup()
  const detoxOriginalCleanup = detox.cleanup.bind(detox);
  detox.cleanup = async (...args) => {
    timing.stop();
    // detox doesn't automatically terminate ios apps after testing
    // but does on android - added to keep consistency
    if (device.getPlatform() === 'ios') {
      await device.terminateApp();
    }
    ws.stop();
    await detoxOriginalCleanup(...args);
  };
}

module.exports = detox;
