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

NS_ASSUME_NONNULL_BEGIN

static NSString *const kPayloadOptionsName = @"notifee_options";
static NSString *const kPayloadOptionsImageURLName = @"image";

@interface NotifeeCoreExtensionHelper : NSObject
@property(nonatomic, strong) void (^contentHandler)(UNNotificationContent *c);
@property(nonatomic, strong) UNMutableNotificationContent *modifiedContent;

+ (NotifeeCoreExtensionHelper *)instance NS_SWIFT_NAME(serviceExtension());

- (void)populateNotificationContent:(UNNotificationRequest *_Nullable)request
                        withContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler;

@end

NS_ASSUME_NONNULL_END
