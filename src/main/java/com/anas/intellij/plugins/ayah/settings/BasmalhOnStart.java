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
    private boolean isSoundActive;
    private SelectedEdition edition;

    public BasmalhOnStart() {
        isActive = true;
        isSoundActive = false;
        edition = new SelectedEdition();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(final boolean active) {
        isActive = active;
    }

    public boolean isSoundActive() {
        return isSoundActive;
    }

    public void setSoundActive(final boolean soundActive) {
        isSoundActive = soundActive;
    }

    public SelectedEdition getEdition() {
        return edition;
    }

    public void setEdition(final SelectedEdition edition) {
        this.edition = edition;
    }
}
