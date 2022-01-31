package com.ndrewcoding.entities;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.main.Sound;

import java.awt.image.BufferedImage;

public class Holiday extends Entity implements Collectible {

    public Holiday(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        depth = 0;
    }

    @Override
    public void beCaught() {
        if (isColliding(this, Game.player)) {
            Sound.calendarCaught.play();
            Game.entities.remove(this);
            Enemy.ghostMode = true;
            for (int i = 0; i < Game.enemies.size(); i++) {
                Enemy e = Game.enemies.get(i);
                e.ghostFrames = 0;
            }
            Player.addScore(10);
        }
    }

    @Override
    public void tick() {
        beCaught();
    }

}
