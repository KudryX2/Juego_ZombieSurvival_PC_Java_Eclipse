package items;

public class DropedItem{
	public float x,y;
	public Item item;
	
	public DropedItem(Item item, float x, float y){
		this.item = item;
		this.x = x;
		this.y = y;
	}
}
