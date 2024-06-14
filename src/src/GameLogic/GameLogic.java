package GameLogic;


import javax.swing.*;
import java.awt.*;

public class GameLogic {
    private static char aktualnyGracz = 'X';
    private static int wygraneGracz1 = 0;
    private static int wygraneGracz2 = 0;
    private static int iloscGier = 0;
    private static String gracz1;
    private static String gracz2;
    private static Color kolorGracz1;
    private static Color kolorGracz2;

    public static void setPlayerDetails(String player1Name, String player2Name, Color player1Color, Color player2Color) {
        gracz1 = player1Name;
        gracz2 = player2Name;
        kolorGracz1 = player1Color;
        kolorGracz2 = player2Color;
    }
    public static void implementacjaGry(JButton button, JButton[][] buttons, int row, int col, JFrame frame) {
        if (button.getText().isEmpty()) {
            button.setText(String.valueOf(aktualnyGracz));
            button.setForeground((aktualnyGracz == 'X') ? kolorGracz1 : kolorGracz2);
            if (czyWygrana(buttons, aktualnyGracz, row, col)) {
                JOptionPane.showMessageDialog(frame, "Wygrał gracz " + aktualnyGracz);
                aktualizujStatystyki(aktualnyGracz);
                wyświetlStatystyki();
                resetujPlansze(buttons);
            } else if (czyPlanszaPelna(buttons)) {
                JOptionPane.showMessageDialog(frame, "Remis!");
                iloscGier++;
                resetujPlansze(buttons);
            } else {
                zmienGracza();
            }
        }
    }

    public static void zmienGracza() {
        aktualnyGracz = (aktualnyGracz == 'X') ? 'O' : 'X';
    }

    public static boolean czyPlanszaPelna(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean czyWygrana(JButton[][] buttons, char aktualnyGracz, int row, int col) {
        if (buttons[row][0].getText().equals(String.valueOf(aktualnyGracz))
                && buttons[row][1].getText().equals(String.valueOf(aktualnyGracz))
                && buttons[row][2].getText().equals(String.valueOf(aktualnyGracz))) {
            return true;
        }
        if (buttons[0][col].getText().equals(String.valueOf(aktualnyGracz))
                && buttons[1][col].getText().equals(String.valueOf(aktualnyGracz))
                && buttons[2][col].getText().equals(String.valueOf(aktualnyGracz))) {
            return true;
        }
        if ((row == col || row + col == 2)) {
            if (buttons[0][0].getText().equals(String.valueOf(aktualnyGracz))
                    && buttons[1][1].getText().equals(String.valueOf(aktualnyGracz))
                    && buttons[2][2].getText().equals(String.valueOf(aktualnyGracz))) {
                return true;
            }
            if (buttons[0][2].getText().equals(String.valueOf(aktualnyGracz))
                    && buttons[1][1].getText().equals(String.valueOf(aktualnyGracz))
                    && buttons[2][0].getText().equals(String.valueOf(aktualnyGracz))) {
                return true;
            }
        }
        return false;
    }

    public static void resetujPlansze(JButton[][] buttons) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }

    public static void aktualizujStatystyki(char aktualnyGracz) {
        if (aktualnyGracz == 'X') {
            wygraneGracz1++;
        } else {
            wygraneGracz2++;
        }
        iloscGier++;
    }

    public static void wyświetlStatystyki() {
        JOptionPane.showMessageDialog(null, "Statystyka gier:\n" +
                gracz1 + " (X): " + wygraneGracz1 + " wygranych\n" +
                gracz2 + " (O): " + wygraneGracz2 + " wygranych\n" +
                "Liczba gier: " + iloscGier);
    }


    public static void resetStatistics() {
        wygraneGracz1 = 0;
        wygraneGracz2 = 0;
        iloscGier = 0;
    }

    public static String getGracz1() {
        return gracz1;
    }

    public static String getGracz2() {
        return gracz2;
    }

    public static char getAktualnyGracz() {
        return aktualnyGracz;
    }

    public static void ustawAktualnegoGracza(char gracz) {
        aktualnyGracz = gracz;
    }

    public static int getWygraneGracz1() {
        return wygraneGracz1;
    }

    public static int getWygraneGracz2() {
        return wygraneGracz2;
    }

    public static int getIloscGier() {
        return iloscGier;
    }
}
