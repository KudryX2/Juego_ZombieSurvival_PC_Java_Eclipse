package npc;

import java.util.ArrayList;
import java.util.Random;

import items.Gun;
import items.ItemManager;
import world.WorldManager;

public class NpcManager {
	public static ArrayList<Npc> npcs = new ArrayList<Npc>();

	public static void init(){
		//Zombies
		npcs.add(new Npc(0.25f,0,NpcKind.ZOMBIE,NpcBehaviour.AGGRESSIVE)); // 0
		npcs.add(new Npc(0.25f,2,NpcKind.ZOMBIE,NpcBehaviour.AGGRESSIVE)); // 1
		//Humans
		npcs.add(new Npc(0.25f,2,NpcKind.HUMAN,NpcBehaviour.AGGRESSIVE));  // 2
		npcs.add(new Npc(0.25f,2,NpcKind.HUMAN,NpcBehaviour.REACTIVE));    // 3
		npcs.add(new Npc(0.25f,2,NpcKind.HUMAN,NpcBehaviour.PASSIVE));     // 4
	}
	
	public static void spawnNpc(int npcID,float x,float y,float rotation){
		Npc npc = new Npc(npcs.get(npcID));
		npc.setPosition(x, y, rotation);
		//human
		if(npcID > 1) npc.inventory.add(new Gun(ItemManager.guns.get(0)));
		//zombie
		if(npcID < 2) if(new Random().nextInt(10) < 2) npc.inventory.add(ItemManager.guns.get(new Random().nextInt(4)));
		
		
		WorldManager.npcs.add(npc);
	}

}
