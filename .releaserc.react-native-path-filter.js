path = require('path');

const reactNativePaths = ['ios', 'android', 'packages/react-native', 'build_ios_core.sh'];

Object.keys(require.cache)
  .filter(m => path.posix.normalize(m).endsWith('/node_modules/git-log-parser/src/index.js'))
  .forEach(moduleName => {
    const parse = require.cache[moduleName].exports.parse;
    require.cache[moduleName].exports.parse = (
      config,
      options,
    ) => {
      // set the `_` "raw value(s)" param of git-log-parser config to be an array
      // of raw values containing whatever is in there now, along with our filter paths
      // at the end so we only get git-log for those paths
      if (Array.isArray(config._)) config._ = config._.concat(reactNativePaths);
      else if (config._) config._ = [config._, ...reactNativePaths];
      else config._ = reactNativePaths;
      return parse(config, options);
    };
  });
