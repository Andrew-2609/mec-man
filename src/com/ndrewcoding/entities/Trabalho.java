package com.ndrewcoding.entities;

import java.awt.image.BufferedImage;

public class Trabalho extends Entity {

	public Trabalho(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		depth = 0;
	}

}
