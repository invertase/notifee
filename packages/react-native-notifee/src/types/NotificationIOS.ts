/* eslint-disable @typescript-eslint/interface-name-prefix */
/*
 * Copyright (c) 2016-present Invertase Limited
 */

/**
 * TODO
 *
 * @platform ios
 */
export interface NotificationIOS {
  /**
   * @platform ios iOS 10+
   */
  attachments?: IOSAttachment[];

  badgeCount?: number;

  categories?: string[];

  groupId?: string;

  groupMessage?: string;

  groupCount?: number;

  launchImage?: string;

  sound?: string;
}

export interface IOSPermissions {
  alert?: boolean; // true
  badge?: boolean; // true
  sound?: boolean; // true
  carPlay?: boolean; // true
  settings?: boolean; // true
  provisional?: boolean; // false
  announcement?: boolean; // true
}

export interface IOSCategory {
  id: string;
  actions: IOSAction[];
}

export interface IOSAction {
  id: string;
  title: string;
  input?: true | IOSInput;
  options?: IOSActionOptions;
}

export interface IOSInput {
  /**
   * Overrides the default button text "Send", next to the input box.
   */
  button?: string;

  placeholder?: string;
}

export interface IOSActionOptions {
  /**
   * Makes text red
   */
  destructive?: boolean; // false

  /**
   * Launch the app into the foreground if true
   */
  launchApp?: boolean; // false

  /**
   * If true, requires the device to be unlocked to show the action
   */
  authentication?: boolean; // false
}

/**
 * TODO
 *
 * @platform ios
 */
export interface IOSAttachment {
  identifier: string;
  url: string;
  options?: IOSAttachmentOptions;
}

/**
 * TODO
 *
 * @platform ios
 */
export interface IOSAttachmentOptions {
  typeHint: string;
  thumbnailHidden?: boolean;
  thumbnailClippingRect?: object;
  thumbnailTime: number;
}
