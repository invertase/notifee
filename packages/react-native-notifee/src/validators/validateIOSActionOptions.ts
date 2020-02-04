import { IOSActionOptions } from '..';
import { hasOwnProperty, isBoolean } from '../utils';

export default function validateIOSActionOptions(options: IOSActionOptions): IOSActionOptions {
  const out: IOSActionOptions = {
    destructive: false,
    launchApp: false,
    authentication: false,
  };

  if (hasOwnProperty(options, 'destructive')) {
    if (!isBoolean(options.destructive)) {
      throw new Error("'destructive' expected a boolean value.");
    }

    out.destructive = options.destructive;
  }

  if (hasOwnProperty(options, 'launchApp')) {
    if (!isBoolean(options.launchApp)) {
      throw new Error("'launchApp' expected a boolean value.");
    }

    out.launchApp = options.launchApp;
  }

  if (hasOwnProperty(options, 'authentication')) {
    if (!isBoolean(options.authentication)) {
      throw new Error("'authentication' expected a boolean value.");
    }

    out.authentication = options.authentication;
  }

  return out;
}
