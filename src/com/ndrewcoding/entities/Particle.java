package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;

public class Particle extends Entity {

    public int lifeTime = 15;
    public int curLife = 0;

    public double dx;
    public double dy;

    private final Color color;

    public Particle(double x, double y, int width, int height, double speed, BufferedImage sprite, Color color) {
        super(x, y, width, height, speed, sprite);
        depth = -1;
        this.color = color;
        dx = new Random().nextGaussian();
        dy = new Random().nextGaussian();
    }

    public void tick() {
        x += dx * speed;
        y += dy * speed;

        curLife++;
        if (lifeTime == curLife) {
            Game.entities.remove(this);
        }
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(this.getX() - Camera.x, this.getY() - Camera.y, width, height);
    }

}
