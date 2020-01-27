/*
 * Copyright (c) 2016-present Invertase Limited
 */

export default function noop(): void {
  return;
}

// import { hasOwnProperty, isBoolean, isNumber, isObject } from '../utils';
// import { NotificationRepeatInterval, NotificationSchedule } from '../../types/Notification';
//
// export default function validateSchedule(schedule: NotificationSchedule): NotificationSchedule {
//   if (!isObject(schedule)) {
//     throw new Error("'schedule' expected an object value.");
//   }
//
//   if (!isNumber(schedule.fireDate)) {
//     throw new Error("'schedule.fireDate' expected a number value.");
//   }
//
//   const now = Date.now();
//
//   if (schedule.fireDate <= now) {
//     throw new Error("'schedule.fireDate' date must be in the future.");
//   }
//
//   const out: NotificationSchedule = {
//     fireDate: schedule.fireDate,
//     exact: false,
//   };
//
//   if (hasOwnProperty(schedule, 'exact')) {
//     if (!isBoolean(schedule.exact)) {
//       throw new Error("'schedule.exact' expected a boolean value.");
//     }
//
//     out.exact = !!schedule.exact;
//   }
//
//   if (hasOwnProperty(schedule, 'repeatInterval') && schedule.repeatInterval != undefined) {
//     if (!Object.values(NotificationRepeatInterval).includes(schedule.repeatInterval)) {
//       throw new Error("'schedule.repeatInterval' expected a valid NotificationRepeatInterval.");
//     }
//
//     out.repeatInterval = schedule.repeatInterval;
//   }
//
//   return out;
// }
