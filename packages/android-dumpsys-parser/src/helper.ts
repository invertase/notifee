const { default: parser, stringToHashCode} = require('./').default;

// tslint:disable-next-line:no-function-expression
module.exports = async function createNotificationsAPI() {
  // @ts-ignore
  if (device.getPlatform() === 'ios') {
    // @ts-ignore
    Object.assign(device, {
      get notifications() {
        throw new Error(`Notifications helpers only supported on Android`);
      },
    });
    return;
  }

  // @ts-ignore
  const deviceId = device._deviceId;
  // @ts-ignore
  const deviceDriver = device.deviceDriver;
  const adb = deviceDriver.adb;
  const cmd = adb.adbCmd.bind(adb);
  const cmdStr = 'shell dumpsys notification --noredact';

  async function getAllNotifications() {
    const output = (await cmd(deviceId, cmdStr, {})).stdout;
    return parser(output);
  }

  // @ts-ignore
  Object.assign(device, {
    get notifications() {
      return {
        async findById(stringId: string) {
          const notifications = await getAllNotifications();
          return notifications.find((n: any) => n.id === stringToHashCode(stringId));
        },
        all() {
          return getAllNotifications();
        },
        async latest() {
          return (await getAllNotifications())[0];
        },
      };
    },
  });
};
