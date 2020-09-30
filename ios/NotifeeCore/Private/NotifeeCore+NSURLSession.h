//
//  NotifeeCore+NSURLSession.h
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
@interface NotifeeCoreNSURLSession : NSObject
+ (NSString *)downloadItemAtURL:(NSURL *)url toFile:(NSString *)localPath error:(NSError **)error;
@end

NS_ASSUME_NONNULL_END
