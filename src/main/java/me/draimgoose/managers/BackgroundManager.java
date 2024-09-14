package me.draimgoose.managers;

import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class BackgroundManager {

    public static void setBackgroundImage(Region region, String imagePath) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image(imagePath, 500, 500, false, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        region.setBackground(new Background(backgroundImage));
    }
}
