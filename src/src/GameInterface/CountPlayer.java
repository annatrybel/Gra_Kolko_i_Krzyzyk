package GameInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CountPlayer extends JPanel {
    private JFrame frame;

    public CountPlayer(JFrame frame) {
        this.frame = frame;
        initializeCountPanel();
    }
    private void initializeCountPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton onePlayerButton = new JButton("1 Gracz");
        onePlayerButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        onePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SinglePanel singlePanel = new SinglePanel(frame);
                frame.setContentPane(singlePanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        add(onePlayerButton);

        JButton twoPlayersButton = new JButton("2 Gracze");
        twoPlayersButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        twoPlayersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerPanel playerPanel = new PlayerPanel(frame);
                frame.setContentPane(playerPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        add(twoPlayersButton);
    }
}
