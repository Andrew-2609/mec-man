package com.ndrewcoding.entities;

import java.awt.image.BufferedImage;

public class Feriado extends Entity {

	public Feriado(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		depth = 0;
	}

}
