/**
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

#import "NotifeeCoreUtil.h"
#include <CoreGraphics/CGGeometry.h>
#import <Intents/INIntentIdentifiers.h>
#import <UIKit/UIKit.h>
#import "NotifeeCore+NSURLSession.h"

@implementation NotifeeCoreUtil

+ (NSNumber *)numberForUNNotificationSetting:(UNNotificationSetting)setting {
  NSNumber *asNumber = @-1;
  if (setting == UNNotificationSettingNotSupported) {
    asNumber = @-1;
  } else if (setting == UNNotificationSettingDisabled) {
    asNumber = @0;
  } else if (setting == UNNotificationSettingEnabled) {
    asNumber = @1;
  }
  return asNumber;
}

+ (NSMutableArray<NSDictionary *> *)notificationActionsToDictionaryArray:
    (NSArray<UNNotificationAction *> *)notificationActions {
  NSMutableArray<NSDictionary *> *notificationActionDicts = [[NSMutableArray alloc] init];

  for (UNNotificationAction *notificationAction in notificationActions) {
    NSMutableDictionary *notificationActionDict = [NSMutableDictionary dictionary];

    notificationActionDict[@"id"] = notificationAction.identifier;
    notificationActionDict[@"title"] = notificationAction.title;

    notificationActionDict[@"destructive"] =
        @(((notificationAction.options & UNNotificationActionOptionDestructive) != 0));
    notificationActionDict[@"foreground"] =
        @(((notificationAction.options & UNNotificationActionOptionForeground) != 0));
    notificationActionDict[@"authenticationRequired"] =
        @(((notificationAction.options & UNNotificationActionOptionAuthenticationRequired) != 0));

    if ([[notificationAction class] isKindOfClass:[UNTextInputNotificationAction class]]) {
      UNTextInputNotificationAction *notificationInputAction =
          (UNTextInputNotificationAction *)notificationAction;
      if ([notificationInputAction textInputButtonTitle] == nil &&
          [notificationInputAction textInputPlaceholder] == nil) {
        notificationActionDict[@"input"] = @(YES);
      } else {
        NSMutableDictionary *inputDict = [NSMutableDictionary dictionary];
        inputDict[@"buttonText"] = [notificationInputAction textInputButtonTitle];
        inputDict[@"placeholderText"] = [notificationInputAction textInputPlaceholder];
        notificationActionDict[@"input"] = inputDict;
      }
    } else {
      notificationActionDict[@"input"] = @(NO);
    }

    [notificationActionDicts addObject:notificationActionDict];
  }

  return notificationActionDicts;
}

+ (NSMutableArray<UNNotificationAction *> *)notificationActionsFromDictionaryArray:
    (NSArray<NSDictionary *> *)actionDictionaries {
  NSMutableArray<UNNotificationAction *> *notificationActions = [[NSMutableArray alloc] init];

  for (NSDictionary *actionDictionary in actionDictionaries) {
    UNNotificationAction *notificationAction;

    NSString *id = actionDictionary[@"id"];
    NSString *title = actionDictionary[@"title"];

    UNNotificationActionOptions options = 0;

    if ([actionDictionary[@"destructive"] isEqual:@(YES)]) {
      options |= UNNotificationActionOptionDestructive;
    }

    if ([actionDictionary[@"foreground"] isEqual:@(YES)]) {
      options |= UNNotificationActionOptionForeground;
    }

    if ([actionDictionary[@"authenticationRequired"] isEqual:@(YES)]) {
      options |= UNNotificationActionOptionAuthenticationRequired;
    }

    if (actionDictionary[@"input"] != nil &&
        [actionDictionary[@"input"] isKindOfClass:NSDictionary.class]) {
      NSDictionary *inputDictionary = actionDictionary[@"input"];
      NSString *buttonText = inputDictionary[@"buttonText"];
      NSString *placeholderText = inputDictionary[@"placeholderText"];
      notificationAction = [UNTextInputNotificationAction actionWithIdentifier:id
                                                                         title:title
                                                                       options:options
                                                          textInputButtonTitle:buttonText
                                                          textInputPlaceholder:placeholderText];
    } else if (actionDictionary[@"input"] != nil) {  // BOOL
      notificationAction = [UNTextInputNotificationAction actionWithIdentifier:id
                                                                         title:title
                                                                       options:options];
    } else {
      notificationAction = [UNNotificationAction actionWithIdentifier:id
                                                                title:title
                                                              options:options];
    }

    [notificationActions addObject:notificationAction];
  }

  return notificationActions;
}

/**
 * Builds the notification attachments
 * If no attachments are resolved, an empty array will be returned
 *
 * @return NSArray<UNNotificationAttachment *> *
 */
