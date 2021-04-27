package com.ndrewcoding.grafics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage spritesheet;
	
	public Spritesheet(String path)
	{
		try {
			spritesheet = ImageIO.read(Objects.requireNonNull(getClass().getResource(path), "The spritesheet cannot be null!!"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int x,int y,int width,int height){
		return spritesheet.getSubimage(x, y, width, height);
	}
}
