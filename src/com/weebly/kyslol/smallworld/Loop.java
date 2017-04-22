package com.weebly.kyslol.smallworld;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.swing.JFrame;

import com.weebly.kyslol.smallworld.mobs.Bullet;
import com.weebly.kyslol.smallworld.mobs.Food;
import com.weebly.kyslol.smallworld.mobs.Zombie;
import com.weebly.kyslol.smallworld.player.Child;
import com.weebly.kyslol.smallworld.player.Player;
import com.weebly.kyslol.smallworld.player.Wife;

public class Loop extends Canvas implements Runnable {
	Thread loop;
	public static boolean running = false;
	static JFrame frame = new JFrame("Survive");
	public static BufferStrategy bs;
	public static boolean starting = true;
	public static final int WIDTH = _INIT_.WIDTH, HEIGHT = _INIT_.HEIGHT;
	public static boolean cutscene = true;
	public static int time = 300;
	public static int cutsceneId = 0;
	public static boolean end = false;
	public static int food = 0;

	public Loop() {

		frame.setSize(_INIT_.WIDTH, _INIT_.HEIGHT);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

		StartRunning();
	}

	public void StartRunning() {
		if (running) {
			return;
		} else {
			running = true;
			loop = new Thread(this);
			loop.start();
		}
	}

	@Override
	public void run() {
		System.out.println("Running");
		requestFocus();

		addKeyListener(new Inputs());
		addMouseListener(new Inputs());
		addMouseMotionListener(new Inputs());
		frame.add(this);

		frame.setSize(_INIT_.WIDTH, _INIT_.HEIGHT + 1);// IDK Doesnt display
														// without it xD
		frame.setSize(_INIT_.WIDTH, _INIT_.HEIGHT);

		bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
		}

		double ups = 0;
		long prevtime = System.nanoTime();
		double spt = 1 / 60.0;
		int tickcount = 0;
		long currenttime, passedTime;
		starting = false;
		new Zombie();

		while (running) {
			currenttime = System.nanoTime();
			passedTime = currenttime - prevtime;
			prevtime = currenttime;
			ups += passedTime / 1000000000.0;
			while (ups > spt) {
				ups -= spt;
				tickcount++;

				if (tickcount % 60 == 0) {// 1 sec
					if (!(cutsceneId == 0)) {
						if (time == 0)
							cutscene = true;

						if (time > -10 && !end) {
							time--;
						}
						if (end) {
							time++;
							if (time > 10) {
								time--;
							}
						}
					}
				}
				if (tickcount % 3 == 0) {// 20 ups
					tick();
				}
				if (tickcount % 60 == 0) {
					prevtime += 1000;
				}
			}
		}
	}

	public static void tick() {
		// System.out.println((end && cutscene) + " " + cutsceneId);

		Inputs.tick();
		if (cutscene) {
			cutscene();
			return;
		}
		if (Inputs.mb != -1) {
			// new Bullet();
			for (int i = 0; i < Zombie.zombies.size(); i++) {
				Zombie z = Zombie.zombies.get(i);
				int mx = Inputs.mx;
				int my = Inputs.my;
				int zx = (int) ((z.getX() * 32 - 16) - (Player.x * 32)) + Player.renX;
				int zy = (int) ((z.getY() * 32 - 16) - (Player.y * 32)) + Player.renY;
				if (zx + 16 > mx && zx - 16 < mx && zy + 16 > my && zy - 16 < my) {
					z.setHealth(z.getHealth() - 3);
				}
			}
		}
		for (int i = 0; i < Bullet.bullets.size(); i++) {
			Bullet.bullets.get(i).tick();
		}

		Player.tick();
		Child.tick();
		Wife.tick();
		
		if (Player.x + Player.renX<= 39 && Player.x + Player.renX>= 31 && Player.y + Player.renY <= 51 && Player.y + Player.renY >= 48) {
//			System.out.println("HOME");
			while (food > 0) {
				if(Child.health <= 0 && Wife.health <= 0) return;
				
				if (Child.hunger <= 5 && Child.health > 0) {
					Child.hunger++;
					food--;
				} else if (Wife.hunger <= 5 && Wife.health > 0) {
					Wife.hunger++;
					food--;
				} else {
					if (Child.health > 0) {
						Child.hunger++;
						food--;
					}
					if (food != 0 && Wife.health > 0) {
						Wife.hunger++;
						food--;
					}
				}
			}
		}

		for (int i = 0; i < Food.food.size(); i++) {
			Food f = Food.food.get(i);
			int x = f.getX();
			int y = f.getY();
			if (Player.x + Player.renX + 2 > x && Player.x + Player.renX - 2 < x && Player.y + Player.renY + 2 > y
					&& Player.y + Player.renY - 2 < y) {
				Food.food.remove(f);
				food++;
			}
		}

		Random random = new Random();
		if (random.nextInt(400) == 0) {
			new Zombie();
			new Food();
		}
		if (random.nextInt(150) == 0) {
			new Food();
		}
		for (int i = 0; i < Zombie.zombies.size(); i++) {
			Zombie.zombies.get(i).tick();
		}

	}

	private static void cutscene() {
		Zombie.zombies.removeAll(Zombie.zombies);
		if (cutsceneId == 1) {
			if (time == -10) {
				if (Player.y + Player.renY == 50 && Player.x + Player.renX == 50 && Child.y + Child.renY == 50 && Child.x + Child.renX == 50 && Wife.y + Wife.renY == 50 && Wife.x + Wife.renX == 50) {
					end = true;
				}

				if (Player.x + Player.renX < 50) {
					Player.x += 0.125;
				} else if (Player.x + Player.renX > 50) {
					Player.x -= 0.125;
				}
				if (Player.y + Player.renY < 50) {
					Player.y += 0.125;
				} else if (Player.y + Player.renY > 50) {
					Player.y -= 0.125;
				}
				
				if (Child.x + Child.renX < 50) {
					Child.x += 0.125;
				} else if (Child.x + Child.renX > 50) {
					Child.x -= 0.125;
				}
				if (Child.y + Child.renY < 50) {
					Child.y += 0.125;
				} else if (Child.y + Child.renY > 50) {
					Child.y -= 0.125;
				}
				
				if (Wife.x + Wife.renX < 50) {
					Wife.x += 0.125;
				} else if (Wife.x + Wife.renX > 50) {
					Wife.x -= 0.125;
				}
				if (Wife.y + Wife.renY < 50) {
					Wife.y += 0.125;
				} else if (Wife.y + Wife.renY > 50) {
					Wife.y -= 0.125;
				}

			}
		}
	}
}
