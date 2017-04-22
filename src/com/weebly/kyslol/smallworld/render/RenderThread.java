package com.weebly.kyslol.smallworld.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.weebly.kyslol.smallworld.Loop;
import com.weebly.kyslol.smallworld.Map;
import com.weebly.kyslol.smallworld._INIT_;
import com.weebly.kyslol.smallworld.mobs.Bullet;
import com.weebly.kyslol.smallworld.mobs.Food;
import com.weebly.kyslol.smallworld.mobs.Zombie;
import com.weebly.kyslol.smallworld.player.Child;
import com.weebly.kyslol.smallworld.player.Player;
import com.weebly.kyslol.smallworld.player.Wife;

public class RenderThread implements Runnable{
	Thread render;
	Loop l;
	Graphics g;
	BufferedImage img = new BufferedImage(l.WIDTH+64, l.HEIGHT+64, BufferedImage.TYPE_INT_RGB);
	int px, py;
	public static ArrayList<String> messages = new ArrayList<String>();
	
	public void run() {
		while(Loop.running){
			render();
		}
	}
	
	
	public void render(){
		l.bs = l.getBufferStrategy();
		g = l.bs.getDrawGraphics();
		if(Loop.cutscene && Loop.cutsceneId == 0){
			try{	
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/newpaper.png")), 0, 0, _INIT_.WIDTH, _INIT_.HEIGHT-10, null);
			}catch(Exception e){
				System.out.println("Error loading: /textures/newpaper.png");
			}

		}else{
		px = (int) Player.x;
		py = (int) Player.y;
		double dpx = Player.x*32.0;
		double pyd = Player.y;
		int xx= 32- (int) dpx%32;
		for(int x = 0; x < l.WIDTH+64; x ++){
			double dpy = pyd*32.0;
			int yy= 32- (int) dpy%32;

			for(int y = 0; y < l.HEIGHT+64; y++){
				int xTex, yTex;
				int rgb = Map.map.getRGB((px + x/32), (py + y/32));
				if(rgb == Map.GRASS){
					xTex = 0;
					yTex = 0;
				}else if(rgb == Map.PINK){
					xTex = 2;
					yTex = 1;
				}else if(rgb == Map.SAND){
					xTex = 1;
					yTex = 1;
				}else if(rgb == Map.WATER){
					xTex = 1;
					yTex = 0;
				}else if(rgb == Map.WATER_DEEP){
					xTex = 0;
					yTex = 1;
				}else if(rgb == Map.WALL){
					xTex = 1;
					yTex = 2;
				}else if(rgb == Map.PATH){
					xTex = 2;
					yTex = 2;
				}else if(rgb == Map.DOOR1){
					xTex = 0;
					yTex = 3;
				}else if(rgb == Map.DOOR2){
					xTex = 1;
					yTex = 3;
				}else if(rgb == Map.FLOORING){
					xTex = 0;
					yTex = 2;
				}else{
					xTex = 2;
					yTex = 0;
				}
				if(xx>= _INIT_.WIDTH + 64) continue;
				if(yy>= _INIT_.HEIGHT + 64) continue;
 				img.setRGB(xx, yy, SpriteLoader.GroundSprites.pixels[((x%32 & SpriteLoader.GROUNDSIZE-1) + xTex * SpriteLoader.GROUNDSIZE) + ((y%32 & SpriteLoader.GROUNDSIZE-1) + yTex * SpriteLoader.GROUNDSIZE) * (SpriteLoader.GROUNDSIZE*SpriteLoader.GROUNDLEN)]);
 				yy++;
			}
			xx++;
		}
		g.drawImage(img, -32, -32, l.WIDTH+32, l.HEIGHT+32, null);
//		g.fillRect(0, 0, 100, 100);
		for(int i = 0; i < Food.food.size(); i ++){
			Food f = Food.food.get(i);
			try{
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/food.png")), (int)(f.getX()*32-px*32 +( 32- dpx%32)) , (int)(f.getY()*32-py*32 +( 32- pyd*32.0%32)), 32, 32, null);
			}catch(Exception e){
				System.out.println("Error loading food.png");
			}

		}
		for(int i = 0; i < Bullet.bullets.size(); i ++){
			Bullet b = Bullet.bullets.get(i);
			try{
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/stats.png")), (int)(b.getX()/*32-px*32 +( 32- dpx%32)*/) , (int)(b.getY()/*32-py*32 +( 32- pyd*32.0%32)*/), 10, 10, null);
			}catch(Exception e){
				System.out.println("Error loading stats.png");
			}
		}
		
	
		try{
			if(!Loop.end){
				if(Player.health > 0){
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/player/player.png")), Player.renX*32-16, Player.renY*32-16, 32, 32, null);
				}
																								//(Child.x*32-16) - (Player.x *32)
				if(Child.health > 0){
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/player/child.png")), (int)(Child.x*32-px*32 +( 32- dpx%32)) + Player.renX*32-16, (int)(Child.y*32-py*32 +( 32- pyd*32.0%32)) + Player.renY*32-16, 32, 32, null);
				}
				if(Wife.health > 0){
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/player/wife.png")), (int)(Wife.x*32-px*32 +( 32- dpx%32)) + Player.renX*32-16, (int)(Wife.y*32-py*32 +( 32- pyd*32.0%32)) + Player.renY*32-16, 32, 32, null);
				}
				
				for(int i = 0; i < Zombie.zombies.size(); i ++){
					Zombie z = Zombie.zombies.get(i);
					
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/mobs/zombie.png")), (int)(z.getX()*32-px*32 +( 32- dpx%32))-32, (int)(z.getY()*32-py*32 +( 32- pyd*32.0%32))-32, 32, 32, null);
				}
			}
		}catch(Exception e){
			System.out.println("Error loading: /textures/player/*.png or /textures/mobs/zombie.png");
		}
		if(!Loop.cutscene){
			try{
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/stats.png")), 0, 0, 240, 145, null);
			}catch(Exception e){
				System.out.println("Error loading stats.png");
			}
			g.drawString("Time left till rescue: " + Loop.time, 0, 10);
			g.drawString("Health: " + Player.health + "/40", 0, 25);
			g.drawString("Food to drop off: " + Loop.food, 0, 40);
			
			int j = 0;
			for(int i = messages.size() - 4; i < messages.size(); i ++){
				g.drawString(messages.get(i) , 0, 80 + (j*20));
				j++;
			}

		}else{
			try{
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/rescue"+Math.abs(Loop.time%2)+".png")), 50*32-px*32 +( 32- (int) dpx%32), 50*32-py*32-(32- (int) (pyd*32.0%32)), 200 + (10*Loop.time), 200 + (10*Loop.time), null);
				if(Loop.time >= 0){
					for(int i = 0; i < Loop.time; i++){
						g.drawImage(ImageIO.read(Map.class.getResource("/textures/grey.png")), 0, 0, _INIT_.WIDTH, _INIT_.HEIGHT, null);
					}
					if(Loop.time == 10){		
						g.drawImage(ImageIO.read(Map.class.getResource("/textures/grey2.png")), 0, 0, _INIT_.WIDTH, _INIT_.HEIGHT, null);
						g.setFont(new Font("cambri", 1, 40));
						g.setColor(Color.WHITE);
						g.drawString("The End...", 300, 300);
						
						g.setFont(new Font("cambri", 0, 20));
						g.drawString("You " + Player.end, 300, 320);
						g.drawString("Your wife "+ Wife.end, 300, 340);
						g.drawString("Your child " + Child.end, 300, 360);
					}

				}
			}catch(Exception e){
				System.out.println("Error loading: /textures/rescue"+Math.abs(Loop.time%2)+".png");
			}
		}
		}
		
		g.dispose();
		l.bs.show();
	}
	
	
	public RenderThread(Loop loop) {
		messages.add("Get food for your family to keep them alive.");
		messages.add("Dont let zombies kill you");
		messages.add("Stab zombies by clicking them");
		messages.add("Child~ Daddy im hungry.");
		
		l = loop;
		StartRunning();
	}

	public void StartRunning() {
		render = new Thread(this);
		render.start();
	}

}
