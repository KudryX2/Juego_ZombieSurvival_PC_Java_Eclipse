package tests;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import graphics.DisplayManager;
import graphics.GraphicsManager;
import graphics.TexturesManager;
import items.Gun;
import items.ItemKind;
import items.ItemManager;
import npc.NpcManager;
import player.Player;
import structs.Struct;
import world.Chunk;
import world.WorldManager;

import static graphics.DisplayManager.*;

import java.util.Random;

public class Test{
	
	public Test(){
		
		DisplayManager.init(true);
		Player.create();
		TexturesManager.init();
		NpcManager.init();
		ItemManager.init();
		
		//TEMP		
		temp1();
		boolean p = Keyboard.isKeyDown(Keyboard.KEY_P);
		boolean n = Keyboard.isKeyDown(Keyboard.KEY_N);
		boolean m = Keyboard.isKeyDown(Keyboard.KEY_M);
		
		/*
		CONTROLS
		W A S D - MOVEMENT
		Q       - drop something
		E       - pick up something
		Mouse0  - attack
		Mouse1  - build/use
		N/M     - change hud size
		P       - spawn zombies
		R       - teleport to spawn
		B       - build mode
		ESCAPE  - exit the game	
		
		*/
		//END TEMP
		
		
		/////////////
		//GAME LOOP//
		/////////////
		while(!Display.isCloseRequested()){	

			Player.move();
			WorldManager.update();
			
			GraphicsManager.render();
			DisplayManager.update();
			
			//TEMP
			if((Keyboard.isKeyDown(Keyboard.KEY_N))&&(!n)){
				n = true;
				GraphicsManager.SCALE --;
			}
			n = Keyboard.isKeyDown(Keyboard.KEY_N);
			if((Keyboard.isKeyDown(Keyboard.KEY_M))&&(!m)){
				m = true;
				GraphicsManager.SCALE ++;
			}
			m = Keyboard.isKeyDown(Keyboard.KEY_M);
			
			
			if((Keyboard.isKeyDown(Keyboard.KEY_P))&&(!p)){
				p = true;
				
				for(int i = 0 ; i < WIDTH ; i += 100) {
					int rand = new Random().nextInt(2);
					if(rand == 1)
						NpcManager.spawnNpc(0, i, 100, 0);
						else
						NpcManager.spawnNpc(1, i, 100, 0);
					
					NpcManager.spawnNpc(2, i, 1000, 0);
				}	
			}
			
			p = Keyboard.isKeyDown(Keyboard.KEY_P);
			/* */	
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) break; //STOP
			if(Keyboard.isKeyDown(Keyboard.KEY_R)){			   //RESTART
				Player.x = 0;
				Player.y = 0;
			}
			/* */	
			//ENDTEMP

			
		}
		System.out.println("//////////////////////////////////////////////////////////");
		System.out.println(" TE HAS CARGADO "+new Random().nextInt(1000) +" ZOMBIES WOW SUCH SKILL VERY AIM ");
		System.out.println("//////////////////////////////////////////////////////////");
		/////////////
		//GAME END //
		/////////////
		Display.destroy();
	}
	
	
	public static void temp1(){
		//test inventrory
		Player.inventory.add(new Gun(ItemManager.guns.get(0)));
		
		//test chunks
		for(int i = 0 ; i < 10000 ; i+= 650){
			for(int j = 0 ; j < 10000 ; j+= 650){
				WorldManager.chunks.add(new Chunk(i,j));
			}
		}
		System.out.println("Se han creado "+WorldManager.chunks.size()+" chunks");

		//test drop
		WorldManager.dropItem(ItemManager.buildeable.get(0), -100, 500, false);
		WorldManager.dropItem(ItemManager.buildeable.get(1), -100, 550, false);
		WorldManager.dropItem(ItemManager.buildeable.get(2), -100, 600, false);
		
		WorldManager.dropItem(ItemManager.guns.get(1), 100, 900, false);
		WorldManager.dropItem(ItemManager.guns.get(2), 100, 950, false);
		WorldManager.dropItem(ItemManager.guns.get(3), 100, 1000, false);
		
		//test environtment
		for(int i = 0 ; i < 10000 ; i+= 65)
			for(int j = 0 ; j < 10000 ; j+= 65)
				WorldManager.build(new Struct(i,j,ItemKind.FLOOR,3), false);
		
		
		for(int i = 0 ; i < WIDTH ; i+=65){
			WorldManager.build(new Struct(i,65*13,ItemKind.WALL,2), false);
		}
		
		WorldManager.build(new Struct(1000,1000,ItemKind.WALL,2),false);
		
		WorldManager.build(new Struct(65*10,65*5,ItemKind.WALL,2), false);
		WorldManager.build(new Struct(65*7,65*6,ItemKind.WALL,2), false);
		WorldManager.build(new Struct(65*7,65*7,ItemKind.WALL,2), false);
		WorldManager.build(new Struct(65*9,65*7,ItemKind.WALL,2), false);
		
		WorldManager.build(new Struct(65*20,65*5,ItemKind.HALFWALL,4), false);
		WorldManager.build(new Struct(65*20,65*6,ItemKind.HALFWALL,4), false);
		WorldManager.build(new Struct(65*20,65*7,ItemKind.HALFWALL,4), false);
		
		//test zombie
		NpcManager.spawnNpc(0, 100, 100, 90);
		
		NpcManager.spawnNpc(2, 1500, 1000, new Random().nextInt(360));
		
		NpcManager.spawnNpc(3, 2500, 1000, new Random().nextInt(360));
		NpcManager.spawnNpc(3, 2500, 1060, new Random().nextInt(360));
		NpcManager.spawnNpc(3, 2500, 1120, new Random().nextInt(360));
		
		NpcManager.spawnNpc(4, 2700, 1000, new Random().nextInt(360));
		NpcManager.spawnNpc(4, 2700, 1060, new Random().nextInt(360));
		NpcManager.spawnNpc(4, 2700, 1120, new Random().nextInt(360));
	}
	
	
	public static void main(String[] args){
		new Test();
	}
}
