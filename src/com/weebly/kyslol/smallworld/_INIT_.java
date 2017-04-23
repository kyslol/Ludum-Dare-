package com.weebly.kyslol.smallworld;

import javax.sound.sampled.Clip;

import com.weebly.kyslol.smallworld.audio.Audio;
import com.weebly.kyslol.smallworld.render.RenderThread;

public class _INIT_ {
	public static final int ZOOM = 2, WIDTH = 800/ZOOM, HEIGHT = 600/ZOOM;
	
	public static void main(String[] args){
		new Map();
		Loop l = new Loop();
		while(Loop.starting){
		}
		new RenderThread(l);

	}
}
