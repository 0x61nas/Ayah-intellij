package com.anas.intellij.plugins.ayah.dialogs;

import com.anas.alqurancloudapi.Ayah;
import com.anas.alqurancloudapi.consts.Constants;
import com.anas.alqurancloudapi.edition.Edition;
import com.anas.alqurancloudapi.edition.EditionFormat;
import com.anas.intellij.plugins.ayah.audio.AudioPlayer;
import com.anas.intellij.plugins.ayah.audio.PlayerListener;
import com.anas.intellij.plugins.ayah.settings.userinterface.ReadableEdition;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author <a href="https://github.com/anas-elgarhy">Anas Elgarhy</a>
 * @since 8/19/22
 */

public class AyahDetailsDialog extends JDialog implements PlayerListener {
    private JPanel contentPane;
    private JButton playButton;
    private JButton buttonCancel;
    private JTextArea ayahTextArea;
    private JLabel surahNameLabel;
    private JLabel numberOfAyahInSuarhLabel;
    private JLabel ayahRevelationType;
    private JButton previousButton;
    private JButton nextButton;
    private JCheckBox autoPlayCheckBox;
    private JTextArea tafseerTextArea;
    private JComboBox<ReadableEdition> tafseerAndTranslationComboBox;
    private JComboBox<ReadableEdition> editionComboBox;
    private boolean isPlaying;
    private AudioPlayer audioPlayer;
    private Ayah ayah;

    public AyahDetailsDialog(final Ayah ayah) {
        this.ayah = ayah;

        setContentPane(contentPane);
        setModal(true);
        setSize(520, 320);
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(playButton);

        try {
            setupTheUI();
        } catch (final IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error while loading the ayah details",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        addListeners();
    }

    private void setupTheUI() throws IOException {
        previousButton.setEnabled(ayah.getNumber() != 1 && ayah.getSurah().getNumber() != 1);
        nextButton.setEnabled(ayah.getNumber() != Constants.AYAHS_COUNT && ayah.getSurah().getNumber() != Constants.SURAS_COUNT);


        final var tafserAndTranslationComboBoxModel = new DefaultComboBoxModel<ReadableEdition>();
        final var editionComboBoxModel = new DefaultComboBoxModel<ReadableEdition>();

        Arrays.stream(Edition.getEditions()).forEach(edition -> {
            if (edition.getFormat() == EditionFormat.AUDIO) {
                editionComboBoxModel.addElement(new ReadableEdition(edition));
            } else {
                tafserAndTranslationComboBoxModel.addElement(new ReadableEdition(edition));
            }
        });

        tafseerAndTranslationComboBox.setModel(tafserAndTranslationComboBoxModel);
        editionComboBox.setModel(editionComboBoxModel);

        // Set the default selected item for the editionComboBox
        editionComboBoxModel.setSelectedItem(new ReadableEdition(ayah.getEdition()));
        // Set the actual information about the ayah in the UI
        updateAyahDetails();
    }

    private void updateAyahDetails() {
        // Update the ayah details
        ayahTextArea.setText(ayah.getText());
        surahNameLabel.setText(ayah.getSurah().getName());
        numberOfAyahInSuarhLabel.setText("آية رقم: " + ayah.getNumberInSurah());
        ayahRevelationType.setText(ayah.getSurah().getRevelationType().getArabicName());
        // Update the tafseer or translation
        updateTheTauseerTextArea();
    }

    private void addListeners() {
        playButton.addActionListener(e -> {
            if (!isPlaying) {
                audioPlayer = new AudioPlayer(ayah.getAudioUrl()).setListener(this);
                audioPlayer.play();
            } else {
                audioPlayer.stop();
                playButton.setText("Play");
                isPlaying = false;
            }
        });

        nextButton.addActionListener(e -> {
            if (ayah.getNumber() <= Constants.AYAHS_COUNT) {
                loadTheAyah(ayah.getNumber() + 1);
                previousButton.setEnabled(true);
                if (ayah.getNumber() >= Constants.AYAHS_COUNT) {
                    nextButton.setEnabled(false);
                }
                if (isPlaying) {
                    playOrNot();
                }
            }
        });

        previousButton.addActionListener(e -> {
            if (ayah.getNumber() >= 1) {
                loadTheAyah(ayah.getNumber() - 1);
                nextButton.setEnabled(true);
                if (ayah.getNumber() == 1) {
                    previousButton.setEnabled(false);
                }
                if (isPlaying) {
                    playOrNot();
                }
            }
        });

        tafseerAndTranslationComboBox.addActionListener(e -> {
            updateTheTauseerTextArea();
        });

        editionComboBox.addActionListener(e -> {
            final var selectedEdition = ((ReadableEdition) Objects.requireNonNull(
                    editionComboBox.getSelectedItem())).getEdition();
            try {
                ayah = Ayah.getAyah(ayah.getNumber(), selectedEdition);
                updateAyahDetails();
            } catch (final IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error while loading the ayah - check your internet connection",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonCancel.addActionListener(l -> close());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(l -> close(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void updateTheTauseerTextArea() {
        final var selectedEdition = ((ReadableEdition) Objects.requireNonNull(
                tafseerAndTranslationComboBox.getSelectedItem())).getEdition();
        try {
            tafseerTextArea.setText(Ayah.getAyah(ayah.getNumber(), selectedEdition).getText());
        } catch (final IOException ioException) {
            ioException.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error while loading the tafseer - check your internet connection",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void close() {
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer = null;
        }
        dispose();
    }

    private void playOrNot() {
        if (autoPlayCheckBox.isSelected()) {
            audioPlayer.stop();
            audioPlayer = new AudioPlayer(ayah.getAudioUrl()).setListener(this);
            audioPlayer.play();
        } else {
            audioPlayer.stop();
            playButton.setText("Play");
            isPlaying = false;
        }
    }

    private boolean loadTheAyah(final int ayhNumber) {
        try {
            ayah = Ayah.getAyah(ayhNumber,
                    ayah.getEdition().getIdentifier());
            updateAyahDetails();
            return true;
        } catch (final IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading the ayah, check your internet connection - حدث خطاء اثناء تحميل الآية، تحقق من اتصالك بالإنترنت",
                    "Error - خطأ", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public void onStarted() {
        playButton.setText("Stop");
        isPlaying = true;
    }

    @Override
    public void onFinished() {
        if (autoPlayCheckBox.isSelected() && ayah.getNumber() <= Constants.AYAHS_COUNT) {
            if (loadTheAyah(ayah.getNumber() + 1)) {
                audioPlayer = new AudioPlayer(ayah.getAudioUrl()).setListener(this);
                audioPlayer.stop();
                audioPlayer.play();
            }
        } else {
            playButton.setText("Play");
            isPlaying = false;
        }
    }
}
