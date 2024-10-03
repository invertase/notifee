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
#import "NotifeeCore.h"

NS_ASSUME_NONNULL_BEGIN

@interface NotifeeCoreUNUserNotificationCenter : NSObject <UNUserNotificationCenterDelegate>

@property(nonatomic, nullable, weak) id<UNUserNotificationCenterDelegate> originalDelegate;

@property(strong, nullable) NSDictionary *initialNotification;
@property bool initialNotificationGathered;
@property(nullable) notifeeMethodNSDictionaryBlock initialNotificationBlock;
@property NSString *initialNoticationID;
@property NSString *notificationOpenedAppID;

+ (_Nonnull instancetype)instance;

- (void)observe;

- (nullable NSDictionary *)getInitialNotification;

- (void)onDidFinishLaunchingNotification:(NSDictionary *)notification;

@end

NS_ASSUME_NONNULL_END
