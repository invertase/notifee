module.exports = {
  dependency: {
    platforms: {
      android: {
        packageImportPath: 'import io.invertase.notifee.NotifeePackage;',
        packageInstance: 'new NotifeePackage()',
      },
      ios: {},
    },
  },
};
