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

#import <UIKit/UIKit.h>
#import "NotifeeCore+NSNotificationCenter.h"
#import "NotifeeCore+UNUserNotificationCenter.h"

@implementation NotifeeCoreNSNotificationCenter

+ (instancetype)instance {
  static dispatch_once_t once;
  __strong static NotifeeCoreNSNotificationCenter *sharedInstance;
  dispatch_once(&once, ^{
    sharedInstance = [[NotifeeCoreNSNotificationCenter alloc] init];
  });
  return sharedInstance;
}

- (void)observe {
  static dispatch_once_t once;
  __weak NotifeeCoreNSNotificationCenter *weakSelf = self;
  dispatch_once(&once, ^{
    NotifeeCoreNSNotificationCenter *strongSelf = weakSelf;
    // Application
    // ObjC -> Initialize other delegates & observers
    [[NSNotificationCenter defaultCenter]
        addObserver:strongSelf
           selector:@selector(application_onDidFinishLaunchingNotification:)
               name:UIApplicationDidFinishLaunchingNotification
             object:nil];
    [[NSNotificationCenter defaultCenter]
        addObserver:strongSelf
           selector:@selector(messaging_didReceiveRemoteNotification:)
               name:@"RNFBMessagingDidReceiveRemoteNotification"
             object:nil];
  });
}

+ (void)load {
  [[self instance] observe];
}

#pragma mark -
#pragma mark Application Notifications

- (void)application_onDidFinishLaunchingNotification:(nonnull NSNotification *)notification {
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wdeprecated-declarations"
  UILocalNotification *launchNotification =
      (UILocalNotification *)notification.userInfo[UIApplicationLaunchOptionsLocalNotificationKey];
  [[NotifeeCoreUNUserNotificationCenter instance]
      onDidFinishLaunchingNotification:launchNotification.userInfo];
  [[NotifeeCoreUNUserNotificationCenter instance] getInitialNotification];

  [[NotifeeCoreUNUserNotificationCenter instance] observe];
}

- (void)messaging_didReceiveRemoteNotification:(nonnull NSNotification *)notification {
  // update me with logic
}

@end
