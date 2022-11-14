import 'package:flutter/material.dart';
import 'package:notifee/notifee.dart';

class Accordion extends StatefulWidget {
  final String title;
  final NotifeeNotification notification;

  const Accordion({Key? key, required this.title, required this.notification})
      : super(key: key);
  @override
  _AccordionState createState() => _AccordionState();
}

Widget row(String context, value) {
  if (value == null || value == "null") {
    return const SizedBox(height: 0);
  } else {
    return Row(mainAxisAlignment: MainAxisAlignment.spaceEvenly, children: [
      Text(context),
      Expanded(
        child: Text(
          value,
        ),
      ),
    ]);
  }
}

class _AccordionState extends State<Accordion> {
  bool _showContent = false;
  @override
  Widget build(BuildContext context) {
    return Card(
      margin: const EdgeInsets.all(10),
      child: Column(children: [
        ListTile(
          title: Text(widget.title),
          trailing: IconButton(
            icon: Icon(
                _showContent ? Icons.arrow_drop_up : Icons.arrow_drop_down),
            onPressed: () {
              setState(() {
                _showContent = !_showContent;
              });
            },
          ),
        ),
        _showContent
            ? Container(
                padding:
                    const EdgeInsets.symmetric(vertical: 15, horizontal: 15),
                child: Wrap(
                  runSpacing: 10,
                  children: [
                    row("ID: ", widget.notification.id),
                    row("Channel ID: ", widget.notification.android?.channelId),
                    row(
                        "Importance: ",
                        widget.notification.android?.importance
                            .toString()
                            .split(".")
                            .last),
                    row("Body: ", widget.notification.body),
                    row("Subtitle: ", widget.notification.subtitle),
                    row("Data: ", widget.notification.data),
                    row("Color: ", widget.notification.android?.color),
                    row("Sound: ", widget.notification.android?.sound),
                    row("Visibility: ",
                        widget.notification.android?.visibility.toString()),
                  ],
                ),
              )
            : Container()
      ]),
    );
  }
}
