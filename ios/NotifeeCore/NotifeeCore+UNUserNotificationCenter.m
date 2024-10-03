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

#import "NotifeeCore+UNUserNotificationCenter.h"

#import "NotifeeCoreDelegateHolder.h"
#import "NotifeeCoreUtil.h"

@implementation NotifeeCoreUNUserNotificationCenter

struct {
  unsigned int willPresentNotification : 1;
  unsigned int didReceiveNotificationResponse : 1;
  unsigned int openSettingsForNotification : 1;
} originalUNCDelegateRespondsTo;

+ (instancetype)instance {
  static dispatch_once_t once;
  __strong static NotifeeCoreUNUserNotificationCenter *sharedInstance;
  dispatch_once(&once, ^{
    sharedInstance = [[NotifeeCoreUNUserNotificationCenter alloc] init];
    sharedInstance.initialNotification = nil;
    sharedInstance.initialNotificationGathered = false;
    sharedInstance.initialNotificationBlock = nil;
  });
  return sharedInstance;
}

- (void)observe {
  static dispatch_once_t once;
  __weak NotifeeCoreUNUserNotificationCenter *weakSelf = self;
  dispatch_once(&once, ^{
    NotifeeCoreUNUserNotificationCenter *strongSelf = weakSelf;
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    if (center.delegate != nil) {
      _originalDelegate = center.delegate;
      originalUNCDelegateRespondsTo.openSettingsForNotification = (unsigned int)[_originalDelegate
          respondsToSelector:@selector(userNotificationCenter:openSettingsForNotification:)];
      originalUNCDelegateRespondsTo.willPresentNotification = (unsigned int)[_originalDelegate
          respondsToSelector:@selector(userNotificationCenter:
                                      willPresentNotification:withCompletionHandler:)];
      originalUNCDelegateRespondsTo.didReceiveNotificationResponse =
          (unsigned int)[_originalDelegate
              respondsToSelector:@selector(userNotificationCenter:
                                     didReceiveNotificationResponse:withCompletionHandler:)];
    }
    center.delegate = strongSelf;
  });
}

- (void)onDidFinishLaunchingNotification:(nonnull NSDictionary *)notifUserInfo {
  if (notifUserInfo != nil) {
    NSDictionary *notifeeNotification = notifUserInfo[kNotifeeUserInfoNotification];
    _initialNoticationID = notifeeNotification[@"id"];
  }

  _initialNotificationGathered = YES;
}

- (nullable NSDictionary *)getInitialNotification {
  if (_initialNotificationGathered && _initialNotificationBlock != nil) {
    // copying initial notification
    if (_initialNotification != nil &&
        [_initialNoticationID isEqualToString:_notificationOpenedAppID]) {
      NSDictionary *initialNotificationCopy = [_initialNotification copy];
      _initialNotification = nil;
      _initialNotificationBlock(nil, initialNotificationCopy);
    } else {
      _initialNotificationBlock(nil, nil);
    }

    _initialNotificationBlock = nil;
  }

  return nil;
}

#pragma mark - UNUserNotificationCenter Delegate Methods

// The method will be called on the delegate only if the application is in the
// foreground. If the the handler is not called in a timely manner then the
// notification will not be presented. The application can choose to have the
// notification presented as a sound, badge, alert and/or in the notification
// list. This decision should be based on whether the information in the
// notification is otherwise visible to the user.
- (void)userNotificationCenter:(UNUserNotificationCenter *)center
       willPresentNotification:(UNNotification *)notification
         withCompletionHandler:
             (void (^)(UNNotificationPresentationOptions options))completionHandler {
  NSDictionary *notifeeNotification =
      notification.request.content.userInfo[kNotifeeUserInfoNotification];

  // we only care about notifications created through notifee
  if (notifeeNotification != nil) {
    UNNotificationPresentationOptions presentationOptions = UNNotificationPresentationOptionNone;
    NSDictionary *foregroundPresentationOptions =
        notifeeNotification[@"ios"][@"foregroundPresentationOptions"];

    BOOL alert = [foregroundPresentationOptions[@"alert"] boolValue];
    BOOL badge = [foregroundPresentationOptions[@"badge"] boolValue];
    BOOL sound = [foregroundPresentationOptions[@"sound"] boolValue];
    BOOL banner = [foregroundPresentationOptions[@"banner"] boolValue];
    BOOL list = [foregroundPresentationOptions[@"list"] boolValue];

    if (badge) {
      presentationOptions |= UNNotificationPresentationOptionBadge;
    }

    if (sound) {
      presentationOptions |= UNNotificationPresentationOptionSound;
    }

    // if list or banner is true, ignore alert property
    if (banner || list) {
      if (banner) {
        if (@available(iOS 14, *)) {
          presentationOptions |= UNNotificationPresentationOptionBanner;
        } else {
          // for iOS 13 we need to set alert
          presentationOptions |= UNNotificationPresentationOptionAlert;
        }
      }

      if (list) {
        if (@available(iOS 14, *)) {
          presentationOptions |= UNNotificationPresentationOptionList;
        } else {
          // for iOS 13 we need to set alert
          presentationOptions |= UNNotificationPresentationOptionAlert;
        }
      }
    } else if (alert) {
      // TODO: remove alert once it has been fully removed from the notifee API
      presentationOptions |= UNNotificationPresentationOptionAlert;
    }

    NSDictionary *notifeeTrigger = notification.request.content.userInfo[kNotifeeUserInfoTrigger];
    if (notifeeTrigger != nil) {
      // post DELIVERED event
      [[NotifeeCoreDelegateHolder instance] didReceiveNotifeeCoreEvent:@{
        @"type" : @(NotifeeCoreEventTypeDelivered),
        @"detail" : @{
          @"notification" : notifeeNotification,
        }
      }];
    }

    completionHandler(presentationOptions);

  } else if (_originalDelegate != nil && originalUNCDelegateRespondsTo.willPresentNotification) {
    [_originalDelegate userNotificationCenter:center
                      willPresentNotification:notification
                        withCompletionHandler:completionHandler];
  }
}

