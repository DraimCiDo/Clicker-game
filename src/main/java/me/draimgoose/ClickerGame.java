package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickerGame {
    private JFrame frame;
    private JPanel panel;
    private JLabel cookieLabel;
    private JLabel scoreLabel;
    private int score;
    private Timer animationTimer;
    private int animationStep = 0;
    private int originalY;

    public ClickerGame() {
        // Initialize components
        frame = new JFrame("Clicker Game");
        panel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        score = 0;

        // Load cookie image
        ImageIcon cookieIcon = new ImageIcon(getClass().getResource("/cookie.png"));
        cookieLabel = new JLabel(cookieIcon);
        cookieLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set up frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Set up panel
        panel.setLayout(null);  // Use absolute positioning for smooth animation
        scoreLabel.setBounds(10, 10, 100, 30);
        panel.add(scoreLabel);

        // Set initial position of cookieLabel
        cookieLabel.setBounds(150, 150, cookieIcon.getIconWidth(), cookieIcon.getIconHeight());
        originalY = cookieLabel.getY(); // Save original Y position
        panel.add(cookieLabel);

        // Add MouseListener to cookie image for click events
        cookieLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                score++;
                scoreLabel.setText("Score: " + score);
                startAnimation();
            }
        });

        // Add panel to frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop(); // Stop any existing animations
        }

        animationStep = 0; // Reset animation step

        // Create a timer to handle the animation
        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int newY = originalY + (int) (Math.sin(animationStep * 0.2) * 20); // Smooth up and down motion

                cookieLabel.setLocation(cookieLabel.getX(), newY);

                if (animationStep >= 30) { // Finish after a certain number of steps
                    animationTimer.stop();
                    cookieLabel.setLocation(cookieLabel.getX(), originalY); // Reset to original position
                }
            }
        });
        animationTimer.start();
    }

    public static void main(String[] args) {
        // Run the game
        SwingUtilities.invokeLater(() -> new ClickerGame());
    }
}
