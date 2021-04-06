//
//  NotifeeDelegate.m
//  NotifeeCore
//
//  Created by Mike on 29/03/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Private/NotifeeCoreDelegateHolder.h"
#import "Private/NotifeeCoreUtil.h"

@implementation NotifeeCoreDelegateHolder {
  struct {
    unsigned int didReceiveNotificationEvent : 1;
  } delegateRespondsTo;
}

@synthesize delegate;

+ (instancetype)instance {
  static dispatch_once_t once;
  __strong static NotifeeCoreDelegateHolder *sharedInstance;
  dispatch_once(&once, ^{
    sharedInstance = [[NotifeeCoreDelegateHolder alloc] init];
    sharedInstance.pendingEvents = [[NSMutableArray alloc] init];
  });
  return sharedInstance;
}

- (void)setDelegate:(id<NotifeeCoreDelegate>)aDelegate {
  if (delegate != aDelegate) {
    delegate = aDelegate;
    self->delegateRespondsTo.didReceiveNotificationEvent = (unsigned int)[delegate
        respondsToSelector:@selector(didReceiveNotifeeCoreEvent:foreground:)];
    if (_pendingEvents.count > 0) {
      // make sure events are only processed once the module that wraps core has
      // set its delegate
      static dispatch_once_t once;
      // TODO temp workaround to delay initial start until RN module can queue
      // events
      dispatch_once(&once, ^{
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)),
                       dispatch_get_main_queue(), ^{
                         for (NSDictionary *pendingEvent in self->_pendingEvents) {
                           [self didReceiveNotifeeCoreEvent:pendingEvent[@"event"]
                                                 foreground:pendingEvent[@"foreground"]];
                         }
                         self->_pendingEvents = [[NSMutableArray alloc] init];
                       });
      });
    }
  }
}

- (void)didReceiveNotifeeCoreEvent:(NSDictionary *)notificationEvent foreground:(BOOL)foreground {
  if (self->delegateRespondsTo.didReceiveNotificationEvent) {
    [self->delegate didReceiveNotifeeCoreEvent:notificationEvent
                                    foreground:[NotifeeCoreUtil isInForeground]];
  } else {
    NSNumber *foregroundResult = [NSNumber numberWithBool:foreground];
    [self->_pendingEvents addObject:@{
      @"event" : notificationEvent,
      @"foreground" : foregroundResult,
    }];
  }
}

@end
