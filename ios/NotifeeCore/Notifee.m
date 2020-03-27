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
    sharedInstance.completionHandlers = [NSMutableDictionary dictionary];
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
    completionHandler(UNNotificationPresentationOptionAlert);
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
// TODO in RN Module; sub to above event and emit 'action' event to JS
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
  NSDictionary *iosDict = notification[@"ios"];
  UNMutableNotificationContent *content = [[UNMutableNotificationContent alloc] init];

  // title
  if (notification[@"title"] != nil) {
    content.title = notification[@"title"];
  }

  // subtitle
  if (notification[@"subtitle"] != nil) {
    content.subtitle = notification[@"subtitle"];
  }

  // body
  if (notification[@"body"] != nil) {
    content.body = notification[@"body"];
  }

  // data
//  NSMutableDictionary *userInfo = [[NSMutableDictionary dictionary] initWithDictionary:notification[@"data"]];
//  // attach a copy of the original notification payload into the data object
//  userInfo[kNotifeeUserInfoNotification] = [notification copy];
//  content.userInfo = userInfo;


  // attachments
  // TODO attachments handling

  // badgeCount - nil is an acceptable value so no need to check key existence
  content.badge = iosDict[@"badgeCount"];

  // categoryId
  if (iosDict[@"categoryId"] != nil) {
    content.categoryIdentifier = iosDict[@"categoryId"];
  }

  // launchImageName
  if (iosDict[@"launchImageName"] != nil) {
    content.launchImageName = iosDict[@"launchImageName"];
  }

  // sound
  if (iosDict[@"sound"] != nil) {
    UNNotificationSound *notificationSound;
    NSDictionary *soundDict = iosDict[@"notification"];
    NSString *soundName = soundDict[@"name"];

    if (@available(iOS 12.0, *)) {
      BOOL criticalSound = [soundDict[@"critical"] boolValue];
      NSNumber *criticalSoundVolume = soundDict[@"criticalVolume"];

      if ([soundName isEqualToString:@"default"]) {
        if (criticalSound) {
          if (criticalSoundVolume != nil) {
            notificationSound = [UNNotificationSound defaultCriticalSoundWithAudioVolume:[criticalSoundVolume floatValue]];
          } else {
            notificationSound = [UNNotificationSound defaultCriticalSound];
          }
        } else {
          notificationSound = [UNNotificationSound defaultSound];
        }
      } else {
        if (criticalSound) {
          if (criticalSoundVolume != nil) {
            notificationSound = [UNNotificationSound criticalSoundNamed:soundName withAudioVolume:[criticalSoundVolume floatValue]];
          } else {
            notificationSound = [UNNotificationSound criticalSoundNamed:soundName];
          }
        } else {
          notificationSound = [UNNotificationSound soundNamed:soundName];
        }
      }
    } else {
      if ([soundName isEqualToString:@"default"]) {
        notificationSound = [UNNotificationSound defaultSound];
      } else {
        notificationSound = [UNNotificationSound soundNamed:soundName];
      }
    }

    content.sound = notificationSound;
  } // sound

  // threadId
  if (iosDict[@"threadId"] != nil) {
    content.threadIdentifier = iosDict[@"threadId"];
  }

  if (@available(iOS 12.0, *)) {
    // summaryArgument
    if (iosDict[@"summaryArgument"] != nil) {
      content.summaryArgument = iosDict[@"summaryArgument"];
    }

    // summaryArgumentCount
    if (iosDict[@"summaryArgumentCount"] != nil) {
      content.summaryArgument = iosDict[@"summaryArgumentCount"];
    }
  }

  if (@available(iOS 13.0, *)) {
    // targetContentId
    if (iosDict[@"targetContentId"] != nil) {
      content.targetContentIdentifier = iosDict[@"targetContentId"];
    }
  }

  UNNotificationRequest *request = [UNNotificationRequest requestWithIdentifier:notification[@"id"] content:content trigger:nil];
  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
  [center addNotificationRequest:request withCompletionHandler:block];
}

