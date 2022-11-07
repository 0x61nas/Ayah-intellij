package com.anas.intellij.plugins.ayah.settings;

import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/21/22
 */
@AllArgsConstructor
@Getter
@Setter
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
}
