package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.World;

public class SnowFloor extends Entity {

	private BufferedImage neveNormal;
	private BufferedImage nevePisada;
	private boolean melted = false;
	private int framesMelted = 0;

	public SnowFloor(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		depth = 0;

		neveNormal = Game.spritesheet.getSprite(0, 64, 16, 16);
		nevePisada = Game.spritesheet.getSprite(16, 64, 16, 16);
	}

	public void tick() {
		if (this.melted == true) {
			framesMelted++;
			if (framesMelted == 60 * 10) {
				this.melted = false;
				framesMelted = 0;
			}
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if (e instanceof Enemy) {
				if (calculateDistance(this.getX(), this.getY(), e.getX(), e.getY()) < 9) {
					g.drawImage(nevePisada, this.getX() - Camera.x, this.getY() - Camera.y, null);
					this.melted = true;
					World.generateParticles(10, e.getX() + 8, e.getY() + 8, Color.white);
				} else {
					if (this.melted == false) {
						g.drawImage(neveNormal, this.getX() - Camera.x, this.getY() - Camera.y, null);
					} else {
						g.drawImage(nevePisada, this.getX() - Camera.x, this.getY() - Camera.y, null);
					}
				}
			}
		}

		if (calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < 9) {
			g.drawImage(nevePisada, this.getX() - Camera.x, this.getY() - Camera.y, null);
			this.melted = true;
		} else {
			if (this.melted == false) {
				g.drawImage(neveNormal, this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else {
				g.drawImage(nevePisada, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}
}
