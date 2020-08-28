package com.github.plutobe.plugin.thrift.notifier;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-23 21:21
 */
public class MessageNotifier {

    public static void notice(String message, MessageType messageType) {
        NotificationGroup notificationGroup = new NotificationGroup("ThriftPluginMessageNotifier", NotificationDisplayType.BALLOON);
        Notification notification = notificationGroup.createNotification(message, messageType);
        Notifications.Bus.notify(notification);
    }

}
