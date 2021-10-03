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

#import "NotifeeCoreDelegateHolder.h"

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
    self->delegateRespondsTo.didReceiveNotificationEvent =
        (unsigned int)[delegate respondsToSelector:@selector(didReceiveNotifeeCoreEvent:)];
    if (_pendingEvents.count > 0) {
      // make sure events are only processed once the module that wraps core has
      // set its delegate
      static dispatch_once_t once;
      // TODO temp workaround to delay initial start until RN module can queue
      // events
      dispatch_once(&once, ^{
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)),
                       dispatch_get_main_queue(), ^{
                         for (NSDictionary *event in self->_pendingEvents) {
                           [self didReceiveNotifeeCoreEvent:event];
                         }
                         self->_pendingEvents = [[NSMutableArray alloc] init];
                       });
      });
    }
  }
}

- (void)didReceiveNotifeeCoreEvent:(NSDictionary *)notificationEvent {
  if (self->delegateRespondsTo.didReceiveNotificationEvent) {
    [self->delegate didReceiveNotifeeCoreEvent:notificationEvent];
  } else {
    [self->_pendingEvents addObject:notificationEvent];
  }
}

@end
