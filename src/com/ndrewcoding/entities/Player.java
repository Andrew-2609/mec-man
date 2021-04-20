package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.main.Sound;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.World;

public class Player extends Entity {

	public boolean right, up, left, down;
	public int lastDir = 1;

	public static int xInicial = 0, yInicial = 0;

	public boolean isDamaged = false;
	public boolean isHealing = false;
	public boolean isReborning = false;

	private int damageFrames = 0;
	private int healingFrames = 0;
	private int reborningFrames = 0;

	public BufferedImage[] sprite_right;
	public BufferedImage[] sprite_left;
	public BufferedImage[] sprite_up;
	public BufferedImage sprite_upParado;
	public BufferedImage[] sprite_down;
	public BufferedImage sprite_downParado;
	public BufferedImage[] sprite_reborning;
	public BufferedImage sprite_damage;
	public BufferedImage sprite_healing;
	public BufferedImage sprite_neutral;

	private int frames = 0, maxFrames = 6, index = 0, maxIndex = 3, indexReborning = 0, maxIndexReborning = 1;

	private int velocidadeAnterior;
	private int tempoEfeitoCafe = 240, framesIniciaisCafe = 0;
	private boolean moved = false;

	public int life = 200, maxLife = 200;

	public int trys = 2;

	public static int pontuacao = 0;

	// public int framesTeleporte = 500;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);

		sprite_right = new BufferedImage[4];
		sprite_left = new BufferedImage[4];
		sprite_up = new BufferedImage[4];
		sprite_down = new BufferedImage[4];
		sprite_reborning = new BufferedImage[2];
		for (int i = 0; i < 4; i++) {
			sprite_right[i] = Game.spritesheet.getSprite(96 - (i * 16), 64, 16, 16);
			sprite_left[i] = Game.spritesheet.getSprite(48 + (i * 16), 80, 16, 16);
			sprite_down[i] = Game.spritesheet.getSprite(112, 80 + (i * 16), 16, 16);
			sprite_up[i] = Game.spritesheet.getSprite(128, 80 + (i * 16), 16, 16);
			if (i > 1) {
				sprite_down[i] = Game.spritesheet.getSprite(112, 128 - (i * 16), 16, 16);
				sprite_up[i] = Game.spritesheet.getSprite(128, 128 - (i * 16), 16, 16);
			}
		}
		sprite_downParado = Game.spritesheet.getSprite(112, 64, 16, 16);
		sprite_upParado = Game.spritesheet.getSprite(128, 64, 16, 16);

