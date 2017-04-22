package com.weebly.kyslol.smallworld;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Inputs implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
	public static boolean[] key = new boolean[65565];
	public static int my, mx, mb = -1; 
	
	public static boolean f, b, l, r, e, s;
	
	static int forward = KeyEvent.VK_W;
	static int backwards = KeyEvent.VK_S;
	static int left = KeyEvent.VK_A;
	static int right = KeyEvent.VK_D;

	static int exit = KeyEvent.VK_ESCAPE;
	static int space = KeyEvent.VK_SPACE;
	
	public static void tick(){
		f = key[forward];
		b = key[backwards];
		l = key[left];
		r = key[right];
		e = key[exit];
		s = key[space];
		
		if(Loop.cutscene && s && Loop.cutsceneId == 0){
			Loop.cutscene = false;
			Loop.cutsceneId = 1;
		}else if(Loop.cutscene){
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mx = e.getX();
		my = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mb = e.getButton();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		mb = -1;
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length){
			key[keyCode] = true;
		}		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length){
			key[keyCode] = false;
		}		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {

		int keyCode = e.getKeyCode();
		if (keyCode > 0 && keyCode < key.length){
			key[keyCode] = true;
		}		
		
	}

}
