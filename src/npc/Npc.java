package npc;

import java.util.ArrayList;
import java.util.Random;

import graphics.DisplayManager;
import items.Item;
import items.ItemKind;
import player.Player;
import structs.Struct;
import world.Chunk;
import world.WorldManager;

public class Npc {
	public float x,y,rotation,sx,sy,lx,ly;
	public float speed;
	private boolean collision;
	
	public NpcKind npcKind;
	public NpcBehaviour npcBehaviour; 
	
	public int textureID;
	
	public int hp;
	public boolean alive;
	private long deathTime;
	private long reviveTime = 5000;
	public boolean canMove;
	public boolean canDisapear;
	
	private boolean takingAction;
	private boolean scaredByPlayer;
	
	public ArrayList<Item> inventory = new ArrayList<Item>();
	
	public Npc(final float x,final float y,final float rotation,final float speed,final int textureID){
		this.x = x;
		this.y = y;
		sx = x;
		sy = y;
		this.rotation = rotation;
		this.speed = speed;
		this.textureID = textureID;
		
		hp = 100;
		alive = true;
		canDisapear = false;
		canMove = true;
		
		takingAction = false;
		scaredByPlayer = false;
		
		collision = false;
	}
	
	public Npc(final float speed,final int textureID, NpcKind npcKind, NpcBehaviour npcBehaviour){	
		this.speed = speed;
		this.textureID = textureID;
		
		this.npcKind = npcKind;
		this.npcBehaviour = npcBehaviour;
		
		hp = 100;
		alive = true;
		canDisapear = false;
		canMove = true;
		
		takingAction = false;
		scaredByPlayer = false;

		collision = false;
	}
	
	public Npc(Npc npc){
		speed = npc.speed;
		textureID = npc.textureID;
		
		this.npcKind = npc.npcKind;
		this.npcBehaviour = npc.npcBehaviour;
		
		hp = 100;
		alive = true;
		canDisapear = false;
		canMove = true;
		
		takingAction = false;
		scaredByPlayer = false;
		
		collision = false;
	}
	
	public void setPosition(float x, float y, float rotation){
		this.x = x;
		this.y = y;
		sx = x;
		sy = y;
		this.rotation = rotation;
	}
	
	public void move(){
		
		if(alive){
			// hp test
			if(hp <= 0){
				die(false);
				return;
			}
			
			// behaviours
			if(npcKind == NpcKind.ZOMBIE){
				//follow the player
				if(canMove){
					if(Math.sqrt(Math.pow((x - Player.x),2) + Math.pow(y - Player.y, 2)) < 600){
						takingAction = false;
						speed = 0.25f;
						lookToPos(Player.x, Player.y);
						if(Math.sqrt(Math.pow((x - Player.x),2) + Math.pow(y - Player.y, 2)) > 75) walk();
					}else{
					//random movement	
						if(!takingAction){
							takingAction = true;
							speed /= 5;
							rotation = new Random().nextInt(360);
						}
						if((Math.sqrt(Math.pow(x - sx, 2)+Math.pow(y - sy, 2)) > 200)){
							lookToPos(sx,sy); 
						}
						walk();	
					}
				}
				
			}else if(npcKind == NpcKind.HUMAN){
				
				//AGGRESSIVE NPC
				if(npcBehaviour == NpcBehaviour.AGGRESSIVE){
					boolean actionDone = false;
					
					// attack zombies
					for(Npc iter : WorldManager.npcs){
						if(iter.npcKind == NpcKind.ZOMBIE){
							if(iter.alive)
							if(Math.sqrt(Math.pow((x - iter.x),2) + Math.pow(y - iter.y, 2)) < 800){
								lookToPos(iter.x , iter.y);
								shoot();
								actionDone = true;
								break;
							}
						}
					}
					
					// attack player
					if(!actionDone)
					if(Math.sqrt(Math.pow((x - Player.x),2) + Math.pow(y - Player.y, 2)) < 400){
						lookToPos(Player.x , Player.y);
						shoot();
					}
					
					
					
				}
				
				// REACTIVE NPC
				if(npcBehaviour == NpcBehaviour.REACTIVE){
					// attack zombies
					for(Npc iter : WorldManager.npcs){
						if(iter.npcKind == NpcKind.ZOMBIE){
							if(iter.canMove && iter.alive)
							if(Math.sqrt(Math.pow((x - iter.x),2) + Math.pow(y - iter.y, 2)) < 800){
								lookToPos(iter.x , iter.y);
								shoot();
								break;
							}
						}
					}				
				}
				
				// PASSIVE NPC
				if(npcBehaviour == NpcBehaviour.PASSIVE){
					// escape from zombies
					for(Npc iter : WorldManager.npcs){
						if(iter.npcKind == NpcKind.ZOMBIE){
							if(iter.canMove)
							if(Math.sqrt(Math.pow((x - iter.x),2) + Math.pow(y - iter.y, 2)) < 500){
								lookToPos(iter.x,iter.y);
								rotation += 180;
								walk();
							}
						}
					}
				}
			}
			
			//Revive as zobmie
			if(!canMove){
				if((System.currentTimeMillis() - deathTime) > reviveTime){
					canMove = true;
				}
			}
			
		}else{
			//-
			if((System.currentTimeMillis() - deathTime) > 4000 ){
				canDisapear = true;
			}
		}

	}

	
	private void lookToPos(float x, float y){		
		float distanceX = (this.x - x);
		float distanceZ = (this.y - y);		
		float distance = (float) Math.sqrt(Math.pow(distanceX , 2) + Math.pow(distanceZ , 2));
		
		float alfa = (float)Math.toDegrees(Math.acos(distanceZ / distance));
		
		if(x >= this.x) rotation = alfa; else rotation = -alfa;
	}
	
