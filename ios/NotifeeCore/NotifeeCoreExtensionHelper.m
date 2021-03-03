//
//  NotifeeCoreExtensionHelper.m
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//
#import "Private/NotifeeCoreExtensionHelper.h"
#import "Public/NotifeeCore.h"

static NSString *const kNoExtension = @"";
static NSString *const kImagePathPrefix = @"image/";

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
  NSMutableDictionary *options = [self.bestAttemptContent.userInfo[@"notifee_options"] mutableCopy];

  // Convert options to Notification and set defaults
  if (options[@"data"] == nil) {
    options[@"data"] = [NSDictionary dictionary];
  }

  if (options[@"ios"] != nil && options[@"ios"][@"attachments"] != nil) {
    options[@"ios"][@"attachments"] = nil;
  }

  if (options[@"title"] == nil && content.title != nil) {
    options[@"title"] = self.bestAttemptContent.title;
  }

  if (options[@"body"] == nil) {
    options[@"body"] = self.bestAttemptContent.body;
  }

  self.bestAttemptContent = [NotifeeCore buildNotificationContent:options withTrigger:nil];

  // Check if image url is in payload
  NSString *currentImageURL = content.userInfo[kPayloadOptionsName][kPayloadOptionsImageURLName];
  if (!currentImageURL) {
    [self deliverNotification];
    return;
  }

  // Attempt to download image
  NSURL *attachmentURL = [NSURL URLWithString:currentImageURL];
  if (attachmentURL) {
    [self loadAttachmentForURL:attachmentURL
             completionHandler:^(UNNotificationAttachment *attachment) {
               if (attachment != nil) {
                 self.bestAttemptContent.attachments = @[ attachment ];
               }

               [self deliverNotification];
             }];
  } else {
    [self deliverNotification];
  }
}

- (NSString *)fileExtensionForResponse:(NSURLResponse *)response {
  NSString *suggestedPathExtension = [response.suggestedFilename pathExtension];
  if (suggestedPathExtension.length > 0) {
    return [NSString stringWithFormat:@".%@", suggestedPathExtension];
  }
  if ([response.MIMEType containsString:kImagePathPrefix]) {
    return [response.MIMEType stringByReplacingOccurrencesOfString:kImagePathPrefix
                                                        withString:@"."];
  }
  return kNoExtension;
}

- (void)loadAttachmentForURL:(NSURL *)attachmentURL
           completionHandler:(void (^)(UNNotificationAttachment *))completionHandler {
  __block UNNotificationAttachment *attachment = nil;

  NSURLSession *session = [NSURLSession
      sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
  [[session
      downloadTaskWithURL:attachmentURL
        completionHandler:^(NSURL *temporaryFileLocation, NSURLResponse *response, NSError *error) {
          if (error != nil) {
            NSLog(@"NotifeeCoreExtensionHelper: An exception occurred while attempting to download "
                  @"image with URL %@: "
                  @"%@",
                  attachmentURL, error);
            completionHandler(attachment);
            return;
          }

          NSFileManager *fileManager = [NSFileManager defaultManager];
          NSString *fileExtension = [self fileExtensionForResponse:response];
          NSURL *localURL = [NSURL
              fileURLWithPath:[temporaryFileLocation.path stringByAppendingString:fileExtension]];
          [fileManager moveItemAtURL:temporaryFileLocation toURL:localURL error:&error];
          if (error) {
            NSLog(@"NotifeeCoreExtensionHelper: Failed to move the image file to local location: "
                  @"%@, error %@",
                  localURL, error);
            completionHandler(attachment);
            return;
          }

          attachment = [UNNotificationAttachment attachmentWithIdentifier:@""
                                                                      URL:localURL
                                                                  options:nil
                                                                    error:&error];
          if (error) {
            NSLog(@"NotifeeCoreExtensionHelper: Failed to create attachment with URL: %@, error %@",
                  localURL, error);
            completionHandler(attachment);
            return;
          }
          completionHandler(attachment);
        }] resume];
}

- (void)deliverNotification {
  if (self.contentHandler) {
    self.contentHandler(self.bestAttemptContent);
  }
}

@end
