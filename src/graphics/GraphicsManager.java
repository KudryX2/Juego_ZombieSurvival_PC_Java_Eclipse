package graphics;

import static graphics.DisplayManager.HEIGHT;
import static graphics.DisplayManager.WIDTH;
import static graphics.Sprite.drawSprite;

import org.lwjgl.input.Mouse;

import items.DropedItem;
import items.Item;
import items.ItemKind;
import items.Projectile;
import npc.Npc;
import player.Player;
import structs.Struct;
import world.Chunk;
import world.WorldManager;

public class GraphicsManager {
	
	public static float SCALE = WIDTH/100;
	
	public static void render(){
		drawEnvirontment();
		drawNpcs();
		drawDropedItems();
		drawPlayerStuff();
		drawGui();
	}
	
	public static void drawEnvirontment(){
		
		for(Chunk iterc : WorldManager.activeChunks){
			// FLOOR
			for(Struct iter : iterc.floors){
				// 32 mitad del tamaño total													  WIDTH = render dist
				if((Math.sqrt( Math.pow(iter.x - Player.x, 2) + Math.pow(iter.y - Player.y, 2)) < WIDTH)&&(true)){
					drawSprite(TexturesManager.environtmentTextures.get(iter.textureID), -(Player.x - DisplayManager.WIDTH/2) + iter.x - iter.sizeX/2 , -(Player.y - DisplayManager.HEIGHT/2) + iter.y - iter.sizeY/2 , iter.sizeX ,iter.sizeY );
				}
			}
			
			// PROJECTILES (no cambiar el orden de renderizado)
			if(!WorldManager.projectiles.isEmpty())
			for(Projectile iter : WorldManager.projectiles){
				drawSprite(TexturesManager.efectTextures.get(iter.textureID), -(Player.x - DisplayManager.WIDTH/2) + iter.x - 2.5f ,-(Player.y - DisplayManager.HEIGHT/2) + iter.y - 2.5f , 5 , 5 , iter.rotation);
			}
			
			// WALLS
			for(Struct iter : iterc.walls){
				// 32 mitad del tamaño total
				if((Math.sqrt( Math.pow(iter.x - Player.x, 2) + Math.pow(iter.y - Player.y, 2)) < WIDTH)&&(true)){
					drawSprite(TexturesManager.environtmentTextures.get(iter.textureID), -(Player.x - DisplayManager.WIDTH/2) +iter.x - iter.sizeX/2 , -(Player.y - DisplayManager.HEIGHT/2) + iter.y - iter.sizeY/2 , iter.sizeX , iter.sizeX );
				}
			}
			
			// HALF WALSS
			for(Struct iter : iterc.halfWalls){
				// 32 mitad del tamaño total
				if((Math.sqrt( Math.pow(iter.x - Player.x, 2) + Math.pow(iter.y - Player.y, 2)) < WIDTH)&&(true)){
					drawSprite(TexturesManager.environtmentTextures.get(iter.textureID), -(Player.x - DisplayManager.WIDTH/2) +iter.x - iter.sizeX/2 , -(Player.y - DisplayManager.HEIGHT/2) + iter.y - iter.sizeY/2  , iter.sizeX , iter.sizeY );
				}
			}
			
		}
	}
	
