package com.anas.intellij.plugins.ayah;

import com.anas.alqurancloudapi.Ayah;
import com.anas.intellij.plugins.ayah.audio.AudioPlayer;
import com.anas.intellij.plugins.ayah.settings.AyahSettingsState;
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
    public void runActivity(@NotNull final Project project) {
        final var basmalhOnStartSettingsState = AyahSettingsState.getInstance().getBasmalhOnStart();
        // Basmalh on start
        if (basmalhOnStartSettingsState.isActive()) {
            try {
                final var bassmalh = Ayah.getAyah(1,
                        basmalhOnStartSettingsState.getPlayerId());
                NotificationGroupManager.getInstance()
                        .getNotificationGroup("Basmalh on Start")
                        .createNotification(bassmalh.getText(), NotificationType.INFORMATION).notify(project);

                if (basmalhOnStartSettingsState.isSoundActive()) {
                    new AudioPlayer(basmalhOnStartSettingsState.getVolume(), bassmalh.getAudioUrl()).play();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        // Start notification timer.
        NotificationTimer.INSTANCE.start(project);
    }
}
