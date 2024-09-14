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

        // Добавление иконки
        ImageView icon = new ImageView(new Image(getClass().getResource(isSuccess ? "/icons/checkmark.png" : "/icons/exclamation.png").toString()));
        icon.setFitWidth(20);
        icon.setFitHeight(20);

        Label label = new Label(message);
        label.setStyle("-fx-text-fill: " + (isSuccess ? "#155724" : "#721c24") + ";"); // Темно-зеленый для успеха, темно-красный для ошибок

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
