//
//  NotifeeCoreExtensionHelper.m
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//
#import "Private/NotifeeCoreExtensionHelper.h"

@implementation NotifeeCoreExtensionHelper
+ (NotifeeCoreExtensionHelper *)instance {
  static dispatch_once_t once;
  static NotifeeCoreExtensionHelper *instance;
  dispatch_once(&once, ^{
      instance = [[self alloc] init];
  });
  return instance;
}

- (void)populateNotificationContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler {
  self.contentHandler = [contentHandler copy];
  self.bestAttemptContent = content;
    if (!content.userInfo[@"notifee_options"]) {
        [self deliverNotification];
        return;
    }
    
    // fcm: apns: { payload: {notifee_options: {} } }
    NSDictionary *options = self.bestAttemptContent.userInfo[@"notifee_options"];

    // TODO(helenaford): parse notifee options
}

- (void)deliverNotification {
  if (self.contentHandler) {
    self.contentHandler(self.bestAttemptContent);
  }
}

@end
