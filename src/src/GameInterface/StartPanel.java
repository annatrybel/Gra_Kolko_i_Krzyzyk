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
    public static boolean soundEnabled = true; // Domyślnie dźwięki są włączone
    private JDialog settingsDialog;

    public StartPanel() {
        initializeStartScreen();
    }

    private void initializeStartScreen() {
        frame = new JFrame("Gra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 750);
        frame.setLocationRelativeTo(null);  // Ustawienie okna na środku ekranu

        // Ładowanie obrazu tła
        ImageIcon backgroundImage = new ImageIcon("src/GameInterface/library.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());

        startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        label = new JLabel("Witaj w grze 'Kółko i Krzyżyk'!");
        label.setFont(new Font("SANS_SERIF", Font.BOLD, fontSize)); // Ustawienie rozmiaru tekstu
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
                playSound(); // Odtwarzanie dźwięku po naciśnięciu przycisku "Start"
            }
        });
        gbc.gridy++;
        startPanel.add(startButton, gbc);

        // Dodanie przycisku ustawień
        JButton settingsButton = new JButton("⚙\uFE0F Ustawienia");
        settingsButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Otwórz okno ustawień i zaktualizuj ustawienia po zamknięciu
                showSettingsDialog();
                playSound(); // Odtwarzanie dźwięku po naciśnięciu przycisku "Ustawienia"
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

        // Dodanie panelu startowego na tło
        backgroundLabel.add(startPanel);

        // Dodanie tła do ramki
        frame.setContentPane(backgroundLabel);
        frame.setVisible(true);
    }

    public void showSettingsDialog() {
        settingsDialog = new JDialog(frame, "Ustawienia", true);
        settingsDialog.setSize(400, 200);
        settingsDialog.setLocationRelativeTo(frame);
        settingsDialog.setLayout(new GridLayout(3, 1));

        // Rozmiar tekstu
        JLabel fontSizeLabel = new JLabel("Wybierz rozmiar tekstu:");
        fontSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        settingsDialog.add(fontSizeLabel);

        JSlider fontSizeSlider = new JSlider(10, 30, fontSize); // Ustawienie aktualnego rozmiaru tekstu
        fontSizeSlider.setMajorTickSpacing(5);
        fontSizeSlider.setMinorTickSpacing(1);
        fontSizeSlider.setPaintTicks(true);
        fontSizeSlider.setPaintLabels(true);
        settingsDialog.add(fontSizeSlider);

        // Opcja dźwięku
        JCheckBox soundCheckBox = new JCheckBox("Włącz dźwięki");
        soundCheckBox.setSelected(soundEnabled); // Ustawienie aktualnej opcji dźwięku
        settingsDialog.add(soundCheckBox);

        // Przycisk zatwierdzenia ustawień
        JButton applyButton = new JButton("Zastosuj");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pobierz wybrane ustawienia
                fontSize = fontSizeSlider.getValue();
                label.setFont(new Font("SANS_SERIF", Font.BOLD, fontSize)); // Zastosuj nowy rozmiar tekstu

                soundEnabled = soundCheckBox.isSelected(); // Zastosuj nową opcję dźwięku

                // Zamknij okno dialogowe
                settingsDialog.dispose();
            }
        });
        settingsDialog.add(applyButton);

        // Wyświetlenie okna dialogowego
        settingsDialog.setVisible(true);
    }

    public static void playSound() {
        if (soundEnabled) {
            try {
                // Ładowanie pliku dźwiękowego
                File soundFile = new File("src/GameInterface/click.wav");
                if (!soundFile.exists()) {
                    System.out.println("Plik dźwiękowy nie istnieje.");
                    return;
                }

                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);

                // Odtwarzanie dźwięku
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
