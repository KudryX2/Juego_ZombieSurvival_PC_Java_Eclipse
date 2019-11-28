package player;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import graphics.DisplayManager;
import items.DropedItem;
import items.Item;
import items.ItemKind;
import structs.Struct;
import world.Chunk;
import world.WorldManager;

public final class Player {
	public static float x,y,lx,ly;
	public static float bx,by;
	public static float rotation = 0;
	public static float speed = 0.5f;
	public static final float SIZE = 35;
	private static boolean collUp,collDown,collLeft,collRight;
	
	public static int selectedCell;
	public static int handSize;
	public static ArrayList<Item> inventory = new ArrayList<Item>();
	
	public static boolean buildMode,canBuild;
	
	public static boolean button0,button1;
	public static boolean q,e,b;

	public static int textureID,handTextureID;
	
	public static int hp;

	
	public static void create(){
		x = 600;
		y = 600;	
		lx = x;
		ly = y;
		
		Mouse.setCursorPosition(DisplayManager.WIDTH/2, DisplayManager.HEIGHT/2);
		
		bx = Player.x + Mouse.getX() - DisplayManager.WIDTH/2;
		by = Player.y + DisplayManager.HEIGHT/2 - Mouse.getY();
		
		textureID = 0;
		handTextureID = 0;
		
		collUp = false;
		collDown = false;
		collLeft = false;
		collRight = false;
		
		buildMode = false;
				
		selectedCell = 0;
		handSize = 3;		
	
		hp = 100;
	}
	
	public static synchronized void move(){
		//movement
		if((Keyboard.isKeyDown(Keyboard.KEY_W))&&(!collUp)){
			y -= speed * DisplayManager.delta;
		}
		if((Keyboard.isKeyDown(Keyboard.KEY_S))&&(!collDown)){
			y += speed * DisplayManager.delta;
		}
		if((Keyboard.isKeyDown(Keyboard.KEY_A))&&(!collLeft)){
			x -= speed * DisplayManager.delta;
		}
		if((Keyboard.isKeyDown(Keyboard.KEY_D))&&(!collRight)){
			x += speed * DisplayManager.delta;
		}
		
		rotation =  - (float) Math.toDegrees(Math.atan2(Mouse.getY() - DisplayManager.HEIGHT/2, Mouse.getX() - DisplayManager.WIDTH/2));
		
		
		//inventory selection
		if(Keyboard.isKeyDown(Keyboard.KEY_1)) selectedCell = 0;
		if(Keyboard.isKeyDown(Keyboard.KEY_2)) selectedCell = 1;
		if(Keyboard.isKeyDown(Keyboard.KEY_3)) selectedCell = 2;
		if(Keyboard.isKeyDown(Keyboard.KEY_4)) selectedCell = 3;
		
		float mouse = Mouse.getDWheel();
		if(mouse < 0){
			if(selectedCell == handSize) selectedCell = 0; else selectedCell++; 
		}else if(mouse > 0){
			if(selectedCell == 0) selectedCell = handSize; else selectedCell--;
		}
		
		//actions
		if(!Mouse.isButtonDown(0)) button0 = false;
		if(Mouse.isButtonDown(0)){ // !button0
			action1();
			button0 = true;
		}
		if(!Mouse.isButtonDown(1)) button1 = false;
		if((Mouse.isButtonDown(1))&&(!button1)){
			action2();
			button1 = true;
		}
		
		//adictional actions
		//drop items
		if((Keyboard.isKeyDown(Keyboard.KEY_Q))&&(!q)){
			dropItem();
			q = true;
		}
		q = Keyboard.isKeyDown(Keyboard.KEY_Q);
		//get items
		if((Keyboard.isKeyDown(Keyboard.KEY_E))&&(!e)){	
			getItem();
		}
		e = Keyboard.isKeyDown(Keyboard.KEY_E);
		//build mode
		if((Keyboard.isKeyDown(Keyboard.KEY_B))&&(!b)){
			if(buildMode) buildMode = false; else buildMode = true;
		}
		b = Keyboard.isKeyDown(Keyboard.KEY_B);
			
		//Updates
		if(buildMode)updateBuildPos();
		checkCollisions();
		if(hp <= 0){
			die();
			respawn();
		}
		/* */
		//	System.out.println("MOUSE WORLD X:" + (x +Mouse.getX()- DisplayManager.WIDTH/2) +" Y:" + (y + Mouse.getY()-DisplayManager.HEIGHT/2));
		//	System.out.println("MOUSE    X:"+Mouse.getX()+" Y:"+Mouse.getY());
		// 	System.out.println("TOP:"+collisionUp+" DOWN:"+collisionDown);
		//	System.out.println("PLAYER      X:"+x+" Y:"+y+" Rot:"+rotation);
		/* */
	}
	
