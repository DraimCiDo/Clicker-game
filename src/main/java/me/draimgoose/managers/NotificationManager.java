package me.draimgoose.managers;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
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

        // Анимация появления
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), notification);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();

        // Анимация исчезновения
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), notification);
        fadeOut.setDelay(Duration.seconds(3)); // Уведомление видно 3 секунды
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> notificationBox.getChildren().remove(notification));
        fadeOut.play();
    }

    private HBox createNotification(String message, boolean isSuccess) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setStyle(
                "-fx-background-color: " + (isSuccess ? "#d4edda" : "#f8d7da") + "; " +  // Зеленый для успеха, красный для ошибок
                        "-fx-border-color: " + (isSuccess ? "#c3e6cb" : "#f5c6cb") + "; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 10;"
        );

        // Добавление иконки
        ImageView icon = new ImageView(new Image(getClass().getResource(isSuccess ? "/icons/checkmark.png" : "/icons/exclamation.png").toString()));
        icon.setFitWidth(20);
        icon.setFitHeight(20);

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: " + (isSuccess ? "#155724" : "#721c24") + ";"); // Темно-зеленый для успеха, темно-красный для ошибок

        hbox.getChildren().addAll(icon, label);
        return hbox;
    }
}