- (void)getNotificationCategoriesWithBlock:(notifeeMethodNSArrayBlock)block {
  UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
  [center getNotificationCategoriesWithCompletionHandler:^(NSSet<UNNotificationCategory *> *categories) {
    NSMutableArray<NSDictionary *> *categoriesArray = [[NSMutableArray alloc] init];

    for (UNNotificationCategory *notificationCategory in categories) {
      NSMutableDictionary *categoryDictionary = [NSMutableDictionary dictionary];

      categoryDictionary[@"id"] = notificationCategory.identifier;
      categoryDictionary[@"allowInCarPlay"] = @(((notificationCategory.options & UNNotificationCategoryOptionAllowInCarPlay) != 0));

      if (@available(iOS 11.0, *)) {
        categoryDictionary[@"hiddenPreviewsShowTitle"] = @(((notificationCategory.options & UNNotificationCategoryOptionHiddenPreviewsShowTitle) != 0));
        categoryDictionary[@"hiddenPreviewsShowSubtitle"] = @(((notificationCategory.options & UNNotificationCategoryOptionHiddenPreviewsShowSubtitle) != 0));
        if (notificationCategory.hiddenPreviewsBodyPlaceholder != nil) {
          categoryDictionary[@"hiddenPreviewsBodyPlaceholder"] = notificationCategory.hiddenPreviewsBodyPlaceholder;
        }
      } else {
        categoryDictionary[@"hiddenPreviewsShowTitle"] = @(NO);
        categoryDictionary[@"hiddenPreviewsShowSubtitle"] = @(NO);
      }

      if (@available(iOS 12.0, *)) {
        if (notificationCategory.categorySummaryFormat != nil) {
          categoryDictionary[@"summaryFormat"] = notificationCategory.categorySummaryFormat;
        }
      }

      if (@available(iOS 13.0, *)) {
        categoryDictionary[@"allowAnnouncement"] = @(((notificationCategory.options & UNNotificationCategoryOptionAllowAnnouncement) != 0));
      } else {
        categoryDictionary[@"allowAnnouncement"] = @(NO);
      }

      categoryDictionary[@"actions"] = [self _notificationActionsToDictionaryArray:notificationCategory.actions];
      categoryDictionary[@"intentIdentifiers"] = [self _intentIdentifiersFromStringArray:notificationCategory.intentIdentifiers];

      [categoriesArray addObject:categoryDictionary];
    }

    block(nil, categoriesArray);
  }];
}

