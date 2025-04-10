package Components;

import org.joml.Vector2f;

public class Transform extends Component{
	private Vector2f position;
	private Vector2f scale;
	
	public Transform(Vector2f position, Vector2f scale) {
		this.position = position;
		this.scale = scale;
	}
	
	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public Vector2f getScale() {
		return scale;
	}
	
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
	@Override
	public void start() {
		
	}

	@Override
	public void update() {
		
	}
}
