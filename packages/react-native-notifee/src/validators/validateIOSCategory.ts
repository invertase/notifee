/*
 * Copyright (c) 2016-present Invertase Limited
 */
import { IOSCategory } from '../types/NotificationIOS';
import { isArray, isObject, isString } from '../utils';
import validateIOSCategoryAction from './validateIOSCategoryAction';

export default function validateIOSCategory(category: IOSCategory): IOSCategory {
  if (!isObject(category)) {
    throw new Error("'category' expected an object value.");
  }

  /**
   * id
   */
  if (!isString(category.id)) {
    throw new Error("'category.id' expected a string value.");
  }

  // empty check
  if (!category.id) {
    throw new Error("'category.id' expected a valid string id.");
  }

  if (!isArray(category.actions) || category.actions.length === 0) {
    throw new Error("'category.actions' expected an array of actions.");
  }

  for (let i = 0; i < category.actions.length; i++) {
    try {
      validateIOSCategoryAction(category.actions[i]);
    } catch (e) {
      throw new Error(`'category.actions' invalid action at index "${i}". ${e}`);
    }
  }

  return {
    id: category.id,
    actions: category.actions,
  } as IOSCategory;
}
