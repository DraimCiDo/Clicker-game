package me.draimgoose;

import javax.swing.*;

public class Clicker {
    private GamePanel gamePanel;
    private Timer idleClickTimer;
    private int autoClickLevel = 0;
    private int autoClicksPerSecond = 0;

    public Clicker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        idleClickTimer = new Timer(1000, e -> idleClick());
    }

    public void increaseAutoClicks() {
        autoClickLevel++;
        autoClicksPerSecond = autoClickLevel;
        gamePanel.updateAutoClickDisplay(autoClicksPerSecond);
        idleClickTimer.start();
    }

    private void idleClick() {
        gamePanel.setScore(gamePanel.getScore() + autoClicksPerSecond);
    }
}
