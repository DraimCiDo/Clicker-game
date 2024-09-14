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
import javafx.scene.paint.Color;
import javafx.util.Duration;
import me.draimgoose.config.GameConfig;
import me.draimgoose.managers.BackgroundManager;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;
import me.draimgoose.panels.BoostPanel;
import me.draimgoose.panels.UpgradePanel;
import me.draimgoose.utils.ButtonFactory;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ClickerGameUI {

    private StackPane mainPane; // StackPane для наложения уведомлений
    private BorderPane uiPane; // Основной UI
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
    private SoundManager soundManager;  // Объект для управления звуками
    private Timer autoClickTimer;
    private Random random = new Random();

    public ClickerGameUI() {
        mainPane = new StackPane(); // StackPane для наложения уведомлений
        notificationManager = new NotificationManager();
        soundManager = new SoundManager();  // Инициализация SoundManager
        initializeUI();
        startAutoClicker();  // Запускаем авто-кликер при создании UI
    }

    private void initializeUI() {
        score = 0;

        // Основной UI Pane
        uiPane = new BorderPane();
        uiPane.getStyleClass().add("main-background"); // Применяем класс для фона

        // Верхняя панель с информацией
        HBox topPanel = new HBox(20);  // Горизонтальный контейнер для размещения информации
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 5;");  // Задний фон и уменьшенные отступы

        // Создание блоков информации с закругленными краями
        scoreLabel = createRoundedLabel("Score: 0");
        autoClickLabel = createRoundedLabel("+0/s");
        batteryLabel = createRoundedLabel("Battery: 100/100");

        topPanel.getChildren().addAll(scoreLabel, autoClickLabel, batteryLabel);
        uiPane.setTop(topPanel);

        // Основная панель
        centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg");  // Фон для панели "Кликер"
        uiPane.setCenter(centerPane);

        showClicker();  // По умолчанию открывается кликер

        // Нижняя панель с кнопками
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setSpacing(5);
        bottomPanel.getStyleClass().add("bottom-panel"); // Применяем класс для нижней панели

        // Кнопки нижней панели
        Button upgradeButton = ButtonFactory.createStyledButton("Улучшения");
        upgradeButton.getStyleClass().add("styled-button");
        upgradeButton.setOnAction(event -> showUpgradeMenu());

        Button clickerButton = ButtonFactory.createStyledButton("Кликер");
        clickerButton.getStyleClass().add("styled-button");
        clickerButton.setOnAction(event -> showClicker());

        Button boostsButton = ButtonFactory.createStyledButton("Бусты");
        boostsButton.getStyleClass().add("styled-button");
        boostsButton.setOnAction(event -> showBoostMenu());

        // Растягиваем кнопки по ширине
        HBox.setHgrow(upgradeButton, Priority.ALWAYS);
        HBox.setHgrow(clickerButton, Priority.ALWAYS);
        HBox.setHgrow(boostsButton, Priority.ALWAYS);

        bottomPanel.getChildren().addAll(upgradeButton, clickerButton, boostsButton);
        bottomPanel.setPrefHeight(50);
        uiPane.setBottom(bottomPanel);

        // Добавляем основной UI Pane в StackPane
        mainPane.getChildren().add(uiPane);

        // Добавляем VBox для уведомлений поверх основного UI
        VBox notificationBox = notificationManager.getNotificationBox();
        mainPane.getChildren().add(notificationBox);
        StackPane.setAlignment(notificationBox, Pos.TOP_RIGHT); // Выравнивание по верхнему правому углу
        StackPane.setMargin(notificationBox, new Insets(10, 10, 0, 0)); // Отступ от верхнего и правого края
    }

    // Метод для создания закругленного блока с текстом
    private Label createRoundedLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("rounded-label"); // Применяем класс из CSS
        return label;
    }

    private void showUpgradeMenu() {
        centerPane.getChildren().clear();  // Очищаем центральную панель
        UpgradePanel upgradePanel = new UpgradePanel(this, notificationManager, soundManager);
        centerPane.getChildren().addAll(upgradePanel.getPanel());
    }

    private void showClicker() {
        centerPane.getChildren().clear();  // Очищаем центральную панель
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg");

        // Используем изображение вместо кнопки
        ImageView cookieImageView = new ImageView(new Image("/cookie.png"));
        cookieImageView.setFitWidth(100);
        cookieImageView.setFitHeight(100);
        cookieImageView.setOnMouseClicked(event -> handleCookieClick(cookieImageView));  // Обработчик кликов

        centerPane.getChildren().add(cookieImageView);
    }

    private void showBoostMenu() {
        centerPane.getChildren().clear();  // Очищаем центральную панель
        BoostPanel boostPanel = new BoostPanel(this, notificationManager, soundManager);
        centerPane.getChildren().addAll(boostPanel.getPanel());
    }

    private void handleCookieClick(ImageView cookieImageView) {
        if (isRecharging) {
            notificationManager.showNotification("Батарея на перезарядке! Подождите.", false);
            soundManager.playErrorSound();  // Звук ошибки
            return;
        }

        if (battery > 0) {
            battery--;  // Уменьшаем батарею на 1 при каждом клике
            score += 1;
            updateUI();
            soundManager.playClickSound();  // Звук клика

            if (GameConfig.areAnimationsEnabled()) {
                animateCookie(cookieImageView);
                showPlusOneAnimation();
            }
        } else {
            notificationManager.showNotification("Батарея разряжена! Перезаряжается.", false);
            soundManager.playErrorSound();  // Звук ошибки
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

        // Устанавливаем случайные координаты для появления "+1"
        double randomX = random.nextDouble() * (centerPane.getWidth() - 100) - (centerPane.getWidth() / 2 - 50);
        double randomY = random.nextDouble() * (centerPane.getHeight() - 100) - (centerPane.getHeight() / 2 - 50);

        plusOne.setTranslateX(randomX);
        plusOne.setTranslateY(randomY);

        centerPane.getChildren().add(plusOne);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), plusOne);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> centerPane.getChildren().remove(plusOne));
        fadeTransition.play();
    }

    private void rechargeBattery() {
        isRecharging = true;
        soundManager.playRechargeSound();  // Звук начала перезарядки
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    battery = maxBattery;
                    isRecharging = false;
                    updateUI();
                    notificationManager.showNotification("Батарея полностью заряжена!", true);
                    soundManager.playRechargeSound();  // Звук завершения перезарядки
                });
            }
        }, 5000);  // Перезарядка длится 5 секунд
    }

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

    private void startAutoClicker() {
        autoClickTimer = new Timer();
        autoClickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (autoClicks > 0) {
                        score += autoClicks;
                        updateUI();
                        soundManager.playClickSound();  // Звук авто-клика
                    }
                });
            }
        }, 0, 1000);  // Запускаем каждую секунду
    }

    public StackPane getMainPane() { // StackPane для наложения уведомлений
        return mainPane;
    }
}
