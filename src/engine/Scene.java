package engine;


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import Components.Sprite;
import Components.*;
import renderer.Renderer;
import renderer.Shader;
import renderer.Texture;

public class Scene {
	
	private Camera camera;
	
	private List<GameObject> gameObjects;
	
	private int vertexID, fragmentID, shaderProgram;
	
	private float[] vertexArray = {
			//position 				//color					//texture
			100.5f, -0.5f, 0.0f,	1.0f, 0.0f, 0.0f, 0.0f, 1,1,//bot right
			-0.5f, 100.5f, 0.0f,	0.0f, 1.0f, 0.0f, 1.0f, 0,0,//top left
			100.5f, 100.5f, 0.0f,	1.0f, 0.0f, 1.0f, 1.0f, 1,0,//top right
			-0.5f, -0.5f, 0.0f,		1.0f, 1.0f, 0.0f, 1.0f, 0,1//bot left
			
	};
	
	private int[] elementArray = {
			2,1,0, //top right
			0,1,3 //bot left
	};
	
	private int vaoID, vboID, eboID;
	
	private Shader defaultShader;
	private Texture defaultTexture;
	
	private Renderer renderer;
	
	
	public Camera getCamera() {
		return camera;
	}
	
	public Scene() {
		init();
		System.out.println("Scene Created");
	};
	
	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
		gameObject.start();
	}
	
	public void update() {
		renderer.render();
		/*
		defaultShader.use();
		defaultShader.addMatrix("uProjection", camera.getProjectionMatrix());
		defaultShader.addMatrix("uView", camera.getViewMatrix());
		
		
		glActiveTexture(GL_TEXTURE0);
		defaultShader.addTexture("TEX_SAMPLER", 0);
		defaultTexture.bind();
		
		glBindVertexArray(vaoID);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glDrawElements(GL_TRIANGLES,elementArray.length,GL_UNSIGNED_INT,0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glBindVertexArray(0);
		
		defaultShader.detach();
		*/
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}
	}
	
	public void init() {
		gameObjects = new ArrayList<GameObject>();
		
		camera = new Camera(new Vector2f(0f,0f));
		GameObject flower = new GameObject();
		Sprite flowerSprite = new Sprite("Assets/Textures/testFlower.jpg",new Vector4f(1.0f,0.0f,0.0f,1.0f));
		Transform transform = new Transform(new Vector2f(100f,100f),new Vector2f(50f,50f));
		flower.addComponent(flowerSprite);
		flower.addComponent(transform);
		addGameObject(flower);
		
		GameObject flower2 = new GameObject();
		Sprite flower2Sprite = new Sprite("Assets/Textures/appleTest.png",new Vector4f(1.0f,0.0f,0.0f,1.0f));
		Transform transform2 = new Transform(new Vector2f(120f,120f),new Vector2f(50f,50f));
		flower2.addComponent(flower2Sprite);
		flower2.addComponent(transform2);
		addGameObject(flower2);
		
		//defaultShader = new Shader("Assets/Shaders/default.glsl");
		//defaultTexture = new Texture("Assets/Textures/testFlower.jpg");
		
		renderer = new Renderer();
		
		for (GameObject i : gameObjects) {
			renderer.add(i);
		}
		/***
		defaultShader.compile();
		
		// create vao vbo ebo buffer
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();
		
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER,vboID);
		glBufferData(GL_ARRAY_BUFFER,vertexBuffer,GL_STATIC_DRAW);
		
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
		elementBuffer.put(elementArray).flip();
		
		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,elementBuffer,GL_STATIC_DRAW);
		
		int positionsSize = 3;
		int colorSize = 4;
		int textureSize = 2;
		int vertexByteSize = (positionsSize +colorSize + textureSize) * Float.BYTES;
		glVertexAttribPointer(0,positionsSize,GL_FLOAT,false,vertexByteSize,0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1,colorSize,GL_FLOAT,false,vertexByteSize,positionsSize * Float.BYTES);
		glEnableVertexAttribArray(1);
		
		glVertexAttribPointer(2,textureSize,GL_FLOAT,false,vertexByteSize,(positionsSize +colorSize) * Float.BYTES);
		glEnableVertexAttribArray(2);
		***/
	}
	
}

