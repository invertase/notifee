/*
 * Copyright (c) 2016-present Invertase Limited
 */
import {
  IOSIntentIdentifier,
  IOSNotificationCategory,
  IOSNotificationCategoryAction,
} from '../types/NotificationIOS';
import { objectHasProperty, isArray, isObject, isString, isBoolean } from '../utils';
import validateIOSCategoryAction from './validateIOSCategoryAction';

export default function validateIOSCategory(
  category: IOSNotificationCategory,
): IOSNotificationCategory {
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

  const out: IOSNotificationCategory = {
    id: category.id,
    allowInCarPlay: false,
    allowAnnouncement: false,
    hiddenPreviewsShowTitle: false,
    hiddenPreviewsShowSubtitle: false,
  };

  /**
   * summaryFormat
   */
  if (objectHasProperty(category, 'summaryFormat')) {
    if (!isString(category.summaryFormat)) {
      throw new Error("'category.summaryFormat' expected a string value.");
    }

    out.summaryFormat = category.summaryFormat;
  }

  /**
   * allowInCarPlay
   */
  if (objectHasProperty(category, 'allowInCarPlay')) {
    if (!isBoolean(category.allowInCarPlay)) {
      throw new Error("'category.allowInCarPlay' expected a boolean value.");
    }

    out.allowInCarPlay = category.allowInCarPlay;
  }

  /**
   * allowAnnouncement
   */
  if (objectHasProperty(category, 'allowAnnouncement')) {
    if (!isBoolean(category.allowAnnouncement)) {
      throw new Error("'category.allowAnnouncement' expected a boolean value.");
    }

    out.allowAnnouncement = category.allowAnnouncement;
  }

  /**
   * hiddenPreviewsShowTitle
   */
  if (objectHasProperty(category, 'hiddenPreviewsShowTitle')) {
    if (!isBoolean(category.hiddenPreviewsShowTitle)) {
      throw new Error("'category.hiddenPreviewsShowTitle' expected a boolean value.");
    }

    out.hiddenPreviewsShowTitle = category.hiddenPreviewsShowTitle;
  }

  /**
   * hiddenPreviewsShowSubtitle
   */
  if (objectHasProperty(category, 'hiddenPreviewsShowSubtitle')) {
    if (!isBoolean(category.hiddenPreviewsShowSubtitle)) {
      throw new Error("'category.hiddenPreviewsShowSubtitle' expected a boolean value.");
    }

    out.hiddenPreviewsShowSubtitle = category.hiddenPreviewsShowSubtitle;
  }

  /**
   * summaryFormat
   */
  if (objectHasProperty(category, 'hiddenPreviewsBodyPlaceholder')) {
    if (!isString(category.hiddenPreviewsBodyPlaceholder)) {
      throw new Error("'category.hiddenPreviewsBodyPlaceholder' expected a string value.");
    }

    out.hiddenPreviewsBodyPlaceholder = category.hiddenPreviewsBodyPlaceholder;
  }

  /**
   * intentIdentifiers
   */
  if (objectHasProperty(category, 'intentIdentifiers')) {
    if (!isArray(category.intentIdentifiers)) {
      throw new Error("'category.intentIdentifiers' expected an array value.");
    }

    const identifiers = Object.values(IOSIntentIdentifier);

    for (let i = 0; i < category.intentIdentifiers.length; i++) {
      const intentIdentifier = category.intentIdentifiers[i];

      if (!identifiers.includes(intentIdentifier)) {
        throw new Error(
          `'category.intentIdentifiers' unexpected intentIdentifier "${intentIdentifier}" at array index "${i}".`,
        );
      }
    }

    out.intentIdentifiers = category.intentIdentifiers;
  }

  /**
   * actions
   */
  if (objectHasProperty(category, 'actions')) {
    if (!isArray(category.actions)) {
      throw new Error("'category.actions' expected an array value.");
    }

    const actions: IOSNotificationCategoryAction[] = [];

    for (let i = 0; i < category.actions.length; i++) {
      try {
        actions[i] = validateIOSCategoryAction(category.actions[i]);
      } catch (e) {
        throw new Error(`'category.actions' invalid action at index "${i}". ${e}`);
      }
    }

    out.actions = actions;
  }

  return out;
}
