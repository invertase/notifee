const path = require('path');

module.exports = {
  paths: function (paths) {
    paths.appIndexJs = path.resolve(__dirname, 'example/index.ts');
    paths.appSrc = path.resolve(__dirname, 'example');
    return paths;
  },
};
