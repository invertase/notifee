//
//  NotifeeCoreDownloadDelegate.m
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Private/NotifeeCoreDownloadDelegate.h"

@implementation NotifeeCoreDownloadDelegate
@synthesize error, response, done;

- (void)URLSession:(NSURLSession *)session
          dataTask:(NSURLSessionDataTask *)dataTask
    didReceiveData:(NSData *)data {
  [outputHandle writeData:data];
}

- (void)URLSession:(NSURLSession *)session
              dataTask:(NSURLSessionDataTask *)dataTask
    didReceiveResponse:(NSURLResponse *)aResponse
     completionHandler:(void (^)(NSURLSessionResponseDisposition))completionHandler {
  response = aResponse;
  long long expectedLength = response.expectedContentLength;
  if (expectedLength > MAX_DOWNLOAD_ATTACHMENT_SIZE) {  // Cancel download task if attachment is
                                                        // more than allowed
    completionHandler(NSURLSessionResponseCancel);
    return;
  }
  completionHandler(NSURLSessionResponseAllow);
}

- (void)URLSession:(NSURLSession *)session didBecomeInvalidWithError:(NSError *)anError {
  error = anError;
  done = YES;

  [outputHandle closeFile];
}

- (void)URLSession:(NSURLSession *)session
                    task:(NSURLSessionTask *)task
    didCompleteWithError:(NSError *)anError {
  done = YES;
  error = anError;
  [outputHandle closeFile];
}

- (id)initWithFilePath:(NSString *)path {
  if (self = [super init]) {
    if ([[NSFileManager defaultManager] fileExistsAtPath:path])
      [[NSFileManager defaultManager] removeItemAtPath:path error:nil];

    [[NSFileManager defaultManager] createFileAtPath:path contents:nil attributes:nil];
    outputHandle = [NSFileHandle fileHandleForWritingAtPath:path];
  }
  return self;
}
@end
