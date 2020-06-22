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

@interface NotifeeCoreUtil : NSObject

+ (NSNumber *)numberForUNNotificationSetting:(UNNotificationSetting)setting;

+ (NSMutableArray<NSDictionary *> *)notificationActionsToDictionaryArray:(NSArray<UNNotificationAction *> *)notificationActions;

+ (NSMutableArray<UNNotificationAction *> *)notificationActionsFromDictionaryArray:(NSArray<NSDictionary *> *)actionDictionaries;

+ (NSMutableArray<UNNotificationAttachment *> *)notificationAttachmentsFromDictionaryArray:(NSArray<NSDictionary *> *)attachmentDictionaries;

+ (NSMutableArray<NSNumber *> *)intentIdentifiersFromStringArray:(NSArray<NSString *> *)identifiers;

+ (NSMutableArray<NSString *> *)intentIdentifiersFromNumberArray:(NSArray<NSNumber *> *)identifiers;

@end

NS_ASSUME_NONNULL_END
