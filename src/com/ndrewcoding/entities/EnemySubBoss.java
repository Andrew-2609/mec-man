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

public class EnemySubBoss extends Enemy {

    private int frames = 0;
    private int index = 0;
    private final BufferedImage[] sprites;

    public EnemySubBoss(int x, int y, int width, int height, int speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);

        sprites = new BufferedImage[2];
        sprites[0] = Game.spritesheet.getSprite(64, 0, 16, 16);
        sprites[1] = Game.spritesheet.getSprite(80, 0, 16, 16);
    }

    public void tick() {
        depth = 1;

        if (!ghostMode) {
            if (path == null || path.size() == 0) {
                Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
                Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
                path = AStar.findPath(start, end);
                Sound.enemyMovingSound.play();
            }

            if (Entity.rand.nextInt(100) < 25) {
                new Random().nextInt(100);
                followPath(path);
            } else {
                if (new Random().nextInt(100) < 20) {
                    followPath(path);
                }
            }

            if (x % 16 == 0 && y % 16 == 0) {
                if (new Random().nextInt(100) < 50) {
                    Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
                    Vector2i end = new Vector2i(((int) (Game.player.x / 16)), ((int) (Game.player.y / 16)));
                    path = AStar.findPath(start, end);
                }
            }
        } else {
            if (path == null || path.size() == 0) {
                Vector2i start = new Vector2i(((int) (x / 16)), ((int) (y / 16)));
                Vector2i end = new Vector2i(Entity.rand.nextInt(Game.WIDTH * Game.SCALE) / 16,
                        Entity.rand.nextInt(Game.WIDTH * Game.SCALE) / 16);
                path = AStar.findPath(start, end);
                Sound.enemyMovingSound.play();
            }
        }

        if (ghostMode) {
            ghostFrames++;
            if (ghostFrames >= 140) {
                ghostMode = false;
                ghostFrames = 0;
            }
        }

        frames++;
        int maxFrames = 16;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            int maxIndex = 1;
            if (index > maxIndex) {
                index = 0;
                int cor = Entity.rand.nextInt(3);
                if (cor == 0) {
                    World.generateParticles(10, this.getX() + 8, this.getY() + 8, Color.ORANGE);
                } else if (cor == 1) {
                    World.generateParticles(10, this.getX() + 8, this.getY() + 8, Color.RED);
                } else {
                    World.generateParticles(10, this.getX() + 8, this.getY() + 8, Color.GREEN);
                }
            }
        }

        if (calculateDistance(this.getX(), this.getY(), Game.player.getX(), Game.player.getY()) > 48) {
            int dx;
            int dy;
            int px = 0;
            int py = 4;
            if (Entity.rand.nextInt(2) != 0) {
                px = 8;
                dx = Entity.rand.nextInt(17);
                dy = Entity.rand.nextInt(17);
            } else {
                dx = -Entity.rand.nextInt(17);
                dy = -Entity.rand.nextInt(17);
            }

            Infection infection = new Infection(this.getX() + px, this.getY() + py, 3, 3, null, dx, dy);
            Game.infections.add(infection);
        }

        if (Entity.isColliding(this, Game.player) && !ghostMode) {
            Game.player.life -= 5;
        }

    }

    public void render(Graphics g) {
        g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        if (ghostMode) {
            g.setColor(Color.yellow);
            g.fillRect(this.getX() - Camera.x - 2, this.getY() - 8 - Camera.y, 20, 5);
            g.setColor(Color.blue);
            g.fillRect(this.getX() - Camera.x - 2, this.getY() - 8 - Camera.y, ghostFrames / 7, 5);
        }
    }
}
