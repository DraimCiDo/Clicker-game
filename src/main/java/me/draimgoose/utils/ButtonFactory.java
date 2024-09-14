package me.draimgoose.utils;

import javafx.scene.control.Button;

public class ButtonFactory {

    public static Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinWidth(100); // Устанавливаем минимальную ширину для кнопок
        button.setStyle("-fx-background-color: #4a4a4a; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10;");
        return button;
    }
}
