package me.draimgoose.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import me.draimgoose.config.GameConfig;
import me.draimgoose.managers.BackgroundManager;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.panels.BoostPanel;
import me.draimgoose.panels.UpgradePanel;
import me.draimgoose.utils.ButtonFactory;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ClickerGameUI {

    private BorderPane mainPane;
    private StackPane centerPane;
    private Label scoreLabel;
    private Label autoClickLabel;
    private Label batteryLabel;
    private int score;
    private int autoClicks = 0;
    private int battery = 100;
    private int maxBattery = 100;
    private boolean isRecharging = false;
    private NotificationManager notificationManager;
    private Timer autoClickTimer;
    private Random random = new Random();

    public ClickerGameUI() {
        mainPane = new BorderPane();
        notificationManager = new NotificationManager();
        initializeUI();
        startAutoClicker();  // Запускаем авто-кликер при создании UI
    }

    private void initializeUI() {
        score = 0;

        // Верхняя панель с информацией
        VBox topPanel = new VBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        autoClickLabel = new Label("+0/s");
        autoClickLabel.setStyle("-fx-font-size: 14px;");

        batteryLabel = new Label("Battery: 100/100");
        batteryLabel.setStyle("-fx-font-size: 14px;");

        topPanel.getChildren().addAll(scoreLabel, autoClickLabel, batteryLabel);
        mainPane.setTop(topPanel);

        // Основная панель
        centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg");  // Фон для панели "Кликер"
        centerPane.getChildren().add(notificationManager.getNotificationLabel());

        showClicker();  // По умолчанию открывается кликер
        mainPane.setCenter(centerPane);

        // Нижняя панель с кнопками
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setSpacing(5);

        // Кнопки нижней панели
        Button upgradeButton = ButtonFactory.createStyledButton("Улучшения");
        upgradeButton.setOnAction(event -> showUpgradeMenu());

        Button clickerButton = ButtonFactory.createStyledButton("Кликер");
        clickerButton.setOnAction(event -> showClicker());

        Button boostsButton = ButtonFactory.createStyledButton("Бусты");
        boostsButton.setOnAction(event -> showBoostMenu());

        // Растягиваем кнопки по ширине
        HBox.setHgrow(upgradeButton, Priority.ALWAYS);
        HBox.setHgrow(clickerButton, Priority.ALWAYS);
        HBox.setHgrow(boostsButton, Priority.ALWAYS);

        bottomPanel.getChildren().addAll(upgradeButton, clickerButton, boostsButton);
        bottomPanel.setPrefHeight(50);
        bottomPanel.setStyle("-fx-background-color: #2a2a2a;");
        mainPane.setBottom(bottomPanel);
    }

    private void showUpgradeMenu() {
        centerPane.getChildren().clear();  // Очищаем центральную панель
        UpgradePanel upgradePanel = new UpgradePanel(this, notificationManager);
        centerPane.getChildren().addAll(upgradePanel.getPanel());
    }

    private void showClicker() {
        centerPane.getChildren().clear();  // Очищаем центральную панель
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg");

        Button cookieButton = ButtonFactory.createStyledButton("Печенька");
        cookieButton.setOnAction(event -> handleCookieClick());
        centerPane.getChildren().add(cookieButton);
    }

    private void showBoostMenu() {
        centerPane.getChildren().clear();  // Очищаем центральную панель
        BoostPanel boostPanel = new BoostPanel(this, notificationManager);
        centerPane.getChildren().addAll(boostPanel.getPanel());
    }

    private void handleCookieClick() {
        if (isRecharging) {
            notificationManager.showNotification("Батарея на перезарядке! Подождите.", false);
            return;
        }

        if (battery > 0) {
            battery--;  // Уменьшаем батарею на 1 при каждом клике
            score += 1;
            updateUI();
        } else {
            notificationManager.showNotification("Батарея разряжена! Перезаряжается.", false);
            rechargeBattery();  // Начинаем процесс перезарядки
        }
    }

    private void updateUI() {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            batteryLabel.setText("Battery: " + battery + "/" + maxBattery);
            autoClickLabel.setText("+" + autoClicks + "/s");
        });
    }

    private void rechargeBattery() {
        isRecharging = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    battery = maxBattery;
                    isRecharging = false;
                    updateUI();
                    notificationManager.showNotification("Батарея полностью заряжена!", true);
                });
            }
        }, 5000);  // Перезарядка длится 5 секунд
    }

    private void startAutoClicker() {
        autoClickTimer = new Timer();
        autoClickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (autoClicks > 0) {
                        score += autoClicks;
                        updateUI();
                    }
                });
            }
        }, 0, 1000);  // Запускаем каждую секунду
    }

    // Добавленные геттеры
    public int getScore() {
        return score;
    }

    public int getCurrentBattery() {
        return battery;
    }

    public int getMaxBattery() {
        return maxBattery;
    }

    public int getAutoClicks() {
        return autoClicks;
    }

    public void updateScore(int amount) {
        score += amount;
        updateUI();
    }

    public void updateAutoClickDisplay(int autoClicks) {
        this.autoClicks = autoClicks;
        autoClickLabel.setText("+" + autoClicks + "/s");
    }

    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        this.battery = currentBattery;
        this.maxBattery = maxBattery;
        batteryLabel.setText("Battery: " + currentBattery + "/" + maxBattery);
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
