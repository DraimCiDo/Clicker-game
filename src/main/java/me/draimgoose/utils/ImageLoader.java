package me.draimgoose.utils;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static BufferedImage loadImage(String path) {
        try {
            URL imageUrl = ImageLoader.class.getResource(path);
            if (imageUrl != null) {
                return ImageIO.read(imageUrl);
            } else {
                System.out.println("Ошибка: изображение не найдено по пути " + path);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при загрузке изображения: " + e.getMessage());
        }
        return null;
    }
}
