//
//  NotifeeCoreUtil.h
//  NotifeeCore
//
//  Created by Mike on 29/03/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import <Foundation/Foundation.h>

#import <UserNotifications/UserNotifications.h>

NS_ASSUME_NONNULL_BEGIN

static NSString *kNotifeeUserInfoNotification = @"__notifee_notification";
static NSString *kNotifeeUserInfoTrigger = @"__notifee_trigger";

// TimeUnit constants for IntervalTrigger
static NSString *kNotifeeCoreTimeUnitSeconds = @"SECONDS";
static NSString *kNotifeeCoreTimeUnitMinutes = @"MINUTES";
static NSString *kNotifeeCoreTimeUnitHours = @"HOURS";
static NSString *kNotifeeCoreTimeUnitDays = @"DAYS";

// Enum representing trigger notification types
typedef NS_ENUM(NSInteger, NotifeeCoreTriggerType) {
  NotifeeCoreTriggerTypeTimestamp = 0,
  NotifeeCoreTriggerTypeInterval = 1
};

// Enum representing repeat frequency for TimestampTrigger
typedef NS_ENUM(NSInteger, NotifeeCoreRepeatFrequency) {
  NotifeeCoreRepeatFrequencyHourly = 0,
  NotifeeCoreRepeatFrequencyDaily = 1,
  NotifeeCoreRepeatFrequencyWeekly = 2
};

@interface NotifeeCoreUtil : NSObject

+ (NSNumber *)numberForUNNotificationSetting:(UNNotificationSetting)setting;

+ (NSMutableArray<NSDictionary *> *)notificationActionsToDictionaryArray:
    (NSArray<UNNotificationAction *> *)notificationActions;

+ (NSMutableArray<UNNotificationAction *> *)notificationActionsFromDictionaryArray:
    (NSArray<NSDictionary *> *)actionDictionaries;

+ (NSMutableArray<UNNotificationAttachment *> *)notificationAttachmentsFromDictionaryArray:
    (NSArray<NSDictionary *> *)attachmentDictionaries;

+ (NSMutableArray<NSNumber *> *)intentIdentifiersFromStringArray:(NSArray<NSString *> *)identifiers;

+ (NSMutableArray<NSString *> *)intentIdentifiersFromNumberArray:(NSArray<NSNumber *> *)identifiers;

+ (UNNotificationTrigger *)triggerFromDictionary:(NSDictionary *)triggerDict;

+ (BOOL)isAppExtension;

+ (nullable instancetype)notifeeUIApplication;

@end

NS_ASSUME_NONNULL_END
