package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradePanel {
    private JPanel upgradePanel;
    private GamePanel gamePanel;
    private Timer animationTimer; // Таймер для анимации панели
    private boolean isAnimating = false; // Флаг состояния анимации

    public UpgradePanel(JPanel parentPanel, JFrame frame, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        upgradePanel = new JPanel();
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.Y_AXIS));
        upgradePanel.setBackground(new Color(0, 0, 0, 200));
        upgradePanel.setBounds(frame.getWidth(), 0, 200, frame.getHeight());

        // Кнопка для покупки улучшения Idle Clicking
        JButton idleClickUpgradeButton = new JButton("Купить Idle Clicking (+1/s) за 10 очков");
        idleClickUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        idleClickUpgradeButton.addActionListener(e -> {
            if (gamePanel.getScore() >= 10) {
                gamePanel.setScore(gamePanel.getScore() - 10);
                gamePanel.getClicker().increaseAutoClicks();
            } else {
                JOptionPane.showMessageDialog(frame, "Недостаточно очков!");
            }
        });

        upgradePanel.add(idleClickUpgradeButton);
        parentPanel.add(upgradePanel);
    }

    public void adaptUpgradePanel(int frameHeight) {
        upgradePanel.setBounds(upgradePanel.getX(), 0, 200, frameHeight);
    }

    public void toggleUpgradePanel(JFrame frame) {
        if (isAnimating) return; // Проверка состояния: если идет анимация, выходим

        isAnimating = true; // Устанавливаем флаг состояния

        int targetX = (upgradePanel.getX() == frame.getWidth()) ? frame.getWidth() - 200 : frame.getWidth();
        animationTimer = new Timer(10, null);
        animationTimer.addActionListener(new ActionListener() {
            int step = 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                int currentX = upgradePanel.getX();
                if (currentX != targetX) {
                    upgradePanel.setLocation(currentX + (targetX < currentX ? -step : step), 0);
                    frame.repaint();
                } else {
                    animationTimer.stop();
                    isAnimating = false; // Сбрасываем флаг состояния после завершения анимации
                }
            }
        });
        animationTimer.start();
    }
}