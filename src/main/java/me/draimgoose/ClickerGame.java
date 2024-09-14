package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

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
        // инициализация компонентов
        frame = new JFrame("Clicker Game");
        panel = new JPanel();
        scoreLabel = new JLabel("Score: 0");
        score = 0;

        // Загрузка изображения печенья
        ImageIcon cookieIcon = null;
        try {
            String imagePath = "/cookie.png";  // Путь от корня ресурсов
            URL resource = getClass().getResource(imagePath);
            if (resource == null) {
                System.out.println("Debug: Image not found at path: " + imagePath);
            } else {
                System.out.println("Debug: Image found at path: " + resource.toString());
                cookieIcon = new ImageIcon(resource);
                if (cookieIcon.getIconWidth() == -1) {
                    System.out.println("Debug: Image couldn't load properly.");
                } else {
                    System.out.println("Debug: Image loaded successfully.");
                }
            }
        } catch (Exception e) {
            System.out.println("Debug: Exception while loading image - " + e.getMessage());
        }

        // инициализация метки с загруженным изображением
        cookieLabel = new JLabel(cookieIcon);
        cookieLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Настройка окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        // Настройка панели
        panel.setLayout(null);  // используем абсолютное позиционирование для плавной анимации
        scoreLabel.setBounds(10, 10, 100, 30);
        panel.add(scoreLabel);

        // Установите начальное положение cookieLabel, если изображение загружено
        if (cookieIcon != null && cookieIcon.getIconWidth() != -1) {
            cookieLabel.setBounds(150, 150, cookieIcon.getIconWidth(), cookieIcon.getIconHeight());
            originalY = cookieLabel.getY(); // Сохраните исходное положение по оси Y
            panel.add(cookieLabel);
        } else {
            System.out.println("Debug: Cookie icon is null or image not loaded properly.");
        }

        // Добавьте MouseListener к изображению печенья для обработки событий кликов
        cookieLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                score++;
                scoreLabel.setText("Score: " + score);
                startAnimation();
                System.out.println("Debug: Cookie clicked. Current score: " + score);
            }
        });

        // Добавьте панель в окно
        frame.add(panel);

        // Сделайте окно видимым
        frame.setVisible(true);
        System.out.println("Debug: Application started.");
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop(); // Остановить любую существующую анимацию
        }

        animationStep = 0; // Сброс шага анимации

        // Создаем таймер для обработки анимации
        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int newY = originalY + (int) (Math.sin(animationStep * 0.2) * 20); // Плавное движение вверх и вниз

                cookieLabel.setLocation(cookieLabel.getX(), newY);

                if (animationStep >= 30) { // Завершить после определенного количества шагов
                    animationTimer.stop();
                    cookieLabel.setLocation(cookieLabel.getX(), originalY); // Вернуться в исходное положение
                    System.out.println("Debug: Animation completed.");
                }
            }
        });
        animationTimer.start();
        System.out.println("Debug: Animation started.");
    }

    public static void main(String[] args) {
        // Запустите игру
        SwingUtilities.invokeLater(() -> new ClickerGame());
    }
}
