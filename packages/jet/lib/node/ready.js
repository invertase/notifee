/* eslint-disable no-return-assign */
let ready = false;
process.on('jet-attached', () => (ready = true));

module.exports = {
  wait() {
    if (ready) {
      return Promise.resolve();
    }
    return new Promise(resolve => {
      process.once('jet-attached', resolve);
    });
  },
  reset() {
    ready = false;
  },
};
