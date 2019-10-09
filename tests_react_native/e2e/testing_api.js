module.exports = function bootstrapGlobalTestingApi(context) {
  context.Platform = {
    android: process.argv.join('').includes('android.emu'),
    ios: process.argv.join('').includes('ios.emu'),
  };

  context.Utils = {
    sleep: d => new Promise(r => setTimeout(r, d)),
    spyToBeCalledOnceAsync(spy, timeout = 5000) {
      let interval;
      const { resolve, reject, promise } = Promise.defer();
      const timer = setTimeout(() => {
        clearInterval(interval);
        reject(new Error('Spy was not called within timeout period.'));
      }, timeout);

      interval = setInterval(() => {
        if (spy.callCount > 0) {
          clearTimeout(timer);
          clearInterval(interval);
          resolve();
        }
      }, 25);

      return promise;
    },

    spyToBeCalledTimesAsync(spy, times = 2, timeout = 5000) {
      let interval;
      const { resolve, reject, promise } = Promise.defer();
      const timer = setTimeout(() => {
        clearInterval(interval);
        reject(new Error('Spy was not called within timeout period.'));
      }, timeout);

      interval = setInterval(() => {
        if (spy.callCount >= times) {
          clearTimeout(timer);
          clearInterval(interval);
          resolve();
        }
      }, 25);

      return promise;
    },
  };

  context.android = {
    describe(name, ctx) {
      if (context.Platform.android) {
        describe(name, ctx);
      } else {
        xdescribe('SKIP: ANDROID ONLY - ' + name, ctx);
      }
    },
    it(name, ctx) {
      if (context.Platform.android) {
        it(name, ctx);
      } else {
        xit('SKIP: ANDROID ONLY - ' + name, ctx);
      }
    },
  };

  context.ios = {
    describe(name, ctx) {
      if (context.Platform.ios) {
        describe(name, ctx);
      } else {
        xdescribe('SKIP: IOS ONLY - ' + name, ctx);
      }
    },
    it(name, ctx) {
      if (context.Platform.ios) {
        it(name, ctx);
      } else {
        xit('SKIP: IOS ONLY - ' + name, ctx);
      }
    },
  };

  Promise.defer = function defer() {
    const deferred = {
      resolve: null,
      reject: null,
    };

    deferred.promise = new Promise((resolve, reject) => {
      deferred.resolve = resolve;
      deferred.reject = reject;
    });

    return deferred;
  };
};
