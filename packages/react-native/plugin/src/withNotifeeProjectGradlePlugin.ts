import { ConfigPlugin, WarningAggregator, withProjectBuildGradle } from '@expo/config-plugins';

const withNotifeeProjectGradlePlugin: ConfigPlugin = config => {
  return withProjectBuildGradle(config, ({ modResults, ...subConfig }) => {
    if (modResults.language !== 'groovy') {
      WarningAggregator.addWarningAndroid(
        'withNotifee',
        `Cannot automatically configure project build.gradle if it's not groovy`,
      );
      return { modResults, ...subConfig };
    }

    modResults.contents = setCompileSdkVersion(modResults.contents);
    return { modResults, ...subConfig };
  });
};

const setCompileSdkVersion = (buildGradle: string): string => {
  const pattern = /compileSdkVersion = 30/g;
  if (!buildGradle.match(pattern)) {
    return buildGradle;
  }
  return buildGradle.replace(/compileSdkVersion = 30/, `compileSdkVersion = 33`);
};

export { setCompileSdkVersion };
export default withNotifeeProjectGradlePlugin;
