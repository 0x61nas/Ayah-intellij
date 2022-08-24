package com.anas.intellij.plugins.ayah.settings;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
@Getter
@Setter
public class BasmalhOnStart {
    private boolean isActive;
    private boolean isSoundActive;
    private SelectedEdition edition;

    public BasmalhOnStart() {
        isActive = true;
        isSoundActive = false;
        edition = new SelectedEdition();
    }
}