	public static void drawNpcs(){
		// ZOMBIES
		if(!WorldManager.npcs.isEmpty())
		for(Npc iter : WorldManager.npcs){
			if((Math.sqrt( Math.pow(iter.x - Player.x, 2) + Math.pow(iter.y - Player.y, 2)) < WIDTH)&&(true)){
				drawSprite(TexturesManager.zombieTextures.get(iter.textureID), -(Player.x - DisplayManager.WIDTH/2) +iter.x - 32 , -(Player.y - DisplayManager.HEIGHT/2) + iter.y - 32 , 64 ,64 , iter.rotation );
				if((iter.hp > 0)&&(iter.hp < 100)){
					drawSprite(TexturesManager.guiTextures.get(8),-(Player.x - DisplayManager.WIDTH/2) +iter.x-32 , -(Player.y - DisplayManager.HEIGHT/2 + 40) + iter.y - 5 , 50 ,5 );
					if(iter.hp > 50) drawSprite(TexturesManager.guiTextures.get(3),-(Player.x - DisplayManager.WIDTH/2) +iter.x-32 , -(Player.y - DisplayManager.HEIGHT/2 + 40) + iter.y - 5 , iter.hp/2 ,5 );
					else if(iter.hp > 25) drawSprite(TexturesManager.guiTextures.get(6),-(Player.x - DisplayManager.WIDTH/2) +iter.x-32 , -(Player.y - DisplayManager.HEIGHT/2 + 40) + iter.y - 5 , iter.hp/2 ,5 );	
					else drawSprite(TexturesManager.guiTextures.get(7),-(Player.x - DisplayManager.WIDTH/2) +iter.x-32 , -(Player.y - DisplayManager.HEIGHT/2 + 40) + iter.y - 5 , iter.hp/2 ,5 );
				}
			}
		}

	}
	
	public static void drawPlayerStuff(){		
		// PLAYER SPRITE
		drawSprite(TexturesManager.playerTextures.get(Player.textureID), WIDTH/2-50, HEIGHT/2-50, 100, 100, Player.rotation - 90); //rotation - 90 porque el sprite esta mal	
		// Player helath
		if(Player.hp > 0){
			drawSprite(TexturesManager.guiTextures.get(8),DisplayManager.WIDTH/2-Player.SIZE/2,DisplayManager.HEIGHT/2-Player.SIZE-5, 50 ,5 );
			if(Player.hp > 50) drawSprite(TexturesManager.guiTextures.get(3),DisplayManager.WIDTH/2-Player.SIZE/2 ,DisplayManager.HEIGHT/2-Player.SIZE-5, Player.hp/2 ,5 );
			else if(Player.hp > 25) drawSprite(TexturesManager.guiTextures.get(6),DisplayManager.WIDTH/2-Player.SIZE/2,DisplayManager.HEIGHT/2-Player.SIZE-5, Player.hp/2 ,5 );	
			else drawSprite(TexturesManager.guiTextures.get(7),DisplayManager.WIDTH/2-Player.SIZE/2,DisplayManager.HEIGHT/2-Player.SIZE-5, Player.hp/2 ,5 );
		}
		// Player hand
		float startPosX = (float) (DisplayManager.WIDTH/2 + Math.sin(Math.toRadians(-Player.rotation+90))*40);
		float startPosY = (float) (DisplayManager.HEIGHT/2 + Math.cos(Math.toRadians(-Player.rotation+90))*40);
		
		if(Player.inventory.size() > Player.selectedCell)
			if(Player.inventory.get(Player.selectedCell) != null){
				//guns
				if(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.GUN){
					drawSprite(TexturesManager.weaponEquipedTextures.get(Player.inventory.get(Player.selectedCell).equipedTextureID), startPosX-25, startPosY-25, 50, 50,Player.rotation);
				}
				//consumible
				if(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.CONSUMIBLE){
	
				}
				//buildeable
				if(Player.buildMode){
					if(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.WALL){
						drawSprite(TexturesManager.environtmentTextures.get(Player.inventory.get(Player.selectedCell).equipedTextureID), startPosX-20, startPosY-20, 40, 40,Player.rotation);
					}else if(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.HALFWALL){
						drawSprite(TexturesManager.environtmentTextures.get(Player.inventory.get(Player.selectedCell).equipedTextureID), startPosX-20, startPosY-20, 40, 40,Player.rotation);
					}else if(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.FLOOR){
						drawSprite(TexturesManager.environtmentTextures.get(Player.inventory.get(Player.selectedCell).equipedTextureID), startPosX-20, startPosY-20, 40, 40,Player.rotation);
					}
				}
			}
	}
	
