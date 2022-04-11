/*
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

library notifee_platform_interface;

export 'src/platform_interface/platform_interface_notifee.dart';

export 'src/method_channel/types.dart';

export 'src/models/event/event.dart';
export 'src/models/event/event_detail.dart';
export 'src/models/event/event_type.dart';

export 'src/models/initial_notification.dart';
export 'src/models/trigger_notification.dart';

export 'src/models/notification/notification_android.dart';
export 'src/models/notification/notification_ios.dart';
export 'src/models/notification/notification.dart';
export 'src/models/notification/notification_settings.dart';

export 'src/models/trigger/interval_trigger.dart';
export 'src/models/trigger/timestamp_trigger.dart';
export 'src/models/trigger/trigger_type.dart';

export 'src/models/notification/ios/ios_notification_permissions.dart';
export 'src/models/notification/ios/ios_notification_attachment.dart';
export 'src/models/notification/ios/ios_attachment_thumbnail_clipping_rect.dart';
export 'src/models/notification/ios/foreground_presentation_options.dart';
export 'src/models/notification/ios/ios_notification_category.dart';
export 'src/models/notification/ios/ios_notification_category_action.dart';
export 'src/models/notification/ios/ios_enums.dart';

export 'src/models/notification/android/channel.dart';
export 'src/models/notification/android/channel_group.dart';
export 'src/models/notification/android/notification_press_action.dart';
export 'src/models/notification/android/android_enums.dart';
