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

#if __IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_10_0 || \
    __MAC_OS_X_VERSION_MAX_ALLOWED >= __MAC_10_14
#import <UserNotifications/UserNotifications.h>
#endif

#import "NotifeeCore.h"

NS_ASSUME_NONNULL_BEGIN
@interface NotifeeExtensionHelper : NSObject

+ (void)populateNotificationContent:(UNNotificationRequest *_Nullable)request
                        withContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler;

+ (void)populateNotificationContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler
    __attribute__((deprecated));
@end

NS_ASSUME_NONNULL_END
