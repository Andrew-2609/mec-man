package com.ndrewcoding.graphics;

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
			System.out.println("The was a problem while reading the spritesheet. Error: " + e.getMessage());
		}
	}
	
	public BufferedImage getSprite(int x,int y,int width,int height){
		return spritesheet.getSubimage(x, y, width, height);
	}
}
