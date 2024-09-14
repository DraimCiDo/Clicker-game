package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GamePanel {
    private JFrame frame;
    private JPanel mainPanel;  // Основная панель для CardLayout
    private JPanel clickerPanel;  // Панель с кликом по печеньке
    private JPanel upgradePanel;  // Панель с улучшениями авто-кликинга
    private JPanel boostPanel;  // Панель с улучшениями кликов игрока
    private JPanel bottomPanel;  // Нижняя панель с кнопками
    private JLabel cookieLabel;
    private JLabel scoreLabel;
    private JLabel autoClickLabel;
    private JLabel batteryLabel;  // Метка для отображения состояния батареи
    private int score;
    private Timer animationTimer;
    private int animationStep = 0;
    private int originalY;
    private BufferedImage originalCookieImage;
    private Image backgroundImage;
    private Random random = new Random();
    private Clicker clicker; // Логика автоматических кликов
    private PlayerClickManager playerClickManager; // Логика кликов игрока и батареи

    private List<JLabel> activePlusOneLabels; // Список активных меток "+1"
    private static final int MAX_PLUS_ONE_LABELS = 10; // Максимальное количество меток "+1"

    public GamePanel() {
        // Настройка окна
        frame = new JFrame("Clicker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setResizable(false); // Запрещаем изменение размера окна
        frame.setLocationRelativeTo(null); // Центрируем окно на экране

        // Инициализация основной панели с CardLayout
        mainPanel = new JPanel(new CardLayout());

        // Загрузка фонового изображения
        URL bgUrl = getClass().getResource("/background.jpg");
        if (bgUrl != null) {
            backgroundImage = new ImageIcon(bgUrl).getImage();
        } else {
            System.out.println("Ошибка: фоновое изображение не найдено.");
        }

        // Создаем панели для CardLayout
        createClickerPanel();
        createUpgradePanel();
        createBoostPanel();

        // Добавляем панели в mainPanel
        mainPanel.add(clickerPanel, "ClickerPanel");
        mainPanel.add(upgradePanel, "UpgradePanel");
        mainPanel.add(boostPanel, "BoostPanel");

        // Устанавливаем начальный экран - ClickerPanel
        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "ClickerPanel");

        // Добавляем основную панель в окно
        frame.add(mainPanel, BorderLayout.CENTER);

        // Создаем нижнюю панель с кнопками
        createBottomPanel();

        // Добавляем нижнюю панель в окно
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Делаем окно видимым
        frame.setVisible(true);
    }

    private void createBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(60, 63, 65)); // Темный фон для панели

        JButton clickerButton = createStyledButton("Кликер");
        clickerButton.addActionListener(e -> switchPanel("ClickerPanel"));
        bottomPanel.add(clickerButton);

        JButton upgradeButton = createStyledButton("Улучшения");
        upgradeButton.addActionListener(e -> switchPanel("UpgradePanel"));
        bottomPanel.add(upgradeButton);

        JButton boostButton = createStyledButton("Бусты");
        boostButton.addActionListener(e -> switchPanel("BoostPanel"));
        bottomPanel.add(boostButton);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Синий цвет фона кнопки
        button.setForeground(Color.WHITE); // Белый цвет текста кнопки
        button.setFocusPainted(false); // Убираем рамку фокуса
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Устанавливаем внутренние отступы
        return button;
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelName);
    }

    private void createClickerPanel() {
        clickerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        clickerPanel.setLayout(null); // Используем абсолютное позиционирование

        // Метка для отображения счета
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setBounds(150, 10, 200, 30); // Центрируем по ширине
        clickerPanel.add(scoreLabel);

        // Метка для отображения скорости автоматических кликов
        autoClickLabel = new JLabel("+0/s");
        autoClickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autoClickLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        autoClickLabel.setForeground(Color.CYAN);
        autoClickLabel.setBounds(150, 40, 200, 30); // Центрируем по ширине
        clickerPanel.add(autoClickLabel);

        // Метка для отображения состояния батареи
        batteryLabel = new JLabel("Battery: 100/100");
        batteryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        batteryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        batteryLabel.setForeground(Color.GREEN);
        batteryLabel.setBounds(150, 70, 200, 30); // Центрируем по ширине
        clickerPanel.add(batteryLabel);

        // Загрузка изображения печенья с использованием ImageIO
        try {
            URL imageUrl = getClass().getResource("/cookie.png");
            if (imageUrl != null) {
                originalCookieImage = ImageIO.read(imageUrl);
            } else {
                System.out.println("Ошибка: изображение печенья не загружено.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке изображения печенья: " + e.getMessage());
            return;
        }

        // Проверяем, что изображение загружено правильно
        if (originalCookieImage == null) {
            System.out.println("Ошибка: изображение печенья не удалось загрузить.");
            return;
        }

        // Масштабируем изображение до нужного размера
        Image scaledImage = originalCookieImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        cookieLabel = new JLabel(new ImageIcon(scaledImage));
        cookieLabel.setBounds(200, 150, 100, 100); // Позиционирование по центру панели
        originalY = cookieLabel.getY();
        clickerPanel.add(cookieLabel); // Добавляем метку на панель кликов

        // Добавляем обработчик событий для клика по изображению
        cookieLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (playerClickManager.canClick()) { // Проверяем, может ли игрок кликнуть
                    playerClickManager.useClick();
                    score += playerClickManager.getClickValue(); // Добавляем очки согласно текущему улучшению кликов
                    updateScoreDisplay();
                    showPlusOneAnimation(); // Показываем анимацию "+1"
                    startAnimation();
                } else {
                    JOptionPane.showMessageDialog(frame, "Батарея разряжена! Подождите восстановления.");
                }
            }
        });

        // Инициализация логики автоматических кликов
        clicker = new Clicker(this);

        // Инициализация логики кликов игрока и батареи
        playerClickManager = new PlayerClickManager(this);

        // Инициализация списка активных меток "+1"
        activePlusOneLabels = new ArrayList<>();
    }

    private void createUpgradePanel() {
        upgradePanel = new JPanel();
        upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.Y_AXIS));

        JButton idleClickUpgradeButton = new JButton("Купить Idle Clicking (+1/s) за 10 очков");
        idleClickUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        idleClickUpgradeButton.addActionListener(e -> {
            if (getScore() >= 10) {
                setScore(getScore() - 10);
                getClicker().increaseAutoClicks();
            } else {
                JOptionPane.showMessageDialog(frame, "Недостаточно очков!");
            }
        });

        upgradePanel.add(idleClickUpgradeButton);
    }

    private void createBoostPanel() {
        boostPanel = new JPanel();
        boostPanel.setLayout(new BoxLayout(boostPanel, BoxLayout.Y_AXIS));

        JButton batteryUpgradeButton = new JButton("Улучшить батарею (+100 к батарее) за 20 очков");
        batteryUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        batteryUpgradeButton.addActionListener(e -> {
            if (getScore() >= 20) {
                setScore(getScore() - 20);
                getPlayerClickManager().increaseMaxBattery(100);
            } else {
                JOptionPane.showMessageDialog(frame, "Недостаточно очков!");
            }
        });

        JButton clickValueUpgradeButton = new JButton("Улучшить клик (+1 к клику) за 100 кликов");
        clickValueUpgradeButton.setFont(new Font("Arial", Font.BOLD, 14));
        clickValueUpgradeButton.addActionListener(e -> {
            if (getScore() >= 100) {
                setScore(getScore() - 100);
                getPlayerClickManager().increaseClickValue();
            } else {
                JOptionPane.showMessageDialog(frame, "Недостаточно кликов!");
            }
        });

        boostPanel.add(batteryUpgradeButton);
        boostPanel.add(clickValueUpgradeButton);
    }

    // Методы для обновления отображения
    public void updateScoreDisplay() {
        scoreLabel.setText("Score: " + score);
    }

    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        batteryLabel.setText("Battery: " + currentBattery + "/" + maxBattery);
    }

    public void updateAutoClickDisplay(int autoClicksPerSecond) {
        autoClickLabel.setText("+" + autoClicksPerSecond + "/s");
    }

    private void showPlusOneAnimation() {
        if (activePlusOneLabels.size() >= MAX_PLUS_ONE_LABELS) {
            return; // Если меток "+1" уже слишком много, не создаем новые
        }

        int frameWidth = clickerPanel.getWidth();
        int frameHeight = clickerPanel.getHeight();

        // Генерируем случайные координаты для метки "+1"
        int randomX = random.nextInt(frameWidth - 50);
        int randomY = random.nextInt(frameHeight - 50);

        JLabel plusOneLabel = new JLabel("+" + playerClickManager.getClickValue()); // Отображение текущего значения клика
        plusOneLabel.setForeground(Color.RED);
        plusOneLabel.setFont(new Font("Arial", Font.BOLD, 18));
        plusOneLabel.setBounds(randomX, randomY, 50, 30);
        clickerPanel.add(plusOneLabel);
        activePlusOneLabels.add(plusOneLabel); // Добавляем метку в список активных

        Timer plusOneTimer = new Timer(50, new ActionListener() {
            int steps = 0;
            int startY = randomY;

            @Override
            public void actionPerformed(ActionEvent e) {
                steps++;
                plusOneLabel.setLocation(randomX, startY - steps * 2);
                if (steps > 20) {
                    ((Timer) e.getSource()).stop();
                    clickerPanel.remove(plusOneLabel);
                    activePlusOneLabels.remove(plusOneLabel); // Удаляем метку из списка активных
                    clickerPanel.repaint();
                }
            }
        });
        plusOneTimer.setRepeats(true); // Обеспечиваем повторение
        plusOneTimer.start();
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            return; // Не запускаем анимацию, если таймер уже работает
        }

        animationStep = 0;
        int animationRange = 20;

        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int newY = originalY + (int) (Math.sin(animationStep * 0.2) * animationRange);

                cookieLabel.setLocation(cookieLabel.getX(), newY);
                clickerPanel.repaint();

                if (animationStep >= 30) {
                    animationTimer.stop();
                    cookieLabel.setLocation(cookieLabel.getX(), originalY);
                    clickerPanel.repaint();
                }
            }
        });
        animationTimer.start();
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

    public PlayerClickManager getPlayerClickManager() {
        return playerClickManager;
    }
}
