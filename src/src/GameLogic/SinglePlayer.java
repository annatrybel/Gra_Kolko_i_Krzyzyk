package GameLogic;

import GameInterface.CountPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SinglePlayer extends JPanel {
    private JFrame frame;
    private JButton[][] buttons;
    private char[][] board;
    private boolean playerTurn;
    private static String graczName;
    private static Color kolorGracz;
    private static char gracz = 'X';

    public SinglePlayer(JFrame frame) {
        this.frame = frame;
        initializeSinglePlayerPanel();
    }

    public static void setPlayerDetails(String playerName, Color playerColor) {
        graczName = playerName;
        kolorGracz = playerColor;
    }

    private void initializeSinglePlayerPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Gra");
        titleLabel.setFont(new Font("SANS_SERIF", Font.BOLD, 25));
        add(titleLabel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        board = new char[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font("SANS_SERIF", Font.BOLD, 80));
                button.addActionListener(new ButtonClickListener(row, col));
                gamePanel.add(button);
                buttons[row][col] = button;
                board[row][col] = ' ';
            }
        }

        add(gamePanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Powrót");
        backButton.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CountPlayer countPanel = new CountPlayer(frame);
                frame.setContentPane(countPanel);
                frame.revalidate();
                frame.repaint();
            }
        });
        add(backButton, BorderLayout.SOUTH);

        playerTurn = true;
    }

    private void botMove() {
        int[] move = getBestMove();
        int row = move[0];
        int col = move[1];

        buttons[row][col].setText("O");
        buttons[row][col].setForeground(Color.GRAY);

        board[row][col] = 'O';

        if (checkWin('O')) {
            JOptionPane.showMessageDialog(frame, "Bot wygrał!");
            resetujPlansze();
        } else if (czyPlanszaPelna()) {
            JOptionPane.showMessageDialog(frame, "Remis!");
            resetujPlansze();
        } else {
            playerTurn = true;
        }
    }

    private int[] getBestMove() {
        int[] bestMove = new int[]{-1, -1};
        int bestScore = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    board[row][col] = 'O';
                    int score = minimax(board, 0, false);
                    board[row][col] = ' ';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = row;
                        bestMove[1] = col;
                    }
                }
            }
        }

        return bestMove;
    }

    private int minimax(char[][] board, int depth, boolean isMaximizing) {
        if (checkWin('O')) return 1;
        if (checkWin('X')) return -1;
        if (czyPlanszaPelna()) return 0;

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = 'O';
                        int score = minimax(board, depth + 1, false);
                        board[row][col] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board[row][col] == ' ') {
                        board[row][col] = 'X';
                        int score = minimax(board, depth + 1, true);
                        board[row][col] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    private boolean czyPlanszaPelna() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            makeMove(row, col);
        }
    }

    private void makeMove(int row, int col) {
        if (playerTurn && board[row][col] == ' ') {
            buttons[row][col].setText("X");
            buttons[row][col].setForeground(kolorGracz);
            board[row][col] = 'X';

            if (checkWin('X')) {
                JOptionPane.showMessageDialog(frame, "Wygrałeś!");
                resetujPlansze();
            } else if (czyPlanszaPelna()) {
                JOptionPane.showMessageDialog(frame, "Remis!");
                resetujPlansze();
            } else {
                playerTurn = false;
                botMove();
            }
        }
    }

    private void resetujPlansze() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                board[row][col] = ' ';
            }
        }
        playerTurn = true;
    }
}


