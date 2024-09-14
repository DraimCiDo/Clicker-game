package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Random;

public class GamePanel {
    private JFrame frame;
    private JPanel panel;
    private JLabel cookieLabel;
    private JLabel scoreLabel;
    private JLabel autoClickLabel;
    private int score;
    private Timer animationTimer;
    private int animationStep = 0;
    private int originalY;
    private Image originalCookieImage;
    private Image backgroundImage;
    private Random random = new Random();
    private UpgradePanel upgradePanel; // Панель для улучшений
    private Clicker clicker; // Логика автоматических кликов

    public GamePanel() {
        // Настройка окна
        frame = new JFrame("Clicker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setResizable(false); // Запрещаем изменение размера окна
        frame.setLocationRelativeTo(null); // Центрируем окно на экране

        // Загрузка фонового изображения
        URL bgUrl = getClass().getResource("/background.jpg");
        if (bgUrl != null) {
            backgroundImage = new ImageIcon(bgUrl).getImage();
        } else {
            System.out.println("Ошибка: фоновое изображение не найдено.");
        }

        // Настройка панели
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(null); // Используем абсолютное позиционирование
        frame.add(panel);

        // Метка для отображения счета
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(150, 10, 200, 30); // Центрируем по ширине
        panel.add(scoreLabel);

        // Метка для отображения скорости автоматических кликов
        autoClickLabel = new JLabel("+0/s");
        autoClickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autoClickLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        autoClickLabel.setForeground(Color.CYAN);
        autoClickLabel.setBounds(150, 40, 200, 30); // Центрируем по ширине
        panel.add(autoClickLabel);

        // Загрузка исходного изображения печенья
        URL imageUrl = getClass().getResource("/cookie.png");
        if (imageUrl == null) {
            System.out.println("Ошибка: изображение печенья не загружено.");
            return;
        }

        originalCookieImage = new ImageIcon(imageUrl).getImage();

        // Инициализируем метку с изображением печенья
        cookieLabel = new JLabel(new ImageIcon(originalCookieImage));
        cookieLabel.setBounds(200, 150, 100, 100); // Позиционирование по центру панели
        originalY = cookieLabel.getY();
        panel.add(cookieLabel);

        // Добавляем обработчик событий для клика по изображению
        cookieLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                score++;
                updateScoreDisplay();
                showPlusOneAnimation(); // Показываем анимацию "+1"
                startAnimation();
            }
        });

        // Создаем панель "Улучшения"
        upgradePanel = new UpgradePanel(panel, frame, this);

        // Кнопка для открытия меню улучшений
        JButton upgradeButton = new JButton("Улучшения");
        upgradeButton.setBounds(10, frame.getHeight() - 70, 150, 40);
        upgradeButton.setFont(new Font("Arial", Font.BOLD, 16));
        upgradeButton.setBackground(new Color(70, 130, 180)); // Синий цвет фона кнопки
        upgradeButton.setForeground(Color.WHITE); // Белый цвет текста кнопки
        upgradeButton.setFocusPainted(false); // Убираем рамку фокуса
        upgradeButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2)); // Устанавливаем границу
        upgradeButton.addActionListener(e -> upgradePanel.toggleUpgradePanel(frame));
        panel.add(upgradeButton);

        // Добавляем ComponentListener для адаптации к изменениям размера окна
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adaptComponentsToWindowSize();
            }
        });

        // Делаем окно видимым
        frame.setVisible(true);

        // Инициализация логики автоматических кликов
        clicker = new Clicker(this);
    }

    // Метод для обновления отображения счета
    public void updateScoreDisplay() {
        scoreLabel.setText("Score: " + score);
    }

    // Метод для обновления отображения автоматических кликов
    public void updateAutoClickDisplay(int autoClicksPerSecond) {
        autoClickLabel.setText("+" + autoClicksPerSecond + "/s");
    }

    // Метод для отображения анимации "+1" в случайном месте
    private void showPlusOneAnimation() {
        int frameWidth = frame.getContentPane().getWidth();
        int frameHeight = frame.getContentPane().getHeight();

        // Генерируем случайные координаты для метки "+1"
        int randomX = random.nextInt(frameWidth - 50);
        int randomY = random.nextInt(frameHeight - 50);

        JLabel plusOneLabel = new JLabel("+1");
        plusOneLabel.setForeground(Color.RED);
        plusOneLabel.setFont(new Font("Arial", Font.BOLD, 18));
        plusOneLabel.setBounds(randomX, randomY, 50, 30);
        panel.add(plusOneLabel);

        Timer plusOneTimer = new Timer(50, new ActionListener() {
            int steps = 0;
            int startY = randomY;

            @Override
            public void actionPerformed(ActionEvent e) {
                steps++;
                plusOneLabel.setLocation(randomX, startY - steps * 2);
                if (steps > 20) {
                    ((Timer) e.getSource()).stop();
                    panel.remove(plusOneLabel);
                    panel.repaint();
                }
            }
        });
        plusOneTimer.start();
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        animationStep = 0;
        int animationRange = 20;

        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int newY = originalY + (int) (Math.sin(animationStep * 0.2) * animationRange);

                cookieLabel.setLocation(cookieLabel.getX(), newY);
                panel.repaint();

                if (animationStep >= 30) {
                    animationTimer.stop();
                    cookieLabel.setLocation(cookieLabel.getX(), originalY);
                    panel.repaint();
                }
            }
        });
        animationTimer.start();
    }

    // Метод для адаптации компонентов под размер окна
    private void adaptComponentsToWindowSize() {
        int frameWidth = frame.getContentPane().getWidth();
        int frameHeight = frame.getContentPane().getHeight();

        // Определяем новые размеры для печенья (50% от ширины и 50% от высоты окна)
        int newWidth = (int) (frameWidth * 0.5);
        int newHeight = (int) (frameHeight * 0.5);

        Image scaledImage = originalCookieImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        cookieLabel.setIcon(new ImageIcon(scaledImage));

        int newX = (frameWidth - newWidth) / 2;
        int newY = (frameHeight - newHeight) / 2;
        cookieLabel.setBounds(newX, newY, newWidth, newHeight);
        originalY = newY;

        scoreLabel.setBounds(frameWidth / 2 - 100, 10, 200, 30);
        autoClickLabel.setBounds(frameWidth / 2 - 100, 40, 200, 30);

        upgradePanel.adaptUpgradePanel(frameHeight);
        panel.repaint();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateScoreDisplay();
    }

    public Clicker getClicker() {
        return clicker;
    }
}
