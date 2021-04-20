package com.ndrewcoding.main;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

	private AudioClip clip;

	public static final Sound bglvl1 = new Sound("/sounds/bgm/lvl1bgm.wav");
	public static final Sound bglvl4 = new Sound("/sounds/bgm/lvl4bgm.wav");
	public static final Sound bglvl5 = new Sound("/sounds/bgm/lvl5bgm.wav");
	public static final Sound bglvl6 = new Sound("/sounds/bgm/lvl6bgm.wav");
	public static final Sound bglvl7 = new Sound("/sounds/bgm/lvl7bgm.wav");
	public static final Sound bglvl8 = new Sound("/sounds/bgm/lvl8bgm.wav");
	public static final Sound bglvl9 = new Sound("/sounds/bgm/lvl9bgm.wav");
	public static final Sound bglvl10 = new Sound("/sounds/bgm/lvl10bgm.wav");

	public static final Sound menuChange = new Sound("/sounds/menuChange.wav");
	public static final Sound playerHurtEffect = new Sound("/sounds/playerHurt.wav");
	public static final Sound healingSound = new Sound("/sounds/healingSound.wav");
	public static final Sound paperCatched = new Sound("/sounds/paperCatched.wav");
	public static final Sound graduationCatched = new Sound("/sounds/graduationCatched.wav");
	public static final Sound calendarCatched = new Sound("/sounds/calendarCatched.wav");
	public static final Sound slowMotionSound = new Sound("/sounds/slowMotionSound.wav");
	public static final Sound playerMovingSound = new Sound("/sounds/playerFootsteps.wav");
	public static final Sound enemyMovingSound = new Sound("/sounds/enemyMoving.wav");
	public static final Sound snowFootstep = new Sound("/sounds/snowFootstep.wav");
	public static final Sound levelUp = new Sound("/sounds/levelUp.wav");
	public static final Sound gameWonMusic = new Sound("/sounds/bgm/gameWonBgm.wav");
	public static final Sound gameOverMusic = new Sound("/sounds/bgm/gameOverBgm.wav");

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					clip.play();
				}
			}.start();
		} catch (Throwable e) {
		}
	}

	public void loop() {
		try {
			new Thread() {
				public void run() {
					clip.loop();
				}
			}.start();
		} catch (Throwable e) {
		}
	}

	public void stop() {
		try {
			new Thread() {
				public void run() {
					clip.stop();
				}
			}.start();
		} catch (Throwable e) {
		}
	}

	public static void pararTodosOsSons() {
		Sound.bglvl1.stop();
		Sound.bglvl4.stop();
		Sound.bglvl5.stop();
		Sound.bglvl6.stop();
		Sound.bglvl7.stop();
		Sound.bglvl8.stop();
		Sound.bglvl9.stop();
		Sound.bglvl10.stop();
	}
}
