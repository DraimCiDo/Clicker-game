package me.draimgoose.managers;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class NotificationManager {

    private Label notificationLabel;

    public NotificationManager() {
        notificationLabel = new Label();
        notificationLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: green;");
    }

    public Label getNotificationLabel() {
        return notificationLabel;
    }

    public void showNotification(String message, boolean isSuccess) {
        notificationLabel.setText(message);
        notificationLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: " + (isSuccess ? "green" : "red") + ";");
        notificationLabel.setOpacity(1.0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(3000), notificationLabel);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> notificationLabel.setText(""));
        fadeTransition.play();
    }
}
