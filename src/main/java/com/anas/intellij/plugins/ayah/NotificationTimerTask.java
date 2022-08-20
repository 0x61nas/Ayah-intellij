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

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
public class NotificationTimerTask extends TimerTask {
    private Project project;
    private static final Logger LOGGER = Logger.getLogger(NotificationTimerTask.class.getName());

    @Override
    public void run() {
        final var settings = AyahSettingsState.getInstance();

        LOGGER.info("Player id: " + settings.getEditionId());
        try {
            final var randomAyah = Ayah.getRandomAyah(settings.getEditionId());

            LOGGER.info("Random Ayah: " + randomAyah.getText());
            LOGGER.info("Rsndom ayah edition: " + randomAyah.getEdition());
            LOGGER.info("Random Ayah Url: " + randomAyah.getAudioUrl());

            // Set up the notification.
            final var notification = new Notification("Random Ayah Notification",
                    randomAyah.getSurah().getName(), randomAyah.getText(), NotificationType.INFORMATION);

            notification.addAction(new AnAction("Play") {
                @Override
                public void actionPerformed(@NotNull final AnActionEvent e) {
                    LOGGER.info("Play action performed");
                    LOGGER.info("Audio url: " + randomAyah.getAudioUrl());
                    play(settings.getVolume(), randomAyah.getAudioUrl());
                }
            });

            notification.addAction(new AnAction("Copy") {
                @Override
                public void actionPerformed(@NotNull final AnActionEvent e) {
                    LOGGER.info("Copy action performed");
                    final var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(new StringSelection(randomAyah.getText()), null);
                }
            });

            notification.addAction(new AnAction("Details") {
                @Override
                public void actionPerformed(@NotNull final AnActionEvent e) {
                    LOGGER.info("Details action performed");
                    new AyahDetailsDialog(project, randomAyah).show();
                }
            });

            // Show notification
            notification.notify(project);

            // Play sound if enabled.
            if (settings.isAutoPlayAudio()) {
                LOGGER.info("Playing ayah");
                play(settings.getVolume(), randomAyah.getAudioUrl());
            }
        } catch (final IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    private void play(final int volume, final String audioUrl) {
        new AudioPlayer(volume, audioUrl).play();
    }

    public void setProject(final Project project) {
        this.project = project;
    }
}
