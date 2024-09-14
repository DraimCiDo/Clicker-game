package me.draimgoose;

import me.draimgoose.panels.BoostPanel;
import me.draimgoose.panels.ClickerPanel;
import me.draimgoose.panels.SettingsPanel;
import me.draimgoose.panels.UpgradePanel;

import javax.swing.*;
import java.awt.*;

public class GamePanel {
    private JFrame frame;
    private JPanel mainPanel; // Основная панель для CardLayout
    private ClickerPanel clickerPanel;
    private UpgradePanel upgradePanel;
    private BoostPanel boostPanel;
    private SettingsPanel settingsPanel; // Добавляем панель настроек
    private JPanel bottomPanel;
    private Clicker clicker;
    private PlayerClickManager playerClickManager;
    private int score;  // Переменная для хранения текущего счета игрока

    public GamePanel() {
        frame = new JFrame("Clicker Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new CardLayout());

        clickerPanel = new ClickerPanel(this);
        upgradePanel = new UpgradePanel(this);
        boostPanel = new BoostPanel(this);
        settingsPanel = new SettingsPanel(); // Инициализируем панель настроек

        mainPanel.add(clickerPanel.getPanel(), "ClickerPanel");
        mainPanel.add(upgradePanel.getPanel(), "UpgradePanel");
        mainPanel.add(boostPanel.getPanel(), "BoostPanel");
        mainPanel.add(settingsPanel.getPanel(), "SettingsPanel"); // Добавляем панель настроек

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "ClickerPanel");

        frame.add(mainPanel, BorderLayout.CENTER);

        createBottomPanel();

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        clicker = new Clicker(this);
        playerClickManager = new PlayerClickManager(this);

        score = 0; // Инициализируем счет
    }

    private void createBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 4));
        bottomPanel.setBackground(new Color(60, 63, 65));

        JButton clickerButton = createStyledButton("Кликер");
        clickerButton.addActionListener(e -> switchPanel("ClickerPanel"));
        bottomPanel.add(clickerButton);

        JButton upgradeButton = createStyledButton("Улучшения");
        upgradeButton.addActionListener(e -> switchPanel("UpgradePanel"));
        bottomPanel.add(upgradeButton);

        JButton boostButton = createStyledButton("Бусты");
        boostButton.addActionListener(e -> switchPanel("BoostPanel"));
        bottomPanel.add(boostButton);

        JButton settingsButton = createStyledButton("Настройки");
        settingsButton.addActionListener(e -> switchPanel("SettingsPanel")); // Добавляем кнопку для переключения на панель настроек
        bottomPanel.add(settingsButton);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, panelName);
    }

    // Добавляем методы getScore() и setScore() для управления счетом
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        clickerPanel.updateScoreDisplay(); // Обновляем отображение счета на панели кликов
    }

    // Метод для обновления отображения автоматических кликов
    public void updateAutoClickDisplay(int autoClicks) {
        clickerPanel.updateAutoClickDisplay(autoClicks); // Обновляем отображение на панели кликов
    }

    // Добавляем метод для обновления отображения состояния батареи
    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        clickerPanel.updateBatteryDisplay(currentBattery, maxBattery); // Обновляем отображение на панели кликов
    }

    public Clicker getClicker() {
        return clicker;
    }

    public PlayerClickManager getPlayerClickManager() {
        return playerClickManager;
    }
}
