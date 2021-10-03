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

#import "NotifeeCore+NSURLSession.h"
#import "NotifeeCoreDownloadDelegate.h"

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
