const coverage = require('./../coverage');
const { sourceMappedError } = require('./../source-map');

let attempts = 0;

function tryPatchAfter() {
  if (attempts > 3) {
    return;
  }
  // setup after hook to ensure final context coverage is captured
  if (global.after) {
    after(() => {
      coverage.collect();
    });
  } else {
    attempts++;
    process.nextTick(tryPatchAfter);
  }
}

tryPatchAfter();

let Mocha;
try {
  Mocha = require('mocha');
} catch (e) {
  // ignore
}

if (Mocha) {
  // override mocha fail so we can replace stack traces
  const Runner = Mocha.Runner;
  const originalFail = Runner.prototype.fail;
  Runner.prototype.fail = function fail(test, error) {
    return originalFail.call(this, test, sourceMappedError(error));
  };
}
