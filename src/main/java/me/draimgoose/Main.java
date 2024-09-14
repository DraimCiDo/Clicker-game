package me.draimgoose;

import javafx.application.Application;
import javafx.scene.Scene;
import me.draimgoose.ui.ClickerGameUI;
import me.draimgoose.managers.GameState;

public class Main extends Application {
    @Override
    public void start(javafx.stage.Stage primaryStage) {
        ClickerGameUI gameUI = new ClickerGameUI();
        Scene scene = new Scene(gameUI.getMainPane(), 800, 600); // Размер окна можно изменить по необходимости
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Добавляем CSS-файл

        primaryStage.setTitle("Clicker Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Добавляем обработчик закрытия окна для сохранения состояния
        primaryStage.setOnCloseRequest(event -> {
            GameState.getInstance().saveState();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
