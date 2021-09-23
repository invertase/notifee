module.exports = {
  dependency: {
    platforms: {
      android: {
        packageImportPath: 'import io.invertase.notifee.NotifeePackage;',
        packageInstance: 'new NotifeePackage()',
      },
      ios: {
        scriptPhases: [
          {
            name: '[NOTIFEE] Config',
            path: './ios_config.sh',
            execution_position: 'after_compile',
          },
        ],
      },
    },
  },
};
