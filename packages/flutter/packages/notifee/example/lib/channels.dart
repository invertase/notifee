import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';
import 'package:notifee/notifee.dart' as notifee;
import 'common.dart';

class Channels extends StatefulWidget {
  const Channels({Key? key}) : super(key: key);

  @override
  State<Channels> createState() => _ChannelsState();
}

class _ChannelsState extends State<Channels> {
  List<String> channelList = [];
  String channelID = '';
  String channelName = '';
  bool channelBadge = false;
  bool channelBypassDnd = false;
  bool channelLights = false;
  bool channelVibration = false;
  AndroidImportance channelImportance = AndroidImportance.none;
  String channelSound = '';
  List<String> importanceList = [];

  void initState() {
    super.initState();

    final importance = notifee.AndroidImportance.values;
    for (var i = 0; i < importance.length; i++) {
      importanceList.add(importance[i].name.toString());
    }
  }

  final fieldTextChannelId = TextEditingController();
  final fieldTextChannelName = TextEditingController();

  @override
  void setChannel() async {
    Channel channel = Channel(
      id: channelID,
      name: channelName,
      badge: channelBadge,
      bypassDnd: channelBypassDnd,
      lights: channelLights,
      vibration: channelVibration,
      importance: channelImportance,
      // sound: channelSound,
    );

    await notifee.createChannel(channel);

    clearText();
    updateChannelIds();
  }

  void setImportance(value) {
    for (var i = 0; i < notifee.AndroidImportance.values.length; i++) {
      if (notifee.AndroidImportance.values[i].toString() == value.toString()) {
        channelImportance = AndroidImportance.values[i];
      }
    }
  }

  void clearText() {
    fieldTextChannelId.clear();
    fieldTextChannelName.clear();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Create Channels"),
      ),
      body: Container(
        width: double.infinity,
        margin: const EdgeInsets.only(left: 8, right: 8, top: 8),
        child: SingleChildScrollView(
          child: Column(
            children: [
              Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    children: [
                      const Text("Enter channel id",
                          style: TextStyle(fontSize: 18)),
                      const SizedBox(height: 20.0),
                      TextFormField(
                        onChanged: (val) => channelID = val,
                        controller: fieldTextChannelId,
                      ),
                      const SizedBox(height: 20.0),
                      const Text("Enter channel name",
                          style: TextStyle(fontSize: 18)),
                      const SizedBox(height: 20.0),
                      TextFormField(
                        onChanged: (val) => channelName = val,
                        controller: fieldTextChannelName,
                      ),
                      const SizedBox(height: 20.0),
                      CheckboxListTile(
                          value: channelBadge,
                          activeColor: Colors.blue,
                          title: const Text("Badge?"),
                          shape: const CircleBorder(),
                          onChanged: (bool? value) {
                            setState(() {
                              channelBadge = value!;
                            });
                          }),
                      CheckboxListTile(
                          value: channelBypassDnd,
                          activeColor: Colors.blue,
                          title: const Text("Bypass Dnd?"),
                          shape: const CircleBorder(),
                          onChanged: (bool? value) {
                            setState(() {
                              channelBypassDnd = value!;
                            });
                          }),
                      CheckboxListTile(
                          value: channelLights,
                          activeColor: Colors.blue,
                          title: const Text("Channel lights?"),
                          shape: const CircleBorder(),
                          onChanged: (bool? value) {
                            setState(() {
                              channelLights = value!;
                            });
                          }),
                      CheckboxListTile(
                          value: channelVibration,
                          activeColor: Colors.blue,
                          title: const Text("Vibration?"),
                          shape: const CircleBorder(),
                          onChanged: (bool? value) {
                            setState(() {
                              channelVibration = value!;
                            });
                          }),
                      const SizedBox(height: 20.0),
                      const Text("Enter channel importance",
                          style: TextStyle(fontSize: 18)),
                      const SizedBox(height: 20.0),
                      DropdownButtonFormField(
                          value: 'auto',
                          items: importanceList.map((name) {
                            return DropdownMenuItem(
                              value: name,
                              child: Text(name),
                            );
                          }).toList(),
                          onChanged: (value) => setImportance(value)),
                      const SizedBox(height: 20.0),
                      ElevatedButton(
                        child: const Text(
                          'Create',
                          style: TextStyle(color: Colors.white),
                        ),
                        onPressed: () => setChannel(),
                      )
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
