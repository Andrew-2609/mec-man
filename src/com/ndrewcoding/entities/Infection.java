package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.World;

public class Infection extends Entity {

	private double dx;
	private double dy;

	private int life = 50, curLife = 0;

	public Infection(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy) {
		super(x, y, width, height, 1, sprite);
		this.dx = dx;
		this.dy = dy;
	}

	public void tick() {
		if (World.isFreeDynamic((int) (x + (dx)), (int) (y + (dy)), 3, 3)) {
			x += dx;
			y += dy;
		} else {
			Game.infection.remove(this);
			World.generateParticles(1, (int) x, (int) y, Color.white);
			return;
		}
		curLife++;
		if (curLife == life) {
			Game.infection.remove(this);
			return;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
	}
}
