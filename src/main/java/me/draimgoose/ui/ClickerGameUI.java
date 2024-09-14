package me.draimgoose.ui;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import me.draimgoose.PlayerClickManager;
import me.draimgoose.config.GameConfig;

import java.util.Timer;
import java.util.TimerTask;

public class ClickerGameUI {

    private BorderPane mainPane;
    private StackPane centerPane;
    private Label scoreLabel;
    private Label autoClickLabel;
    private Label batteryLabel;
    private Label notificationLabel; // Уведомления
    private int score;
    private int autoClicks = 0;
    private int battery = 100;
    private int maxBattery = 100;
    private boolean isRecharging = false;

    private Timer autoClickTimer;

    public ClickerGameUI() {
        mainPane = new BorderPane();
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

        // Панель уведомлений
        notificationLabel = new Label();
        notificationLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green;");
        StackPane.setAlignment(notificationLabel, Pos.TOP_CENTER);

        // Основная панель
        centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);

        // Загрузка фона
        ImageView backgroundImageView = new ImageView(new Image("/background.jpg"));
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.setFitWidth(500);
        backgroundImageView.setFitHeight(500);
        centerPane.getChildren().add(backgroundImageView);
        centerPane.getChildren().add(notificationLabel);  // Добавляем уведомления в центр

        // Добавление изображений и кнопок для разных страниц
        showClicker();  // По умолчанию открывается кликер

        mainPane.setCenter(centerPane);

        // Нижняя панель с кнопками
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setAlignment(Pos.CENTER);

        Button upgradeButton = new Button("Улучшения");
        upgradeButton.setOnAction(event -> showUpgradeMenu());

        Button clickerButton = new Button("Кликер");
        clickerButton.setOnAction(event -> showClicker());

        Button boostsButton = new Button("Бусты");
        boostsButton.setOnAction(event -> showBoostMenu());

        bottomPanel.getChildren().addAll(upgradeButton, clickerButton, boostsButton);
        bottomPanel.setPrefHeight(50);  // Высота нижней панели
        bottomPanel.setStyle("-fx-background-color: #f0f0f0;");  // Цвет нижней панели
        mainPane.setBottom(bottomPanel);
    }

    private void showUpgradeMenu() {
        centerPane.getChildren().clear();  // Очищаем центральную панель

        VBox upgradeBox = new VBox(15);
        upgradeBox.setAlignment(Pos.CENTER);

        Label upgradeLabel = new Label("Улучшения");
        upgradeLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button buyAutoClickButton = new Button("Купить авто-клик (+1/s) - 100 очков");
        buyAutoClickButton.setOnAction(event -> {
            int cost = 100;
            if (score >= cost) {
                autoClicks++;
                score -= cost;
                updateAutoClickDisplay(autoClicks);
                updateUI();
                showNotification("Авто-клик куплен!", true);
            } else {
                showNotification("Недостаточно очков!", false);
            }
        });

        upgradeBox.getChildren().addAll(upgradeLabel, buyAutoClickButton);
        centerPane.getChildren().add(upgradeBox);
    }

    private void showClicker() {
        centerPane.getChildren().clear();  // Очищаем центральную панель

        ImageView cookieImageView = new ImageView(new Image("/cookie.png"));
        cookieImageView.setFitWidth(100);
        cookieImageView.setFitHeight(100);
        cookieImageView.setOnMouseClicked(event -> handleCookieClick(cookieImageView));

        centerPane.getChildren().add(cookieImageView);
    }

    private void showBoostMenu() {
        centerPane.getChildren().clear();  // Очищаем центральную панель

        VBox boostBox = new VBox(15);
        boostBox.setAlignment(Pos.CENTER);

        Label boostLabel = new Label("Бусты");
        boostLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button buyClickBoostButton = new Button("Улучшить клики (x2) - 200 очков");
        buyClickBoostButton.setOnAction(event -> {
            int cost = 200;
            if (score >= cost) {
                // Логика улучшения кликов
                score -= cost;
                updateUI();
                showNotification("Улучшение кликов куплено!", true);
            } else {
                showNotification("Недостаточно очков!", false);
            }
        });

        Button buyBatteryBoostButton = new Button("Увеличить батарею (+50) - 150 очков");
        buyBatteryBoostButton.setOnAction(event -> {
            int cost = 150;
            if (score >= cost) {
                maxBattery += 50;
                battery = maxBattery;
                score -= cost;
                updateBatteryDisplay(battery, maxBattery);
                updateUI();
                showNotification("Увеличение батареи куплено!", true);
            } else {
                showNotification("Недостаточно очков!", false);
            }
        });

        boostBox.getChildren().addAll(boostLabel, buyClickBoostButton, buyBatteryBoostButton);
        centerPane.getChildren().add(boostBox);
    }

    private void handleCookieClick(ImageView cookieImageView) {
        if (isRecharging) {
            showNotification("Батарея на перезарядке! Подождите.", false);
            return;
        }

        if (battery > 0) {
            battery--;  // Уменьшаем батарею на 1 при каждом клике
            score += 1;
            updateUI();

            if (GameConfig.areAnimationsEnabled()) {
                animateCookie(cookieImageView);
                showPlusOneAnimation();
            }
        } else {
            showNotification("Батарея разряжена! Перезаряжается.", false);
            rechargeBattery();  // Начинаем процесс перезарядки
        }
    }

    private void animateCookie(ImageView cookieImageView) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), cookieImageView);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }

    private void updateUI() {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            batteryLabel.setText("Battery: " + battery + "/" + maxBattery);
            autoClickLabel.setText("+" + autoClicks + "/s");
        });
    }

    private void showPlusOneAnimation() {
        Label plusOne = new Label("+1");
        plusOne.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");
        StackPane center = centerPane;  // Используем StackPane
        center.getChildren().add(plusOne);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), plusOne);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> center.getChildren().remove(plusOne));
        fadeTransition.play();
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
                    showNotification("Батарея полностью заряжена!", true);
                });
            }
        }, 5000);  // Перезарядка длится 5 секунд
    }

    private void showNotification(String message, boolean isSuccess) {
        notificationLabel.setText(message);
        notificationLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: " + (isSuccess ? "green" : "red") + ";");
        notificationLabel.setOpacity(1.0);  // Устанавливаем начальную непрозрачность

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), notificationLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> notificationLabel.setText(""));
        fadeTransition.play();
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

    public BorderPane getMainPane() {
        return mainPane;
    }
}
