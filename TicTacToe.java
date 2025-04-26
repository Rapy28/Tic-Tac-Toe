// Tic Tac Toe is fun!

import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends Frame implements ActionListener {
    private Button[][] buttons = new Button[3][3];
    private char currentPlayer = 'X';
    private Label statusLabel;
    private Button newGameButton;

    public TicTacToe() {
        setTitle("Magaso's Tic Tac Toe Game");
        setSize(350, 350);
        setLayout(new BorderLayout());

        Panel gridPanel = new Panel();
        gridPanel.setLayout(new GridLayout(3, 3));

        Font font = new Font("Arial", Font.BOLD, 40);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button("");
                buttons[i][j].setFont(font);
                buttons[i][j].addActionListener(this);
                gridPanel.add(buttons[i][j]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new GridLayout(2, 1));

        statusLabel = new Label("Player X's Turn", Label.CENTER);
        newGameButton = new Button("New Game");
        newGameButton.addActionListener(e -> resetGame());

        bottomPanel.add(statusLabel);
        bottomPanel.add(newGameButton);
        add(bottomPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
            }
        });

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Button clicked = (Button) e.getSource();

        if (!clicked.getLabel().equals("")) {
            return; // already clicked
        }

        clicked.setLabel(String.valueOf(currentPlayer));

        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableButtons();
        } else if (isDraw()) {
            statusLabel.setText("It's a Draw!");
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            // Rows and Columns
            if (checkLine(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    checkLine(buttons[0][i], buttons[1][i], buttons[2][i])) {
                return true;
            }
        }

        // Diagonals
        return checkLine(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                checkLine(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean checkLine(Button b1, Button b2, Button b3) {
        String s1 = b1.getLabel();
        return !s1.equals("") && s1.equals(b2.getLabel()) && s1.equals(b3.getLabel());
    }

    private boolean isDraw() {
        for (Button[] row : buttons) {
            for (Button b : row) {
                if (b.getLabel().equals("")) return false;
            }
        }
        return true;
    }

    private void disableButtons() {
        for (Button[] row : buttons) {
            for (Button b : row) {
                b.setEnabled(false);
            }
        }
    }

    private void resetGame() {
        for (Button[] row : buttons) {
            for (Button b : row) {
                b.setLabel("");
                b.setEnabled(true);
            }
        }
        currentPlayer = 'X';
        statusLabel.setText("Player X's Turn");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
