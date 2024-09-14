package me.draimgoose;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.draimgoose.ui.ClickerGameUI;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        ClickerGameUI gameUI = new ClickerGameUI();
        Scene scene = new Scene(gameUI.getMainPane(), 800, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setTitle("Clicker Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Обработчик закрытия для сохранения состояния
        primaryStage.setOnCloseRequest(event -> {
            me.draimgoose.managers.GameState.getInstance().saveState();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
