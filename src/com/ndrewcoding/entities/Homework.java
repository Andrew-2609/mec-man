package com.ndrewcoding.entities;

import java.awt.image.BufferedImage;

public class Homework extends Entity {
    
    public Homework(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        depth = 0;
    }

}
