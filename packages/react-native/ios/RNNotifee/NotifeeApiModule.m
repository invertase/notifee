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

#import "NotifeeApiModule.h"
#import <React/RCTUtils.h>
#import <UIKit/UIKit.h>

static NSString *kReactNativeNotifeeNotificationEvent = @"app.notifee.notification-event";
static NSString *kReactNativeNotifeeNotificationBackgroundEvent =
    @"app.notifee.notification-event-background";

static NSInteger kReactNativeNotifeeNotificationTypeDisplayed = 1;
static NSInteger kReactNativeNotifeeNotificationTypeTrigger = 2;
static NSInteger kReactNativeNotifeeNotificationTypeAll = 0;

@implementation NotifeeApiModule {
  bool hasListeners;
  NSMutableArray *pendingCoreEvents;
}

#pragma mark - Module Setup

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

- (id)init {
  if (self = [super init]) {
    pendingCoreEvents = [[NSMutableArray alloc] init];
    [NotifeeCore setCoreDelegate:self];
  }
  return self;
}

- (NSArray<NSString *> *)supportedEvents {
  return @[ kReactNativeNotifeeNotificationEvent, kReactNativeNotifeeNotificationBackgroundEvent ];
}

- (void)startObserving {
  hasListeners = YES;
  for (NSDictionary *eventBody in pendingCoreEvents) {
    [self sendNotifeeCoreEvent:eventBody];
  }
  [pendingCoreEvents removeAllObjects];
}

- (void)stopObserving {
  hasListeners = NO;
}

+ (BOOL)requiresMainQueueSetup {
  return YES;
}

#pragma mark - Events

- (void)didReceiveNotifeeCoreEvent:(NSDictionary *_Nonnull)event {
  if (hasListeners) {
    [self sendNotifeeCoreEvent:event];
  } else {
    [pendingCoreEvents addObject:event];
  }
}

- (void)sendNotifeeCoreEvent:(NSDictionary *_Nonnull)eventBody {
  dispatch_after(
      dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        if (RCTRunningInAppExtension() ||
            [UIApplication sharedApplication].applicationState == UIApplicationStateBackground) {
          [self sendEventWithName:kReactNativeNotifeeNotificationBackgroundEvent body:eventBody];
        } else {
          [self sendEventWithName:kReactNativeNotifeeNotificationEvent body:eventBody];
        }
      });
}

// TODO(helenaford): look into a custom format style for React Native Method signatures
// clang-format off

# pragma mark - React Native Methods