	private void shoot(){
		for(Item iter : inventory){
			if(iter.itemKind == ItemKind.GUN){
				float ssx = (float) (x + Math.sin(Math.toRadians(-rotation))*-50);
				float ssy = (float) (y + Math.cos(Math.toRadians(-rotation))*-50);
				iter.shoot(ssx, ssy, rotation-90,false);
				break;
			}
		}
	}
	
	private void walk(){		
		x += Math.sin(Math.toRadians(-rotation+180))*speed*DisplayManager.delta;
		y += Math.cos(Math.toRadians(-rotation+180))*speed*DisplayManager.delta;
		
		checkCollisions();
	}
	
	private void checkCollisions(){
		boolean stop = false;
		
		for(Chunk iterc : WorldManager.chunks){
			//walls
			for(Struct iter : iterc.walls){
				if((iter.y+iter.sizeY > y)&&(iter.y-iter.sizeY < y)&&(iter.x+iter.sizeX > x)&&(iter.x-iter.sizeX < x)){
					collision = true;
					stop = true;
					break;
				}
			}
			if(stop) break;
			//half walls
			for(Struct iter : iterc.halfWalls){
				if((iter.y+iter.sizeY > y)&&(iter.y-iter.sizeY < y)&&(iter.x+iter.sizeX > x)&&(iter.x-iter.sizeX < x)){
					collision = true;
					stop = true;
					break;
				}
			}
			if(stop) break;
		}
		
		if(collision){				
			x = lx;
			y = ly;
			
			collision = false;
			rotation++;
			
			walk();
		}else{	
			lx = x;
			ly = y;
		}
	}
	
	public void die(boolean headShoot){
		if(npcKind == NpcKind.HUMAN){
			if(headShoot){
				for(Item iter : inventory) WorldManager.dropItem(iter, x, y, true);
				deathTime = System.currentTimeMillis();
				hp = 0;
				textureID = 1;
				alive = false;
			}else{
				npcKind = NpcKind.ZOMBIE;
				hp = 100;
				canMove = false;
				deathTime = System.currentTimeMillis();
			}
		}else{
			for(Item iter : inventory) WorldManager.dropItem(iter, x, y, true);
			deathTime = System.currentTimeMillis();
			hp = 0;
			textureID = 1;
			alive = false;
			canMove = false;
		}
		
	}
	
	
	
}
