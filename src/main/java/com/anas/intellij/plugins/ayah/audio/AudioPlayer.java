package com.anas.intellij.plugins.ayah.audio;

import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;

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
    private final StreamPlayer streamPlayer;
    private final String audioUrl;
    private static final Logger LOGGER = Logger.getLogger(AudioPlayer.class.getName());

    public AudioPlayer(final int volume, final String audioUrl) {
        streamPlayer = new StreamPlayer();
        this.audioUrl = audioUrl;
        streamPlayer.setGain(volume / 100.0);
    }

    private void loadAndOpen() {
        try {
            streamPlayer.open(getInputStream(audioUrl));
        } catch (final MalformedURLException | StreamPlayerException e) {
            LOGGER.severe("Error while opening stream player: " + e.getMessage());
        } catch (final IOException e) {
            LOGGER.severe("Can't load audio file: " + audioUrl);
            LOGGER.severe(e.getMessage());
        }
    }

    public void play() {
        new Thread(() -> {
            try {
                loadAndOpen();
                streamPlayer.play();
            } catch (final StreamPlayerException e) {
                LOGGER.severe(e.getMessage());
            }
        }).start();
    }


    private InputStream getInputStream(final String audioUrl) throws IOException {
        final var url = new URL(audioUrl);
        final var inputStream = url.openStream();
        return new BufferedInputStream(inputStream);
    }

    public void run() {
        loadAndOpen();
        play();
    }
}
