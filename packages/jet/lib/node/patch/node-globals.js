/**
 * Make sinon and should the default globally available
 * test assertion/spies/stubs/mocks utilities.
 *
 * Currently hard coded as for these utilities to work
 * they need to be required in both environments, the pairing
 * React Native import for jet also bootstraps these
 * (not globally though as not required) so that they function
 * on objects created inside the vm context of RN.
 */

// TODO look into making these optional in future, or
// TODO have a way to turn them off but are on by default
// TODO (not straightforward as no dynamic imports on RN/Metro)
// TODO on by default to reduce configuration steps
global.sinon = require('sinon');
require('should-sinon');
global.should = require('should');

/**
 * Patch global classes to return their RN equivalents
 * This is needed otherwise typeof checks will fail
 * in both Node.js code and rn code.
 *
 * E.g. Node's Array class is !== RN's Array class.
 *
 * This only patches if called from inside a test context.
 */
// TODO 1) revise stack checks to be shared and support more hooks
// TODO 2) any other global patches required
// TODO 3) make isJetContextAvailable() and isInTestContext() helper utils
const { Uint8Array, Array } = global;

Object.defineProperty(global, 'Uint8Array', {
  get() {
    const { stack } = new Error();
    if (
      (stack.includes('Context.it') || stack.includes('Context.beforeEach')) &&
      global.jet &&
      global.jet.context
    ) {
      return jet.context.window.Uint8Array;
    }
    return Uint8Array;
  },
});

Object.defineProperty(global, 'Array', {
  get() {
    const { stack } = new Error();
    if (
      (stack.includes('Context.it') || stack.includes('Context.beforeEach')) &&
      global.jet &&
      global.jet.context
    ) {
      return jet.context.window.Array;
    }
    return Array;
  },
});

module.exports = {};
