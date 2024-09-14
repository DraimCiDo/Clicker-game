package me.draimgoose.ui;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import me.draimgoose.config.GameConfig;

public class ClickerGameUI {

    private BorderPane mainPane;
    private Label scoreLabel;
    private Label autoClickLabel;
    private Label batteryLabel;
    private int score;
    private int autoClicks = 0;
    private int battery = 100;
    private final int maxBattery = 100;

    public ClickerGameUI() {
        mainPane = new BorderPane();
        initializeUI();
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

        // Основная панель с изображением печенья
        VBox centerPanel = new VBox();
        centerPanel.setAlignment(Pos.CENTER);

        ImageView cookieImageView = new ImageView(new Image("/cookie.png"));
        cookieImageView.setFitWidth(100);
        cookieImageView.setFitHeight(100);
        cookieImageView.setOnMouseClicked(event -> handleCookieClick(cookieImageView));

        centerPanel.getChildren().add(cookieImageView);
        mainPane.setCenter(centerPanel);

        // Нижняя панель с кнопками
        HBox bottomPanel = new HBox(10);
        bottomPanel.setPadding(new Insets(10));
        bottomPanel.setAlignment(Pos.CENTER);

        Button upgradeButton = new Button("Улучшения");
        Button clickerButton = new Button("Кликер");
        Button boostsButton = new Button("Бусты");

        bottomPanel.getChildren().addAll(upgradeButton, clickerButton, boostsButton);
        mainPane.setBottom(bottomPanel);
    }

    private void handleCookieClick(ImageView cookieImageView) {
        if (battery > 0) {
            score += 1;
            battery -= 1;
            updateUI();

            if (GameConfig.areAnimationsEnabled()) {
                playClickAnimation(cookieImageView);
                showPlusOneAnimation();
            }
        } else {
            System.out.println("Батарея разряжена! Подождите восстановления.");
        }
    }

    private void updateUI() {
        scoreLabel.setText("Score: " + score);
        batteryLabel.setText("Battery: " + battery + "/" + maxBattery);
        autoClickLabel.setText("+" + autoClicks + "/s");
    }

    private void playClickAnimation(ImageView cookieImageView) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(100), cookieImageView);
        translate.setByY(-10);
        translate.setAutoReverse(true);
        translate.setCycleCount(2);
        translate.play();
    }

    private void showPlusOneAnimation() {
        Label plusOne = new Label("+1");
        plusOne.setStyle("-fx-text-fill: red; -fx-font-size: 18px; -fx-font-weight: bold;");
        VBox center = (VBox) mainPane.getCenter();
        center.getChildren().add(plusOne);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), plusOne);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> center.getChildren().remove(plusOne));
        fadeTransition.play();
    }

    public BorderPane getMainPane() {
        return mainPane;
    }
}
