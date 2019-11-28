package graphics;

import static graphics.Sprite.loadTexture;

import java.util.ArrayList;
import org.newdawn.slick.opengl.Texture;

public final class TexturesManager {
	
	public static ArrayList<Texture> environtmentTextures = new ArrayList<Texture>(); //contructions
	public static ArrayList<Texture> efectTextures = new ArrayList<Texture>();		  //efects
	public static ArrayList<Texture> guiTextures = new ArrayList<Texture>();          //HUD
	public static ArrayList<Texture> playerTextures = new ArrayList<Texture>();		  //player
	public static ArrayList<Texture> zombieTextures = new ArrayList<Texture>();       //zombies
	public static ArrayList<Texture> weaponTextures = new ArrayList<Texture>();		  //weapons
	public static ArrayList<Texture> weaponEquipedTextures = new ArrayList<Texture>();//equiped weapons extures
	
	public static void init(){
		initPlayerTextures();
		initGuiTextures();
		initEnvirontmentTextures();
		initZombieTextures();
		initWeaponTextures();
		initEfectTextures();
	}
	
	private static void initEfectTextures(){
		efectTextures.add(loadTexture("res/0-bullet.png","PNG"));         // 0
	}
	
	private static void initPlayerTextures(){
		playerTextures.add(loadTexture("res/1-player.png","PNG"));        // 0
	}
	
	private static void initGuiTextures(){
		guiTextures.add(loadTexture("res/2-pointer.png","PNG"));          // 0
		guiTextures.add(loadTexture("res/2-selectedCell.png","PNG"));     // 1
		guiTextures.add(loadTexture("res/2-normalCell.png","PNG"));       // 2
		guiTextures.add(loadTexture("res/2-hp.png","PNG"));               // 3
		guiTextures.add(loadTexture("res/2-buildModePointer.png","PNG")); // 4
		guiTextures.add(loadTexture("res/2-buildModePointer1.png","PNG"));// 5
		guiTextures.add(loadTexture("res/2-hp1.png","PNG"));              // 6
		guiTextures.add(loadTexture("res/2-hp2.png","PNG"));              // 7
		guiTextures.add(loadTexture("res/2-hpFont.png","PNG"));           // 8
	}
	
	private static void initEnvirontmentTextures(){
		environtmentTextures.add(loadTexture("res/3-Teemo.png","PNG"));    // 0	
		environtmentTextures.add(loadTexture("res/3-dirt.png","PNG"));     // 1
		environtmentTextures.add(loadTexture("res/3-wall.png","PNG"));     // 2
		environtmentTextures.add(loadTexture("res/3-grass.png","PNG"));    // 3		
		environtmentTextures.add(loadTexture("res/3-halfWall.png","PNG")); // 4
	}
	
	private static void initZombieTextures(){
		zombieTextures.add(loadTexture("res/4-zombie.png","PNG"));		   // 0
		zombieTextures.add(loadTexture("res/4-blood.png","PNG"));		   // 1
		zombieTextures.add(loadTexture("res/4-zombie1.png","PNG"));		   // 2
	}
	
	private static void initWeaponTextures(){
		weaponTextures.add(loadTexture("res/5-pistol.png","PNG"));	       // 0
		weaponTextures.add(loadTexture("res/5-shootgun.png","PNG"));       // 1
		weaponTextures.add(loadTexture("res/5-sniper.png","PNG"));	       // 2
		weaponTextures.add(loadTexture("res/5-ak47.png","PNG"));	       // 3
		
		weaponEquipedTextures.add(loadTexture("res/6-pistol.png","PNG"));  // 0
		weaponEquipedTextures.add(loadTexture("res/6-shootgun.png","PNG"));// 1
		weaponEquipedTextures.add(loadTexture("res/6-sniper.png","PNG"));  // 2
		weaponEquipedTextures.add(loadTexture("res/6-sniper.png","PNG"));  // 3
	}
	
	
}
