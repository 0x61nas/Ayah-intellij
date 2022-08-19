package com.anas.intellij.plugins.ayah.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@State(
        name = "com.anas.intellij.plugins.ayah.settings.AyahSettingsState",
        storages = @Storage("ayah.xml")
)
public class AyahSettingsState implements PersistentStateComponent<AyahSettingsState> {
    private BasmalhOnStart basmalhOnStart;
    private long intervalTimeBetweenNotifications;
    private boolean autoPlay;
    private String playerId;

    public static AyahSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(AyahSettingsState.class);
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