	public static void drawGui(){
		// INVENTORY		
		int posX = 5;
		int cont = 0;
		for(Item iter : Player.inventory){	
			if((iter.itemKind == ItemKind.FLOOR)||(iter.itemKind == ItemKind.HALFWALL)||(iter.itemKind == ItemKind.WALL)){
				drawSprite(TexturesManager.environtmentTextures.get(iter.cellTextureID), WIDTH/2+posX+SCALE*cont, HEIGHT/1.1f+5, 40+SCALE, 40+SCALE);
			}else if(iter.itemKind == ItemKind.GUN){
				drawSprite(TexturesManager.weaponTextures.get(iter.cellTextureID), WIDTH/2+posX+SCALE*cont, HEIGHT/1.1f+5, 40+SCALE, 40+SCALE);
			}	
			posX += 50;
			cont++;
		}
		// HAND CELLS
		drawSprite(TexturesManager.guiTextures.get(2), WIDTH/2+SCALE*0, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		drawSprite(TexturesManager.guiTextures.get(2), WIDTH/2+50+SCALE*1, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		drawSprite(TexturesManager.guiTextures.get(2), WIDTH/2+100+SCALE*2, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		drawSprite(TexturesManager.guiTextures.get(2), WIDTH/2+150+SCALE*3, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		if(Player.selectedCell == 0) 	 drawSprite(TexturesManager.guiTextures.get(1), WIDTH/2+SCALE*0, HEIGHT/1.1f, 50+SCALE, 50+SCALE); 
		else if(Player.selectedCell == 1)drawSprite(TexturesManager.guiTextures.get(1), WIDTH/2+50+SCALE*1, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		else if(Player.selectedCell == 2)drawSprite(TexturesManager.guiTextures.get(1), WIDTH/2+100+SCALE*2, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		else if(Player.selectedCell == 3)drawSprite(TexturesManager.guiTextures.get(1), WIDTH/2+150+SCALE*3, HEIGHT/1.1f, 50+SCALE, 50+SCALE);
		
		// CURSOR
		if((Player.buildMode)&&(Player.inventory.size() > Player.selectedCell)&&((Player.inventory.get(Player.selectedCell).itemKind == ItemKind.FLOOR)||
				(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.WALL)||(Player.inventory.get(Player.selectedCell).itemKind == ItemKind.HALFWALL))){
			if(Player.canBuild){
				drawSprite(TexturesManager.guiTextures.get(4), Mouse.getX()-32, HEIGHT - Mouse.getY() -32 , 64,64);
			}else{
				drawSprite(TexturesManager.guiTextures.get(5), Mouse.getX()-32, HEIGHT - Mouse.getY() -32 , 64,64);
			}
		}else{
			drawSprite(TexturesManager.guiTextures.get(0), Mouse.getX()-20-SCALE/2, HEIGHT-SCALE/2 - Mouse.getY() -20 , 40+SCALE, 40+SCALE);
		}
		
	}
	
	public static void drawDropedItems(){
		for(Chunk iter : WorldManager.activeChunks){
			for(DropedItem i : iter.droppedItems){
				if(i.item.itemKind == ItemKind.GUN){
					drawSprite(TexturesManager.weaponTextures.get(i.item.cellTextureID), -(Player.x - DisplayManager.WIDTH/2) + i.x-16 , -(Player.y - DisplayManager.HEIGHT/2) +  i.y-16 , 32, 32);
				}else if((i.item.itemKind == ItemKind.FLOOR)||(i.item.itemKind == ItemKind.HALFWALL)||(i.item.itemKind == ItemKind.WALL)){
					drawSprite(TexturesManager.environtmentTextures.get(i.item.cellTextureID), -(Player.x - DisplayManager.WIDTH/2) + i.x-16 , -(Player.y - DisplayManager.HEIGHT/2) +  i.y-16 , 32, 32);
				}
			}
		}
	}
	
	
	
}
