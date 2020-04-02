//
//  Notifee+UNUserNotificationCenter.m
//  NotifeeCore
//
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Private/NotifeeCore+UNUserNotificationCenter.h"

#import "Private/NotifeeCoreUtil.h"
#import "Private/NotifeeCoreDelegateHolder.h"

@implementation NotifeeCoreUNUserNotificationCenter

+ (instancetype)instance {
  static dispatch_once_t once;
  __strong static NotifeeCoreUNUserNotificationCenter *sharedInstance;
  dispatch_once(&once, ^{
    sharedInstance = [[NotifeeCoreUNUserNotificationCenter alloc] init];
    sharedInstance.initialNotification = nil;
  });
  return sharedInstance;
}

- (void)observe {
  static dispatch_once_t once;
  __weak NotifeeCoreUNUserNotificationCenter *weakSelf = self;
  dispatch_once(&once, ^{
    NotifeeCoreUNUserNotificationCenter *strongSelf = weakSelf;
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    center.delegate = strongSelf;
  });
}

- (nullable NSDictionary *)getInitialNotification {
  if (_initialNotification != nil) {
    NSDictionary *initialNotificationCopy = [_initialNotification copy];
    _initialNotification = nil;
    return initialNotificationCopy;
  }

  return nil;
}

# pragma mark - UNUserNotificationCenter Delegate Methods

// The method will be called on the delegate only if the application is in the foreground.
// If the the handler is not called in a timely manner then the notification will not be presented.
// The application can choose t o have the notification presented as a sound, badge, alert and/or in the
// notification list. This decision should be based on whether the information in the notification is otherwise visible
// to the user.
- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler {
  NSDictionary *notifeeNotification = notification.request.content.userInfo[kNotifeeUserInfoNotification];

  // we only care about notifications created through notifee
  if (notifeeNotification != nil) {
    UNNotificationPresentationOptions presentationOptions = 0;
    NSNumber *importance = notifeeNotification[@"ios"][@"importance"];

    if ([importance isEqualToNumber:@4]) { // HIGH
      presentationOptions += UNNotificationPresentationOptionAlert + UNNotificationPresentationOptionSound;
    } else if ([importance isEqualToNumber:@3]) { // DEFAULT
      presentationOptions += UNNotificationPresentationOptionAlert;
    }

    if (notifeeNotification[@"ios"][@"badgeCount"] != nil) {
      presentationOptions += UNNotificationPresentationOptionBadge;
    }

    completionHandler(presentationOptions);
  }
}

// The method will be called when the user responded to the notification by opening the application, dismissing the
// notification or choosing a UNNotificationAction. The delegate must be set before the application returns from
// application:didFinishLaunchingWithOptions:.
- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler {
  NSDictionary *notifeeNotification = response.notification.request.content.userInfo[kNotifeeUserInfoNotification];

  // we only care about notifications created through notifee
  if (notifeeNotification != nil) {
    if ([response.actionIdentifier isEqualToString:UNNotificationDismissActionIdentifier]) {
      // TODO handle in a later version - sending a DISMISSED = 0 event to match Android
      return;
    }

    NSNumber *eventType;
    NSMutableDictionary *event = [NSMutableDictionary dictionary];
    NSMutableDictionary *eventDetail = [NSMutableDictionary dictionary];
    NSMutableDictionary *eventDetailPressAction = [NSMutableDictionary dictionary];

    if ([response.actionIdentifier isEqualToString:UNNotificationDefaultActionIdentifier]) {
      eventType = @1; // PRESS
      // event.detail.pressAction.id
      eventDetailPressAction[@"id"] = @"default";
    } else {
      eventType = @2; // ACTION_PRESS
      // event.detail.pressAction.id
      eventDetailPressAction[@"id"] = response.actionIdentifier;
    }

    if ([response isKindOfClass:UNTextInputNotificationResponse.class]) {
      // event.detail.input
      eventDetail[@"input"] = [(UNTextInputNotificationResponse *) response userText];
    }

    // event.type
    event[@"type"] = eventType;

    // event.detail.notification
    eventDetail[@"notification"] = notifeeNotification;

    // event.detail.pressAction
    eventDetail[@"pressAction"] = eventDetailPressAction;

    //event.detail
    event[@"detail"] = eventDetail;

    // store notification for getInitialNotification
    _initialNotification = [eventDetail copy];

    // post PRESS/ACTION_PRESS event
    [[NotifeeCoreDelegateHolder instance] didReceiveNotifeeCoreEvent:event];

    // TODO figure out if this is needed or if we can just complete immediately
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t) (15 * NSEC_PER_SEC)), dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_HIGH, 0), ^{
      completionHandler();
    });
  }
}

@end
