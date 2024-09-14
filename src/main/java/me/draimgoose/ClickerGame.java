package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickerGame {
    private JFrame frame;
    private JPanel panel;
    private JButton clickButton;
    private JLabel scoreLabel;
    private int score;

    public ClickerGame() {
        // Initialize components
        frame = new JFrame("Clicker Game");
        panel = new JPanel();
        clickButton = new JButton("Click Me!");
        scoreLabel = new JLabel("Score: 0");
        score = 0;

        // Set up frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // Set up panel
        panel.setLayout(new BorderLayout());
        panel.add(scoreLabel, BorderLayout.NORTH);
        panel.add(clickButton, BorderLayout.CENTER);

        // Add ActionListener to button
        clickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                score++;
                scoreLabel.setText("Score: " + score);
            }
        });

        // Add panel to frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Run the game
        SwingUtilities.invokeLater(() -> new ClickerGame());
    }
}
