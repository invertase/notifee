//
//  NotifeeCore.h
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import <Foundation/Foundation.h>

//! Project version number for NotifeeCore.
FOUNDATION_EXPORT double NotifeeCoreVersionNumber;

//! Project version string for NotifeeCore.
FOUNDATION_EXPORT const unsigned char NotifeeCoreVersionString[];

// Import all the public headers of your framework using statements like #import <NotifeeCore/PublicHeader.h>
// #import <NotifeeCore/Example.h>
// END public headers import

NS_ASSUME_NONNULL_BEGIN

typedef void (^notifeeMethodVoidBlock)(NSError *_Nullable);

typedef void (^notifeeMethodNSDictionaryBlock)(NSError *_Nullable, NSDictionary *_Nullable);

typedef void (^notifeeMethodNSArrayBlock)(NSError *_Nullable, NSArray *_Nullable);

typedef void (^notifeeMethodBooleanBlock)(NSError *_Nullable, BOOL);

typedef void (^notifeeMethodNSIntegerBlock)(NSError *_Nullable, NSInteger);

@class NotifeeCore;

@protocol NotifeeCoreDelegate <NSObject>
@optional
- (void) didReceiveNotifeeCoreEvent:(NSDictionary *_Nonnull)event;
@end

@interface NotifeeCore : NSObject

+ (void)setCoreDelegate:(id <NotifeeCoreDelegate>)coreDelegate;

+ (void)cancelNotification:(NSString *)notificationId withBlock:(notifeeMethodVoidBlock)block;

+ (void)displayNotification:(NSDictionary *)notification withBlock:(notifeeMethodVoidBlock)block;

+ (void)requestPermission:(NSDictionary *)permissions withBlock:(notifeeMethodNSDictionaryBlock)block;

+ (void)getNotificationCategories:(notifeeMethodNSArrayBlock)block;

+ (void)setNotificationCategories:(NSArray<NSDictionary *> *)categories withBlock:(notifeeMethodVoidBlock)block;

+ (void)getNotificationSettings:(notifeeMethodNSDictionaryBlock)block;

+ (void)getInitialNotification:(notifeeMethodNSDictionaryBlock)block;

+ (void)setBadgeCount:(NSInteger)count withBlock:(notifeeMethodVoidBlock)block;

+ (void)getBadgeCount:(notifeeMethodNSIntegerBlock)block;

@end

NS_ASSUME_NONNULL_END
