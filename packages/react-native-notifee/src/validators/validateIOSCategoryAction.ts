import { IOSAction } from '..';
import { hasOwnProperty, isBoolean, isObject, isString, isUndefined } from '../utils';
import validateIOSInput from './validateIOSInput';
import validateIOSActionOptions from './validateIOSActionOptions';

export default function validateIOSCategoryAction(action: IOSAction): IOSAction {
  if (!isObject(action)) {
    throw new Error('"action" expected an object value');
  }

  if (!isString(action.id) || action.id.length === 0) {
    throw new Error('"action.id" expected a valid string value.');
  }

  if (!isString(action.title) || action.title.length === 0) {
    throw new Error('"action.title" expected a valid string value.');
  }

  const out: IOSAction = {
    id: action.id,
    title: action.title,
  };

  if (hasOwnProperty(action, 'input') && !isUndefined(action.input)) {
    if (isBoolean(action.input) && action.input) {
      out.input = validateIOSInput();
    } else {
      try {
        out.input = validateIOSInput(action.input);
      } catch (e) {
        throw new Error(`'action' ${e.message}.`);
      }
    }
  }

  if (hasOwnProperty(action, 'options') && !isUndefined(action.options)) {
    if (!isObject(action.options)) {
      throw new Error('"action.options" expected an object value.');
    }

    try {
      out.options = validateIOSActionOptions(action.options);
    } catch (e) {
      throw new Error(`"action.options" ${e.message}`);
    }
  }

  return out;
}
