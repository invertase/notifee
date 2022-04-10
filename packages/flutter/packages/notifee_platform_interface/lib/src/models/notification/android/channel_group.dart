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

class ChannelGroup {
  ChannelGroup({required this.id, required this.name, this.description});

  factory ChannelGroup.fromMap(Map<String, dynamic> map) {
    return ChannelGroup(
        id: map['id'] as String,
        name: map['name'] as String,
        description: map['description'] as String?);
  }

  String id;
  String name;
  String? description;

  Map<String, Object?> asMap() {
    Map<String, Object?> map = {
      'id': id,
      'name': name,
      'description': description,
    };

    map.removeWhere((_, value) => value == null);

    return map;
  }
}
