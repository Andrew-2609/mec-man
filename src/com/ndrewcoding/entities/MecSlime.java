package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.World;

public class MecSlime extends Entity {

	private double dx;
	private double dy;

	private int life = 600, curLife = 0;

	public MecSlime(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, 1, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public void tick() {
		if (World.isFreeDynamic((int) (x + (dx)), (int) (y + (dy)), 3, 3)) {
			x += dx;
			y += dy;
		} else {
			Game.mecSlime.remove(this);
			World.generateParticles(1, (int) x, (int) y, Color.white);
			int random = Entity.rand.nextInt(100);
			int random2 = Entity.rand.nextInt(100);
			System.out.println(random + "," + random2);
			if (random > 90 && random2 < 5) {
				Enemy e = new Enemy((int) x, (int) y, 16, 16, 1, Entity.INIMIGO_SLIME);
				Game.entities.add(e);
			}
			return;
		}
		curLife++;
		if (curLife == life) {
			Game.mecSlime.remove(this);
			return;
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
	}
}
