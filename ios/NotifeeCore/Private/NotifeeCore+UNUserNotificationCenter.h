//
//  Notifee+UNUserNotificationCenter.h
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UserNotifications/UserNotifications.h>

NS_ASSUME_NONNULL_BEGIN

@interface NotifeeCoreUNUserNotificationCenter : NSObject <UNUserNotificationCenterDelegate>

@property (strong, nullable) NSDictionary* initialNotification;

+ (_Nonnull instancetype)instance;

- (void)observe;

- (nullable NSDictionary *)getInitialNotification;

@end

NS_ASSUME_NONNULL_END
