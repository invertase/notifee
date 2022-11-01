//
//  NotificationService.m
//  NotifeeTestingNSE
//
//  Created by Helena Ford on 21/08/2021.
//  Copyright © 2021 Invertase. All rights reserved.
//

#import "NotificationService.h"
#import "NotifeeExtensionHelper.h"

@interface NotificationService ()

@property(nonatomic, strong) void (^contentHandler)(UNNotificationContent *contentToDeliver);
@property(nonatomic, strong) UNMutableNotificationContent *bestAttemptContent;

@end

@implementation NotificationService

- (void)didReceiveNotificationRequest:(UNNotificationRequest *)request
                   withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler {
  self.contentHandler = contentHandler;
  self.bestAttemptContent = [request.content mutableCopy];

  // Modify the notification content here...
  self.bestAttemptContent.title =
      [NSString stringWithFormat:@"%@ [modified]", self.bestAttemptContent.title];

  [NotifeeExtensionHelper populateNotificationContent:request
                                          withContent:self.bestAttemptContent
                                   withContentHandler:contentHandler];
}

- (void)serviceExtensionTimeWillExpire {
  // Called just before the extension will be terminated by the system.
  // Use this as an opportunity to deliver your "best attempt" at modified content, otherwise the
  // original push payload will be used.
  self.contentHandler(self.bestAttemptContent);
}

@end
