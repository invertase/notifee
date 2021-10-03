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

#import "NotifeeCoreDownloadDelegate.h"

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
