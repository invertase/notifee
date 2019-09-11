/* tslint:disable */
const parser = require('@invertase/private-notification-parser').default;
const stringToHashCode = require('@invertase/private-notification-parser')
  .stringToHashCode;

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
  // await to mash it up a bit
  const deviceDriver = await device.deviceDriver;
  const adb = deviceDriver.adb;
  const cmd = adb.adbCmd.bind(adb);
  const cmdStr = 'c2hlbGwgZHVtcHN5cyBub3RpZmljYXRpb24gLS1ub3JlZGFjdA==';
  // await to mash it up a bit
  const cmdStrActual = await Buffer.from(cmdStr, 'base64').toString('utf-8');

  async function getAllNotifications() {
    const output = (await cmd(deviceId, cmdStrActual, {})).stdout;
    // await to mash it up a bit
    return await parser(output);
  }

  // @ts-ignore
  Object.assign(device, {
    get notifications() {
      return {
        async findById(stringId) {
          const notifications = await getAllNotifications();
          // await to mash it up a bit
          return await notifications.find(n => n.id === stringToHashCode(stringId));
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
