package me.draimgoose.animations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class AnimationManager {
    private Timer animationTimer;
    private List<AnimatedObject> animatedObjects;
    private JPanel panel;

    public AnimationManager(JPanel panel) {
        this.panel = panel;
        this.animatedObjects = new ArrayList<>();

        animationTimer = new Timer(16, (ActionEvent e) -> updateAnimations()); // Обновление каждые ~60 FPS
    }

    public void addAnimatedObject(AnimatedObject obj) {
        animatedObjects.add(obj);
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
    }

    private void updateAnimations() {
        boolean anyAnimating = false;

        for (int i = 0; i < animatedObjects.size(); i++) {
            AnimatedObject obj = animatedObjects.get(i);
            if (obj.update()) {
                anyAnimating = true;
            } else {
                animatedObjects.remove(obj); // Удаляем объект из списка, если анимация завершена
                i--; // Уменьшаем индекс, чтобы не пропустить следующий элемент после удаления
            }
        }

        if (anyAnimating) {
            panel.repaint(); // Перерисовываем только если есть активные анимации
        } else {
            animationTimer.stop(); // Останавливаем таймер, если анимаций больше нет
        }
    }

    public interface AnimatedObject {
        boolean update();
    }
}
