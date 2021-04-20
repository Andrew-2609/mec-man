package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.main.Sound;
import com.ndrewcoding.world.AStar;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.Vector2i;
import com.ndrewcoding.world.World;

public class Enemy extends Entity {

	public static boolean ghostMode = false;
	public int ghostFrames = 0;
	public static int velocidadeEstatica = 65;
	public int velocidadeInimigo = velocidadeEstatica;
	public static int rangeInimigo = 180;

	public BufferedImage sprite_ghost1;

	public Enemy(int x, int y, int width, int height, int speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		sprite_ghost1 = Game.spritesheet.getSprite(32, 32, 16, 16);
	}

	public void tick() {
		depth = 1;
		if (ghostMode == false) {
			if (calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) < rangeInimigo) {
				if (path == null || path.size() == 0) {
					Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
					Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
					path = AStar.findPath(Game.world, start, end);
				}

				if (new Random().nextInt(100) < velocidadeInimigo)
					followPath(path);

				if (x % 16 == 0 && y % 16 == 0) {
					if (new Random().nextInt(100) < 50) {
						Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
						Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
						path = AStar.findPath(Game.world, start, end);
						if (new Random().nextInt(100) < 5) {
							World.generateParticles(3, this.getX() + 8, this.getY() + 9, Color.red);
							Sound.enemyMovingSound.play();
						}
					}
				}
			} else {
				if (path == null || path.size() == 0) {
					Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
					Vector2i end = new Vector2i(((int) (Entity.rand.nextInt(Game.WIDTH * Game.SCALE) / 16)),
							((int) (Entity.rand.nextInt(Game.WIDTH * Game.SCALE) / 16)));
					path = AStar.findPath(Game.world, start, end);
				}
			}
		}

		if (ghostMode == true) {
			ghostFrames++;
			if (ghostFrames >= 140) {
				ghostMode = false;
				ghostFrames = 0;
			}
		}

		System.out.println(velocidadeInimigo);
//		System.out.println(World.velocidadeNova);

	}

	public static void aumentarVelocidadeInimigo(int velocidade) {
		velocidadeEstatica += velocidade;
		for (int i1 = 0; i1 < Game.entities.size(); i1++) {
			Entity en = Game.entities.get(i1);
			if (en instanceof Enemy) {
				((Enemy) en).velocidadeInimigo = velocidadeEstatica;
			}
		}
		
	}

	public void render(Graphics g) {
		super.render(g);
		if (ghostMode) {
			g.drawImage(sprite_ghost1, this.getX() - Camera.x, this.getY() - Camera.y, null);
			g.setColor(Color.yellow);
			g.fillRect(this.getX() - Camera.x - 2, this.getY() - 8 - Camera.y, 20, 5);
			g.setColor(Color.blue);
			g.fillRect(this.getX() - Camera.x - 2, this.getY() - 8 - Camera.y, ghostFrames / 7, 5);
		}
	}
}
