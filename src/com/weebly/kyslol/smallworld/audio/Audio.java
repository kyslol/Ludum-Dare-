package com.weebly.kyslol.smallworld.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Audio {
	
	public static Clip music = setAudio("/audio/music.wav");
	public static Clip spin = setAudio("/audio/helicopter.wav");
	public static Clip death = setAudio("/audio/death.wav");
	
	
	public static Clip setAudio(String Sound) {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Audio.class.getResource(Sound));
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        return clip;
	    } catch(Exception ex) {
	        System.out.println("Error Loading " + Sound);
	        ex.printStackTrace();
			System.exit(1);
			return null;
	    }
	}

	
	
}
