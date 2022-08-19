package com.anas.intellij.plugins.ayah;

import com.anas.alqurancloudapi.Ayah;
import com.anas.intellij.plugins.ayah.audio.AudioPlayer;
import com.anas.intellij.plugins.ayah.settings.AyahSettingsState;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
public class NotificationTimerTask extends TimerTask {
    private Project project;
    @Override
    public void run() {
        final var settings = AyahSettingsState.getInstance();

        try {
            final var randomAyah = Ayah.getRandomAyah();

            // Set up the notification.
            final var notification = new Notification("Random Ayah Notification",
                    randomAyah.getSurah().getName(),  randomAyah.getText(), NotificationType.INFORMATION);

            notification.addAction(new AnAction() {
                @Override
                public void actionPerformed(@NotNull AnActionEvent e) {
                    System.out.println("Action performed");
                }
            });

            // Show notification
            notification.notify(project);

            // Play sound if enabled.
            if (settings.isAutoPlayAudio()) {
                new AudioPlayer(settings.getVolume(), randomAyah.getAudioUrl()).play();
            }

        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void setProject(final Project project) {
        this.project = project;
    }
}
