package com.anas.intellij.plugins.ayah.settings;

import com.anas.intellij.plugins.ayah.NotificationTimer;
import com.anas.intellij.plugins.ayah.settings.userinterface.SettingsComponent;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
public class AyahSettingsConfigurable implements Configurable {

    private SettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Ayah Settings";
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new SettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        return settingsComponent.isModified();
    }

    @Override
    public void apply() {
        final var settingsState = AyahSettingsState.getInstance();
        settingsState.setBasmalhOnStart(settingsComponent.getBasmalhOnStart());
        settingsState.setIntervalTimeBetweenNotifications(settingsComponent.getIntervalTimeBetweenNotifications());
        settingsState.setAutoPlayAudio(settingsComponent.isAutoPlayAudio());
        settingsState.setEdition(settingsComponent.getSelectedEdition());

        // Update the timer with the new interval time between notifications if interval time between notifications has changed
        if (settingsState.getIntervalTimeBetweenNotifications() !=
                settingsComponent.getIntervalTimeBetweenNotifications()) {
            NotificationTimer.INSTANCE
                    .updateIntervalTimeBetweenNotifications(settingsState.getIntervalTimeBetweenNotifications());
        }
    }

    @Override
    public void reset() {
        settingsComponent.reset();
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
