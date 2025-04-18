package renderer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import Components.Sprite;
import Components.Transform;
import engine.GameObject;
import engine.Window;

public class Batch {
	private final int OFFSET = 2 * Float.BYTES + 4 * Float.BYTES;
	
	private Shader shader;
	
	private int[] elements;
	
	private float[] vertices;
	
	private float[] vertexArray = {
			//position 				//color					//texture
			100.5f, -0.5f, 0.0f,	1.0f, 0.0f, 0.0f, 0.0f, 1,1,//bot right
			-0.5f, 100.5f, 0.0f,	0.0f, 1.0f, 0.0f, 1.0f, 0,0,//top left
			100.5f, 100.5f, 0.0f,	1.0f, 0.0f, 1.0f, 1.0f, 1,0,//top right
			-0.5f, -0.5f, 0.0f,		1.0f, 1.0f, 0.0f, 1.0f, 0,1//bot left
			
	};
	
	private int vaoID, vboID, eboID;
	
	private int maxSize;
	
	private Sprite[] sprites;
	
	private Texture[] textures;
	
	private int currentNumSprites;
	
	public Batch(int batchSize) {
		shader = new Shader("Assets/Shaders/default.glsl");
		shader.compile();
		
		sprites = new Sprite[batchSize];
		
		textures = new Texture[batchSize];
		
		elements = new int[batchSize * 6];
				
		vertices = new float[batchSize * 6 * 4];
		
		maxSize = batchSize;
		start();
	}
	
	public void start() {
		// create vao vbo ebo buffer
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		/**
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();
		**/
		vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

		
		generateIndices();
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elements.length);
		elementBuffer.put(elements).flip();
				
		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,elementBuffer,GL_DYNAMIC_DRAW);
		
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
	}
	
	public void addSprite(Sprite sprite) {
		if (currentNumSprites < sprites.length) {
			this.sprites[currentNumSprites] = sprite;
			
			textures[currentNumSprites] = new Texture(sprite.getPath());
			generateVertex(currentNumSprites);
			currentNumSprites++;
		}
	}
	
	private void generateVertex(int index) { 
		int Offset = 9;
		int xOffset = 0;
		int yOffset = 0;
		float uOffset = 0;
		float vOffset = 0;
		
		for (int  i = 0; i < 4; i++) {
			
			if (i == 0) {
				xOffset = 0;
				yOffset = 0;
				uOffset = 0.0f;
				vOffset = 1.0f;
			}else if (i == 1) {
				xOffset = 1;
				yOffset = 0;
				uOffset = 1.0f;
				vOffset = 1.0f;
			} 
			else if (i == 2){
				xOffset = 1;
				yOffset = 1;
				uOffset = 1.0f;
				vOffset = 0.0f;
			}
			else if (i == 3) {
				xOffset = 0;
				yOffset = 1;
				uOffset = 0.0f;
				vOffset = 0.0f;
			}
			
			Vector2f vertexPos = sprites[index].getGameObject().getComponent(Transform.class).getPosition();
			Vector2f vertexScale = sprites[index].getGameObject().getComponent(Transform.class).getScale();
			vertices[index*Offset*4+i*Offset] = vertexPos.x+xOffset*vertexScale.x;
			vertices[index*Offset*4+i*Offset+1] = vertexPos.y+yOffset*vertexScale.y;
			vertices[index*Offset*4+i*Offset+2] = 0.0f;
			Vector4f vertexColor = sprites[index].getColor();
			
			vertices[index*Offset*4+i*Offset+3] = vertexColor.x;
			vertices[index*Offset*4+i*Offset+4] = vertexColor.y;
			vertices[index*Offset*4+i*Offset+5] = vertexColor.z;
			vertices[index*Offset*4+i*Offset+6] = vertexColor.w;
			vertices[index*Offset*4+i*Offset+7] = uOffset;
			vertices[index*Offset*4+i*Offset+8]	= vOffset;	
			
		}
		
		
	}
	
	private void generateIndices() {
		int Offsetelement = 6;
		int Offsetvertex = 4;
		for (int i = 0; i < maxSize; i++) {
			elements[i*Offsetelement] = 0+Offsetvertex*i;
			elements[i*Offsetelement+1] = 3+Offsetvertex*i;
			elements[i*Offsetelement+2] = 1+Offsetvertex*i;
			elements[i*Offsetelement+3] = 1+Offsetvertex*i;
			elements[i*Offsetelement+4] = 3+Offsetvertex*i;
			elements[i*Offsetelement+5] = 2+Offsetvertex*i;
		}
	}
	
	public void render () {
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
	    glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
		
		shader.use();
		shader.addMatrix("uProjection", Window.get().getScene().getCamera().getProjectionMatrix());
		shader.addMatrix("uView", Window.get().getScene().getCamera().getViewMatrix());
		
		
		for (int i = 0; i < currentNumSprites; i ++) {
			glActiveTexture(GL_TEXTURE0 + i);
			shader.addTexture("TEX_SAMPLER", i);
			textures[i].bind();
		}
		
		//glActiveTexture(GL_TEXTURE0);
		//shader.addTexture("TEX_SAMPLER", 0);
		//defaultTexture.bind();
		
		glBindVertexArray(vaoID);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glDrawElements(GL_TRIANGLES,currentNumSprites*6,GL_UNSIGNED_INT,0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glBindVertexArray(0);
		
		shader.detach();
	}
	
	public boolean isFull() {
		return currentNumSprites >= sprites.length;
	}
}
