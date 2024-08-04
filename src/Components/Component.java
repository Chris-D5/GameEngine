package Components;

import engine.GameObject;

public abstract class Component {
	private GameObject gameObject;

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	abstract public void start();
	
	abstract public void update();
}
