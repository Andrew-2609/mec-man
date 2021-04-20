package com.ndrewcoding.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ndrewcoding.entities.Coffee;
import com.ndrewcoding.entities.Enemy;
import com.ndrewcoding.entities.EnemySubBoss;
import com.ndrewcoding.entities.Entity;
import com.ndrewcoding.entities.Feriado;
import com.ndrewcoding.entities.FinalBoss;
import com.ndrewcoding.entities.GraduationHat;
import com.ndrewcoding.entities.Particle;
import com.ndrewcoding.entities.Player;
import com.ndrewcoding.entities.SnowFloor;
import com.ndrewcoding.entities.Trabalho;
import com.ndrewcoding.graficos.Spritesheet;
import com.ndrewcoding.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;

	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					if (Game.CUR_LEVEL != 5) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					} else {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.SNOW_FLOOR_ITEM);
					}
					if (pixelAtual == 0xFF000000) {
						// Floor
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
					} else if (pixelAtual == 0xFFFFFFFF) {
						// Parede
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
					} else if (pixelAtual == 0xFF7F0037) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL2);
					} else if (pixelAtual == 0xFF267F00) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL3);
					} else if (pixelAtual == 0xFFFFFB49) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL4);
					} else if (pixelAtual == 0xFF007F46) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL5);
					} else if (pixelAtual == 0xFFFF7FB6) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL6);
					} else if (pixelAtual == 0xFFFF6A00) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL7);
					} else if (pixelAtual == 0xFFB200FF) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL8);
					} else if (pixelAtual == 0xFF004A7F) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL9);
					} else if (pixelAtual == 0xFF0026FF) {
						// Player
						Game.player.setX(xx * 16);
						Game.player.setY(yy * 16);
						Player.xInicial = Game.player.getX();
						Player.yInicial = Game.player.getY();
					} else if (pixelAtual == 0xFF808080) {
						// Instancia a parede invisível
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL2);
					} else if (pixelAtual == 0xFFAFFFC5) {
						// Instancia a parede invisível 2
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL8);
					} else if (pixelAtual == 0xFFFFD800) {
						// Instancia o item de pontuação
						Trabalho trabalho = new Trabalho(xx * 16, yy * 16, 16, 16, 0, Entity.TRABALHO_SPRITE);
						Game.entities.add(trabalho);
						Game.trabalhosContagem++;
					} else if (pixelAtual == 0xFFFF0000) {
						// Instanciar inimigo 1 e adicionar a lista das entities
						Enemy enemy = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.INIMIGO_1);
						Game.entities.add(enemy);
					} else if (pixelAtual == 0xFF4CFF00) {
						// Instanciar inimigo 2 e adicionar a lista das entities
						Enemy enemy2 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.INIMIGO_2);
						Game.entities.add(enemy2);
					} else if (pixelAtual == 0xFFC1DBF7) {
						Enemy enemy3 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.INIMIGO_3);
						Game.entities.add(enemy3);
					} else if (pixelAtual == 0xFF0076BF) {
						Enemy enemy4 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.INIMIGO_4);
						Game.entities.add(enemy4);
					} else if (pixelAtual == 0xFF3A4651) {
						Enemy enemy5 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.INIMIGO_5);
						Game.entities.add(enemy5);
					} else if (pixelAtual == 0xFF754FFF) {
						Enemy subboss = new EnemySubBoss(xx * 16, yy * 16, 16, 16, 1, Entity.SUB_BOSS);
						Game.entities.add(subboss);
					} else if (pixelAtual == 0xFFE74C3C) {
						// Instancia o item de Feriado
						Feriado feriado = new Feriado(xx * 16, yy * 16, 16, 16, 0, Entity.FERIADO_SPRITE);
						Game.entities.add(feriado);
					} else if (pixelAtual == 0xFFFEE4CF) {
						// Instancia o item do Café
						Coffee coffee = new Coffee(xx * 16, yy * 16, 16, 16, 0, Entity.COFFEE_SPRITE);
						Game.entities.add(coffee);
					} else if (pixelAtual == 0xFFF1E0FF) {
						GraduationHat graduationHat = new GraduationHat(xx * 16, yy * 16, 16, 16, 0,
								Entity.GRADUATION_SPRITE);
						Game.entities.add(graduationHat);
					} else if (pixelAtual == 0xFF7FFFFF) {
						SnowFloor snowFloor = new SnowFloor(xx * 16, yy * 16, 16, 16, 0, Entity.SNOW_FLOOR);
						Game.entities.add(snowFloor);
					} else if (pixelAtual == 0xFF7F3300) {
						Entity fb = new FinalBoss(xx * 16, yy * 16, 48, 48, 1, Entity.FINAL_BOSS);
						Game.entities.add(fb);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void generateParticles(int amount, int x, int y, Color color) {
		for (int i = 0; i < amount; i++) {
			Game.entities.add(new Particle(x, y, 1, 1, 1, null, color));
		}
	}

	public static boolean isFree(int xnext, int ynext) {

		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		int x4 = (xnext + TILE_SIZE - 1) / TILE_SIZE;
		int y4 = (ynext + TILE_SIZE - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile));
	}

	public static boolean isFreeDynamic(int xnext, int ynext, int width, int height) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;

		int x2 = (xnext + width - 1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;

		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext + height - 1) / TILE_SIZE;

		int x4 = (xnext + width - 1) / TILE_SIZE;
		int y4 = (ynext + height - 1) / TILE_SIZE;

		return !((tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile)
				|| (tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile)
				|| tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile
				|| tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile);
	}

	public static void restartGame(String level) {
		Game.entities.clear();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0, 0, 16, 16, 1, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.trabalhosAtual = 0;
		Game.trabalhosContagem = 0;
		Game.world = new World("/" + level);
		if (Game.CUR_LEVEL > 3) {
			Game.checarLevelSong();
		}
	}

	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;

		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);

		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}

}
