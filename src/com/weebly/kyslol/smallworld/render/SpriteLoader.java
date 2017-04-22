package com.weebly.kyslol.smallworld.render;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SpriteLoader {
	public static ImageData GroundSprites = loadImage("/textures/ground/sprites.png");
	public static final int GROUNDSIZE = 32, GROUNDLEN = 10;
	
//	public static ImageData MAP = loadImage("/textures/ground/map.png");
//	public static final int MAPSIZE = 100;
	
	
//pixels[xx + ((int)yy+i) * width] =  LoadSprites.SpriteSheet.pixels[((xPix & size-1) + sprx * size) + ((yPix & size-1) + spry * size) * (size*len)];

	public static ImageData loadImage(String fileName){
		try{
			BufferedImage image = ImageIO.read(SpriteLoader.class.getResource(fileName));
			int width = image.getWidth();
			int height = image.getHeight();
			ImageData result = new ImageData(width, height);
			
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		
		} catch (Exception e) {
			System.out.println("Error loading: " + fileName);
			return null;
		}
	}
}
