package com.weebly.kyslol.smallworld.mobs;

import java.util.ArrayList;
import java.util.Random;

public class Food {
	public static ArrayList<Food> food = new ArrayList<Food>();
	int x, y;
	Random random = new Random();
	
	public Food(){
		food.add(this);
		x = random.nextInt(63 - 18) + 18;
		y = random.nextInt(81 - 16) + 16;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
