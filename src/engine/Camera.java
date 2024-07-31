package engine;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Matrix4f;

public class Camera {
	private Matrix4f ProjectionMatrix, ViewMatrix;
	private Vector2f position;
	
	public Camera (Vector2f position) {
		this.position = position;
		this.ProjectionMatrix = new Matrix4f();
		this.ViewMatrix = new Matrix4f();
		ProjectionMatrix.identity();
		ProjectionMatrix.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
	}
	
	public void changePosition(Vector2f position) {
		this.position = position;
	} 
	
	public Matrix4f getViewMatrix() {
		this.ViewMatrix.identity();
		this.ViewMatrix.lookAt(new Vector3f(this.position.x,this.position.y,100f)/*eye*/,
							   new Vector3f(this.position.x,this.position.y,-1f) /*center*/,
							   new Vector3f(0f,1f,0f)) /*up*/;
		return this.ViewMatrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return this.ProjectionMatrix;
	}
}
