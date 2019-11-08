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
   * @ios iOS 10+
   */
  attachment?: IOSAttachment;

  /**
   * @ios iOS 9 Only
   */
  alertAction?: string;

  badge?: number;

  // todo ios categories?
  category?: string;

  /**
   * @ios iOS 9 Only
   */
  hasAction?: boolean;

  launchImage?: string;

  /**
   * @ios iOS 10+
   */
  threadIdentifier?: string;

  // todo
  complete?: Function;
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
