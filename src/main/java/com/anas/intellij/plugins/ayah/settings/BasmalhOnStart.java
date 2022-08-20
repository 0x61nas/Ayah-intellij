package com.anas.intellij.plugins.ayah.settings;

import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;

import java.io.IOException;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
public class BasmalhOnStart {
    private boolean isActive;
    private boolean isNotificationActive;
    private boolean isSoundActive;
    private String editionId;
    private int volume;

    public BasmalhOnStart() {
        isActive = true;
        isNotificationActive = true;
        isSoundActive = false;
        try {
            editionId = Edition.getRandomEdition(EditionFormat.AUDIO, "ar").getIdentifier();
        } catch (final IOException e) {
            editionId = null;
        }
        volume = 40; // 40%
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(final boolean active) {
        isActive = active;
    }

    public boolean isNotificationActive() {
        return isNotificationActive;
    }

    public void setNotificationActive(final boolean notificationActive) {
        isNotificationActive = notificationActive;
    }

    public boolean isSoundActive() {
        return isSoundActive;
    }

    public void setSoundActive(final boolean soundActive) {
        isSoundActive = soundActive;
    }

    public String getEditionId() {
        return editionId;
    }

    public void setEditionId(final String editionId) {
        this.editionId = editionId;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(final int volume) {
        this.volume = volume;
    }
}
