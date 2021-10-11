package com.ndrewcoding.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;

public class FinalBoss extends Enemy {

	private int frames = 0;
	private int index = 0;
	private final BufferedImage[] sprites;

	public FinalBoss(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		sprites = new BufferedImage[5];
		sprites[0] = Game.spritesheet.getSprite(160, 0, 48, 48);
		sprites[1] = Game.spritesheet.getSprite(208, 0, 48, 48);
		sprites[2] = Game.spritesheet.getSprite(256, 0, 48, 48);
		sprites[3] = Game.spritesheet.getSprite(304, 0, 48, 48);
		sprites[4] = Game.spritesheet.getSprite(352, 0, 48, 48);
	}

	public void tick() {
		depth = 1;

		frames++;
		int maxFrames = 16;
		if (frames == maxFrames) {
			frames = 0;
			index++;
			int maxIndex = 4;
			if (index > maxIndex) {
				index = 0;
			}
		}

		int dx = 0;
		int dy = 0;
		int px = 0;
		int py = 4;
		if (Entity.rand.nextInt(4) == 0) {
			px = 8;
			dx = Entity.rand.nextInt(17);
			dy = Entity.rand.nextInt(17);
		} else if (Entity.rand.nextInt(4) == 1) {
			dx = Entity.rand.nextInt(17) * (-1);
			dy = Entity.rand.nextInt(17) * (-1);
		} else if (Entity.rand.nextInt(4) == 2) {
			dx = Entity.rand.nextInt(17);
			dy = Entity.rand.nextInt(17) * (-1);
		} else if (Entity.rand.nextInt(4) == 3) {
			dx = Entity.rand.nextInt(17) * (-1);
			dy = Entity.rand.nextInt(17);
		}

		MecSlime mecSlime = new MecSlime(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
		Game.enemies.add(mecSlime);

		if (Entity.isColliding(this, Game.player)) {
			Game.player.life -= 5;
		}
	}

	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