		sprite_reborning[0] = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprite_reborning[1] = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_damage = Game.spritesheet.getSprite(48, 32, 16, 16);
		sprite_healing = Game.spritesheet.getSprite(64, 32, 16, 16);
	}

	public void tick() {
		depth = 2;

		if (pontuacao <= 0) {
			pontuacao = 0;
		}

		moved = false;

		if (right && World.isFree((int) (x + speed), this.getY())) {
			moved = true;
			x += speed;
			lastDir = 1;
		} else if (left && World.isFree((int) (x - speed), this.getY())) {
			moved = true;
			x -= speed;
			lastDir = -1;
		}
		if (up && World.isFree(this.getX(), (int) (y - speed))) {
			moved = true;
			lastDir = 2;
			y -= speed;
		} else if (down && World.isFree(this.getX(), (int) (y + speed))) {
			moved = true;
			lastDir = -2;
			y += speed;
		}

		if (framesIniciaisCafe != 0) {
			framesIniciaisCafe++;
			if (framesIniciaisCafe == tempoEfeitoCafe) {
				framesIniciaisCafe = 0;
				for (int i1 = 0; i1 < Game.entities.size(); i1++) {
					Entity e = Game.entities.get(i1);
					if (e instanceof Enemy) {
						((Enemy) e).velocidadeInimigo = velocidadeAnterior;
					}
				}
			}
		}

		if (moved) {
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				index++;
				if (index > maxIndex) {
					if (Game.CUR_LEVEL != 5) {
						Sound.playerMovingSound.play();
						index = 0;
					} else {
						Sound.snowFootstep.play();
						index = 0;
					}
				}
			}
		} else {
			Sound.snowFootstep.stop();
		}

		if (isReborning) {
			Game.player.setX(xInicial);
			Game.player.setY(yInicial);
			moved = false;
			left = false;
			right = false;
			up = false;
			down = false;
			frames++;
			if (frames == maxFrames) {
				frames = 0;
				indexReborning++;
				if (indexReborning > maxIndexReborning) {
					indexReborning = 0;
				}
			}
		}

		for (int i = 0; i < Game.entities.size(); i++) {
			Entity inimigo = Game.entities.get(i);
			if (inimigo instanceof Enemy || inimigo instanceof FinalBoss) {
				if (Entity.isColidding(this, inimigo) && Enemy.ghostMode == false) {
					levarDano();
				} else if (Entity.isColidding(this, inimigo) && Enemy.ghostMode == true) {
					life++;
					isHealing = true;
					if (life >= maxLife) {
						life = maxLife;
					}
				}
			}
		}

		for (int i = 0; i < Game.infection.size(); i++) {
			Infection infection = Game.infection.get(i);
			if (Entity.isColidding(this, infection)) {
				levarDano();
			}
		}

		if (isDamaged) {
			this.damageFrames++;
			if (this.damageFrames == 8) {
				Sound.playerHurtEffect.play();
				this.damageFrames = 0;
				isDamaged = false;
				pontuacao -= 2;
			}
		}

		if (isHealing) {
			this.healingFrames++;
			if (this.healingFrames == 8) {
				Sound.healingSound.play();
				this.healingFrames = 0;
				isHealing = false;
				pontuacao += 2;
			}
		}

		if (isReborning) {
			World.generateParticles(1, this.getX() + 8, this.getY() + 8, Color.red);
			this.reborningFrames++;
			Enemy.ghostMode = true;

			if (this.reborningFrames == 60 * 3) {
				this.reborningFrames = 0;
				isReborning = false;
				Enemy.ghostMode = false;
			}
		}

		/*
		 * // if (framesTeleporte != 500) { // framesTeleporte++; // } // // if
		 * (framesTeleporte == 500) { // framesTeleporte = 500; // }
		 */
		verificaPegaTrabalho();
		verificarPegaFeriado();
		verificarPegaCafe();
		verificarPegarFormatura();
		verificarNeve();

		updateCamera();

		// System.out.println("Velocidade Inimigo: " + Enemy.velocidadeInimigo);
		// System.out.println("Velocidade Anterior:" + velocidadeAnterior);
	}

	private void levarDano() {
		life--;
		isDamaged = true;
		if (life <= 0) {
			if (trys > 0) {
				trys--;
				life = 200;
				Player.pontuacao -= 600;
				isReborning = true;
				World.generateParticles(500, this.getX(), this.getY(), Color.ORANGE);
				if (trys == 0) {
					isReborning = false;
					Game.gameState = "GAME_OVER";
					Sound.gameOverMusic.loop();
				}
			}
		}
	}

	private void verificarPegaFeriado() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Feriado) {
				if (Entity.isColidding(this, current)) {
					Sound.calendarCatched.play();
					Game.entities.remove(i);
					Enemy.ghostMode = true;
					for (int i1 = 0; i1 < Game.entities.size(); i1++) {
						Entity e = Game.entities.get(i1);
						if (e instanceof Enemy) {
							((Enemy) e).ghostFrames = 0;
						}
					}
					pontuacao += 10;
					return;
				}
			}
		}
	}

	private void verificaPegaTrabalho() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Trabalho) {
				if (Entity.isColidding(this, current)) {
					Sound.paperCatched.play();
					Game.trabalhosAtual++;
					Game.entities.remove(i);
					pontuacao += 100;
					return;
				}
			}
		}
	}

	private void verificarPegaCafe() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof Coffee) {
				if (Entity.isColidding(this, current)) {
					Game.entities.remove(i);
					framesIniciaisCafe++;
					World.generateParticles(15, getX() + 8, getY() + 8, Color.orange);
					Sound.slowMotionSound.play();
					for (int i1 = 0; i1 < Game.entities.size(); i1++) {
						Entity e = Game.entities.get(i1);
						if (e instanceof Enemy) {
							if (((Enemy) e).velocidadeInimigo == 25) {
								velocidadeAnterior = 65;
							} else {
								velocidadeAnterior = ((Enemy) e).velocidadeInimigo;
							}
							((Enemy) e).velocidadeInimigo = 25;
						}
					}
					life -= 10;
					isDamaged = true;
					pontuacao -= 10;
					return;
				}
			}
		}
	}

	private void verificarNeve() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof SnowFloor) {
				if (Entity.isColidding(this, current)) {
					World.generateParticles(10, this.getX() + 8, this.getY() + 8, new Color(127, 255, 255));
					return;
				}
			}
		}
	}

	private void verificarPegarFormatura() {
		for (int i = 0; i < Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if (current instanceof GraduationHat) {
				if (Entity.isColidding(this, current)) {
					Sound.graduationCatched.play();
					Game.entities.remove(i);
					pontuacao += 600;
					Enemy.aumentarVelocidadeInimigo(1);
					return;
				}
			}
		}
	}

	/*
	 * public void teleporte() { if (framesTeleporte == 500) { if (left || right ||
	 * up || down && framesTeleporte == 0) { if (Entity.rand.nextInt(100) < 50) {
	 * Enemy enemy = new Enemy(Game.player.getX(), Game.player.getY(), 16, 16, 1,
	 * Entity.INIMIGO_1); Game.entities.add(enemy); Enemy.velocidadeInimigo += 10; }
	 * else { Enemy enemy = new Enemy(Game.player.getX(), Game.player.getY(), 16,
	 * 16, 1, Entity.INIMIGO_2); Game.entities.add(enemy); Enemy.velocidadeInimigo
	 * += 10; } } if (left) { if (World.isFree(Game.player.getX() - 50,
	 * Game.player.getY()) && Game.player.getX() - 50 > 0) {
	 * Game.player.setX(Game.player.getX() - 50); framesTeleporte = 0; } } else if
	 * (right) { if (World.isFree(Game.player.getX() + 50, Game.player.getY()) &&
	 * Game.player.getX() + 50 < Game.WIDTH * Game.SCALE) {
	 * Game.player.setX(Game.player.getX() + 50); framesTeleporte = 0; } } else if
	 * (up) { if (World.isFree(Game.player.getX(), Game.player.getY() - 50) &&
	 * Game.player.getY() - 50 > 0) { Game.player.setY(Game.player.getY() - 50);
	 * framesTeleporte = 0; } } else if (down) { if
	 * (World.isFree(Game.player.getX(), Game.player.getY() + 50) &&
	 * Game.player.getY() + 50 < Game.HEIGHT * Game.SCALE) {
	 * Game.player.setY(Game.player.getY() + 50); framesTeleporte = 0; } } } }
	 */

	public void render(Graphics g) {
		Game.player.speed = 1;
		if (isHealing) {
			g.drawImage(sprite_healing, this.getX() - Camera.x, this.getY() - Camera.y, null);
		} else if (isDamaged) {
			g.drawImage(sprite_damage, this.getX() - Camera.x, this.getY() - Camera.y, null);
			if (Game.CUR_LEVEL == 9) {
				Game.player.speed = 0.5;
			}
		}

		if (isReborning) {
			// System.out.println((this.getX() - Camera.x) + "," + (this.getY() -
			// Camera.y));
			g.drawImage(sprite_reborning[indexReborning], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}

		if (moved) {
			if (lastDir == 1) {
				g.drawImage(sprite_right[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (lastDir == -1) {
				g.drawImage(sprite_left[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (lastDir == 2) {
				g.drawImage(sprite_up[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (lastDir == -2) {
				g.drawImage(sprite_down[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else {
			if (lastDir == 1) {
				g.drawImage(sprite_right[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (lastDir == -1) {
				g.drawImage(sprite_left[0], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (lastDir == 2) {
				g.drawImage(sprite_upParado, this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else if (lastDir == -2) {
				g.drawImage(sprite_downParado, this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}
}
