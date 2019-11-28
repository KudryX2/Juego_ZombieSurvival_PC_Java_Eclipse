package structs;

import items.ItemKind;

public class Struct {
	public float x,y,sizeX,sizeY;
	public float hp;
	public final ItemKind structKind;
	public int textureID;
	
	public Struct(float x , float y , ItemKind structKind, int textureID){
		this.x = x;
		this.y = y;
		sizeX = 65;
		sizeY = 65;
		hp = 100;
		this.structKind = structKind;
		this.textureID = textureID;
	}
}
