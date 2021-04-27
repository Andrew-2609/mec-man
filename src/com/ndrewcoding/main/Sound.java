package com.ndrewcoding.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.net.URL;
import java.util.Objects;

public class Sound {

    private Clip clip;

    public static final Sound bgLevelOneToThree = new Sound("/sounds/bgm/lvl1bgm.wav");
    public static final Sound bgLevelFour = new Sound("/sounds/bgm/lvl4bgm.wav");
    public static final Sound bgLevelFive = new Sound("/sounds/bgm/lvl5bgm.wav");
    public static final Sound bgLevelSix = new Sound("/sounds/bgm/lvl6bgm.wav");
    public static final Sound bgLevelSeven = new Sound("/sounds/bgm/lvl7bgm.wav");
    public static final Sound bgLevelEight = new Sound("/sounds/bgm/lvl8bgm.wav");
    public static final Sound bgLevelNine = new Sound("/sounds/bgm/lvl9bgm.wav");
    public static final Sound bgLevelTen = new Sound("/sounds/bgm/lvl10bgm.wav");

    public static final Sound menuChange = new Sound("/sounds/menuChange.wav");
    public static final Sound playerHurtEffect = new Sound("/sounds/playerHurt.wav");
    public static final Sound healingSound = new Sound("/sounds/healingSound.wav");
    public static final Sound paperCaught = new Sound("/sounds/paperCaught.wav");
    public static final Sound graduationCaught = new Sound("/sounds/graduationCaught.wav");
    public static final Sound calendarCaught = new Sound("/sounds/calendarCaught.wav");
    public static final Sound slowMotionSound = new Sound("/sounds/slowMotionSound.wav");
    public static final Sound playerMovingSound = new Sound("/sounds/playerFootsteps.wav");
    public static final Sound enemyMovingSound = new Sound("/sounds/enemyMoving.wav");
    public static final Sound snowFootstep = new Sound("/sounds/snowFootstep.wav");
    public static final Sound levelUp = new Sound("/sounds/levelUp.wav");
    public static final Sound gameWonMusic = new Sound("/sounds/bgm/gameWonBgm.wav");
    public static final Sound gameOverMusic = new Sound("/sounds/bgm/gameOverBgm.wav");

    private Sound(String name) {
        try {
            URL url = Sound.class.getResource(name);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(url));
            DataLine.Info info = new DataLine.Info(Clip.class, audioInputStream.getFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(1);
    }

    public void stop() {
        clip.stop();
        clip.setMicrosecondPosition(0);
    }

    public static void stopAllBackgroundMusic() {
        Sound.bgLevelOneToThree.stop();
        Sound.bgLevelFour.stop();
        Sound.bgLevelFive.stop();
        Sound.bgLevelSix.stop();
        Sound.bgLevelSeven.stop();
        Sound.bgLevelEight.stop();
        Sound.bgLevelNine.stop();
        Sound.bgLevelTen.stop();
    }
}
