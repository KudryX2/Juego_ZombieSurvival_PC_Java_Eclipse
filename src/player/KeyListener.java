package player;

import org.lwjgl.input.Keyboard;

public class KeyListener extends Thread{

	private boolean stop;
	public boolean W,A,S,D;
	
	public KeyListener(){
		stop = false;
		Thread keyListenerThread = new Thread(this,"KeyListener");
		keyListenerThread.start();
	}
	
	public void run(){
		System.out.println("KeyListener working");
		
		while(!stop){
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) W = true; else W = false;
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) A = true; else A = false;
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) S = true; else S = false;
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) D = true; else D = false;
		}
		
		System.out.println("KeyListener off");
	}
	
	public void end(){
		stop = true;
	}
	
	
	
}
