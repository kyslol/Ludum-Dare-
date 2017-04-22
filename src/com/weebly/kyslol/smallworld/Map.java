package com.weebly.kyslol.smallworld;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Map {
	public static ArrayList<Integer> solid = new ArrayList<Integer>();

	public static int GRASS, PINK, WATER, WATER_DEEP, SAND, FLOORING, WALL, DOOR1, DOOR2, PATH;
	
	public static int size = 100;
	public static BufferedImage map = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
	public Map(){
		loadColours();
		try{
			map = ImageIO.read(Map.class.getResource("/textures/ground/map.png"));
		}catch(Exception e){
			System.out.println("Error loading: /textures/ground/map.png");
			System.exit(1);
		}
	}
	private void loadColours() {
		map.setRGB(0, 0, 0x00FF00);
		GRASS = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0xFF00FF);
		PINK = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0x00FFFF);
		WATER = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0x0000FF);
		WATER_DEEP = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0xFFFF00);
		SAND = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0x7A7A7A);
		FLOORING = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0x7A3900);
		WALL = map.getRGB(0, 0);
		
		map.setRGB(0, 0, 0x331600);
		DOOR1 = map.getRGB(0, 0);

		map.setRGB(0, 0, 0x3F1C00);
		DOOR2 = map.getRGB(0, 0);

		map.setRGB(0, 0, 0xEAEA00);
		PATH = map.getRGB(0, 0);
		
		solid.add(WATER);
		solid.add(WATER_DEEP);
		solid.add(WALL);
		
	}
}
