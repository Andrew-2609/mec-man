package com.ndrewcoding.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;

public class Tile {

    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
    public static BufferedImage TILE_WALL2 = Game.spritesheet.getSprite(0, 48, 16, 16);
    public static BufferedImage TILE_WALL3 = Game.spritesheet.getSprite(16, 48, 16, 16);
    public static BufferedImage TILE_WALL4 = Game.spritesheet.getSprite(32, 48, 16, 16);
    public static BufferedImage TILE_WALL5 = Game.spritesheet.getSprite(48, 48, 16, 16);
    public static BufferedImage TILE_WALL6 = Game.spritesheet.getSprite(64, 48, 16, 16);
    public static BufferedImage TILE_WALL7 = Game.spritesheet.getSprite(80, 48, 16, 16);
    public static BufferedImage TILE_WALL8 = Game.spritesheet.getSprite(96, 48, 16, 16);
    public static BufferedImage TILE_WALL9 = Game.spritesheet.getSprite(112, 48, 16, 16);

    private final BufferedImage sprite;
    protected int x;
    protected int y;

    public Tile(int x, int y, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
        if (Game.CUR_LEVEL == 7) {
            World.generateParticles(1, x, y, Color.red);
        }
    }

}
