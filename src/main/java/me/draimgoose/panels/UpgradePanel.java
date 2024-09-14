package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;
import me.draimgoose.utils.ButtonFactory;

public class UpgradePanel {

    private VBox panel;

    public UpgradePanel(ClickerGameUI ui, NotificationManager notificationManager, SoundManager soundManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button buyAutoClickButton = createStyledRectangleButton("Купить авто-клик (+1/s)", "100 очков");
        buyAutoClickButton.setOnAction(event -> {
            int cost = 100;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                ui.updateAutoClickDisplay(ui.getAutoClicks() + 1);
                notificationManager.showNotification("Авто-клик куплен!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Звук ошибки
            }
        });

        panel.getChildren().addAll(buyAutoClickButton);
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
