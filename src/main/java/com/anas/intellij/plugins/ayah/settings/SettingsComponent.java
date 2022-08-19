package com.anas.intellij.plugins.ayah.settings;


import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.IOException;

/**
 * The settings UI.
 *
 * @author: <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @date: 8/19/22
 */
public class SettingsComponent {
    private final JPanel panel;
    private final JBCheckBox basmalhOnStartCheckBox;
    private final JBCheckBox autoPlayBasmalhCheckBox;
    private final JComboBox<String> basmalhPlayerIdComboBox;
    private final JSpinner notificationsIntervalSpinner;
    private SpinnerNumberModel notificationsIntervalSpinnerModel;
    private final JBCheckBox notificationsAudioCheckBox;
    private final JComboBox<String> ayahPlayerIdComboBox;

    {
        basmalhOnStartCheckBox = new JBCheckBox("Basmalh on start");
        autoPlayBasmalhCheckBox = new JBCheckBox("Auto play basmalh audio");
        basmalhPlayerIdComboBox = new JComboBox<>();
        notificationsIntervalSpinner = new JSpinner();
        notificationsAudioCheckBox = new JBCheckBox("Notifications audio");
        ayahPlayerIdComboBox = new JComboBox<>();
    }

    public SettingsComponent() {
        panel = FormBuilder.createFormBuilder()
                .addComponent(new PanelBuilder()
                        .setLayout(new MigLayout("fill"))
                        .addComponent(
                                new PanelBuilder()
                                        .setBorder(BorderFactory.createTitledBorder("Basmalh on start"))
                                        .setLayout(new MigLayout())
                                        .addComponent(basmalhOnStartCheckBox, "span, grow")
                                        .addComponent(autoPlayBasmalhCheckBox)
                                        .addComponent(new JBLabel("Basmalh player"),
                                                "gap unrelated")
                                        .addComponent(basmalhPlayerIdComboBox,
                                                "grow, wrap")
                                        .build(),
                                "span, grow, wrap"
                        )
                        .addComponent(new JBLabel("Notifications interval"),
                                "gap unrelated")
                        .addComponent(notificationsIntervalSpinner, "grow")
                        .addComponent(new JBLabel("Minutes"), "gap 1, wrap")
                        .addComponent(notificationsAudioCheckBox, "grow")
                        .addComponent(new JBLabel("Ayah player"), "gap unrelated")
                        .addComponent(ayahPlayerIdComboBox, "grow, wrap")
                        .build()
                )
                .getPanel();

        setup();
        addListeners();
        loadComboBoxesValues();
    }

    private void setup() {
        final var settings = AyahSettingsState.getInstance();
        notificationsIntervalSpinnerModel = new SpinnerNumberModel(settings.getIntervalTimeBetweenNotifications(),
                        1, Integer.MAX_VALUE, 1);

        notificationsIntervalSpinner.setModel(notificationsIntervalSpinnerModel);
        basmalhOnStartCheckBox.setSelected(settings.getBasmalhOnStart().isActive());
        autoPlayBasmalhCheckBox.setSelected(settings.getBasmalhOnStart().isSoundActive());
        autoPlayBasmalhCheckBox.setEnabled(settings.getBasmalhOnStart().isActive());
        notificationsAudioCheckBox.setSelected(settings.isAutoPlayAudio());
        basmalhPlayerIdComboBox.setEnabled(settings.getBasmalhOnStart().isActive());

        if (settings.getBasmalhOnStart().getPlayerId() != null) {
            basmalhPlayerIdComboBox.setSelectedItem(settings.getBasmalhOnStart().getPlayerId());
        }
        if (settings.getPlayerId() != null) {
            ayahPlayerIdComboBox.setSelectedItem(settings.getPlayerId());
        }
    }

    private void addListeners() {
        basmalhOnStartCheckBox.addActionListener(e ->
                autoPlayBasmalhCheckBox.setEnabled(basmalhOnStartCheckBox.isSelected()));

        ayahPlayerIdComboBox.addActionListener(e ->
                basmalhPlayerIdComboBox.setEnabled(basmalhOnStartCheckBox.isSelected()));

        notificationsAudioCheckBox.addActionListener(e ->
                ayahPlayerIdComboBox.setEnabled(notificationsAudioCheckBox.isSelected()));
    }

    private void loadComboBoxesValues() {
        try {
            final var editions = Edition.getEditions(EditionFormat.AUDIO);
            for (final var edition : editions) {
                basmalhPlayerIdComboBox.addItem(edition.getEnglishName());
                ayahPlayerIdComboBox.addItem(edition.getEnglishName());
            }
        } catch (final IOException e) {
            e.printStackTrace();
            basmalhPlayerIdComboBox.addItem("Error can't get editions, please check internet connection");
            ayahPlayerIdComboBox.addItem("Error can't get editions, please check internet connection");
        }
    }

    public boolean isModified() {
        final var settings = AyahSettingsState.getInstance();
        return settings.getBasmalhOnStart().getPlayerId() != null &&
                !settings.getBasmalhOnStart().getPlayerId().equals(basmalhPlayerIdComboBox.getSelectedItem()) ||
                settings.getPlayerId() != null &&
                        !settings.getPlayerId().equals(ayahPlayerIdComboBox.getSelectedItem()) ||
                settings.getIntervalTimeBetweenNotifications() != notificationsIntervalSpinnerModel.getNumber().intValue() ||
                settings.getBasmalhOnStart().isActive() != basmalhOnStartCheckBox.isSelected() ||
                settings.getBasmalhOnStart().isSoundActive() != autoPlayBasmalhCheckBox.isSelected() ||
                settings.isAutoPlayAudio() != notificationsAudioCheckBox.isSelected();
    }

    public void reset() {
        setup();
    }

    public JPanel getPanel() {
        return panel;
    }
}
