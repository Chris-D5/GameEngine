package renderer;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Shader {
	
	private int shaderProgramID;
	
	private String vertexSrc;
	private String fragmentSrc;
	private String path;
	
	public Shader (String path) {
		this.path = path;
		try {
			String content = Files.readString(Paths.get(path));
			String[] splitContent = content.split("(#type)( )+([a-zA-Z]+)");
			
			int startIndex = content.indexOf("#type") + 6;
			int endIndex = content.indexOf("\r\n",startIndex);
			String[] types = new String[2];
			
			types[0] = content.substring(startIndex, endIndex).trim();
			
			startIndex = content.indexOf("#type",endIndex) + 6;
			endIndex = content.indexOf("\r\n",startIndex);
			
			types[1] = content.substring(startIndex, endIndex).trim();
			
			System.out.println(splitContent[1]);
			System.out.println(splitContent[2]);
			
			if (types[0].equals("vertex")) {
				vertexSrc = splitContent[1];
			}
			else if (types[0].equals("fragment")) {
				fragmentSrc = splitContent[1];
			}
			else {
				throw new IOException("Unexpect type: " + types[0]);
			}
			
			if (types[1].equals("vertex")) {
				vertexSrc = splitContent[2];
			}
			else if (types[1].equals("fragment")) {
				fragmentSrc = splitContent[2];
			}
			else {
				throw new IOException("Unexpect type: " + types[1]);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			assert false : "Error: Could not open file: '" + path + "'";
		}
	}
	
	public void compile() {
		int vertexID, fragmentID;
		vertexID = glCreateShader(GL_VERTEX_SHADER);
		
		glShaderSource(vertexID, vertexSrc);
		glCompileShader(vertexID);
		//check for errors
		int sucess = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if (sucess == GL_FALSE) {
			int length = glGetShaderi(vertexID,GL_INFO_LOG_LENGTH);
			System.out.println("Error: '"+ path +"'\n\tVertex shader compilation failed.");
			System.out.println(glGetShaderi(vertexID,length));
			assert false : "";
		}
		
		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		
		glShaderSource(fragmentID,fragmentSrc);
		glCompileShader(fragmentID);
		//check for errors
		if (glGetShaderi(fragmentID,GL_COMPILE_STATUS) == GL_FALSE) {
			int length = glGetShaderi(fragmentID,GL_INFO_LOG_LENGTH);
			System.out.println("Error: '" + path + "'\n\tFragment shader compilation failed.");
			System.out.println(glGetShaderi(fragmentID,length));
			assert false : "";
		}
		
		shaderProgramID = glCreateProgram();
		glAttachShader(shaderProgramID,vertexID);
		glAttachShader(shaderProgramID,fragmentID);
		glLinkProgram(shaderProgramID);
		//check for errors
		if (glGetProgrami(shaderProgramID,GL_LINK_STATUS) == GL_FALSE) {
			int length = glGetProgrami(shaderProgramID,GL_INFO_LOG_LENGTH);
			System.out.println("Error: '"+ path +"'\n\tLinking shader failed.");
			System.out.println(glGetProgramInfoLog(fragmentID,length));
			assert false : "";
		}
	}
	
	public void use() {
		glUseProgram(shaderProgramID);
	}
	
	public void detach() {
		glUseProgram(0);
	}
	
	public void addTexture(String name, int slot) {
		int location = glGetUniformLocation(shaderProgramID,name);
		use();
		glUniform1i(location, slot);
	}
}
