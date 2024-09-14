package me.draimgoose.animations;

import javax.swing.*;

public class CookieAnimation implements AnimationManager.AnimatedObject {
    private JLabel cookieLabel;
    private int originalY;
    private int animationStep;
    private boolean animating;
    private static final int ANIMATION_RANGE = 20;

    public CookieAnimation(JLabel cookieLabel, int originalY, AnimationManager manager) {
        this.cookieLabel = cookieLabel;
        this.originalY = originalY;
        this.animationStep = 0;
        this.animating = true;
        manager.addAnimatedObject(this);
    }

    @Override
    public boolean update() {
        if (!animating) return false;

        animationStep++;
        int newY = originalY + (int) (Math.sin(animationStep * 0.2) * ANIMATION_RANGE);
        cookieLabel.setLocation(cookieLabel.getX(), newY);

        if (animationStep >= 30) {
            cookieLabel.setLocation(cookieLabel.getX(), originalY);
            animating = false;
            return false; // Анимация завершена
        }
        return true; // Анимация продолжается
    }
}
