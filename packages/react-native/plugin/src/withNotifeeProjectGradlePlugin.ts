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

    modResults.contents = setMavenRepository(modResults.contents);
    return { modResults, ...subConfig };
  });
};

const setMavenRepository = (projectBuildGradle: string): string => {
  if (projectBuildGradle.includes('@notifee/react-native/android/libs')) return projectBuildGradle;

  return projectBuildGradle.replace(
    /mavenLocal\(\)/,
    `mavenLocal()
        maven { url "$rootDir/../node_modules/@notifee/react-native/android/libs" }`,
  );
};

export { setMavenRepository };
export default withNotifeeProjectGradlePlugin;
