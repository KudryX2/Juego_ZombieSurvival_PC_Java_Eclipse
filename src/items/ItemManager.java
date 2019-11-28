package items;

import java.util.ArrayList;

public class ItemManager {
	public static ArrayList<Gun> guns = new ArrayList<Gun>();
	public static ArrayList<Item> buildeable = new ArrayList<Item>();

	
	public static void init(){
		initGuns();
		initBuildeableItems();
	}
	
	public static void initGuns(){				
		guns.add(new Gun("Pistol",GunKind.PISTOL,0,0,600,3,35,100));
		guns.add(new Gun("Shootgun",GunKind.SHOOTGUN,1,1,350,3,50,400));
		guns.add(new Gun("Sniper",GunKind.SNIPER,2,2,1000,5,80,500));
		guns.add(new Gun("AK-47",GunKind.ASSAULTRIFLE,3,3,800,4,25,50));
	}
	
	public static void initBuildeableItems(){
		//WALLS
		buildeable.add(new StructItem("WALL",ItemKind.WALL,2,2));
		//HALF WALLS
		buildeable.add(new StructItem("HALF WALL",ItemKind.HALFWALL,4,4));
		//FLOORS
		buildeable.add(new StructItem("FLOOR",ItemKind.FLOOR,3,3));
	}
	
}
