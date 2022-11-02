package com.anas.intellij.plugins.ayah.settings.userinterface;


import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;
import com.anas.intellij.plugins.ayah.settings.AyahSettingsState;
import com.anas.intellij.plugins.ayah.settings.BasmalhOnStart;
import com.anas.intellij.plugins.ayah.settings.SelectedEdition;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The settings UI.
 *
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */
public class SettingsComponent {
    private final JPanel panel;
    private final JBCheckBox basmalhOnStartCheckBox;
    private final JBCheckBox autoPlayBasmalhCheckBox;
    private final JComboBox<ReadableEdition> basmalhPlayerIdComboBox;
    private final JSpinner notificationsIntervalSpinner;
    private SpinnerNumberModel notificationsIntervalSpinnerModel;
    private final JBCheckBox notificationsAudioCheckBox;
    private final JComboBox<ReadableEdition> ayahPlayerIdComboBox;

    private final Logger LOGGER = Logger.getLogger(SettingsComponent.class.getName());

    // Initialize block yoo.
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

        loadComboBoxesValues();
        setup();
        addListeners();
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

        if (basmalhPlayerIdComboBox.getItemCount() <= 0) {
            basmalhPlayerIdComboBox.addItem(new ReadableEdition(settings.getBasmalhOnStart()
                    .getEdition().getEditionIdentifier()));
            ayahPlayerIdComboBox.addItem(new ReadableEdition(settings.getEdition().getEditionIdentifier()));
        }

        basmalhPlayerIdComboBox.setSelectedIndex(settings.getBasmalhOnStart().getEdition().getIndex());
        ayahPlayerIdComboBox.setSelectedIndex(settings.getEdition().getIndex());
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
                basmalhPlayerIdComboBox.addItem(new ReadableEdition(edition));
                ayahPlayerIdComboBox.addItem(new ReadableEdition(edition));
            }
        } catch (final IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public boolean isModified() {
        final var settings = AyahSettingsState.getInstance();
        return settings.getBasmalhOnStart().getEdition() != null &&
                !settings.getBasmalhOnStart().getEdition().getEditionIdentifier()
                        .equals(((ReadableEdition) basmalhPlayerIdComboBox.getSelectedItem())
                                .getEdition().getIdentifier()) ||
                settings.getEdition() != null &&
                        !settings.getEdition().getEditionIdentifier()
                                .equals(((ReadableEdition) ayahPlayerIdComboBox.getSelectedItem())
                                        .getEdition().getIdentifier()) ||
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

    public BasmalhOnStart getBasmalhOnStart() {
        final var b = new BasmalhOnStart();
        b.setActive(basmalhOnStartCheckBox.isSelected());
        b.setSoundActive(autoPlayBasmalhCheckBox.isSelected());
        b.setEdition(new SelectedEdition(((ReadableEdition) Objects.requireNonNull(
                basmalhPlayerIdComboBox.getSelectedItem())).getEdition().getIdentifier(), basmalhPlayerIdComboBox.getSelectedIndex()));
        return b;
    }

    public int getIntervalTimeBetweenNotifications() {
        return notificationsIntervalSpinnerModel.getNumber().intValue();
    }

    public boolean isAutoPlayAudio() {
        return notificationsAudioCheckBox.isSelected();
    }

    public SelectedEdition getSelectedEdition() {
        return new SelectedEdition(((ReadableEdition) ayahPlayerIdComboBox
                .getSelectedItem()).getEdition().getIdentifier(),
                ayahPlayerIdComboBox.getSelectedIndex());
    }
}
