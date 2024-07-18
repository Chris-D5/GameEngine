package engine;


public class KeyListener extends Listener{
	private static KeyListener keyListener;
	
	private KeyListener() {	
		super.buttons = new boolean[349];
	}
	
	public static KeyListener get() {
		if (listener == null) {
			keyListener = new KeyListener();
		}
		return (KeyListener)keyListener;
	}
	
	public static void keyCallback(long window, int key, int scancode, int action, int mods) {
		Listener.changeListener(keyListener);
		Listener.buttonCallback(window, key, action, mods);
	}
	
}
