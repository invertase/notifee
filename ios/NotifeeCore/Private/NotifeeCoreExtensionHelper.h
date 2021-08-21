//
//  NotifeeCoreExtensionHelper.h
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//
#import <Foundation/Foundation.h>
#import <UserNotifications/UserNotifications.h>

NS_ASSUME_NONNULL_BEGIN

static NSString *const kPayloadOptionsName = @"notifee_options";
static NSString *const kPayloadOptionsImageURLName = @"image";

@interface NotifeeCoreExtensionHelper : NSObject
@property(nonatomic, strong) void (^contentHandler)(UNNotificationContent *contentToDeliver);
@property(nonatomic, strong) UNMutableNotificationContent *bestAttemptContent;

+ (NotifeeCoreExtensionHelper *)instance NS_SWIFT_NAME(serviceExtension());

- (void)populateNotificationContent:(UNNotificationRequest *)request
                        withContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler;

- (void)populateNotificationContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler
    __attribute__((deprecated));

@end

NS_ASSUME_NONNULL_END
