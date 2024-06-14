package GameInterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GameLogic.GameLogic;

public class GamePanel extends JPanel {
    private JLabel label;
    private JButton changePlayerButton;
    private JPanel buttonsPanel;
    private JButton[][] buttons;

    public GamePanel(JFrame frame) {
        setLayout(new BorderLayout());

        label = new JLabel("Witaj w grze 'Kółko i Krzyżyk'!");
        add(label, BorderLayout.NORTH);

        changePlayerButton = new JButton("Zmień graczy");
        changePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerPanel playerPanel = new PlayerPanel(frame);
                frame.setContentPane(playerPanel);
                frame.revalidate();
                frame.repaint();
                StartPanel.playSound();
                GameLogic.resetStatistics();
            }
        });
        add(changePlayerButton, BorderLayout.SOUTH);

        buttonsPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton("");
                button.setFont(new Font("SANS_SERIF", Font.BOLD, 80));
                final int row = i;
                final int col = j;
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        GameLogic.implementacjaGry(button, buttons, row, col, frame);
                        StartPanel.playSound();
                    }
                });
                buttons[i][j] = button;
                buttonsPanel.add(button);
            }
        }

        add(buttonsPanel, BorderLayout.CENTER);
    }
}
