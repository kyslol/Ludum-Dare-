package com.weebly.kyslol.smallworld.player;

import java.util.Random;

import com.weebly.kyslol.smallworld.Loop;
import com.weebly.kyslol.smallworld.Map;
import com.weebly.kyslol.smallworld._INIT_;
import com.weebly.kyslol.smallworld.audio.Audio;
import com.weebly.kyslol.smallworld.render.RenderThread;

public class Child {
	public static double x = 38 - _INIT_.WIDTH / 2 / 32, y = 49 - _INIT_.HEIGHT / 2 / 32;
	public static int renX = _INIT_.WIDTH / 2 / 32, renY = _INIT_.HEIGHT / 2 / 32;
	public static int health = 20, hunger = 5;
	static Random random = new Random();
	public static String end = "Lived!";
	public static char dir = 'U';
	
	public static void reset(){
		x = 38 - _INIT_.WIDTH / 2 / 32; y = 49 - _INIT_.HEIGHT / 2 / 32;
		renX = _INIT_.WIDTH / 2 / 32; renY = _INIT_.HEIGHT / 2 / 32;
		health = 20;
		hunger = 5;
		end = "Lived!";
		dir = 'U';

	}
	
	public static void tick() {
		if(Loop.time > 295)return;//So they can read the starting text
		if(health == 0){
			if(end.equalsIgnoreCase("Lived!")){
				Audio.death.loop(1);
			}
			end = "Died :(";
			return;
		}

		if (random.nextInt(200) == 0) {
			if (hunger == 0)
				hunger++;
			hunger--;
			if (hunger <= 5) {
				RenderThread.messages.add("Child~ Im hungry daddy");
			}
		}
		if (hunger == 0) {
			if (random.nextInt(100) == 0) {

				health--;
				if(health == 0){
					RenderThread.messages.add("Your child has starved.");
					return;
				}
				RenderThread.messages.add("Your child is starving, bring him food.");
			}
		}

		// if (Inputs.f) {
		// y -= 0.125;
		// if (check()) {
		// y += 0.125;
		// }
		//
		// }
		// if (Inputs.b) {
		// y += 0.125;
		// if (check()) {
		// y -= 0.125;
		// }
		//
		// }
		// if (Inputs.l) {
		// x -= 0.125;
		// if (check()) {
		// x += 0.125;
		// }
		//
		// }
		// if (Inputs.r) {
		// x += 0.125;
		// if (check()) {
		// x -= 0.125;
		// }
		// }
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
