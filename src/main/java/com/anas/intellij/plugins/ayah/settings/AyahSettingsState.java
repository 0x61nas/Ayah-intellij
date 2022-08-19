package com.anas.intellij.plugins.ayah.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
@State(
        name = "com.anas.intellij.plugins.ayah.settings.AyahSettingsState",
        storages = @Storage("ayah.xml")
)
public class AyahSettingsState implements PersistentStateComponent<AyahSettingsState> {
    private BasmalhOnStart basmalhOnStart;
    private int intervalTimeBetweenNotifications; // in minutes
    private boolean autoPlayAudio;
    private String playerId;

    public static AyahSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AyahSettingsState.class);
    }

    private AyahSettingsState() {
        basmalhOnStart = new BasmalhOnStart();
        intervalTimeBetweenNotifications = 30; // 30 minutes
        autoPlayAudio = false;
        playerId = null;
    }


    @Override
    public @Nullable AyahSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull final AyahSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    public BasmalhOnStart getBasmalhOnStart() {
        return basmalhOnStart;
    }

    public void setBasmalhOnStart(final BasmalhOnStart basmalhOnStart) {
        this.basmalhOnStart = basmalhOnStart;
    }

    public long getIntervalTimeBetweenNotifications() {
        return intervalTimeBetweenNotifications;
    }

    public void setIntervalTimeBetweenNotifications(final int intervalTimeBetweenNotifications) {
        this.intervalTimeBetweenNotifications = intervalTimeBetweenNotifications;
    }

    public boolean isAutoPlayAudio() {
        return autoPlayAudio;
    }

    public void setAutoPlayAudio(final boolean autoPlayAudio) {
        this.autoPlayAudio = autoPlayAudio;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(final String playerId) {
        this.playerId = playerId;
    }

}
