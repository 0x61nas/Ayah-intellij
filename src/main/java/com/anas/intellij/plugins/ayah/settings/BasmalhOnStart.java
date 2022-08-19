package com.anas.intellij.plugins.ayah.settings;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
public class BasmalhOnStart {
    private boolean isActive;
    private boolean isNotificationActive;
    private boolean isSoundActive;
    private String playerId;

    public BasmalhOnStart() {
        isActive = true;
        isNotificationActive = true;
        isSoundActive = false;
        playerId = null;
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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(final String playerId) {
        this.playerId = playerId;
    }
}
