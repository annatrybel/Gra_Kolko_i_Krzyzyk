package GameInterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerPanel extends JPanel {
    private JTextField player1Field;
    private JTextField player2Field;
    private JComboBox<String> colorComboBox1;
    private JComboBox<String> colorComboBox2;
    private JButton startButton;

    public PlayerPanel(JFrame frame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        Font font = new Font("SANS_SERIF", Font.BOLD, 15);

        player1Field = new JTextField(10);
        player1Field.setFont(font);
        player2Field = new JTextField(10);
        player2Field.setFont(font);
        colorComboBox1 = new JComboBox<>(new String[]{"Czerwony", "Niebieski", "Zielony"});
        colorComboBox1.setFont(font);
        colorComboBox2 = new JComboBox<>(new String[]{"Czerwony", "Niebieski", "Zielony"});
        colorComboBox2.setFont(font);
        startButton = new JButton("Dalej");
        startButton.setFont(font);

        JLabel player1Label = new JLabel("Gracz 1:");
        player1Label.setFont(font);
        JLabel player2Label = new JLabel("Gracz 2:");
        player2Label.setFont(font);
        JLabel color1Label = new JLabel("Kolor gracza 1:");
        color1Label.setFont(font);
        JLabel color2Label = new JLabel("Kolor gracza 2:");
        color2Label.setFont(font);

        add(player1Label, gbc);
        gbc.gridx++;
        add(player1Field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        add(player2Label, gbc);
        gbc.gridx++;
        add(player2Field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        add(color1Label, gbc);
        gbc.gridx++;
        add(colorComboBox1, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        add(color2Label, gbc);
        gbc.gridx++;
        add(colorComboBox2, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(startButton, gbc);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String player1Name = player1Field.getText();
                String player2Name = player2Field.getText();
                Color player1Color = getColorFromString(colorComboBox1.getSelectedItem().toString());
                Color player2Color = getColorFromString(colorComboBox2.getSelectedItem().toString());

                // Ustaw dane graczy w GameLogic
                GameLogic.GameLogic.setPlayerDetails(player1Name, player2Name, player1Color, player2Color);

                // Przełącz na GamePanel
                GamePanel gamePanel = new GamePanel(frame);
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
