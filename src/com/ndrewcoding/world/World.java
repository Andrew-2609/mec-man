package com.ndrewcoding.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import com.ndrewcoding.entities.Coffee;
import com.ndrewcoding.entities.Enemy;
import com.ndrewcoding.entities.EnemySubBoss;
import com.ndrewcoding.entities.Entity;
import com.ndrewcoding.entities.Holiday;
import com.ndrewcoding.entities.FinalBoss;
import com.ndrewcoding.entities.GraduationHat;
import com.ndrewcoding.entities.Particle;
import com.ndrewcoding.entities.Player;
import com.ndrewcoding.entities.SnowFloor;
import com.ndrewcoding.entities.Homework;
import com.ndrewcoding.graphics.Spritesheet;
import com.ndrewcoding.main.Game;

public class World {

    protected static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    private static final int TILE_SIZE = 16;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int xx = 0; xx < map.getWidth(); xx++) {
                for (int yy = 0; yy < map.getHeight(); yy++) {
                    int currentPixel = pixels[xx + (yy * map.getWidth())];
                    if (Game.CUR_LEVEL != 5) {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    } else {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Entity.SNOW_FLOOR_ITEM);
                    }
                    if (currentPixel == 0xFF000000) {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_FLOOR);
                    } else if (currentPixel == 0xFFFFFFFF) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL);
                    } else if (currentPixel == 0xFF7F0037) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL2);
                    } else if (currentPixel == 0xFF267F00) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL3);
                    } else if (currentPixel == 0xFFFFFB49) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL4);
                    } else if (currentPixel == 0xFF007F46) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL5);
                    } else if (currentPixel == 0xFFFF7FB6) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL6);
                    } else if (currentPixel == 0xFFFF6A00) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL7);
                    } else if (currentPixel == 0xFFB200FF) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL8);
                    } else if (currentPixel == 0xFF004A7F) {
                        tiles[xx + (yy * WIDTH)] = new WallTile(xx * 16, yy * 16, Tile.TILE_WALL9);
                    } else if (currentPixel == 0xFF0026FF) {
                        Game.player.setX(xx * 16);
                        Game.player.setY(yy * 16);
                        Player.xInitial = Game.player.getX();
                        Player.yInitial = Game.player.getY();
                    } else if (currentPixel == 0xFF808080) {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL2);
                    } else if (currentPixel == 0xFFAFFFC5) {
                        tiles[xx + (yy * WIDTH)] = new FloorTile(xx * 16, yy * 16, Tile.TILE_WALL8);
                    } else if (currentPixel == 0xFFFFD800) {
                        Homework homework = new Homework(xx * 16, yy * 16, 16, 16, 0, Entity.HOMEWORK_SPRITE);
                        Game.entities.add(homework);
                        Game.homeworksCount++;
                    } else if (currentPixel == 0xFFFF0000) {
                        Enemy enemy = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.ENEMY_1);
                        Game.enemies.add(enemy);
                    } else if (currentPixel == 0xFF4CFF00) {
                        Enemy enemy2 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.ENEMY_2);
                        Game.enemies.add(enemy2);
                    } else if (currentPixel == 0xFFC1DBF7) {
                        Enemy enemy3 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.ENEMY_3);
                        Game.enemies.add(enemy3);
                    } else if (currentPixel == 0xFF0076BF) {
                        Enemy enemy4 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.ENEMY_4);
                        Game.enemies.add(enemy4);
                    } else if (currentPixel == 0xFF3A4651) {
                        Enemy enemy5 = new Enemy(xx * 16, yy * 16, 16, 16, 1, Entity.ENEMY_5);
                        Game.enemies.add(enemy5);
                    } else if (currentPixel == 0xFF754FFF) {
                        Enemy subboss = new EnemySubBoss(xx * 16, yy * 16, 16, 16, 1, Entity.SUB_BOSS);
                        Game.enemies.add(subboss);
                    } else if (currentPixel == 0xFFE74C3C) {
                        Holiday holiday = new Holiday(xx * 16, yy * 16, 16, 16, 0, Entity.HOLIDAY_SPRITE);
                        Game.entities.add(holiday);
                    } else if (currentPixel == 0xFFFEE4CF) {
                        Coffee coffee = new Coffee(xx * 16, yy * 16, 16, 16, 0, Entity.COFFEE_SPRITE);
                        Game.entities.add(coffee);
                    } else if (currentPixel == 0xFFF1E0FF) {
                        GraduationHat graduationHat = new GraduationHat(xx * 16, yy * 16, 16, 16, 0,
                                Entity.GRADUATION_SPRITE);
                        Game.entities.add(graduationHat);
                    } else if (currentPixel == 0xFF7FFFFF) {
                        SnowFloor snowFloor = new SnowFloor(xx * 16, yy * 16, 16, 16, 0, Entity.SNOW_FLOOR);
                        Game.entities.add(snowFloor);
                    } else if (currentPixel == 0xFF7F3300) {
                        Enemy fb = new FinalBoss(xx * 16, yy * 16, 48, 48, 1, Entity.FINAL_BOSS);
                        Game.enemies.add(fb);
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

    public static boolean isFree(int xNext, int yNext) {
        return coordinateHandler(xNext, yNext, TILE_SIZE, TILE_SIZE);
    }

    public static boolean isFreeDynamic(int xNext, int yNext, int width, int height) {
        return coordinateHandler(xNext, yNext, width, height);
    }

    public static void restartGame(String level) {
        Game.entities.clear();
        Game.enemies.clear();
        Game.infections.clear();
        Game.spritesheet = new Spritesheet("/spritesheet.png");
        Game.player = new Player(0, 0, 16, 16, 1, Game.spritesheet.getSprite(32, 0, 16, 16), "normal");
        Game.entities.add(Game.player);
        Game.currentHomeworks = 0;
        Game.homeworksCount = 0;
        Game.world = new World("/" + level);
        if (Game.CUR_LEVEL > 3) {
            Game.checkCurrentLevelToSetRespectiveMusic();
        }
    }

    private static boolean coordinateHandler(int xNext, int yNext, int tileSize, int tileSize2) {
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + tileSize - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + tileSize2 - 1) / TILE_SIZE;

        int x4 = (xNext + tileSize - 1) / TILE_SIZE;
        int y4 = (yNext + tileSize2 - 1) / TILE_SIZE;

        return !(tiles[x1 + (y1 * World.WIDTH)] instanceof WallTile
                || tiles[x2 + (y2 * World.WIDTH)] instanceof WallTile
                || tiles[x3 + (y3 * World.WIDTH)] instanceof WallTile
                || tiles[x4 + (y4 * World.WIDTH)] instanceof WallTile);
    }

    public void render(Graphics g) {
        int xStart = Camera.x >> 4;
        int yStart = Camera.y >> 4;

        int xFinal = xStart + (Game.WIDTH >> 4);
        int yFinal = yStart + (Game.HEIGHT >> 4);

        for (int xx = xStart; xx <= xFinal; xx++) {
            for (int yy = yStart; yy <= yFinal; yy++) {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
                    continue;
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }

}
