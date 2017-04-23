package com.weebly.kyslol.smallworld;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.util.Random;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;

import com.weebly.kyslol.smallworld.audio.Audio;
import com.weebly.kyslol.smallworld.mobs.Bullet;
import com.weebly.kyslol.smallworld.mobs.Food;
import com.weebly.kyslol.smallworld.mobs.Zombie;
import com.weebly.kyslol.smallworld.player.Child;
import com.weebly.kyslol.smallworld.player.Player;
import com.weebly.kyslol.smallworld.player.Wife;
import com.weebly.kyslol.smallworld.render.RenderThread;

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
	public static int spin = 0;
	
	public Loop() {

		frame.setSize(_INIT_.WIDTH*_INIT_.ZOOM, _INIT_.HEIGHT*_INIT_.ZOOM);
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

		frame.setSize(_INIT_.WIDTH*_INIT_.ZOOM, _INIT_.HEIGHT*_INIT_.ZOOM + 1);// IDK Doesnt display
														// without it xD
		frame.setSize(_INIT_.WIDTH*_INIT_.ZOOM, _INIT_.HEIGHT*_INIT_.ZOOM);

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
		Audio.music.loop(Clip.LOOP_CONTINUOUSLY);

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
//							Audio.music.stop();

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
//		time = 0;
		Inputs.tick();
		if(end && Inputs.mb != -1){
			Player.reset();
			Child.reset();
			Wife.reset();
			
			time = 300;
			cutsceneId = 0;
			food = 0;
			end = false;
			Food.food.removeAll(Food.food);
			Audio.spin.stop();
			RenderThread.messages.add("Get food for your family to keep them alive.");
			RenderThread.messages.add("Dont let zombies kill you");
			RenderThread.messages.add("Stab zombies by clicking them");
			RenderThread.messages.add("Child~ Daddy im hungry.");
			new Zombie();
		}
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
			spin ++;
			Audio.spin.loop(Clip.LOOP_CONTINUOUSLY);

			if (time == -10) {
				if (Player.y + Player.renY == 51 && Player.x + Player.renX == 50 && Child.y + Child.renY == 50 && Child.x + Child.renX == 50 && Wife.y + Wife.renY == 49 && Wife.x + Wife.renX == 50) {
					end = true;
				}
				Child.dir = 'L';
				Wife.dir = 'L';
				
				if (Player.x + Player.renX < 50) {
					Player.x += 0.125;
					Player.dir = 'L';
				} else if (Player.x + Player.renX > 50) {
					Player.x -= 0.125;
					Player.dir = 'R';
				}
				if (Player.y + Player.renY < 51) {
					Player.y += 0.125;
					Player.dir = 'U';
				} else if (Player.y + Player.renY > 51) {
					Player.y -= 0.125;
					Player.dir = 'U';

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
				if (Wife.y + Wife.renY < 49) {
					Wife.y += 0.125;
				} else if (Wife.y + Wife.renY > 49) {
					Wife.y -= 0.125;
				}

			}
		}
	}
}
