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

import 'package:notifee_platform_interface/notifee_platform_interface.dart';
import '../../../utils/generate_id.dart';

class IOSNotificationAttachment {
  IOSNotificationAttachment(
      {required this.url,
      this.thumbnailClippingRect,
      this.thumbnailHidden = false,
      this.id,
      this.typeHint,
      this.thumbnailTime}) {
    id ??= GenerateId.generateId();
  }

  String url;
  bool? thumbnailHidden;
  String? id;
  String? typeHint;
  int? thumbnailTime;
  IOSAttachmentThumbnailClippingRect? thumbnailClippingRect;

  factory IOSNotificationAttachment.fromMap(Map<String, dynamic> map) =>
      IOSNotificationAttachment(
        url: map['url'] as String,
        thumbnailClippingRect: map['thumbnailClippingRect'] == null
            ? null
            : IOSAttachmentThumbnailClippingRect.fromMap(
                Map<String, dynamic>.from(map['thumbnailClippingRect'] as Map)),
        thumbnailHidden: map['thumbnailHidden'] as bool? ?? false,
        id: map['id'] as String?,
        typeHint: map['typeHint'] as String?,
        thumbnailTime: map['thumbnailTime'] as int?,
      );

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'url': url,
      'thumbnailHidden': thumbnailHidden,
      'typeHint': typeHint,
      'thumbnailTime': thumbnailTime,
      'thumbnailClippingRect': thumbnailClippingRect?.asMap(),
      'id': id,
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
