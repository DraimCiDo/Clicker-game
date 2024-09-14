package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.GameState;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;

public class BoostPanel {

    private VBox panel;

    public BoostPanel(ClickerGameUI ui, NotificationManager notificationManager, SoundManager soundManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.getStyleClass().add("boost-panel"); // Добавляем CSS-класс
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Кнопка улучшения кликов
        Button buyClickBoostButton = createStyledRectangleButton("Улучшить клики (x2)", String.valueOf(getCostForBoost("clickBoost")));
        buyClickBoostButton.setOnAction(event -> {
            GameState gameState = GameState.getInstance();
            int cost = getCostForBoost("clickBoost");
            if (gameState.getScore() >= cost) {
                gameState.setScore(gameState.getScore() - cost);
                // Удвоение множителя улучшений
                gameState.setUpgradeMultiplier(gameState.getUpgradeMultiplier() * 2);
                // Масштабирование стоимости
                setCostForBoost("clickBoost", (int)(cost * 2.5));
                notificationManager.showNotification("Улучшение кликов куплено!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
                gameState.saveState(); // Сохранение состояния после покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Звук ошибки
            }
        });

        // Кнопка увеличения батареи
        Button buyBatteryBoostButton = createStyledRectangleButton("Увеличить батарею (+50)", String.valueOf(getCostForBoost("batteryBoost")));
        buyBatteryBoostButton.setOnAction(event -> {
            GameState gameState = GameState.getInstance();
            int cost = getCostForBoost("batteryBoost");
            if (gameState.getScore() >= cost) {
                gameState.setScore(gameState.getScore() - cost);
                gameState.setBattery(gameState.getBattery() + 50);
                gameState.setMaxBattery(gameState.getMaxBattery() + 50);
                // Масштабирование стоимости
                setCostForBoost("batteryBoost", (int)(cost * 2.5));
                notificationManager.showNotification("Увеличение батареи куплено!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
                gameState.saveState(); // Сохранение состояния после покупки
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
        button.setText(description + "\nСтоимость: " + cost + " очков");
        button.getStyleClass().add("styled-button"); // Применяем CSS-класс
        return button;
    }

    // Метод получения стоимости буста
    private int getCostForBoost(String boostType) {
        GameState gameState = GameState.getInstance();
        switch (boostType) {
            case "clickBoost":
                return gameState.getLevel() * 200; // Пример: стоимость зависит от уровня
            case "batteryBoost":
                return gameState.getLevel() * 300; // Пример: стоимость увеличения батареи
            default:
                return 150;
        }
    }

    // Метод установки новой стоимости буста
    private void setCostForBoost(String boostType, int newCost) {
        // В данном примере стоимость рассчитывается динамически в getCostForBoost
        // Поэтому данный метод может быть опущен или использоваться для сохранения пользовательских настроек
    }

    public VBox getPanel() {
        return panel;
    }
}
