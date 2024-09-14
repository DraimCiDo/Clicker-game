package me.draimgoose.managers;

import javafx.scene.media.AudioClip;

public class SoundManager {

    private AudioClip clickSound;
    private AudioClip purchaseSound;
    private AudioClip errorSound;
    private AudioClip rechargeSound;

    public SoundManager() {
        // Загрузите звуки из ресурсов
        clickSound = new AudioClip(getClass().getResource("/sounds/click.wav").toString());
        purchaseSound = new AudioClip(getClass().getResource("/sounds/purchase.wav").toString());
        errorSound = new AudioClip(getClass().getResource("/sounds/error.wav").toString());
        rechargeSound = new AudioClip(getClass().getResource("/sounds/recharge.wav").toString());
    }

    public void playClickSound() {
        clickSound.play();
    }

    public void playPurchaseSound() {
        purchaseSound.play();
    }

    public void playErrorSound() {
        errorSound.play();
    }

    public void playRechargeSound() {
        rechargeSound.play();
    }
}
