package me.draimgoose;

import me.draimgoose.animations.CookieAnimation;
import me.draimgoose.animations.PlusOneAnimation;
import me.draimgoose.panels.BoostPanel;
import me.draimgoose.panels.ClickerPanel;
import me.draimgoose.panels.UpgradePanel;

import javax.swing.*;
import java.awt.*;

public class GamePanel {
    private JFrame frame;
    private JPanel mainPanel; // Основная панель для CardLayout
    private ClickerPanel clickerPanel;
    private UpgradePanel upgradePanel;
    private BoostPanel boostPanel;
    private JPanel bottomPanel; // Нижняя панель с кнопками
    private Clicker clicker; // Логика автоматических кликов
    private PlayerClickManager playerClickManager; // Логика кликов игрока и батареи
    private int score; // Переменная для хранения текущего счета игрока

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

        // Создаем панели
        clickerPanel = new ClickerPanel(this);
        upgradePanel = new UpgradePanel(this);
        boostPanel = new BoostPanel(this);

        // Добавляем панели в mainPanel
        mainPanel.add(clickerPanel.getPanel(), "ClickerPanel");
        mainPanel.add(upgradePanel.getPanel(), "UpgradePanel");
        mainPanel.add(boostPanel.getPanel(), "BoostPanel");

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

        // Инициализация логики
        clicker = new Clicker(this);
        playerClickManager = new PlayerClickManager(this);

        score = 0; // Начальный счет
    }

    private void createBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3)); // Используем GridLayout для равномерного размещения кнопок
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
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Увеличен шрифт для лучшей читаемости
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

    // Добавляем методы getScore() и setScore()
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        clickerPanel.updateScoreDisplay(); // Обновляем отображение счета на панели кликов
    }

    public void updateAutoClickDisplay(int autoClicks) {
        clickerPanel.updateAutoClickDisplay(autoClicks); // Обновляем отображение автокликов на панели кликов
    }

    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        clickerPanel.updateBatteryDisplay(currentBattery, maxBattery); // Обновляем отображение батареи на панели кликов
    }

    public Clicker getClicker() {
        return clicker;
    }

    public PlayerClickManager getPlayerClickManager() {
        return playerClickManager;
    }
}
