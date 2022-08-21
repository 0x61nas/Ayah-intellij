package com.anas.intellij.plugins.ayah.settings;

import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;

import java.io.IOException;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/21/22
 */
public class SelectedEdition {
    private String editionIdentifier;
    private int index;

    // For XML serialization
    public SelectedEdition() {
        try {
            editionIdentifier = Edition.getEditions(EditionFormat.AUDIO)[0].getIdentifier();
        } catch (final IOException e) {
            editionIdentifier = "ar.abdulbasitmurattal";
        }
        index = 0;
    }

    public SelectedEdition(final String editionIdentifier, final int index) {
        this.editionIdentifier = editionIdentifier;
        this.index = index;
    }

    public String getEditionIdentifier() {
        return editionIdentifier;
    }

    // For XML serialization
    public void setEditionIdentifier(final String editionIdentifier) {
        this.editionIdentifier = editionIdentifier;
    }

    public int getIndex() {
        return index;
    }

    // For XML serialization
    public void setIndex(final int index) {
        this.index = index;
    }
}
