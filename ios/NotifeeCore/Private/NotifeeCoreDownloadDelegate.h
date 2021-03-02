//
//  NotifeeCoreDownloadDelegate.h
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

#define MAX_DOWNLOAD_ATTACHMENT_SIZE 50000000

@interface NotifeeCoreDownloadDelegate : NSObject <NSURLSessionDataDelegate> {
  NSError *error;
  NSURLResponse *response;
  BOOL done;
  NSFileHandle *outputHandle;
}

@property(readonly, getter=isDone) BOOL done;
@property(readonly) NSError *error;
@property(readonly) NSURLResponse *response;

- (id)initWithFilePath:(NSString *)path;

@end

NS_ASSUME_NONNULL_END
