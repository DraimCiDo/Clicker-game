# Clicker Game

[![Java CI with Gradle](https://github.com/DraimCiDo/Clicker-game/actions/workflows/gradle.yml/badge.svg)](https://github.com/DraimCiDo/Clicker-game/actions/workflows/gradle.yml)

Добро пожаловать в **Clicker Game** — простое, но увлекательное приложение на базе JavaFX, которое позволяет игрокам зарабатывать очки путем кликов и автоматизировать этот процесс с помощью улучшений и бустов. В этом файле ReadMe представлен полный обзор проекта, включая его функции, инструкции по установке и подробное объяснение механик с примерами кода.

---

## Содержание

1. [Обзор проекта](#обзор-проекта)
2. [Особенности](#особенности)
3. [Установка](#установка)
4. [Использование](#использование)
5. [Структура проекта](#структура-проекта)
6. [Подробные механики](#подробные-механики)
    - [Главный класс](#главный-класс)
    - [Настройка интерфейса пользователя](#настройка-интерфейса-пользователя)
    - [Уведомления](#уведомления)
    - [Управление звуком](#управление-звуком)
    - [Авто-кликер](#авто-кликер)
    - [Улучшения и Бусты](#улучшения-и-бусты)
7. [Ресурсы](#ресурсы)
8. [Вклад в проект](#вклад-в-проект)
9. [Лицензия](#лицензия)

---

## Обзор проекта

**Clicker Game** — это приложение на JavaFX, созданное для предоставления захватывающего опыта клика. Игроки накапливают очки, кликая по объекту для клика (например, печенье) и могут улучшать свои возможности зарабатывания очков с помощью различных улучшений и бустов. Игра оснащена интуитивно понятным интерфейсом, анимированными уведомлениями и звуковыми эффектами, что делает взаимодействие с пользователем более увлекательным.

---

## Особенности

- **Интерактивный кликер:** Кликните по основному объекту, чтобы заработать очки.
- **Авто-кликер:** Покупайте улучшения для автоматического заработка очков со временем.
- **Бусты:** Улучшайте свои возможности клика или увеличивайте емкость батареи с помощью бустов.
- **Анимированные уведомления:** Получайте обратную связь через стильные всплывающие уведомления.
- **Звуковые эффекты:** Наслаждайтесь аудиофидбеком при кликах, покупках и ошибках.
- **Отзывчивый интерфейс:** Бесшовный пользовательский опыт с чистым и интуитивно понятным интерфейсом.
- **Сохранение состояния (Будущее улучшение):** Сохранение и загрузка прогресса игры.

---

## Установка

### Требования

- **Java Development Kit (JDK):** Убедитесь, что на вашем компьютере установлена JDK версии 11 или выше. Вы можете скачать её с [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) или [OpenJDK](https://jdk.java.net/).
- **Gradle:** Проект использует Gradle для автоматизации сборки. Если он не установлен, вы можете скачать его с [Gradle](https://gradle.org/install/).
- **Библиотеки JavaFX:** Проект использует модули JavaFX. Убедитесь, что они правильно настроены в вашей сборке.

### Шаги

1. **Клонирование репозитория:**

   ```bash
   git clone https://github.com/your-username/clicker-game.git
   cd clicker-game
   ```

2. **Проверка файлов ресурсов:**

   Убедитесь, что все необходимые ресурсы (иконки, звуки, изображения) находятся в директории `src/main/resources/`.

3. **Сборка проекта:**

   ```bash
   ./gradlew build
   ```

4. **Запуск приложения:**

   ```bash
   ./gradlew run
   ```

---

## Использование

При запуске приложения вас встретит главный экран, отображающий текущий счет, скорость авто-клика и статус батареи. Используйте кнопки внизу для навигации между разделами "Кликер", "Улучшения" и "Бусты".

- **Кликер:** Кликните по основному объекту, чтобы заработать очки.
- **Улучшения:** Покупайте авто-кликеры для автоматического заработка очков.
- **Бусты:** Улучшайте свою силу клика или увеличивайте емкость батареи.

Получайте уведомления в реальном времени о ваших действиях и наслаждайтесь звуковыми эффектами для более захватывающего опыта.

---

## Структура проекта

```
clicker-game/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── me/
│   │   │       └── draimgoose/
│   │   │           ├── Main.java
│   │   │           ├── ui/
│   │   │           │   └── ClickerGameUI.java
│   │   │           ├── managers/
│   │   │           │   ├── NotificationManager.java
│   │   │           │   └── SoundManager.java
│   │   │           ├── panels/
│   │   │           │   ├── BoostPanel.java
│   │   │           │   └── UpgradePanel.java
│   │   │           └── utils/
│   │   │               └── ButtonFactory.java
│   │   └── resources/
│   │       ├── styles.css
│   │       ├── background.jpg
│   │       ├── icons/
│   │       │   ├── checkmark.png
│   │       │   └── exclamation.png
│   │       └── sounds/
│   │           ├── click.wav
│   │           ├── purchase.wav
│   │           ├── error.wav
│   │           └── recharge.wav
├── build.gradle
└── README.md
```

---

## Подробные механики

В этом разделе рассматриваются основные механики **Clicker Game**, объясняя, как каждый компонент функционирует и взаимодействует внутри приложения.

### Главный класс

Класс `Main.java` служит точкой входа в приложение. Он инициализирует первичную сцену и настраивает сцену с основным интерфейсом пользователя.

```java
package me.draimgoose;

import javafx.application.Application;
import javafx.scene.Scene;
import me.draimgoose.ui.ClickerGameUI;

public class Main extends Application {
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        ClickerGameUI gameUI = new ClickerGameUI();
        Scene scene = new Scene(gameUI.getMainPane(), 800, 600); // Размер окна

        // Применение CSS-стилей
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Clicker Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

**Объяснение:**

- **Инициализация:** Создает экземпляр `ClickerGameUI`, который настраивает пользовательский интерфейс.
- **Настройка сцены:** Определяет размер окна приложения и применяет внешний CSS для стилизации.
- **Запуск приложения:** Отображает первичную сцену с настроенным интерфейсом.

### Настройка интерфейса пользователя

Класс `ClickerGameUI.java` отвечает за построение пользовательского интерфейса, включая основное расположение, кнопки, метки и интеграцию системы уведомлений.

```java
package me.draimgoose.ui;

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

    private StackPane mainPane; // Основной контейнер с наложением
    private BorderPane uiPane; // Основное расположение UI
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
    private SoundManager soundManager;  // Управление звуками
    private Timer autoClickTimer;
    private Random random = new Random();

    public ClickerGameUI() {
        mainPane = new StackPane(); // StackPane для наложения уведомлений
        notificationManager = new NotificationManager();
        soundManager = new SoundManager();  // Инициализация SoundManager
        initializeUI();
        startAutoClicker();  // Запуск авто-кликера
    }

    private void initializeUI() {
        score = 0;

        // Основной UI Pane с фоном
        uiPane = new BorderPane();
        uiPane.getStyleClass().add("main-background"); // Применение класса для фона

        // Верхняя панель с информацией
        HBox topPanel = new HBox(20);
        topPanel.setPadding(new Insets(10));
        topPanel.setAlignment(Pos.CENTER);
        topPanel.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 5;"); // Темный фон

        // Информационные метки
        scoreLabel = createRoundedLabel("Score: 0");
        autoClickLabel = createRoundedLabel("+0/s");
        batteryLabel = createRoundedLabel("Battery: 100/100");

        topPanel.getChildren().addAll(scoreLabel, autoClickLabel, batteryLabel);
        uiPane.setTop(topPanel);

        // Центральная панель для Кликера, Улучшений, Бустов
        centerPane = new StackPane();
        centerPane.setAlignment(Pos.CENTER);
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg"); // Установка фонового изображения
        uiPane.setCenter(centerPane);

        showClicker();  // Отображение Кликера по умолчанию

        // Нижняя панель с навигационными кнопками
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setAlignment(Pos.CENTER);
        bottomPanel.setSpacing(5);
        bottomPanel.getStyleClass().add("bottom-panel"); // Применение класса нижней панели

        // Навигационные кнопки
        Button upgradeButton = ButtonFactory.createStyledButton("Улучшения");
        upgradeButton.getStyleClass().add("styled-button");
        upgradeButton.setOnAction(event -> showUpgradeMenu());

        Button clickerButton = ButtonFactory.createStyledButton("Кликер");
        clickerButton.getStyleClass().add("styled-button");
        clickerButton.setOnAction(event -> showClicker());

        Button boostsButton = ButtonFactory.createStyledButton("Бусты");
        boostsButton.getStyleClass().add("styled-button");
        boostsButton.setOnAction(event -> showBoostMenu());

        // Обеспечение равномерного растяжения кнопок
        HBox.setHgrow(upgradeButton, Priority.ALWAYS);
        HBox.setHgrow(clickerButton, Priority.ALWAYS);
        HBox.setHgrow(boostsButton, Priority.ALWAYS);

        bottomPanel.getChildren().addAll(upgradeButton, clickerButton, boostsButton);
        bottomPanel.setPrefHeight(50);
        uiPane.setBottom(bottomPanel);

        // Добавление основного UI Pane в основной StackPane
        mainPane.getChildren().add(uiPane);

        // Добавление бокса уведомлений в верхний правый угол
        VBox notificationBox = notificationManager.getNotificationBox();
        mainPane.getChildren().add(notificationBox);
        StackPane.setAlignment(notificationBox, Pos.TOP_RIGHT); // Выравнивание по верхнему правому углу
        StackPane.setMargin(notificationBox, new Insets(10, 10, 0, 0)); // Отступ от краев
    }

    // Создание стилизованных закругленных меток
    private Label createRoundedLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("rounded-label"); // Применение CSS-класса
        return label;
    }

    // Отображение меню улучшений
    private void showUpgradeMenu() {
        centerPane.getChildren().clear();  // Очистка существующего содержимого
        UpgradePanel upgradePanel = new UpgradePanel(this, notificationManager, soundManager);
        centerPane.getChildren().addAll(upgradePanel.getPanel());
    }

    // Отображение интерфейса Кликера
    private void showClicker() {
        centerPane.getChildren().clear();  // Очистка существующего содержимого
        BackgroundManager.setBackgroundImage(centerPane, "/background.jpg"); // Сброс фонового изображения

        // Кликабельное изображение (например, Печенье)
        ImageView cookieImageView = new ImageView(new Image("/cookie.png"));
        cookieImageView.setFitWidth(100);
        cookieImageView.setFitHeight(100);
        cookieImageView.setOnMouseClicked(event -> handleCookieClick(cookieImageView));  // Обработчик кликов

        centerPane.getChildren().add(cookieImageView);
    }

    // Отображение меню бустов
    private void showBoostMenu() {
        centerPane.getChildren().clear();  // Очистка существующего содержимого
        BoostPanel boostPanel = new BoostPanel(this, notificationManager, soundManager);
        centerPane.getChildren().addAll(boostPanel.getPanel());
    }

    // Обработка кликов по Кликеру
    private void handleCookieClick(ImageView cookieImageView) {
        if (isRecharging) {
            notificationManager.showNotification("Батарея на перезарядке! Подождите.", false);
            soundManager.playErrorSound();  // Воспроизведение звука ошибки
            return;
        }

        if (battery > 0) {
            battery--;  // Уменьшение батареи
            score += 1; // Увеличение счета
            updateUI();
            soundManager.playClickSound();  // Воспроизведение звука клика

            if (GameConfig.areAnimationsEnabled()) {
                animateCookie(cookieImageView);
                showPlusOneAnimation();
            }
        } else {
            notificationManager.showNotification("Батарея разряжена! Перезаряжается.", false);
            soundManager.playErrorSound();  // Воспроизведение звука ошибки
            rechargeBattery();  // Начало процесса перезарядки
        }
    }

    // Анимация Кликера
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

    // Обновление UI меток
    private void updateUI() {
        Platform.runLater(() -> {
            scoreLabel.setText("Score: " + score);
            batteryLabel.setText("Battery: " + battery + "/" + maxBattery);
            autoClickLabel.setText("+" + autoClicks + "/s");
        });
    }

    // Анимация появления "+1"
    private void showPlusOneAnimation() {
        Label plusOne = new Label("+1");
        plusOne.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");

        // Случайная позиция внутри центральной панели
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

    // Механизм перезарядки батареи
    private void rechargeBattery() {
        isRecharging = true;
        soundManager.playRechargeSound();  // Воспроизведение звука начала перезарядки
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    battery = maxBattery;
                    isRecharging = false;
                    updateUI();
                    notificationManager.showNotification("Батарея полностью заряжена!", true);
                    soundManager.playRechargeSound();  // Воспроизведение звука завершения перезарядки
                });
            }
        }, 5000);  // Продолжительность перезарядки: 5 секунд
    }

    // Геттеры для счета и батареи
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

    // Обновление счета на заданную сумму
    public void updateScore(int amount) {
        score += amount;
        updateUI();
    }

    // Обновление отображения авто-кликов
    public void updateAutoClickDisplay(int autoClicks) {
        this.autoClicks = autoClicks;
        autoClickLabel.setText("+" + autoClicks + "/s");
    }

    // Обновление отображения батареи
    public void updateBatteryDisplay(int currentBattery, int maxBattery) {
        this.battery = currentBattery;
        this.maxBattery = maxBattery;
        batteryLabel.setText("Battery: " + currentBattery + "/" + maxBattery);
    }

    // Запуск таймера авто-кликера
    private void startAutoClicker() {
        autoClickTimer = new Timer();
        autoClickTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (autoClicks > 0) {
                        score += autoClicks;
                        updateUI();
                        soundManager.playClickSound();  // Воспроизведение звука авто-клика
                    }
                });
            }
        }, 0, 1000);  // Авто-клик каждую секунду
    }

    // Получение основного контейнера для сцены
    public StackPane getMainPane() { // StackPane для наложения уведомлений
        return mainPane;
    }
}
```

**Объяснение:**

- **Контейнеры расположения:**
    - `StackPane` (`mainPane`): Позволяет накладывать уведомления поверх основного интерфейса.
    - `BorderPane` (`uiPane`): Организует интерфейс на верхнюю, центральную и нижнюю части.
- **Верхняя панель:** Отображает текущий счет, скорость авто-клика и статус батареи с использованием стилизованных меток.
- **Центральная панель:** Динамически переключается между интерфейсами Кликера, Улучшений и Бустов.
- **Нижняя панель:** Содержит навигационные кнопки (`Улучшения`, `Кликер`, `Бусты`), стилизованные единообразно.
- **Интеграция уведомлений:** `NotificationManager` добавляет `notificationBox` в `mainPane` для отображения всплывающих уведомлений без блокировки основного интерфейса.
- **Обработчики событий:**
    - Клик по печенью увеличивает счет и обрабатывает разряд батареи.
    - Покупка улучшений или бустов изменяет состояние игры и вызывает уведомления и звуки.

### Уведомления

Класс `NotificationManager.java` отвечает за создание и отображение уведомлений в игре. Эти уведомления являются небольшими, полупрозрачными всплывающими окнами, которые появляются в верхнем правом углу экрана, предоставляя игроку обратную связь.

```java
package me.draimgoose.managers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class NotificationManager {

    private VBox notificationBox;

    public NotificationManager() {
        notificationBox = new VBox(10); // Отступ между уведомлениями
        notificationBox.setAlignment(Pos.TOP_RIGHT);
        notificationBox.getStyleClass().add("notification-box");
        notificationBox.setPickOnBounds(false); // Позволяет событиям мыши проходить через пустые области
        notificationBox.setMouseTransparent(true); // Сделать контейнер прозрачным для мыши
    }

    public VBox getNotificationBox() {
        return notificationBox;
    }

    public void showNotification(String message, boolean isSuccess) {
        HBox notification = createNotification(message, isSuccess);
        notificationBox.getChildren().add(notification);

        // Сделать уведомление интерактивным (разрешить взаимодействие)
        notification.setMouseTransparent(false);

        // Устанавливаем начальное положение уведомления за пределами экрана справа
        notification.setTranslateX(300); // Настройте значение по вашему усмотрению

        // Анимация появления (слайд-ин и плавное появление)
        TranslateTransition slideIn = new TranslateTransition(Duration.millis(500), notification);
        slideIn.setFromX(300);
        slideIn.setToX(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), notification);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        slideIn.play();
        fadeIn.play();

        // Анимация исчезновения (слайд-аут и плавное исчезновение)
        TranslateTransition slideOut = new TranslateTransition(Duration.millis(500), notification);
        slideOut.setFromX(0);
        slideOut.setToX(300);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), notification);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // После задержки запускаем анимацию исчезновения
        slideOut.setDelay(Duration.seconds(3));
        fadeOut.setDelay(Duration.seconds(3));

        // Удаление уведомления после исчезновения
        fadeOut.setOnFinished(event -> notificationBox.getChildren().remove(notification));

        slideOut.play();
        fadeOut.play();
    }

    private HBox createNotification(String message, boolean isSuccess) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.getStyleClass().add("notification");
        if (!isSuccess) {
            hbox.getStyleClass().add("error");
        }

        // Иконка
        ImageView icon = new ImageView(new Image(getClass().getResource(isSuccess ? "/icons/checkmark.png" : "/icons/exclamation.png").toString()));
        icon.setFitWidth(20);
        icon.setFitHeight(20);

        // Метка сообщения
        Label label = new Label(message);
        label.setStyle("-fx-text-fill: " + (isSuccess ? "#155724" : "#721c24") + ";"); // Темно-зеленый или темно-красный

        // Кнопка закрытия уведомления
        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(event -> {
            // Анимация исчезновения при нажатии на кнопку
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), hbox);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> notificationBox.getChildren().remove(hbox));
            fadeOut.play();
        });

        hbox.getChildren().addAll(icon, label, closeButton);
        return hbox;
    }
}
```

**Объяснение:**

- **Box уведомлений (`VBox`):** Расположен в верхнем правом углу и содержит все активные уведомления.
- **Уведомление (`HBox`):** Каждое уведомление состоит из иконки, сообщения и кнопки закрытия.
- **Анимации:**
    - **Slide-In:** Уведомления слайдят из правой части экрана.
    - **Fade-In:** Одновременно слайдят и плавно появляются.
    - **Slide-Out & Fade-Out:** Через 3 секунды уведомления слайдят обратно и исчезают.
- **Интерактивность:** Пользователи могут вручную закрывать уведомления с помощью кнопки `X`, которая запускает анимацию исчезновения.

### Управление звуком

Класс `SoundManager.java` отвечает за все звуковые эффекты в игре, обеспечивая аудиофидбек для улучшения пользовательского опыта.

```java
package me.draimgoose.managers;

import javafx.scene.media.AudioClip;

public class SoundManager {

    private AudioClip clickSound;
    private AudioClip purchaseSound;
    private AudioClip errorSound;
    private AudioClip rechargeSound;

    public SoundManager() {
        // Загрузка звуков из ресурсов
        clickSound = new AudioClip(getClass().getResource("/sounds/click.wav").toString());
        purchaseSound = new AudioClip(getClass().getResource("/sounds/purchase.wav").toString());
        errorSound = new AudioClip(getClass().getResource("/sounds/error.wav").toString());
        rechargeSound = new AudioClip(getClass().getResource("/sounds/recharge.wav").toString());
    }

    public void playClickSound() {
        clickSound.play();
    }

    public void playPurchaseSound() {
        purchaseSound.play();
    }

    public void playErrorSound() {
        errorSound.play();
    }

    public void playRechargeSound() {
        rechargeSound.play();
    }
}
```

**Объяснение:**

- **AudioClips:** Каждый звуковой эффект загружается как `AudioClip` для быстрого воспроизведения.
- **Методы воспроизведения:** Методы, такие как `playClickSound()` и `playPurchaseSound()`, вызываются для воспроизведения соответствующих звуков при игровых событиях.

### Авто-кликер

Функция авто-кликера позволяет игрокам зарабатывать очки автоматически со временем. Это реализовано через таймер, который увеличивает счет на основе количества приобретенных авто-кликов.

```java
// Внутри ClickerGameUI.java

// Запуск таймера авто-кликера
private void startAutoClicker() {
    autoClickTimer = new Timer();
    autoClickTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(() -> {
                if (autoClicks > 0) {
                    score += autoClicks;
                    updateUI();
                    soundManager.playClickSound();  // Воспроизведение звука авто-клика
                }
            });
        }
    }, 0, 1000);  // Авто-клик каждую секунду
}
```

**Объяснение:**

- **TimerTask:** Запланирован для выполнения каждую секунду, увеличивает счет на количество авто-кликов (`autoClicks`).
- **Безопасность потоков:** Использует `Platform.runLater` для обеспечения обновления UI в основном потоке приложения JavaFX.
- **Звуковой фидбек:** Воспроизводится звук клика для каждого авто-клика.

### Улучшения и Бусты

Игроки могут улучшать свои возможности зарабатывания очков с помощью различных улучшений и бустов. Эти функции реализованы в классах `UpgradePanel.java` и `BoostPanel.java`.

#### UpgradePanel.java

Отвечает за улучшения, которые увеличивают скорость авто-клика.

```java
package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;

