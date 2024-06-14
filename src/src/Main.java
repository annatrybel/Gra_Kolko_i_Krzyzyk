import GameInterface.StartPanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartPanel gameFrame = new StartPanel();
            gameFrame.setVisible(true);
        });
    }
}
