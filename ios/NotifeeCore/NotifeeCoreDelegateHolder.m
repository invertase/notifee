//
//  NotifeeDelegate.m
//  NotifeeCore
//
//  Created by Mike on 29/03/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Private/NotifeeCoreDelegateHolder.h"

@implementation NotifeeCoreDelegateHolder {
  struct {
    unsigned int didReceiveNotificationEvent:1;
  } delegateRespondsTo;
}

@synthesize delegate;

+ (instancetype)instance {
  static dispatch_once_t once;
  __strong static NotifeeCoreDelegateHolder *sharedInstance;
  dispatch_once(&once, ^{
    sharedInstance = [[NotifeeCoreDelegateHolder alloc] init];
  });
  return sharedInstance;
}

- (void)setDelegate:(id <NotifeeCoreDelegate>)aDelegate {
  if (delegate != aDelegate) {
    delegate = aDelegate;
    self->delegateRespondsTo.didReceiveNotificationEvent = (unsigned int) [delegate respondsToSelector:@selector(didReceiveNotifeeCoreEvent:)];
  }
}

- (void)didReceiveNotifeeCoreEvent:(NSDictionary *)notificationEvent {
  if (self->delegateRespondsTo.didReceiveNotificationEvent) {
    [self->delegate didReceiveNotifeeCoreEvent:notificationEvent];
  } else {
    // TODO logger
  }
}


@end
