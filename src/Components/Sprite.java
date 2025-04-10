package Components;

import java.util.Vector;

import org.joml.Vector4f;

public class Sprite extends Component{
	private String path;
	private Vector4f color;
	
	public Sprite(String path,Vector4f color) {
		this.path = path;
		this.color = color;
	}
	
	public String getPath () {
		return path;
	}
	
	public Vector4f getColor() {
		return color;
	}
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
