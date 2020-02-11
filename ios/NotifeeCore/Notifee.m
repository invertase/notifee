//
//  Notifee.m
//  NotifeeCore
//
//  Created by Mike on 31/01/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Notifee.h"

@import UserNotifications;


@implementation Notifee

+ (instancetype)initialize:(NSString *)testString {
  NSLog(@"NotifeeInitialize, %@", testString);
  return [self instance];
}

+ (instancetype)instance {
  static dispatch_once_t once;
  static Notifee *sharedInstance;
  dispatch_once(&once, ^{
    sharedInstance = [[Notifee alloc] init];
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    center.delegate = sharedInstance;
  });

  return sharedInstance;
}

# pragma mark - Delegate Methods

// The method will be called on the delegate only if the application is in the foreground.
// If the the handler is not called in a timely manner then the notification will not be presented.
// The application can choose to have the notification presented as a sound, badge, alert and/or in the
// notification list. This decision should be based on whether the information in the notification is otherwise visible
// to the user.
// TODO in RN Module; sub to above event and call js method with value which when returned
//  calls the completionHandler with the user defined UNNotificationPresentationOption's
- (void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions options))completionHandler {
  [[NSNotificationCenter defaultCenter]
      postNotificationName:kNotifeeWillPresentNotification
                    object:completionHandler
                  userInfo:@{@"notification": notification}
  ];
}

// The method will be called when the user responded to the notification by opening the application, dismissing the
// notification or choosing a UNNotificationAction. The delegate must be set before the application returns from
// application:didFinishLaunchingWithOptions:.
// TODO in RN Module; sub to above event and emit 'action' event to JS
- (void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)(void))completionHandler {
  [[NSNotificationCenter defaultCenter]
      postNotificationName:kNotifeeDidReceiveNotificationResponse
                    object:nil
                  userInfo:@{@"response": response}
  ];
  completionHandler();
}

// The method will be called on the delegate when the application is launched in response to the user's request to view
// in-app notification settings. Add UNAuthorizationOptionProvidesAppNotificationSettings as an option in
// requestAuthorizationWithOptions:completionHandler: to add a button to inline notification settings view and the
// notification settings view in Settings. The notification will be nil when opened from Settings.
- (void)userNotificationCenter:(UNUserNotificationCenter *)center openSettingsForNotification:(nullable UNNotification *)notification {
  [[NSNotificationCenter defaultCenter]
      postNotificationName:kNotifeeOpenSettingsForNotification
                    object:nil
                  userInfo:@{@"notification": notification}
  ];
}

# pragma mark - Library Methods

/**
 * Cancel a currently displayed or scheduled notification.
 *
 * @param notificationId NSString id of the notification to cancel
 * @param block notifeeMethodVoidBlock
 */
- (void)cancelNotification:(NSString *)notificationId withBlock:(notifeeMethodVoidBlock)block {
  NSLog(@"cancelNotification, %@", notificationId);
  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
  [center removeDeliveredNotificationsWithIdentifiers:@[notificationId]];
  [center removePendingNotificationRequestsWithIdentifiers:@[notificationId]];
  block(nil);
}

/**
 * Display a local notification immediately.
 *
 * @param notification NSDictionary representation of UNMutableNotificationContent
 * @param block notifeeMethodVoidBlock
 */
- (void)displayNotification:(NSDictionary *)notification withBlock:(notifeeMethodVoidBlock)block {
  NSLog(@"request authorization succeeded!");

  UNMutableNotificationContent *content = [[UNMutableNotificationContent alloc] init];
  content.title = @"Hello iOS!!!!";
  content.body = @"Look at me &#128517;";
  content.sound = [UNNotificationSound defaultSound];
  content.categoryIdentifier = @"NOTIFEE";


  UNNotificationAction *action1 = [UNNotificationAction actionWithIdentifier:@"ACCEPT" title:@"Yes mofo &#128517;" options:nil];

  UNNotificationAction *action2 = [UNNotificationAction actionWithIdentifier:@"DECLINE" title:@"Nope" options:nil];

  UNNotificationAction *action3 = [UNNotificationAction actionWithIdentifier:@"MAYBE" title:@"Maybe?????" options:nil];

  UNTextInputNotificationAction *action4 = [UNTextInputNotificationAction actionWithIdentifier:@"REPLY" title:@"Reply..." options:nil textInputButtonTitle:nil textInputPlaceholder:nil];

  UNNotificationCategory *category = [UNNotificationCategory categoryWithIdentifier:@"NOTIFEE" actions:@[] intentIdentifiers:@[] options:nil];

  UNNotificationCategory *categoryExt = [UNNotificationCategory categoryWithIdentifier:@"notifee_ext" actions:@[] intentIdentifiers:@[] options:nil];

  UNTimeIntervalNotificationTrigger *trigger = [UNTimeIntervalNotificationTrigger
      triggerWithTimeInterval:5 repeats:NO];

  UNNotificationRequest *request = [UNNotificationRequest requestWithIdentifier:@"FiveSecond"
                                                                        content:content trigger:nil];

  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];

  [center setNotificationCategories:@[category, categoryExt]];
  [center addNotificationRequest:request withCompletionHandler:block];
}

/**
 * Request UNAuthorizationOptions for user notifications.
 * Resolves a NSDictionary representation of UNNotificationSettings.
 *
 * @param permissions NSDictionary
 * @param block NSDictionary block
 */
