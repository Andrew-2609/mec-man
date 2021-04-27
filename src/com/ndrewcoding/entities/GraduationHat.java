package com.ndrewcoding.entities;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.ndrewcoding.world.World;

public class GraduationHat extends Entity {

    public GraduationHat(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        depth = 0;
    }

    public void tick() {
        World.generateParticles(1, (int) x + 8, (int) y + 8, Color.yellow);
    }

}
