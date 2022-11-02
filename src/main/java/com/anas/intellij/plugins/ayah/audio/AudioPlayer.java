package com.anas.intellij.plugins.ayah.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
public class AudioPlayer {
    private final String audioUrl;
    private PlayerListener listener; // we don't need more than one listener
    private AdvancedPlayer player;
    private static final Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());

    public AudioPlayer(final String audioUrl) {
        this.audioUrl = audioUrl;
    }

    private AdvancedPlayer loadAndOpen() {
        try {
            return new AdvancedPlayer(getInputStream(audioUrl),
                    FactoryRegistry.systemRegistry().createAudioDevice());
        } catch (final MalformedURLException | JavaLayerException e) {
            LOGGER.severe("Error while opening stream player: " + e.getMessage());
        } catch (final IOException e) {
            LOGGER.severe("Can't load audio file: " + audioUrl);
            LOGGER.severe(e.getMessage());

            JOptionPane.showMessageDialog(null,
                    "Error loading the ayah, check your internet connection - حدث خطاء اثناء تحميل الآية، تحقق من اتصالك بالإنترنت",
                    "Error - خطأ", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void play() {
        new Thread(() -> {
            try {
                player = loadAndOpen();
                if (player == null)
                    throw new IOException("Can't create player");

                if (listener != null) {
                    player.setPlayBackListener(new PlaybackListener() {
                        @Override
                        public void playbackStarted(final PlaybackEvent evt) {
                            listener.onStarted();
                        }

                        @Override
                        public void playbackFinished(final PlaybackEvent evt) {
                            listener.onFinished();
                        }
                    });
                }

                player.play();
            } catch (final JavaLayerException | IOException e) {
                LOGGER.severe(e.getMessage());

                JOptionPane.showMessageDialog(null,
                        "Error whole playing the ayah - حدث خطاء اثناء تشغيل الآية",
                        "Error - خطأ", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }


    private InputStream getInputStream(final String audioUrl) throws IOException {
        final var url = new URL(audioUrl);
        final var inputStream = url.openStream();
        return new BufferedInputStream(inputStream);
    }

    public AudioPlayer setListener(final PlayerListener listener) {
        this.listener = listener;
        return this;
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
    }
}
