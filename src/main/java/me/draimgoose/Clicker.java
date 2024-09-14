package me.draimgoose.ui;

import java.util.Timer;
import java.util.TimerTask;

public class Clicker {
    private ClickerGameUI gameUI;
    private int autoClickInterval = 1000; // Интервал автокликов в миллисекундах
    private int autoClicks = 0; // Количество автокликов в секунду

    public Clicker(ClickerGameUI gameUI) {
        this.gameUI = gameUI;
        startAutoClicker();
    }

    public void startAutoClicker() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (autoClicks > 0) {
                    gameUI.updateScore(autoClicks);
                    gameUI.updateAutoClickDisplay(autoClicks);
                }
            }
        }, autoClickInterval, autoClickInterval);
    }

    public void addAutoClick(int amount) {
        autoClicks += amount;
        gameUI.updateAutoClickDisplay(autoClicks);
    }
}
