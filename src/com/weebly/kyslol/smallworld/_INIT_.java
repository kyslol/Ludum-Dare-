package com.weebly.kyslol.smallworld;

import com.weebly.kyslol.smallworld.render.RenderThread;

public class _INIT_ {
	public static final int WIDTH = 800, HEIGHT = 600;
	
	public static void main(String[] args){
		new Map();
		Loop l = new Loop();
		while(Loop.starting){
		}
		new RenderThread(l);
	}
}