+ (NSMutableArray<UNNotificationAttachment *> *)notificationAttachmentsFromDictionaryArray:
    (NSArray<NSDictionary *> *)attachmentDictionaries {
  NSMutableArray<UNNotificationAttachment *> *attachments = [[NSMutableArray alloc] init];

  for (NSDictionary *attachmentDict in attachmentDictionaries) {
    UNNotificationAttachment *attachment = [self attachmentFromDictionary:attachmentDict];
    if (attachment) {
      [attachments addObject:attachment];
    }
  }
  return attachments;
}

/**
 * Returns an UNNotificationAttachment from a file path or local resource
 *
 * @return UNNotificationAttachment or null if the attachment fails to resolve
 */
+ (UNNotificationAttachment *)attachmentFromDictionary:(NSDictionary *)attachmentDict {
  NSString *identifier = attachmentDict[@"id"];
  NSString *urlString = attachmentDict[@"url"];

  NSURL *url = [self getURLFromString:urlString];

  if (url) {
    NSError *error;
    UNNotificationAttachment *attachment = [UNNotificationAttachment
        attachmentWithIdentifier:identifier
                             URL:url
                         options:[self attachmentOptionsFromDictionary:attachmentDict]
                           error:&error];
    if (error != nil) {
      NSLog(@"NotifeeCore: An error occurred whilst trying to resolve an "
            @"attachment %@: %@",
            attachmentDict, error);
      return nil;
    } else if (attachment == nil) {
      NSLog(@"NotifeeCore: Failed resolving an attachment %@: data at URL is "
            @"not a supported type.",
            attachmentDict);
    }

    return attachment;
  }

  NSLog(@"NotifeeCore: Unable to resolve url for attachment: %@", attachmentDict);
  return nil;
}

/*
 * get the URL from a string
 *
 * @param urlString NSString
 * @return NSURL
 */
+ (NSURL *)getURLFromString:(NSString *)urlString {
  NSURL *url;

  if ([urlString hasPrefix:@"http://"] || [urlString hasPrefix:@"https://"]) {
    // handle remote url by attempting to download attachement synchronously
    url = [self downloadMediaSynchronously:urlString];
  } else if ([urlString hasPrefix:@"/"]) {
    // handle absolute file path
    url = [NSURL fileURLWithPath:urlString];
  } else {
    // try to resolve local resource
    url = [[NSBundle mainBundle] URLForResource:urlString withExtension:nil];
  }

  return url;
}

/*
 * Downloads a media file, syncronously to the NSCachesDirectory
 *
 * @param urlString NSString
 * @return NSURL or nil
 */
+ (NSURL *)downloadMediaSynchronously:(NSString *)urlString {
  NSURL *url = [NSURL URLWithString:urlString];

  NSString *newCachedFileName = [self generateCachedFileName:15];

  NSArray *localDirectoryPaths =
      NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES);
  NSString *tempDestination =
      [localDirectoryPaths[0] stringByAppendingPathComponent:newCachedFileName];

  @try {
    NSError *error;

    // Apple gives us a suggested file name which can be used to infer the file
    // extension
    NSString *suggestedFilename = [NotifeeCoreNSURLSession downloadItemAtURL:url
                                                                      toFile:tempDestination
                                                                       error:&error];

    if (error) {
      NSLog(@"NotifeeCore: Failed to download attachement with URL %@: %@", urlString, error);
      return nil;
    }

    // Rename the recently downloaded file to include its file extension
    NSFileManager *fileManager = [NSFileManager defaultManager];

    NSString *fileExtension = [NSString stringWithFormat:@".%@", [suggestedFilename pathExtension]];

    if (!fileExtension || [fileExtension isEqualToString:@""]) {
      NSLog(@"NotifeeCore: Failed to determine file extension for attachment "
            @"with URL %@: %@",
            urlString, error);
      return nil;
    }

    NSString *localFilePath =
        [localDirectoryPaths[0] stringByAppendingPathComponent:newCachedFileName];

    localFilePath = [localFilePath stringByAppendingString:fileExtension];
    NSURL *localURL = [NSURL fileURLWithPath:localFilePath];

    [fileManager moveItemAtPath:tempDestination toPath:localFilePath error:&error];

    // Returns the local cached path to attachment
    return localURL;
  } @catch (NSException *exception) {
    NSLog(@"NotifeeCore: An exception occured while attempting to download "
          @"attachment with URL %@: "
          @"%@",
          urlString, exception);
    return nil;
  }
}

