//
//  Notifee.h
//  NotifeeCore
//
//  Created by Mike on 31/01/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import <Foundation/Foundation.h>

@import UserNotifications;

NS_ASSUME_NONNULL_BEGIN


typedef void (^notifeeMethodVoidBlock)(NSError *_Nullable);

typedef void (^notifeeMethodNSDictionaryBlock)(NSError *_Nullable, NSDictionary *_Nullable);

typedef void (^notifeeMethodBooleanBlock)(NSError *_Nullable, BOOL);

static NSString *kNotifeeWillPresentNotification = @"NotifeeWillPresentNotification";

static NSString *kNotifeeOpenSettingsForNotification = @"NotifeeOpenSettingsForNotification";

static NSString *kNotifeeDidReceiveNotificationResponse = @"NotifeeDidReceiveNotificationResponse";

@interface Notifee : NSObject <UNUserNotificationCenterDelegate>

@property(nonatomic, strong, nonnull) Notifee *instance;

+ (instancetype)initialize:(NSString *)testString;

+ (instancetype)instance;

- (void)cancelNotification:(NSString *)notificationId withBlock:(notifeeMethodVoidBlock)block;

- (void)displayNotification:(NSDictionary *)notification withBlock:(notifeeMethodVoidBlock)block;

- (void)requestPermission:(NSDictionary *)permissions withBlock:(notifeeMethodNSDictionaryBlock)block;

- (void)getNotificationSettings:(notifeeMethodNSDictionaryBlock)block;

@end


NS_ASSUME_NONNULL_END
