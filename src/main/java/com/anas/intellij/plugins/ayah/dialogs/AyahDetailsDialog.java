package com.anas.intellij.plugins.ayah.dialogs;

import com.anas.alqurancloudapi.Ayah;
import com.anas.intellij.plugins.ayah.settings.PanelBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextArea;
import net.miginfocom.swing.MigLayout;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/20/22
 */
public class AyahDetailsDialog extends DialogWrapper {
    private final JBTextArea ayahTextArea;
    private JBLabel surahNameLabel;
    private JBLabel numberOfAyahInSurahLabel;
    private JBLabel tanzelLabel;
    private final JButton playButton;

    {
        ayahTextArea = new JBTextArea();
        playButton = new JButton("Play");
    }
    public AyahDetailsDialog(final Project project, final Ayah ayah) {
        super(project, false);
        setTitle("Ayah Details");
        initializeDataFiles(ayah);
        setSize(500, 300);
        setModal(false);
        setOKButtonText("");
        init();
    }

    private void initializeDataFiles(final Ayah ayah) {
        ayahTextArea.setText(ayah.getText());
        ayahTextArea.setEditable(false);

        final var surah = ayah.getSurah();
        surahNameLabel = new JBLabel(surah.getName());
        tanzelLabel = new JBLabel(surah.getRevelationType().getArabicName() + " " + surah.getRevelationType().toString());

        numberOfAyahInSurahLabel = new JBLabel("Number: " + ayah.getNumberInSurah());

    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return new PanelBuilder()
                .setLayout(new MigLayout("fill"))
                .addComponent(ayahTextArea, "grow, push, span")
                .addComponent(surahNameLabel, "grow, push")
                .addComponent(numberOfAyahInSurahLabel, "grow, push")
                .addComponent(tanzelLabel, "grow, push, wrap")
                .addComponent(playButton, "grow, push")
                .build();
    }
}
