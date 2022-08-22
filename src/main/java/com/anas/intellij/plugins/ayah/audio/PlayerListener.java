package com.anas.intellij.plugins.ayah.audio;

import javazoom.jl.player.advanced.PlaybackEvent;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/22/22
 */
public interface PlayerListener {
    void onStarted(final PlaybackEvent event);
    void onFinished(final PlaybackEvent event);
}
