package me.draimgoose.animations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CookieAnimation {
    private Timer animationTimer;
    private int animationStep = 0;
    private int originalY;

    public CookieAnimation(JLabel cookieLabel, int originalY) {
        this.originalY = originalY;
        startAnimation(cookieLabel);
    }

    public void startAnimation(JLabel cookieLabel) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop(); // Останавливаем текущую анимацию, если она уже работает
        }

        int animationRange = 20;
        animationStep = 0;

        animationTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animationStep++;
                int newY = originalY + (int) (Math.sin(animationStep * 0.2) * animationRange);

                cookieLabel.setLocation(cookieLabel.getX(), newY);

                if (animationStep >= 30) {
                    animationTimer.stop();
                    cookieLabel.setLocation(cookieLabel.getX(), originalY);
                }
            }
        });
        animationTimer.start();
    }
}
