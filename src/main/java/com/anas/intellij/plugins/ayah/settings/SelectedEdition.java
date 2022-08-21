package com.anas.intellij.plugins.ayah.settings;

import com.anas.alqurancloudapi.edition.Edition;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/21/22
 */
public class SelectedEdition {
    private final String editionIdentifier;
    private final int index;

    public SelectedEdition(final String editionIdentifier, final int index) {
        this.editionIdentifier = editionIdentifier;
        this.index = index;
    }

    public String getEditionIdentifier() {
        return editionIdentifier;
    }

    public int getIndex() {
        return index;
    }
}
