//
//  NotifeeApiModule.m
//  RNNotifee
//
//  Created by Mike on 31/01/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "NotifeeApiModule.h"
#import <NotifeeCore/Notifee.h>

@implementation NotifeeApiModule

#pragma mark - Module Setup

RCT_EXPORT_MODULE();

- (dispatch_queue_t)methodQueue {
  return dispatch_get_main_queue();
}

- (id)init {
  if (self = [super init]) {
    [Notifee initialize:@"hello from RNNotifee"];
  }
  return self;
}

- (void)invalidate {
  // todo
}

- (NSArray<NSString *> *)supportedEvents {
  return @[@"app.notifee.notification.event"];
}

+ (BOOL)requiresMainQueueSetup {
  return YES;
}


@end
