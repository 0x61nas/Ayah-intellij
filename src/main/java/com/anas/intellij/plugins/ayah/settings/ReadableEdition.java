package com.anas.intellij.plugins.ayah.settings;

import com.anas.alqurancloudapi.edition.Edition;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/20/22
 */
public class ReadableEdition {
    private Edition edition;

    public ReadableEdition(final Edition edition) {
        this.edition = edition;
    }

    public ReadableEdition(final String identifier) {
        this.edition = new Edition(identifier);
    }

    public Edition getEdition() {
        return edition;
    }

    @Override
    public String toString() {
        return edition.getName() + " (" + edition.getLanguage() + ")";
    }
}
