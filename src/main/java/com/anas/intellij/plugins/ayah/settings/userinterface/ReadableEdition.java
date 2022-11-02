package com.anas.intellij.plugins.ayah.settings.userinterface;

import com.anas.alqurancloudapi.edition.Edition;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/20/22
 */
@RequiredArgsConstructor
@Getter
public class ReadableEdition {
    private final Edition edition;

    public ReadableEdition(final String identifier) {
        this.edition = new Edition(identifier);
    }

    @Override
    public String toString() {
        return edition.getName() + " (" + edition.getLanguage() + ")";
    }
}
