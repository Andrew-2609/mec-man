package com.ndrewcoding.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;

public class FinalBoss extends Entity {

	private int frames = 0, maxFrames = 16, index = 0, maxIndex = 4;
	private BufferedImage sprites[];

	public FinalBoss(double x, double y, int width, int height, double speed, BufferedImage sprite) {
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
		if (frames == maxFrames) {
			frames = 0;
			index++;
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
		Game.mecSlime.add(mecSlime);

		if (Entity.isColidding(this, Game.player)) {
			Game.player.life -= 5;
		}
	}

	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
