import 'dart:io' show Platform;

import 'package:example/common.dart';
import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';
import 'components/popover.dart';

/// Used by displayNotification() to demonstrate different [Channel] behaviour
enum ExampleAndroidChannelIds { horse, highImportance }

enum ExampleIOSCategoryIds { one, two }

String exampleSmallIcon = 'ic_launcher';
String selectedAndroidChannelId = ExampleAndroidChannelIds.horse.name;
String selectedIosCategoryId = ExampleIOSCategoryIds.one.name;
String body = 'body';
String title = 'title';
bool hasBody = false;

Map<String, NotifeeNotification> exampleNotifications = {
  'randomId': NotifeeNotification(title: title = 'randomId'),
  'basic': NotifeeNotification(id: 'basic', title: title = 'basic', body: body),
  'attachments': NotifeeNotification(
      id: 'attachments',
      title: title = 'attachments',
      ios: NotificationIOS(attachments: [
        IOSNotificationAttachment(
            id: 'attachment-cat',
            url:
                'https://icatcare.org/app/uploads/2018/07/Thinking-of-getting-a-cat.png')
      ])),
};

NotifeeNotification? selectedNotification = exampleNotifications['randomId'];

void checkNotificationType() {
  if (selectedNotification?.body != null) {
    hasBody = true;
  } else {
    hasBody = false;
  }
}

class CreateNotificationModal extends StatefulWidget {
  const CreateNotificationModal({Key? key}) : super(key: key);

  @override
  State<CreateNotificationModal> createState() =>
      _CreateNotificationModalState();
}

class _CreateNotificationModalState extends State<CreateNotificationModal> {
  void updateTitle(val) {
    title = val;
  }

  void updateBody(val) {
    body = val;
  }

  void updateNotificationType(val) {
    selectedNotification = exampleNotifications[val];
    checkNotificationType();
  }

  void updateChannel(val) {
    for (var i = 0; i < channelIds.length; i++) {
      if (channelIds[i] == val) {
        selectedAndroidChannelId = channelIds[i];
      }
    }
  }

  void updateCategory(val) {
    for (var i = 0; i < categoryIds.length; i++) {
      if (categoryIds[i] == val) {
        selectedIosCategoryId = categoryIds[i];
      }
    }
  }

  String channelsOrCategories = '';

  void updateNotification() {
    if (Platform.isAndroid) {
      channelsOrCategories = 'Channels';
      selectedNotification = NotifeeNotification(
        id: selectedAndroidChannelId,
        title: title,
        body: body,
      );
    } else if (Platform.isIOS) {
      channelsOrCategories = 'Categories';
      selectedNotification = NotifeeNotification(
        // id: selectedIosCategoryId,
        title: title,
        body: body,
      );
    }
    title = 'title';
    body = 'body';
  }

  Widget getDropdownField() {
    if (Platform.isAndroid) {
      return DropdownButtonFormField(
          value: channelIds[0],
          items: channelIds.map((id) {
            return DropdownMenuItem(
              value: id,
              child: Text(id.toString()),
            );
          }).toList(),
          onChanged: (value) {
            updateChannel(value);
          });
    } else if (Platform.isIOS) {
      return DropdownButtonFormField(
          value: categoryIds.first,
          items: categoryIds.map((id) {
            return DropdownMenuItem(
              value: id,
              child: Text(id.toString()),
            );
          }).toList(),
          onChanged: (value) {
            updateCategory(value);
          });
    } else {
      return const SizedBox(
        height: 0,
      );
    }
  }

  Widget getDropdownNotificationType() {
    var uniqueList = [];
    exampleNotifications.forEach(((key, value) {
      if (uniqueList.contains(value.title)) {
      } else {
        uniqueList.add(value.title);
      }
    }));

    if (uniqueList.isEmpty) {
      return const SizedBox(
        height: 0,
      );
    } else {
      return DropdownButtonFormField(
          value: uniqueList.first,
          items: uniqueList.map((id) {
            return DropdownMenuItem(
              value: id,
              child: Text(id.toString()),
            );
          }).toList(),
          onChanged: (value) {
            updateNotificationType(value);
          });
    }
  }

