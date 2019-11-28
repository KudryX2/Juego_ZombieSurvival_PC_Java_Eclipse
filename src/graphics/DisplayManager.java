package graphics;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public final class DisplayManager {
	public static int WIDTH = 1200;
	public static int HEIGHT = 800;
	public static final int FPS = 120;
	public static final String title = "Too Late To Retrieve 2D";
	
	public static float delta;
	private static long lastTime = System.nanoTime();
	
	public static void init(boolean fullScreen){
		Display.setTitle(title);
		
		if(fullScreen){
			try {
				Display.setFullscreen(true);
				WIDTH = Display.getWidth();
				HEIGHT = Display.getHeight();
				Display.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}else{
			try {
				Display.setDisplayMode(new DisplayMode(WIDTH,HEIGHT));
				Display.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		Mouse.setGrabbed(true);

	}
	
	public static void update(){
		Display.sync(FPS);
		Display.update();
		glClear(GL_COLOR_BUFFER_BIT); //TEMPORAL despues de tener la pantalla dibujada puede que no se necsite (solo con huecos)
	
		long actualTime = System.nanoTime();
		delta = (actualTime - lastTime)/1000000;
		lastTime = actualTime;
	}
	
	public static void drawQuad(final float x, final float y, final float width, final float height){
		glBegin(GL_QUADS);
		glVertex2f(x,y);
		glVertex2f(x+width,y);
		glVertex2f(x+width,y+height);
		glVertex2f(x,y+height);
		glEnd();
	}
	
	public static void drawLine(final float x, final float y, final float x2, final float y2){
		glBegin(GL_LINES);
		glVertex2f(x,y);
		glVertex2f(x2,y2);
		glEnd();
	}
	
	
	
}
