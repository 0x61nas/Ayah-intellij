package com.anas.intellij.plugins.ayah;

import com.anas.alqurancloudapi.Ayah;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/18/22
 */
public class AyahStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        System.out.println("Hello World");

//        Messages.showDialog(project, "Hi yoo", "My First Message Yooo", new String[]{"Ok"}, 0, Messages.getInformationIcon());
/*
        new Notification("com.anas.intellij.plugins.ayah.notificationGroup", "My First Notification",
                "My First Notification", NotificationType.INFORMATION).notify(project);
*/
        try {
            final var rAyah = Ayah.getRandomAyah();
            NotificationGroupManager.getInstance()
                    .getNotificationGroup("com.anas.intellij.plugins.ayah.notificationGroup")
                    .createNotification(rAyah.getSurah().getName(),
                            rAyah.getText(), NotificationType.INFORMATION).notify(project);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
