//
//  NotifeeCore+NSURLSession.m
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Private/NotifeeCore+NSURLSession.h"
#import "Private/NotifeeCoreDownloadDelegate.h"

@implementation NotifeeCoreNSURLSession

+ (NSString *)downloadItemAtURL:(NSURL *)url toFile:(NSString *)localPath error:(NSError **)error {
  NSMutableURLRequest *request = [[NSMutableURLRequest alloc] initWithURL:url];

  NotifeeCoreDownloadDelegate *delegate =
      [[NotifeeCoreDownloadDelegate alloc] initWithFilePath:localPath];

  // The session is created with the defaultSessionConfiguration
  // default timeoutIntervalForRequest is 60 seconds.
  NSURLSession *session =
      [NSURLSession sessionWithConfiguration:[NSURLSessionConfiguration defaultSessionConfiguration]
                                    delegate:delegate
                               delegateQueue:nil];

  NSURLSessionDataTask *task = [session dataTaskWithRequest:request];

  [task resume];

  [session finishTasksAndInvalidate];

  while (![delegate isDone]) {
    [[NSRunLoop currentRunLoop] runUntilDate:[NSDate dateWithTimeIntervalSinceNow:1.0]];
  }

  NSError *delegateError = [delegate error];
  if (delegateError != nil) {
    if (error) *error = delegateError;
    return nil;
  }

  return delegate.response.suggestedFilename;
}

@end
