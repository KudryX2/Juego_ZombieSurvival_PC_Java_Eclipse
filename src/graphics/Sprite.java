package graphics;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public final class Sprite {
	
	public static void drawSprite(Texture texture, float x, float y, float width, float height){
		texture.bind();
		glTranslatef(x, y, 0);
		 
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0,1);
		glVertex2f(0, height);
		glEnd();
		
		glLoadIdentity();
	}
	
	public static void drawSprite(Texture texture, float x, float y, float width, float height, float rotation){
		texture.bind();
		
		glTranslatef(x + width/2, y + height/2, 0);
		glRotatef(rotation,0,0,1); 
		glTranslatef(-width/2, -height/2, 0);
		
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0,1);
		glVertex2f(0, height);
		glEnd();
		
		glLoadIdentity();
	}
	
	public static Texture loadTexture(String path, String fileType){
		Texture texture = null;
		
		try {
			InputStream inputStream = ResourceLoader.getResourceAsStream(path);
			texture = TextureLoader.getTexture(fileType, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(texture == null) System.out.println("Problemas con textura");
		
		return texture;
	}
}
