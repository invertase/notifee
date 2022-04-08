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
    modResults.contents = setMavenRepository(modResults.contents);
    return { modResults, ...subConfig };
  });
};

const setCompileSdkVersion = (buildGradle: string): string => {
  const pattern = /compileSdkVersion = 30/g;
  if (!buildGradle.match(pattern)) {
    return buildGradle;
  }
  return buildGradle.replace(/compileSdkVersion = 30/, `compileSdkVersion = 31`);
};

const setMavenRepository = (projectBuildGradle: string): string => {
  if (projectBuildGradle.includes('@notifee/react-native/android/libs')) return projectBuildGradle;

  return projectBuildGradle.replace(
    /mavenLocal\(\)/,
    `mavenLocal()
        maven { url "$rootDir/../node_modules/@notifee/react-native/android/libs" }`,
  );
};

export { setCompileSdkVersion, setMavenRepository };
export default withNotifeeProjectGradlePlugin;
