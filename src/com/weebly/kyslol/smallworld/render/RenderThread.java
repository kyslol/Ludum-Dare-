package com.weebly.kyslol.smallworld.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.weebly.kyslol.smallworld.Inputs;
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
	BufferedImage img = new BufferedImage(l.WIDTH+(32*_INIT_.ZOOM), l.HEIGHT+(32*_INIT_.ZOOM), BufferedImage.TYPE_INT_RGB);
	int px, py;
	public static ArrayList<String> messages = new ArrayList<String>();
	char dir = 'U';
	
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
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/newpaper.png")), 0, 0, _INIT_.WIDTH * _INIT_.ZOOM, _INIT_.HEIGHT*_INIT_.ZOOM-20, null);
			}catch(Exception e){
				System.out.println("Error loading: /textures/newpaper.png");
			}

		}else{
		px = (int) Player.x;
		py = (int) Player.y;
		double dpx = Player.x*32.0;  
		double pyd = Player.y;
		int xx= 32- (int) dpx%32;
		for(int x = 0; x < l.WIDTH+(32*_INIT_.ZOOM); x ++){
			double dpy = pyd*32.0;
			int yy= 32- (int) dpy%32;

			for(int y = 0; y < l.HEIGHT+(32*_INIT_.ZOOM); y++){
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
				if(xx>= _INIT_.WIDTH  + (32*_INIT_.ZOOM)) continue;
				if(yy>= _INIT_.HEIGHT + (32*_INIT_.ZOOM)) continue;
 				img.setRGB(xx, yy, SpriteLoader.GroundSprites.pixels[((x%32 & SpriteLoader.GROUNDSIZE-1) + xTex * SpriteLoader.GROUNDSIZE) + ((y%32 & SpriteLoader.GROUNDSIZE-1) + yTex * SpriteLoader.GROUNDSIZE) * (SpriteLoader.GROUNDSIZE*SpriteLoader.GROUNDLEN)]);
 				yy++;
			}
			xx++;
		}
		g.drawImage(img, -32*_INIT_.ZOOM, -32*_INIT_.ZOOM, l.WIDTH*_INIT_.ZOOM+(32*_INIT_.ZOOM), l.HEIGHT*_INIT_.ZOOM+(32*_INIT_.ZOOM), null);
//		g.fillRect(0, 0, 100, 100);
		for(int i = 0; i < Food.food.size(); i ++){
			Food f = Food.food.get(i);
			try{
				int x =(int)(f.getX()*32-px*32 +( 32- dpx%32));
				int y =(int)(f.getY()*32-py*32 +( 32- pyd*32.0%32));
				if(x>0 && x < _INIT_.WIDTH && y > 0 && y < _INIT_.HEIGHT){
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/food.png")), x*_INIT_.ZOOM, y*_INIT_.ZOOM, 32, 32, null);
				}
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
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/player/player"+Player.dir+".png")), (Player.renX*32-16)*_INIT_.ZOOM, (Player.renY*32-16)*_INIT_.ZOOM, 32, 32, null);
				}
				if(Child.health > 0){
					int x =(int)(Child.x*32-px*32 +( 32- dpx%32)) + Player.renX*32-16;
					int y =(int)(Child.y*32-py*32 +( 32- pyd*32.0%32)) + Player.renY*32-16;
					if(x>0 && x < _INIT_.WIDTH && y > 0 && y < _INIT_.HEIGHT){
						g.drawImage(ImageIO.read(Map.class.getResource("/textures/player/child"+Child.dir+".png")), x*_INIT_.ZOOM, y*_INIT_.ZOOM, 32, 32, null);
					}
				}
				if(Wife.health > 0){
					int x =(int)(Wife.x*32-px*32 +( 32- dpx%32)) + Player.renX*32-16;
					int y =(int)(Wife.y*32-py*32 +( 32- pyd*32.0%32)) + Player.renY*32-16;
					if(x>0 && x < _INIT_.WIDTH && y > 0 && y < _INIT_.HEIGHT){
						g.drawImage(ImageIO.read(Map.class.getResource("/textures/player/wife"+Wife.dir+".png")), x*_INIT_.ZOOM, y*_INIT_.ZOOM, 32, 32, null);
					}
				}
				
				for(int i = 0; i < Zombie.zombies.size(); i ++){
					Zombie z = Zombie.zombies.get(i);
					int x =(int)(z.getX()*32-px*32 +( 32- dpx%32))-48;
					int y =(int)(z.getY()*32-py*32 +( 32- pyd*32.0%32))-48;
					if(x>0 && x < _INIT_.WIDTH && y > 0 && y < _INIT_.HEIGHT){
					//, )-32
					g.drawImage(ImageIO.read(Map.class.getResource("/textures/mobs/zombie"+z.getDir()+".png")), x*_INIT_.ZOOM, y*_INIT_.ZOOM, 32, 32, null);
				}}
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
			g.setColor(Color.BLACK);
			g.drawString("Time left till rescue: " + Loop.time, 0, 10);
			g.drawString("Health: " + Player.health + "/40", 0, 25);
			g.drawString("Food to drop off: " + Loop.food, 0, 40);
			g.drawString("##################################", 0, 55);
			g.setColor(Color.WHITE);

			int j = 0;
			for(int i = messages.size() - 4; i < messages.size(); i ++){
				g.drawString(messages.get(i) , 0, 80 + (j*20));
				j++;
			}

		}else{
			try{
				g.drawImage(ImageIO.read(Map.class.getResource("/textures/rescue"+Math.abs(Loop.spin%2)+".png")), (50*32-px*32 +( 32- (int) dpx%32)) * _INIT_.ZOOM, (50*32-py*32-(32- (int) (pyd*32.0%32)))*_INIT_.ZOOM, 200 + (10*Loop.time), 200 + (10*Loop.time), null);
				if(Loop.time >= 0){
					for(int i = 0; i < Loop.time; i++){
						g.drawImage(ImageIO.read(Map.class.getResource("/textures/grey.png")), 0, 0, _INIT_.WIDTH*_INIT_.ZOOM, _INIT_.HEIGHT*_INIT_.ZOOM, null);
					}
					if(Loop.time == 10){		
						g.drawImage(ImageIO.read(Map.class.getResource("/textures/grey2.png")), 0, 0, _INIT_.WIDTH*_INIT_.ZOOM, _INIT_.HEIGHT*_INIT_.ZOOM, null);
						g.setFont(new Font("cambri", 1, 40));
						g.setColor(Color.WHITE);
						g.drawString("The End...", _INIT_.WIDTH*_INIT_.ZOOM/2-100, _INIT_.HEIGHT*_INIT_.ZOOM/2-100);
						
						g.setFont(new Font("cambri", 0, 20));
						g.drawString("You " + Player.end, _INIT_.WIDTH*_INIT_.ZOOM/2-100, _INIT_.HEIGHT*_INIT_.ZOOM/2-100+20);
						g.drawString("Your wife "+ Wife.end, _INIT_.WIDTH*_INIT_.ZOOM/2-100, _INIT_.HEIGHT*_INIT_.ZOOM/2-100+40);
						g.drawString("Your child " + Child.end, _INIT_.WIDTH*_INIT_.ZOOM/2-100, _INIT_.HEIGHT*_INIT_.ZOOM/2-100+60);
						g.drawString("Click anywhere to restart.", _INIT_.WIDTH*_INIT_.ZOOM/2-100, _INIT_.HEIGHT*_INIT_.ZOOM/2-100+80);
					}	//Each line has 3 more characters than the last XD

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
