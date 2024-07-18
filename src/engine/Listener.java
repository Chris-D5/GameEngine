package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public abstract class Listener {
	protected boolean[] buttons;
	protected static Listener listener;
	
	public static void buttonCallback(long window, int button, int action, int mod) {
		if (button >= listener.buttons.length) {return;}
		if (action == GLFW_PRESS) {
			listener.buttons[button] = true;
		}
		else if (action == GLFW_RELEASE) {
			listener.buttons[button] = false;
		}
	}
	
	public static boolean getButton(int button) {
		if (button >= listener.buttons.length) {
			return false;
		}
		return listener.buttons[button];
	}
	
	public static void changeListener (Listener listener) {
		Listener.listener = listener;
	}
}
