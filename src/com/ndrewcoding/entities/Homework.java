package com.ndrewcoding.entities;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.main.Sound;

import java.awt.image.BufferedImage;

public class Homework extends Entity implements Collectible {
    
    public Homework(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        depth = 0;
    }

    @Override
    public void beCaught() {
        if (isColliding(this, Game.player)) {
            Sound.paperCaught.play();
            Game.currentHomeworks++;
            Game.entities.remove(this);
            Player.addScore(100);
        }
    }

    @Override
    public void tick() {
        beCaught();
    }

}