	private static void checkCollisions(){		
		collUp = false;
		collDown = false;
		collLeft = false;
		collRight = false; 
		
		for(Chunk iterc : WorldManager.chunks){			
			for(Struct iter : iterc.walls){	
				//up
				if((iter.y+iter.sizeY/2+10 > y-SIZE/2)&&(iter.y-iter.sizeY/2+10 < y-SIZE/2)&&(iter.x+iter.sizeX/2 > x-SIZE/2)&&(iter.x-iter.sizeX/2 < x+SIZE/2)) collUp = true;
				//down
				if((iter.y+iter.sizeY/2-10 > y+SIZE/2)&&(iter.y-iter.sizeY/2-10 < y+SIZE/2)&&(iter.x+iter.sizeX/2 > x-SIZE/2)&&(iter.x-iter.sizeX/2 < x+SIZE/2)) collDown = true;
				//left
				if((iter.y+iter.sizeY/2 > y-SIZE/2)&&(iter.y-iter.sizeY/2 < y+SIZE/2)&&(iter.x+iter.sizeX/2+10 > x-SIZE/2)&&(iter.x-iter.sizeX/2+10 < x-SIZE/2)) collLeft = true;
				//right
				if((iter.y+iter.sizeY/2 > y-SIZE/2)&&(iter.y-iter.sizeY/2 < y+SIZE/2)&&(iter.x+iter.sizeX/2-10 > x+SIZE/2)&&(iter.x-iter.sizeX/2-10 < x+SIZE/2)) collRight = true;
			}
			
			for(Struct iter : iterc.halfWalls){
				//up
				if((iter.y+iter.sizeY/2+10 > y-SIZE/2)&&(iter.y-iter.sizeY/2+10 < y-SIZE/2)&&(iter.x+iter.sizeX/2 > x-SIZE/2)&&(iter.x-iter.sizeX/2 < x+SIZE/2)) collUp = true;
				//down
				if((iter.y+iter.sizeY/2-10 > y+SIZE/2)&&(iter.y-iter.sizeY/2-10 < y+SIZE/2)&&(iter.x+iter.sizeX/2 > x-SIZE/2)&&(iter.x-iter.sizeX/2 < x+SIZE/2)) collDown = true;
				//left
				if((iter.y+iter.sizeY/2 > y-SIZE/2)&&(iter.y-iter.sizeY/2 < y+SIZE/2)&&(iter.x+iter.sizeX/2+10 > x-SIZE/2)&&(iter.x-iter.sizeX/2+10 < x-SIZE/2)) collLeft = true;
				//right
				if((iter.y+iter.sizeY/2 > y-SIZE/2)&&(iter.y-iter.sizeY/2 < y+SIZE/2)&&(iter.x+iter.sizeX/2-10 > x+SIZE/2)&&(iter.x-iter.sizeX/2-10 < x+SIZE/2)) collRight = true;
			}
		}		
	}
	
	private static void action1(){
		if((selectedCell < inventory.size())&&(inventory.get(selectedCell)!= null)){
			// Shoot
			if(inventory.get(selectedCell).itemKind == ItemKind.GUN){
				float startPosX = (float) (x + Math.sin(Math.toRadians(-rotation+90))*40);
				float startPosY = (float) (y + Math.cos(Math.toRadians(-rotation+90))*40);
				inventory.get(selectedCell).shoot(startPosX, startPosY, rotation, true);
			}
			
		}
	}
	
