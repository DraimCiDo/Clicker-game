package me.draimgoose.panels;

import me.draimgoose.GamePanel;
import me.draimgoose.PlayerClickManager;
import me.draimgoose.animations.CookieAnimation;
import me.draimgoose.animations.PlusOneAnimation;
import me.draimgoose.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ClickerPanel {
    private JPanel panel;
    private JLabel cookieLabel;
    private JLabel scoreLabel;
    private JLabel autoClickLabel;
    private JLabel batteryLabel;
    private BufferedImage originalCookieImage;
    private List<JLabel> activePlusOneLabels; // Список активных меток "+1"
    private GamePanel gamePanel;

    public ClickerPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageLoader.loadImage("/background.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(null); // Используем абсолютное позиционирование

        // Инициализация меток и панели
        initializeComponents();
    }

    private void initializeComponents() {
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(150, 10, 200, 30); // Центрируем по ширине
        panel.add(scoreLabel);

        autoClickLabel = new JLabel("+0/s");
        autoClickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autoClickLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        autoClickLabel.setForeground(Color.CYAN);
        autoClickLabel.setBounds(150, 40, 200, 30); // Центрируем по ширине
        panel.add(autoClickLabel);

        batteryLabel = new JLabel("Battery: 100/100");
        batteryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        batteryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        batteryLabel.setForeground(Color.GREEN);
        batteryLabel.setBounds(150, 70, 200, 30); // Центрируем по ширине
        panel.add(batteryLabel);

        // Загрузка изображения печенья с использованием утилиты ImageLoader
        originalCookieImage = ImageLoader.loadImage("/cookie.png");

        if (originalCookieImage != null) {
            Image scaledImage = originalCookieImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            cookieLabel = new JLabel(new ImageIcon(scaledImage));
            cookieLabel.setBounds(200, 150, 100, 100);
            int originalY = cookieLabel.getY();
            panel.add(cookieLabel);

            cookieLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    PlayerClickManager manager = gamePanel.getPlayerClickManager();
                    if (manager.canClick()) {
                        manager.useClick();
                        gamePanel.setScore(gamePanel.getScore() + manager.getClickValue());
                        PlusOneAnimation.showPlusOneAnimation(panel, activePlusOneLabels, manager.getClickValue());
                        new CookieAnimation(cookieLabel, originalY);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Батарея разряжена! Подождите восстановления.");
                    }
                }
            });
        } else {
            System.out.println("Ошибка: изображение печенья не удалось загрузить.");
        }

        activePlusOneLabels = new ArrayList<>();
    }

    private JLabel createPlusOneLabel(int value) {
        JLabel plusOneLabel = new JLabel("+" + value);
        plusOneLabel.setForeground(Color.RED);
        plusOneLabel.setFont(new Font("Arial", Font.BOLD, 18));
        plusOneLabel.setSize(50, 30);
        return plusOneLabel;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void updateScoreDisplay() {
        scoreLabel.setText("Score: " + gamePanel.getScore());
    }

    public void updateAutoClickDisplay(int autoClicks) {
        autoClickLabel.setText("+" + autoClicks + "/s");
    }

    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        batteryLabel.setText("Battery: " + currentBattery + "/" + maxBattery);
    }
}
