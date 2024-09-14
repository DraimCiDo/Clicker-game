package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;
import me.draimgoose.utils.ButtonFactory;

public class BoostPanel {

    private VBox panel;

    public BoostPanel(ClickerGameUI ui, NotificationManager notificationManager, SoundManager soundManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button buyClickBoostButton = createStyledRectangleButton("Улучшить клики (x2)", "200 очков");
        buyClickBoostButton.setOnAction(event -> {
            int cost = 200;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                // Логика улучшения кликов (например, умножение на 2)
                notificationManager.showNotification("Улучшение кликов куплено!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Звук ошибки
            }
        });

        Button buyBatteryBoostButton = createStyledRectangleButton("Увеличить батарею (+50)", "150 очков");
        buyBatteryBoostButton.setOnAction(event -> {
            int cost = 150;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                ui.updateBatteryDisplay(ui.getCurrentBattery() + 50, ui.getMaxBattery() + 50);
                notificationManager.showNotification("Увеличение батареи куплено!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Звук ошибки
            }
        });

        panel.getChildren().addAll(buyClickBoostButton, buyBatteryBoostButton);
    }

    // Метод создания кнопки с описанием и стоимостью
    private Button createStyledRectangleButton(String description, String cost) {
        Button button = new Button();
        button.setText(description + "\nСтоимость: " + cost);
        button.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        button.setWrapText(true);
        button.setMaxWidth(200);
        button.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-color: #2a2a2a; " +
                        "-fx-border-width: 2px; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 15; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-radius: 10;"
        );
        return button;
    }

    public VBox getPanel() {
        return panel;
    }
}
