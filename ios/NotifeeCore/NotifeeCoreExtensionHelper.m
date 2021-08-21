//
//  NotifeeCoreExtensionHelper.m
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//
#import "Private/NotifeeCoreExtensionHelper.h"
#import "Private/NotifeeCoreUtil.h"
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

- (void)populateNotificationContent:(UNNotificationRequest *)request
                        withContent:(UNMutableNotificationContent *)content
                 withContentHandler:(void (^)(UNNotificationContent *_Nonnull))contentHandler {
  self.contentHandler = [contentHandler copy];
  self.bestAttemptContent = content;
  if (!content.userInfo[@"notifee_options"]) {
    [self deliverNotification];
    return;
  }

  // fcm: apns: { payload: {notifee_options: {} } }
  NSMutableDictionary *options = [self.bestAttemptContent.userInfo[@"notifee_options"] mutableCopy];

  options[@"fromExtension"] = @YES;

  // Convert options to Notification and set defaults
  if (options[@"data"] == nil) {
    options[@"data"] = [NSDictionary dictionary];
  }

  // Convert options to Notification and set defaults
  if (request != nil && options[@"id"] == nil) {
    options[@"id"] = request.identifier;
  }

  NSMutableDictionary *attachmentDict = [NSMutableDictionary new];

  if (options[@"ios"] != nil && options[@"ios"][@"attachments"] != nil &&
      [options[@"ios"][@"attachments"] isKindOfClass:[NSArray class]] &&
      [options[@"ios"][@"attachments"] count] != 0) {
    attachmentDict = options[@"ios"][@"attachments"][0];
  }

  if (options[@"title"] == nil && content.title != nil) {
    options[@"title"] = self.bestAttemptContent.title;
  }

  if (options[@"body"] == nil) {
    options[@"body"] = self.bestAttemptContent.body;
  }

  self.bestAttemptContent = [NotifeeCore buildNotificationContent:options withTrigger:nil];

  // Check if image url is in payload and parse it if attachmentDict is empty
  NSString *currentImageURL = options[kPayloadOptionsImageURLName];
  if ([attachmentDict count] == 0 && ![currentImageURL isEqual:[NSNull null]] &&
      currentImageURL.length > 1) {
    // make into an attachment dict
    attachmentDict[@"url"] = currentImageURL;
  }

  if ([attachmentDict count] == 0) {
    [self deliverNotification];
    return;
  }

  // Attempt to download attachment
  [self loadAttachment:attachmentDict
      completionHandler:^(UNNotificationAttachment *attachment) {
        if (attachment != nil) {
          self.bestAttemptContent.attachments = @[ attachment ];
        }

        [self deliverNotification];
      }];
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

- (void)loadAttachment:(NSDictionary *)attachmentDict
     completionHandler:(void (^)(UNNotificationAttachment *))completionHandler {
  @try {
    __block UNNotificationAttachment *attachment = nil;
    NSString *attachmentIdentifier = attachmentDict[@"id"];
    NSURL *attachmentURL = [NSURL URLWithString:attachmentDict[@"url"]];

    NSURLSession *session = [NSURLSession
        sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]];
    [[session
        downloadTaskWithURL:attachmentURL
          completionHandler:^(NSURL *temporaryFileLocation, NSURLResponse *response,
                              NSError *error) {
            if (error != nil) {
              NSLog(
                  @"NotifeeCoreExtensionHelper: An exception occurred while attempting to download "
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

            attachment = [UNNotificationAttachment
                attachmentWithIdentifier:attachmentIdentifier
                                     URL:localURL
                                 options:[NotifeeCoreUtil
                                             attachmentOptionsFromDictionary:attachmentDict]
                                   error:&error];
            if (error) {
              NSLog(
                  @"NotifeeCoreExtensionHelper: Failed to create attachment with URL: %@, error %@",
                  localURL, error);
              completionHandler(attachment);
              return;
            }
            completionHandler(attachment);
          }] resume];
  } @catch (NSException *exception) {
    NSLog(@"NotifeeCoreExtensionHelper: Failed to create attachment: %@, error %@", attachmentDict,
          exception.reason);
    completionHandler(nil);
  }
}

- (void)deliverNotification {
  if (self.contentHandler) {
    self.contentHandler(self.bestAttemptContent);
  }
}

@end
