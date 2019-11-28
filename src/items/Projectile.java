package items;

import graphics.DisplayManager;

public class Projectile{

	public float x,y;
	public final float sx,sy,speed,rotation;
	public final int scope,textureID,damage;
	
	
	public Projectile(final float x, final float y, final float rotation, final float speed, final int scope, int damage, final int textureID){
		this.x = x;
		this.y = y;
		sx = x;
		sy = y;
		this.rotation = rotation;
		this.speed = speed;
		this.scope = scope;
		this.damage = damage;
		this.textureID = textureID;
	}
	
	public void move(){
		x += Math.sin(Math.toRadians(-rotation+90))*speed*DisplayManager.delta;
		y += Math.cos(Math.toRadians(-rotation+90))*speed*DisplayManager.delta;
	}
	
}
