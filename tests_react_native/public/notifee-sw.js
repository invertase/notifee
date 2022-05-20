self.addEventListener('message', function (event) {
  if (!event.data.notifee) {
    return;
  }

  self.registration.getNotifications().then(notifications => {
    switch (event.data.notifee) {
      case 'get_displayed_notifications':
        self.clients.matchAll().then(clients => {
          clients.forEach(client =>
            client.postMessage({
              notifications: JSON.stringify(notifications.map(x => x.data.displayedNotification)),
            }),
          );
        });
        break;
      case 'cancel_displayed_notification':
        const notification = notifications.find(
          n => n.data.displayedNotification.id === event.data.notificationId,
        );
        if (notification) {
          notification.close();
        }
        break;
      case 'cancel_notifications_with_ids':
        notifications.forEach(_notification => {
          if (!event.data.notificationIds.includes(_notification.data.displayedNotification.id)) {
            return;
          }
          _notification.close();
        });
        break;
      case 'cancel_displayed_notifications':
        notifications.forEach(_notification => {
          _notification.close();
        });
        break;
    }
  });
});
