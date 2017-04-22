package com.weebly.kyslol.smallworld.player;

import com.weebly.kyslol.smallworld.Inputs;
import com.weebly.kyslol.smallworld.Loop;
import com.weebly.kyslol.smallworld.Map;
import com.weebly.kyslol.smallworld._INIT_;

public class Player {
	public static double x = 38 - _INIT_.WIDTH / 2 / 32, y = 49 - _INIT_.HEIGHT / 2 / 32;
	public static int renX = _INIT_.WIDTH / 2 / 32, renY = _INIT_.HEIGHT / 2 / 32;
	public static int health = 40;
	public static String end = "Lived!";
	
	public static void tick() {
		if(health <= 0){
			end = "Died :(";
			x = Child.x;
			y = Child.y;
//			Loop.cutscene = true;
			Loop.time = 0;
			return;
		}
		
		if (Inputs.f) {
			y -= 0.125;
			if (check()) {
				y += 0.125;
			}

		}
		if (Inputs.b) {
			y += 0.125;
			if (check()) {
				y -= 0.125;
			}

		}
		if (Inputs.l) {
			x -= 0.125;
			if (check()) {
				x += 0.125;
			}

		}
		if (Inputs.r) {
			x += 0.125;
			if (check()) {
				x -= 0.125;
			}
		}
		// System.out.println(x + " " + y + " " + Inputs.f);
	}

	private static boolean check() {
		int rgb = Map.map.getRGB((int) (renX + x), (int) (renY + y));
		if (Map.solid.contains(rgb)) {
			return true;
		}
		rgb = Map.map.getRGB((int) (renX + x + 0.5), (int) (renY + y + 0.5));
		if (Map.solid.contains(rgb)) {
			return true;
		}
		rgb = Map.map.getRGB((int) (renX + x + 0.5), (int) (renY + y));
		if (Map.solid.contains(rgb)) {
			return true;
		}
		rgb = Map.map.getRGB((int) (renX + x), (int) (renY + y + 0.5));
		if (Map.solid.contains(rgb)) {
			return true;
		}

		return false;
	}

}