public class UpgradePanel {

    private VBox panel;

    public UpgradePanel(ClickerGameUI ui, NotificationManager notificationManager, SoundManager soundManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.getStyleClass().add("upgrade-panel"); // Применение CSS-класса
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button buyAutoClickButton = createStyledRectangleButton("Купить авто-клик (+1/s)", "100 очков");
        buyAutoClickButton.setOnAction(event -> {
            int cost = 100;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                ui.updateAutoClickDisplay(ui.getAutoClicks() + 1);
                notificationManager.showNotification("Авто-клик куплен!", true);
                soundManager.playPurchaseSound(); // Воспроизведение звука успешной покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Воспроизведение звука ошибки
            }
        });

        panel.getChildren().addAll(buyAutoClickButton);
    }

    // Создание стилизованных кнопок
    private Button createStyledRectangleButton(String description, String cost) {
        Button button = new Button();
        button.setText(description + "\nСтоимость: " + cost);
        button.getStyleClass().add("styled-button"); // Применение CSS-класса
        return button;
    }

    public VBox getPanel() {
        return panel;
    }
}
```

**Объяснение:**

- **Кнопка улучшения:** Позволяет игрокам покупать авто-кликеры, увеличивающие скорость авто-клика.
- **Обработка событий:** Проверяет наличие достаточного количества очков (`score`) перед обработкой покупки.
- **Обратная связь:** Отображает уведомления об успешной покупке или ошибке и воспроизводит соответствующие звуки.

#### BoostPanel.java

Отвечает за бусты, которые улучшают силу клика или емкость батареи.

```java
package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;

