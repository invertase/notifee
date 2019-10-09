const vm = require('./vm');
const chalk = require('chalk');
const WebSocket = require('ws');

let ws;
let closing = false;

function wsTerminatedError() {
  console.error(
    `${chalk.red(
      '[✈️]',
    )} connection terminated. This is most likely due to a bundling error on your packager or your packager was terminated.`,
  );
  process.exit(1);
}

function wsConnectionError() {
  console.error(
    `${chalk.red(
      '[✈️]',
    )} connection unsuccessful. This is most likely due to a bundling error on your packager or your packager is not running.`,
  );
  process.exit(1);
}

module.exports = {
  start() {
    if (ws) {
      return ws;
    }
    const port = process.env.RCT_METRO_PORT || 8081;
    ws = new WebSocket(`ws://localhost:${port}/debugger-proxy?role=debugger&name=Chrome`, {
      perMessageDeflate: false,
    });

    ws.onerror = wsConnectionError;
    vm.send = obj => {
      if (!closing) {
        ws.send(JSON.stringify(obj));
      }
    };
    ws.onmessage = message => vm.message(JSON.parse(message.data));
    ws.onclose = event => (!event.wasClean ? wsTerminatedError() : '');
    return ws;
  },
  stop() {
    if (ws) {
      closing = true;
      try {
        ws.close();
      } catch (e) {
        // do nothing
      }
    }
  },
};
