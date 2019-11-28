package items;

import player.Player;
import world.WorldManager;

public class Gun extends Item {

	public GunKind gunKind;
	public long lastShot = 0;
	public final float SPEED;
	public final int SCOPE, DAMAGE;
	public final int RATE;
	
	public Gun(String name, GunKind gunKind, int cellTextureID, int equipedTextureID, int scope, float speed, int damage, int rate) {
		super(name, ItemKind.GUN, cellTextureID, equipedTextureID);
		this.gunKind = gunKind;
		this.SCOPE = scope;
		this.SPEED = speed;
		this.DAMAGE = damage;
		this.RATE = rate;
	}
	
	public Gun(Gun gun){
		super(gun.name, ItemKind.GUN, gun.cellTextureID, gun.equipedTextureID);
		this.gunKind = gun.gunKind;
		this.SCOPE = gun.SCOPE;
		this.SPEED = gun.SPEED;
		this.DAMAGE = gun.DAMAGE;
		this.RATE = gun.RATE;
	}
	
	@Override
	public void shoot(float x, float y, float rotation, boolean shotByPlayer){
		
		if(shotByPlayer){
			if(System.currentTimeMillis() - lastShot > RATE){
				if(gunKind == GunKind.ASSAULTRIFLE){
					WorldManager.projectiles.add(new Projectile(x ,y ,rotation,SPEED,SCOPE,DAMAGE,0));
				}else if(!Player.button0){
					if(gunKind == GunKind.PISTOL){
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation,SPEED,SCOPE,DAMAGE,0));
					}else if(gunKind == GunKind.SHOOTGUN){
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation+12,SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation+6, SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation,   SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation-6, SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation-12,SPEED,SCOPE,DAMAGE,0));
					}else if(gunKind == GunKind.SNIPER){
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation,SPEED,SCOPE,DAMAGE,0));
					}
				}
		
				lastShot = System.currentTimeMillis();
			}
		}else{
			if(System.currentTimeMillis() - lastShot > RATE*2){
				if(gunKind == GunKind.ASSAULTRIFLE){
					WorldManager.projectiles.add(new Projectile(x ,y ,rotation,SPEED,SCOPE,DAMAGE,0));
				}else if(!Player.button0){
					if(gunKind == GunKind.PISTOL){
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation,SPEED,SCOPE,DAMAGE,0));
					}else if(gunKind == GunKind.SHOOTGUN){
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation+12,SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation+6, SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation,   SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation-6, SPEED,SCOPE,DAMAGE,0));
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation-12,SPEED,SCOPE,DAMAGE,0));
					}else if(gunKind == GunKind.SNIPER){
						WorldManager.projectiles.add(new Projectile(x ,y ,rotation,SPEED,SCOPE,DAMAGE,0));
					}
				}
		
				lastShot = System.currentTimeMillis();
			}
		}
			
		
	}
	

}
