package GameInterface;


import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class StartPanel extends JPanel {
    private JFrame frame;
    private JPanel startPanel;
    private JLabel label;
    private PlayerPanel playerPanel;
    private int fontSize = 25;
    public static boolean soundEnabled = true;
    private JDialog settingsDialog;

    public StartPanel() {
        initializeStartScreen();
    }

    private void initializeStartScreen() {
        frame = new JFrame("Gra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 750);
        frame.setLocationRelativeTo(null);


        ImageIcon backgroundImage = new ImageIcon("src/GameInterface/library.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());

        startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        label = new JLabel("Witaj w grze 'Kółko i Krzyżyk'!");
        label.setFont(new Font("SANS_SERIF", Font.BOLD, fontSize));
        gbc.gridy++;
        startPanel.add(label, gbc);

        JButton startButton = new JButton("▶\uFE0F Start");
        startButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(startPanel);
                playerPanel = new PlayerPanel(frame);
                frame.add(playerPanel);
                frame.revalidate();
                frame.repaint();
                playSound();
            }
        });
        gbc.gridy++;
        startPanel.add(startButton, gbc);


        JButton settingsButton = new JButton("⚙\uFE0F Ustawienia");
        settingsButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSettingsDialog();
                playSound();
            }
        });
        gbc.gridy++;
        startPanel.add(settingsButton, gbc);

        JButton endButton = new JButton("❌ Koniec");
        endButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridy++;
        startPanel.add(endButton, gbc);

        backgroundLabel.add(startPanel);

        frame.setContentPane(backgroundLabel);
        frame.setVisible(true);
    }

    public void showSettingsDialog() {
        settingsDialog = new JDialog(frame, "Ustawienia", true);
        settingsDialog.setSize(400, 200);
        settingsDialog.setLocationRelativeTo(frame);
        settingsDialog.setLayout(new GridLayout(3, 1));

        JLabel fontSizeLabel = new JLabel("Wybierz rozmiar tekstu:");
        fontSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsDialog.add(fontSizeLabel);

        JSlider fontSizeSlider = new JSlider(10, 30, fontSize);
        fontSizeSlider.setMajorTickSpacing(5);
        fontSizeSlider.setMinorTickSpacing(1);
        fontSizeSlider.setPaintTicks(true);
        fontSizeSlider.setPaintLabels(true);
        settingsDialog.add(fontSizeSlider);

        JCheckBox soundCheckBox = new JCheckBox("Włącz dźwięki");
        soundCheckBox.setSelected(soundEnabled);
        settingsDialog.add(soundCheckBox);

        JButton applyButton = new JButton("Zastosuj");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fontSize = fontSizeSlider.getValue();
                label.setFont(new Font("SANS_SERIF", Font.BOLD, fontSize));

                soundEnabled = soundCheckBox.isSelected();

                settingsDialog.dispose();
            }
        });
        settingsDialog.add(applyButton);

        settingsDialog.setVisible(true);
    }

    public static void playSound() {
        if (soundEnabled) {
            try {
                File soundFile = new File("src/GameInterface/click.wav");
                if (!soundFile.exists()) {
                    System.out.println("Plik dźwiękowy nie istnieje.");
                    return;
                }

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                clip.start();
            } catch (UnsupportedAudioFileException ex) {
                System.out.println("Nieobsługiwany format pliku dźwiękowego.");
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Błąd podczas odczytu pliku dźwiękowego.");
                ex.printStackTrace();
            } catch (LineUnavailableException ex) {
                System.out.println("Linia dźwiękowa jest niedostępna.");
                ex.printStackTrace();
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }

    public int getFontSize() {
        return fontSize;
    }

    public JDialog getSettingsDialog() {
        return settingsDialog;
    }
}
