package world;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import graphics.DisplayManager;
import items.DropedItem;
import items.Item;
import items.ItemKind;
import items.Projectile;
import npc.Npc;
import player.Player;
import structs.Struct;

public final class WorldManager {
	public static ArrayList<Chunk> chunks = new ArrayList<Chunk>();
	public static ArrayList<Chunk> activeChunks = new ArrayList<Chunk>();
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<Npc> npcs = new ArrayList<Npc>();
	
	public static void update(){
		updateProjectiles();
		updateNpcs();
		updateChunks();
	}
	
	private static void updateProjectiles(){
		boolean stop = false;
		
		//Projectile collisions
		for(Projectile iterp : projectiles){
			
			iterp.move();
			if(Math.sqrt(Math.pow(iterp.x - iterp.sx, 2) + Math.pow(iterp.y - iterp.sy, 2)) > iterp.scope){
				projectiles.remove(iterp);
				break;
			}
		
		//player	
			if((iterp.y+20 > Player.y)&&(iterp.y-20 < Player.y)&&(iterp.x+20 > Player.x)&&(iterp.x-20 < Player.x)){
				projectiles.remove(iterp);
				Player.hp -= iterp.damage/10;
				break;
			}
		//npcs	
			for(Npc iterz : npcs){
				if(iterz.alive){
					boolean collision = false;
					//size = 20;			
					if((iterp.y+20 > iterz.y)&&(iterp.y-20 < iterz.y)&&(iterp.x+20 > iterz.x)&&(iterp.x-20 < iterz.x)) collision = true;
					
					if(collision){
						// cx,cy = cursor position
						float cx = Player.x + Mouse.getX() - DisplayManager.WIDTH/2;
						float cy = Player.y + DisplayManager.HEIGHT/2 - Mouse.getY();
						
						if((iterp.y+20 > cy)&&(iterp.y-20 < cy)&&(iterp.x+20 > cx)&&(iterp.x-20 < cx)){
							iterz.die(true);
							projectiles.remove(iterp);
							stop = true;
							break;
						}else{
							iterz.hp -= iterp.damage;
							projectiles.remove(iterp);
							stop = true;
							break;
						}		
					}			
				}		
			}
			if(stop) break;
			
		//structs	
			for(Chunk iter : chunks){
				for(Struct iters : iter.walls){	
					boolean collision = false;			
					//size = 30;
					if((iterp.y+30 > iters.y)&&(iterp.y-30 < iters.y)&&(iterp.x+30 > iters.x)&&(iterp.x-30 < iters.x)) collision = true;
						
					if(collision){
						projectiles.remove(iterp);
						stop = true;
						break;
					}				
				}
				if(stop) break;
			}
			if(stop) break;
		}
	
	}

	private static void updateNpcs(){
		for(Npc iter : WorldManager.npcs){
			iter.move();
			if(iter.canDisapear){
				WorldManager.npcs.remove(iter);
				break;
			}
		}

	}
	
	private static void updateChunks(){
		for(Chunk iter : chunks){
			// DisplayManager.WIDTH*2 = dist para cargar
			if(Math.sqrt(Math.pow(iter.x - Player.x, 2)+ Math.pow(iter.y - Player.y, 2)) < DisplayManager.WIDTH*2){
				if(!activeChunks.contains(iter)) activeChunks.add(iter);
			}else{
				activeChunks.remove(iter);
			}
		}
	//	System.out.println("ACTIVE CHUNKS "+activeChunks.size());
	}
		
	public static void build(Struct struct, boolean buildedByPlayer){
		if(buildedByPlayer){
			Chunk closerChunk = activeChunks.get(0);
			
			for(Chunk iter : activeChunks){
				if(Math.sqrt(Math.pow(closerChunk.x - struct.x, 2)+Math.pow(closerChunk.y - struct.y, 2)) > Math.sqrt(Math.pow(iter.x - struct.x, 2)+Math.pow(iter.y - struct.y, 2))){
					closerChunk = iter;
				}
			}
			if(struct.structKind == ItemKind.FLOOR)    closerChunk.floors.add(struct);
			if(struct.structKind == ItemKind.HALFWALL) closerChunk.halfWalls.add(struct);
			if(struct.structKind == ItemKind.WALL)	 closerChunk.walls.add(struct);
			
		}else{
			Chunk closerChunk = chunks.get(0);
			
			for(Chunk iter : chunks){
				if(Math.sqrt(Math.pow(closerChunk.x - struct.x, 2)+Math.pow(closerChunk.y - struct.y, 2)) > Math.sqrt(Math.pow(iter.x - struct.x, 2)+Math.pow(iter.y - struct.y, 2))){
					closerChunk = iter;
				}
			}
			if(struct.structKind == ItemKind.FLOOR)    closerChunk.floors.add(struct);
			if(struct.structKind == ItemKind.HALFWALL) closerChunk.halfWalls.add(struct);
			if(struct.structKind == ItemKind.WALL)	 closerChunk.walls.add(struct);
		}
	}
	
	public static void dropItem(Item item, float x, float y, boolean dropedByPlayer){
		if(dropedByPlayer){
			Chunk closerChunk = activeChunks.get(0);
			for(Chunk iter : activeChunks){
				if(Math.sqrt(Math.pow(closerChunk.x - x, 2)+Math.pow(closerChunk.y - y, 2)) > Math.sqrt(Math.pow(iter.x - x, 2)+Math.pow(iter.y - y, 2))){
					closerChunk = iter;
				}
			}
			closerChunk.droppedItems.add(new DropedItem(item,x,y));
		}else{
			Chunk closerChunk = chunks.get(0);
			for(Chunk iter : chunks){
				if(Math.sqrt(Math.pow(closerChunk.x - x, 2)+Math.pow(closerChunk.y - y, 2)) > Math.sqrt(Math.pow(iter.x - x, 2)+Math.pow(iter.y - y, 2))){
					closerChunk = iter;
				}
			}
			closerChunk.droppedItems.add(new DropedItem(item,x,y));
		}
	}
	
}
