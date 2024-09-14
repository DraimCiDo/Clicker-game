package me.draimgoose.panels;

import me.draimgoose.GamePanel;
import me.draimgoose.PlayerClickManager;
import me.draimgoose.animations.AnimationManager;
import me.draimgoose.animations.CookieAnimation;
import me.draimgoose.config.GameConfig;  // Импортируем GameConfig
import me.draimgoose.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ClickerPanel {
    private JPanel panel;
    private JLabel cookieLabel;
    private JLabel scoreLabel;
    private JLabel autoClickLabel;
    private JLabel batteryLabel;
    private BufferedImage originalCookieImage;
    private List<JLabel> activePlusOneLabels;
    private Stack<JLabel> labelPool;  // Пул для меток "+1"
    private GamePanel gamePanel;
    private AnimationManager animationManager;

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
        panel.setLayout(null);

        this.animationManager = new AnimationManager(panel);

        // Инициализация меток и панели
        initializeComponents();
    }

    private void initializeComponents() {
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(150, 10, 200, 30);
        panel.add(scoreLabel);

        autoClickLabel = new JLabel("+0/s");
        autoClickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autoClickLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        autoClickLabel.setForeground(Color.CYAN);
        autoClickLabel.setBounds(150, 40, 200, 30);
        panel.add(autoClickLabel);

        batteryLabel = new JLabel("Battery: 100/100");
        batteryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        batteryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        batteryLabel.setForeground(Color.GREEN);
        batteryLabel.setBounds(150, 70, 200, 30);
        panel.add(batteryLabel);

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
                    handleCookieClick(originalY);
                }
            });
        } else {
            System.out.println("Ошибка: изображение печенья не удалось загрузить.");
        }

        activePlusOneLabels = new ArrayList<>();
        labelPool = new Stack<>();  // Инициализируем пул меток
    }

    private void handleCookieClick(int originalY) {
        // Обработка клика на печеньку
        PlayerClickManager manager = gamePanel.getPlayerClickManager();
        if (manager.canClick()) {
            manager.useClick();
            gamePanel.setScore(gamePanel.getScore() + manager.getClickValue());

            // Проверяем, включены ли анимации
            if (GameConfig.areAnimationsEnabled()) {
                // Используем метки из пула для анимации "+1"
                SwingUtilities.invokeLater(() -> {
                    showPlusOneAnimation(manager.getClickValue());
                    new CookieAnimation(cookieLabel, originalY, animationManager);
                });
            }
        } else {
            JOptionPane.showMessageDialog(panel, "Батарея разряжена! Подождите восстановления.");
        }
    }

    private void showPlusOneAnimation(int clickValue) {
        JLabel plusOneLabel;
        if (!labelPool.isEmpty()) {
            plusOneLabel = labelPool.pop();  // Берем метку из пула
            plusOneLabel.setText("+" + clickValue);
        } else {
            plusOneLabel = new JLabel("+" + clickValue);
            plusOneLabel.setForeground(Color.RED);
            plusOneLabel.setFont(new Font("Arial", Font.BOLD, 18));
            plusOneLabel.setSize(50, 30);
        }

        int randomX = (int) (Math.random() * (panel.getWidth() - plusOneLabel.getWidth()));
        final int[] randomY = {(int) (Math.random() * (panel.getHeight() - plusOneLabel.getHeight()))}; // Используем массив

        plusOneLabel.setLocation(randomX, randomY[0]);
        panel.add(plusOneLabel);
        activePlusOneLabels.add(plusOneLabel);

        animationManager.addAnimatedObject(() -> {
            randomY[0] -= 2;  // Изменяем значение в массиве
            plusOneLabel.setLocation(randomX, randomY[0]);
            if (randomY[0] < -plusOneLabel.getHeight()) {
                panel.remove(plusOneLabel);
                labelPool.push(plusOneLabel);  // Возвращаем метку в пул
                return false;
            }
            return true;
        });
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
