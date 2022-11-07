package com.anas.intellij.plugins.ayah;

import com.anas.alqurancloudapi.Ayah;
import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;
import com.anas.intellij.plugins.ayah.audio.AudioPlayer;
import com.anas.intellij.plugins.ayah.audio.PlayerListener;
import com.anas.intellij.plugins.ayah.dialogs.AyahDetailsDialog;
import com.anas.intellij.plugins.ayah.settings.AyahSettingsState;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import javazoom.jl.player.advanced.PlaybackEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
public class NotificationTimerTask extends TimerTask implements PlayerListener {
    private Project project;
    private static final Logger LOGGER = Logger.getLogger(NotificationTimerTask.class.getName());
    private AudioPlayer audioPlayer;
    private boolean isPlaying;
    private Ayah ayah;
    private Ayah currentAyah; // current ayah
    private AudioPlayer player;


    @Override
    public void run() {
        final var settings = AyahSettingsState.getInstance();

        LOGGER.info("Player id: " + settings.getEdition());
        try {
            ayah = Ayah.getRandomAyah(settings.getEdition().getEditionIdentifier());

            // Set up the notification.
            final var notification = new Notification("Random Ayah Notification",
                    ayah.getSurah().getName(), ayah.getText(), NotificationType.INFORMATION);

            // Play sound if enabled.
            if (settings.isAutoPlayAudio()) {
                LOGGER.info("Playing ayah");
                audioPlayer = new AudioPlayer(ayah.getAudioUrl()).setListener(this);
                audioPlayer.play();
            }

            // Setup the notification actions.
            notification.addAction(new PlayAction());

            notification.addAction(new AnAction("Copy") {
                @Override
                public void actionPerformed(@NotNull final AnActionEvent e) {
                    LOGGER.info("Copy action performed");
                    final var clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(new StringSelection(ayah.getText()), null);
                }
            });

            notification.addAction(new AnAction("Details") {
                @Override
                public void actionPerformed(@NotNull final AnActionEvent e) {
                    LOGGER.info("Details action performed");
                    new AyahDetailsDialog(ayah).setVisible(true);
                }
            });
            notification.addAction(new AnAction("PlayMode") {
                @Override
                public void actionPerformed(@NotNull AnActionEvent e) {
                    playMode();
                }
            });

            // Show notification
            notification.notify(project);
        } catch (final IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }


    public void setProject(final Project project) {
        this.project = project;
    }


    // Play action implementation.
    private class PlayAction extends AnAction {
        PlayAction() {
            super("Play", "Play the ayah", null);
        }

        @Override
        public void actionPerformed(@NotNull final AnActionEvent e) {
            LOGGER.info("Play action performed");
            if (!isPlaying) {
                audioPlayer = new AudioPlayer(ayah.getAudioUrl()).setListener(NotificationTimerTask.this);
                audioPlayer.play();
            } else {
                audioPlayer.stop();
                isPlaying = false;
            }
        }
    }
    public void playMode(){
        try{
            final var currentEdition= Edition.getEditions(EditionFormat.AUDIO,"ar",null);
            currentAyah=Ayah.getAyah(1,currentEdition[0]);
            player=new AudioPlayer(currentAyah.getAudioUrl()).setListener(this);
            player.play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Player listener methods.
    @Override
    public void onStarted() {
        isPlaying = true;
    }

    @Override
    public void onFinished() {
        isPlaying = false;
        new Thread(() -> {
            try {
                currentAyah = Ayah.getAyah(currentAyah.getNumber() + 1, currentAyah.getEdition());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        player = new AudioPlayer(currentAyah.getAudioUrl()).setListener(this);
        player.play();
    }
}