/**
 * Returns a NSDictionary representation of options related to the attached file
 *
 * @param optionsDict NSDictionary
 */
+ (NSDictionary *)attachmentOptionsFromDictionary:(NSDictionary *)optionsDict {
  NSMutableDictionary *options = [NSMutableDictionary new];
  if (optionsDict[@"typeHint"] != nil) {
    options[UNNotificationAttachmentOptionsTypeHintKey] = optionsDict[@"typeHint"];
  }

  if (optionsDict[@"thumbnailHidden"] != nil) {
    options[UNNotificationAttachmentOptionsThumbnailHiddenKey] = optionsDict[@"thumbnailHidden"];
  }

  if (optionsDict[@"thumbnailClippingRect"] != nil) {
    NSDictionary *area = optionsDict[@"thumbnailClippingRect"];
    NSNumber *x = area[@"x"];
    NSNumber *y = area[@"y"];
    NSNumber *width = area[@"width"];
    NSNumber *height = area[@"height"];
    CGRect areaRect =
        CGRectMake([x doubleValue], [y doubleValue], [width doubleValue], [height doubleValue]);
    options[UNNotificationAttachmentOptionsThumbnailClippingRectKey] =
        (__bridge id _Nullable)(CGRectCreateDictionaryRepresentation(areaRect));
  }

  if (optionsDict[@"thumbnailTime"] != nil) {
    options[UNNotificationAttachmentOptionsThumbnailTimeKey] = optionsDict[@"thumbnailTime"];
  }

  return options;
}

/**
 * Returns an UNNotificationTrigger from NSDictionary representing a trigger
 *
 * @param triggerDict NSDictionary
 * @return UNNotificationTrigger or null if trigger type is not recognised
 */
+ (UNNotificationTrigger *)triggerFromDictionary:(NSDictionary *)triggerDict {
  UNNotificationTrigger *trigger;
  NSInteger triggerType = [triggerDict[@"type"] integerValue];

  if (triggerType == NotifeeCoreTriggerTypeTimestamp) {
    trigger = [self timestampTriggerFromDictionary:triggerDict];
  } else if (triggerType == NotifeeCoreTriggerTypeInterval) {
    trigger = [self intervalTriggerFromDictionary:triggerDict];
  } else {
    NSLog(@"NotifeeCore: Failed to parse trigger with unknown trigger type: %ld",
          (long)triggerType);
  }

  return trigger;
}

/**
 * Returns an UNNotificationTrigger from NSDictionary representing a
 * TimestampTrigger
 *
 * @param triggerDict NSDictionary
 */
