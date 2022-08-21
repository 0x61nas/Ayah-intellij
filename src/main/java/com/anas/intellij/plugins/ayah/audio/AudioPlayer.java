package com.anas.intellij.plugins.ayah.audio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
public class AudioPlayer {
    private final String audioUrl;
    private static final Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());

    public AudioPlayer(final String audioUrl) {
        this.audioUrl = audioUrl;
    }

    private Player loadAndOpen() {
        try {
            return new Player(getInputStream(audioUrl),
                    FactoryRegistry.systemRegistry().createAudioDevice());
        } catch (final MalformedURLException | JavaLayerException e) {
            LOGGER.severe("Error while opening stream player: " + e.getMessage());
        } catch (final IOException e) {
            LOGGER.severe("Can't load audio file: " + audioUrl);
            LOGGER.severe(e.getMessage());
        }
        return null;
    }

    public void play() {
        new Thread(() -> {
            try {
                loadAndOpen().play();
            } catch (final JavaLayerException | NullPointerException e) {
                LOGGER.severe(e.getMessage());
            }
        }).start();
    }


    private InputStream getInputStream(final String audioUrl) throws IOException {
        final var url = new URL(audioUrl);
        final var inputStream = url.openStream();
        return new BufferedInputStream(inputStream);
    }
}