// The method will be called when the user responded to the notification by
// opening the application, dismissing the notification or choosing a
// UNNotificationAction. The delegate must be set before the application returns
// from application:didFinishLaunchingWithOptions:.
- (void)userNotificationCenter:(UNUserNotificationCenter *)center
    didReceiveNotificationResponse:(UNNotificationResponse *)response
             withCompletionHandler:(void (^)(void))completionHandler {
  NSDictionary *notifeeNotification =
      response.notification.request.content.userInfo[kNotifeeUserInfoNotification];

  _notificationOpenedAppID = notifeeNotification[@"id"];

  // handle notification outside of notifee
  if (notifeeNotification == nil) {
    if (_originalDelegate != nil && originalUNCDelegateRespondsTo.didReceiveNotificationResponse) {
      [_originalDelegate userNotificationCenter:center
                 didReceiveNotificationResponse:response
                          withCompletionHandler:completionHandler];
    } else {
      notifeeNotification =
          [NotifeeCoreUtil parseUNNotificationRequest:response.notification.request];
    }
  }

  if (notifeeNotification != nil) {
    if ([response.actionIdentifier isEqualToString:UNNotificationDismissActionIdentifier]) {
      // post DISMISSED event, only triggers if notification has a categoryId
      [[NotifeeCoreDelegateHolder instance] didReceiveNotifeeCoreEvent:@{
        @"type" : @(NotifeeCoreEventTypeDismissed),
        @"detail" : @{
          @"notification" : notifeeNotification,
        }
      }];
      return;
    }

    NSNumber *eventType;
    NSMutableDictionary *event = [NSMutableDictionary dictionary];
    NSMutableDictionary *eventDetail = [NSMutableDictionary dictionary];
    NSMutableDictionary *eventDetailPressAction = [NSMutableDictionary dictionary];

    if ([response.actionIdentifier isEqualToString:UNNotificationDefaultActionIdentifier]) {
      eventType = @1;  // PRESS
      // event.detail.pressAction.id
      eventDetailPressAction[@"id"] = @"default";
    } else {
      eventType = @2;  // ACTION_PRESS
      // event.detail.pressAction.id
      eventDetailPressAction[@"id"] = response.actionIdentifier;
    }

    if ([response isKindOfClass:UNTextInputNotificationResponse.class]) {
      // event.detail.input
      eventDetail[@"input"] = [(UNTextInputNotificationResponse *)response userText];
    }

    // event.type
    event[@"type"] = eventType;

    // event.detail.notification
    eventDetail[@"notification"] = notifeeNotification;

    // event.detail.pressAction
    eventDetail[@"pressAction"] = eventDetailPressAction;

    // event.detail
    event[@"detail"] = eventDetail;

    // store notification for getInitialNotification
    _initialNotification = [eventDetail copy];

    // post PRESS/ACTION_PRESS event
    // Set is initial notification to true
    if (_notificationOpenedAppID != nil &&
        [_initialNoticationID isEqualToString:_notificationOpenedAppID]) {
      eventDetail[@"initialNotification"] = @1;
    }

    [[NotifeeCoreDelegateHolder instance] didReceiveNotifeeCoreEvent:event];

    // TODO figure out if this is needed or if we can just complete immediately
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(15 * NSEC_PER_SEC)),
                   dispatch_get_main_queue(), ^{
                     completionHandler();
                   });
  }
}

- (void)userNotificationCenter:(UNUserNotificationCenter *)center
    openSettingsForNotification:(nullable UNNotification *)notification {
  if (_originalDelegate != nil && originalUNCDelegateRespondsTo.openSettingsForNotification) {
    if (@available(iOS 12.0, macOS 10.14, macCatalyst 13.0, *)) {
      [_originalDelegate userNotificationCenter:center openSettingsForNotification:notification];
    } else {
      // Fallback on earlier versions
    }
  }
}

@end
