package com.weebly.kyslol.smallworld.mobs;

import java.util.ArrayList;
import java.util.Random;

import com.weebly.kyslol.smallworld.Map;
import com.weebly.kyslol.smallworld._INIT_;
import com.weebly.kyslol.smallworld.player.Child;
import com.weebly.kyslol.smallworld.player.Player;
import com.weebly.kyslol.smallworld.player.Wife;

public class Zombie {
	public static ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	Random random = new Random();
	private int health, dam;
	private double x, y;
	private char dir = 'U';

	public Zombie() {
		zombies.add(this);
		health = 20;
		x = random.nextInt(63 - 18) + 18;
		y = random.nextInt(81 - 16) + 16;
		dam = 1;
	}

	public void tick() {
		if(health <= 0){
			zombies.remove(this);
		}

		if (Player.x + Player.renX > x) {
			dir = 'R';
			x += 0.06;
			if (check()) {
				x -= 0.06;
			}
		} else if (Player.x + Player.renX < x) {
			dir = 'L';
			x -= 0.06;
			if (check()) {
				x += 0.06;
			}
		}
		if (Player.y + Player.renY > y) {
			dir = 'D';
			y += 0.06;
			if (check()) {
				y -= 0.06;
			}
		} else if (Player.y + Player.renY < y) {
			dir = 'U';
			y -= 0.06;
			if (check()) {
				y += 0.06;
			}
		}

		// Loop.time ++;
		// System.out.println(x + " " + y);
		if (inRange()) {
			if (random.nextInt(40) == 0) {
				Player.health -= dam;
			}
		}
	}

	public char getDir() {
		return dir;
	}

	private boolean inRange() {
		if (Player.y + Player.renY + 2 > y && Player.y + Player.renY - 2 < y && Player.x + Player.renX + 2 > x
				&& Player.x + Player.renX - 2 < x) {
			return true;
		}
		return false;
	}

	private boolean check() {
		int rgb = Map.map.getRGB((int) (x), (int) (y));
		if (Map.solid.contains(rgb) || Map.DOOR2 == rgb) {
			return true;
		}
		rgb = Map.map.getRGB((int) (x + 0.5), (int) (y + 0.5));
		if (Map.solid.contains(rgb) || Map.DOOR2 == rgb) {
			return true;
		}
		rgb = Map.map.getRGB((int) (x + 0.5), (int) (y));
		if (Map.solid.contains(rgb) || Map.DOOR2 == rgb) {
			return true;
		}
		rgb = Map.map.getRGB((int) (x), (int) (y + 0.5));
		if (Map.solid.contains(rgb) || Map.DOOR2 == rgb) {
			return true;
		}
		for (int i = 0; i < Zombie.zombies.size(); i++) {
			Zombie z = Zombie.zombies.get(i);
			if(z == this) continue;
			double zx = z.getX();
			double zy = z.getY();

			if (zy + 1 > y && zy - 1 < y && zx + 1 > x && zx - 1 < x) {
				return true;
			}

		}

		return false;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getDam() {
		return dam;
	}
}