+ (UNNotificationTrigger *)timestampTriggerFromDictionary:(NSDictionary *)triggerDict {
  UNNotificationTrigger *trigger;
  Boolean repeats = false;
  NSCalendarUnit calendarUnit;

  NSInteger repeatFrequency = [triggerDict[@"repeatFrequency"] integerValue];
  NSNumber *timestampMillis = triggerDict[@"timestamp"];

  // convert timestamp to a NSDate
  NSInteger timestamp = [timestampMillis doubleValue] / 1000;
  NSDate *date = [NSDate dateWithTimeIntervalSince1970:timestamp];

  if (repeatFrequency != -1) {
    repeats = true;

    if (repeatFrequency == NotifeeCoreRepeatFrequencyHourly) {
      // match by minute and second
      calendarUnit = NSCalendarUnitMinute | NSCalendarUnitSecond;
    } else if (repeatFrequency == NotifeeCoreRepeatFrequencyDaily) {
      // match by hour, minute and second
      calendarUnit = NSCalendarUnitHour | NSCalendarUnitMinute | NSCalendarUnitSecond;
    } else if (repeatFrequency == NotifeeCoreRepeatFrequencyWeekly) {
      // match by day, hour, minute, and second
      calendarUnit =
          NSCalendarUnitWeekday | NSCalendarUnitHour | NSCalendarUnitMinute | NSCalendarUnitSecond;
    } else {
      NSLog(@"NotifeeCore: Failed to parse TimestampTrigger with unknown "
            @"repeatFrequency: %ld",
            (long)repeatFrequency);

      return nil;
    }
  } else {
    // Needs to match exactly to the second
    calendarUnit = NSCalendarUnitYear | NSCalendarUnitMonth | NSCalendarUnitDay |
                   NSCalendarUnitHour | NSCalendarUnitMinute | NSCalendarUnitSecond;
  }

  NSDateComponents *components = [[NSCalendar currentCalendar] components:calendarUnit
                                                                 fromDate:date];
  trigger = [UNCalendarNotificationTrigger triggerWithDateMatchingComponents:components
                                                                     repeats:repeats];

  return trigger;
}
/**
 * Returns an UNNotificationTrigger from NSDictionary representing an
 * IntervalTrigger
 *
 * @param triggerDict NSDictionary
 */
+ (UNNotificationTrigger *)intervalTriggerFromDictionary:(NSDictionary *)triggerDict {
  double intervalNumber = [triggerDict[@"interval"] doubleValue];
  NSString *timeUnit = triggerDict[@"timeUnit"];

  NSTimeInterval intervalInSeconds = 0;

  if ([timeUnit isEqualToString:kNotifeeCoreTimeUnitSeconds]) {
    intervalInSeconds = intervalNumber;
  } else if ([timeUnit isEqualToString:kNotifeeCoreTimeUnitMinutes]) {
    // multiply by the number of seconds in 1 minute
    intervalInSeconds = intervalNumber * 60;
  } else if ([timeUnit isEqualToString:kNotifeeCoreTimeUnitHours]) {
    // multiply by the number of seconds in 1 hour
    intervalInSeconds = intervalNumber * 3600;
  } else if ([timeUnit isEqualToString:kNotifeeCoreTimeUnitDays]) {
    // multiply by the number of seconds in 1 day
    intervalInSeconds = intervalNumber * 86400;
  } else {
    NSLog(@"NotifeeCore: Failed to parse IntervalTrigger with unknown "
          @"timeUnit: %@",
          timeUnit);
    return nil;
  }

  return [UNTimeIntervalNotificationTrigger triggerWithTimeInterval:intervalInSeconds repeats:true];
}

