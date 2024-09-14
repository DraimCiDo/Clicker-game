package me.draimgoose.panels;

import me.draimgoose.GamePanel;

import javax.swing.*;
import java.awt.*;

public class UpgradePanel {
    private JPanel panel;

    public UpgradePanel(GamePanel gamePanel) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Используем BoxLayout для размещения кнопок вертикально
        panel.setBackground(new Color(30, 30, 30)); // Устанавливаем темный фон для панели

        initializeComponents(gamePanel);
    }

    private void initializeComponents(GamePanel gamePanel) {
        JLabel titleLabel = new JLabel("Улучшения");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Центрируем заголовок
        panel.add(titleLabel);

        JButton idleClickUpgradeButton = new JButton("Купить Idle Clicking (+1/s) за 10 очков");
        idleClickUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        idleClickUpgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        idleClickUpgradeButton.addActionListener(e -> {
            if (gamePanel.getScore() >= 10) {
                gamePanel.setScore(gamePanel.getScore() - 10);
                gamePanel.getClicker().increaseAutoClicks();
            } else {
                JOptionPane.showMessageDialog(panel, "Недостаточно очков!");
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Добавляем отступы
        panel.add(idleClickUpgradeButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
