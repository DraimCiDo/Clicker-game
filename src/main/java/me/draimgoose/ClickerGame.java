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
        panel.setLayout(new BorderLayout());
        panel.add(scoreLabel, BorderLayout.NORTH);
        panel.add(cookieLabel, BorderLayout.CENTER);

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
        animationTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int scaleAmount = animationStep % 20 < 10 ? 5 : -5; // Scale up then down

                // Create new image icon with scaled size
                ImageIcon scaledIcon = new ImageIcon(
                        new ImageIcon(getClass().getResource("/cookie.png"))
                                .getImage()
                                .getScaledInstance(
                                        cookieLabel.getWidth() + scaleAmount,
                                        cookieLabel.getHeight() + scaleAmount,
                                        Image.SCALE_SMOOTH
                                )
                );

                cookieLabel.setIcon(scaledIcon);

                if (animationStep >= 20) {
                    animationTimer.stop();
                    cookieLabel.setIcon(new ImageIcon(getClass().getResource("/cookie.png"))); // Reset to original size
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