	private static void action2(){
		if((selectedCell < inventory.size())&&(inventory.get(selectedCell)!= null)){
			//build
			if((buildMode)&&(canBuild))
			if((inventory.get(selectedCell).itemKind == ItemKind.WALL)||(inventory.get(selectedCell).itemKind == ItemKind.HALFWALL)||(inventory.get(selectedCell).itemKind == ItemKind.FLOOR)){	
				
				if(inventory.get(selectedCell).itemKind == ItemKind.WALL){	
					WorldManager.build(new Struct(bx,by,ItemKind.WALL,inventory.get(selectedCell).cellTextureID), true);	
				}else if(inventory.get(selectedCell).itemKind == ItemKind.HALFWALL){
					WorldManager.build(new Struct(bx,by,ItemKind.HALFWALL,inventory.get(selectedCell).cellTextureID), true);		
				}else if(inventory.get(selectedCell).itemKind == ItemKind.FLOOR){
					WorldManager.build(new Struct(bx,by,ItemKind.FLOOR,inventory.get(selectedCell).cellTextureID), true);
				}
	
			}
			//consume
			if(inventory.get(selectedCell).itemKind == ItemKind.CONSUMIBLE){
				
			}
		}
	}
	
	private static void updateBuildPos(){
		bx = x + Mouse.getX() - DisplayManager.WIDTH/2;
		by = y - Mouse.getY() + DisplayManager.HEIGHT/2;
		
		
		
		canBuild = true;
		boolean stop = false;
		
		for(Chunk iter : WorldManager.activeChunks){
			for(Struct iters : iter.walls){
				if((iters.y+iters.sizeY > by)&&(iters.y-iters.sizeY < by)&&(iters.x+iters.sizeX > bx)&&(iters.x-iters.sizeX < bx)){
					canBuild = false;
					stop = true;
					
					/*
					if((by - y) <= (by - iters.y)) by += iters.y - by + iters.sizeY; else by += iters.y - by - iters.sizeY; 
					if((bx - x) <= (bx - iters.x)) bx += iters.x - bx + iters.sizeX;//  else bx += iters.x - bx - iters.sizeX; 
					*/
					break;
				}
			}
			if(stop) break;
			for(Struct iters : iter.halfWalls){
				if((iters.y+iters.sizeY > by)&&(iters.y-iters.sizeY < by)&&(iters.x+iters.sizeX > bx)&&(iters.x-iters.sizeX < bx)){
					canBuild = false;
					stop = true;
					break;
				}
			}
		}
		
		
		Mouse.setCursorPosition((int)(bx - x + DisplayManager.WIDTH/2), -(int)(by - y - DisplayManager.HEIGHT/2));
		
		if(Math.sqrt(Math.pow(bx - x + Mouse.getX() - DisplayManager.WIDTH/2, 2)+Math.pow(by - y - Mouse.getY() + DisplayManager.HEIGHT/2, 2)) > 500) canBuild = false;
		
	}
	
	public static void dropItem(){
		if(selectedCell < inventory.size()){
			WorldManager.dropItem(inventory.get(selectedCell), x, y, true);
			inventory.remove(selectedCell);
		}
	}
	
	public static void getItem(){
		if(inventory.size() < 4){
			for(Chunk iter : WorldManager.activeChunks){
				for(DropedItem i : iter.droppedItems){
					if(Math.sqrt(Math.pow(i.x - x, 2)+Math.pow(i.y - y, 2)) < 50){
						iter.droppedItems.remove(i);
						inventory.add(i.item);
						break;
					}
				}
			}
		}
	}
	
	public static void die(){
		for(Item iter : inventory){
			WorldManager.dropItem(iter, x, y, true);
		}
		inventory.clear();
	}
	
	public static void respawn(){
		x = 0;
		y = 0;
		hp = 100;
	}
	
}
