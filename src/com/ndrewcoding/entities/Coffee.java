package com.ndrewcoding.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;

public class Coffee extends Entity {

	public BufferedImage sprites_coffee[];

	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 1;

	public Coffee(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		depth = 0;

		sprites_coffee = new BufferedImage[6];

		for (int i = 0; i < 5; i++) {
			sprites_coffee[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
		}
	}

	public void tick() {
		frames++;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			if (index > maxIndex) {
				index = 0;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(sprites_coffee[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
