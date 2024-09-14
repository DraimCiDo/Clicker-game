package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.utils.ButtonFactory;

public class BoostPanel {

    private VBox panel;

    public BoostPanel(ClickerGameUI ui, NotificationManager notificationManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button buyClickBoostButton = ButtonFactory.createStyledButton("Улучшить клики (x2) - 200 очков");
        buyClickBoostButton.setOnAction(event -> {
            int cost = 200;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                notificationManager.showNotification("Улучшение кликов куплено!", true);
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
            }
        });

        Button buyBatteryBoostButton = ButtonFactory.createStyledButton("Увеличить батарею (+50) - 150 очков");
        buyBatteryBoostButton.setOnAction(event -> {
            int cost = 150;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                ui.updateBatteryDisplay(ui.getCurrentBattery() + 50, ui.getMaxBattery() + 50);
                notificationManager.showNotification("Увеличение батареи куплено!", true);
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
            }
        });

        panel.getChildren().addAll(buyClickBoostButton, buyBatteryBoostButton);
    }

    public VBox getPanel() {
        return panel;
    }
}
