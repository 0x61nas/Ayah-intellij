package com.anas.intellij.plugins.ayah.settings;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
@Getter
@Setter
public class BasmalhOnStart {
    private boolean isActive;
    private boolean isSoundActive;
    @NonNull
    private SelectedEdition edition;

    public BasmalhOnStart() {
        isActive = true;
        isSoundActive = false;
        edition = new SelectedEdition();
    }
}
