package me.draimgoose.panels;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.GameState;
import me.draimgoose.managers.NotificationManager;
import me.draimgoose.managers.SoundManager;

public class UpgradePanel {

    private VBox panel;

    public UpgradePanel(ClickerGameUI ui, NotificationManager notificationManager, SoundManager soundManager) {
        panel = new VBox(15);
        panel.setAlignment(Pos.CENTER);
        panel.getStyleClass().add("upgrade-panel"); // Добавляем CSS-класс
        panel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-padding: 20px; -fx-border-radius: 10; -fx-background-radius: 10;");

        // Кнопка покупки авто-клика
        Button buyAutoClickButton = createStyledRectangleButton("Улучшить клики (+1/s)", String.valueOf(getCostForUpgrade("autoClick")));
        buyAutoClickButton.setOnAction(event -> {
            GameState gameState = GameState.getInstance();
            int cost = getCostForUpgrade("autoClick");
            if (gameState.getScore() >= cost) {
                gameState.setScore(gameState.getScore() - cost);
                gameState.setAutoClicks(gameState.getAutoClicks() + 1);
                // Масштабирование стоимости
                // В данном примере стоимость зависит от уровня, поэтому дополнительного масштабирования не требуется
                notificationManager.showNotification("Авто-клик куплен!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
                gameState.saveState(); // Сохранение состояния после покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Звук ошибки
            }
        });

        // Кнопка повышения уровня
        Button levelUpButton = createStyledRectangleButton("Повысить уровень", String.valueOf(getCostForUpgrade("level")));
        levelUpButton.setOnAction(event -> {
            GameState gameState = GameState.getInstance();
            int cost = getCostForUpgrade("level");
            if (gameState.getScore() >= cost) {
                gameState.setScore(gameState.getScore() - cost);
                gameState.setLevel(gameState.getLevel() + 1);
                // Увеличение множителя улучшений
                gameState.setUpgradeMultiplier(gameState.getUpgradeMultiplier() + 0.1);
                // Масштабирование стоимости
                // В данном примере стоимость зависит от уровня, поэтому дополнительного масштабирования не требуется
                notificationManager.showNotification("Уровень повышен!", true);
                soundManager.playPurchaseSound(); // Звук успешной покупки
                gameState.saveState(); // Сохранение состояния после покупки
            } else {
                notificationManager.showNotification("Недостаточно очков!", false);
                soundManager.playErrorSound(); // Звук ошибки
            }
        });

        panel.getChildren().addAll(buyAutoClickButton, levelUpButton);
    }

    // Метод создания кнопки с описанием и стоимостью
    private Button createStyledRectangleButton(String description, String cost) {
        Button button = new Button();
        button.setText(description + "\nСтоимость: " + cost + " очков");
        button.getStyleClass().add("styled-button"); // Применяем CSS-класс
        return button;
    }

    // Метод получения стоимости улучшения
    private int getCostForUpgrade(String upgradeType) {
        GameState gameState = GameState.getInstance();
        switch (upgradeType) {
            case "autoClick":
                return gameState.getLevel() * 100; // Пример: стоимость зависит от уровня
            case "level":
                return gameState.getLevel() * 250; // Пример: стоимость повышения уровня
            default:
                return 100;
        }
    }

    // Метод установки новой стоимости улучшения
    private void setCostForUpgrade(String upgradeType, int newCost) {
        // В данном примере стоимость рассчитывается динамически в getCostForUpgrade
        // Поэтому данный метод может быть опущен или использоваться для сохранения пользовательских настроек
    }

    public VBox getPanel() {
        return panel;
    }
}
