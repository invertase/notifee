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
//! Project version number for NotifeeCore.
FOUNDATION_EXPORT double NotifeeCoreVersionNumber;
#import <UserNotifications/UserNotifications.h>

//! Project version string for NotifeeCore.
FOUNDATION_EXPORT const unsigned char NotifeeCoreVersionString[];

// Import all the public headers of your framework using statements like #import
// <NotifeeCore/PublicHeader.h> #import <NotifeeCore/Example.h> END public
// headers import

NS_ASSUME_NONNULL_BEGIN

typedef void (^notifeeMethodVoidBlock)(NSError *_Nullable);

typedef void (^notifeeMethodNSDictionaryBlock)(NSError *_Nullable, NSDictionary *_Nullable);

typedef void (^notifeeMethodNSArrayBlock)(NSError *_Nullable, NSArray *_Nullable);

typedef void (^notifeeMethodBooleanBlock)(NSError *_Nullable, BOOL);

typedef void (^notifeeMethodNSIntegerBlock)(NSError *_Nullable, NSInteger);

typedef NS_ENUM(NSInteger, NotifeeCoreNotificationType) {
  NotifeeCoreNotificationTypeAll = 0,
  NotifeeCoreNotificationTypeDisplayed = 1,
  NotifeeCoreNotificationTypeTrigger = 2
};

typedef NS_ENUM(NSInteger, NotifeeCoreEventType) {
  NotifeeCoreEventTypeDismissed = 0,
  NotifeeCoreEventTypeDelivered = 3,
  NotifeeCoreEventTypeTriggerNotificationCreated = 7,
};

@class NotifeeCore;

@protocol NotifeeCoreDelegate <NSObject>
@optional
- (void)didReceiveNotifeeCoreEvent:(NSDictionary *_Nonnull)event;
@end

@interface NotifeeCore : NSObject

+ (void)setCoreDelegate:(id<NotifeeCoreDelegate>)coreDelegate;

+ (void)cancelNotification:(NSString *)notificationId
      withNotificationType:(NSInteger)notificationType
                 withBlock:(notifeeMethodVoidBlock)block;

+ (void)cancelAllNotifications:(NSInteger)notificationType withBlock:(notifeeMethodVoidBlock)block;

+ (void)cancelAllNotificationsWithIds:(NSInteger)notificationType
                              withIds:(NSArray<NSString *> *)ids
                            withBlock:(notifeeMethodVoidBlock)block;

+ (void)displayNotification:(NSDictionary *)notification withBlock:(notifeeMethodVoidBlock)block;

+ (void)createTriggerNotification:(NSDictionary *)notification
                      withTrigger:(NSDictionary *)trigger
                        withBlock:(notifeeMethodVoidBlock)block;

+ (void)getTriggerNotificationIds:(notifeeMethodNSArrayBlock)block;

+ (void)getDisplayedNotifications:(notifeeMethodNSArrayBlock)block;

+ (void)getTriggerNotifications:(notifeeMethodNSArrayBlock)block;

+ (void)requestPermission:(NSDictionary *)permissions
                withBlock:(notifeeMethodNSDictionaryBlock)block;

+ (void)getNotificationCategories:(notifeeMethodNSArrayBlock)block;

+ (void)setNotificationCategories:(NSArray<NSDictionary *> *)categories
                        withBlock:(notifeeMethodVoidBlock)block;

+ (void)getNotificationSettings:(notifeeMethodNSDictionaryBlock)block;

+ (void)getInitialNotification:(notifeeMethodNSDictionaryBlock)block;

+ (void)setBadgeCount:(NSInteger)count withBlock:(notifeeMethodVoidBlock)block;

+ (void)getBadgeCount:(notifeeMethodNSIntegerBlock)block;

+ (void)incrementBadgeCount:(NSInteger)incrementBy withBlock:(notifeeMethodVoidBlock)block;

+ (void)decrementBadgeCount:(NSInteger)decrementBy withBlock:(notifeeMethodVoidBlock)block;

+ (nullable instancetype)notifeeUIApplication;

+ (UNMutableNotificationContent *)buildNotificationContent:(NSDictionary *)notification
                                               withTrigger:(NSDictionary *_Nullable)trigger;

+ (void)populateNotificationContent:(UNNotificationRequest *_Nullable)request
                        withContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler;

@end

NS_ASSUME_NONNULL_END
