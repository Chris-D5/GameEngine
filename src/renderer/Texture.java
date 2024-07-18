package renderer;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

import org.lwjgl.glfw.*;

import org.lwjgl.BufferUtils;

public class Texture {
	private String path;
	private int textureID;
	
	public Texture(String path) {
		this.path = path;
		
		textureID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D,textureID);
		
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
		//pixelate stretch or shrink
		//glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
		//glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
		//blur stretch or shrink
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
		
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		ByteBuffer image = stbi_load(path,width,height,channels,0);
		
		if (image == null) {
			assert false : "Error: Texture could not load image'" +path+ "'";
		} else {
			int color = 0;
			if (channels.get(0) == 3) {
				color = GL_RGB;
			}else if (channels.get(0) == 4) {
				color = GL_RGBA;
			}else {
				assert false : "Error: Texture invalid number of channels '" + channels.get(0) +"'";
			}
			glTexImage2D(GL_TEXTURE_2D,0,color,width.get(0),height.get(0),0,color,GL_UNSIGNED_BYTE,image);
		}
		
		stbi_image_free(image);
	}
	
	public void bind() {
		glBindTexture(GL_TEXTURE_2D,textureID);
	}
	
	public void unbind() {
		glBindTexture(GL_TEXTURE_2D,0);
	}
}
