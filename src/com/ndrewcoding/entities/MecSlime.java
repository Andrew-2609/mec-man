package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;
import com.ndrewcoding.world.World;

public class MecSlime extends Entity {

    private final double dx;
    private final double dy;

    private int curLife = 0;

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
            Game.mecSlimes.remove(this);
            World.generateParticles(1, (int) x, (int) y, Color.white);
            int random = Entity.rand.nextInt(100);
            int random2 = Entity.rand.nextInt(100);
            System.out.println(random + "," + random2);
            if (random > 90 && random2 < 5) {
                Enemy e = new Enemy((int) x, (int) y, 16, 16, 1, Entity.ENEMY_SLIME_SPRITE);
                Game.entities.add(e);
            }
            return;
        }
        curLife++;
        int life = 600;
        if (curLife == life) {
            Game.mecSlimes.remove(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
    }
}
