package com.ndrewcoding.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ndrewcoding.main.Game;
import com.ndrewcoding.world.Camera;

public class Coffee extends Entity {

    public BufferedImage[] coffeeSprite;

    private int frames = 0;
    private int index = 0;

    public Coffee(double x, double y, int width, int height, double speed, BufferedImage sprite) {
        super(x, y, width, height, speed, sprite);
        depth = 0;

        coffeeSprite = new BufferedImage[6];

        for (int i = 0; i < 5; i++) {
            coffeeSprite[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    public void tick() {
        frames++;
        int maxFrames = 15;
        if (frames == maxFrames) {
            frames = 0;
            index++;
            int maxIndex = 1;
            if (index > maxIndex) {
                index = 0;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(coffeeSprite[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
    }
}
