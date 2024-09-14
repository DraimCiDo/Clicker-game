package me.draimgoose.ui;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import me.draimgoose.config.GameConfig;
import me.draimgoose.managers.BackgroundManager;
import me.draimgoose.managers.GameState;
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
    private ProgressBar batteryProgressBar;
    private Label rechargeLabel;
    private Timeline rechargeTimeline;
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
        GameState gameState = GameState.getInstance();

        // Основной UI Pane
        uiPane = new BorderPane();
        uiPane.getStyleClass().add("main-background"); // Применяем класс для фона

        // Верхняя панель с информацией
        HBox topPanel = new HBox(20);  // Горизонтальный контейнер для размещения информации
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 5;");  // Темный фон

        // Создание блоков информации с закругленными краями
        scoreLabel = createRoundedLabel("Score: " + gameState.getScore());
        autoClickLabel = createRoundedLabel("+" + gameState.getAutoClicks() + "/s");
        batteryLabel = createRoundedLabel("Battery: " + gameState.getBattery() + "/" + gameState.getMaxBattery());

        // Создание ProgressBar для батареи
        batteryProgressBar = new ProgressBar();
        batteryProgressBar.setPrefWidth(150);
        batteryProgressBar.setProgress((double) gameState.getBattery() / gameState.getMaxBattery());

        // Создание Label для отображения времени перезарядки
        rechargeLabel = new Label("");
        rechargeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        // Добавляем все элементы в topPanel
        topPanel.getChildren().addAll(scoreLabel, autoClickLabel, batteryLabel, batteryProgressBar, rechargeLabel);
        uiPane.setTop(topPanel);

        // Основная панель
        centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg"); // Фон для панели "Кликер"
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

        // Слушатель закрытия приложения добавлен в Main.java, поэтому здесь ничего не делаем
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
        GameState gameState = GameState.getInstance();
        if (gameState.getBattery() <= 0) {
            notificationManager.showNotification("Батарея разряжена! Перезаряжается.", false);
            soundManager.playErrorSound();  // Звук ошибки
            rechargeBattery();  // Начинаем процесс перезарядки
            return;
        }

        // Применение множителя при клике
        int pointsEarned = (int) (1 * gameState.getUpgradeMultiplier());
        gameState.setScore(gameState.getScore() + pointsEarned);
        gameState.setBattery(gameState.getBattery() - 1);
        updateUI();

        soundManager.playClickSound();  // Звук клика

        if (GameConfig.areAnimationsEnabled()) {
            animateCookie(cookieImageView);
            showPlusOneAnimation(pointsEarned);  // Передаём количество очков
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
        GameState gameState = GameState.getInstance();
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + gameState.getScore());
            batteryLabel.setText("Battery: " + gameState.getBattery() + "/" + gameState.getMaxBattery());
            autoClickLabel.setText("+" + gameState.getAutoClicks() + "/s");
            // Обновляем ProgressBar
            double progress = (double) gameState.getBattery() / gameState.getMaxBattery();
            batteryProgressBar.setProgress(progress);
        });
    }

    private void showPlusOneAnimation(int pointsEarned) {
        Label plusLabel = new Label("+" + pointsEarned);
        plusLabel.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Устанавливаем случайные координаты для появления "+X"
        double randomX = random.nextDouble() * (centerPane.getWidth() - 100) - (centerPane.getWidth() / 2 - 50);
        double randomY = random.nextDouble() * (centerPane.getHeight() - 100) - (centerPane.getHeight() / 2 - 50);

        plusLabel.setTranslateX(randomX);
        plusLabel.setTranslateY(randomY);

        centerPane.getChildren().add(plusLabel);

        // Создаем анимацию перемещения вверх и исчезновения
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), plusLabel);
        translateTransition.setByY(-50);  // Перемещение вверх на 50 пикселей

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), plusLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        // Запускаем обе анимации параллельно
        translateTransition.play();
        fadeTransition.play();

        // Удаляем Label после завершения анимации
        fadeTransition.setOnFinished(event -> centerPane.getChildren().remove(plusLabel));
    }

    private void rechargeBattery() {
        GameState gameState = GameState.getInstance();
        gameState.setBattery(0);
        gameState.saveState(); // Сохранение состояния до перезарядки

        soundManager.playRechargeSound();  // Звук начала перезарядки
        notificationManager.showNotification("Батарея разряжена! Перезаряжается.", false);

        // Отображение метки перезарядки
        rechargeLabel.setText("Перезарядка: 5 секунд");

        // Инициализация Timeline для обновления ProgressBar и Label
        rechargeTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(batteryProgressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(5), new KeyValue(batteryProgressBar.progressProperty(), 1))
        );

        // Обновление Label каждую секунду
        rechargeTimeline.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            int remainingSeconds = 5 - (int) newTime.toSeconds();
            if (remainingSeconds > 0) {
                rechargeLabel.setText("Перезарядка: " + remainingSeconds + " секунд");
            } else {
                rechargeLabel.setText("Батарея полностью заряжена!");
            }
        });

        rechargeTimeline.setOnFinished(event -> {
            gameState.setBattery(gameState.getMaxBattery());
            gameState.saveState(); // Сохранение состояния после перезарядки
            rechargeLabel.setText("Батарея полностью заряжена!");
            notificationManager.showNotification("Батарея полностью заряжена!", true);
            soundManager.playRechargeSound();  // Звук завершения перезарядки
        });

        rechargeTimeline.play();
    }

    public int getScore() {
        return GameState.getInstance().getScore();
    }

    public int getCurrentBattery() {
        return GameState.getInstance().getBattery();
    }

    public int getMaxBattery() {
        return GameState.getInstance().getMaxBattery();
    }

    public int getAutoClicks() {
        return GameState.getInstance().getAutoClicks();
    }

    public void updateScore(int amount) {
        GameState gameState = GameState.getInstance();
        gameState.setScore(gameState.getScore() + amount);
        updateUI();
    }

    public void updateAutoClickDisplay(int autoClicks) {
        GameState gameState = GameState.getInstance();
        gameState.setAutoClicks(autoClicks);
        updateUI();
    }

    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        GameState gameState = GameState.getInstance();
        gameState.setBattery(currentBattery);
        gameState.setMaxBattery(maxBattery);
        updateUI();
    }

    // Запуск авто-кликера
    private void startAutoClicker() {
        autoClickTimer = new Timer();
        autoClickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    GameState gameState = GameState.getInstance();
                    if (gameState.getAutoClicks() > 0) {
                        int autoPoints = gameState.getAutoClicks(); // Без применения множителя
                        gameState.setScore(gameState.getScore() + autoPoints);
                        updateUI();
                        soundManager.playClickSound();  // Звук авто-клика
                        showPlusOneAnimation(autoPoints);  // Отображение анимации
                    }
                });
            }
        }, 0, 1000);  // Авто-клик каждую секунду
    }

    public StackPane getMainPane() { // StackPane для наложения уведомлений
        return mainPane;
    }
}
