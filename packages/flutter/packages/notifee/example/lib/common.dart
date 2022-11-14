import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;

List<String> channelIds = [];
List<String> categoryIds = [];

void updateChannelIds() async {
  List<Channel> channels = await notifee.getChannels();
  for (var i = 0; i < channels.length; i++) {
    if (!channelIds.contains(channels[i].name.toString())) {
      channelIds.add(channels[i].name.toString());
    }
  }
}

void updateCategoryIds() async {
  final categories = await notifee.getNotificationCategories();

  for (var i = 0; i < categories.length; i++) {
    if (!categoryIds.contains(categories[i].id.toString())) {
      categoryIds.add(categories[i].id.toString());
    }
  }
}