RCT_EXPORT_METHOD(cancelNotification:
  (NSString *) notificationId
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore cancelNotification:notificationId withNotificationType:kReactNativeNotifeeNotificationTypeAll withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelDisplayedNotification:
  (NSString *) notificationId
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore cancelNotification:notificationId withNotificationType:kReactNativeNotifeeNotificationTypeDisplayed withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelTriggerNotification:
  (NSString *) notificationId
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore cancelNotification:notificationId withNotificationType:kReactNativeNotifeeNotificationTypeTrigger withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelAllNotifications:
  (RCTPromiseResolveBlock)resolve
      reject:
      (RCTPromiseRejectBlock)reject
) {
   [NotifeeCore cancelAllNotifications:kReactNativeNotifeeNotificationTypeAll withBlock:^(NSError *_Nullable error) {
     [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelDisplayedNotifications:
  (RCTPromiseResolveBlock)resolve
      reject:
      (RCTPromiseRejectBlock)reject
) {
  [NotifeeCore cancelAllNotifications:kReactNativeNotifeeNotificationTypeDisplayed withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelTriggerNotifications:
  (RCTPromiseResolveBlock)resolve
      reject:
      (RCTPromiseRejectBlock)reject
) {
   [NotifeeCore cancelAllNotifications:kReactNativeNotifeeNotificationTypeTrigger withBlock:^(NSError *_Nullable error) {
     [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelAllNotificationsWithIds:
      (NSArray<NSString *> *)ids
      resolve:
      (RCTPromiseResolveBlock)resolve
      reject:
      (RCTPromiseRejectBlock)reject
) {
  [NotifeeCore cancelAllNotificationsWithIds:kReactNativeNotifeeNotificationTypeAll withIds:ids withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}


RCT_EXPORT_METHOD(cancelDisplayedNotificationsWithIds:
      (NSArray<NSString *> *)ids
      resolve:
      (RCTPromiseResolveBlock)resolve
      reject:
      (RCTPromiseRejectBlock)reject
) {
  [NotifeeCore cancelAllNotificationsWithIds:kReactNativeNotifeeNotificationTypeDisplayed withIds:ids withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(cancelTriggerNotificationsWithIds:
      (NSArray<NSString *> *)ids
      resolve:
      (RCTPromiseResolveBlock)resolve
      reject:
      (RCTPromiseRejectBlock)reject
) {
  [NotifeeCore cancelAllNotificationsWithIds:kReactNativeNotifeeNotificationTypeTrigger withIds:ids withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(getTriggerNotificationIds:
  (RCTPromiseResolveBlock)resolve
     reject:
     (RCTPromiseRejectBlock)reject
) {
  [NotifeeCore getTriggerNotificationIds:^(NSError *_Nullable error, NSArray<NSDictionary *> *notifications) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:notifications];
  }];
}

RCT_EXPORT_METHOD(displayNotification:
  (NSDictionary *) notification
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore displayNotification:notification withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(createTriggerNotification:
  (NSDictionary *)notification
      trigger:
      (NSDictionary *)trigger
                  resolve:
                  (RCTPromiseResolveBlock) resolve
                  reject:
                  (RCTPromiseRejectBlock) reject) {
        [NotifeeCore createTriggerNotification:notification withTrigger:trigger withBlock:^(NSError *_Nullable error) {
          [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
    }];
  }

RCT_EXPORT_METHOD(requestPermission:
  (NSDictionary *) permissions
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore requestPermission:permissions withBlock:^(NSError *_Nullable error, NSDictionary *settings) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:settings];
  }];
}

RCT_EXPORT_METHOD(getNotificationSettings:
  (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore getNotificationSettings:^(NSError *_Nullable error, NSDictionary *settings) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:settings];
  }];
}

RCT_EXPORT_METHOD(getInitialNotification:
  (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore getInitialNotification:^(NSError *_Nullable error, NSDictionary *settings) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:settings];
  }];
}

RCT_EXPORT_METHOD(getNotificationCategories:
  (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore getNotificationCategories:^(NSError *_Nullable error, NSArray<NSDictionary *> *categories) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:categories];
  }];
}

RCT_EXPORT_METHOD(getDisplayedNotifications:
  (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore getDisplayedNotifications:^(NSError *_Nullable error, NSArray<NSDictionary *> *notifications) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:notifications];
  }];
}

RCT_EXPORT_METHOD(getTriggerNotifications:
  (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore getTriggerNotifications:^(NSError *_Nullable error, NSArray<NSDictionary *> *notifications) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:notifications];
  }];
}

RCT_EXPORT_METHOD(setNotificationCategories:
  (NSArray<NSDictionary *> *) categories
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore setNotificationCategories:categories withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(setBadgeCount:
  (nonnull NSNumber *) count
      resolve:
      (RCTPromiseResolveBlock) resolve
      reject:
      (RCTPromiseRejectBlock) reject
) {
    [NotifeeCore setBadgeCount:[count integerValue] withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(getBadgeCount:
  (RCTPromiseResolveBlock) resolve
  reject:
  (RCTPromiseRejectBlock) reject
) {
  [NotifeeCore getBadgeCount:^(NSError *_Nullable error, NSInteger count) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:@(count)];
  }];
}

RCT_EXPORT_METHOD(incrementBadgeCount:
  (nonnull NSNumber *) incrementBy
  resolve:
  (RCTPromiseResolveBlock) resolve
  reject:
  (RCTPromiseRejectBlock) reject
) {
    [NotifeeCore incrementBadgeCount:[incrementBy integerValue] withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

RCT_EXPORT_METHOD(decrementBadgeCount:
  (nonnull NSNumber *) decrementBy
  resolve:
  (RCTPromiseResolveBlock) resolve
  reject:
  (RCTPromiseRejectBlock) reject
) {
    [NotifeeCore decrementBadgeCount:[decrementBy integerValue] withBlock:^(NSError *_Nullable error) {
    [self resolve:resolve orReject:reject promiseWithError:error orResult:nil];
  }];
}

// clang-format on

#pragma mark - Internals

- (void)resolve:(RCTPromiseResolveBlock)resolve
            orReject:(RCTPromiseRejectBlock)reject
    promiseWithError:(NSError *_Nullable)error
            orResult:(id _Nullable)result {
  if (error != nil) {
    reject(@"unknown", error.localizedDescription, error);
  } else {
    resolve(result);
  }
}

@end
