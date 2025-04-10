package renderer;

import java.util.ArrayList;
import java.util.List;

import Components.Sprite;
import engine.GameObject;

public class Renderer {
	final private int DEFAULTBATCHSIZE = 10; 
	
	private List<Batch> batches;
	
	public void render() {
		for (Batch batch : batches) {
			batch.render();
		}
	}
	
	public void add(GameObject gameObject) {
		Sprite sprite = gameObject.getComponent(Sprite.class);
		if (batches == null) {
			batches = new ArrayList<Batch>();
		}
		if (batches.size() == 0) {
			batches.add(new Batch(DEFAULTBATCHSIZE));
		}
		if (batches.get(batches.size()-1).isFull()) {
			batches.add(new Batch(DEFAULTBATCHSIZE));
		}
		batches.get(batches.size()-1).addSprite(sprite);
	}
	
}