  Widget getTitleChannelsOrCategories() {
    if (Platform.isAndroid) {
      channelsOrCategories = 'Channels';
      return Text(channelsOrCategories);
    } else if (Platform.isIOS) {
      channelsOrCategories = 'Categories';
      return Text(channelsOrCategories);
    } else {
      return const SizedBox(
        height: 0,
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    updateChannelIds();
    updateCategoryIds();
    checkNotificationType();

    return Popover(
        child: SingleChildScrollView(
            child: Column(
      children: [
        Container(
          padding: const EdgeInsets.symmetric(
            horizontal: 24.0,
            vertical: 16.0,
          ),
          decoration: const BoxDecoration(
            border: Border(
              bottom: BorderSide(
                color: Colors.white30,
                width: 0.5,
              ),
            ),
          ),
          child: Column(children: [
            getTitleChannelsOrCategories(),
            const SizedBox(height: 10.0),
            getDropdownField(),
          ]),
        ),
        Container(
          padding: const EdgeInsets.symmetric(
            horizontal: 24.0,
            vertical: 16.0,
          ),
          decoration: const BoxDecoration(
            border: Border(
              bottom: BorderSide(
                color: Colors.white30,
                width: 0.5,
              ),
            ),
          ),
          child: Column(children: [
            const Text('Type of notifications'),
            const SizedBox(height: 10.0),
            getDropdownNotificationType()
          ]),
        ),
        Container(
          padding: const EdgeInsets.symmetric(
            horizontal: 24.0,
            vertical: 16.0,
          ),
          decoration: const BoxDecoration(
            border: Border(
              bottom: BorderSide(
                color: Colors.white30,
                width: 0.5,
              ),
            ),
          ),
          child: Column(children: [
            const Text('Title'),
            const SizedBox(height: 20.0),
            TextFormField(
              textAlignVertical: TextAlignVertical.center,
              decoration: const InputDecoration(
                  filled: true,
                  fillColor: Color.fromRGBO(83, 83, 83, 100),
                  border: OutlineInputBorder(),
                  contentPadding:
                      EdgeInsets.only(top: 0, bottom: 0, left: 10, right: 10)),
              initialValue: selectedNotification?.title,
              onChanged: (val) {
                updateTitle(val);
              },
            ),
          ]),
        ),
        Container(
          padding: const EdgeInsets.symmetric(
            horizontal: 24.0,
            vertical: 16.0,
          ),
          decoration: hasBody
              ? const BoxDecoration(
                  border: Border(
                    bottom: BorderSide(
                      color: Colors.white30,
                      width: 0.5,
                    ),
                  ),
                )
              : const BoxDecoration(),
          child: Column(children: [
            hasBody ? const Text('Body') : const SizedBox(height: 0),
            hasBody ? const SizedBox(height: 20.0) : const SizedBox(height: 0),
            hasBody
                ? TextFormField(
                    textAlignVertical: TextAlignVertical.center,
                    decoration: const InputDecoration(
                      filled: true,
                      fillColor: Color.fromRGBO(83, 83, 83, 100),
                      border: OutlineInputBorder(),
                      contentPadding: EdgeInsets.only(
                          top: 0, bottom: 0, left: 10, right: 10),
                    ),
                    initialValue: selectedNotification?.body,
                    onChanged: (val) {
                      updateBody(val);
                    },
                  )
                : const SizedBox(
                    height: 0,
                  ),
          ]),
        ),
        Container(
          padding: const EdgeInsets.symmetric(
            horizontal: 24.0,
            vertical: 16.0,
          ),
          child: Column(children: [
            ElevatedButton(
              child: const Text(
                'Update',
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () async {
                updateNotification();
              },
            )
          ]),
        ),
      ],
    )));
  }
}
