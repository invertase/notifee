/* eslint-disable guard-for-in,no-restricted-syntax,no-return-assign */
const url = require('url');
const http = require('http');
const chalk = require('chalk');
const { Script } = require('vm');
const timing = require('./timing');
const invariant = require('assert');
const context = require('./context');
const coverage = require('./coverage');
const { buildSourceMap } = require('./source-map');

let send;
let bundle;

const PREPARE = 'prepareJSRuntime';
const BUNDLE_FILE_NAME = 'app.bundle.js';
const EXECUTE = 'executeApplicationScript';

function reply(id, result) {
  send({
    replyID: id,
    result,
  });
}

/**
 *
 * @param _bundleUrl
 * @return {Promise<string>}
 */
async function downloadUrl(_bundleUrl) {
  const bundleUrl = _bundleUrl.replace(':8081', `:${process.env.RCT_METRO_PORT || 8081}`);
  const res = await new Promise((resolve, reject) =>
    http.get(bundleUrl, resolve).on('error', reject),
  );

  let buffer = '';
  res.setEncoding('utf8');
  res.on('data', chunk => (buffer += chunk));
  await new Promise(resolve => res.on('end', resolve));
  return buffer;
}

/**
 * Downloads the app bundle file and it's source map
 * @param bundleUrl
 * @return {Promise.<Script|*>}
 */
async function downloadBundle(bundleUrl) {
  const bundleStr = await downloadUrl(bundleUrl);

  bundle = new Script(bundleStr, {
    timeout: 120000,
    displayErrors: true,
    filename: BUNDLE_FILE_NAME,
  });

  const sourceMapUrl = bundleStr
    .slice(bundleStr.lastIndexOf('\n'))
    .replace('//# sourceMappingURL=', '');

  const sourceMapSource = await downloadUrl(sourceMapUrl);

  await buildSourceMap(sourceMapSource, BUNDLE_FILE_NAME);

  return bundle;
}

/**
 * Returns the module scope cached bundle or downloads it via http
 *
 * @param request
 * @return {Promise.<*>}
 */
async function getBundle(request) {
  if (bundle) {
    return bundle;
  }
  console.log(`${chalk.blue('[✈️]')} debugger has connected! Downloading app JS bundle...`);

  const parsedUrl = url.parse(request.url, true);
  invariant(parsedUrl.query);
  parsedUrl.query.sourceMapURL = true;
  delete parsedUrl.search;
  return downloadBundle(url.format(parsedUrl));
}

module.exports = {
  set send(fn) {
    send = fn;
  },

  async message(request) {
    const { method } = request;
    switch (method) {
      case PREPARE:
        timing.stop();
        coverage.collect();
        await context.cleanup();
        context.create();
        reply(request.id);
        break;

      case EXECUTE: {
        const script = await getBundle(request);
        if (global.jet.context === null) {
          throw new Error('VM context was not prepared.');
        }
        if (request.inject) {
          for (const name in request.inject) {
            global.jet.context[name] = JSON.parse(request.inject[name]);
          }
        }

        script.runInContext(global.jet.context, {
          filename: BUNDLE_FILE_NAME,
        });
        reply(request.id);
        break;
      }

      default: {
        let returnValue = [[], [], [], 0];
        try {
          if (
            global.jet.context !== null &&
            typeof global.jet.context.__fbBatchedBridge === 'object'
          ) {
            returnValue = global.jet.context.__fbBatchedBridge[method].apply(
              null,
              request.arguments,
            );
          }
        } catch (e) {
          if (method !== '$disconnected') {
            throw new Error(`Failed while making a call bridge call ${method}::${e.message}`);
          }
        } finally {
          reply(request.id, JSON.stringify(returnValue));
        }
      }
    }
  },
};
