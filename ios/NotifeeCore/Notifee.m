//
//  Notifee.m
//  NotifeeCore
//
//  Created by Mike on 31/01/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Notifee.h"
#import <Intents/INIntentIdentifiers.h>

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
                    object:completionHandler
                  userInfo:@{@"response": response}
  ];
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


  UNTimeIntervalNotificationTrigger *trigger = [UNTimeIntervalNotificationTrigger
      triggerWithTimeInterval:5 repeats:NO];

  UNNotificationRequest *request = [UNNotificationRequest requestWithIdentifier:@"FiveSecond"
                                                                        content:content trigger:nil];

  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
  [center addNotificationRequest:request withCompletionHandler:block];
}

- (void)getNotificationCategoriesWithBlock:(notifeeMethodNSArrayBlock)block {
  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
  [center getNotificationCategoriesWithCompletionHandler:^(NSSet<UNNotificationCategory *> *categories) {
    // TODO parse into dictionary
    block(nil, nil);
  }];
}

- (void)setNotificationCategories:(NSArray<NSDictionary *> *)categories withBlock:(notifeeMethodVoidBlock)block {
  NSMutableSet *UNNotificationCategories = [[NSMutableSet alloc] init];

  for (NSDictionary *categoryDictionary in categories) {
    UNNotificationCategory *category;

    NSString *id = categoryDictionary[@"id"];
    NSString *summaryFormat = categoryDictionary[@"summaryFormat"];
    NSString *bodyPlaceHolder = categoryDictionary[@"hiddenPreviewsBodyPlaceholder"];

    NSArray<UNNotificationAction *> *actions = [self _notificationActionsFromDictionaryArray:categoryDictionary[@"actions"]];
    NSArray<NSString *> *intentIdentifiers = [self _intentIdentifiersFromNumberArray:categoryDictionary[@"intentIdentifiers"]];

    UNNotificationCategoryOptions options = UNNotificationCategoryOptionCustomDismissAction;

    if ([categoryDictionary[@"allowInCarPlay"] isEqual:@(YES)]) {
      options |= UNNotificationCategoryOptionAllowInCarPlay;
    }

    if (@available(iOS 11.0, *)) {
      if ([categoryDictionary[@"hiddenPreviewsShowTitle"] isEqual:@(YES)]) {
        options |= UNNotificationCategoryOptionHiddenPreviewsShowTitle;
      }

      if ([categoryDictionary[@"hiddenPreviewsShowSubtitle"] isEqual:@(YES)]) {
        options |= UNNotificationCategoryOptionHiddenPreviewsShowSubtitle;
      }
    }

    if (@available(iOS 13.0, *)) {
      if ([categoryDictionary[@"allowAnnouncement"] isEqual:@(YES)]) {
        options |= UNNotificationCategoryOptionAllowAnnouncement;
      }
    }

    if (@available(iOS 12.0, *)) {
      category = [
          UNNotificationCategory categoryWithIdentifier:id
                                                actions:actions
                                      intentIdentifiers:intentIdentifiers
                          hiddenPreviewsBodyPlaceholder:bodyPlaceHolder
                                  categorySummaryFormat:summaryFormat
                                                options:options
      ];
    } else if (@available(iOS 11.0, *)) {
      category = [
          UNNotificationCategory categoryWithIdentifier:id
                                                actions:actions
                                      intentIdentifiers:intentIdentifiers
                          hiddenPreviewsBodyPlaceholder:bodyPlaceHolder
                                                options:options
      ];
    } else {
      category = [
          UNNotificationCategory categoryWithIdentifier:id
                                                actions:actions
                                      intentIdentifiers:intentIdentifiers
                                                options:options
      ];
    }

    [UNNotificationCategories addObject:category];
  }

  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
  [center setNotificationCategories:UNNotificationCategories];
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


  if ([permissions[@"inAppNotificationSettings"] isEqual:@(YES)]) {
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
      settingsDictionary[@"announcement"] = [self _numberForUNNotificationSetting:settings.announcementSetting];
    } else {
      settingsDictionary[@"announcement"] = @-1;
    }

    if (@available(iOS 12.0, *)) {
      settingsDictionary[@"criticalAlert"] = [self _numberForUNNotificationSetting:settings.criticalAlertSetting];
    } else {
      settingsDictionary[@"criticalAlert"] = @-1;
    }

    if (@available(iOS 12.0, *)) {
      settingsDictionary[@"inAppNotificationSettings"] = settings.providesAppNotificationSettings ? @1 : @0;
    } else {
      settingsDictionary[@"inAppNotificationSettings"] = @-1;
    }

    settingsDictionary[@"showPreviews"] = showPreviews;
    settingsDictionary[@"authorizationStatus"] = authorizedStatus;
    settingsDictionary[@"alert"] = [self _numberForUNNotificationSetting:settings.alertSetting];
    settingsDictionary[@"badge"] = [self _numberForUNNotificationSetting:settings.badgeSetting];
    settingsDictionary[@"sound"] = [self _numberForUNNotificationSetting:settings.soundSetting];
    settingsDictionary[@"carPlay"] = [self _numberForUNNotificationSetting:settings.carPlaySetting];
    settingsDictionary[@"lockScreen"] = [self _numberForUNNotificationSetting:settings.lockScreenSetting];
    settingsDictionary[@"notificationCenter"] = [self _numberForUNNotificationSetting:settings.notificationCenterSetting];

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

- (NSMutableArray<UNNotificationAction *> *)_notificationActionsFromDictionaryArray:(NSArray<NSDictionary *> *)actionDictionaries {
  NSMutableArray<UNNotificationAction *> *notificationActions = [[NSMutableArray alloc] init];

  for (NSDictionary *actionDictionary in actionDictionaries) {
    UNNotificationAction *notificationAction;

    NSString *id = actionDictionary[@"id"];
    NSString *title = actionDictionary[@"title"];

    UNNotificationActionOptions options = 0;
    if (actionDictionary[@"options"] != nil) {
      NSDictionary *optionsDictionary = actionDictionary[@"options"];
      if ([optionsDictionary[@"destructive"] isEqual:@(YES)]) {
        options |= UNNotificationActionOptionDestructive;
      }
      if ([optionsDictionary[@"launchApp"] isEqual:@(YES)]) {
        options |= UNNotificationActionOptionForeground;
      }
      if ([optionsDictionary[@"authentication"] isEqual:@(YES)]) {
        options |= UNNotificationActionOptionAuthenticationRequired;
      }
    }

    if (actionDictionary[@"input"] != nil) {
      NSDictionary *inputDictionary = actionDictionary[@"input"];
      NSString *buttonText = inputDictionary[@"buttonText"];
      NSString *placeholderText = inputDictionary[@"placeholderText"];
      notificationAction = [
          UNTextInputNotificationAction actionWithIdentifier:id
                                                       title:title
                                                     options:options
                                        textInputButtonTitle:buttonText
                                        textInputPlaceholder:placeholderText
      ];
    } else {
      notificationAction = [UNNotificationAction actionWithIdentifier:id title:title options:options];
    }

    [notificationActions addObject:notificationAction];
  }

  return notificationActions;
}

- (NSMutableArray<NSString *> *)_intentIdentifiersFromNumberArray:(NSArray<NSNumber *> *)identifiers {
  NSMutableArray<NSString *> *intentIdentifiers = [[NSMutableArray alloc] init];

  for (NSNumber *identifier in identifiers) {
    if ([identifier isEqualToNumber:@0]) {
      // IOSIntentIdentifier.START_AUDIO_CALL
      [intentIdentifiers addObject:INStartAudioCallIntentIdentifier];
    } else if ([identifier isEqualToNumber:@1]) {
      // IOSIntentIdentifier.START_VIDEO_CALL
      [intentIdentifiers addObject:INStartVideoCallIntentIdentifier];
    } else if ([identifier isEqualToNumber:@2]) {
      // IOSIntentIdentifier.SEARCH_CALL_HISTORY
      [intentIdentifiers addObject:INSearchCallHistoryIntentIdentifier];
    } else if ([identifier isEqualToNumber:@3]) {
      // IOSIntentIdentifier.SET_AUDIO_SOURCE_IN_CAR
      [intentIdentifiers addObject:INSetAudioSourceInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@4]) {
      // IOSIntentIdentifier.SET_CLIMATE_SETTINGS_IN_CAR
      [intentIdentifiers addObject:INSetClimateSettingsInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@5]) {
      // IOSIntentIdentifier.SET_DEFROSTER_SETTINGS_IN_CAR
      [intentIdentifiers addObject:INSetDefrosterSettingsInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@6]) {
      // IOSIntentIdentifier.SET_SEAT_SETTINGS_IN_CAR
      [intentIdentifiers addObject:INSetSeatSettingsInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@7]) {
      // IOSIntentIdentifier.SET_PROFILE_IN_CAR
      [intentIdentifiers addObject:INSetProfileInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@8]) {
      // IOSIntentIdentifier.SAVE_PROFILE_IN_CAR
      [intentIdentifiers addObject:INSaveProfileInCarIntentIdentifier];
    } else if ([identifier isEqualToNumber:@9]) {
      // IOSIntentIdentifier.START_WORKOUT
      [intentIdentifiers addObject:INStartWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@10]) {
      // IOSIntentIdentifier.PAUSE_WORKOUT
      [intentIdentifiers addObject:INPauseWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@11]) {
      // IOSIntentIdentifier.END_WORKOUT
      [intentIdentifiers addObject:INEndWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@12]) {
      // IOSIntentIdentifier.CANCEL_WORKOUT
      [intentIdentifiers addObject:INCancelWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@13]) {
      // IOSIntentIdentifier.RESUME_WORKOUT
      [intentIdentifiers addObject:INResumeWorkoutIntentIdentifier];
    } else if ([identifier isEqualToNumber:@14]) {
      // IOSIntentIdentifier.SET_RADIO_STATION
      [intentIdentifiers addObject:INSetRadioStationIntentIdentifier];
    } else if ([identifier isEqualToNumber:@15]) {
      // IOSIntentIdentifier.SEND_MESSAGE
      [intentIdentifiers addObject:INSendMessageIntentIdentifier];
    } else if ([identifier isEqualToNumber:@16]) {
      // IOSIntentIdentifier.SEARCH_FOR_MESSAGES
      [intentIdentifiers addObject:INSearchForMessagesIntentIdentifier];
    } else if ([identifier isEqualToNumber:@17]) {
      // IOSIntentIdentifier.SET_MESSAGE_ATTRIBUTE
      [intentIdentifiers addObject:INSetMessageAttributeIntentIdentifier];
    } else if ([identifier isEqualToNumber:@18]) {
      // IOSIntentIdentifier.SEND_PAYMENT
      [intentIdentifiers addObject:INSendPaymentIntentIdentifier];
    } else if ([identifier isEqualToNumber:@19]) {
      // IOSIntentIdentifier.REQUEST_PAYMENT
      [intentIdentifiers addObject:INRequestPaymentIntentIdentifier];
    } else if ([identifier isEqualToNumber:@20]) {
      // IOSIntentIdentifier.SEARCH_FOR_PHOTOS
      [intentIdentifiers addObject:INSearchForPhotosIntentIdentifier];
    } else if ([identifier isEqualToNumber:@21]) {
      // IOSIntentIdentifier.START_PHOTO_PLAYBACK
      [intentIdentifiers addObject:INStartPhotoPlaybackIntentIdentifier];
    } else if ([identifier isEqualToNumber:@22]) {
      // IOSIntentIdentifier.LIST_RIDE_OPTIONS
      [intentIdentifiers addObject:INListRideOptionsIntentIdentifier];
    } else if ([identifier isEqualToNumber:@23]) {
      // IOSIntentIdentifier.REQUEST_RIDE
      [intentIdentifiers addObject:INRequestRideIntentIdentifier];
    } else if ([identifier isEqualToNumber:@24]) {
      // IOSIntentIdentifier.GET_RIDE_STATUS
      [intentIdentifiers addObject:INGetRideStatusIntentIdentifier];
    }
  }

  return intentIdentifiers;
}

@end
