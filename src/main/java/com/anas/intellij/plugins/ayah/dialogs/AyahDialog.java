package com.anas.intellij.plugins.ayah.dialogs;

import com.anas.alqurancloudapi.Ayah;
import com.anas.intellij.plugins.ayah.audio.AudioPlayer;
import com.anas.intellij.plugins.ayah.settings.AyahSettingsState;

import javax.swing.*;
import java.awt.event.*;

public class AyahDialog extends JDialog {
    private JPanel contentPane;
    private JButton playButton;
    private JButton buttonCancel;
    private JTextArea ayahTextArea;
    private JLabel surahNameLabel;
    private JLabel numberOfAyahInSuarhLabel;
    private JLabel ayahRevelationType;

    public AyahDialog(final Ayah ayah) {
        setContentPane(contentPane);
        setModal(true);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(playButton);

        ayahTextArea.setText(ayah.getText());
        surahNameLabel.setText(ayah.getSurah().getName());
        numberOfAyahInSuarhLabel.setText("Number: " + ayah.getNumberInSurah());
        ayahRevelationType.setText(ayah.getSurah().getRevelationType().getArabicName());

        addListeners(ayah);
    }

    private void addListeners(final Ayah ayah) {
        playButton.addActionListener(e ->
                new AudioPlayer(AyahSettingsState.getInstance().getVolume(), ayah.getAudioUrl()).play());

        buttonCancel.addActionListener(l -> dispose());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(l -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
}
