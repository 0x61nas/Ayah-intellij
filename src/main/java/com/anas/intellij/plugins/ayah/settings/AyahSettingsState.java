package com.anas.intellij.plugins.ayah.settings;

import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
@Getter
@Setter
@State(
        name = "com.anas.intellij.plugins.ayah.settings.AyahSettingsState",
        storages = @Storage("ayah.xml")
)
public class AyahSettingsState implements PersistentStateComponent<AyahSettingsState> {
    private BasmalhOnStart basmalhOnStart;
    private int intervalTimeBetweenNotifications; // in minutes
    private boolean autoPlayAudio;
    private SelectedEdition edition;

    public static AyahSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AyahSettingsState.class);
    }

    private AyahSettingsState() {
        basmalhOnStart = new BasmalhOnStart();
        intervalTimeBetweenNotifications = 30; // 30 minutes
        autoPlayAudio = false;
        edition = new SelectedEdition();
    }


    @Override
    public @Nullable AyahSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull final AyahSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
