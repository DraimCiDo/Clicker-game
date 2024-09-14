package me.draimgoose;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.draimgoose.ui.ClickerGameUI;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Clicker Game");
        ClickerGameUI gameUI = new ClickerGameUI();
        Scene scene = new Scene(gameUI.getMainPane(), 500, 500);

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(500);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
