package engine;


import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import renderer.Shader;
import renderer.Texture;

public class Scene {
	
	private String vertexShaderSrc = "#version 330 core\n"
			+ "layout (location=0) in vec3 aPos;\n"
			+ "layout (location=1) in vec4 aColor;\n"
			+ "\n"
			+ "out vec4 fColor;\n"
			+ "void main() {\n"
			+ "	fColor = aColor;\n"
			+ "	gl_Position = vec4(aPos,1.0);\n"
			+ "}";
	
	private String fragmentShaderSrc = "#version 330 core\n"
			+ "\n"
			+ "in vec4 fColor;\n"
			+ "\n"
			+ "out vec4 color;\n"
			+ "\n"
			+ "void main() {\n"
			+ "	color = fColor;\n"
			+ "}";
	
	private int vertexID, fragmentID, shaderProgram;
	
	private float[] vertexArray = {
			//position 			//color					//texture
			0.5f, -0.5f, 0.0f,	1.0f, 0.0f, 0.0f, 0.0f, 1,1,//bot right
			-0.5f, 0.5f, 0.0f,	0.0f, 1.0f, 0.0f, 1.0f, 0,0,//top left
			0.5f, 0.5f, 0.0f,	1.0f, 0.0f, 1.0f, 1.0f, 1,0,//top right
			-0.5f, -0.5f, 0.0f,	1.0f, 1.0f, 0.0f, 1.0f, 0,1//bot left
			
	};
	
	private int[] elementArray = {
			2,1,0, //top right
			0,1,3 //bot left
	};
	
	private int vaoID, vboID, eboID;
	
	private Shader defaultShader;
	private Texture defaultTexture;
	
	public Scene() {
		init();
		System.out.println("Scene Created");
	};
	
	public void update() {
		
		defaultShader.use();
		
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
	}
	
	public void init() {
		
		defaultShader = new Shader("Assets/Shaders/default.glsl");
		defaultTexture = new Texture("Assets/Textures/testFlower.jpg");
		
		
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
	}
	
}

