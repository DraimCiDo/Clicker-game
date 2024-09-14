package me.draimgoose.panels;

import me.draimgoose.GamePanel;

import javax.swing.*;
import java.awt.*;

public class BoostPanel {
    private JPanel panel;

    public BoostPanel(GamePanel gamePanel) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Используем BoxLayout для размещения кнопок вертикально
        panel.setBackground(new Color(30, 30, 30)); // Устанавливаем темный фон для панели

        initializeComponents(gamePanel);
    }

    private void initializeComponents(GamePanel gamePanel) {
        JLabel titleLabel = new JLabel("Бусты");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Центрируем заголовок
        panel.add(titleLabel);

        JButton batteryUpgradeButton = new JButton("Улучшить батарею (+100 к батарее) за 20 очков");
        batteryUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        batteryUpgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        batteryUpgradeButton.addActionListener(e -> {
            if (gamePanel.getScore() >= 20) {
                gamePanel.setScore(gamePanel.getScore() - 20);
                gamePanel.getPlayerClickManager().increaseMaxBattery(100);
            } else {
                JOptionPane.showMessageDialog(panel, "Недостаточно очков!");
            }
        });

        JButton clickValueUpgradeButton = new JButton("Улучшить клик (+1 к клику) за 100 кликов");
        clickValueUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        clickValueUpgradeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clickValueUpgradeButton.addActionListener(e -> {
            if (gamePanel.getScore() >= 100) {
                gamePanel.setScore(gamePanel.getScore() - 100);
                gamePanel.getPlayerClickManager().increaseClickValue();
            } else {
                JOptionPane.showMessageDialog(panel, "Недостаточно кликов!");
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Добавляем отступы
        panel.add(batteryUpgradeButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Добавляем отступы
        panel.add(clickValueUpgradeButton);
    }

    public JPanel getPanel() {
        return panel;
    }
}
