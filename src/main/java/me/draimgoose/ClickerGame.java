package me.draimgoose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.Random;

public class ClickerGame {
    private JFrame frame;
    private JPanel panel;
    private JLabel cookieLabel;
    private JLabel scoreLabel;
    private JLabel autoClickLabel; // Метка для отображения скорости автоматических кликов
    private int score;
    private Timer animationTimer;
    private int animationStep = 0;
    private int originalY;
    private Image originalCookieImage; // Исходное изображение печенья
    private Random random = new Random(); // Для генерации случайных чисел

    // Idle Clicker переменные
    private int autoClickLevel = 0; // Уровень автоматических кликов
    private int autoClicksPerSecond = 0; // Количество кликов в секунду
    private Timer idleClickTimer; // Таймер для автоматических кликов

    public ClickerGame() {
        // Настройка окна
        frame = new JFrame("Clicker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setLocationRelativeTo(null); // Центрируем окно на экране

        // Настройка панели
        panel = new JPanel();
        panel.setLayout(null); // Используем абсолютное позиционирование
        frame.add(panel);

        // Метка для отображения счета
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // Выравнивание по центру
        scoreLabel.setBounds(frame.getWidth() / 2 - 50, 10, 100, 30); // Размещаем по центру
        panel.add(scoreLabel);

        // Метка для отображения скорости автоматических кликов
        autoClickLabel = new JLabel("+0/s");
        autoClickLabel.setHorizontalAlignment(SwingConstants.CENTER);
        autoClickLabel.setBounds(frame.getWidth() / 2 - 50, 40, 100, 30); // Размещаем под счетом
        panel.add(autoClickLabel);

        // Загрузка исходного изображения печенья
        URL imageUrl = getClass().getResource("/cookie.png");
        if (imageUrl == null) {
            System.out.println("Ошибка: изображение не загружено.");
            return; // Прекращаем выполнение, если изображение не загружено
        }

        originalCookieImage = new ImageIcon(imageUrl).getImage(); // Сохраняем исходное изображение

        // Инициализируем метку с изображением
        cookieLabel = new JLabel(new ImageIcon(originalCookieImage));
        cookieLabel.setBounds(150, 150, 100, 100); // Устанавливаем начальные размеры метки
        originalY = cookieLabel.getY(); // Сохраняем исходное положение по оси Y
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

        // Кнопка для открытия меню улучшений
        JButton upgradeButton = new JButton("Улучшения");
        upgradeButton.setBounds(10, frame.getHeight() - 70, 100, 30);
        upgradeButton.addActionListener(e -> openUpgradeMenu());
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

        // Инициализация таймера для Idle Clicker
        idleClickTimer = new Timer(1000, e -> idleClick());
    }

    // Метод для адаптации компонентов и масштабирования изображения под размер окна
    private void adaptComponentsToWindowSize() {
        int frameWidth = frame.getContentPane().getWidth();
        int frameHeight = frame.getContentPane().getHeight();

        // Определяем новые размеры для печенья (50% от ширины и 50% от высоты окна)
        int newWidth = (int) (frameWidth * 0.5);
        int newHeight = (int) (frameHeight * 0.5);

        // Масштабируем изображение печенья
        Image scaledImage = originalCookieImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        cookieLabel.setIcon(new ImageIcon(scaledImage));

        // Центрируем метку печенья
        int newX = (frameWidth - newWidth) / 2;
        int newY = (frameHeight - newHeight) / 2;
        cookieLabel.setBounds(newX, newY, newWidth, newHeight);
        originalY = newY; // Обновляем исходное положение по оси Y

        // Размещаем метку для отображения счета в верхнем центре
        scoreLabel.setBounds(frameWidth / 2 - 50, 10, 100, 30);
        autoClickLabel.setBounds(frameWidth / 2 - 50, 40, 100, 30); // Обновляем расположение авто-кликов

        // Перерисовываем панель
        panel.repaint();
    }

    private void startAnimation() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop(); // Останавливаем текущую анимацию
        }

        animationStep = 0; // Сброс шага анимации
        int animationRange = 20; // Амплитуда анимации

        // Создаем таймер для анимации
        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int newY = originalY + (int) (Math.sin(animationStep * 0.2) * animationRange); // Плавное движение вверх и вниз

                cookieLabel.setLocation(cookieLabel.getX(), newY);
                panel.repaint(); // Перерисовка панели после изменения

                if (animationStep >= 30) { // Завершить после определенного количества шагов
                    animationTimer.stop();
                    cookieLabel.setLocation(cookieLabel.getX(), originalY); // Возвращаемся в исходное положение
                    panel.repaint(); // Перерисовка панели после изменения
                }
            }
        });
        animationTimer.start(); // Запуск анимации
    }

    // Метод для отображения анимации "+1" в случайном месте
    private void showPlusOneAnimation() {
        int frameWidth = frame.getContentPane().getWidth();
        int frameHeight = frame.getContentPane().getHeight();

        // Генерируем случайные координаты для метки "+1"
        int randomX = random.nextInt(frameWidth - 50); // Учитываем ширину метки
        int randomY = random.nextInt(frameHeight - 50); // Учитываем высоту метки

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
                plusOneLabel.setLocation(randomX, startY - steps * 2); // Двигаемся вверх
                if (steps > 20) { // Через несколько шагов удаляем метку
                    ((Timer) e.getSource()).stop();
                    panel.remove(plusOneLabel);
                    panel.repaint();
                }
            }
        });
        plusOneTimer.start();
    }

    // Открытие меню улучшений
    private void openUpgradeMenu() {
        JFrame upgradeFrame = new JFrame("Улучшения");
        upgradeFrame.setSize(300, 200);
        upgradeFrame.setLayout(new FlowLayout());
        upgradeFrame.setLocationRelativeTo(frame);

        JButton idleClickUpgradeButton = new JButton("Купить Idle Clicking (+1/s) за 10 очков");
        idleClickUpgradeButton.addActionListener(e -> {
            if (score >= 10) {
                score -= 10;
                autoClickLevel++;
                autoClicksPerSecond = autoClickLevel; // Уровень улучшения влияет на клики в секунду
                updateScoreDisplay();
                updateAutoClickDisplay();
                idleClickTimer.start(); // Запуск таймера, если не запущен
                upgradeFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(upgradeFrame, "Недостаточно очков!");
            }
        });

        upgradeFrame.add(idleClickUpgradeButton);
        upgradeFrame.setVisible(true);
    }

    // Метод для обновления отображения счета
    private void updateScoreDisplay() {
        scoreLabel.setText("Score: " + score);
    }

    // Метод для обновления отображения автоматических кликов
    private void updateAutoClickDisplay() {
        autoClickLabel.setText("+" + autoClicksPerSecond + "/s");
    }

    // Метод для автоматического клика (Idle Clicker)
    private void idleClick() {
        score += autoClicksPerSecond;
        updateScoreDisplay();
    }

    public static void main(String[] args) {
        // Запуск игры
        SwingUtilities.invokeLater(() -> new ClickerGame());
    }
}