+ (NSMutableArray<NSNumber *> *)intentIdentifiersFromStringArray:
    (NSArray<NSString *> *)identifiers {
  NSMutableArray<NSNumber *> *intentIdentifiers = [[NSMutableArray alloc] init];

  for (NSString *identifier in identifiers) {
    if ([identifier isEqualToString:INStartAudioCallIntentIdentifier]) {
      // IOSIntentIdentifier.START_AUDIO_CALL
      [intentIdentifiers addObject:@0];
    } else if ([identifier isEqualToString:INStartVideoCallIntentIdentifier]) {
      // IOSIntentIdentifier.START_VIDEO_CALL
      [intentIdentifiers addObject:@1];
    } else if ([identifier isEqualToString:INSearchCallHistoryIntentIdentifier]) {
      // IOSIntentIdentifier.SEARCH_CALL_HISTORY
      [intentIdentifiers addObject:@2];
    } else if ([identifier isEqualToString:INSetAudioSourceInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_AUDIO_SOURCE_IN_CAR
      [intentIdentifiers addObject:@3];
    } else if ([identifier isEqualToString:INSetClimateSettingsInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_CLIMATE_SETTINGS_IN_CAR
      [intentIdentifiers addObject:@4];
    } else if ([identifier isEqualToString:INSetDefrosterSettingsInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_DEFROSTER_SETTINGS_IN_CAR
      [intentIdentifiers addObject:@5];
    } else if ([identifier isEqualToString:INSetSeatSettingsInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_SEAT_SETTINGS_IN_CAR
      [intentIdentifiers addObject:@6];
    } else if ([identifier isEqualToString:INSetProfileInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_PROFILE_IN_CAR
      [intentIdentifiers addObject:@7];
    } else if ([identifier isEqualToString:INSaveProfileInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SAVE_PROFILE_IN_CAR
      [intentIdentifiers addObject:@8];
    } else if ([identifier isEqualToString:INStartWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.START_WORKOUT
      [intentIdentifiers addObject:@9];
    } else if ([identifier isEqualToString:INPauseWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.PAUSE_WORKOUT
      [intentIdentifiers addObject:@10];
    } else if ([identifier isEqualToString:INEndWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.END_WORKOUT
      [intentIdentifiers addObject:@11];
    } else if ([identifier isEqualToString:INCancelWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.CANCEL_WORKOUT
      [intentIdentifiers addObject:@12];
    } else if ([identifier isEqualToString:INResumeWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.RESUME_WORKOUT
      [intentIdentifiers addObject:@13];
    } else if ([identifier isEqualToString:INSetRadioStationIntentIdentifier]) {
      // IOSIntentIdentifier.SET_RADIO_STATION
      [intentIdentifiers addObject:@14];
    } else if ([identifier isEqualToString:INSendMessageIntentIdentifier]) {
      // IOSIntentIdentifier.SEND_MESSAGE
      [intentIdentifiers addObject:@15];
    } else if ([identifier isEqualToString:INSearchForMessagesIntentIdentifier]) {
      // IOSIntentIdentifier.SEARCH_FOR_MESSAGES
      [intentIdentifiers addObject:@16];
    } else if ([identifier isEqualToString:INSetMessageAttributeIntentIdentifier]) {
      // IOSIntentIdentifier.SET_MESSAGE_ATTRIBUTE
      [intentIdentifiers addObject:@17];
    } else if ([identifier isEqualToString:INSendPaymentIntentIdentifier]) {
      // IOSIntentIdentifier.SEND_PAYMENT
      [intentIdentifiers addObject:@18];
    } else if ([identifier isEqualToString:INRequestPaymentIntentIdentifier]) {
      // IOSIntentIdentifier.REQUEST_PAYMENT
      [intentIdentifiers addObject:@19];
    } else if ([identifier isEqualToString:INSearchForPhotosIntentIdentifier]) {
      // IOSIntentIdentifier.SEARCH_FOR_PHOTOS
      [intentIdentifiers addObject:@20];
    } else if ([identifier isEqualToString:INStartPhotoPlaybackIntentIdentifier]) {
      // IOSIntentIdentifier.START_PHOTO_PLAYBACK
      [intentIdentifiers addObject:@21];
    } else if ([identifier isEqualToString:INListRideOptionsIntentIdentifier]) {
      // IOSIntentIdentifier.LIST_RIDE_OPTIONS
      [intentIdentifiers addObject:@22];
    } else if ([identifier isEqualToString:INRequestRideIntentIdentifier]) {
      // IOSIntentIdentifier.REQUEST_RIDE
      [intentIdentifiers addObject:@23];
    } else if ([identifier isEqualToString:INGetRideStatusIntentIdentifier]) {
      // IOSIntentIdentifier.GET_RIDE_STATUS
      [intentIdentifiers addObject:@24];
    }
  }

  return intentIdentifiers;
}

+ (NSMutableArray<NSString *> *)intentIdentifiersFromNumberArray:
    (NSArray<NSNumber *> *)identifiers {
  NSMutableArray<NSString *> *intentIdentifiers = [[NSMutableArray alloc] init];

  for (NSNumber *identifier in identifiers) {
    if ([identifier isEqualToNumber:@0]) {
      // IOSIntentIdentifier.START_AUDIO_CALL
      [intentIdentifiers addObject:INStartAudioCallIntentIdentifier];
    } else if ([identifier isEqualToNumber:@1]) {
      // IOSIntentIdentifier.START_VIDEO_CALL
      [intentIdentifiers addObject:INStartVideoCallIntentIdentifier];
    } else if ([identifier isEqualToNumber:@2]) {
      // IOSIntentIdentifier.SEARCH_CALL_HISTORY
      [intentIdentifiers addObject:INSearchCallHistoryIntentIdentifier];
    } else if ([identifier isEqualToNumber:@3]) {
      // IOSIntentIdentifier.SET_AUDIO_SOURCE_IN_CAR
      [intentIdentifiers addObject:INSetAudioSourceInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@4]) {
      // IOSIntentIdentifier.SET_CLIMATE_SETTINGS_IN_CAR
      [intentIdentifiers addObject:INSetClimateSettingsInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@5]) {
      // IOSIntentIdentifier.SET_DEFROSTER_SETTINGS_IN_CAR
      [intentIdentifiers addObject:INSetDefrosterSettingsInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@6]) {
      // IOSIntentIdentifier.SET_SEAT_SETTINGS_IN_CAR
      [intentIdentifiers addObject:INSetSeatSettingsInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@7]) {
      // IOSIntentIdentifier.SET_PROFILE_IN_CAR
      [intentIdentifiers addObject:INSetProfileInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@8]) {
      // IOSIntentIdentifier.SAVE_PROFILE_IN_CAR
      [intentIdentifiers addObject:INSaveProfileInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@9]) {
      // IOSIntentIdentifier.START_WORKOUT
      [intentIdentifiers addObject:INStartWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@10]) {
      // IOSIntentIdentifier.PAUSE_WORKOUT
      [intentIdentifiers addObject:INPauseWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@11]) {
      // IntentIdentifier.END_WORKOUT
      [intentIdentifiers addObject:INEndWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@12]) {
      // IOSIntentIdentifier.CANCEL_WORKOUT
      [intentIdentifiers addObject:INCancelWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@13]) {
      // IOSIntentIdentifier.RESUME_WORKOUT
      [intentIdentifiers addObject:INResumeWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@14]) {
      // IOSIntentIdentifier.SET_RADIO_STATION
      [intentIdentifiers addObject:INSetRadioStationIntentIdentifier];
    } else if ([identifier isEqualToNumber:@15]) {
      // IOSIntentIdentifier.SEND_MESSAGE
      [intentIdentifiers addObject:INSendMessageIntentIdentifier];
    } else if ([identifier isEqualToNumber:@16]) {
      // IOSIntentIdentifier.SEARCH_FOR_MESSAGES
      [intentIdentifiers addObject:INSearchForMessagesIntentIdentifier];
    } else if ([identifier isEqualToNumber:@17]) {
      // IOSIntentIdentifier.SET_MESSAGE_ATTRIBUTE
      [intentIdentifiers addObject:INSetMessageAttributeIntentIdentifier];
    } else if ([identifier isEqualToNumber:@18]) {
      // IOSIntentIdentifier.SEND_PAYMENT
      [intentIdentifiers addObject:INSendPaymentIntentIdentifier];
    } else if ([identifier isEqualToNumber:@19]) {
      // IOSIntentIdentifier.REQUEST_PAYMENT
      [intentIdentifiers addObject:INRequestPaymentIntentIdentifier];
    } else if ([identifier isEqualToNumber:@20]) {
      // IOSIntentIdentifier.SEARCH_FOR_PHOTOS
      [intentIdentifiers addObject:INSearchForPhotosIntentIdentifier];
    } else if ([identifier isEqualToNumber:@21]) {
      // IOSIntentIdentifier.START_PHOTO_PLAYBACK
      [intentIdentifiers addObject:INStartPhotoPlaybackIntentIdentifier];
    } else if ([identifier isEqualToNumber:@22]) {
      // IOSIntentIdentifier.LIST_RIDE_OPTIONS
      [intentIdentifiers addObject:INListRideOptionsIntentIdentifier];
    } else if ([identifier isEqualToNumber:@23]) {
      // IOSIntentIdentifier.REQUEST_RIDE
      [intentIdentifiers addObject:INRequestRideIntentIdentifier];
    } else if ([identifier isEqualToNumber:@24]) {
      // IOSIntentIdentifier.GET_RIDE_STATUS
      [intentIdentifiers addObject:INGetRideStatusIntentIdentifier];
    }
  }

  return intentIdentifiers;
}

/**
 * Returns timestamp in millisecons
 *
 * @param date NSDate
 */
+ (NSNumber *)convertToTimestamp:(NSDate *)date {
  return [NSNumber numberWithDouble:([date timeIntervalSince1970] * 1000)];
}

/**
 * Parse UNNotificationRequest to NSDictionary
 *
 * @param request UNNotificationRequest
 */
+ (NSMutableDictionary *)parseUNNotificationRequest:(UNNotificationRequest *)request {
  NSMutableDictionary *dictionary = [NSMutableDictionary dictionary];

  dictionary = [self parseUNNotificationContent:request.content];
  dictionary[@"id"] = request.identifier;

  NSDictionary *userInfo = request.content.userInfo;

  // Check for remote details
  if ([request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
    NSMutableDictionary *remote = [NSMutableDictionary dictionary];

    remote[@"messageId"] = userInfo[@"gcm.message_id"];
    remote[@"senderId"] = userInfo[@"google.c.sender.id"];

    if (userInfo[@"aps"] != nil) {
      remote[@"mutableContent"] = userInfo[@"aps"][@"mutable-content"];
      remote[@"contentAvailable"] = userInfo[@"aps"][@"content-available"];
    }

    dictionary[@"remote"] = remote;
  }

  dictionary[@"data"] = [self parseDataFromUserInfo:userInfo];

  return dictionary;
}

+ (NSMutableDictionary *)parseDataFromUserInfo:(NSDictionary *)userInfo {
  NSMutableDictionary *data = [[NSMutableDictionary alloc] init];
  for (id key in userInfo) {
    // build data dict from remaining keys but skip keys that shouldn't be included in data
    if ([key isEqualToString:@"aps"] || [key hasPrefix:@"gcm."] || [key hasPrefix:@"google."] ||
        // notifee or notifee_options
        [key hasPrefix:@"notifee"] ||
        // fcm_options
        [key hasPrefix:@"fcm"]) {
      continue;
    }
    data[key] = userInfo[key];
  }

  return data;
}

+ (NSMutableDictionary *)parseUNNotificationContent:(UNNotificationContent *)content {
  NSMutableDictionary *dictionary = [NSMutableDictionary dictionary];
  NSMutableDictionary *iosDict = [NSMutableDictionary dictionary];

  dictionary[@"subtitle"] = content.subtitle;
  dictionary[@"body"] = content.body;
  dictionary[@"data"] = [content.userInfo mutableCopy];

  // title
  if (content.title != nil) {
    dictionary[@"title"] = content.title;
  }

  // subtitle
  if (content.subtitle != nil) {
    dictionary[@"subtitle"] = content.subtitle;
  }

  // body
  if (content.body != nil) {
    dictionary[@"body"] = content.body;
  }

  iosDict[@"badgeCount"] = content.badge;

  // categoryId
  if (content.categoryIdentifier != nil) {
    iosDict[@"categoryId"] = content.categoryIdentifier;
  }

  // launchImageName
  if (content.launchImageName != nil) {
    iosDict[@"launchImageName"] = content.launchImageName;
  }

  // threadId
  if (content.threadIdentifier != nil) {
    iosDict[@"threadId"] = content.threadIdentifier;
  }

  // targetContentId
  if (@available(iOS 13.0, macOS 10.15, macCatalyst 13.0, tvOS 13.0, watchOS 6.0, *)) {
    if (content.targetContentIdentifier != nil) {
      iosDict[@"targetContentId"] = content.targetContentIdentifier;
    }
  }

  if (content.attachments != nil) {
    // TODO: parse attachments
  }

  // sound
  if (content.sound != nil) {
    if ([content.sound isKindOfClass:[NSString class]]) {
      iosDict[@"sound"] = content.sound;
    } else if ([content.sound isKindOfClass:[NSDictionary class]]) {
      NSDictionary *soundDict = (NSDictionary *)content.sound;
      NSMutableDictionary *notificationIOSSound = [[NSMutableDictionary alloc] init];

      // ios.sound.name String
      if (soundDict[@"name"] != nil) {
        notificationIOSSound[@"name"] = soundDict[@"name"];
      }

      // sound.critical Boolean
      if (soundDict[@"critical"] != nil) {
        notificationIOSSound[@"critical"] = soundDict[@"critical"];
      }

      // ios.sound.volume Number
      if (soundDict[@"volume"] != nil) {
        notificationIOSSound[@"volume"] = soundDict[@"volume"];
      }

      // ios.sound
      iosDict[@"sound"] = notificationIOSSound;
    }
  }

  dictionary[@"ios"] = iosDict;
  return dictionary;
}

+ (INSendMessageIntent *)generateSenderIntentForCommunicationNotification:
    (NSDictionary *)communicationInfo {
  if (@available(iOS 15.0, *)) {
    NSDictionary *sender = communicationInfo[@"sender"];
    INPersonHandle *senderPersonHandle =
        [[INPersonHandle alloc] initWithValue:sender[@"id"] type:INPersonHandleTypeUnknown];

    // Parse sender's avatar
    INImage *avatar = nil;
    if (sender[@"avatar"] != nil) {
      NSURL *url = [self getURLFromString:sender[@"avatar"]];
      avatar = [INImage imageWithURL:url];
    }

    INPerson *senderPerson = [[INPerson alloc] initWithPersonHandle:senderPersonHandle
                                                     nameComponents:nil
                                                        displayName:sender[@"displayName"]
                                                              image:avatar
                                                  contactIdentifier:nil
                                                   customIdentifier:nil];

    NSMutableArray *recipients = nil;

    INSpeakableString *speakableGroupName = nil;
    if (communicationInfo[@"groupName"] != nil) {
      speakableGroupName =
          [[INSpeakableString alloc] initWithSpokenPhrase:communicationInfo[@"groupName"]];

      // For the `groupName` to work we need to have more than one recipient, otherwise, it won't be
      // recognized as a group communication. For this reason, we are adding a placeholder person to
      // the recipients which is not going to do any harm, the recipients are used as a fallback for
      // when you don't have a `groupName` it concatenates the recipients name and then use that as
      // a group name.
      INPersonHandle *placeholderPersonHandle =
          [[INPersonHandle alloc] initWithValue:@"placeholderId" type:INPersonHandleTypeUnknown];
      INPerson *placeholderPerson = [[INPerson alloc] initWithPersonHandle:placeholderPersonHandle
                                                            nameComponents:nil
                                                               displayName:sender[@"displayName"]
                                                                     image:avatar
                                                         contactIdentifier:nil
                                                          customIdentifier:nil];
      recipients = [NSMutableArray array];
      [recipients addObject:senderPerson];
      [recipients addObject:placeholderPerson];
    }

    INSendMessageIntent *intent =
        [[INSendMessageIntent alloc] initWithRecipients:recipients
                                    outgoingMessageType:INOutgoingMessageTypeOutgoingMessageText
                                                content:communicationInfo[@"body"]
                                     speakableGroupName:speakableGroupName
                                 conversationIdentifier:communicationInfo[@"conversationId"]
                                            serviceName:nil
                                                 sender:senderPerson
                                            attachments:nil];

    if (communicationInfo[@"groupAvatar"] != nil) {
      NSURL *groupAvatarURL = [[NSURL alloc] initWithString:communicationInfo[@"groupAvatar"]];
      INImage *groupAvatarImage = [INImage imageWithURL:groupAvatarURL];

      [intent setImage:groupAvatarImage forParameterNamed:@"speakableGroupName"];
    }

    return intent;
  }

  return nil;
}
/**
 * Returns a random string using UUID
 *
 * @param length int
 */
+ (NSString *)generateCachedFileName:(int)length {
  return [[NSUUID UUID] UUIDString];
}

/**
 * Returns a shared instance of [UIApplication sharedApplication]
 * Needed to prevent compile errors for App extensions when calling [UIApplication
 * sharedApplication]
 *
 * @return instancetype
 */
+ (nullable instancetype)notifeeUIApplication {
  static dispatch_once_t once;
  static NotifeeCoreUtil *sharedInstance;
  dispatch_once(&once, ^{
    static Class applicationClass = nil;
    if (![self isAppExtension]) {
      Class cls = NSClassFromString(@"UIApplication");
      if (cls && [cls respondsToSelector:NSSelectorFromString(@"sharedApplication")]) {
        applicationClass = cls;
      }
    }

    sharedInstance = (NotifeeCoreUtil *)[applicationClass sharedApplication];
  });

  return sharedInstance;
}

/**
 * Checks if the current application is an extension
 */
+ (BOOL)isAppExtension {
#if TARGET_OS_IOS || TARGET_OS_TV || TARGET_OS_WATCH
  BOOL appExtension = [[[NSBundle mainBundle] bundlePath] hasSuffix:@".appex"];
  return appExtension;
#elif TARGET_OS_OSX
  return NO;
#endif
}

@end
