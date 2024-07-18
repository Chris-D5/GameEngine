package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener extends Listener{
	private static MouseListener mouseListener;
	
	private double xoffset, yoffset, xpos, ypos;
	
	private MouseListener() {	
		xoffset = 0;
		yoffset = 0;
		xpos = 0;
		ypos = 0;
		super.buttons = new boolean[8];
	}
	
	public static MouseListener get() {
		if (listener == null) {
			mouseListener = new MouseListener();
		}
		return (MouseListener)mouseListener;
	}
	
	public static void mouseCallback (long window, int button, int action, int mod) {
		Listener.changeListener(mouseListener);
		Listener.buttonCallback(window, button, action, mod);
	}
	
	public static void scrollCallback(long window, double xoffset, double yoffset) {
		get().xoffset = xoffset;
		get().yoffset = yoffset;
	}
	
	public static void cursorPositionCallback(long window, double xpos, double ypos) {
		get().xpos = xpos;
		get().ypos = ypos;
	}
	
	public static double getXPosition() {
		return get().xpos;
	}

	public static double getYPosition() {
		return get().ypos;
	}
	
	public static double getXScroll() {
		return get().xoffset;
	}
	
	public static double getYScroll() {
		return get().yoffset;
	}
}
