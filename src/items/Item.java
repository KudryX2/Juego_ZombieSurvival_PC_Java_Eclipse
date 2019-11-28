package items;

public abstract class Item {
	public final String name;
	public final ItemKind itemKind;
	public final int cellTextureID, equipedTextureID;
	
	public Item(final String name, final ItemKind itemKind, final int cellTextureID, final int equipedTextureID){
		this.name = name;
		this.itemKind = itemKind;
		this.cellTextureID = cellTextureID;
		this.equipedTextureID = equipedTextureID;
	}

	public void shoot(float x, float y, float rotation, boolean shotByPlayer) {
		//METODO CON OVERRIDE EN CLASE GUN
	}

}
