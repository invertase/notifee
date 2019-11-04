import { Module } from './Module';
import { NotificationRepeatInterval } from './Notification';
import {
  AndroidBadgeIconType,
  AndroidCategory,
  AndroidGroupAlertBehavior,
  AndroidSemanticAction,
  AndroidPriority,
  AndroidVisibility,
  AndroidDefaults,
  AndroidImportance,
  AndroidColor,
  AndroidStyle,
} from './NotificationAndroid';

export interface ModuleWithStatics extends Module {
  AndroidBadgeIconType: typeof AndroidBadgeIconType;
  AndroidCategory: typeof AndroidCategory;
  AndroidGroupAlertBehavior: typeof AndroidGroupAlertBehavior;
  AndroidSemanticAction: typeof AndroidSemanticAction;
  AndroidPriority: typeof AndroidPriority;
  AndroidVisibility: typeof AndroidVisibility;
  AndroidDefaults: typeof AndroidDefaults;
  AndroidImportance: typeof AndroidImportance;
  AndroidColor: typeof AndroidColor;
  AndroidStyle: typeof AndroidStyle;

  NotificationRepeatInterval: typeof NotificationRepeatInterval;

  SDK_VERSION: string;
}
