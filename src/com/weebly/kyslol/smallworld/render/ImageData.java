package com.weebly.kyslol.smallworld.render;

public class ImageData {
	public final int width;
	public final int height;
	public final int[] pixels;

	public ImageData(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
}
