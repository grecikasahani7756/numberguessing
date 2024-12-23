import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {

    private int generatedNumber;
    private int attemptsLeft;
    private int score;
    private int roundCount;
    private JLabel feedbackLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JButton playAgainButton;
    private JLabel scoreLabel;
    private JPanel controlPanel;
    private JPanel resultPanel;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        score = 0;
        roundCount = 0;

        JPanel instructionPanel = new JPanel();
        instructionPanel.add(new JLabel("<html>Welcome to the game!<br>Guess the number between 1 and 100.<br>Try to guess in fewer attempts to score more points!</html>"));
        add(instructionPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(2, 1));

        feedbackLabel = new JLabel("You have 10 attempts. Start guessing!");
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gamePanel.add(feedbackLabel);

        guessField = new JTextField(10);
        guessButton = new JButton("Guess");
        JPanel guessPanel = new JPanel();
        guessPanel.add(new JLabel("Enter your guess:"));
        guessPanel.add(guessField);
        guessPanel.add(guessButton);
        gamePanel.add(guessPanel);

        add(gamePanel, BorderLayout.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        scoreLabel = new JLabel("Score: " + score);
        playAgainButton = new JButton("Play Again");
        playAgainButton.setEnabled(false);
        controlPanel.add(scoreLabel);
        controlPanel.add(playAgainButton);
        add(controlPanel, BorderLayout.EAST);

        resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.add(new JLabel(""));

        add(resultPanel, BorderLayout.SOUTH);

        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userInput = guessField.getText();
                try {
                    int userGuess = Integer.parseInt(userInput);
                    if (userGuess < 1 || userGuess > 100) {
                        feedbackLabel.setText("Please guess between 1 and 100.");
                    } else {
                        checkGuess(userGuess);
                    }
                } catch (NumberFormatException ex) {
                    feedbackLabel.setText("Oops! Please enter a valid number.");
                }
            }
        });

        playAgainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewRound();
            }
        });

        startNewRound();
    }

    private void startNewRound() {
        roundCount++;
        generatedNumber = new Random().nextInt(100) + 1;
        attemptsLeft = 10;
        feedbackLabel.setText("Round " + roundCount + " started! You have " + attemptsLeft + " attempts.");
        guessField.setText("");
        resultPanel.removeAll();
        resultPanel.add(new JLabel("Start guessing!"));
        playAgainButton.setEnabled(false);
        guessButton.setEnabled(true);
        scoreLabel.setText("Score: " + score);
        revalidate();
        repaint();
    }

    private void checkGuess(int userGuess) {
        attemptsLeft--;

        if (userGuess == generatedNumber) {
            score += 10;
            resultPanel.removeAll();
            resultPanel.add(new JLabel("Correct! The number was " + generatedNumber + "."));
            resultPanel.add(new JLabel("You earned 10 points!"));
            guessButton.setEnabled(false);
            playAgainButton.setEnabled(true);
        } else if (userGuess < generatedNumber) {
            String clue = (generatedNumber - userGuess) > 10 ? "It's much higher!" : "It's a bit higher!";
            resultPanel.removeAll();
            resultPanel.add(new JLabel("Too low! " + clue));
            resultPanel.add(new JLabel("You have " + attemptsLeft + " attempts left."));
        } else {
            String clue = (userGuess - generatedNumber) > 10 ? "It's much lower!" : "It's a bit lower!";
            resultPanel.removeAll();
            resultPanel.add(new JLabel("Too high! " + clue));
            resultPanel.add(new JLabel("You have " + attemptsLeft + " attempts left."));
        }

        if (attemptsLeft == 0 && userGuess != generatedNumber) {
            resultPanel.removeAll();
            resultPanel.add(new JLabel("Out of attempts! The number was " + generatedNumber + "."));
            resultPanel.add(new JLabel("Better luck next time!"));
            guessButton.setEnabled(false);
            playAgainButton.setEnabled(true);
        }

        scoreLabel.setText("Score: " + score);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NumberGuessingGame().setVisible(true);
            }
        });
    }
}
