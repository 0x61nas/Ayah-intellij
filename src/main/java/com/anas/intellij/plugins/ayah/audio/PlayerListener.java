package com.anas.intellij.plugins.ayah.audio;

import javazoom.jl.player.advanced.PlaybackEvent;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/22/22
 */
public interface PlayerListener {
    void onStarted();
    void onFinished();
}