public class BoostPanel {

    private VBox panel;

    public BoostPanel(ClickerGameUI ui, NotificationManager notificationManager, SoundManager soundManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.getStyleClass().add("boost-panel"); // Применение CSS-класса
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button buyClickBoostButton = createStyledRectangleButton("Улучшить клики (x2)", "200 очков");
        buyClickBoostButton.setOnAction(event -> {
            int cost = 200;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                // Логика улучшения кликов (например, умножение на 2)
                notificationManager.showNotification("Улучшение кликов куплено!", true);
                soundManager.playPurchaseSound(); // Воспроизведение звука успешной покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Воспроизведение звука ошибки
            }
        });

        Button buyBatteryBoostButton = createStyledRectangleButton("Увеличить батарею (+50)", "150 очков");
        buyBatteryBoostButton.setOnAction(event -> {
            int cost = 150;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                ui.updateBatteryDisplay(ui.getCurrentBattery() + 50, ui.getMaxBattery() + 50);
                notificationManager.showNotification("Увеличение батареи куплено!", true);
                soundManager.playPurchaseSound(); // Воспроизведение звука успешной покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Воспроизведение звука ошибки
            }
        });

        panel.getChildren().addAll(buyClickBoostButton, buyBatteryBoostButton);
    }

    // Создание стилизованных кнопок
    private Button createStyledRectangleButton(String description, String cost) {
        Button button = new Button();
        button.setText(description + "\nСтоимость: " + cost);
        button.getStyleClass().add("styled-button"); // Применение CSS-класса
        return button;
    }

    public VBox getPanel() {
        return panel;
    }
}
```

**Объяснение:**

- **Бусты:**
    - **Улучшение кликов:** Повышает силу клика, например, удваивая количество очков за клик.
    - **Увеличение батареи:** Увеличивает емкость батареи, позволяя больше кликать до необходимости перезарядки.
- **Обработка событий:** Проверяет наличие достаточного количества очков перед обработкой покупки.
- **Обратная связь:** Отображает уведомления об успешной покупке или ошибке и воспроизводит соответствующие звуки.

### Фабрика кнопок

Класс `ButtonFactory.java` предоставляет централизованный метод для создания единообразно стилизованных кнопок по всему приложению.

```java
package me.draimgoose.utils;

