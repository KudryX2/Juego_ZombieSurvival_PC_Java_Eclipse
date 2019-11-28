package world;

import java.util.ArrayList;

import items.DropedItem;
import structs.Struct;

public final class Chunk {
	
	public final float x,y;
	public ArrayList<DropedItem> droppedItems = new ArrayList<DropedItem>();
	
	public ArrayList<Struct> walls = new ArrayList<Struct>();				//player,zombie,projectiles    coll
	public ArrayList<Struct> halfWalls = new ArrayList<Struct>();   //player,zombie                coll
	public ArrayList<Struct> floors = new ArrayList<Struct>();			//							no coll
	
	public Chunk(final float x, final float y){
		this.x = x;
		this.y = y;
	}
	

}
