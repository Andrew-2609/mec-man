package com.ndrewcoding.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.Node;
import com.ndrewcoding.world.Vector2i;
import com.ndrewcoding.world.World;

public abstract class Entity {

    public static BufferedImage HOMEWORK_SPRITE = Game.spritesheet.getSprite(0, 16, 16, 16);
    public static BufferedImage HOLIDAY_SPRITE = Game.spritesheet.getSprite(16, 16, 16, 16);
    public static BufferedImage COFFEE_SPRITE = Game.spritesheet.getSprite(64, 16, 16, 16);
    public static BufferedImage SNOW_FLOOR = Game.spritesheet.getSprite(0, 64, 16, 16);
    public static BufferedImage SNOW_FLOOR_ITEM = Game.spritesheet.getSprite(32, 64, 16, 16);
    public static BufferedImage GRADUATION_SPRITE = Game.spritesheet.getSprite(80, 32, 16, 16);
    public static BufferedImage ENEMY_1 = Game.spritesheet.getSprite(0, 32, 16, 16);
    public static BufferedImage ENEMY_2 = Game.spritesheet.getSprite(16, 32, 16, 16);
    public static BufferedImage ENEMY_3 = Game.spritesheet.getSprite(112, 32, 16, 16);
    public static BufferedImage ENEMY_4 = Game.spritesheet.getSprite(128, 32, 16, 16);
    public static BufferedImage ENEMY_5 = Game.spritesheet.getSprite(144, 32, 16, 16);
    public static BufferedImage SUB_BOSS = Game.spritesheet.getSprite(64, 0, 16, 16);
    public static BufferedImage FINAL_BOSS = Game.spritesheet.getSprite(160, 0, 48, 48);
    public static BufferedImage ENEMY_SLIME_SPRITE = Game.spritesheet.getSprite(96, 32, 16, 16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected double speed;

    public int depth;

    protected List<Node> path;

    private final BufferedImage sprite;

    public static Random rand = new Random();

    public Entity(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public static Comparator<Entity> nodeSorter = Comparator.comparingInt(n0 -> n0.depth);

    public void updateCamera() {
        Camera.x = Camera.clamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    public int getX() {
        return (int) this.x;
    }

    public int getY() {
        return (int) this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void tick() {
    }

    public double calculateDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public void followPath(List<Node> path) {
        if (path != null) {
            if (path.size() > 0) {
                Vector2i target = path.get(path.size() - 1).tile;
                if (x < target.x * 16) {
                    x++;
                } else if (x > target.x * 16) {
                    x--;
                }

                if (y < target.y * 16) {
                    y++;
                } else if (y > target.y * 16) {
                    y--;
                }

                if (x == target.x * 16 && y == target.y * 16) {
                    path.remove(path.size() - 1);
                }

            }
        }
    }

    public static boolean isColliding(Entity e1, Entity e2) {
        Rectangle e1Mask = new Rectangle(e1.getX(), e1.getY(), e1.getWidth(), e1.getHeight());
        Rectangle e2Mask = new Rectangle(e2.getX(), e2.getY(), e2.getWidth(), e2.getHeight());

        return e1Mask.intersects(e2Mask);
    }

    public void render(Graphics g) {
        g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
    }

}
