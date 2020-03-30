//
//  Notifee.m
//  NotifeeCore
//
//  Created by Mike on 31/01/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "Public/NotifeeCore.h"

#import "Private/NotifeeCoreUtil.h"
#import "Private/NotifeeCoreDelegateHolder.h"
#import "Private/NotifeeCore+UNUserNotificationCenter.h"

@implementation NotifeeCore

# pragma mark - Library Methods

+ (void)setCoreDelegate:(id <NotifeeCoreDelegate>)coreDelegate {
  [NotifeeCoreDelegateHolder instance].delegate = coreDelegate;
}

/**
 * Cancel a currently displayed or scheduled notification.
 *
 * @param notificationId NSString id of the notification to cancel
 * @param block notifeeMethodVoidBlock
 */
+ (void)cancelNotification:(NSString *)notificationId withBlock:(notifeeMethodVoidBlock)block {
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
+ (void)displayNotification:(NSDictionary *)notification withBlock:(notifeeMethodVoidBlock)block {
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
  NSMutableDictionary *userInfo = [notification[@"data"] mutableCopy];
  // attach a copy of the original notification payload into the data object, for internal use
  userInfo[kNotifeeUserInfoNotification] = [notification mutableCopy];
  content.userInfo = userInfo;

  // attachments
  // TODO attachments handling in a later release

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

  // critical, criticalVolume, sound
  if (@available(iOS 12.0, *) && iosDict[@"critical"] != nil) {
    UNNotificationSound *notificationSound;
    BOOL criticalSound = [iosDict[@"critical"] boolValue];
    NSNumber *criticalSoundVolume = iosDict[@"criticalVolume"];
    NSString *soundName = iosDict[@"sound"] != nil ? iosDict[@"sound"] : @"default";

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

    content.sound = notificationSound;
  } else if (iosDict[@"sound"] != nil) {
    UNNotificationSound *notificationSound;
    NSString *soundName = iosDict[@"sound"];

    if ([soundName isEqualToString:@"default"]) {
      notificationSound = [UNNotificationSound defaultSound];
    } else {
      notificationSound = [UNNotificationSound soundNamed:soundName];
    }

    content.sound = notificationSound;
  } // critical, criticalVolume, sound

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
  [center addNotificationRequest:request withCompletionHandler:^(NSError *error) {
    if (error == nil) {
      [[NotifeeCoreDelegateHolder instance] didReceiveNotifeeCoreEvent:@{
          @"type": @3, // DELIVERED = 3
          @"detail": @{
              @"notification": notification,
          }
      }];
    }
    block(error);
  }];
}

+ (void)getNotificationCategories:(notifeeMethodNSArrayBlock)block {
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

      categoryDictionary[@"actions"] = [NotifeeCoreUtil notificationActionsToDictionaryArray:notificationCategory.actions];
      categoryDictionary[@"intentIdentifiers"] = [NotifeeCoreUtil intentIdentifiersFromStringArray:notificationCategory.intentIdentifiers];

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
+ (void)setNotificationCategories:(NSArray<NSDictionary *> *)categories withBlock:(notifeeMethodVoidBlock)block {
  NSMutableSet *UNNotificationCategories = [[NSMutableSet alloc] init];

  for (NSDictionary *categoryDictionary in categories) {
    UNNotificationCategory *category;

    NSString *id = categoryDictionary[@"id"];
    NSString *summaryFormat = categoryDictionary[@"summaryFormat"];
    NSString *bodyPlaceHolder = categoryDictionary[@"hiddenPreviewsBodyPlaceholder"];

    NSArray<UNNotificationAction *> *actions = [NotifeeCoreUtil notificationActionsFromDictionaryArray:categoryDictionary[@"actions"]];
    NSArray<NSString *> *intentIdentifiers = [NotifeeCoreUtil intentIdentifiersFromNumberArray:categoryDictionary[@"intentIdentifiers"]];

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
  block(nil);
}

/**
 * Request UNAuthorizationOptions for user notifications.
 * Resolves a NSDictionary representation of UNNotificationSettings.
 *
 * @param permissions NSDictionary
 * @param block NSDictionary block
 */
+ (void)requestPermission:(NSDictionary *)permissions withBlock:(notifeeMethodNSDictionaryBlock)block {
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
      // TODO send error to notifeeMethodNSDictionaryBlock
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
+ (void)getNotificationSettings:(notifeeMethodNSDictionaryBlock)block {
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
      settingsDictionary[@"announcement"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.announcementSetting];
    } else {
      settingsDictionary[@"announcement"] = @-1;
    }

    if (@available(iOS 12.0, *)) {
      settingsDictionary[@"criticalAlert"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.criticalAlertSetting];
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
    settingsDictionary[@"alert"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.alertSetting];
    settingsDictionary[@"badge"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.badgeSetting];
    settingsDictionary[@"sound"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.soundSetting];
    settingsDictionary[@"carPlay"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.carPlaySetting];
    settingsDictionary[@"lockScreen"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.lockScreenSetting];
    settingsDictionary[@"notificationCenter"] = [NotifeeCoreUtil numberForUNNotificationSetting:settings.notificationCenterSetting];

    block(nil, settingsDictionary);
  }];
}

+ (void)getInitialNotification:(notifeeMethodNSDictionaryBlock)block {
  block(nil, [[NotifeeCoreUNUserNotificationCenter instance] getInitialNotification]);
}

@end