/**
 * Builds and replaces the existing notification categories on UNUserNotificationCenter
 *
 * @param categories NSArray<NSDictionary *> *
 * @param block notifeeMethodVoidBlock
 */
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
    
  if ([permissions[@"criticalAlert"] isEqual:@(YES)]) {
    if (@available(iOS 12.0, *)) {
      options |= UNAuthorizationOptionCriticalAlert;
    }
  }

  id handler = ^(BOOL granted, NSError *_Nullable error) {
      if (error != nil) {
          NSLog(error.localizedDescription);
      }
      
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

- (NSMutableArray<NSDictionary *> *)_notificationActionsToDictionaryArray:(NSArray<UNNotificationAction *> *)notificationActions {
  NSMutableArray<NSDictionary *> *notificationActionDicts = [[NSMutableArray alloc] init];

  for (UNNotificationAction *notificationAction in notificationActions) {
    NSMutableDictionary *notificationActionDict = [NSMutableDictionary dictionary];

    notificationActionDict[@"id"] = notificationAction.identifier;
    notificationActionDict[@"title"] = notificationAction.title;

    notificationActionDict[@"destructive"] = @(((notificationAction.options & UNNotificationActionOptionDestructive) != 0));
    notificationActionDict[@"foreground"] = @(((notificationAction.options & UNNotificationActionOptionForeground) != 0));
    notificationActionDict[@"authenticationRequired"] = @(((notificationAction.options & UNNotificationActionOptionAuthenticationRequired) != 0));

    if ([[notificationAction class] isKindOfClass:[UNTextInputNotificationAction class]]) {
      UNTextInputNotificationAction *notificationInputAction = (UNTextInputNotificationAction *) notificationAction;
      if ([notificationInputAction textInputButtonTitle] == nil && [notificationInputAction textInputPlaceholder] == nil) {
        notificationActionDict[@"input"] = @(YES);
      } else {
        NSMutableDictionary *inputDict = [NSMutableDictionary dictionary];
        inputDict[@"buttonText"] = [notificationInputAction textInputButtonTitle];
        inputDict[@"placeholderText"] = [notificationInputAction textInputPlaceholder];
        notificationActionDict[@"input"] = inputDict;
      }
    } else {
      notificationActionDict[@"input"] = @(NO);
    }

    [notificationActionDicts addObject:notificationActionDict];
  }

  return notificationActionDicts;
}

- (NSMutableArray<UNNotificationAction *> *)_notificationActionsFromDictionaryArray:(NSArray<NSDictionary *> *)actionDictionaries {
  NSMutableArray<UNNotificationAction *> *notificationActions = [[NSMutableArray alloc] init];

  for (NSDictionary *actionDictionary in actionDictionaries) {
    UNNotificationAction *notificationAction;

    NSString *id = actionDictionary[@"id"];
    NSString *title = actionDictionary[@"title"];

    UNNotificationActionOptions options = 0;

    if ([actionDictionary[@"destructive"] isEqual:@(YES)]) {
      options |= UNNotificationActionOptionDestructive;
    }

    if ([actionDictionary[@"foreground"] isEqual:@(YES)]) {
      options |= UNNotificationActionOptionForeground;
    }

    if ([actionDictionary[@"authenticationRequired"] isEqual:@(YES)]) {
      options |= UNNotificationActionOptionAuthenticationRequired;
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

- (NSMutableArray<NSNumber *> *)_intentIdentifiersFromStringArray:(NSArray<NSString *> *)identifiers {
  NSMutableArray<NSNumber *> *intentIdentifiers = [[NSMutableArray alloc] init];

  for (NSString *identifier in identifiers) {
    if ([identifier isEqualToString:INStartAudioCallIntentIdentifier]) {
      // IOSIntentIdentifier.START_AUDIO_CALL
      [intentIdentifiers addObject:@0];
    } else if ([identifier isEqualToString:INStartVideoCallIntentIdentifier]) {
      // IOSIntentIdentifier.START_VIDEO_CALL
      [intentIdentifiers addObject:@1];
    } else if ([identifier isEqualToString:INSearchCallHistoryIntentIdentifier]) {
      // IOSIntentIdentifier.SEARCH_CALL_HISTORY
      [intentIdentifiers addObject:@2];
    } else if ([identifier isEqualToString:INSetAudioSourceInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_AUDIO_SOURCE_IN_CAR
      [intentIdentifiers addObject:@3];
    } else if ([identifier isEqualToString:INSetClimateSettingsInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_CLIMATE_SETTINGS_IN_CAR
      [intentIdentifiers addObject:@4];
    } else if ([identifier isEqualToString:INSetDefrosterSettingsInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_DEFROSTER_SETTINGS_IN_CAR
      [intentIdentifiers addObject:@5];
    } else if ([identifier isEqualToString:INSetSeatSettingsInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_SEAT_SETTINGS_IN_CAR
      [intentIdentifiers addObject:@6];
    } else if ([identifier isEqualToString:INSetProfileInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SET_PROFILE_IN_CAR
      [intentIdentifiers addObject:@7];
    } else if ([identifier isEqualToString:INSaveProfileInCarIntentIdentifier]) {
      // IOSIntentIdentifier.SAVE_PROFILE_IN_CAR
      [intentIdentifiers addObject:@8];
    } else if ([identifier isEqualToString:INStartWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.START_WORKOUT
      [intentIdentifiers addObject:@9];
    } else if ([identifier isEqualToString:INPauseWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.PAUSE_WORKOUT
      [intentIdentifiers addObject:@10];
    } else if ([identifier isEqualToString:INEndWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.END_WORKOUT
      [intentIdentifiers addObject:@11];
    } else if ([identifier isEqualToString:INCancelWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.CANCEL_WORKOUT
      [intentIdentifiers addObject:@12];
    } else if ([identifier isEqualToString:INResumeWorkoutIntentIdentifier]) {
      // IOSIntentIdentifier.RESUME_WORKOUT
      [intentIdentifiers addObject:@13];
    } else if ([identifier isEqualToString:INSetRadioStationIntentIdentifier]) {
      // IOSIntentIdentifier.SET_RADIO_STATION
      [intentIdentifiers addObject:@14];
    } else if ([identifier isEqualToString:INSendMessageIntentIdentifier]) {
      // IOSIntentIdentifier.SEND_MESSAGE
      [intentIdentifiers addObject:@15];
    } else if ([identifier isEqualToString:INSearchForMessagesIntentIdentifier]) {
      // IOSIntentIdentifier.SEARCH_FOR_MESSAGES
      [intentIdentifiers addObject:@16];
    } else if ([identifier isEqualToString:INSetMessageAttributeIntentIdentifier]) {
      // IOSIntentIdentifier.SET_MESSAGE_ATTRIBUTE
      [intentIdentifiers addObject:@17];
    } else if ([identifier isEqualToString:INSendPaymentIntentIdentifier]) {
      // IOSIntentIdentifier.SEND_PAYMENT
      [intentIdentifiers addObject:@18];
    } else if ([identifier isEqualToString:INRequestPaymentIntentIdentifier]) {
      // IOSIntentIdentifier.REQUEST_PAYMENT
      [intentIdentifiers addObject:@19];
    } else if ([identifier isEqualToString:INSearchForPhotosIntentIdentifier]) {
      // IOSIntentIdentifier.SEARCH_FOR_PHOTOS
      [intentIdentifiers addObject:@20];
    } else if ([identifier isEqualToString:INStartPhotoPlaybackIntentIdentifier]) {
      // IOSIntentIdentifier.START_PHOTO_PLAYBACK
      [intentIdentifiers addObject:@21];
    } else if ([identifier isEqualToString:INListRideOptionsIntentIdentifier]) {
      // IOSIntentIdentifier.LIST_RIDE_OPTIONS
      [intentIdentifiers addObject:@22];
    } else if ([identifier isEqualToString:INRequestRideIntentIdentifier]) {
      // IOSIntentIdentifier.REQUEST_RIDE
      [intentIdentifiers addObject:@23];
    } else if ([identifier isEqualToString:INGetRideStatusIntentIdentifier]) {
      // IOSIntentIdentifier.GET_RIDE_STATUS
      [intentIdentifiers addObject:@24];
    }
  }

  return intentIdentifiers;
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
