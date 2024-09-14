package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.utils.ButtonFactory;

public class UpgradePanel {

    private VBox panel;

    public UpgradePanel(ClickerGameUI ui, NotificationManager notificationManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button buyAutoClickButton = ButtonFactory.createStyledButton("Купить авто-клик (+1/s) - 100 очков");
        buyAutoClickButton.setOnAction(event -> {
            int cost = 100;
            if (ui.getScore() >= cost) {
                ui.updateScore(-cost);
                ui.updateAutoClickDisplay(ui.getAutoClicks() + 1);
                notificationManager.showNotification("Авто-клик куплен!", true);
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
            }
        });

        panel.getChildren().addAll(buyAutoClickButton);
    }

    public VBox getPanel() {
        return panel;
    }
}
