package com.weebly.kyslol.smallworld.mobs;

import java.util.ArrayList;

import com.weebly.kyslol.smallworld.Inputs;
import com.weebly.kyslol.smallworld._INIT_;
import com.weebly.kyslol.smallworld.player.Player;

public class Bullet {
	double x, y, dir, dist, totdist;
	public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public Bullet() {
		totdist = 0;
		x = Player.x + Player.renX * 32 - 16;
		y = Player.y + Player.renY * 32 - 16;
		double mx = (Inputs.mx - _INIT_.WIDTH/2);// + Player.x + Player.renX; //TODO _INIT_
		double my = (Inputs.my - _INIT_.HEIGHT/2);// + Player.y + Player.renY;
		// my *= 32;
		// mx *= 32;

		if (my > 0) {
			dist = 0.5;
		} else {
			dist = -0.5;
		}

		double xc = mx - x / 32;
		double yc = my - y / 32;
		// if(yc <= -9) yc = -8.5;
		dir = yc / xc;
		// if(dir < 0.001 && dir > -0.05) dir = 0.001;
		// if(dir < 0.001 && dir > -0.1) dir = -0.1;
//		System.out.println(dir + " " + yc + " " + xc);

		bullets.add(this);
	}

	public void tick() {
		// if (dir <= 1 && dir >= -1) {

		if (dir < 0) {
			double speed = 0.12293;
			if (dir > -0.5)
				speed *= 3;
			// if(dir > -0.4) speed *= 3;
			// if(dir > -0.3) speed *= 3;
			// if(dir > -0.2) speed *= 3;
			if (dir > -0.1)
				speed *= 3;

			y += dist * (6 / speed);
			double yc = dist / dir * (6 / speed);
			x += yc;

		} else {
			double speed = 0.12293;
			if (dir > 2)
				speed *= 3;
			if (dir > 5)
				speed *= 3;
			// if(dir > 10) speed *= 3;
			if (dir > 50)
				speed *= 3;
			if (dir > 15)
				speed *= 3;
			x += dist * (6 / speed);
			double yc = (dir * dist) * (6 / speed);

			y += yc;

		}

		// }else{
		// y += dist;
		// double yc = dist/dir;
		// x += yc;
		//
		//// y += dist;
		//// x = y / dir;
		// }

		for(int i = 0; i < Zombie.zombies.size(); i++){
			Zombie z = Zombie.zombies.get(i);
			double zx = z.getX();
			double zy = z.getY();
			
			if(zy +32 > y/32 && zy - 32 < y/32 && zx + 32 > x/32 && zx - 32 < x/32){
				z.setHealth(0);
				System.out.println(z.getHealth());
			}else{
//				System.out.println(x + " " + y );
			}
			
			
		}
		
		totdist += 0.5;
		if (totdist > 10) {
			bullets.remove(this);
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDir() {
		return dir;
	}

	public double getDist() {
		return dist;
	}

}