- (void)requestPermission:(NSDictionary *)permissions withBlock:(notifeeMethodNSDictionaryBlock)block {
  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];

  UNAuthorizationOptions options = UNAuthorizationOptionNone;

  if ([permissions[@"alert"] isEqual:@(YES)]) {
    options |= UNAuthorizationOptionAlert;
  }

  if ([permissions[@"badge"] isEqual:@(YES)]) {
    options |= UNAuthorizationOptionBadge;
  }

  if ([permissions[@"sound"] isEqual:@(YES)]) {
    options |= UNAuthorizationOptionSound;
  }


  if ([permissions[@"settings"] isEqual:@(YES)]) {
    if (@available(iOS 12.0, *)) {
      options |= UNAuthorizationOptionProvidesAppNotificationSettings;
    }
  }

  if ([permissions[@"provisional"] isEqual:@(YES)]) {
    if (@available(iOS 12.0, *)) {
      options |= UNAuthorizationOptionProvisional;
    }
  }

  if ([permissions[@"announcement"] isEqual:@(YES)]) {
    if (@available(iOS 13.0, *)) {
      options |= UNAuthorizationOptionAnnouncement;
    }
  }

  if ([permissions[@"carPlay"] isEqual:@(YES)]) {
    options |= UNAuthorizationOptionCarPlay;
  }

  id handler = ^(BOOL granted, NSError *_Nullable error) {
    [self getNotificationSettings:block];
  };

  [center requestAuthorizationWithOptions:options completionHandler:handler];
}

/**
 * Retrieve UNNotificationSettings for the application.
 * Resolves a NSDictionary representation of UNNotificationSettings.
 *
 * @param block NSDictionary block
 */
- (void)getNotificationSettings:(notifeeMethodNSDictionaryBlock)block {
  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];

  [center getNotificationSettingsWithCompletionHandler:^(UNNotificationSettings *_Nonnull settings) {
    NSMutableDictionary *settingsDictionary = [NSMutableDictionary dictionary];

    // authorizedStatus
    NSNumber *authorizedStatus = @-1;
    if (settings.authorizationStatus == UNAuthorizationStatusNotDetermined) {
      authorizedStatus = @-1;
    } else if (settings.authorizationStatus == UNAuthorizationStatusDenied) {
      authorizedStatus = @0;
    } else if (settings.authorizationStatus == UNAuthorizationStatusAuthorized) {
      authorizedStatus = @1;
    }

    if (@available(iOS 12.0, *)) {
      if (settings.authorizationStatus == UNAuthorizationStatusProvisional) {
        authorizedStatus = @2;
      }
    }

    NSNumber *showPreviews = @-1;
    if (@available(iOS 11.0, *)) {
      if (settings.showPreviewsSetting == UNShowPreviewsSettingNever) {
        showPreviews = @0;
      } else if (settings.showPreviewsSetting == UNShowPreviewsSettingAlways) {
        showPreviews = @1;
      } else if (settings.showPreviewsSetting == UNShowPreviewsSettingWhenAuthenticated) {
        showPreviews = @2;
      }
    }

    if (@available(iOS 13.0, *)) {
      settingsDictionary[@"announcementSetting"] = [self _numberForUNNotificationSetting:settings.announcementSetting];
    } else {
      settingsDictionary[@"announcementSetting"] = @-1;
    }

    if (@available(iOS 12.0, *)) {
      settingsDictionary[@"criticalAlertSetting"] = [self _numberForUNNotificationSetting:settings.criticalAlertSetting];
    } else {
      settingsDictionary[@"criticalAlertSetting"] = @-1;
    }

    if (@available(iOS 12.0, *)) {
      settingsDictionary[@"providesAppNotificationSettings"] = @(settings.providesAppNotificationSettings);
    } else {
      settingsDictionary[@"providesAppNotificationSettings"] = @-1;
    }

    settingsDictionary[@"showPreviews"] = showPreviews;
    settingsDictionary[@"authorizationStatus"] = authorizedStatus;
    settingsDictionary[@"alertSetting"] = [self _numberForUNNotificationSetting:settings.alertSetting];
    settingsDictionary[@"soundSetting"] = [self _numberForUNNotificationSetting:settings.soundSetting];
    settingsDictionary[@"badgeSetting"] = [self _numberForUNNotificationSetting:settings.badgeSetting];
    settingsDictionary[@"carPlaySetting"] = [self _numberForUNNotificationSetting:settings.carPlaySetting];
    settingsDictionary[@"lockScreenSetting"] = [self _numberForUNNotificationSetting:settings.lockScreenSetting];
    settingsDictionary[@"notificationCenterSetting"] = [self _numberForUNNotificationSetting:settings.notificationCenterSetting];

    block(nil, settingsDictionary);
  }];
}


# pragma mark - Internals

- (NSNumber *)_numberForUNNotificationSetting:(UNNotificationSetting)setting {
  NSNumber *asNumber = @-1;
  if (setting == UNNotificationSettingNotSupported) {
    asNumber = @-1;
  } else if (setting == UNNotificationSettingDisabled) {
    asNumber = @0;
  } else if (setting == UNNotificationSettingEnabled) {
    asNumber = @1;
  }
  return asNumber;
}

@end
