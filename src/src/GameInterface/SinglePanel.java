package GameInterface;

import GameLogic.SinglePlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePanel extends JPanel {
    private JTextField playerField;
    private JComboBox<String> colorComboBox;
    private JButton startButton;
    public SinglePanel(JFrame frame){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        Font font = new Font("SANS_SERIF", Font.BOLD, 15);

        playerField = new JTextField(10);
        playerField.setFont(font);
        colorComboBox = new JComboBox<>(new String[]{"Czerwony", "Niebieski", "Zielony"});
        colorComboBox.setFont(font);
        startButton = new JButton("Dalej");
            startButton.setFont(font);

        JLabel playerLabel = new JLabel("Podaj imię:");
            playerLabel.setFont(font);
        JLabel colorLabel = new JLabel("Kolor gracza:");
            colorLabel.setFont(font);

        add(playerLabel, gbc);
        gbc.gridx++;
        add(playerField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        add(colorLabel, gbc);
        gbc.gridx++;
        add(colorComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(startButton, gbc);
            startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = playerField.getText();
                Color playerColor = getColorFromString(colorComboBox.getSelectedItem().toString());

                SinglePlayer.setPlayerDetails(playerName, playerColor);

                SinglePlayer gamePanel = new SinglePlayer(frame);
                frame.setContentPane(gamePanel);
                frame.revalidate();
                frame.repaint();
                StartPanel.playSound();
            }
        });
    }

    private Color getColorFromString(String colorString) {
        switch (colorString) {
            case "Czerwony":
                return Color.RED;
            case "Niebieski":
                return Color.BLUE;
            case "Zielony":
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }
}