import javafx.scene.control.Button;

public class ButtonFactory {

    // Создание стилизованных кнопок с единообразным внешним видом
    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("styled-button"); // Применение CSS-класса
        return button;
    }
}
```

**Объяснение:**

- **Согласованность:** Обеспечивает одинаковый внешний вид всех кнопок путем применения одного CSS-класса.
- **Гибкость:** Упрощает создание кнопок по всему приложению.

---

## Ресурсы

Все ресурсы, используемые в **Clicker Game**, хранятся в директории `src/main/resources/`.

- **Стили:**
    - `styles.css` – Определяет внешний вид компонентов интерфейса.

- **Изображения:**
    - `background.jpg` – Основное фоновое изображение, заполняющее окно приложения.
    - `icons/`
        - `checkmark.png` – Иконка для уведомлений об успехе.
        - `exclamation.png` – Иконка для уведомлений об ошибках.
    - `cookie.png` – Кликабельный объект в игре.

- **Звуки:**
    - `sounds/`
        - `click.wav` – Звук при каждом клике.
        - `purchase.wav` – Звук при успешных покупках.
        - `error.wav` – Звук при ошибках (например, недостаточно очков).
        - `recharge.wav` – Звук при перезарядке батареи.

**Примечание:** Убедитесь, что все файлы ресурсов правильно размещены и пути к ним точно указаны в коде.

---

## Вклад в проект

Вклад приветствуется! Если вы хотите улучшить **Clicker Game**, не стесняйтесь форкнуть репозиторий и отправить pull-запросы. Будь то добавление новых функций, улучшение существующих или исправление ошибок, ваш вклад ценен.

1. **Форк репозитория**
2. **Создайте ветку для новой функции**

   ```bash
   git checkout -b feature/YourFeature
   ```

3. **Закоммитьте ваши изменения**

   ```bash
   git commit -m "Add Your Feature"
   ```

4. **Отправьте в ветку**

   ```bash
   git push origin feature/YourFeature
   ```

5. **Откройте Pull Request**

---

## Лицензия

Этот проект лицензирован под лицензией [MIT License](LICENSE).

---

## Благодарности

- **JavaFX:** Спасибо сообществу JavaFX за предоставление мощных инструментов для создания насыщенных клиентских приложений.
- **Вкладчики с открытым исходным кодом:** Особая благодарность всем, кто помог улучшить этот проект.

---

Если у вас возникнут вопросы или предложения, не стесняйтесь обращаться. Приятной игры в **Clicker Game**!