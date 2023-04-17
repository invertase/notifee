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

#import <Foundation/Foundation.h>

#import <UserNotifications/UserNotifications.h>
#import "Intents/Intents.h"

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

+ (NSDictionary *)attachmentOptionsFromDictionary:(NSDictionary *)optionsDict;

+ (NSMutableArray<NSNumber *> *)intentIdentifiersFromStringArray:(NSArray<NSString *> *)identifiers;

+ (NSMutableArray<NSString *> *)intentIdentifiersFromNumberArray:(NSArray<NSNumber *> *)identifiers;

+ (UNNotificationTrigger *)triggerFromDictionary:(NSDictionary *)triggerDict;

+ (NSNumber *)convertToTimestamp:(NSDate *)date;

+ (NSDictionary *)parseUNNotificationRequest:(UNNotificationRequest *)request;

+ (INSendMessageIntent *)generateSenderIntentForCommunicationNotification:
    (NSMutableDictionary *)options;

+ (BOOL)isAppExtension;

+ (nullable instancetype)notifeeUIApplication;

@end

NS_ASSUME_NONNULL_END
