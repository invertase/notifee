//
//  NotificationViewController.m
//  NotifeeContentExtension
//
//  Created by Mike on 07/02/2020.
//  Copyright © 2020 Invertase. All rights reserved.
//

#import "NotificationViewController.h"
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
#import <UserNotifications/UserNotifications.h>
#import <UserNotificationsUI/UserNotificationsUI.h>

@interface NotificationViewController () <UNNotificationContentExtension>

@end

@implementation NotificationViewController

- (void)loadView {
  NSLog(@"NOTIFEE_EXTENSION_LOAD_VIEW");

  [[RCTBundleURLProvider sharedSettings] setEnableDev:NO];
  [[RCTBundleURLProvider sharedSettings] setEnableLiveReload:NO];
  NSURL *jsCodeLocation;
  jsCodeLocation = [[RCTBundleURLProvider sharedSettings] jsBundleURLForBundleRoot:@"index"
                                                                  fallbackResource:nil];

  RCTRootView *rootView = [[RCTRootView alloc]
      initWithBundleURL:jsCodeLocation
             moduleName:@"notifee_notification"
      initialProperties:@{@"testing" : @[ @{@"123" : @"456", @"789" : @"0123"} ]}
          launchOptions:nil];
  rootView.backgroundColor = [[UIColor alloc] initWithRed:1.0f green:1.0f blue:1.0f alpha:1];
  [self setView:rootView];
}

- (void)didReceiveNotification:(UNNotification *)notification {
  NSLog(@"NOTIFEE_EXTENSION_RECEIVED_REMOTE");
  //  self.label.text = notification.request.content.body;
}

@end
